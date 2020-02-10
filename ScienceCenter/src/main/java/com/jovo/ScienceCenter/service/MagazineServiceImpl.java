package com.jovo.ScienceCenter.service;

import com.jovo.ScienceCenter.dto.*;
import com.jovo.ScienceCenter.exception.AlreadyExistsException;
import com.jovo.ScienceCenter.exception.NotFoundException;
import com.jovo.ScienceCenter.exception.TaskNotAssignedToYouException;
import com.jovo.ScienceCenter.model.*;
import com.jovo.ScienceCenter.model.Currency;
import com.jovo.ScienceCenter.repository.MagazineRepository;
import com.jovo.ScienceCenter.util.StringUtil;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class MagazineServiceImpl implements MagazineService {

    @Value("${payment-microservice.urls.backend.login}")
    private String pmLoginBackendUrl;

    @Value("${payment-microservice.urls.backend.registration.single}")
    private String pmSingleRegistrationBackendUrl;

    @Autowired
    private MagazineRepository magazineRepository;

    @Autowired
    private MembershipFeeService membershipFeeService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ScientificAreaService scientificAreaService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private FormService formService;

    private static final  PasswordGenerator passwordGenerator = new PasswordGenerator();
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss");




    //@EventListener(ApplicationReadyEvent.class)
    private void loginAllMagazines() {
        List<Magazine> magazines = getAllActivatedMagazines();

        magazines.stream()
                .forEach(magazine -> loginMagazine(magazine));
    }

    @Override
    public Magazine loginMagazine(Magazine magazine) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        LoginUserDTO userDTO = new LoginUserDTO(magazine.getUsername(), magazine.getPassword());

        HttpEntity<LoginUserDTO> httpEntity = new HttpEntity<LoginUserDTO>(userDTO, headers);
        ResponseEntity<TokenDTO> tokenDTOResponseEntity = restTemplate.exchange(pmLoginBackendUrl, HttpMethod.PUT, httpEntity, TokenDTO.class);
        if (tokenDTOResponseEntity.getStatusCode() == HttpStatus.OK) {
            String token = tokenDTOResponseEntity.getBody().getToken();
            magazine.setMerchantId(token);
            magazine = magazineRepository.save(magazine);
            System.out.println(magazine.getName() + " login - success");
            return magazine;
        }
        else {
            System.out.println(magazine.getName() + " login - fail");
            return null;
        }
    }

    @Override
    public void submitUserTask(String camundaUserId, String taskId, Map<String, Object> formFieldsMap)
            throws NotFoundException, TaskNotAssignedToYouException {
        //ProcessInstance pi = runtimeService.startProcessInstanceByKey("UserRegistrationProcess");

        //List<Task> tasks = taskService.createTaskQuery().processInstanceId(pi.getId()).list();
        //Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        //if (taskService.createTaskQuery().taskId(taskId).count() == 0) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            throw new NotFoundException("Task (id=".concat(taskId).concat(") doesn't exist!"));
        }

        if (!task.getAssignee().equals(camundaUserId)) {
            throw new TaskNotAssignedToYouException("The task(taskId=".concat(taskId).concat( " is assigned to ")
                    .concat(task.getAssignee()).concat(", not ").concat(camundaUserId).concat("!"));
        }

        formService.submitTaskForm(taskId, formFieldsMap);
    }

    @Override
    public void submitFirstUserTask(String camundaUserId, String processInstanceId, Map<String, Object> formFieldsMap)
            throws NotFoundException, TaskNotAssignedToYouException {
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
        if(task == null) {
            throw new NotFoundException("UserTask for process with id=".concat(processInstanceId).concat(" not found!"));
        }

        if (!task.getAssignee().equals(camundaUserId)) {
            throw new TaskNotAssignedToYouException("The task (processInstanceId=".concat(processInstanceId).concat(") is assigned to ")
                    .concat(task.getAssignee()).concat(", not ").concat(camundaUserId).concat("!"));
        }

        formService.submitTaskForm(task.getId(), formFieldsMap);
    }

    @Override
    public void activateMagazine(String name) {
        Magazine magazine = getMagazine(name);
        magazine.setMagazineStatus(Status.ACTIVATED);
        magazineRepository.save(magazine);
    }

    @Override
    public List<PendingMagazineDTO> getAllPendingMagazines() {
        List<Magazine> pendingMagazines = magazineRepository.findByMagazineStatus(Status.PENDING);
        return pendingMagazines.stream()
                .map(m -> new PendingMagazineDTO(m))
                .collect(Collectors.toList());
    }

    @Override
    public List<PendingMagazineDTO> getPendingMagazinesForChecking() {
        List<PendingMagazineDTO> pendingMagazineDTOs = getAllPendingMagazines();
        List<Task> tasks = taskService.createTaskQuery().taskName("CheckData").taskAssignee("admin").list();
        tasks.stream()
                .filter(task -> {
                    String checkedMagazineName = (String) runtimeService.getVariable(task.getExecutionId(), "checkedMagazineName");
                    return checkedMagazineName == null;
                })
                .forEach(task -> {
                    String name = (String) runtimeService.getVariable(task.getExecutionId(), "name");
                    PendingMagazineDTO pendingMagazineDTO = pendingMagazineDTOs.stream()
                            .filter(pm -> pm.getName().equals(name))
                            .findFirst().orElse(null);
                    pendingMagazineDTO.setTaskId(task.getId());
                });

        return pendingMagazineDTOs.stream()
                .filter(p -> p.getTaskId() != null)
                .collect(Collectors.toList());
    }

    @Override
    public List<FixMagazineDTO> getMagazinesWithInvalidData(String camundaUserId) {
        List<Task> tasks = taskService.createTaskQuery().taskName("EnterNewMagazineData").taskAssignee(camundaUserId).list();
        return tasks.stream()
                .filter(task -> {
                    String checkedMagazineName = (String) runtimeService.getVariable(task.getExecutionId(), "checkedMagazineName");
                    return checkedMagazineName != null;
                })
                .map(task -> {
                    String name = (String) runtimeService.getVariable(task.getExecutionId(), "name");
                    return new FixMagazineDTO(task.getProcessInstanceId(), name);
                })
                .collect(Collectors.toList());
    }

    @Override
    public Magazine getMagazine(Long id) {
        return magazineRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Magazine (id=" + id + ") not found!"));
    }

    @Override
    public List<Magazine> getAllActivatedMagazines() {
        return magazineRepository.findByMagazineStatus(Status.ACTIVATED);
    }

    @Override
    public List<MagazineWithoutPaidStatusDTO> getAllActivatedMagazinesByEditor(UserData editor) {
        return magazineRepository.findAllActivatedMagazinesByEditor(editor).stream()
                .map(m -> new MagazineWithoutPaidStatusDTO(m))
                .collect(Collectors.toList());
    }

    @Override
    public List<MagazineDTO> getActivatedMagazinesWithPaidStatus(Long payerId) {
        List<Magazine> magazines = magazineRepository.findByMagazineStatus(Status.ACTIVATED);
        return magazines.stream()
                .map(m -> {
                    String paidUpTo;

                    try {
                        MembershipFee membershipFee =
                                membershipFeeService.getActivatedMembershipFeeByMagazineIdAndPayerId(m.getId(), payerId);
                        paidUpTo = "Paid up to " + membershipFee.getValidUntil().format(DATE_TIME_FORMATTER);
                    } catch (Exception e) {
                        paidUpTo = null;
                    }

                    return new MagazineDTO(m, paidUpTo);
                })
                .collect(Collectors.toList());
    }

    @Override
    public MembershipFee makeMembershipFee(Long authorId, Long magazineId, double price , Currency currency) {
        MembershipFee membershipFee = new MembershipFee(magazineId, authorId, price, currency);
        membershipFeeService.save(membershipFee);
        return  membershipFee;
    }

    private String generatePassword() {
        CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
        CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
        lowerCaseRule.setNumberOfCharacters(2);

        CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
        CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
        upperCaseRule.setNumberOfCharacters(2);

        CharacterData digitChars = EnglishCharacterData.Digit;
        CharacterRule digitRule = new CharacterRule(digitChars);
        digitRule.setNumberOfCharacters(2);

        CharacterData specialChars = new CharacterData() {
            public String getErrorCode() {
                return "special_chars_error_code";
            }

            public String getCharacters() {
                return "!@#$%^&*()_+";
            }
        };
        CharacterRule splCharRule = new CharacterRule(specialChars);
        splCharRule.setNumberOfCharacters(2);

        String password = passwordGenerator.generatePassword(10, splCharRule, lowerCaseRule,
                upperCaseRule, digitRule);
        return password;
    }

    @Override
    public void saveNewMagazine(String name, String issn, List<Long> scientificAreaIds, PayerType payerType, double membershipFee,
                                Currency currency, String mainEditorUsername, String checkedMagazineName) {
        Objects.requireNonNull(name, "Name should not be null!");
        Objects.requireNonNull(issn, "Issn should not be null!");
        Objects.requireNonNull(mainEditorUsername, "MainEditorUsername should not be null!");
        Objects.requireNonNull(scientificAreaIds, "ScientificAreaIds should not be null!");

        StringUtil.requireNonEmptyString(name, "Name should not be empty!");
        StringUtil.requireNonEmptyString(issn, "Issn should not be empty!");
        StringUtil.requireNonEmptyString(mainEditorUsername, "MainEditorUsername should not be empty!");

        if (membershipFee <= 0) {
            throw new RuntimeException("MembershipFee <= 0");
        }

        Magazine oldDataMgazine = null;
        if (checkedMagazineName != null) {
            oldDataMgazine = getMagazine(checkedMagazineName);
        }

        // Ako je oldDataMgazine != null, znaci da magazin vec postoji i da se radi o ispravljanju podataka u casopisu.
        // Ako je ovo !oldDataMgazine.getName().equals(name) tacno, znaci da je naziv casopisa promenjen i da bi trebalo proveriti,
        // da li se mozda novi naziv poklapa sa nazivom nekog drugog casopisa ne racunajuci stari naziv casopisa koji se isto nalazi u bazi
        if ( ((oldDataMgazine != null && !oldDataMgazine.getName().equals(name)) || oldDataMgazine == null)
                && magazineRepository.existsByName(name)) {
            throw new AlreadyExistsException("There is already a magazine with a name: ".concat(name));
        }

        // Ako je oldDataMgazine != null, znaci da magazin vec postoji i da se radi o ispravljanju podataka u casopisu.
        // Ako je ovo !oldDataMgazine.getIssn().equals(issn) tacno, znaci da je issn casopisa promenjen i da bi trebalo proveriti,
        // da li se mozda novi issn poklapa sa issn-om nekog drugog casopisa ne racunajuci stari issn casopisa koji se isto nalazi u bazi
        if ( ((oldDataMgazine != null && !oldDataMgazine.getIssn().equals(issn)) || oldDataMgazine == null)
                && magazineRepository.existsByIssn(issn)) {
            throw new AlreadyExistsException("There is already a magazine with an issn: ".concat(issn));
        }

        if (scientificAreaIds.isEmpty()) {
            throw new RuntimeException("ScientificAreaIds empty!");
        }

        List<ScientificArea> scientificAreas = scientificAreaService.getScientificAreasByIds(scientificAreaIds);
        for (ScientificArea sa : scientificAreas) {
            if (sa == null) {
                throw new RuntimeException("Invalid scientificAreaIds!");
            }
        }

        UserData mainEditor = userService.getUserDataByUsername(mainEditorUsername);

        if (oldDataMgazine == null) {
            String username = "magazine_" + magazineRepository.findAll().size();
            String password = generatePassword();
            Magazine magazine = new Magazine(name, issn, username, password, membershipFee, currency, scientificAreas,
                                                mainEditor, payerType);
            magazineRepository.save(magazine);
        }
        else {
            oldDataMgazine.setName(name);
            oldDataMgazine.setIssn(issn);
            oldDataMgazine.setMembershipFee(membershipFee);
            oldDataMgazine.setCurrency(currency);
            oldDataMgazine.setScientificAreas(new HashSet<ScientificArea>(scientificAreas));
            oldDataMgazine.setMainEditor(mainEditor);
            magazineRepository.save(oldDataMgazine);
        }
    }

    @Override
    public Magazine getMagazine(String name) {
        return magazineRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Magazine (name=".concat(name).concat(") not found!")));
    }

    @Override
    public void saveEditorsAndReviewersInMagazine(String magazineName, List<Long> editorIds, List<Long> reviewerIds) {
        Objects.requireNonNull(magazineName, "MagazineName should not be null!");
        Objects.requireNonNull(editorIds, "EditorIds should not be null!");
        Objects.requireNonNull(reviewerIds, "ReviewerIds should not be null!");

        StringUtil.requireNonEmptyString(magazineName, "MagazineName should not be empty!");

        if (editorIds.isEmpty()) {
            throw new RuntimeException("EditorIds empty!");
        }

        List<UserData> editors = userService.getUserDatasByIds(editorIds);
        for(UserData editor : editors) {
            if (editor == null) {
                throw new RuntimeException("Invalid editorIds!");
            }
        }

        if (reviewerIds.size() < 2) {
            throw new RuntimeException("ReviewerIds.size() >= 2");
        }

        List<UserData> reviewers = userService.getUserDatasByIds(reviewerIds);
        for(UserData reviewer : reviewers) {
            if (reviewer == null) {
                throw new RuntimeException("Invalid reviewerIds!");
            }
        }

        Magazine magazine = getMagazine(magazineName);
        magazine.setEditors(editors);
        magazine.setReviewers(reviewers);
        magazineRepository.save(magazine);
    }

    @Override
    public FormFieldsDto getCreateMagazineFormFields(String camundaUserId, String processInstanceId) throws NotFoundException, TaskNotAssignedToYouException {
        if (processInstanceId == null) {
            Map<String, Object> variablesMap = new HashMap<String, Object>();
            variablesMap.put("processInitiator", camundaUserId);
            // iz nekog razloga camunda engine ne dodeli ulogovanog korisnika varijabli processInitiator
            ProcessInstance pi = runtimeService.startProcessInstanceByKey("CreateNewMagazineProcess", variablesMap);
            processInstanceId = pi.getId();
        }


        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).list().get(0);

        if(task == null) {
            throw new NotFoundException("UserTask for process with id=".concat(processInstanceId).concat(" not found!"));
        }

        if (!task.getAssignee().equals(camundaUserId)) {
            throw new TaskNotAssignedToYouException("The task (processInstanceId=".concat(processInstanceId).concat(") is assigned to ")
                    .concat(task.getAssignee()).concat(", not ").concat(camundaUserId).concat("!"));
        }

        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();
        for(FormField fp : properties) {
            System.out.println(fp.getId() + fp.getType());
        }

        return new FormFieldsDto(task.getId(), processInstanceId, properties);
    }

    @Override
    public void savePaymentTypesForMagazine(String magazineName, List<PaymentTypeDTO> paymentTypes) {
        Magazine magazine = getMagazine(magazineName);

        RegistrationPaymentConcentratorDTO registrationPaymentConcentratorDTO = new RegistrationPaymentConcentratorDTO(
                                                                                    magazineName, magazine.getUsername(),
                                                                                     magazine.getPassword(), magazine.getPassword(),
                                                                                        paymentTypes);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RegistrationPaymentConcentratorDTO> httpEntity =
                new HttpEntity<RegistrationPaymentConcentratorDTO>(registrationPaymentConcentratorDTO, headers);

        ResponseEntity<Void> responseEntity = restTemplate.exchange(pmSingleRegistrationBackendUrl,
                HttpMethod.POST, httpEntity, Void.class);

        if (responseEntity.getStatusCode() != HttpStatus.CREATED) {
            System.out.println("PaymentConcentrator registration error!");
            throw new RuntimeException("PaymentConcentrator registration error!");
        }
    }
}

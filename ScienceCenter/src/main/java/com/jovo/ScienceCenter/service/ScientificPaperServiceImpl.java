package com.jovo.ScienceCenter.service;


import com.jovo.ScienceCenter.dto.*;
import com.jovo.ScienceCenter.exception.NotFoundException;
import com.jovo.ScienceCenter.exception.TaskNotAssignedToYouException;
import com.jovo.ScienceCenter.model.*;
import com.jovo.ScienceCenter.repository.ScientificPaperRepository;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class ScientificPaperServiceImpl implements ScientificPaperService {

    @Autowired
    private ScientificPaperRepository scientificPaperRepository;

    @Autowired
    private TaskService taskService;

    @Autowired
    private FormService formService;

    @Autowired
    private UserService userService;

    @Autowired
    private ScientificAreaService scientificAreaService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private MagazineService magazineService;

    @Autowired
    private CoauthorService coauthorService;

    @Autowired
    private FileService fileService;


    @Override
    public String startProcessForAddingScientificPaper(String camundaUserId, Long magazineId) {
        Map<String, Object> variablesMap = new HashMap<String, Object>();
        variablesMap.put("processInitiator", camundaUserId);
        // iz nekog razloga camunda engine ne dodeli ulogovanog korisnika varijabli processInitiator
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("ProcesObradePodnetogTeksta", variablesMap);
        String processInstanceId = pi.getId();

        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
        if(task == null) {
            throw new NotFoundException("UserTask for process with id=".concat(processInstanceId).concat(" not found!"));
        }

        if (!task.getAssignee().equals(camundaUserId)) {
            throw new TaskNotAssignedToYouException("The task (processInstanceId=".concat(processInstanceId).concat(") is assigned to ")
                    .concat(task.getAssignee()).concat(", not ").concat(camundaUserId).concat("!"));
        }

        Map<String, Object> formFieldsMap = new HashMap<String, Object>();
        formFieldsMap.put("magazineId", magazineId);
        formService.submitTaskForm(task.getId(), formFieldsMap);

        return pi.getId();
    }

    @Override
    public ScientificPaper getScientificPaper(Long id) {
        return scientificPaperRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("ScientificPaper (id=".concat("" + id).concat(") not found!")));
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
    public void selectMagazine(String processInstnaceId, Long magazineId) {
        Magazine selectedMagazine = magazineService.getMagazine(magazineId);
        runtimeService.setVariable(processInstnaceId, "selectedMagazine", selectedMagazine);
    }

    @Override
    public FormFieldsDto getAddScientificPaperFormFields(String camundaUserId, String processInstanceId) {
        TaskQuery taskQuery = taskService.createTaskQuery().processInstanceId(processInstanceId);
        if (taskQuery.count() == 0) {
            throw new NotFoundException("taskQuery.count() == 0 (processInstanceId="+processInstanceId+")");
        }

        Task task = taskQuery.list().get(0);

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
    public List<ScientificPaperFrontendDTO> getScientificPapersForProcessing(String camundaUserId) {
        List<ScientificPaperFrontendDTO> scientificPaperFrontendDTOs = new ArrayList<ScientificPaperFrontendDTO>();
        List<Task> tasks = taskService.createTaskQuery().taskName("ProcessingScientificPaper").list();
        tasks.stream()
            .forEach(task -> {
                MainEditorAndScientificPaper mainEditorAndScientificPaper =
                (MainEditorAndScientificPaper) runtimeService.getVariable(task.getExecutionId(), "mainEditorAndScientificPaper");
                if (mainEditorAndScientificPaper.getMainEditor().getCamundaUserId().equals(camundaUserId)) {
                    ScientificPaper scientificPaper = mainEditorAndScientificPaper.getScientificPaper();

                    UserData author = scientificPaper.getAuthor();
                    User authorCamundaUser = userService.getUser(author.getCamundaUserId());
                    String authorStr = author.getCamundaUserId().concat(" (").concat(authorCamundaUser.getFirstName()).concat(" ")
                            .concat(authorCamundaUser.getLastName()).concat(")");

                    List<String> coauthors = scientificPaper.getCoauthors().stream()
                            .map(c -> c.getFirstName().concat(" ").concat(c.getLastName()))
                            .collect(Collectors.toList());

                    String coauthorsStr = String.join(", ", coauthors);

                    ScientificPaperFrontendDTO scientificPaperFrontendDTO =
                            new ScientificPaperFrontendDTO(task.getId(),  scientificPaper.getTitle(), scientificPaper.getKeywords(),
                                    scientificPaper.getScientificPaperAbstract(), scientificPaper.getScientificArea().getName(),
                                    authorStr, coauthorsStr);
                    scientificPaperFrontendDTOs.add(scientificPaperFrontendDTO);
                }
            });

        return scientificPaperFrontendDTOs;
    }

    @Override
    public void submitUserTask(String camundaUserId, String taskId, Map<String, Object> formFieldsMap)
            throws NotFoundException, TaskNotAssignedToYouException {
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
    public void saveNewScientificPaper(String processInstnaceId, UserData author, String title, List<CoauthorDTO> coauthorDTOs, String keywords,
                                       String scientificPaperAbstract, Long selectedScientificAreaId, String fileName) {
        List<Coauthor> coauthors = coauthorDTOs.stream()
                                        .map(c -> {
                                            Long registeresUserId;
                                            UserData registeredUser = null;
                                            try {
                                                registeredUser = userService.getUserDataByEmail(c.getEmail());
                                                registeresUserId = registeredUser.getId();
                                            } catch (Exception e) {
                                                registeresUserId = null;
                                            }
                                            Coauthor coauthor = new Coauthor(c.getFirstName(), c.getLastName(), c.getEmail(),
                                                    c.getAddress(), c.getCity(), c.getCountry(), registeresUserId);
                                            coauthorService.save(coauthor);
                                            return coauthor;
                                        })
                                        .collect(Collectors.toList());
        ScientificArea selectedScientificArea = scientificAreaService.getScientificArea(selectedScientificAreaId);

        String relativePathToFile = File.separator.concat(selectedScientificArea.getName()).concat(File.separator)
                                                                                            .concat(fileName);
        ScientificPaper scientificPaper = new ScientificPaper(title, keywords, scientificPaperAbstract,
                                 relativePathToFile, selectedScientificArea, author, coauthors);
        scientificPaper = scientificPaperRepository.save(scientificPaper);

        Magazine selectedMagazine = (Magazine) runtimeService.getVariable(processInstnaceId, "selectedMagazine");

        MainEditorAndScientificPaper mainEditorAndScientificPaper =
                new MainEditorAndScientificPaper(selectedMagazine.getMainEditor(), scientificPaper);
        runtimeService.setVariable(processInstnaceId, "mainEditorAndScientificPaper", mainEditorAndScientificPaper);
    }

    @Override
    public MainEditorAndScientificPaper getMainEditorAndScientificPaper(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            throw new NotFoundException("Task (id=".concat(taskId).concat(") doesn't exist!"));
        }
        return (MainEditorAndScientificPaper) runtimeService.getVariable(task.getProcessInstanceId(), "mainEditorAndScientificPaper");
    }

    @Override
    public List<ScientificPaperFrontendDtoWithComment> getScientificPapersForRepairing(String camundaUserId) {
        List<ScientificPaperFrontendDtoWithComment> scientificPaperFrontendDtoWithComments =
                                                                new ArrayList<ScientificPaperFrontendDtoWithComment>();
        List<Task> tasks = taskService.createTaskQuery().taskName("RepairScientificPaper").taskAssignee(camundaUserId)
                                .list();
        tasks.stream()
                .forEach(task -> {
                    MainEditorAndScientificPaper mainEditorAndScientificPaper =
                            (MainEditorAndScientificPaper) runtimeService.getVariable(task.getExecutionId(), "mainEditorAndScientificPaper");
                    ScientificPaper scientificPaper = mainEditorAndScientificPaper.getScientificPaper();

                    UserData author = scientificPaper.getAuthor();
                    User authorCamundaUser = userService.getUser(author.getCamundaUserId());
                    String authorStr = author.getCamundaUserId().concat(" (").concat(authorCamundaUser.getFirstName()).concat(" ")
                            .concat(authorCamundaUser.getLastName()).concat(")");

                    List<String> coauthors = scientificPaper.getCoauthors().stream()
                            .map(c -> c.getFirstName().concat(" ").concat(c.getLastName()))
                            .collect(Collectors.toList());

                    String coauthorsStr = String.join(", ", coauthors);

                    String commentForScientificPaper = (String) runtimeService.getVariable(task.getProcessInstanceId(), "commentForScientificPaper");

                    ScientificPaperFrontendDtoWithComment scientificPaperFrontendDtoWithComment =
                            new ScientificPaperFrontendDtoWithComment(task.getId(),  scientificPaper.getTitle(), scientificPaper.getKeywords(),
                                    scientificPaper.getScientificPaperAbstract(), scientificPaper.getScientificArea().getName(),
                                    authorStr, coauthorsStr, commentForScientificPaper);
                    scientificPaperFrontendDtoWithComments.add(scientificPaperFrontendDtoWithComment);
                });

        return scientificPaperFrontendDtoWithComments;
    }

    @Override
    public void repairScientificPaper(String processInstanceId, String repairedFileName) {
        MainEditorAndScientificPaper mainEditorAndScientificPaper =
            (MainEditorAndScientificPaper) runtimeService.getVariable(processInstanceId, "mainEditorAndScientificPaper");
        ScientificPaper scientificPaper = mainEditorAndScientificPaper.getScientificPaper();

        String relativePathToFile = File.separator.concat(scientificPaper.getScientificArea().getName())
                .concat(File.separator).concat(repairedFileName);

        scientificPaper.setRelativePathToFile(relativePathToFile);
        scientificPaperRepository.save(scientificPaper);
    }

    @Override
    public byte[] getPdfContent(String taskId) throws IOException {
        MainEditorAndScientificPaper mainEditorAndScientificPaper = getMainEditorAndScientificPaper(taskId);
        ScientificPaper scientificPaper = mainEditorAndScientificPaper.getScientificPaper();

        return fileService.getPdfContent(scientificPaper.getRelativePathToFile());
    }

    @Override
    public void selectEditorOfScientificArea(String processInstanceId) {
        MainEditorAndScientificPaper mainEditorAndScientificPaper =
            (MainEditorAndScientificPaper) runtimeService.getVariable(processInstanceId, "mainEditorAndScientificPaper");
        ScientificArea scientificArea = mainEditorAndScientificPaper.getScientificPaper().getScientificArea();

        Magazine selectedMagazine = (Magazine) runtimeService.getVariable(processInstanceId, "selectedMagazine");

        List<UserData> editors = selectedMagazine.getEditors().stream()
                .filter(editor -> editor.getUserStatus() == Status.ACTIVATED
                        && editor.getScientificAreas().contains(scientificArea))
                .collect(Collectors.toList());

        UserData selectedEditorOfScientificArea = null;

        if (editors.isEmpty()) {
            selectedEditorOfScientificArea = mainEditorAndScientificPaper.getMainEditor();
        }
        else {
            Random rand = new Random();
            selectedEditorOfScientificArea = editors.get(rand.nextInt(editors.size()));
            // TODO kasnije napravi da se pametnije odlucuje ko ce biti selectedEditorOfScientificArea
        }

        runtimeService.setVariable(processInstanceId, "selectedEditorOfScientificArea", selectedEditorOfScientificArea);

    }

    @Override
    public List<EditorOrReviewerDTO> getReviewersForScientificPaper(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            throw new NotFoundException("Task (id=".concat(taskId).concat(") doesn't exist!"));
        }

        String processInstanceId = task.getProcessInstanceId();

        MainEditorAndScientificPaper mainEditorAndScientificPaper =
                (MainEditorAndScientificPaper) runtimeService.getVariable(processInstanceId, "mainEditorAndScientificPaper");
        ScientificArea scientificArea = mainEditorAndScientificPaper.getScientificPaper().getScientificArea();

        Magazine selectedMagazine = (Magazine) runtimeService.getVariable(processInstanceId, "selectedMagazine");

        List<UserData> reviewers = selectedMagazine.getReviewers().stream()
                .filter(reviewer -> reviewer.getUserStatus() == Status.ACTIVATED
                        && reviewer.getScientificAreas().contains(scientificArea))
                .collect(Collectors.toList());

        boolean mainEditorMustBeReviewer = reviewers.isEmpty();
        if (mainEditorMustBeReviewer) {
            reviewers.add(mainEditorAndScientificPaper.getMainEditor());
        }

        List<EditorOrReviewerDTO> reviewerDTOs = reviewers.stream()
                .map(r -> {
                    List<String> scientificAreaNames = r.getScientificAreas().stream()
                            .map(ScientificArea::getName).collect(Collectors.toList());
                    String scientificAreaNamesStr = String.join(", ", scientificAreaNames);
                    org.camunda.bpm.engine.identity.User user = userService.getUser(r.getCamundaUserId());
                    return new EditorOrReviewerDTO(r.getId(), r.getCamundaUserId(), user.getFirstName(), user.getLastName(), scientificAreaNamesStr, mainEditorMustBeReviewer);
                })
                .collect(Collectors.toList());
        return  reviewerDTOs;
    }

    @Override
    public List<TaskIdAndTitleDTO> getScientificPapersForSelectingReviews(String camundaUserId) {
        List<TaskIdAndTitleDTO> taskIdAndTitleDTOs = new ArrayList<TaskIdAndTitleDTO>();
        List<Task> tasks = taskService.createTaskQuery().taskName("SelectReviewersForScientificPaper").taskAssignee(camundaUserId)
                .list();
        tasks.stream()
                .forEach(task -> {
                    MainEditorAndScientificPaper mainEditorAndScientificPaper =
                            (MainEditorAndScientificPaper) runtimeService.getVariable(task.getExecutionId(), "mainEditorAndScientificPaper");
                    ScientificPaper scientificPaper = mainEditorAndScientificPaper.getScientificPaper();

                    taskIdAndTitleDTOs.add(new TaskIdAndTitleDTO(task.getId(), scientificPaper.getTitle()));
                });

        return taskIdAndTitleDTOs;
    }

    @Override
    public void saveSelectedReviewersForScientificPaper(String processInstanceId, List<Long> reviewerIds) {
        List<UserData> reviewers;
        if (reviewerIds.size() == 1) {
            MainEditorAndScientificPaper mainEditorAndScientificPaper =
                    (MainEditorAndScientificPaper) runtimeService.getVariable(processInstanceId, "mainEditorAndScientificPaper");
            UserData mainEditor = mainEditorAndScientificPaper.getMainEditor();
            if (mainEditor.getId().longValue() != reviewerIds.get(0).longValue()) {
                throw new RuntimeException("ReviewerIds.size() == 1 (this is not main editor)");
            }
            else {
                reviewers = new ArrayList<UserData>();
                reviewers.add(mainEditor);
            }

        }
        else if (reviewerIds.size() == 0) {
            throw new RuntimeException("ReviewerIds.size() must be >= 2");
        }
        else {
            reviewers = userService.getUserDatasByIds(reviewerIds);
            for (UserData reviewer : reviewers) {
                if (reviewer == null) {
                    throw new RuntimeException("Invalid reviewerIds!");
                }
            }
        }

        runtimeService.setVariable(processInstanceId, "selectedReviewersForScientificPaper", reviewers);

    }
}
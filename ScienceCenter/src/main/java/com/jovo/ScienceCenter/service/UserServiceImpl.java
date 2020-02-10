package com.jovo.ScienceCenter.service;


import com.jovo.ScienceCenter.dto.*;
import com.jovo.ScienceCenter.exception.AlreadyExistsException;
import com.jovo.ScienceCenter.exception.NotFoundException;
import com.jovo.ScienceCenter.exception.TaskNotAssignedToYouException;
import com.jovo.ScienceCenter.model.*;
import com.jovo.ScienceCenter.repository.UserDataRepository;
import com.jovo.ScienceCenter.util.StringUtil;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDataRepository userDataRepository;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private FormService formService;

	@Autowired
	private IdentityService identityService;

	@Autowired
	private ScientificAreaService scientificAreaService;

	@Autowired
	private ConfirmationService confirmationService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private RequestForReviewerService requestForReviewerService;

	@Autowired
	private MagazineService magazineService;


	@EventListener(ApplicationReadyEvent.class)
	public void createCamundaUsers() {
		createCamundaUser("guest", "guest", "Guest", "Guest", "guest@camunda.org");
		createCamundaUser("admin", "admin", "Admin", "Admin", "admin@camunda.org");
		createCamundaUser("pera", "pera", "Pera", "Pera", "pera@camunda.org");
		createCamundaUser("zika", "zika", "Zika", "Zika", "zika@camunda.org");
		createCamundaUser("mika", "mika", "Mika", "Mika", "mika@camunda.org");
		createCamundaUser("editor", "editor", "Editor", "Editor", "editor1@camunda.org");
		createCamundaUser("editor1", "editor1", "Editor1", "Editor1", "editor2@camunda.org");
		createCamundaUser("editor2", "editor2", "Editor2", "Editor2", "editor3@camunda.org");
		createCamundaUser("editor3", "editor3", "Editor3", "Editor3", "editor4@camunda.org");
		createCamundaUser("editor4", "editor4", "Editor4", "Editor4", "editor@camunda.org");
		createCamundaUser("editor5", "editor5", "Editor5", "Editor5", "editor5@camunda.org");
		createCamundaUser("editor6", "editor6", "Editor6", "Editor6", "editor6@camunda.org");
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	@Override
	public UserData getLoggedUser() throws Exception {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		try {
			org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) auth
					.getPrincipal();
			return getUserDataByUsername(user.getUsername())
;
		} catch (Exception e) {
			throw new Exception("UserData not found!");
		}

	}

	@Override
	public void setAuthenticatedUserIdInCamunda(String userId) {
		identityService.setAuthenticatedUserId(userId);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void save(UserData userData) throws Exception {
		userDataRepository.save(userData);
	}

	@Override
    public UserData getUserDataByUsername(String username) {
        org.camunda.bpm.engine.identity.User camundaUser = getUser(username);
        if(camundaUser == null) {
        	return null;
		}

		try {
			return getUserData(camundaUser.getId());
		} catch (Exception e) {
			return null;
		}
	}

	@Override
    public UserData getUserData(String camundaUserId) {
        return userDataRepository.findByCamundaUserId(camundaUserId)
				.orElseThrow(() -> new NotFoundException("UserData (camundaUserId=" + camundaUserId + ") not found!"));
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	@Override
	public org.camunda.bpm.engine.identity.User getUser(String username) {
		return identityService.createUserQuery().userId(username).singleResult();
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	@Override
	public org.camunda.bpm.engine.identity.User getUser(String username, String password) {
        org.camunda.bpm.engine.identity.User user = identityService.createUserQuery().userId(username).singleResult();
        if (user == null) {
            return null;
        }
        boolean validPassword = identityService.checkPassword(user.getId(), password); // user.getId() == username
	    if (validPassword) {
	        return user;
        }
	    else {
	        return null;
        }
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	@Override
	public boolean exists(String username) {
	    return identityService.createUserQuery().userId(username).count() > 0;
	}


	@Override
	public void register(String delegateExecutionId, String username, String password, String repeatedPassword,
							String firstName, String lastName, String email, String city, String country,
							List<Long> scientificAreaIds) {

		Objects.requireNonNull(username, "Username should not be null!");
        Objects.requireNonNull(password, "Password should not be null!");
        Objects.requireNonNull(repeatedPassword, "RepeatedPassword should not be null!");
        Objects.requireNonNull(firstName, "FirstName should not be null!");
        Objects.requireNonNull(lastName, "LastName should not be null!");
        Objects.requireNonNull(email, "Email should not be null!");
        Objects.requireNonNull(city, "City should not be null!");
        Objects.requireNonNull(country, "Country should not be null!");
        Objects.requireNonNull(scientificAreaIds, "ScientificAreaIds should not be null!");

		StringUtil.requireNonEmptyString(username, "Username should not be empty!");
		StringUtil.requireNonEmptyString(password, "Password should not be empty!");
		StringUtil.requireNonEmptyString(repeatedPassword, "RepeatedPassword should not be empty!");
		StringUtil.requireNonEmptyString(firstName, "FirstName should not be empty!");
		StringUtil.requireNonEmptyString(lastName, "LastName should not be empty!");
		StringUtil.requireNonEmptyString(email, "Email should not be empty!");
		StringUtil.requireNonEmptyString(city, "City should not be empty!");
		StringUtil.requireNonEmptyString(country, "Country should not be empty!");

		if (exists(username)) {
			throw new AlreadyExistsException("There is already a user with a username: ".concat(username));
		}

		if (!password.equals(repeatedPassword)) {
			throw new RuntimeException("The password and the repeated password are not the same");
		}

		if (scientificAreaIds.isEmpty()) {
			throw new RuntimeException("ScientificAreaIds empty!");
		}

		List<ScientificArea> scientificAreas = scientificAreaService.getScientificAreasByIds(scientificAreaIds);
		for(ScientificArea sa : scientificAreas) {
			if (sa == null) {
				throw new RuntimeException("Invalid scientificAreaIds!");
			}
		}

		org.camunda.bpm.engine.identity.User camundaUser = createCamundaUser(username, password, firstName, lastName,  email);

		Role role = roleService.getRole("USER");
		UserData userData = new UserData(camundaUser.getId(), city, country, scientificAreas, role);
		userData = userDataRepository.save(userData);

		String token = UUID.randomUUID().toString();
		//String token = UUID.fromString(username).toString();
		runtimeService.setVariable(delegateExecutionId, "token", token);
		Confirmation confirmation = new Confirmation(token, userData);
		confirmationService.save(confirmation);
	}

	@Override
	public org.camunda.bpm.engine.identity.User createCamundaUser(String username, String password, String firstName, String lastName, String email) {
		org.camunda.bpm.engine.identity.User camundaUser = identityService.newUser(username);
		camundaUser.setPassword(password);
		camundaUser.setFirstName(firstName);
		camundaUser.setLastName(lastName);
		camundaUser.setEmail(email);
		identityService.saveUser(camundaUser);
		return camundaUser;
	}

	@Override
	public FormFieldsDto getRegistrationFormFields() {
		//provera da li korisnik sa id-jem pera postoji
		//createCamundaUser("pera", "pera", "Pera", "Pera", "pera@camunda.org");
		//createCamundaUser("zika", "zika", "Zika", "Zika", "zika@camunda.org");
		//createCamundaUser("mika", "mika", "Mika", "Mika", "mika@camunda.org");
		//List<org.camunda.bpm.engine.identity.User> users = identityService.createUserQuery().list();

		Map<String, Object> variablesMap = new HashMap<String, Object>();
		variablesMap.put("processInitiator", "guest");
		// iz nekog razloga camunda engine ne dodeli ulogovanog korisnika varijabli processInitiator
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("UserRegistrationProcess", variablesMap);

		Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);

		TaskFormData tfd = formService.getTaskFormData(task.getId());
		List<FormField> properties = tfd.getFormFields();
		for(FormField fp : properties) {
			System.out.println(fp.getId() + fp.getType());
		}

		return new FormFieldsDto(task.getId(), pi.getId(), properties);
	}

	@Override
	public void submitUserTask(String camundaUserId, String taskId, Map<String, Object> formFieldsMap)
			throws NotFoundException, TaskNotAssignedToYouException {
		//ProcessInstance pi = runtimeService.startProcessInstanceByKey("UserRegistrationProcess");

		//List<Task> tasks = taskService.createTaskQuery().processInstanceId(pi.getId()).list();
		//Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		if (task == null) {
			throw new NotFoundException("Task (id=".concat(taskId).concat(") doesn't exist!"));
		}

		if (!task.getAssignee().equals(camundaUserId)) {
			throw new TaskNotAssignedToYouException("The task (taskId=".concat(taskId).concat(") is assigned to ").concat(task.getAssignee()).concat(", not ")
					.concat(camundaUserId).concat("!"));
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
			throw new TaskNotAssignedToYouException("The task(processInstanceId=".concat(processInstanceId).concat(") is assigned to ")
					.concat(task.getAssignee()).concat(", not ").concat(camundaUserId).concat("!"));
		}

		formService.submitTaskForm(task.getId(), formFieldsMap);
	}

	@Override
	public void checkConfirmationAndActivateUser(String confirmationToken, boolean reviewer) {
		Confirmation confirmation = confirmationService.getConfirmation(confirmationToken);

        confirmationService.delete(confirmation);

		/*if(LocalDateTime.now().isAfter( confirmation.getExpirationDate())) {
			throw new RuntimeException("The confirmation of registration deadline has passed!");
		}*/

		UserData userData = confirmation.getUserData();
		Objects.requireNonNull(userData);

		userData.setUserStatus(Status.ACTIVATED);
		userData = userDataRepository.save(userData);

		if(reviewer) {
			RequestForReviewer requestForReviewer = new RequestForReviewer(userData);
			requestForReviewerService.save(requestForReviewer);
		}
	}

	@Override
	public List<UserDTO> getAllUsers() {
		List<org.camunda.bpm.engine.identity.User> users = identityService.createUserQuery().list();
		List<UserDTO> userDTOs = new ArrayList<UserDTO>();

		users.stream()
				.filter(user -> !user.getId().equals("admin"))
				.forEach(user -> {
					UserData userData = userDataRepository.findByCamundaUserId(user.getId()).orElse(null);

					if (userData != null) {
						List<String> scientificAreaNames = userData.getScientificAreas().stream()
								.map(ScientificArea::getName).collect(Collectors.toList());
						String scientificAreaNamesStr = String.join(", ", scientificAreaNames);
						List<DTO> roleDTOs = userData.getRoles().stream()
														.map(role -> new DTO(role.getId(), role.getName()))
														.collect(Collectors.toList());
						UserDTO userDTO = new UserDTO(userData.getId(), user.getId(), user.getFirstName(),
								user.getLastName(), user.getEmail(), userData.getCity(),
								userData.getCountry(), scientificAreaNamesStr, userData.isReviewer(),
								userData.getUserStatus(), roleDTOs);
						userDTOs.add(userDTO);
					}
				});
		return userDTOs;
	}

	@Override
	public void deleteUser(Long id) {
		UserData userData = userDataRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("UserData (id=".concat(""+id).concat(") not found!")));
		userDataRepository.delete(userData);
		identityService.deleteUser(userData.getCamundaUserId());
	}

	@Override
	public void activateOrDeactivate(Long id, boolean activate) {
		UserData userData = userDataRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("UserData (id=".concat(""+id).concat(") not found!")));
		if (activate){
			userData.setUserStatus(Status.ACTIVATED);
		}
		else {
			userData.setUserStatus(Status.DEACTIVATED);
		}
		userDataRepository.save(userData);
	}

	@Override
	public void confirmReviewerStatus(Long id, boolean confirmed) {
		RequestForReviewer requestForReviewer = requestForReviewerService.getRequestForReviewer(id);
		requestForReviewerService.delete(requestForReviewer);

		if (confirmed) {
			UserData userData = requestForReviewer.getUserData();
			if (userData == null) {
				throw new NotFoundException("For RequestForReviewer(id=".concat(""+id).concat("), userData not found!"));
			}
			userData.setReviewer(true);
			userDataRepository.save(userData);
		}
	}

	@Override
	public List<RequestForReviewerDTO> getAllRequestsForReviewer() {
		List<RequestForReviewerDTO> requests = requestForReviewerService.getAllRequestsForReviewer();
		List<Task> tasks = taskService.createTaskQuery().taskName("ConfirmReviewerStatus").taskAssignee("admin").list();
		tasks.stream()
				.forEach(task -> {
					String username = (String) runtimeService.getVariable(task.getExecutionId(), "username");
					RequestForReviewerDTO request = requests.stream()
							.filter(r -> r.getUsername().equals(username))
							.findFirst().orElse(null);
					if (request != null) {
						request.setTaskId(task.getId());
					}

				});

		return requests.stream()
				.filter(r -> r.getTaskId() != null)
				.collect(Collectors.toList());
	}

	@Override
	public void deleteRegistration(String username) {
		UserData userData = userDataRepository.findByCamundaUserId(username)
				.orElseThrow(() -> new NotFoundException("UserData (username=".concat(username).concat(") not found!")));
		userDataRepository.delete(userData);

		identityService.deleteUser(username);
	}

	@Override
	public List<UserData> getUserDatasByIds(List<Long> ids) {
		return userDataRepository.findAllById(ids);
	}

	@Override
	public List<EditorOrReviewerDTO> getEditorsForMagazineInCurrentProcess(String processInstanceId) {
		String magazineName = (String) runtimeService.getVariable(processInstanceId, "name");
		Magazine magazine = magazineService.getMagazine(magazineName);
		Set<ScientificArea> scientificAreas = magazine.getScientificAreas();

		List<UserData> editors = userDataRepository.findAll();
		List<EditorOrReviewerDTO> editorDTOs = editors.stream()
				.filter(r -> r.getRoles().stream()
								.map(Role::getName)
								.filter(roleName -> roleName.equalsIgnoreCase("EDITOR")).count() > 0
				)
				.filter(e -> e.getScientificAreas().stream().filter(scientificAreas::contains).count() > 0) // intersect
				// override-oveane su equals i hashCode metode
				.map(e -> {
					List<String> scientificAreaNames = e.getScientificAreas().stream()
							.map(ScientificArea::getName).collect(Collectors.toList());
					String scientificAreaNamesStr = String.join(", ", scientificAreaNames);
					org.camunda.bpm.engine.identity.User user = getUser(e.getCamundaUserId());
					return new EditorOrReviewerDTO(e.getId(), e.getCamundaUserId(), user.getFirstName(), user.getLastName(), scientificAreaNamesStr, false);
				})
				.collect(Collectors.toList());
		return  editorDTOs;
	}

	@Override
	public UserData getUserDataByEmail(String email) {
		org.camunda.bpm.engine.identity.User user = identityService.createUserQuery().userEmail(email).singleResult();
		if (user == null) {
			throw new NotFoundException("Camunda user (email=" + email + ") not found!");
		}

		return getUserData(user.getId());

	}

	@Override
	public List<EditorOrReviewerDTO> getReviewersForMagazineInCurrentProcess(String processInstanceId) {
		String magazineName = (String) runtimeService.getVariable(processInstanceId, "name");
		Magazine magazine = magazineService.getMagazine(magazineName);
		Set<ScientificArea> scientificAreas = magazine.getScientificAreas();

		List<UserData> reviewers = userDataRepository.findAll();
		List<EditorOrReviewerDTO> reviewerDTOs = reviewers.stream()
				.filter(r -> r.isReviewer())
				.filter(r -> r.getScientificAreas().stream().filter(scientificAreas::contains).count() > 0) // intersect
				// override-oveane su equals i hashCode metode
				.map(r -> {
					List<String> scientificAreaNames = r.getScientificAreas().stream()
							.map(ScientificArea::getName).collect(Collectors.toList());
					String scientificAreaNamesStr = String.join(", ", scientificAreaNames);
					org.camunda.bpm.engine.identity.User user = getUser(r.getCamundaUserId());
					return new EditorOrReviewerDTO(r.getId(), r.getCamundaUserId(), user.getFirstName(), user.getLastName(), scientificAreaNamesStr, false);
				})
				.collect(Collectors.toList());
		return  reviewerDTOs;
	}

	@Override
	public UserData getUserDataByScientificArea(ScientificArea scientificArea) {
		return userDataRepository.findByScientificArea(scientificArea)
				.orElseThrow(() -> new NotFoundException("UserData (scientificAreas contains " + scientificArea.getName() + ") not found!"));
	}
}
package com.jovo.ScienceCenter.service;


import com.jovo.ScienceCenter.dto.EditorOrReviewerDTO;
import com.jovo.ScienceCenter.dto.FormFieldsDto;
import com.jovo.ScienceCenter.dto.RequestForReviewerDTO;
import com.jovo.ScienceCenter.dto.UserDTO;
import com.jovo.ScienceCenter.model.UserData;

import java.util.List;
import java.util.Map;


public interface UserService {

	UserData getLoggedUser() throws Exception;
	
    void save(UserData user) throws Exception;

    UserData getUserDataByUsername(String username);

    UserData getUserData(String camundaUserId);

    org.camunda.bpm.engine.identity.User createCamundaUser(String username, String password, String firstName,
                                                           String lastName, String email);

    org.camunda.bpm.engine.identity.User getUser(String username);

    org.camunda.bpm.engine.identity.User getUser(String username, String password);

    boolean exists(String username);

    void register(String delegateExecutionId, String username, String password, String repeatedPassword, String firstName,
                     String lastName, String email, String city, String country,
                     List<Long> scientificAreaIds);

    FormFieldsDto getRegistrationFormFields();

    void submitUserTask(String taskId, Map<String, Object> formFieldsMap) throws Exception;

    void submitFirstUserTask(String processInstanceId, Map<String, Object> formFieldsMap) throws Exception;

    void checkConfirmationAndActivateUser(String confirmationToken, boolean reviewer);

    List<UserDTO> getAllUsers();

    void deleteUser(Long id);

    void activateOrDeactivate(Long id, boolean activate);

    void confirmReviewerStatus(Long id, boolean confirmed);

    List<RequestForReviewerDTO> getAllRequestsForReviewer();

    void deleteRegistration(String username);

    List<UserData> getUserDatasByIds(List<Long> editorIds);

    List<EditorOrReviewerDTO> getReviewersForMagazineInCurrentProcess(String processInstanceId);

    List<EditorOrReviewerDTO> getEditorsForMagazineInCurrentProcess(String processInstanceId);

}

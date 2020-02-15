package com.jovo.ScienceCenter.service;

import com.jovo.ScienceCenter.dto.*;
import com.jovo.ScienceCenter.exception.NotFoundException;
import com.jovo.ScienceCenter.exception.TaskNotAssignedToYouException;
import com.jovo.ScienceCenter.model.*;

import java.util.List;
import java.util.Map;

public interface MagazineService {

    Magazine getMagazine(Long id);

    Magazine getMagazine(String name);

    List<Magazine> getAllActivatedMagazines();

    List<MagazineWithoutPaidStatusDTO> getAllActivatedMagazinesByEditor(UserData editor);

    MembershipFee makeMembershipFee(Long authorId, Long magazineId, double price , Currency currency);

    void saveNewMagazine(String name, String issn, List<Long> scientificAreaIds, PayerType payerType, double membershipFee,
                         Currency currency, String mainEditorUsername, String checkedMagazineName);

    void saveEditorsAndReviewersInMagazine(String magazineName, List<Long> editorIds, List<Long> reviewerIds);

    FormFieldsDto getCreateMagazineFormFields(String camundaUserId, String processInstanceId)
            throws NotFoundException, TaskNotAssignedToYouException;

    void savePaymentTypesForMagazine(String magazineName, List<PaymentTypeDTO> paymentTypes);

    void submitUserTask(String camundaUserId, String taskId, Map<String, Object> formFieldsMap)
            throws NotFoundException, TaskNotAssignedToYouException;

    void submitFirstUserTask(String camundaUserId, String processInstanceId, Map<String, Object> formFieldsMap)
            throws NotFoundException, TaskNotAssignedToYouException;

    void activateMagazine(String name);

    List<PendingMagazineDTO> getAllPendingMagazines();

    List<PendingMagazineDTO> getPendingMagazinesForChecking();

    List<FixMagazineDTO> getMagazinesWithInvalidData(String camundaUserId);

    Magazine loginMagazine(Magazine magazine);

    List<MagazineDTO> getActivatedMagazinesWithPaidStatus(Long payerId);

    void save(Magazine selectedMagazine);
}

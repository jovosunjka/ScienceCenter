package com.jovo.ScienceCenter.service;

import com.jovo.ScienceCenter.dto.FixMagazineDTO;
import com.jovo.ScienceCenter.dto.FormFieldsDto;
import com.jovo.ScienceCenter.dto.PaymentTypeDTO;
import com.jovo.ScienceCenter.dto.PendingMagazineDTO;
import com.jovo.ScienceCenter.model.Currency;
import com.jovo.ScienceCenter.model.Magazine;
import com.jovo.ScienceCenter.model.MembershipFee;
import com.jovo.ScienceCenter.model.PayerType;

import java.util.List;
import java.util.Map;

public interface MagazineService {

    Magazine getMagazine(Long id);

    Magazine getMagazine(String name);

    List<Magazine> getAllActivatedMagazines();

    MembershipFee makeMembershipFee(Long authorId, Long magazineId, double price , Currency currency);

    void saveNewMagazine(String name, String issn, List<Long> scientificAreaIds, PayerType payerType, double membershipFee,
                         Currency currency, String mainEditorUsername, String checkedMagazineName);

    void saveEditorsAndReviewersInMagazine(String magazineName, List<Long> editorIds, List<Long> reviewerIds);

    FormFieldsDto getCreateMagazineFormFields(String processInstanceId) throws Exception;

    void savePaymentTypesForMagazine(String magazineName, List<PaymentTypeDTO> paymentTypes);

    void submitUserTask(String taskId, Map<String, Object> formFieldsMap) throws Exception;

    void submitFirstUserTask(String processInstanceId, Map<String, Object> formFieldsMap) throws Exception;

    void activateMagazine(String name);

    List<PendingMagazineDTO> getAllPendingMagazines();

    List<PendingMagazineDTO> getPendingMagazinesForChecking();

    List<FixMagazineDTO> getMagazinesWithInvalidData() throws Exception;

    Magazine loginMagazine(Magazine magazine);
}

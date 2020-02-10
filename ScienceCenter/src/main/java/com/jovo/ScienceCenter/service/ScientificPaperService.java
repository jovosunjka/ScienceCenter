package com.jovo.ScienceCenter.service;

import com.jovo.ScienceCenter.dto.*;
import com.jovo.ScienceCenter.exception.NotFoundException;
import com.jovo.ScienceCenter.exception.TaskNotAssignedToYouException;
import com.jovo.ScienceCenter.model.MainEditorAndScientificPaper;
import com.jovo.ScienceCenter.model.ScientificPaper;
import com.jovo.ScienceCenter.model.UserData;
<<<<<<< HEAD
import com.jovo.ScienceCenter.util.ReviewingResult;
=======
>>>>>>> 0bf60d5178864860cbaed111bbc052c87417ba2f

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ScientificPaperService {

    ScientificPaper getScientificPaper(Long id);

    void submitFirstUserTask(String camundaUserId, String processInstanceId, Map<String, Object> formFieldsMap)
            throws NotFoundException, TaskNotAssignedToYouException;

    void saveNewScientificPaper(String processInstnaceId, UserData author, String title, List<CoauthorDTO> coauthorDTOs, String keywords,
                                String scientificPaperAbstract, Long selectedScientificAreaId, String fileName);

    String startProcessForAddingScientificPaper(String camundaUserId, Long magazineId);

    void selectMagazine(String processInstnaceId, Long magazineId);

    FormFieldsDto getAddScientificPaperFormFields(String camundaUserId, String processInstanceId);

    List<ScientificPaperFrontendDTO> getScientificPapersForProcessing(String camundaUserId);

    void submitUserTask(String camundaUserId, String taskId, Map<String, Object> formFieldsMap)
            throws NotFoundException, TaskNotAssignedToYouException;

    MainEditorAndScientificPaper getMainEditorAndScientificPaper(String taskId);

<<<<<<< HEAD
    List<ScientificPaperFrontendDtoWithComment> getFirstRepairScientificPaper(String camundaUserId);

    List<ScientificPaperFrontendDtoWithReviewingDTOs> getSecondRepairScientificPaper(String camundaUserId);

    List<ScientificPaperFrontendDtoWithComment> getFinalRepairScientificPaper(String camundaUserId);
=======
    List<ScientificPaperFrontendDtoWithComment> getScientificPapersForRepairing(String camundaUserId);
>>>>>>> 0bf60d5178864860cbaed111bbc052c87417ba2f

    void repairScientificPaper(String processInstanceId, String repairedFileName);

    byte[] getPdfContent(String taskId) throws IOException;

    void selectEditorOfScientificArea(String processInstanceId);

    List<EditorOrReviewerDTO> getReviewersForScientificPaper(String taskId);

    List<TaskIdAndTitleDTO> getScientificPapersForSelectingReviews(String camundaUserId);

    void saveSelectedReviewersForScientificPaper(String processInstanceId, List<Long> reviewerIds);
<<<<<<< HEAD

    void addReviewingResult(/*String mainProcessInstanceId,*/ String taskId, ReviewingResult reviewingResult);

    List<ScientificPaperFrontendDtoWithReviewings> getFirstDecision(String camundaUserId);

    List<ScientificPaperFrontendDtoWithReviewingsAndAnswers> getSecondDecision(String camundaUserId);

    List<ScientificPaperFrontendDtoWithComment> getFinalDecision(String camundaUserId);

    List<ScientificPaperFrontendDTO> getScientificPapersForReviewing(String camundaUserId);

    void publishScientificPaper(String processInstanceId);

    void assignDoi(String processInstanceId);

    void prepareForSearching(String processInstanceId);

    List<ScientificPaperFrontendDTOWithId> getScientificPapersForMagazine(Long magazineId);

    byte[] getPdfContent(Long scientificPaperId) throws IOException;
=======
>>>>>>> 0bf60d5178864860cbaed111bbc052c87417ba2f
}

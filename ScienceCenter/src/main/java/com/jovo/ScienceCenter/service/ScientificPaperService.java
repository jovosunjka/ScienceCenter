package com.jovo.ScienceCenter.service;

import com.jovo.ScienceCenter.dto.*;
import com.jovo.ScienceCenter.exception.NotFoundException;
import com.jovo.ScienceCenter.exception.TaskNotAssignedToYouException;
import com.jovo.ScienceCenter.model.MainEditorAndScientificPaper;
import com.jovo.ScienceCenter.model.ScientificPaper;
import com.jovo.ScienceCenter.model.UserData;

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

    List<ScientificPaperFrontendDtoWithComment> getScientificPapersForRepairing(String camundaUserId);

    void repairScientificPaper(String processInstanceId, String repairedFileName);

    byte[] getPdfContent(String taskId) throws IOException;

    void selectEditorOfScientificArea(String processInstanceId);

    List<EditorOrReviewerDTO> getReviewersForScientificPaper(String taskId);

    List<TaskIdAndTitleDTO> getScientificPapersForSelectingReviews(String camundaUserId);

    void saveSelectedReviewersForScientificPaper(String processInstanceId, List<Long> reviewerIds);
}

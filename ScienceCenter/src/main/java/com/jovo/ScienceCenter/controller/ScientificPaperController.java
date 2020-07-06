package com.jovo.ScienceCenter.controller;

import com.jovo.ScienceCenter.dto.*;
import com.jovo.ScienceCenter.exception.NotFoundException;
import com.jovo.ScienceCenter.exception.TaskNotAssignedToYouException;
import com.jovo.ScienceCenter.model.*;
import com.jovo.ScienceCenter.service.FileService;
import com.jovo.ScienceCenter.service.ScientificAreaService;
import com.jovo.ScienceCenter.service.ScientificPaperService;
import com.jovo.ScienceCenter.service.UserService;
import com.jovo.ScienceCenter.util.ReviewingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(value = "/scientific-papers")
public class ScientificPaperController {

    @Autowired
    private UserService userService;

    @Autowired
    private ScientificPaperService scientificPaperService;

    @Autowired
    private FileService fileService;

    @Autowired
    private ScientificAreaService scientificAreaService;


    @RequestMapping(value ="/start-process", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProcessInstanceIdDTO> startProcessForAddingScientificPaper(@RequestParam("magazineId") Long magazineId) {
        UserData loggedUser = null;
        try {
            loggedUser = userService.getLoggedUser();
        } catch (Exception e) {
            new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        String processInstanceId = scientificPaperService.startProcessForAddingScientificPaper(loggedUser.getCamundaUserId(),
                magazineId);

        return new ResponseEntity<ProcessInstanceIdDTO>(new ProcessInstanceIdDTO(processInstanceId), HttpStatus.OK);
    }

    @RequestMapping(value ="/select-magazine", method = RequestMethod.GET)
    public ResponseEntity selectMagazine(@RequestParam("processInstanceId") String processInstanceId,
                                                               @RequestParam("magazineId") Long magazineId) {
        UserData loggedUser = null;
        try {
            loggedUser = userService.getLoggedUser();
        } catch (Exception e) {
            new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        Map<String, Object> formFieldsMap = new HashMap<String, Object>();
        formFieldsMap.put("magazineId", magazineId);

        try {
            scientificPaperService.submitFirstUserTask(loggedUser.getCamundaUserId(), processInstanceId, formFieldsMap);
        } catch (NotFoundException | TaskNotAssignedToYouException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value ="/add", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity addScientificPaper(@RequestParam("processInstanceId") String processInstanceId,
                                             @RequestPart("scientific_paper_file") MultipartFile file,
                                             @RequestPart("scientific_paper_data") List<IdValueDTO> idValueDTOList) {
        UserData loggedUser = null;
        try {
            loggedUser = userService.getLoggedUser();
        } catch (Exception e) {
            new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        if (file == null || idValueDTOList == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }


        /*Map<String, Object> formFieldsMap = new HashMap<String, Object>();
        formFieldsMap.put("title", scientificPaperDTO.getTitle());
        formFieldsMap.put("coauthors", scientificPaperDTO.getCoauthors());
        formFieldsMap.put("keywords", scientificPaperDTO.getKeywords());
        formFieldsMap.put("abstract", scientificPaperDTO.getScientificPaperAbstract());
        formFieldsMap.put("selectedScientificAreaId", scientificPaperDTO.getScientificAreaId());
        formFieldsMap.put("fileName", file.getName());*/

        IdValueDTO idValueDTO  = idValueDTOList.stream()
                .filter(idValue -> idValue.getId().equals("selectedScientificAreaId"))
                .findFirst().orElse(null);
        if (idValueDTO == null) {
            return new ResponseEntity("selectedScientificAreaId not found!", HttpStatus.BAD_REQUEST);
        }
        Long selectedScientificAreaId = Long.parseLong(idValueDTO.getValue().toString());
        ScientificArea selectedScientificArea = scientificAreaService.getScientificArea(selectedScientificAreaId);
        try {
            fileService.save(file, selectedScientificArea.getName());
        } catch (IOException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        Map<String, Object> formFieldsMap = new HashMap<String, Object>();
        idValueDTOList.stream()
                .forEach(field -> formFieldsMap.put(field.getId(), field.getValue()));
        formFieldsMap.put("fileName", file.getOriginalFilename());

        try {
            scientificPaperService.submitFirstUserTask(loggedUser.getCamundaUserId(), processInstanceId, formFieldsMap);
        } catch (NotFoundException | TaskNotAssignedToYouException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value ="/first-repair", method = RequestMethod.PUT, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity firstRepair(@RequestParam("taskId") String taskId,
                                      @RequestPart("scientific_paper_file") MultipartFile file) {
        UserData loggedUser = null;
        try {
            loggedUser = userService.getLoggedUser();
        } catch (Exception e) {
            new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        if (file == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        MainEditorAndScientificPaper mainEditorAndScientificPaper = scientificPaperService
                .getMainEditorAndScientificPaper(taskId);

        ScientificPaper scientificPaper = mainEditorAndScientificPaper.getScientificPaper();

        try {
            fileService.remove(scientificPaper.getRelativePathToFile());
        } catch (IOException e) {
            System.out.println("Removing file ("+scientificPaper.getRelativePathToFile()+") error!");
        }

        try {
            fileService.save(file, scientificPaper.getScientificArea().getName());
        } catch (IOException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        Map<String, Object> formFieldsMap = new HashMap<String, Object>();
        formFieldsMap.put("repairedFileName", file.getOriginalFilename());

        try {
            scientificPaperService.submitUserTask(loggedUser.getCamundaUserId(), taskId, formFieldsMap);
        } catch (NotFoundException | TaskNotAssignedToYouException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value ="/second-repair", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity secondRepair(@RequestParam("taskId") String taskId,
                                       @RequestPart("scientific_paper_file") MultipartFile file,
                                       @RequestPart("scientific_paper_data") AnswersDTO answersDTO) {
        UserData loggedUser = null;
        try {
            loggedUser = userService.getLoggedUser();
        } catch (Exception e) {
            new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        if (file == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        MainEditorAndScientificPaper mainEditorAndScientificPaper = scientificPaperService
                .getMainEditorAndScientificPaper(taskId);

        ScientificPaper scientificPaper = mainEditorAndScientificPaper.getScientificPaper();

        try {
            fileService.remove(scientificPaper.getRelativePathToFile());
        } catch (IOException e) {
            System.out.println("Removing file ("+scientificPaper.getRelativePathToFile()+") error!");
        }

        try {
            fileService.save(file, scientificPaper.getScientificArea().getName());
        } catch (IOException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        Map<String, Object> formFieldsMap = new HashMap<String, Object>();
        formFieldsMap.put("repairedFileName", file.getOriginalFilename());
        formFieldsMap.put("answers", answersDTO.getAnswers());

        try {
            scientificPaperService.submitUserTask(loggedUser.getCamundaUserId(), taskId, formFieldsMap);
        } catch (NotFoundException | TaskNotAssignedToYouException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value ="/final-repair", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity finalRepair(@RequestParam("taskId") String taskId,
                                      @RequestPart("scientific_paper_file") MultipartFile file,
                                      @RequestPart("scientific_paper_data") AnswersDTO answersDTO) {
        UserData loggedUser = null;
        try {
            loggedUser = userService.getLoggedUser();
        } catch (Exception e) {
            new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        if (file == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        MainEditorAndScientificPaper mainEditorAndScientificPaper = scientificPaperService
                .getMainEditorAndScientificPaper(taskId);

        ScientificPaper scientificPaper = mainEditorAndScientificPaper.getScientificPaper();

        try {
            fileService.remove(scientificPaper.getRelativePathToFile());
        } catch (IOException e) {
            System.out.println("Removing file ("+scientificPaper.getRelativePathToFile()+") error!");
        }

        try {
            fileService.save(file, scientificPaper.getScientificArea().getName());
        } catch (IOException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        Map<String, Object> formFieldsMap = new HashMap<String, Object>();
        formFieldsMap.put("repairedFileName", file.getOriginalFilename());
        formFieldsMap.put("answers", answersDTO.getAnswers());

        try {
            scientificPaperService.submitUserTask(loggedUser.getCamundaUserId(), taskId, formFieldsMap);
        } catch (NotFoundException | TaskNotAssignedToYouException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value ="/first-decision", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity firstDecision(@RequestParam("taskId") String taskId,
                                        @RequestBody EditorOfScientificAreaDecisionDTO editorOfScientificAreaDecisionDTO) {
        UserData loggedUser = null;
        try {
            loggedUser = userService.getLoggedUser();
        } catch (Exception e) {
            new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        Map<String, Object> formFieldsMap = new HashMap<String, Object>();
        formFieldsMap.put("editorOfScientificAreaDecision", editorOfScientificAreaDecisionDTO
                .getEditorOfScientificAreaDecision().name());

        try {
            scientificPaperService.submitUserTask(loggedUser.getCamundaUserId(), taskId, formFieldsMap);
        } catch (NotFoundException | TaskNotAssignedToYouException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value ="/second-decision", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity secondDecision(@RequestParam("taskId") String taskId,
                                         @RequestBody SecondDecisionDTO secondDecisionDTO) {
        UserData loggedUser = null;
        try {
            loggedUser = userService.getLoggedUser();
        } catch (Exception e) {
            new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        Map<String, Object> formFieldsMap = new HashMap<String, Object>();
        formFieldsMap.put("editorDecision", secondDecisionDTO.getEditorDecision().name());
        formFieldsMap.put("editorCommentForAuthor", secondDecisionDTO.getEditorCommentForAuthor());

        try {
            scientificPaperService.submitUserTask(loggedUser.getCamundaUserId(), taskId, formFieldsMap);
        } catch (NotFoundException | TaskNotAssignedToYouException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value ="/final-decision", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity secondDecision(@RequestParam("taskId") String taskId,
                                         @RequestBody FinalDecisionDTO finalDecisionDTO) {
        UserData loggedUser = null;
        try {
            loggedUser = userService.getLoggedUser();
        } catch (Exception e) {
            new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        Map<String, Object> formFieldsMap = new HashMap<String, Object>();
        formFieldsMap.put("editorDecision", finalDecisionDTO.getEditorDecision().name());

        try {
            scientificPaperService.submitUserTask(loggedUser.getCamundaUserId(), taskId, formFieldsMap);
        } catch (NotFoundException | TaskNotAssignedToYouException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/add-scientific-paper-form-fields", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FormFieldsDto> getAddScientificPaperFormFields(@RequestParam("processInstanceId")
                                                                                 String processInstanceId) {
        UserData loggedUser = null;
        try {
            loggedUser = userService.getLoggedUser();
        } catch (Exception e) {
            new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        FormFieldsDto formFieldsDto = null;
        try {
            formFieldsDto = scientificPaperService.getAddScientificPaperFormFields(loggedUser.getCamundaUserId(), processInstanceId);
        } catch (NotFoundException | TaskNotAssignedToYouException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<FormFieldsDto>(formFieldsDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/for-processing", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ScientificPaperFrontendDTO>> getScientificPapersForProcessing() {
        UserData loggedUser = null;
        try {
            loggedUser = userService.getLoggedUser();
        } catch (Exception e) {
            new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        List<ScientificPaperFrontendDTO> scientificPaperFrontendDTOs =
                scientificPaperService.getScientificPapersForProcessing(loggedUser.getCamundaUserId());
        return new ResponseEntity<List<ScientificPaperFrontendDTO>>(scientificPaperFrontendDTOs, HttpStatus.OK);
    }

    @RequestMapping(value = "/cities", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getCities() {

        List<String> cities = scientificPaperService.getCities();
        return new ResponseEntity<List<String>>(cities, HttpStatus.OK);
    }

    @RequestMapping(value = "/for-first-repair", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ScientificPaperFrontendDtoWithComment>> getFirstRepairScientificPaper() {
        UserData loggedUser = null;
        try {
            loggedUser = userService.getLoggedUser();
        } catch (Exception e) {
            new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        List<ScientificPaperFrontendDtoWithComment> scientificPaperFrontendDtoWithComments =
                scientificPaperService.getFirstRepairScientificPaper(loggedUser.getCamundaUserId());
        return new ResponseEntity<List<ScientificPaperFrontendDtoWithComment>>(scientificPaperFrontendDtoWithComments,
                HttpStatus.OK);
    }

    @RequestMapping(value = "/for-second-repair", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ScientificPaperFrontendDtoWithReviewingDTOs>> getSecondRepairScientificPaper() {
        UserData loggedUser = null;
        try {
            loggedUser = userService.getLoggedUser();
        } catch (Exception e) {
            new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        List<ScientificPaperFrontendDtoWithReviewingDTOs> scientificPaperFrontendDtoWithReviewings =
                scientificPaperService.getSecondRepairScientificPaper(loggedUser.getCamundaUserId());
        return new ResponseEntity<List<ScientificPaperFrontendDtoWithReviewingDTOs>>(scientificPaperFrontendDtoWithReviewings,
                HttpStatus.OK);
    }

    @RequestMapping(value = "/for-final-repair", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ScientificPaperFrontendDtoWithComment>> getFinalRepairScientificPaper() {
        UserData loggedUser = null;
        try {
            loggedUser = userService.getLoggedUser();
        } catch (Exception e) {
            new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        List<ScientificPaperFrontendDtoWithComment> scientificPaperFrontendDtoWithComments =
                scientificPaperService.getFinalRepairScientificPaper(loggedUser.getCamundaUserId());
        return new ResponseEntity<List<ScientificPaperFrontendDtoWithComment>>(scientificPaperFrontendDtoWithComments,
                HttpStatus.OK);
    }

    @RequestMapping(value = "/process", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity processScientificPaper(@RequestParam("taskId") String taskId,
                                                 @RequestBody ProcessingScientificPaperDTO processingScientificPaperDTO) {
        UserData loggedUser = null;
        try {
            loggedUser = userService.getLoggedUser();
        } catch (Exception e) {
            new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        Map<String, Object> formFieldsMap = new HashMap<String, Object>();
        formFieldsMap.put("scientificPaperStatus", processingScientificPaperDTO.getStatus().name());
        formFieldsMap.put("commentForScientificPaper", processingScientificPaperDTO.getComment());

        try {
            scientificPaperService.submitUserTask(loggedUser.getCamundaUserId(), taskId, formFieldsMap);
        } catch (NotFoundException | TaskNotAssignedToYouException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value ="/pdf-by-id", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<byte[]> getPdfContent2(@RequestParam("scientificPaperId") Long scientificPaperId) {
        byte[] bytes = null;
        try {
            bytes = scientificPaperService.getPdfContent(scientificPaperId);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity(bytes, HttpStatus.OK);
    }

    @RequestMapping(value ="/pdf", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<byte[]> getPdfContent(@RequestParam("taskId") String taskId) {
        byte[] bytes = null;
        try {
            bytes = scientificPaperService.getPdfContent(taskId);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity(bytes, HttpStatus.OK);
    }

    @RequestMapping(value = "/reviewers-for-scientific-paper", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EditorOrReviewerDTO>> getReviewersForScientificPaper(
            @RequestParam("taskId") String taskId,
            @RequestParam("filter") ReviewersFilter filter) {
        List<EditorOrReviewerDTO> reviewerDTOs = scientificPaperService.getReviewersForScientificPaper(taskId, filter);
        return new ResponseEntity<List<EditorOrReviewerDTO>>(reviewerDTOs, HttpStatus.OK);
    }

    @RequestMapping(value = "/for-selecting-reviewers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TaskIdAndTitleDTO>> getScientificPapersForSelectingReviews() {
        UserData loggedUser = null;
        try {
            loggedUser = userService.getLoggedUser();
        } catch (Exception e) {
            new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        List<TaskIdAndTitleDTO> taskIdAndTitleDTOs =
                scientificPaperService.getScientificPapersForSelectingReviews(loggedUser.getCamundaUserId());
        return new ResponseEntity<List<TaskIdAndTitleDTO>>(taskIdAndTitleDTOs, HttpStatus.OK);
    }

    @RequestMapping(value = "/select-reviewers", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addEditorsAndReviewer(@RequestParam("taskId") String taskId,
                                                @RequestBody ReviewersDTO reviewersDTO) {
        UserData loggedUser = null;
        try {
            loggedUser = userService.getLoggedUser();
        } catch (Exception e) {
            new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        List<String> reviewersStrList = reviewersDTO.getReviewers().stream()
                .map(r -> r.toString()).collect(Collectors.toList());
        String reviewers = String.join(",", reviewersStrList);

        Map<String, Object> formFieldsMap = new HashMap<String, Object>();
        formFieldsMap.put("reviewers", reviewers);
        try {
            scientificPaperService.submitUserTask(loggedUser.getCamundaUserId(), taskId, formFieldsMap);
        } catch (NotFoundException | TaskNotAssignedToYouException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/for-reviewing", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ScientificPaperFrontendDTO>> getScientificPapersForReviewing() {
        UserData loggedUser = null;
        try {
            loggedUser = userService.getLoggedUser();
        } catch (Exception e) {
            new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        List<ScientificPaperFrontendDTO> scientificPaperFrontendDTOs =
                scientificPaperService.getScientificPapersForReviewing(loggedUser.getCamundaUserId());
        return new ResponseEntity<List<ScientificPaperFrontendDTO>>(scientificPaperFrontendDTOs, HttpStatus.OK);
    }

    @RequestMapping(value = "/for-magazine", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ScientificPaperFrontendDTOWithId>> getScientificPapersForMagazine(@RequestParam("magazineId") Long magazineId) {
    	UserData loggedUser = null;
        try {
            loggedUser = userService.getLoggedUser();
        } catch (Exception e) {
            new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    	
        List<ScientificPaperFrontendDTOWithId> scientificPaperFrontendDTOs =
                scientificPaperService.getScientificPapersForMagazine(magazineId, loggedUser.getId());
        return new ResponseEntity<List<ScientificPaperFrontendDTOWithId>>(scientificPaperFrontendDTOs, HttpStatus.OK);
    }

    @RequestMapping(value = "/pending", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ScientificPaperFrontendDTOWithMagazineName>> getScientificPapersForMagazine() {
        UserData loggedUser = null;
        try {
            loggedUser = userService.getLoggedUser();
        } catch (Exception e) {
            new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        List<ScientificPaperFrontendDTOWithMagazineName> scientificPaperFrontendDTOWithMagazineNames =
                scientificPaperService.getPendingScientificPapers(loggedUser);
        return new ResponseEntity<List<ScientificPaperFrontendDTOWithMagazineName>>(scientificPaperFrontendDTOWithMagazineNames,
                                                                                    HttpStatus.OK);
    }

    @RequestMapping(value = "/review", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity reviewScientificPaper(/*@RequestParam("mainProcessInstanceId") String mainProcessInstanceId,*/
            @RequestParam("taskId") String taskId,
            @RequestBody ReviewingScientificPaperDTO reviewingScientificPaperDTO) {
        UserData loggedUser = null;
        try {
            loggedUser = userService.getLoggedUser();
        } catch (Exception e) {
            new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        Map<String, Object> formFieldsMap = new HashMap<String, Object>();
        formFieldsMap.put("statusAfterReviewing", reviewingScientificPaperDTO.getStatusAfterReviewing().name());
        formFieldsMap.put("commentForAuthor", reviewingScientificPaperDTO.getCommentForAuthor());
        formFieldsMap.put("commentForEditor", reviewingScientificPaperDTO.getCommentForEditor());

        ReviewingResult reviewingResult = new ReviewingResult(loggedUser.getCamundaUserId(),
                reviewingScientificPaperDTO.getStatusAfterReviewing(), reviewingScientificPaperDTO.getCommentForAuthor(),
                reviewingScientificPaperDTO.getCommentForEditor());
        scientificPaperService.addReviewingResult(/*mainProcessInstanceId, */taskId, reviewingResult);

        try {
            scientificPaperService.submitUserTask(loggedUser.getCamundaUserId(), taskId, formFieldsMap);
        } catch (NotFoundException | TaskNotAssignedToYouException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/for-first-decision", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ScientificPaperFrontendDtoWithReviewings>> getFirstDecision() {
        UserData loggedUser = null;
        try {
            loggedUser = userService.getLoggedUser();
        } catch (Exception e) {
            new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        List<ScientificPaperFrontendDtoWithReviewings> scientificPaperFrontendDtoWithReviewings =
                scientificPaperService.getFirstDecision(loggedUser.getCamundaUserId());
        return new ResponseEntity<List<ScientificPaperFrontendDtoWithReviewings>>(scientificPaperFrontendDtoWithReviewings,
                HttpStatus.OK);
    }

    @RequestMapping(value = "/for-second-decision", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ScientificPaperFrontendDtoWithReviewingsAndAnswers>> getSecondDecision() {
        UserData loggedUser = null;
        try {
            loggedUser = userService.getLoggedUser();
        } catch (Exception e) {
            new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        List<ScientificPaperFrontendDtoWithReviewingsAndAnswers> scientificPaperFrontendDtoWithComments =
                scientificPaperService.getSecondDecision(loggedUser.getCamundaUserId());
        return new ResponseEntity<List<ScientificPaperFrontendDtoWithReviewingsAndAnswers>>(scientificPaperFrontendDtoWithComments,
                HttpStatus.OK);
    }

    @RequestMapping(value = "/for-final-decision", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ScientificPaperFrontendDtoWithComment>> getFinalDecision() {
        UserData loggedUser = null;
        try {
            loggedUser = userService.getLoggedUser();
        } catch (Exception e) {
            new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        List<ScientificPaperFrontendDtoWithComment> scientificPaperFrontendDtoWithReviewings =
                scientificPaperService.getFinalDecision(loggedUser.getCamundaUserId());
        return new ResponseEntity<List<ScientificPaperFrontendDtoWithComment>>(scientificPaperFrontendDtoWithReviewings,
                HttpStatus.OK);
    }

}

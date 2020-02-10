package com.jovo.ScienceCenter.controller;

import com.jovo.ScienceCenter.dto.*;
import com.jovo.ScienceCenter.exception.NotFoundException;
import com.jovo.ScienceCenter.exception.TaskNotAssignedToYouException;
import com.jovo.ScienceCenter.model.Magazine;
import com.jovo.ScienceCenter.model.UserData;
import com.jovo.ScienceCenter.service.MagazineService;
import com.jovo.ScienceCenter.service.UserService;
import javafx.concurrent.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(value = "/magazines")
public class MagazineController {

    @Autowired
    private MagazineService magazineService;

    @Autowired
    private UserService userService;

    @RequestMapping(value ="/user-task-submit", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity registerationSubmit(@RequestParam("taskId") String taskId,
                                              @RequestBody List<IdValueDTO> idValueDTOList) {
        UserData loggedUser = null;
        try {
            loggedUser = userService.getLoggedUser();
        } catch (Exception e) {
            new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

//        Map<String, Object> formFieldsMap = idValueDTOList.stream()
//                                             .collect(Collectors.toMap(IdValueDTO::getId, IdValueDTO::getValue));
        Map<String, Object> formFieldsMap = new HashMap<String, Object>();
        idValueDTOList.stream()
                .forEach(field -> formFieldsMap.put(field.getId(), field.getValue()));
        try {
            magazineService.submitUserTask(loggedUser.getCamundaUserId(), taskId, formFieldsMap);
        } catch (NotFoundException | TaskNotAssignedToYouException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/activated-with-paid-status", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MagazineDTO>> getActivatedMagazinesWithPaidStatus() {
        UserData loggedUser = null;
        try {
            loggedUser = userService.getLoggedUser();
        } catch (Exception e) {
            new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        List<MagazineDTO> magazineDTOs = magazineService.getActivatedMagazinesWithPaidStatus(loggedUser.getId());
        return new ResponseEntity<List<MagazineDTO>>(magazineDTOs, HttpStatus.OK);
    }

    @RequestMapping(value = "/activated-by-editor", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MagazineWithoutPaidStatusDTO>> getAllActivatedMagazines() {
        UserData loggedUser = null;
        try {
            loggedUser = userService.getLoggedUser();
        } catch (Exception e) {
            new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        List<MagazineWithoutPaidStatusDTO> magazineDTOs = magazineService.getAllActivatedMagazinesByEditor(loggedUser);
        return new ResponseEntity<List<MagazineWithoutPaidStatusDTO>>(magazineDTOs, HttpStatus.OK);
    }

    @RequestMapping(value = {"/create-magazine-form-fields", "/create-magazine-form-fields/{processInstanceId}"},
                                method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FormFieldsDto> getCreateMagazineFormFields(@PathVariable(name = "processInstanceId", required = false)
                                                                                 String processInstanceId) {
        UserData loggedUser = null;
        try {
            loggedUser = userService.getLoggedUser();
        } catch (Exception e) {
            new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        FormFieldsDto formFieldsDto = null;
        try {
            formFieldsDto = magazineService.getCreateMagazineFormFields(loggedUser.getCamundaUserId(), processInstanceId);
        } catch (NotFoundException | TaskNotAssignedToYouException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<FormFieldsDto>(formFieldsDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/add-editors-and-reviewers", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addEditorsAndReviewer(@RequestParam("processInstanceId") String processInstanceId,
                                                @RequestBody AddEditorsAndReviewerDTO addEditorsAndReviewerDTO) {
        UserData loggedUser = null;
        try {
            loggedUser = userService.getLoggedUser();
        } catch (Exception e) {
            new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        Map<String, Object> formFieldsMap = new HashMap<String, Object>();
        formFieldsMap.put("editors", addEditorsAndReviewerDTO.getEditors());
        formFieldsMap.put("reviewers", addEditorsAndReviewerDTO.getReviewers());
        try {
            magazineService.submitFirstUserTask(loggedUser.getCamundaUserId(), processInstanceId, formFieldsMap);
        } catch (NotFoundException | TaskNotAssignedToYouException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/add-payment-types", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addEditorsAndReviewer(@RequestParam("processInstanceId") String processInstanceId,
                                                @RequestBody AddPaymentTypesDTO addPaymentTypesDTO) {
        UserData loggedUser = null;
        try {
            loggedUser = userService.getLoggedUser();
        } catch (Exception e) {
            new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        Map<String, Object> formFieldsMap = new HashMap<String, Object>();
        formFieldsMap.put("paymentTypes", addPaymentTypesDTO.getPaymentTypes());
        try {
            magazineService.submitFirstUserTask(loggedUser.getCamundaUserId(), processInstanceId, formFieldsMap);
        } catch (NotFoundException | TaskNotAssignedToYouException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/all-pending", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PendingMagazineDTO>> getPendingMagazines() {
        List<PendingMagazineDTO> pendingMagazineDTOs = magazineService.getPendingMagazinesForChecking();
        return new ResponseEntity<List<PendingMagazineDTO>>(pendingMagazineDTOs, HttpStatus.OK);
    }

    @RequestMapping(value = "/check-data", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity checkData(@RequestParam("taskId") String taskId,
                                                @RequestBody Boolean validData) {
        UserData loggedUser = null;
        try {
            loggedUser = userService.getLoggedUser();
        } catch (Exception e) {
            new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        Map<String, Object> formFieldsMap = new HashMap<String, Object>();
        formFieldsMap.put("validData", validData);
        try {
            magazineService.submitUserTask(loggedUser.getCamundaUserId(), taskId, formFieldsMap);
        } catch (NotFoundException | TaskNotAssignedToYouException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/magazines-with-invalid-data", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FixMagazineDTO>> getMagazinesWithInvalidData() {
        UserData loggedUser = null;
        try {
            loggedUser = userService.getLoggedUser();
        } catch (Exception e) {
            new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        List<FixMagazineDTO> fixMagazineDTOs = magazineService.getMagazinesWithInvalidData(loggedUser.getCamundaUserId());

        return new ResponseEntity<List<FixMagazineDTO>>(fixMagazineDTOs, HttpStatus.OK);
    }
}

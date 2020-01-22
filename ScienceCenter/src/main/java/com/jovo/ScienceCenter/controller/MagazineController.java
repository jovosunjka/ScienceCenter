package com.jovo.ScienceCenter.controller;

import com.jovo.ScienceCenter.dto.*;
import com.jovo.ScienceCenter.model.Magazine;
import com.jovo.ScienceCenter.service.MagazineService;
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


    @RequestMapping(value ="/user-task-submit", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity registerationSubmit(@RequestParam("taskId") String taskId,
                                              @RequestBody List<IdValueDTO> idValueDTOList) {

//        Map<String, Object> formFieldsMap = idValueDTOList.stream()
//                                             .collect(Collectors.toMap(IdValueDTO::getId, IdValueDTO::getValue));
        Map<String, Object> formFieldsMap = new HashMap<String, Object>();
        idValueDTOList.stream()
                .forEach(field -> formFieldsMap.put(field.getId(), field.getValue()));
        try {
            magazineService.submitUserTask(taskId, formFieldsMap);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/all-activated", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MagazineDTO>> getMagazines() {
        List<Magazine> magazines = magazineService.getAllActivatedMagazines();
        List<MagazineDTO> magazineDTOs = magazines.stream()
                                        .map(m -> new MagazineDTO(m))
                                        .collect(Collectors.toList());
        return new ResponseEntity<List<MagazineDTO>>(magazineDTOs, HttpStatus.OK);
    }

    @RequestMapping(value = {"/create-magazine-form-fields", "/create-magazine-form-fields/{processInstanceId}"},
                                method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FormFieldsDto> getCreateMagazineFormFields(@PathVariable(name = "processInstanceId", required = false)
                                                                                 String processInstanceId) {
        FormFieldsDto formFieldsDto = null;
        try {
            formFieldsDto = magazineService.getCreateMagazineFormFields(processInstanceId);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<FormFieldsDto>(formFieldsDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/add-editors-and-reviewers", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addEditorsAndReviewer(@RequestParam("processInstanceId") String processInstanceId,
                                                @RequestBody AddEditorsAndReviewerDTO addEditorsAndReviewerDTO) {
        Map<String, Object> formFieldsMap = new HashMap<String, Object>();
        formFieldsMap.put("editors", addEditorsAndReviewerDTO.getEditors());
        formFieldsMap.put("reviewers", addEditorsAndReviewerDTO.getReviewers());
        try {
            magazineService.submitFirstUserTask(processInstanceId, formFieldsMap);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/add-payment-types", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addEditorsAndReviewer(@RequestParam("processInstanceId") String processInstanceId,
                                                @RequestBody AddPaymentTypesDTO addPaymentTypesDTO) {
        Map<String, Object> formFieldsMap = new HashMap<String, Object>();
        formFieldsMap.put("paymentTypes", addPaymentTypesDTO.getPaymentTypes());
        try {
            magazineService.submitFirstUserTask(processInstanceId, formFieldsMap);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
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
        Map<String, Object> formFieldsMap = new HashMap<String, Object>();
        formFieldsMap.put("validData", validData);
        try {
            magazineService.submitUserTask(taskId, formFieldsMap);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/magazines-with-invalid-data", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FixMagazineDTO>> getMagazinesWithInvalidData() {
        List<FixMagazineDTO> fixMagazineDTOs = null;
        try {
            fixMagazineDTOs = magazineService.getMagazinesWithInvalidData();
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<List<FixMagazineDTO>>(fixMagazineDTOs, HttpStatus.OK);
    }
}

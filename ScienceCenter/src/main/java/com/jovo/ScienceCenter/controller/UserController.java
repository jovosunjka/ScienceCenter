package com.jovo.ScienceCenter.controller;

import com.jovo.ScienceCenter.dto.*;
import com.jovo.ScienceCenter.exception.NotFoundException;
import com.jovo.ScienceCenter.exception.TaskNotAssignedToYouException;
import com.jovo.ScienceCenter.model.Magazine;
import com.jovo.ScienceCenter.model.UserData;
import com.jovo.ScienceCenter.security.TokenUtils;
import com.jovo.ScienceCenter.service.UserService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
    AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;
	
	@Autowired
    TokenUtils tokenUtils;


    @RequestMapping(value="/login", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity login(@RequestBody LoginUserDTO userDTO) {
    	try {
        	// Perform the authentication
        	UsernamePasswordAuthenticationToken userInfo = new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword());
            Authentication authentication = authenticationManager.authenticate(userInfo);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            userService.setAuthenticatedUserIdInCamunda(userDTO.getUsername());

            // Reload user details so we can generate token
            //authentication.getDetails()
            UserDetails details = userDetailsService.loadUserByUsername(userDTO.getUsername());
            String generatedToken = tokenUtils.generateToken(details);
            Map<String,String> result = new HashMap<>();
            result.put("token",generatedToken);
            return new ResponseEntity<TokenDTO>(new TokenDTO(generatedToken), HttpStatus.OK);
        }
    	catch (Exception e) {
            return new ResponseEntity<String>("Invalid login", HttpStatus.BAD_REQUEST);
        }
    	
    }

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
            userService.submitUserTask(loggedUser.getCamundaUserId(), taskId, formFieldsMap);
        } catch (NotFoundException | TaskNotAssignedToYouException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value ="/registration-form-fields", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FormFieldsDto> getRegisterationFormFields() {
        FormFieldsDto formFieldsDto = userService.getRegistrationFormFields();
        return new ResponseEntity<FormFieldsDto>(formFieldsDto, HttpStatus.OK);
    }

    @RequestMapping(value="/confirm-registration", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity confirmRegistration(@RequestParam("processInstanceId") String processInstanceId,
                                              @RequestBody ConfirmRegistrationDTO confirmRegistrationDTO) {
        UserData loggedUser = null;
        try {
            loggedUser = userService.getLoggedUser();
        } catch (Exception e) {
            new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        Map<String, Object> formFieldsMap = Collections.singletonMap("token", confirmRegistrationDTO.getToken());
        try {
            userService.submitFirstUserTask(loggedUser.getCamundaUserId(), processInstanceId, formFieldsMap);
        } catch (NotFoundException | TaskNotAssignedToYouException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value ="/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> userDTOs = userService.getAllUsers();
        return new ResponseEntity<List<UserDTO>>(userDTOs, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("id") Long id) {
        try{
            userService.deleteUser(id);
        }
        catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/activation-or-deactivation/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity activationOrDeactivation(@PathVariable("id") Long id, @RequestBody boolean activate) {
        try{
            userService.activateOrDeactivate(id, activate);
            return new ResponseEntity(HttpStatus.OK);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/confirm-reviewer-status", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity confirmReviewerStatus(@RequestParam("taskId") String taskId, @RequestBody ConfirmReviewerStatusDTO confirmReviewerStatusDTO) {
        UserData loggedUser = null;
        try {
            loggedUser = userService.getLoggedUser();
        } catch (Exception e) {
            new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        Map<String, Object> formFieldsMap = new HashMap<String, Object>();
        formFieldsMap.put("confirmId", confirmReviewerStatusDTO.getConfirmId());
        formFieldsMap.put("confirmed", confirmReviewerStatusDTO.isConfirmed());
        try {
            userService.submitUserTask(loggedUser.getCamundaUserId(), taskId, formFieldsMap);
        } catch (NotFoundException | TaskNotAssignedToYouException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }


    @RequestMapping(value ="/requests-for-reviewer", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RequestForReviewerDTO>> getAllRequestsForReviewer() {
        List<RequestForReviewerDTO> userDTOs = userService.getAllRequestsForReviewer();
        return new ResponseEntity<List<RequestForReviewerDTO>>(userDTOs, HttpStatus.OK);
    }

    @RequestMapping(value = "/reviewers-for-magazine-in-current-process", method = RequestMethod.GET,
                                                                            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EditorOrReviewerDTO>> getReviewersForMagazineInCurrentProcess(@RequestParam("processInstanceId")
                                                                                                 String processInstanceId) {
        List<EditorOrReviewerDTO> reviewerDTOs = userService.getReviewersForMagazineInCurrentProcess(processInstanceId);
        return new ResponseEntity<List<EditorOrReviewerDTO>>(reviewerDTOs, HttpStatus.OK);
    }

    @RequestMapping(value = "/editors-for-magazine-in-current-process", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EditorOrReviewerDTO>> getEditorsForMagazineInCurrentProcess(@RequestParam("processInstanceId")
                                                                                                     String processInstanceId) {
        List<EditorOrReviewerDTO> editorrDTOs = userService.getEditorsForMagazineInCurrentProcess(processInstanceId);
        return new ResponseEntity<List<EditorOrReviewerDTO>>(editorrDTOs, HttpStatus.OK);
    }

}

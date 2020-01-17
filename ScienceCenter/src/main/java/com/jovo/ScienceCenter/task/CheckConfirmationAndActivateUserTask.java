package com.jovo.ScienceCenter.task;


import com.jovo.ScienceCenter.exception.CheckConfirmationAndActivateUserFailedException;
import com.jovo.ScienceCenter.exception.RegistrationFailedException;
import com.jovo.ScienceCenter.service.UserService;
import com.jovo.ScienceCenter.websockets.dto.WebSocketMessageDTO;
import com.jovo.ScienceCenter.websockets.messaging.Producer;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CheckConfirmationAndActivateUserTask implements JavaDelegate {

    @Autowired
    private UserService userService;

    @Autowired
    private Producer producer;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Object message;

        try {
            System.out.println("CheckConfirmationAndActivateUserTask_START");

            String confirmationToken = (String) delegateExecution.getVariable("token");
            boolean reviewer = (boolean) delegateExecution.getVariable("reviewer");
            userService.checkConfirmationAndActivateUser(confirmationToken, reviewer);

            System.out.println("CheckConfirmationAndActivateUserTask_END");
        } catch (Exception e) {
            System.out.println("CheckConfirmationAndActivateUser Failed");
            //e.printStackTrace();
            message = new WebSocketMessageDTO(true, "confirm-registration", e.getMessage());
            producer.sendMessage(message);
            throw new CheckConfirmationAndActivateUserFailedException("CheckConfirmationAndActivateUser Failed");
        }

        message = new WebSocketMessageDTO(false, "confirm-registration",
                "Confirmation successful!");
        producer.sendMessage(message);
    }

}
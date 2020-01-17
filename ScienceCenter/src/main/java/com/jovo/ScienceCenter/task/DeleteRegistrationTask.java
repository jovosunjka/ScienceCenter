package com.jovo.ScienceCenter.task;


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
public class DeleteRegistrationTask implements JavaDelegate {

    @Autowired
    private UserService userService;

    @Autowired
    private Producer producer;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Object message;

        try {
            System.out.println("DeleteRegistrationTask_START");

            String username = (String) delegateExecution.getVariable("username");

            userService.deleteRegistration(username);

            System.out.println("DeleteRegistrationTask_END");
        } catch (Exception e) {
            System.out.println("DeleteRegistrationTask Failed");
            //e.printStackTrace();
            message = new WebSocketMessageDTO(true, "delete-registration", e.getMessage());
            producer.sendMessage(message);
        }

        message = new WebSocketMessageDTO(false, "delete-registration",
                "Registration successfully deleted.");
        producer.sendMessage(message);
    }

}
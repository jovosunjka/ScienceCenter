package com.jovo.ScienceCenter.task;


import com.jovo.ScienceCenter.exception.RegistrationFailedException;
import com.jovo.ScienceCenter.model.UserData;
import com.jovo.ScienceCenter.service.UserService;
import com.jovo.ScienceCenter.websockets.dto.WebSocketMessageDTO;
import com.jovo.ScienceCenter.websockets.messaging.Producer;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegistrationTask implements JavaDelegate {

    @Autowired
    private UserService userService;

    @Autowired
    private Producer producer;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Object message;

        try {
            System.out.println("RegistrationTask_START");

            String username = (String) delegateExecution.getVariable("username");
            String password = (String) delegateExecution.getVariable("password");
            String repeatedPassword = (String) delegateExecution.getVariable("repeatedPassword");
            String firstName = (String) delegateExecution.getVariable("firstName");
            String lastName = (String) delegateExecution.getVariable("lastName");
            String city = (String) delegateExecution.getVariable("city");
            String country = (String) delegateExecution.getVariable("country");
            String email = (String) delegateExecution.getVariable("email");

            String scientificAreas = (String) delegateExecution.getVariable("scientificAreas");
            List<Long> scientificAreaIds;
            if (scientificAreas.equals("")) {
                scientificAreaIds = new ArrayList<Long>();
            }
            else {
                scientificAreaIds = Arrays.stream(scientificAreas.split(","))
                        .map(idStr -> Long.parseLong(idStr))
                        .collect(Collectors.toList());
            }

            userService.register(delegateExecution.getId(), username, password, repeatedPassword, firstName, lastName, email, city,
                                    country, scientificAreaIds);

            System.out.println("RegistrationTask_END");
        } catch (Exception e) {
            System.out.println("Registration Failed");
            //e.printStackTrace();
            message = new WebSocketMessageDTO(true, "register-page", e.getMessage());
            producer.sendMessage(message);
            throw new RegistrationFailedException("Registration Failed");
        }

        message = new WebSocketMessageDTO(false, "register-page",
                "Registration successfully saved, you will receive a confirmation email.");
        producer.sendMessage(message);
    }

}
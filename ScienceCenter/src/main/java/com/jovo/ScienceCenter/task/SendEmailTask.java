package com.jovo.ScienceCenter.task;


import com.jovo.ScienceCenter.service.UserService;
import com.jovo.ScienceCenter.util.Email;
import com.jovo.ScienceCenter.websockets.dto.WebSocketMessageDTO;
import com.jovo.ScienceCenter.websockets.messaging.Producer;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.Expression;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

@Service
public class SendEmailTask implements JavaDelegate {

    @Value("${registration.confirmation.url}")
    private String confirmationUrl;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Producer producer;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private UserService userService;

    private Expression emailForSendingExpression; // u nekim slucajevima ovo ce biti instancirano u realtime-u
                                                    // (parallel multi instance)


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Object message;

        try {
            System.out.println("SendEmailTask_START");

            Email emailData = (Email) runtimeService.getVariable(delegateExecution.getProcessInstanceId(), "currentEmail");

            // https://docs.camunda.org/manual/latest/user-guide/process-engine/delegation-code/#field-injection
            String emailOfUser;
            if (emailData.getAddress() == null) {
                emailOfUser = (String) emailForSendingExpression.getValue(delegateExecution);
            }
            else {
                emailOfUser = emailData.getAddress();
            }

            // TODO nakon kontrolne tacke, odkomentarisi ovo
            // sendEmail(emailOfUser, emailData.getSubject(), emailData.getMessage());
            printEmail(emailOfUser, emailData.getSubject(), emailData.getMessage());

            System.out.println("SendEmailTask_END");
        } catch (Exception e) {
            message = new WebSocketMessageDTO(true, "mail sent", e.getMessage());
            producer.sendMessage(message);
            return;
           // e.printStackTrace();
        }

        message = new WebSocketMessageDTO(false, "mail sent",
                "Mail sent! Refresh received emails and confirm registration.");
        producer.sendMessage(message);
    }

    private void printEmail(String emailOfUser, String subject, String message) {
        System.out.println("****************MAIL****************");
        System.out.println("====================================");
        System.out.println("EmailOfUser: " + emailOfUser);
        System.out.println("Subject: " + subject);
        System.out.println("HtmlMessage:");
        System.out.println(message);
    }

    private void sendEmail(String emailOfUser, String subject, String htmlMessage) throws MessagingException, UnsupportedEncodingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        mimeMessage.setContent(htmlMessage, "text/html");


        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
        helper.setTo(emailOfUser);
        helper.setSubject(subject);
        //helper.setSubject("Science Centre Activation Account Support");
        // helper.setFrom(env.getProperty(emailOfSender, passwordOfSender));

        javaMailSender.send(mimeMessage);

        System.out.println("Email poslat!");
    }
}
package com.jovo.ScienceCenter.task;


import com.jovo.ScienceCenter.websockets.dto.WebSocketMessageDTO;
import com.jovo.ScienceCenter.websockets.messaging.Producer;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
public class SendEmailForConfirmationTask implements JavaDelegate {

    @Value("${registration.confirmation.url}")
    private String confirmationUrl;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Producer producer;


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Object message;

        try {
            System.out.println("SendEmailTask_START");


            String username = (String) delegateExecution.getVariable("username");
            // String emailOfUser = "sunjkajovo@gmail.com";
            // String emailOfUser = "marko_srb@hotmail.rs";
            String emailOfUser = (String) delegateExecution.getVariable("email");
            String token = (String) delegateExecution.getVariable("token");
            String linkUrl = confirmationUrl.concat("?token=").concat(token);
            System.out.println("LINK:  ".concat(linkUrl));

            // TODO nakon kontrolne tacke, odkomentarisi ovo
            // sendEmail(username, emailOfUser, linkUrl);

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

    private void sendEmail(String username, String emailOfUser, String linkUrl) throws MessagingException, UnsupportedEncodingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        // String htmlMsg = "Dear " + user.getFirstName() + ", <br/><br/>";
        String htmlMsg = "";
        htmlMsg += "<h3> Welcome to Science Centre </h3> <br/><br/>";
        htmlMsg += "Below are your login and activation details for your new account: <br/><br/>";
        htmlMsg += "&nbsp; Email: &nbsp; <b> "+ emailOfUser +" </b> <br/>";
        htmlMsg += "&nbsp; Username: &nbsp; <b> " + username + " </b> <br/>";
        // htmlMsg += "&nbsp; Password: &nbsp; <b> " + user.getPassword() + " </b> <br/><br/>";
        htmlMsg += "To activate your account, please click on the following link (if the link is disabled, Copy and Paste the URL into your Browser): <br/>";
        // htmlMsg += "<a href='http://isaapp.herokuapp.com/myapp/#/users/activate?id_for_activation=" + idForActivation + "'> http://isaapp.herokuapp.com/myapp/#/users/activate?id_for_activation=" + idForActivation + " </a> <br/><br/>";
        htmlMsg += "<a href='" + linkUrl + "'>" + linkUrl + " </a> <br/><br/>";
        htmlMsg += "Kind Regards, <br/>";
        htmlMsg += "Science Centre";
        mimeMessage.setContent(htmlMsg, "text/html");


        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
        helper.setTo(emailOfUser);
        helper.setSubject("Science Centre Activation Account Support");
        // helper.setFrom(env.getProperty(emailOfSender, passwordOfSender));

        javaMailSender.send(mimeMessage);

        System.out.println("Email poslat!");
    }
}
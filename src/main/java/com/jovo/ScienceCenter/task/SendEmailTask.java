package com.jovo.ScienceCenter.task;


import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
public class SendEmailTask implements JavaDelegate {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("SendEmailTask_START");

        // String emailOfUser = "sunjkajovo@gmail.com";
        // String emailOfUser = "marko_srb@hotmail.rs";
        String emailOfUser = (String) delegateExecution.getVariable("email");
        String linkUrl = "http://localhost:8080/camunda";
        try {
            sendEmail(emailOfUser, linkUrl);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        System.out.println("SendEmailTask_END");
    }

    private void sendEmail(String emailOfUser, String linkUrl) throws MessagingException, UnsupportedEncodingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        // String htmlMsg = "Dear " + user.getFirstName() + ", <br/><br/>";
        String htmlMsg = "";
        htmlMsg += "<h3> Welcome to Science Centre </h3> <br/><br/>";
        htmlMsg += "Below are your login and activation details for your new account: <br/><br/>";
        htmlMsg += "&nbsp; Email: &nbsp; <b> "+ emailOfUser +" </b> <br/>";
        // htmlMsg += "&nbsp; Username: &nbsp; <b> " + user.getUsername() + " </b> <br/>";
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
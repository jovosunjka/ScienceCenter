package com.jovo.ScienceCenter.task.external;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.logging.Logger;

import org.apache.logging.log4j.util.Strings;
import org.camunda.bpm.client.ExternalTaskClient;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class SendEmailExternalTask {

    private static String BASE_URL = "http://localhost:8080/engine-rest";
    private static JavaMailSenderImpl mailSenderImpl = null;

    public static void main(String[] args) {
        ExternalTaskClient client = ExternalTaskClient.create()
                .baseUrl(BASE_URL)
                .asyncResponseTimeout(10000) // long polling timeout
                .build();

        // subscribe to an external task topic as specified in the process
        client.subscribe("send-email")
                .lockDuration(1000) // the default lock duration is 20 seconds, but you can override this
                .handler((externalTask, externalTaskService) -> {
                    // Put your business logic here

                    System.out.println("************ SendEmailExternalTask_START ************");

                    // String emailOfUser = "sunjkajovo@gmail.com";
                    // String emailOfUser = "marko_srb@hotmail.rs";
                    String emailOfUser = (String) externalTask.getVariable("email");
                    String linkUrl = "http://localhost:8080/camunda";
                    try {
                        sendEmail(emailOfUser, linkUrl);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    // Complete the task
                    externalTaskService.complete(externalTask);

                    System.out.println("************ SendEmailExternalTask_END ************");
                })
                .open();
    }

    private static JavaMailSender getJavaMailSender() {
        if (mailSenderImpl == null) {

        }
        mailSenderImpl = new JavaMailSenderImpl();
        mailSenderImpl.setHost("smtp.gmail.com");
        mailSenderImpl.setPort(587);

        String emailOfSender = "mrs.isa.jvm@gmail.com";
        String passwordOfSender = "isa**mrs";

        mailSenderImpl.setUsername(emailOfSender);
        mailSenderImpl.setPassword(passwordOfSender);

        Properties props = mailSenderImpl.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSenderImpl;
    }

    private static void sendEmail(String emailOfUser, String linkUrl) throws MessagingException, UnsupportedEncodingException {
        JavaMailSender javaMailSender = getJavaMailSender();
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
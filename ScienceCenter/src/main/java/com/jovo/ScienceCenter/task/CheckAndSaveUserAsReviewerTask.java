package com.jovo.ScienceCenter.task;


import com.jovo.ScienceCenter.exception.CheckConfirmationAndActivateUserFailedException;
import com.jovo.ScienceCenter.service.UserService;
import com.jovo.ScienceCenter.websockets.dto.WebSocketMessageDTO;
import com.jovo.ScienceCenter.websockets.messaging.Producer;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckAndSaveUserAsReviewerTask implements JavaDelegate {

    @Autowired
    private UserService userService;

    @Autowired
    private Producer producer;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Object message;

        try {
            System.out.println("CheckAndSaveUserAsReviewerTask_START");

            long confirmId = (long) delegateExecution.getVariable("confirmId");
            boolean confirmed = (boolean) delegateExecution.getVariable("confirmed");
            userService.confirmReviewerStatus(confirmId, confirmed);

            System.out.println("CheckAndSaveUserAsReviewerTask_END");
        } catch (Exception e) {
            System.out.println("CheckAndSaveUserAsReviewerTask Failed");
            //e.printStackTrace();
            message = new WebSocketMessageDTO(true, "confirm-reviewer-status", e.getMessage());
            producer.sendMessage(message);
        }

        message = new WebSocketMessageDTO(false, "confirm-reviewer-status",
                "Confirmation reviewer status successful!");
        producer.sendMessage(message);
    }

}
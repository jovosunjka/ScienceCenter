package com.jovo.ScienceCenter.task;



import com.jovo.ScienceCenter.exception.AuthorHasNotActivatedMembershipFeeException;
import com.jovo.ScienceCenter.model.Magazine;
import com.jovo.ScienceCenter.model.UserData;
import com.jovo.ScienceCenter.service.MembershipFeeService;
import com.jovo.ScienceCenter.service.UserService;
import com.jovo.ScienceCenter.websockets.dto.WebSocketMessageDTO;
import com.jovo.ScienceCenter.websockets.messaging.Producer;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CheckAuthorHasActivatedMembershipFeeTask implements JavaDelegate {

    @Autowired
    private MembershipFeeService membershipFeeService;

    @Autowired
    private UserService userService;

    @Autowired
    private Producer producer;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Object message;

        try {
            System.out.println("CheckAuthorHasActivatedMembershipFeeTask_START");

            UserData author = userService.getLoggedUser();
            Magazine selectedMagazine = (Magazine) delegateExecution.getVariable("selectedMagazine");
            membershipFeeService.getActivatedMembershipFeeByProductIdAndPayerId(selectedMagazine.getId(), true, author.getId());
            // ako nema aktivnu clanarinu, baca exception
            System.out.println("CheckAuthorHasActivatedMembershipFeeTask_END");
        } catch (Exception e) {
            System.out.println("CheckAuthorHasActivatedMembershipFeeTask Failed");
            //e.printStackTrace();
            message = new WebSocketMessageDTO(true, "check-author-has-activated-membership-fee", e.getMessage());
            producer.sendMessage(message);
            throw new AuthorHasNotActivatedMembershipFeeException("Author has not activated membership fee!");
        }

        message = new WebSocketMessageDTO(false, "check-author-has-activated-membership-fee",
                "Author has activated membership fee!");
        producer.sendMessage(message);
    }

}
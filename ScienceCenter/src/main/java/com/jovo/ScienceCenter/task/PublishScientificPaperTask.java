package com.jovo.ScienceCenter.task;


import com.jovo.ScienceCenter.service.MagazineService;
import com.jovo.ScienceCenter.service.ScientificPaperService;
import com.jovo.ScienceCenter.websockets.dto.WebSocketMessageDTO;
import com.jovo.ScienceCenter.websockets.messaging.Producer;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublishScientificPaperTask implements JavaDelegate {

    @Autowired
    private Producer producer;

    @Autowired
    private ScientificPaperService scientificPaperService;


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Object message;

        try {
            System.out.println("PublishScientificPaperTaskk_START");

            scientificPaperService.publishScientificPaper(delegateExecution.getProcessInstanceId());

            System.out.println("PublishScientificPaperTask_END");
        } catch (Exception e) {
            System.out.println("PublishScientificPaperTask Failed");
            //e.printStackTrace();
            message = new WebSocketMessageDTO(true, "publish-scientific-paper", e.getMessage());
            producer.sendMessage(message);
        }

        message = new WebSocketMessageDTO(false, "publish-scientific-paper",
                "The scientific paper is published!");
        producer.sendMessage(message);
    }

}
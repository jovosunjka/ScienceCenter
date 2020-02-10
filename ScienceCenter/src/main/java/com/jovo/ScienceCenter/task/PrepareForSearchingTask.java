package com.jovo.ScienceCenter.task;


import com.jovo.ScienceCenter.service.ScientificPaperService;
import com.jovo.ScienceCenter.websockets.dto.WebSocketMessageDTO;
import com.jovo.ScienceCenter.websockets.messaging.Producer;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrepareForSearchingTask implements JavaDelegate {

    @Autowired
    private Producer producer;

    @Autowired
    private ScientificPaperService scientificPaperService;


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Object message;

        try {
            System.out.println("PrepareForSearchingTask_START");

            scientificPaperService.prepareForSearching(delegateExecution.getProcessInstanceId());

            System.out.println("PrepareForSearchingTask_END");
        } catch (Exception e) {
            System.out.println("PrepareForSearchingTask Failed");
            //e.printStackTrace();
            message = new WebSocketMessageDTO(true, "prepare-for-searching", e.getMessage());
            producer.sendMessage(message);
        }

        message = new WebSocketMessageDTO(false, "prepare-for-searching",
                "Scientific paper is prepared for searching!");
        producer.sendMessage(message);
    }

}
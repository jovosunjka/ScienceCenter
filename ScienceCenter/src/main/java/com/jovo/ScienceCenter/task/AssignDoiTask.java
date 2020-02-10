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
public class AssignDoiTask implements JavaDelegate {

    @Autowired
    private Producer producer;

    @Autowired
    private ScientificPaperService scientificPaperService;


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Object message;

        try {
            System.out.println("AssignDoiTask_START");

            scientificPaperService.assignDoi(delegateExecution.getProcessInstanceId());

            System.out.println("AssignDoiTask_END");
        } catch (Exception e) {
            System.out.println("AssignDoiTask Failed");
            //e.printStackTrace();
            message = new WebSocketMessageDTO(true, "assign-doi", e.getMessage());
            producer.sendMessage(message);
        }

        message = new WebSocketMessageDTO(false, "assign-doi",
                "DOI is assigned to scientific paper!");
        producer.sendMessage(message);
    }

}
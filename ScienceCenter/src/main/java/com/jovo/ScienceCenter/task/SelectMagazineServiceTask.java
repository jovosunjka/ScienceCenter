package com.jovo.ScienceCenter.task;


import com.jovo.ScienceCenter.exception.SelectMagazineFailedException;
import com.jovo.ScienceCenter.service.MagazineService;
import com.jovo.ScienceCenter.service.ScientificPaperService;
import com.jovo.ScienceCenter.websockets.dto.WebSocketMessageDTO;
import com.jovo.ScienceCenter.websockets.messaging.Producer;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SelectMagazineServiceTask implements JavaDelegate {

    @Autowired
    private ScientificPaperService scientificPaperService;

    @Autowired
    private Producer producer;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Object message;

        try {
            System.out.println("SelectMagazineServiceTask_START");

            String processInstnaceId = delegateExecution.getProcessInstanceId();
            Long magazineId = (Long) delegateExecution.getVariable("magazineId");

            scientificPaperService.selectMagazine(processInstnaceId, magazineId);

            System.out.println("SelectMagazineServiceTask_END");
        } catch (Exception e) {
            System.out.println("SelectMagazineServiceTask Failed");
            //e.printStackTrace();
            message = new WebSocketMessageDTO(true, "select-magazine", e.getMessage());
            producer.sendMessage(message);
            throw new SelectMagazineFailedException("Select magazine failed");
        }

        message = new WebSocketMessageDTO(false, "select-magazine",
                "The magazine is selected!");
        producer.sendMessage(message);
    }

}
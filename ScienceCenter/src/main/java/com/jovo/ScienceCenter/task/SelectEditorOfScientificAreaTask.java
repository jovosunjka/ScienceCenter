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
public class SelectEditorOfScientificAreaTask implements JavaDelegate {

    @Autowired
    private ScientificPaperService scientificPaperService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Object message;

        try {
            System.out.println("SelectEditorOfScientificAreaTask_START");

            scientificPaperService.selectEditorOfScientificArea(delegateExecution.getProcessInstanceId());

            System.out.println("SelectEditorOfScientificAreaTask_END");
        } catch (Exception e) {
            System.out.println("SelectEditorOfScientificAreaTask Failed");
        }

    }

}
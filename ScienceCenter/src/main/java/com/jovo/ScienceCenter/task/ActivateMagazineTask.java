package com.jovo.ScienceCenter.task;


import com.jovo.ScienceCenter.exception.SaveNewMagazineFailedException;
import com.jovo.ScienceCenter.model.Currency;
import com.jovo.ScienceCenter.model.PayerType;
import com.jovo.ScienceCenter.service.MagazineService;
import com.jovo.ScienceCenter.websockets.dto.WebSocketMessageDTO;
import com.jovo.ScienceCenter.websockets.messaging.Producer;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivateMagazineTask implements JavaDelegate {

    @Autowired
    private MagazineService magazineService;

    @Autowired
    private Producer producer;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Object message;

        try {
            System.out.println("ActivateMagazineTask_START");

            String magzineName = (String) delegateExecution.getVariable("name");

            magazineService.activateMagazine(magzineName);

            System.out.println("ActivateMagazineTask_END");
        } catch (Exception e) {
            System.out.println("ActivateMagazineTask Failed");
            //e.printStackTrace();
            message = new WebSocketMessageDTO(true, "activate-magazine", e.getMessage());
            producer.sendMessage(message);
        }

        message = new WebSocketMessageDTO(false, "activate-magazine",
                "The new magazine is saved!");
        producer.sendMessage(message);
    }

}
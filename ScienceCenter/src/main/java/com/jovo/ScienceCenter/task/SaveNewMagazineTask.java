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
public class SaveNewMagazineTask implements JavaDelegate {

    @Autowired
    private MagazineService magazineService;

    @Autowired
    private Producer producer;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Object message;

        try {
            System.out.println("SaveNewMagazineTask_START");

            String name = (String) delegateExecution.getVariable("name");
            String issn = (String) delegateExecution.getVariable("issn");

            String scientificAreas = (String) delegateExecution.getVariable("scientificAreas");
            List<Long> scientificAreaIds;
            if (scientificAreas.equals("")) {
                scientificAreaIds = new ArrayList<Long>();
            }
            else {
                scientificAreaIds = Arrays.stream(scientificAreas.split(","))
                        .map(idStr -> Long.parseLong(idStr))
                        .collect(Collectors.toList());
            }

            String payerTypeStr = (String) delegateExecution.getVariable("payerType");
            PayerType payerType = PayerType.valueOf(payerTypeStr.toUpperCase());

            String currencyStr = (String) delegateExecution.getVariable("currency");
            Currency currency = Currency.valueOf(currencyStr.toUpperCase());

            String mainEditorUsername = (String) delegateExecution.getVariable("processInitiator");

            String checkedMagazineName = (String) delegateExecution.getVariable("checkedMagazineName");

            magazineService.saveNewMagazine(name, issn, scientificAreaIds, payerType, currency, mainEditorUsername, checkedMagazineName);

            System.out.println("SaveNewMagazineTask_END");
        } catch (Exception e) {
            System.out.println("SaveNewMagazineTask Failed");
            //e.printStackTrace();
            message = new WebSocketMessageDTO(true, "save-new-magazine", e.getMessage());
            producer.sendMessage(message);
            throw new SaveNewMagazineFailedException("Save new magazine failed");
        }

        message = new WebSocketMessageDTO(false, "save-new-magazine",
                "The new magazine is saved!");
        producer.sendMessage(message);
    }

}
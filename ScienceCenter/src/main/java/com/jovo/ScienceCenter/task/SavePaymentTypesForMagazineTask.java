package com.jovo.ScienceCenter.task;


import com.jovo.ScienceCenter.dto.PaymentTypeDTO;
import com.jovo.ScienceCenter.dto.PropertyDTO;
import com.jovo.ScienceCenter.exception.SavePaymentTypesForMagazineFailedException;
import com.jovo.ScienceCenter.model.Currency;
import com.jovo.ScienceCenter.service.MagazineService;
import com.jovo.ScienceCenter.websockets.dto.WebSocketMessageDTO;
import com.jovo.ScienceCenter.websockets.messaging.Producer;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class SavePaymentTypesForMagazineTask implements JavaDelegate {

    @Autowired
    private MagazineService magazineService;

    @Autowired
    private Producer producer;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Object message;

        try {
            System.out.println("SavePaymentTypesForMagazineTask_START");

            String magazineName = (String) delegateExecution.getVariable("name");

            String currencyStr = (String) delegateExecution.getVariable("currency");
            Currency currency = Currency.valueOf(currencyStr.toUpperCase());

            String paymentTypesStr = (String) delegateExecution.getVariable("paymentTypes");
            List<PaymentTypeDTO> paymentTypes = getPaymentTypes(paymentTypesStr, currency);
            magazineService.savePaymentTypesForMagazine(magazineName, paymentTypes);

            delegateExecution.setVariable("checkedMagazineName", null);
            // resetujemo varijablu nakon sto je casopis izmenjen, i trazimo od admina da ponovo proveri casopis


            System.out.println("SavePaymentTypesForMagazineTask_END");
        } catch (Exception e) {
            System.out.println("SavePaymentTypesForMagazineTask Failed");
            //e.printStackTrace();
            message = new WebSocketMessageDTO(true, "save-payment-types", e.getMessage());
            producer.sendMessage(message);
            throw new SavePaymentTypesForMagazineFailedException("Save paymment types in magazine failed");
        }

        message = new WebSocketMessageDTO(false, "save-payment-types",
                "Payment types for magazine are saved!");
        producer.sendMessage(message);
    }

    private List<PaymentTypeDTO> getPaymentTypes(String paymentTypesStr, Currency currency) {
        String[] paymentTypeTokens = paymentTypesStr.split("\\|");
        return Arrays.stream(paymentTypeTokens)
                .map(token -> {
                    String[] tokens = token.split(";");
                    List<PropertyDTO> properties;
                    if (tokens.length == 2) {
                        tokens[1] = tokens[1].trim();
                        if (tokens[1].equals("")) {
                            properties = null;
                        } else {
                            String[] tokens2 = tokens[1].split(",");
                            properties = Arrays.stream(tokens2)
                                    .map(token2 -> {
                                        String[] tokens3 = token2.split(":");
                                        return new PropertyDTO(tokens3[0], tokens3[1]);
                                    })
                                    .collect(Collectors.toList());
                        }
                    }
                    else {
                        properties = null;
                    }

                    return new PaymentTypeDTO(tokens[0], properties, currency);
                })
                .filter(pt -> pt.getProperties() != null)
                .collect(Collectors.toList());
    }
}
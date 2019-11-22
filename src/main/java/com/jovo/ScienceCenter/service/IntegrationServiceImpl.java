package com.jovo.ScienceCenter.service;

import com.jovo.ScienceCenter.dto.ScienceCenterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class IntegrationServiceImpl implements IntegrationService {

    @Value("${science-center.name}")
    private String scienceCenterName;

    @Value("${science-center.base-url}")
    private String scienceCenterBaseUrl;

    @Value("${payment-concentrator.url}")
    private String paymentConcentratorUrl;

    @Autowired
    private RestTemplate restTemplate;


    @EventListener(ApplicationReadyEvent.class)
    @Override
    public void integrate() {
        ScienceCenterDTO scienceCenterDTO = new ScienceCenterDTO(scienceCenterName,scienceCenterBaseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ScienceCenterDTO> httpEntity = new HttpEntity<ScienceCenterDTO>(scienceCenterDTO, headers);

        ResponseEntity<Void> responseEntity = restTemplate.exchange(paymentConcentratorUrl, HttpMethod.POST, httpEntity, Void.class);

        if (responseEntity.getStatusCode() == HttpStatus.CREATED) {
            System.out.println(
                String.format("The science center %s has been successfully integrated with the Payment Concentrator.", scienceCenterName));
        } else {
            System.out.println("Integration error!");
        }
    }
}

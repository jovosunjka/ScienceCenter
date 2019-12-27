package com.jovo.ScienceCenter.service;

import com.jovo.ScienceCenter.dto.PaymentAccountDTO;
import com.jovo.ScienceCenter.dto.PaymentDTO;
import com.jovo.ScienceCenter.dto.RegistrationResultDTOs;
import com.jovo.ScienceCenter.dto.ScienceCenterDTO;
import com.jovo.ScienceCenter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class IntegrationServiceImpl implements IntegrationService {

    //@Value("${spring.application.name}")
    //private String scienceCenterName;

    @Value("${payment-concentrator.integration.url}")
    private String paymentConcentratorIntegrationUrl;


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    //@EventListener(ApplicationReadyEvent.class)
    @Override
    public void integrateAllMagazines() {
        //User user= userRepository.findById(2L).orElse(null);
        List<PaymentAccountDTO> paymentAccounts = new ArrayList<PaymentAccountDTO>();
        List<PaymentDTO> payments = new ArrayList<PaymentDTO>();
        payments.add(new PaymentDTO("paypal", "paypal_identifier"));
        paymentAccounts.add(new PaymentAccountDTO("magazine_1", payments));
        ScienceCenterDTO scienceCenterDTO = new ScienceCenterDTO(/*scienceCenterName,*/ paymentAccounts);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ScienceCenterDTO> httpEntity = new HttpEntity<ScienceCenterDTO>(scienceCenterDTO, headers);

        ResponseEntity<RegistrationResultDTOs> responseEntity = restTemplate.exchange(paymentConcentratorIntegrationUrl,
                                                                    HttpMethod.POST, httpEntity, RegistrationResultDTOs.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            RegistrationResultDTOs registrationResultDTOs = responseEntity.getBody();
            registrationResultDTOs.getRegistrationResults().stream()
                    .forEach(registrationResultDTO -> System.out.println("***  " + registrationResultDTO.getName()
                            + " (registered = " + registrationResultDTO.isResult() + ")"));
        } else {
            System.out.println("Integration error!");
        }
    }
}

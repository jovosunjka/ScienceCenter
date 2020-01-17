package com.jovo.ScienceCenter.service;

import com.jovo.ScienceCenter.dto.*;
import com.jovo.ScienceCenter.model.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class IntegrationServiceImpl implements IntegrationService {

    //@Value("${spring.application.name}")
    //private String scienceCenterName;

    @Value("${payment-microservice.urls.backend.registration.all}")
    private String pmAllRegistrationBackendUrl;


    @Autowired
    private RestTemplate restTemplate;


    //@EventListener(ApplicationReadyEvent.class)
    @Override
    public void integrateAllMagazines() {
        List<RegistrationPaymentConcentratorDTO> paymentAccounts = new ArrayList<RegistrationPaymentConcentratorDTO>();
        List<PaymentTypeDTO> payments = new ArrayList<PaymentTypeDTO>();
        payments.add(new PaymentTypeDTO("paypal", Arrays.asList(new PropertyDTO("paypal account",
                                                                                "paypal account_value")), Currency.RSD));
        paymentAccounts.add(new RegistrationPaymentConcentratorDTO("magazine_1", "magazine_1_username",
                "magazine_1_password", "magazine_1_password", payments));
        ScienceCenterForPaymentConsectratorDTO scienceCenterDTO = new ScienceCenterForPaymentConsectratorDTO(/*scienceCenterName,*/ paymentAccounts);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ScienceCenterForPaymentConsectratorDTO> httpEntity = new HttpEntity<ScienceCenterForPaymentConsectratorDTO>(scienceCenterDTO, headers);

        ResponseEntity<RegistrationResultDTOs> responseEntity = restTemplate.exchange(pmAllRegistrationBackendUrl,
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

package com.jovo.ScienceCenter.service;

import com.jovo.ScienceCenter.dto.PayDTO;
import com.jovo.ScienceCenter.dto.TransactionDTO;
import com.jovo.ScienceCenter.model.Magazine;
import com.jovo.ScienceCenter.model.MembershipFee;
import com.jovo.ScienceCenter.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PaymentServiceImpl implements PaymentService {


    @Value("${token.header}")
    private String tokenHeader;

    @Value("${backend.url}")
    private String backendUrl;

    @Value("${frontend.url}")
    private String frontendUrl;

    @Value("${payment-microservice.urls.frontend.choose-payment}")
    private String pmChoosePaymentFrontendUrl;

    @Value("${payment-microservice.urls.backend.pay}")
    private String pmPayBackendUrl;

    @Autowired
    private MagazineService magazineService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public String pay(Long magazineId) {
        User loggedUser = null;
        try {
            loggedUser = userService.getLoggedUser();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Magazine magazine = magazineService.getMagazine(magazineId);
        MembershipFee membershipFee = magazineService.makeMembershipFee(loggedUser.getId(), magazineId,
                magazine.getMembershipFee(), magazine.getCurrency());
        String redirectUrl = this.frontendUrl + "/magazine/" + magazineId;
        String callbackUrl = this.backendUrl + "/confirm-paying";
        PayDTO payDTO = new PayDTO(membershipFee.getId(), membershipFee.getPrice(), membershipFee.getCurrency(),
                redirectUrl, callbackUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set(tokenHeader, magazine.getMerchantId());
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PayDTO> httpEntity = new HttpEntity<PayDTO>(payDTO, headers);

        ResponseEntity<TransactionDTO> transactionDTOResponseEntity = restTemplate.exchange(pmPayBackendUrl, HttpMethod.POST, httpEntity, TransactionDTO.class);

        Long transactionId = transactionDTOResponseEntity.getBody().getId();
        return pmChoosePaymentFrontendUrl + "/" + transactionId;
    }

    @Override
    public void confirmPaying(Long merchantOrderId) {

    }
}

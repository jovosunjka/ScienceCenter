package com.jovo.ScienceCenter.service;

import com.jovo.ScienceCenter.dto.*;
import com.jovo.ScienceCenter.exception.PaymentConcentratorException;
import com.jovo.ScienceCenter.exception.RequestTimeoutException;
import com.jovo.ScienceCenter.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private String pmPayBackendPayUrl;

    @Value("${payment-microservice.urls.backend.transactions}")
    private String pmPayBackendTransactionsUrl;

    @Value("${payment-microservice.urls.backend.form-fields-for-payment-types}")
    private String pmFormFieldsForPaymentTypesBackendUrl;

    @Autowired
    private MagazineService magazineService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MembershipFeeService membershipFeeService;


    @Override
    public String pay(Long payerId, Long magazineId) throws RequestTimeoutException, PaymentConcentratorException {
        Magazine magazine = magazineService.getMagazine(magazineId);
        MembershipFee membershipFee = magazineService.makeMembershipFee(payerId, magazineId,
                magazine.getMembershipFee(), magazine.getCurrency());
        String redirectUrl = this.frontendUrl + "/transactions-page";
        String callbackUrl = this.backendUrl + "/payment/transaction-completed";
        PayDTO payDTO = new PayDTO(membershipFee.getId(), membershipFee.getPrice(), membershipFee.getCurrency(),
                redirectUrl, callbackUrl);

        HttpHeaders headers = new HttpHeaders();
        //headers.set(tokenHeader, magazine.getMerchantId());
        headers.set("magazineName", magazine.getName());
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PayDTO> httpEntity = new HttpEntity<PayDTO>(payDTO, headers);

        ResponseEntity<TransactionIdDTO> transactionDTOResponseEntity = restTemplate.exchange(pmPayBackendPayUrl, HttpMethod.POST, httpEntity, TransactionIdDTO.class);
        HttpStatus statusCode = transactionDTOResponseEntity.getStatusCode();

        if (statusCode == HttpStatus.CREATED) {
            Long transactionId = transactionDTOResponseEntity.getBody().getId();
            membershipFee.setKpTransactionId(transactionId);
            membershipFeeService.save(membershipFee);

            return pmChoosePaymentFrontendUrl + "/" + transactionId + "?token=" + magazine.getMerchantId();
        }
        else if (statusCode == HttpStatus.REQUEST_TIMEOUT) {
            throw new RequestTimeoutException();
        }
        else {
            System.out.println("PaymentConcentrator pay error!");
            throw new PaymentConcentratorException("PaymentConcentrator pay error!");
        }

    }

    @Override
    public List<TransactionDTO> getTransactions() throws RequestTimeoutException, PaymentConcentratorException {
        Long payerId = null;
        try {
            payerId = userService.getLoggedUser().getId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<MembershipFee> membershipFees = membershipFeeService.getMembershipFees(payerId);

        StringBuilder sb = new StringBuilder("");
        membershipFees.stream()
                .forEach(mf -> {
                    sb.append(",");
                    sb.append(mf.getId());
                });

        String transactionIds = sb.substring(1);
        ResponseEntity<TransactionDTOs> responseEntity = restTemplate.getForEntity(pmPayBackendTransactionsUrl
                                                            + "?transactionIds=" + transactionIds, TransactionDTOs.class);
        HttpStatus statusCode = responseEntity.getStatusCode();

        if (statusCode == HttpStatus.OK) {
            return responseEntity.getBody().getTransactions();
        }
        else if (statusCode == HttpStatus.REQUEST_TIMEOUT) {
            throw new RequestTimeoutException();
        }
        else {
            System.out.println("PaymentConcentrator pay error!");
            throw new PaymentConcentratorException("PaymentConcentrator getTransactions error!");
        }
    }

    @Override
    public void transactionCompleted(long merchantOrderId, String status) {
        MembershipFee membershipFee = membershipFeeService.getMembershipFee(merchantOrderId);
        boolean paid = status.equals("success");
        membershipFee.setPaid(paid);
        membershipFeeService.save(membershipFee);
    }

    @Override
    public List<FormFieldsForPaymentTypeDTO> getFormFieldsForPaymentTypes()
            throws RequestTimeoutException, PaymentConcentratorException {
        ResponseEntity<FormFieldsForPaymentTypesDTO> responseEntity = restTemplate.getForEntity(pmFormFieldsForPaymentTypesBackendUrl,FormFieldsForPaymentTypesDTO.class);

        HttpStatus statusCode = responseEntity.getStatusCode();

        if (statusCode == HttpStatus.OK) {
            return responseEntity.getBody().getTypes();
        }
        else if (statusCode == HttpStatus.REQUEST_TIMEOUT) {
            throw new RequestTimeoutException();
        }
        else {
            System.out.println("PaymentConcentrator getFormFieldsForPaymentTypes error!");
            throw new PaymentConcentratorException("PaymentConcentrator getFormFieldsForPaymentTypes error!");
        }

//        List<FormFieldsForPaymentTypeDTO> formFieldsForPaymentTypeDTOs = new ArrayList<FormFieldsForPaymentTypeDTO>();
//        formFieldsForPaymentTypeDTOs.add(new FormFieldsForPaymentTypeDTO("card-payment", "account number"));
//        formFieldsForPaymentTypeDTOs.add(new FormFieldsForPaymentTypeDTO("paypal", "paypal account"));
//        formFieldsForPaymentTypeDTOs.add(new FormFieldsForPaymentTypeDTO("bitcoin", "bitcoin account"));
//        return formFieldsForPaymentTypeDTOs;
    }
}

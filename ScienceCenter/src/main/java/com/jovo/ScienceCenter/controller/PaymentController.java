package com.jovo.ScienceCenter.controller;

import com.jovo.ScienceCenter.dto.FormFieldsForPaymentTypeDTO;
import com.jovo.ScienceCenter.dto.RedirectUrlDTO;
import com.jovo.ScienceCenter.dto.TransactionCompletedDTO;
import com.jovo.ScienceCenter.dto.TransactionDTO;
import com.jovo.ScienceCenter.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;


    @RequestMapping(value = "/pay/{magazineId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RedirectUrlDTO> pay(@PathVariable("magazineId") Long magazineId) {
        String redirectUrl = paymentService.pay(magazineId);
        return new ResponseEntity<RedirectUrlDTO>(new RedirectUrlDTO(redirectUrl), HttpStatus.OK);
    }

    @RequestMapping(value = "/transaction-completed", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity transactionCompleted(@RequestBody TransactionCompletedDTO transactionCompletedDTO) {
        paymentService.transactionCompleted(transactionCompletedDTO.getMerchantOrderId(), transactionCompletedDTO.getStatus());
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/transactions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TransactionDTO>> getTransactions() {
        List<TransactionDTO> transactions = paymentService.getTransactions();
        return new ResponseEntity<List<TransactionDTO>>(transactions, HttpStatus.OK);
    }

    @RequestMapping(value = "/form-fields-for-payment-types", method = RequestMethod.GET)
    public ResponseEntity<List<FormFieldsForPaymentTypeDTO>> getFormFieldsForPaymentTypes() {
        try {
            List<FormFieldsForPaymentTypeDTO> formFieldsForPaymentTypeDTOs = paymentService.getFormFieldsForPaymentTypes();
            return new ResponseEntity<List<FormFieldsForPaymentTypeDTO>>(formFieldsForPaymentTypeDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}

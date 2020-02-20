package com.jovo.ScienceCenter.controller;

import com.jovo.ScienceCenter.dto.FormFieldsForPaymentTypeDTO;
import com.jovo.ScienceCenter.dto.RedirectUrlDTO;
import com.jovo.ScienceCenter.dto.TransactionCompletedDTO;
import com.jovo.ScienceCenter.dto.TransactionDTO;
import com.jovo.ScienceCenter.exception.RequestTimeoutException;
import com.jovo.ScienceCenter.model.UserData;
import com.jovo.ScienceCenter.service.PaymentService;
import com.jovo.ScienceCenter.service.UserService;
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

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/pay", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RedirectUrlDTO> pay(@RequestParam("productId") Long id, @RequestParam("magazine") boolean magazine,
    											@RequestParam("planId") Long planId) {
        UserData loggedUserData = null;
        try {
            loggedUserData = userService.getLoggedUser();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String redirectUrl = null;
        try {
            redirectUrl = paymentService.pay(loggedUserData.getId(), id, magazine, planId);
        } catch (RequestTimeoutException e) {
            return new ResponseEntity(HttpStatus.REQUEST_TIMEOUT);
        } catch (Exception e) {
           return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<RedirectUrlDTO>(new RedirectUrlDTO(redirectUrl), HttpStatus.OK);
    }

    @RequestMapping(value = "/transaction-completed", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity transactionCompleted(@RequestBody TransactionCompletedDTO transactionCompletedDTO) {
        paymentService.transactionCompleted(transactionCompletedDTO.getMerchantOrderId(), transactionCompletedDTO.getStatus());
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/transactions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TransactionDTO>> getTransactions() {
        try {
            List<TransactionDTO> transactions = paymentService.getTransactions();
            return new ResponseEntity<List<TransactionDTO>>(transactions, HttpStatus.OK);
        }
        catch (RequestTimeoutException e) {
            return new ResponseEntity(HttpStatus.REQUEST_TIMEOUT);
        }
        catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/form-fields-for-payment-types", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FormFieldsForPaymentTypeDTO>> getFormFieldsForPaymentTypes() {
        try {
            List<FormFieldsForPaymentTypeDTO> formFieldsForPaymentTypeDTOs = paymentService.getFormFieldsForPaymentTypes();
            return new ResponseEntity<List<FormFieldsForPaymentTypeDTO>>(formFieldsForPaymentTypeDTOs, HttpStatus.OK);
        }
        catch (RequestTimeoutException e) {
            return new ResponseEntity(HttpStatus.REQUEST_TIMEOUT);
        }
        catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}

package com.jovo.ScienceCenter.controller;

import com.jovo.ScienceCenter.dto.PayDTO;
import com.jovo.ScienceCenter.dto.RedirectUrlDTO;
import com.jovo.ScienceCenter.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/confirm-paying/{merchantOrderId}", method = RequestMethod.GET)
    public ResponseEntity confirmpPaying(@PathVariable("merchantOrderId") Long merchantOrderId) {
        paymentService.confirmPaying(merchantOrderId);
        return new ResponseEntity(HttpStatus.OK);
    }
}

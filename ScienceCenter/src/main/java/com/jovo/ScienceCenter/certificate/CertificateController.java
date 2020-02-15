package com.jovo.ScienceCenter.certificate;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/certificate")
public class CertificateController {

    @Autowired
    private CertificateService certificateService;


    @RequestMapping(value = "/send-request", method = RequestMethod.GET)
    //@EventListener(ApplicationReadyEvent.class)
    public ResponseEntity sendRequestForCertificate() {
        //KeyPair keyPair = certificateService.generateKeyPair();
        CertificateSigningRequest csr = certificateService.prepareCSR(/*keyPair.getPublic()*/);

        try {
            certificateService.sendCSRR(csr);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(HttpStatus.CREATED);
    }

}

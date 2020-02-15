package com.jovo.ScienceCenter.certificate;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping(value = "/certificate")
public class CertificateController {

    @Autowired
    private CertificateService certificateService;

    @Autowired
    private RestTemplate restTemplate;


    @Value("${pki-microservice.create-certificate}")
    private String createCertificateUrl;


    @RequestMapping(value = "/send-request", method = RequestMethod.GET)
    //@EventListener(ApplicationReadyEvent.class)
    public ResponseEntity sendRequestForCertificate() {
        //KeyPair keyPair = certificateService.generateKeyPair();
        CertificateSigningRequest csr = certificateService.prepareCSR(/*keyPair.getPublic()*/);

        ResponseEntity<Void> responseEntity = restTemplate.postForEntity(createCertificateUrl, csr, Void.class);
        if(responseEntity.getStatusCode() == HttpStatus.CREATED) {
            return new ResponseEntity(responseEntity.getStatusCode());
        }

        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

}

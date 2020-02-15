package com.jovo.ScienceCenter.certificate;


import com.jovo.ScienceCenter.certificate.CertificateSigningRequest;


public interface CertificateService {

    CertificateSigningRequest prepareCSR(/*PublicKey publicKey*/);

    void sendCSRR(CertificateSigningRequest csr);
}

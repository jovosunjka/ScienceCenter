package com.jovo.ScienceCenter.certificate;


import com.jovo.ScienceCenter.certificate.CertificateSigningRequest;

import java.io.IOException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;

public interface CertificateService {

    CertificateSigningRequest prepareCSR(/*PublicKey publicKey*/);
}

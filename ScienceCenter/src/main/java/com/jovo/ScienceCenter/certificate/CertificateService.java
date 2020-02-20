package com.jovo.ScienceCenter.certificate;


import com.jovo.ScienceCenter.certificate.CertificateSigningRequest;

import java.io.File;


public interface CertificateService {

    CertificateSigningRequest prepareCSR(/*PublicKey publicKey*/);

    void sendCSRR(CertificateSigningRequest csr);

    boolean changePasswordOfKeyStore(File keyStoreFile, String alias, String oldPasswordStr, String newPasswordStr);
}

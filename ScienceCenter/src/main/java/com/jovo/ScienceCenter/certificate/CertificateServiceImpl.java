package com.jovo.ScienceCenter.certificate;

import com.jovo.ScienceCenter.certificate.CertificateSigningRequest;
import com.jovo.ScienceCenter.certificate.CertificateService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.security.*;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.UUID;


@Service
public class CertificateServiceImpl implements CertificateService
{

    @Value("${server.ssl.key-store}")
    private Resource keyStore;

    @Value("${server.ssl.key-store-password}")
    private char[] keyStorePassword;

    @Value("${server.ssl.key-alias}")
    private String aliasKeyStore;

    @Value("${server.ssl.trust-store}")
    private Resource trustStore;

    @Value("${server.ssl.trust-store-password}")
    private char[] trustStorePassword;

    @Value("${my-url}")
    private String myUrl;

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${ftp.host}")
    private String ftpHost;

    @Value("${ftp.port}")
    private int ftpPort;

    @Value("${ftp.relative-url-to-store-directory}")
    private String ftpRelativeUrlToStoreDirectory;

    @Value("${ftp.username}")
    private String ftpUsername;

    @Value("${ftp.password}")
    private String ftpPassword;



    private final String directoryClassesPath = this.getClass().getResource("../../../../").getPath(); // target/classes folder
    private String directoryStoresPath = directoryClassesPath + "stores"; // target/classes/stores folder


    @Override
    public CertificateSigningRequest prepareCSR(/*PublicKey publicKey*/) {
        // String publicKeyStr = Base64.encodeBase64String(publicKey.getEncoded());
        CertificateSigningRequest csr = new CertificateSigningRequest("localhost", "siem_center",
                applicationName, "RS", UUID.randomUUID().toString()/*, publicKeyStr*/, myUrl, ftpHost, ftpPort,
                ftpRelativeUrlToStoreDirectory, ftpUsername, ftpPassword);
        return csr;
    }

}

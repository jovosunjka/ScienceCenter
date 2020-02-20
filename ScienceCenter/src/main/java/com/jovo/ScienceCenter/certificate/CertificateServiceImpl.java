package com.jovo.ScienceCenter.certificate;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
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


    @Value("${ftp.username}")
    private String ftpUsername;

    @Value("${ftp.password}")
    private String ftpPassword;

    @Value("${pki-microservice.create-certificate}")
    private String createCertificateUrl;

    @Autowired
    private RestTemplate restTemplate;

    private final String directoryClassesPath = this.getClass().getResource("../../../../").getPath(); // target/classes folder
    private String directoryStoresPath = directoryClassesPath + "stores"; // target/classes/stores folder


    @Override
    public CertificateSigningRequest prepareCSR(/*PublicKey publicKey*/) {
        // String publicKeyStr = Base64.encodeBase64String(publicKey.getEncoded());
        CertificateSigningRequest csr = new CertificateSigningRequest("localhost", "siem_center",
                applicationName, "RS", UUID.randomUUID().toString()/*, publicKeyStr*/, myUrl, ftpHost, ftpPort, ftpUsername, ftpPassword);
        return csr;
    }

    @Override
    public void sendCSRR(CertificateSigningRequest csr) {
        ResponseEntity<Void> responseEntity = restTemplate.postForEntity(createCertificateUrl, csr, Void.class);
        if(responseEntity.getStatusCode() != HttpStatus.CREATED) {
            throw new RuntimeException("Certificate Signing Request sending - failed");
        }
    }

    @Override
    public boolean changePasswordOfKeyStore(File keyStoreFile, String alias, String oldPasswordStr, String newPasswordStr) {
        char[] oldPassword = oldPasswordStr.toCharArray();
        char[] newPassword =  newPasswordStr.toCharArray();

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(keyStoreFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        KeyStore keyStore = null;
        try {
            keyStore = KeyStore.getInstance("JKS", "SUN");
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        try {
            keyStore.load(fileInputStream, oldPassword);
        } catch (Exception e) {
            return false;
        }

        if (alias != null) { // ovo znaci da radimo sa keystore-om, a ne sa trustore-om
            // moramo promeniti password i za private key
            try {
                Certificate[] certificates = keyStore.getCertificateChain(alias);
                PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, oldPassword);
                keyStore.setKeyEntry(alias, privateKey, newPassword, certificates); // menjamo password za private key
            } catch (KeyStoreException e) {
                e.printStackTrace();
            } catch (UnrecoverableKeyException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }


        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(keyStoreFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            keyStore.store(fileOutputStream, newPassword);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        try {
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

}

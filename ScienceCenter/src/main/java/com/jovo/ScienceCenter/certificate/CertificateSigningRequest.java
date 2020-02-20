package com.jovo.ScienceCenter.certificate;


public class CertificateSigningRequest {
    private String commonName;
    //private String surname;
    //private String givenName;
    private String organizationName;
    private String organizationalUnitName;
    private String countryCode;
    //private String emailAddress;
    private String userId;
    // private String publicKey;  // ovaj atribut je null za certifikate za CA-a
    private String destinationUrl; // nije obavezan
    private String ftpHost;
    private int ftpPort;
    private String ftpUsername;
    private String ftpPassword;

    public CertificateSigningRequest() {

    }

    public CertificateSigningRequest(String commonName, /*String surname, String givenName,*/ String organizationName,
                                     String organizationalUnitName, String countryCode, /*String emailAddress,*/ String userId,
                                     /*String publicKey,*/ String destinationUrl, String ftpHost, int ftpPort,
                                     String ftpUsername, String ftpPassword) {
        this.commonName = commonName;
        //this.surname = surname;
        //this.givenName = givenName;
        this.organizationName = organizationName;
        this.organizationalUnitName = organizationalUnitName;
        this.countryCode = countryCode;
        //this.emailAddress = emailAddress;
        this.userId = userId;
        //this.publicKey = publicKey;
        this.destinationUrl = destinationUrl;
        this.ftpHost = ftpHost;
        this.ftpPort = ftpPort;
        this.ftpUsername = ftpUsername;
        this.ftpPassword = ftpPassword;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    /*public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }*/

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationalUnitName() {
        return organizationalUnitName;
    }

    public void setOrganizationalUnitName(String organizationalUnitName) {
        this.organizationalUnitName = organizationalUnitName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /*
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }*/

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    /*public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }*/

    public String getDestinationUrl() {
        return destinationUrl;
    }

    public void setDestinationUrl(String destinationUrl) {
        this.destinationUrl = destinationUrl;
    }

    public String getFtpHost() {
        return ftpHost;
    }

    public void setFtpHost(String ftpHost) {
        this.ftpHost = ftpHost;
    }

    public int getFtpPort() {
        return ftpPort;
    }

    public void setFtpPort(int ftpPort) {
        this.ftpPort = ftpPort;
    }


    public String getFtpUsername() {
        return ftpUsername;
    }

    public void setFtpUsername(String ftpUsername) {
        this.ftpUsername = ftpUsername;
    }

    public String getFtpPassword() {
        return ftpPassword;
    }

    public void setFtpPassword(String ftpPassword) {
        this.ftpPassword = ftpPassword;
    }
}

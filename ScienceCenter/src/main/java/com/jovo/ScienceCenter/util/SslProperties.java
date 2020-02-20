package com.jovo.ScienceCenter.util;


public class SslProperties {
    private String keyStoreFilePath;
    private char[] keyStorePassword;
    private String trustStoreFilePath;
    private char[] trustStorePassword;


    public SslProperties() {
        
    }
    
    public SslProperties(String keyStoreFilePath, char[] keyStorePassword, String trustStoreFilePath, char[] trustStorePassword) {
        this.keyStoreFilePath = keyStoreFilePath;
        this.keyStorePassword = keyStorePassword;
        this.trustStoreFilePath = trustStoreFilePath;
        this.trustStorePassword = trustStorePassword;
    }

    public String getKeyStoreFilePath() {
        return keyStoreFilePath;
    }

    public void setKeyStoreFilePath(String keyStoreFilePath) {
        this.keyStoreFilePath = keyStoreFilePath;
    }

    public char[] getKeyStorePassword() {
        return keyStorePassword;
    }

    public void setKeyStorePassword(char[] keyStorePassword) {
        this.keyStorePassword = keyStorePassword;
    }

    public String getTrustStoreFilePath() {
        return trustStoreFilePath;
    }

    public void setTrustStoreFilePath(String trustStoreFilePath) {
        this.trustStoreFilePath = trustStoreFilePath;
    }

    public char[] getTrustStorePassword() {
        return trustStorePassword;
    }

    public void setTrustStorePassword(char[] trustStorePassword) {
        this.trustStorePassword = trustStorePassword;
    }
}

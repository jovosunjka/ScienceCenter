package com.jovo.ScienceCenter.config;


import org.camunda.bpm.engine.impl.digest.PasswordEncryptor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


// https://docs.camunda.org/manual/7.7/user-guide/process-engine/password-hashing/#customize-the-hashing-algorithm
public class BCryptPasswordEncryptor implements PasswordEncryptor {


    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public BCryptPasswordEncryptor() {
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public String encrypt(String password) {
        return this.bCryptPasswordEncoder.encode(password);
    }

    @Override
    public boolean check(String password, String encrypted) {
        return this.bCryptPasswordEncoder.matches(password, encrypted);
    }

    @Override
    public String hashAlgorithmName() {
        // This name is used to resolve the algorithm used for the encryption of a password.
        return "bcrypt";
    }
}

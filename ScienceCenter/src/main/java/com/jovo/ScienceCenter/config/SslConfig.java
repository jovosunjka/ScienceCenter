package com.jovo.ScienceCenter.config;


import com.jovo.ScienceCenter.util.SslProperties;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import javax.net.ssl.SSLContext;
import java.io.IOException;


@Configuration
public class SslConfig {
    @Value("${server.ssl.key-store}")
    private Resource keyStore;

    @Value("${server.ssl.key-store-password}")
    private char[] keyStorePassword;

     @Value("${server.ssl.trust-store}")
     private Resource trustStore;

     @Value("${server.ssl.trust-store-password}")
     private char[] trustStorePassword;


    @Bean
    public SSLContext sslContext() throws Exception {
        System.out.println("*********************** initialize ssl context bean with keystore {} ");
        return new SSLContextBuilder()
                .loadKeyMaterial(keyStore.getFile(), keyStorePassword, keyStorePassword)
                //.loadTrustMaterial(null, new TrustSelfSignedStrategy())
                .loadTrustMaterial(trustStore.getFile(), trustStorePassword)
                .build();
    }

    @Bean
    public SslProperties sslProperties() {
        try {
            return new SslProperties(keyStore.getFile().getAbsolutePath(), keyStorePassword,
                    trustStore.getFile().getAbsolutePath(), trustStorePassword);
        } catch (IOException e) {
            return null;
        }
    }

}

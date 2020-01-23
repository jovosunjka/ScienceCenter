package com.jovo.ScienceCenter.config;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;


@Configuration
public class RestTemplateConfig {

    @Value("${server.ssl.key-store}")
    private Resource keyStore;

    @Value("${server.ssl.key-store-password}")
    private char[] keyStorePassword;

    // @Value("${server.ssl.trust-store}")
    // private Resource trustStore;

    // @Value("${server.ssl.trust-store-password}")
    // private char[] trustStorePassword;

    @Autowired
    private RestTemplateInterceptor restTemplateInterceptor;


    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) throws Exception {
        // TrustSelfSignedStrategy
        // =======================
        // A trust strategy that accepts self-signed certificates as trusted.
        // Verification of all other certificates is done by the trust manager configured in the SSL context.

        SSLContext sslContext = SSLContextBuilder.create()
                .loadKeyMaterial(keyStore.getFile(), keyStorePassword, keyStorePassword)
                .loadTrustMaterial(null, new TrustSelfSignedStrategy())
                // .loadTrustMaterial(trustStore.getFile(), trustStorePassword)
                .build();

        HttpClient client = HttpClients.custom().setSSLContext(sslContext).build();

        RestTemplate restTemplate = builder
                .requestFactory(() -> new HttpComponentsClientHttpRequestFactory(client))
                .build();

        restTemplate.getInterceptors().add(restTemplateInterceptor);

        return restTemplate;
    }

}

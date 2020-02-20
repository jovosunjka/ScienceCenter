package com.rmj.PkiMicroservice.config;

import com.netflix.discovery.DiscoveryClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import javax.net.ssl.SSLContext;


@Configuration
public class SslConfig {
    @Value("${server.ssl.key-store}")
    private Resource keyStore;

    @Value("${server.ssl.key-store-password}")
    private char[] keyStorePassword;

//     @Value("${server.ssl.trust-store}")
//     private Resource trustStore;
//
//     @Value("${server.ssl.trust-store-password}")
//     private char[] trustStorePassword;


    @Bean
    public DiscoveryClient.DiscoveryClientOptionalArgs getTrustStoredEurekaClient(SSLContext sslContext) {
        DiscoveryClient.DiscoveryClientOptionalArgs args = new DiscoveryClient.DiscoveryClientOptionalArgs();
        args.setSSLContext(sslContext);
        args.setHostnameVerifier(new NoopHostnameVerifier());
        return args;
    }

    @Bean
    public SSLContext sslContext() throws Exception {
        System.out.println("*********************** initialize ssl context bean with keystore {} ");
        return new SSLContextBuilder()
                .loadKeyMaterial(keyStore.getFile(), keyStorePassword, keyStorePassword)
                .loadTrustMaterial(null, new TrustSelfSignedStrategy())
                //.loadTrustMaterial(trustStore.getFile(), trustStorePassword)
                .build();
    }

}

package com.jovo.ScienceCenter.config;


import com.jovo.ScienceCenter.model.Magazine;
import com.jovo.ScienceCenter.service.MagazineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

// https://www.baeldung.com/spring-rest-template-interceptor

@Component
public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {

    @Value("${token.header}")
    private String tokenHeader;

    @Autowired
    @Lazy // MagazineService se injektuje u RestTemplateInterceptor, RestTemplateInterceptor se injektuje u RestTemplate,
            // RestTemplate se injektuje u MagazineService => Circular Dependencies
    private MagazineService magazineService;


    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
                                        throws IOException {
        HttpHeaders headers = request.getHeaders();
        String magazineName = headers.getFirst("magazineName");
        if (headers.containsKey("magazineName")) {
            headers.remove("magazineName");
        }

        ClientHttpResponse response;
        if (magazineName == null) {
            response = execution.execute(request, body);
            return response;
        }

        Magazine magazine = magazineService.getMagazine(magazineName);
        boolean loginRequired = magazine.getMerchantId() == null;

        if (loginRequired) { // magazine nije jos ulogovan na payment microservice-u
            magazine = magazineService.loginMagazine(magazine);
        }

        headers.set(tokenHeader, magazine.getMerchantId());
        response = execution.execute(request, body);

        if (response.getStatusCode() == HttpStatus.FORBIDDEN) {
            if (!loginRequired) {
                magazine = magazineService.loginMagazine(magazine);
                headers.set(tokenHeader, magazine.getMerchantId());
                response = execution.execute(request, body);
            }
        }

        return response;
    }

}

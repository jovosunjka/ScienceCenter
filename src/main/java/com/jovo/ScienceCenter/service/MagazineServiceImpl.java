package com.jovo.ScienceCenter.service;

import com.jovo.ScienceCenter.dto.*;
import com.jovo.ScienceCenter.model.Currency;
import com.jovo.ScienceCenter.model.Magazine;
import com.jovo.ScienceCenter.model.MembershipFee;
import com.jovo.ScienceCenter.repository.MagazineRepository;
import com.jovo.ScienceCenter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MagazineServiceImpl implements MagazineService {

    @Value("${payment-microservice.urls.backend.login}")
    private String pmLoginBackendUrl;

    @Autowired
    private MagazineRepository magazineRepository;

    @Autowired
    private MembershipFeeService membershipFeeService;

    @Autowired
    private RestTemplate restTemplate;


    @EventListener(ApplicationReadyEvent.class)
    private void loginAllMagazines() {
        List<Magazine> magazines = getAllMagazines();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        magazines.stream()
                .forEach(magazine -> {
                    LoginUserDTO userDTO = new LoginUserDTO(magazine.getUsername(), magazine.getPassword());

                    HttpEntity<LoginUserDTO> httpEntity = new HttpEntity<LoginUserDTO>(userDTO, headers);
                    ResponseEntity<TokenDTO> tokenDTOResponseEntity = restTemplate.exchange(pmLoginBackendUrl, HttpMethod.PUT, httpEntity, TokenDTO.class);
                    if (tokenDTOResponseEntity.getStatusCode() == HttpStatus.OK) {
                        String token = tokenDTOResponseEntity.getBody().getToken();
                        magazine.setMerchantId(token);
                        magazineRepository.save(magazine);
                        System.out.println(magazine.getName() + " login - success");
                    }
                    else {
                        System.out.println(magazine.getName() + " login - fail");
                    }
                });
    }

    @Override
    public Magazine getMagazine(Long id) {
        return magazineRepository.findById(id).orElse(null);
    }

    @Override
    public List<Magazine> getAllMagazines() {
        return magazineRepository.findAll();
    }

    @Override
    public MembershipFee makeMembershipFee(Long authorId, Long magazineId, double price , Currency currency) {
        MembershipFee membershipFee = new MembershipFee(magazineId, authorId, price, currency, LocalDateTime.now(), 1);
        membershipFeeService.save(membershipFee);
        return  membershipFee;
    }
}

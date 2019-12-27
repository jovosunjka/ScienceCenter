package com.jovo.ScienceCenter.service;

import com.jovo.ScienceCenter.model.Currency;
import com.jovo.ScienceCenter.model.Magazine;
import com.jovo.ScienceCenter.model.MembershipFee;
import com.jovo.ScienceCenter.repository.MagazineRepository;
import com.jovo.ScienceCenter.repository.MembershipFeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MembershipFeeServiceImpl implements MembershipFeeService {

    @Autowired
    private MembershipFeeRepository membershipFeeRepository;


    @Override
    public void save(MembershipFee membershipFee) {
        membershipFeeRepository.save(membershipFee);
    }
}

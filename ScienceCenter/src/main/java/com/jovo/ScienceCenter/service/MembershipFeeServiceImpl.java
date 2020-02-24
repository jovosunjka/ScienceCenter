package com.jovo.ScienceCenter.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jovo.ScienceCenter.exception.NotFoundException;
import com.jovo.ScienceCenter.model.MembershipFee;
import com.jovo.ScienceCenter.repository.MembershipFeeRepository;

@Service
public class MembershipFeeServiceImpl implements MembershipFeeService {

    @Autowired
    private MembershipFeeRepository membershipFeeRepository;


    @Override
    public void save(MembershipFee membershipFee) {
        membershipFeeRepository.save(membershipFee);
    }

    @Override
    public MembershipFee getMembershipFee(Long id) {
        return membershipFeeRepository.findById(id).orElse(null);
    }

    @Override
    public MembershipFee getActivatedMembershipFeeByProductIdAndPayerId(Long productId, boolean magazine, Long payerId) {
        return membershipFeeRepository.findByProductIdAndMagazineAndPayerIdAndPaidAndValidUntilGreaterThan(productId,
                magazine, payerId, true, LocalDateTime.now())
                .orElseThrow(() -> new NotFoundException("MembershipFee (productId=" + productId + ", payerId=" + payerId
                        + ", paid=true) not found!"));
    }

    @Override
    public List<MembershipFee> getMembershipFees(Long payerId) {
        return membershipFeeRepository.findByPayerId(payerId);
    }
    
}

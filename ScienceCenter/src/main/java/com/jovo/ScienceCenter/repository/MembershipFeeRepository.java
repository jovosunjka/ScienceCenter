package com.jovo.ScienceCenter.repository;

import com.jovo.ScienceCenter.model.MembershipFee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MembershipFeeRepository extends JpaRepository<MembershipFee, Long>{

    List<MembershipFee> findByPayerId(Long payerId);
}

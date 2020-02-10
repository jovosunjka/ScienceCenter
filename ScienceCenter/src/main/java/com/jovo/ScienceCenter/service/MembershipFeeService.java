package com.jovo.ScienceCenter.service;

import com.jovo.ScienceCenter.model.Currency;
import com.jovo.ScienceCenter.model.Magazine;
import com.jovo.ScienceCenter.model.MembershipFee;

import java.util.List;

public interface MembershipFeeService {

   void save(MembershipFee membershipFee);

   MembershipFee getMembershipFee(Long id);

   List<MembershipFee> getMembershipFees(Long payerId);

   MembershipFee getActivatedMembershipFeeByMagazineIdAndPayerId(Long magazineId, Long payerId);
}

package com.jovo.ScienceCenter.repository;

import com.jovo.ScienceCenter.model.MembershipFee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MembershipFeeRepository extends JpaRepository<MembershipFee, Long>{

    List<MembershipFee> findByPayerId(Long payerId);

    Optional<MembershipFee> findByMagazineIdAndPayerIdAndPaidAndValidUntilGreaterThan(Long magazineId, Long payerId,
                                                                            boolean paid, LocalDateTime validUntil);
}

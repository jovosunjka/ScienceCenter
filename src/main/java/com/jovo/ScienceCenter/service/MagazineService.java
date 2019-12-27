package com.jovo.ScienceCenter.service;

import com.jovo.ScienceCenter.model.Currency;
import com.jovo.ScienceCenter.model.Magazine;
import com.jovo.ScienceCenter.model.MembershipFee;

import java.util.List;

public interface MagazineService {

    Magazine getMagazine(Long id);

    List<Magazine> getAllMagazines();

    MembershipFee makeMembershipFee(Long authorId, Long magazineId, double price , Currency currency);
}

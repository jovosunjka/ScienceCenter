package com.jovo.ScienceCenter.dto;

import com.jovo.ScienceCenter.model.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class MagazineDTO extends MagazineWithoutPaidStatusDTO {
    private String paidUpTo;
    private List<DTO> plans;
    
    public MagazineDTO() {

    }

    public MagazineDTO(long id, String name, String issn, double membershipFee, Currency currency, String scientificAreas,
                       String mainEditor, String editors, String reviewers, String paidUpTo, List<DTO> plans) {
        super(id, name, issn, currency, scientificAreas, mainEditor, editors, reviewers);
        this.paidUpTo = paidUpTo;
        this.plans = plans;
    }

    public MagazineDTO(Magazine magazine, String paidUpTo) {
        super(magazine);
        this.paidUpTo = paidUpTo;
        this.plans = magazine.getPlans().stream()
        						.map(plan -> new DTO(plan.getId(), plan.getIntervalUnit() + ", "
        										+ plan.getIntervalCount() + ", " + plan.getPrice()))
        						.collect(Collectors.toList());
    }		

    
    public List<DTO> getPlans() {
		return plans;
	}

	public void setPlans(List<DTO> plans) {
		this.plans = plans;
	}

	public String getPaidUpTo() { return paidUpTo; }

    public void setPaidUpTo(String paidUpTo) { this.paidUpTo = paidUpTo; }
}

package com.jovo.ScienceCenter.dto;

import com.jovo.ScienceCenter.model.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class MagazineDTO extends MagazineWithoutPaidStatusDTO {
    private String paidUpTo;

    public MagazineDTO() {

    }

    public MagazineDTO(long id, String name, String issn, double membershipFee, Currency currency, String scientificAreas,
                       String mainEditor, String editors, String reviewers, String paidUpTo) {
        super(id, name, issn, membershipFee, currency, scientificAreas, mainEditor, editors, reviewers);
        this.paidUpTo = paidUpTo;
    }

    public MagazineDTO(Magazine magazine, String paidUpTo) {
        super(magazine);
        this.paidUpTo = paidUpTo;
    }

    public String getPaidUpTo() { return paidUpTo; }

    public void setPaidUpTo(String paidUpTo) { this.paidUpTo = paidUpTo; }
}

package com.jovo.ScienceCenter.service;

import com.jovo.ScienceCenter.model.Coauthor;

public interface CoauthorService {

    void save(Coauthor coauthor);

    Coauthor getCoauthor(String email);
}

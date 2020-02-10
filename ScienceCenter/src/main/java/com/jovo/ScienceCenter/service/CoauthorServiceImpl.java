package com.jovo.ScienceCenter.service;


import com.jovo.ScienceCenter.exception.NotFoundException;
import com.jovo.ScienceCenter.model.Coauthor;
import com.jovo.ScienceCenter.model.Confirmation;
import com.jovo.ScienceCenter.repository.CoauthorRepository;
import com.jovo.ScienceCenter.repository.ConfirmationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CoauthorServiceImpl implements CoauthorService {

    @Autowired
    private CoauthorRepository coauthorRepository;


    @Override
    public void save(Coauthor coauthor) {
        coauthorRepository.save(coauthor);
    }

    @Override
    public Coauthor getCoauthor(String email) {
        return coauthorRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Coauthor (email=".concat(email).concat(") not found!")));
    }

}

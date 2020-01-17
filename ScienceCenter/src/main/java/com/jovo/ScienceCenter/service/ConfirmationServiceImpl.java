package com.jovo.ScienceCenter.service;


import com.jovo.ScienceCenter.exception.NotFoundException;
import com.jovo.ScienceCenter.model.Confirmation;
import com.jovo.ScienceCenter.repository.ConfirmationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;


@Service
public class ConfirmationServiceImpl implements ConfirmationService {

    @Autowired
    private ConfirmationRepository confirmationRepository;


    @Override
    public void save(Confirmation confirmation) {
        confirmationRepository.save(confirmation);
    }

    @Override
    public Confirmation getConfirmation(String confirmationToken) {
        Confirmation confirmation = confirmationRepository.findByToken(confirmationToken)
                .orElseThrow(() -> new NotFoundException("Not found confirmation for token: ".concat(confirmationToken)));
        return  confirmation;
    }

    @Override
    public void delete(Confirmation confirmation) {
        confirmationRepository.delete(confirmation);
    }
}

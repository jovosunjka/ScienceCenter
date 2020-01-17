package com.jovo.ScienceCenter.service;

import com.jovo.ScienceCenter.model.Confirmation;

public interface ConfirmationService {

    void save(Confirmation confirmation);

    Confirmation getConfirmation(String confirmationToken);

    void delete(Confirmation confirmation);
}

package com.jovo.ScienceCenter.service;

import com.jovo.ScienceCenter.dto.FormFieldsForPaymentTypeDTO;
import com.jovo.ScienceCenter.dto.TransactionDTO;
import com.jovo.ScienceCenter.exception.PaymentConcentratorException;
import com.jovo.ScienceCenter.exception.RequestTimeoutException;

import java.util.List;


public interface PaymentService {

    String pay(Long payerId, Long magazineId) throws RequestTimeoutException, PaymentConcentratorException;

    List<TransactionDTO> getTransactions() throws RequestTimeoutException, PaymentConcentratorException;

    void transactionCompleted(long merchantOrderId, String status);

    List<FormFieldsForPaymentTypeDTO> getFormFieldsForPaymentTypes()
            throws RequestTimeoutException, PaymentConcentratorException;
}

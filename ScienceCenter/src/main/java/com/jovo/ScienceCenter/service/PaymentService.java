package com.jovo.ScienceCenter.service;

import com.jovo.ScienceCenter.dto.FormFieldsForPaymentTypeDTO;
import com.jovo.ScienceCenter.dto.TransactionDTO;
import java.util.List;


public interface PaymentService {

    String pay(Long magazineId);

    List<TransactionDTO> getTransactions();

    void transactionCompleted(long merchantOrderId, String status);

    List<FormFieldsForPaymentTypeDTO> getFormFieldsForPaymentTypes();
}

package com.jovo.ScienceCenter.service;

public interface PaymentService {

    String pay(Long magazineId);

    void confirmPaying(Long merchantOrderId);
}

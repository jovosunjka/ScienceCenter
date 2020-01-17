package com.jovo.ScienceCenter.dto;

import java.util.List;

public class TransactionDTOs {
    private List<TransactionDTO> transactions;


    public TransactionDTOs() {

    }


    public TransactionDTOs(List<TransactionDTO> transactions) {
        this.transactions = transactions;
    }


    public List<TransactionDTO> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionDTO> transactions) {
        this.transactions = transactions;
    }
}

package com.jovo.ScienceCenter.repository;

import com.jovo.ScienceCenter.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{

}

package br.com.caju.transaction.repository;

import br.com.caju.transaction.domain.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {
}

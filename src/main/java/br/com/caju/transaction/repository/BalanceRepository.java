package br.com.caju.transaction.repository;

import br.com.caju.transaction.domain.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Long> {

    List<Balance> findByAccountId(final Long accountId);

}

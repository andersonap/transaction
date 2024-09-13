package br.com.caju.transaction.repository;

import br.com.caju.transaction.domain.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {
}

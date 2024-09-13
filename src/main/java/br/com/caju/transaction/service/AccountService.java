package br.com.caju.transaction.service;

import br.com.caju.transaction.domain.Account;
import br.com.caju.transaction.domain.Balance;
import br.com.caju.transaction.repository.AccountRepository;
import br.com.caju.transaction.repository.BalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BalanceRepository balanceRepository;

    public Long createTestAccount() {
        Account account = accountRepository.save(new Account());
        balanceRepository.saveAll(Balance.createDefaultBalances(account.getId()));
        return account.getId();
    }
}

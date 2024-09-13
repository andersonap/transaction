package br.com.caju.transaction.service;

import br.com.caju.transaction.domain.Balance;
import br.com.caju.transaction.exception.InsufficientBalanceException;
import br.com.caju.transaction.repository.BalanceRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class BalanceService {

    @Autowired
    private BalanceRepository balanceRepository;

    @Transactional
    public void updateBalance(final Balance.BalanceCategory category,
                              final Long accountId,
                              final BigDecimal amount) throws InsufficientBalanceException {


        List<Balance> balances = balanceRepository.findByAccountId(accountId);
        try {
            updateBalance(getBalance(balances, category), amount);
        } catch (InsufficientBalanceException e) {
            updateBalance(getBalance(balances, Balance.BalanceCategory.CASH), amount);
        }
    }

    private Balance getBalance(final List<Balance> balances,
                            final Balance.BalanceCategory category) {
        return balances.stream()
                .filter(balance -> category.equals(balance.getCategory()))
                .findFirst()
                .orElseThrow();
    }

    private void updateBalance(final Balance balance, final BigDecimal amount) throws InsufficientBalanceException {
        if (balance.getAmount().compareTo(amount) < 0) {
            log.info("InsufficientBalanceException: balance {}, amount {}", balance.toString(), amount);
            throw new InsufficientBalanceException();
        }
        balanceRepository.save(balance.updateAmount(amount));
    }
}

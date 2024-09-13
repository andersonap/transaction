package br.com.caju.transaction.service;

import br.com.caju.transaction.domain.Balance;
import br.com.caju.transaction.exception.InsufficientBalanceException;
import br.com.caju.transaction.repository.BalanceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BalanceServiceTest {

    @Mock
    private BalanceRepository balanceRepository;

    @InjectMocks
    private BalanceService balanceService;

    private Balance cashBalance;
    private Balance mealBalance;

    @BeforeEach
    void setup() {
        cashBalance = Balance.builder()
                .accountId(1L)
                .amount(BigDecimal.valueOf(100))
                .category(Balance.BalanceCategory.CASH)
                .build();
        mealBalance = Balance.builder()
                .accountId(1L)
                .amount(BigDecimal.valueOf(50))
                .category(Balance.BalanceCategory.MEAL)
                .build();
    }

    @Test
    void whenMealBalanceIsSufficient_thenUpdateBalance() throws InsufficientBalanceException {
        List<Balance> balances = Arrays.asList(cashBalance, mealBalance);
        when(balanceRepository.findByAccountId(1L)).thenReturn(balances);

        balanceService.updateBalance(Balance.BalanceCategory.MEAL, 1L, BigDecimal.valueOf(20.0));

        verify(balanceRepository, times(1)).save(any(Balance.class));
        assertEquals(BigDecimal.valueOf(30.0), mealBalance.getAmount());
    }

    @Test
    void whenMealBalanceIsInsufficient_thenUseCashBalance() throws InsufficientBalanceException {
        List<Balance> balances = Arrays.asList(cashBalance, mealBalance);
        when(balanceRepository.findByAccountId(1L)).thenReturn(balances);

        balanceService.updateBalance(Balance.BalanceCategory.MEAL, 1L, BigDecimal.valueOf(60));

        verify(balanceRepository, times(1)).save(any(Balance.class));
        assertEquals(BigDecimal.valueOf(40), cashBalance.getAmount());
        assertEquals(BigDecimal.valueOf(50), mealBalance.getAmount());
    }

    @Test
    void whenBothMealAndCashBalanceInsufficient_thenThrowInsufficientBalanceException() {
        cashBalance.setAmount(BigDecimal.valueOf(10.0));
        mealBalance.setAmount(BigDecimal.valueOf(5.0));
        List<Balance> balances = Arrays.asList(cashBalance, mealBalance);
        when(balanceRepository.findByAccountId(1L)).thenReturn(balances);

        assertThrows(InsufficientBalanceException.class, () -> {
            balanceService.updateBalance(Balance.BalanceCategory.MEAL, 1L, BigDecimal.valueOf(20.0));
        });

        verify(balanceRepository, times(0)).save(any(Balance.class));
    }

    @Test
    void whenBalanceNotFoundForCategory_thenThrowException() {
        List<Balance> balances = Collections.singletonList(cashBalance);
        when(balanceRepository.findByAccountId(1L)).thenReturn(balances);

        assertThrows(NoSuchElementException.class, () -> {
            balanceService.updateBalance(Balance.BalanceCategory.MEAL, 1L, BigDecimal.valueOf(20.0));
        });

        verify(balanceRepository, times(0)).save(any(Balance.class));
    }

}
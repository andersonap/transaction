package br.com.caju.transaction.service;

import br.com.caju.transaction.controller.resources.TransactionRequest;
import br.com.caju.transaction.controller.resources.TransactionResponse;
import br.com.caju.transaction.domain.Balance;
import br.com.caju.transaction.domain.Transaction;
import br.com.caju.transaction.exception.InsufficientBalanceException;
import br.com.caju.transaction.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private MerchantDictionaryService merchantDictionaryService;

    @Mock
    private BalanceService balanceService;

    @Mock
    private TransactionRepository transactionRepository;

    private TransactionRequest request;

    @BeforeEach
    public void setup() {
        request = TransactionRequest.builder()
                .accountId(1L)
                .amount(BigDecimal.valueOf(100))
                .mcc("5811")
                .merchant("PADARIA DO ZE               SAO PAULO BR")
                .build();

        when(merchantDictionaryService.getCategory(request.getMerchant(), request.getMcc()))
                .thenReturn(Balance.BalanceCategory.MEAL);
    }

    @Test
    public void shouldApproveTransaction() throws InsufficientBalanceException {
        doNothing()
                .when(balanceService).updateBalance(Balance.BalanceCategory.MEAL, request.getAccountId(), request.getAmount());

        when(transactionRepository.save(any(Transaction.class)))
                .thenReturn(Transaction.create(request));

        TransactionResponse response = transactionService.process(request);

        assertTrue(response.isApproved());
    }

    @Test
    public void shouldRejectTransaction_WhenInsufficientBalance() throws InsufficientBalanceException {
        doThrow(new InsufficientBalanceException())
                .when(balanceService).updateBalance(Balance.BalanceCategory.MEAL, request.getAccountId(), request.getAmount());

        TransactionResponse response = transactionService.process(request);

        assertTrue(response.isRejected());
    }

    @Test
    public void shouldReturnGenericError_WhenUnexpectedErrorOccurs() throws InsufficientBalanceException {
        doThrow(new RuntimeException("Generic error"))
                .when(balanceService).updateBalance(Balance.BalanceCategory.MEAL, request.getAccountId(), request.getAmount());

        TransactionResponse response = transactionService.process(request);

        assertTrue(response.isGenericError());
    }

    @Test
    public void shouldSaveTransaction_WhenBalanceIsUpdated() throws InsufficientBalanceException {
        doNothing()
                .when(balanceService).updateBalance(Balance.BalanceCategory.MEAL, request.getAccountId(), request.getAmount());

        transactionService.process(request);

        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

}
package br.com.caju.transaction.service;

import br.com.caju.transaction.controller.resources.TransactionRequest;
import br.com.caju.transaction.controller.resources.TransactionResponse;
import br.com.caju.transaction.domain.Balance;
import br.com.caju.transaction.domain.Transaction;
import br.com.caju.transaction.exception.InsufficientBalanceException;
import br.com.caju.transaction.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    private MerchantDictionaryService merchantDictionaryService;

    @Autowired
    private BalanceService balanceService;

    @Autowired
    private TransactionRepository transactionRepository;


    public TransactionResponse process(final TransactionRequest request) {
        Balance.BalanceCategory category = merchantDictionaryService.getCategory(request.getMerchant(), request.getMcc());
        return updateBalance(request, category);
    }

    @Transactional
    private TransactionResponse updateBalance(TransactionRequest request, Balance.BalanceCategory category) {
        try {
            balanceService.updateBalance(category, request.getAccountId(), request.getAmount());
            transactionRepository.save(Transaction.create(request));
        } catch (InsufficientBalanceException e) {
            return TransactionResponse.rejected();
        } catch (Exception e) {
            return TransactionResponse.genericError();
        }
        return TransactionResponse.approved();
    }

}

package br.com.caju.transaction.service;

import br.com.caju.transaction.domain.Balance;

public class MccFallbackService {

    public static Balance.BalanceCategory getCategory(final String mcc) {
        if ("5411".equals(mcc) || "5412".equals(mcc)) {
            return Balance.BalanceCategory.FOOD;
        }
        if ("5811".equals(mcc) || "5812".equals(mcc)) {
            return Balance.BalanceCategory.MEAL;
        }
        return Balance.BalanceCategory.CASH;
    }
}

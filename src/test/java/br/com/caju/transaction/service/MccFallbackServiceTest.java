package br.com.caju.transaction.service;

import br.com.caju.transaction.domain.Balance;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MccFallbackServiceTest {

    @Test
    void whenMccIs5411Or5412_thenReturnFoodCategory() {
        Balance.BalanceCategory category1 = MccFallbackService.getCategory("5411");
        assertEquals(Balance.BalanceCategory.FOOD, category1);

        Balance.BalanceCategory category2 = MccFallbackService.getCategory("5412");
        assertEquals(Balance.BalanceCategory.FOOD, category2);
    }

    @Test
    void whenMccIs5811Or5812_thenReturnMealCategory() {
        Balance.BalanceCategory category1 = MccFallbackService.getCategory("5811");
        assertEquals(Balance.BalanceCategory.MEAL, category1);

        Balance.BalanceCategory category2 = MccFallbackService.getCategory("5812");
        assertEquals(Balance.BalanceCategory.MEAL, category2);
    }

    @Test
    void whenMccIsNotListed_thenReturnCashCategory() {
        Balance.BalanceCategory category = MccFallbackService.getCategory("1234");
        assertEquals(Balance.BalanceCategory.CASH, category);
    }

}
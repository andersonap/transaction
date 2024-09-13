package br.com.caju.transaction.service;

import br.com.caju.transaction.domain.Balance;
import br.com.caju.transaction.domain.MerchantDictionary;
import br.com.caju.transaction.repository.MerchantDictionaryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MerchantDictionaryServiceTest {

    @Mock
    private MerchantDictionaryRepository repository;

    @InjectMocks
    private MerchantDictionaryService merchantDictionaryService;

    private MerchantDictionary merchantDictionary;

    @BeforeEach
    void setup() {
        merchantDictionary = new MerchantDictionary();
        merchantDictionary.setWord("MERCADO");
        merchantDictionary.setCategory("FOOD");
    }

    @Test
    void whenWordExistsInDictionary_thenReturnsCorrectCategory() {
        when(repository.findAll()).thenReturn(Collections.singletonList(merchantDictionary));

        Balance.BalanceCategory category = merchantDictionaryService.getCategory("MERCADO DO ZE", "9999");

        assertEquals(Balance.BalanceCategory.FOOD, category);

        MockedStatic<MccFallbackService> mccFallbackServiceMockedStatic = mockStatic(MccFallbackService.class);
        mccFallbackServiceMockedStatic.verify(() -> MccFallbackService.getCategory(anyString()), never());
        mccFallbackServiceMockedStatic.close();
    }

    @Test
    void whenWordDontExistsInDictionary_thenFallbackToMccCategory() {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        MockedStatic<MccFallbackService> mccFallbackServiceMockedStatic = mockStatic(MccFallbackService.class);
        mccFallbackServiceMockedStatic.when(() -> MccFallbackService.getCategory(anyString()))
                .thenReturn(Balance.BalanceCategory.CASH);

        Balance.BalanceCategory category = merchantDictionaryService.getCategory("MERCHANT GENERICO", "9999");

        assertEquals(Balance.BalanceCategory.CASH, category);
        mccFallbackServiceMockedStatic.verify(() -> MccFallbackService.getCategory("9999"), times(1));
        mccFallbackServiceMockedStatic.close();
    }

    @Test
    void whenCategoryIsInvalid_thenFallbackToMccCategory() {
        MerchantDictionary invalidCategoryMerchant = new MerchantDictionary();
        invalidCategoryMerchant.setWord("MERCHANT");
        invalidCategoryMerchant.setCategory("INVALID_CATEGORY");

        when(repository.findAll()).thenReturn(Collections.singletonList(invalidCategoryMerchant));

        MockedStatic<MccFallbackService> mccFallbackServiceMockedStatic = mockStatic(MccFallbackService.class);
        mccFallbackServiceMockedStatic.when(() -> MccFallbackService.getCategory(anyString()))
                .thenReturn(Balance.BalanceCategory.CASH);

        Balance.BalanceCategory category = merchantDictionaryService.getCategory("MERCHANT", "9999");

        assertEquals(Balance.BalanceCategory.CASH, category);
        mccFallbackServiceMockedStatic.verify(() -> MccFallbackService.getCategory("9999"), times(1));
        mccFallbackServiceMockedStatic.close();
    }

}
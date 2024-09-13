package br.com.caju.transaction.service;

import br.com.caju.transaction.domain.Balance;
import br.com.caju.transaction.domain.MerchantDictionary;
import br.com.caju.transaction.repository.MerchantDictionaryRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class MerchantDictionaryService {

    @Autowired
    private MerchantDictionaryRepository repository;

    public Balance.BalanceCategory getCategory(final String merchant, final String mcc) {
        List<MerchantDictionary> dictionary = getDictionary();

        Optional<MerchantDictionary> merchantDictionaryOptional = dictionary.stream()
                .filter(word -> StringUtils.containsIgnoreCase(merchant, word.getWord()))
                .findFirst();

        if (merchantDictionaryOptional.isEmpty()) {
            return MccFallbackService.getCategory(mcc);
        }

        String category = merchantDictionaryOptional.get().getCategory();
        try {
            return Balance.BalanceCategory.valueOf(category);
        } catch (IllegalArgumentException e) {
            log.error("Unable to map category {} to enum BalanceCategory", category);
            return MccFallbackService.getCategory(mcc);
        }
    }

    private List<MerchantDictionary> getDictionary() {
        long time = System.nanoTime();
        List<MerchantDictionary> dictionary = repository.findAll();
        System.out.println("TEMPO" + (System.nanoTime() - time));
        return dictionary;
    }
}

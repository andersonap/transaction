package br.com.caju.transaction.repository;

import br.com.caju.transaction.domain.MerchantDictionary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantDictionaryRepository extends JpaRepository<MerchantDictionary, Long> {

}

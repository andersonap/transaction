package br.com.caju.transaction.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "merchant_dictionary")
public class MerchantDictionary implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String word;
    private String category;

}

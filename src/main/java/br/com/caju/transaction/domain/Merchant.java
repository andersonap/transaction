package br.com.caju.transaction.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "merchant")
public class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

}

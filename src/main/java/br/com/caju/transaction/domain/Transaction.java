package br.com.caju.transaction.domain;

import br.com.caju.transaction.controller.resources.TransactionRequest;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal amount;
    private Long accountId;
    private String mcc;
    private String merchant;

    public static Transaction create(final TransactionRequest request) {
        return Transaction.builder()
                .amount(request.getAmount())
                .accountId(request.getAccountId())
                .mcc(request.getMcc())
                .merchant(request.getMerchant())
                .build();
    }
}

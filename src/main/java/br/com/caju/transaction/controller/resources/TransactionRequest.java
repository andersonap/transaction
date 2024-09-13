package br.com.caju.transaction.controller.resources;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
public class TransactionRequest implements Serializable {

    private Long accountId;
    private BigDecimal amount;
    private String merchant;
    private String mcc;

}

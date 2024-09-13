package br.com.caju.transaction.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "balance")
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "account_id")
    private Long accountId;
    @Enumerated(EnumType.STRING)
    private BalanceCategory category;
    private BigDecimal amount;

    public enum BalanceCategory { FOOD, MEAL, CASH }

    public static List<Balance> createDefaultBalances(final Long accountId) {
        return List.of(
                Balance.builder().accountId(accountId).amount(BigDecimal.valueOf(1000)).category(BalanceCategory.FOOD).build(),
                Balance.builder().accountId(accountId).amount(BigDecimal.valueOf(1000)).category(BalanceCategory.MEAL).build(),
                Balance.builder().accountId(accountId).amount(BigDecimal.valueOf(1000)).category(BalanceCategory.CASH).build());
    }

    public Balance updateAmount(final BigDecimal amount) {
        this.setAmount(this.getAmount().subtract(amount));
        return this;
    }
}

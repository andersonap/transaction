package br.com.caju.transaction.controller.resources;

import lombok.Data;

import java.io.Serializable;

@Data
public class TransactionResponse implements Serializable {

    private static final String APPROVED = "00";
    private static final String REJECTED = "51";
    private static final String GENERIC_ERROR = "07";

    private final String code;

    private TransactionResponse(final String code) {
        this.code = code;
    }

    public static TransactionResponse approved() {
        return new TransactionResponse(APPROVED);
    }

    public static TransactionResponse rejected() {
        return new TransactionResponse(REJECTED);
    }

    public static TransactionResponse genericError() {
        return new TransactionResponse(GENERIC_ERROR);
    }

    public boolean isApproved() {
        return APPROVED.equals(code);
    }

    public boolean isRejected() {
        return REJECTED.equals(code);
    }

    public boolean isGenericError() {
        return GENERIC_ERROR.equals(code);
    }
}

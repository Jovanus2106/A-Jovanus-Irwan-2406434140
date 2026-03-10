package id.ac.ui.cs.advprog.eshops.enums;

import lombok.Getter;

@Getter
public enum PaymentMethod {
    VOUCHER_CODE("VOUCHER_CODE"),
    BANK_TRANSFER("BANK_TRANSFER");

    private final String value;

    private PaymentMethod(String value) {
        this.value = value;
    }
}
package com.mercadopago.android.px.model;

import java.math.BigDecimal;

public class AccountMoney {

    private PaymentMethod paymentMethod;
    private BigDecimal availableBalance;
    private boolean invested;
    private String token;

    public String getToken() {
        return token;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public boolean isInvested() {
        return invested;
    }
}

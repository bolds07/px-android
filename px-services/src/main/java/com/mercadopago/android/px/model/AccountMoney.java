package com.mercadopago.android.px.model;

import android.support.annotation.NonNull;
import java.math.BigDecimal;

public class AccountMoney {

    private PaymentMethod paymentMethod;
    private BigDecimal availableBalance;
    private boolean invested;

    @NonNull
    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    @NonNull
    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public boolean isInvested() {
        return invested;
    }
}

package com.mercadopago.android.px.tracking.internal.model;

import android.support.annotation.NonNull;
import java.math.BigDecimal;

public class AccountMoneyExtraInfo extends TrackingMapModel {

    @NonNull private BigDecimal balance;
    private boolean invested;

    public AccountMoneyExtraInfo(@NonNull final BigDecimal balance,
        final boolean invested) {
        this.balance = balance;
        this.invested = invested;
    }
}

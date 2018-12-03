package com.mercadopago.android.px.tracking.internal.views;

import android.support.annotation.NonNull;

public class CardNumberView extends ViewTracker {

    private static final String CARD_NUMBER = "/number";

    @NonNull private final String paymentMethodTypeId;

    public CardNumberView(@NonNull final String paymentMethodTypeId) {
        this.paymentMethodTypeId = paymentMethodTypeId;
    }

    @NonNull
    @Override
    public String getViewPath() {
        return BASE_VIEW_PATH + "/add_payment_method/" + paymentMethodTypeId + CARD_NUMBER;
    }
}

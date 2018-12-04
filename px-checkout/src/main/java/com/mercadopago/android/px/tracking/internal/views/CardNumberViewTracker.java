package com.mercadopago.android.px.tracking.internal.views;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.mercadopago.android.px.internal.util.TextUtil;

public class CardNumberViewTracker extends ViewTracker {

    private static final String CARD_NUMBER = "/number";
    private static final String ADD_PAYMENT_METHOD = "/add_payment_method";

    @Nullable private final String paymentMethodTypeId;

    public CardNumberViewTracker(@Nullable final String paymentMethodTypeId) {
        this.paymentMethodTypeId = paymentMethodTypeId;
    }

    @NonNull
    @Override
    public String getViewPath() {
        if (TextUtil.isEmpty(paymentMethodTypeId)) {
            // Only guessing has no paymentMethodTypeId when screen is showing number input
            // for that reason we decided to remove it from path.
            return BASE_VIEW_PATH + ADD_PAYMENT_METHOD + CARD_NUMBER;
        }
        return BASE_VIEW_PATH + ADD_PAYMENT_METHOD + "/" + paymentMethodTypeId + CARD_NUMBER;
    }
}

package com.mercadopago.android.px.tracking.internal.views;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.mercadopago.android.px.model.Card;
import java.util.HashMap;
import java.util.Map;

public class CvvAskViewTracker extends ViewTracker {

    private static final String PATH = BASE_VIEW_PATH + "/payments/select_method/";

    @Nullable private final Card card;
    @NonNull private final String paymentMethodType;

    public CvvAskViewTracker(@Nullable final Card card, @NonNull final String paymentMethodType) {
        this.card = card;
        this.paymentMethodType = paymentMethodType;
    }

    @NonNull
    @Override
    public Map<String, Object> getData() {
        //TODO verify recovery escenario.
        if (card != null && card.getPaymentMethod() != null) {
            final Map<String, Object> data = new HashMap<>();
            data.put("payment_method_id", card.getPaymentMethod().getId());
            data.put("card_id", card.getId());
            return data;
        }
        return super.getData();
    }

    @NonNull
    @Override
    public String getViewPath() {
        return PATH + paymentMethodType;
    }
}

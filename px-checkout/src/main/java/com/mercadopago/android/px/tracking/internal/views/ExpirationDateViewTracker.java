package com.mercadopago.android.px.tracking.internal.views;

import android.support.annotation.NonNull;
import java.util.Map;

public class ExpirationDateViewTracker extends ViewTracker {

    public static final String CARD_EXPIRATION_DATE = "/expiration_date";

    @NonNull private final String paymentMethodTypeId;
    @NonNull private final String paymentMethodId;

    public ExpirationDateViewTracker(@NonNull final String paymentMethodTypeId, @NonNull final String paymentMethodId) {
        this.paymentMethodTypeId = paymentMethodTypeId;
        this.paymentMethodId = paymentMethodId;
    }

    @NonNull
    @Override
    public Map<String, Object> getData() {
        final Map<String, Object> data = super.getData();
        data.put("payment_method_id", paymentMethodId);
        return data;
    }

    @NonNull
    @Override
    public String getViewPath() {
        return BASE_VIEW_PATH + "/add_payment_method/" + paymentMethodTypeId + CARD_EXPIRATION_DATE;
    }
}

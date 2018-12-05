package com.mercadopago.android.px.tracking.internal.views;

import android.support.annotation.NonNull;
import java.util.Locale;

public class ErrorMoreInfoCardViewTracker extends ViewTracker {

    private static final String PATH_EXCLUDED_CARD = BASE_VIEW_PATH + "/add_payment_method/%s/number/error_more_info";

    @NonNull private final String paymentMethodTypeId;

    public ErrorMoreInfoCardViewTracker(@NonNull final String paymentMethodTypeId) {
        this.paymentMethodTypeId = paymentMethodTypeId;
    }

    @NonNull
    @Override
    public String getViewPath() {
        return String.format(Locale.US, PATH_EXCLUDED_CARD, paymentMethodTypeId);
    }
}

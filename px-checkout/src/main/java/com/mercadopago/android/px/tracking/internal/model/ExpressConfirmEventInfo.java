package com.mercadopago.android.px.tracking.internal.model;

import android.support.annotation.NonNull;

public class ExpressConfirmEventInfo extends AvailableMethod {

    private String reviewType = "one_tap";

    public ExpressConfirmEventInfo(@NonNull final String paymentMethodType) {
        super(paymentMethodType);
    }

    public ExpressConfirmEventInfo(@NonNull final String paymentMethodId, @NonNull final String paymentMethodType,
        @NonNull final TrackModel trackModel) {
        super(paymentMethodId, paymentMethodType, trackModel);
    }

    public ExpressConfirmEventInfo(@NonNull final String paymentMethodId, @NonNull final String paymentMethodType) {
        super(paymentMethodId, paymentMethodType);
    }
}

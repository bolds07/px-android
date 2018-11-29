package com.mercadopago.android.px.tracking.internal.model;

import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import java.util.Map;

/**
 * Class used for Payment vault and Express checkout screen.
 */
@SuppressWarnings("unused")
@Keep
public class AvailableMethod extends TrackModel {

    private final String paymentMethodId;
    private final String paymentMethodType;
    private final Map<String, Object> extraInfo;

    public AvailableMethod(@NonNull final String paymentMethodType) {
        this.paymentMethodType = paymentMethodType;
        paymentMethodId = null;
        extraInfo = null;
    }

    public AvailableMethod(@NonNull final String paymentMethodId,
        @NonNull final String paymentMethodType,
        @NonNull final TrackModel trackModel) {
        this.paymentMethodId = paymentMethodId;
        this.paymentMethodType = paymentMethodType;
        extraInfo = trackModel.transform();
    }

    public AvailableMethod(@NonNull final String paymentMethodId,
        @NonNull final String paymentMethodType) {
        this.paymentMethodId = paymentMethodId;
        this.paymentMethodType = paymentMethodType;
        extraInfo = null;
    }
}

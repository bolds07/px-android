package com.mercadopago.android.px.tracking.internal.model;

import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.Map;

/**
 * Class used for Payment vault and Express checkout screen.
 */
@SuppressWarnings("unused")
@Keep
public class AvailableMethod extends TrackingMapModel {

    @Nullable protected final String paymentMethodId;
    @NonNull protected final String paymentMethodType;
    @Nullable protected final Map<String, Object> extraInfo;

    public AvailableMethod(@NonNull final String paymentMethodType) {
        this.paymentMethodType = paymentMethodType;
        paymentMethodId = null;
        extraInfo = null;
    }

    public AvailableMethod(@NonNull final String paymentMethodId,
        @NonNull final String paymentMethodType,
        @NonNull final Map<String, Object> extraInfo) {
        this.paymentMethodId = paymentMethodId;
        this.paymentMethodType = paymentMethodType;
        this.extraInfo = extraInfo;
    }

    public AvailableMethod(@Nullable final String paymentMethodId,
        @NonNull final String paymentMethodType) {
        this.paymentMethodId = paymentMethodId;
        this.paymentMethodType = paymentMethodType;
        extraInfo = null;
    }
}

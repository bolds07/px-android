package com.mercadopago.android.px.tracking.internal.model;

import android.support.annotation.NonNull;

@SuppressWarnings("unused")
public class ExpressConfirmEventData extends AvailableMethod {

    private final String reviewType = "one_tap";

    public ExpressConfirmEventData(@NonNull final AvailableMethod availableMethod) {
        super(availableMethod.paymentMethodId, availableMethod.paymentMethodType, availableMethod.extraInfo);
    }
}

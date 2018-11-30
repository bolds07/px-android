package com.mercadopago.android.px.tracking.internal.model;

import android.support.annotation.Keep;
import android.support.annotation.NonNull;

@SuppressWarnings("unused")
@Keep
public class ExpressConfirmEventData extends AvailableMethod {

    private final String reviewType = "one_tap";

    public ExpressConfirmEventData(@NonNull final AvailableMethod availableMethod) {
        super(availableMethod.paymentMethodId, availableMethod.paymentMethodType, availableMethod.extraInfo);
    }
}

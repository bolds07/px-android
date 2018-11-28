package com.mercadopago.android.px.internal.features.review_and_confirm.models;

import android.os.Parcelable;

public abstract class PaymentModel implements Parcelable {

    /* default */ String paymentType;
    /* default */ String paymentMethodId;

    public abstract String getPaymentType();
    public abstract String getPaymentMethodId();
}

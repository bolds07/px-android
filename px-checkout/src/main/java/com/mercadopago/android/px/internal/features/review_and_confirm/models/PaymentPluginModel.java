package com.mercadopago.android.px.internal.features.review_and_confirm.models;

import android.os.Parcel;
import android.os.Parcelable;
import com.mercadopago.android.px.model.Issuer;
import com.mercadopago.android.px.model.PaymentMethod;
import com.mercadopago.android.px.model.Token;

public class PaymentPluginModel extends PaymentModel {

    public PaymentPluginModel(final String paymentMethodId, final String paymentType) {
        this.paymentMethodId = paymentMethodId;
        this.paymentType = paymentType;
    }

    protected PaymentPluginModel(final Parcel in) {
        paymentMethodId = in.readString();
        paymentType = in.readString();
    }

    public static final Creator<PaymentPluginModel> CREATOR = new Creator<PaymentPluginModel>() {
        @Override
        public PaymentPluginModel createFromParcel(final Parcel in) {
            return new PaymentPluginModel(in);
        }

        @Override
        public PaymentPluginModel[] newArray(final int size) {
            return new PaymentPluginModel[size];
        }
    };

    @Override
    public String getPaymentType() {
        return paymentType;
    }

    @Override
    public String getPaymentMethodId() {
        return paymentMethodId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeString(paymentMethodId);
        dest.writeString(paymentType);
    }
}

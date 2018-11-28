package com.mercadopago.android.px.internal.features.review_and_confirm.models;

import android.os.Parcel;
import com.mercadopago.android.px.model.Issuer;
import com.mercadopago.android.px.model.PaymentMethod;
import com.mercadopago.android.px.model.Token;

public class PaymentMethodOffModel extends PaymentModel {

    public final Integer accreditationTime;
    public final String paymentMethodName;

    public PaymentMethodOffModel(final String paymentMethodId, final String paymentType,
        final Integer accreditationTime, final String paymentMethodName) {
        this.accreditationTime = accreditationTime;
        this.paymentMethodName = paymentMethodName;
        this.paymentType = paymentType;
        this.paymentMethodId = paymentMethodId;
    }

    protected PaymentMethodOffModel(final Parcel in) {
        paymentMethodId = in.readString();
        if (in.readByte() == 0) {
            accreditationTime = null;
        } else {
            accreditationTime = in.readInt();
        }

        paymentMethodName = in.readString();
        paymentType = in.readString();
    }

    public static final Creator<PaymentMethodOffModel> CREATOR = new Creator<PaymentMethodOffModel>() {
        @Override
        public PaymentMethodOffModel createFromParcel(final Parcel in) {
            return new PaymentMethodOffModel(in);
        }

        @Override
        public PaymentMethodOffModel[] newArray(final int size) {
            return new PaymentMethodOffModel[size];
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
        if (accreditationTime == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(accreditationTime);
        }

        dest.writeString(paymentMethodName);
        dest.writeString(paymentType);
    }
}

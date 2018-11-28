package com.mercadopago.android.px.internal.features.review_and_confirm.models;

import android.os.Parcel;

public class PaymentAccountMoneyModel extends PaymentModel {

    private final String paymentMethodName;
    private final boolean invested;

    public PaymentAccountMoneyModel(final String paymentType, final String paymentMethodId,
        final String paymentMethodName, final boolean invested) {
        this.paymentMethodName = paymentMethodName;
        this.invested = invested;
        this.paymentMethodId = paymentMethodId;
        this.paymentType = paymentType;
    }

    protected PaymentAccountMoneyModel(final Parcel in) {
        paymentMethodId = in.readString();
        paymentMethodName = in.readString();
        paymentType = in.readString();
        invested = in.readByte() != 0;
    }

    public static final Creator<PaymentAccountMoneyModel> CREATOR = new Creator<PaymentAccountMoneyModel>() {
        @Override
        public PaymentAccountMoneyModel createFromParcel(final Parcel in) {
            return new PaymentAccountMoneyModel(in);
        }

        @Override
        public PaymentAccountMoneyModel[] newArray(final int size) {
            return new PaymentAccountMoneyModel[size];
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

    public String getPaymentMethodName() {
        return paymentMethodName;
    }

    public boolean isInvested() {
        return invested;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeString(paymentMethodId);
        dest.writeString(paymentMethodName);
        dest.writeString(paymentType);
        dest.writeByte((byte) (invested ? 1 : 0));
    }
}

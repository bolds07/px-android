package com.mercadopago.android.px.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PayerCostConfiguration implements Serializable, Parcelable {

    private final Map<String, List<PayerCost>> configuration = new HashMap<>();
    private final int selectedPayerCostIndex;

    public Map<String, List<PayerCost>> getConfiguration() {
        return configuration;
    }

    public int getSelectedPayerCostIndex() {
        return selectedPayerCostIndex;
    }

    protected PayerCostConfiguration(final Parcel in) {
        selectedPayerCostIndex = in.readInt();
        in.readMap(configuration, PayerCost.class.getClassLoader());
    }

    public static final Creator<PayerCostConfiguration> CREATOR = new Creator<PayerCostConfiguration>() {
        @Override
        public PayerCostConfiguration createFromParcel(final Parcel in) {
            return new PayerCostConfiguration(in);
        }

        @Override
        public PayerCostConfiguration[] newArray(final int size) {
            return new PayerCostConfiguration[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeInt(selectedPayerCostIndex);
        dest.writeMap(configuration);
    }
}

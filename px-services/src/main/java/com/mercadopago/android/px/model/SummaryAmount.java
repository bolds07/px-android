package com.mercadopago.android.px.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SummaryAmount implements Serializable, Parcelable {

    private String selectedAmountConfiguration;
    private Map<String, DiscountConfigurationModel> discountConfigurations;
    private Map<String, List<PayerCost>> payerCostConfigurations;

    protected SummaryAmount(Parcel in) {
        selectedAmountConfiguration = in.readString();
        discountConfigurations = new HashMap<>();
        in.readMap(discountConfigurations, SummaryAmount.class.getClassLoader());
        payerCostConfigurations = new HashMap<>();
        in.readMap(payerCostConfigurations, SummaryAmount.class.getClassLoader());
    }

    public static final Creator<SummaryAmount> CREATOR = new Creator<SummaryAmount>() {
        @Override
        public SummaryAmount createFromParcel(Parcel in) {
            return new SummaryAmount(in);
        }

        @Override
        public SummaryAmount[] newArray(int size) {
            return new SummaryAmount[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel parcel, final int i) {
        parcel.writeString(selectedAmountConfiguration);
        parcel.writeMap(discountConfigurations);
        parcel.writeMap(payerCostConfigurations);
    }

    public String getSelectedAmountConfiguration() {
        return selectedAmountConfiguration;
    }

    public List<PayerCost> getPayerCostConfiguration(final String key) {
        return payerCostConfigurations.get(key);
    }

    public DiscountConfigurationModel getDiscountConfiguration(final String key) {
        return discountConfigurations.get(key);
    }
}

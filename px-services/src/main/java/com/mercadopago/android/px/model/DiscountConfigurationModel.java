package com.mercadopago.android.px.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;

class DiscountConfigurationModel implements Serializable, Parcelable {

    private Discount discount;
    private Campaign campaign;
    private boolean isAvailable;

    public DiscountConfigurationModel(final Discount discount, final Campaign campaign, final boolean isAvailable) {
        this.discount = discount;
        this.campaign = campaign;
        this.isAvailable = isAvailable;
    }

    protected DiscountConfigurationModel(Parcel in) {
        discount = in.readParcelable(Discount.class.getClassLoader());
        campaign = in.readParcelable(Campaign.class.getClassLoader());
        isAvailable = in.readByte() != 0;
    }

    public static final Creator<DiscountConfigurationModel> CREATOR = new Creator<DiscountConfigurationModel>() {
        @Override
        public DiscountConfigurationModel createFromParcel(Parcel in) {
            return new DiscountConfigurationModel(in);
        }

        @Override
        public DiscountConfigurationModel[] newArray(int size) {
            return new DiscountConfigurationModel[size];
        }
    };

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(final Discount discount) {
        this.discount = discount;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(final Campaign campaign) {
        this.campaign = campaign;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(final boolean available) {
        isAvailable = available;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel parcel, final int i) {
        parcel.writeParcelable(discount, i);
        parcel.writeParcelable(campaign, i);
        parcel.writeByte((byte) (isAvailable ? 1 : 0));
    }
}

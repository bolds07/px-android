package com.mercadopago.android.px.configuration;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import org.json.JSONObject;

@SuppressWarnings("unused")
public class DiscountParamsConfiguration {

    @NonNull private final JSONObject extraData;
    @Nullable private final String flow;

    /* default */ DiscountParamsConfiguration(@NonNull final Builder builder) {
        extraData = builder.extraData;
        flow = builder.flow;
    }

    /**
     * @return extraData needed to apply a specific discount.
     */
    @NonNull
    public JSONObject getExtraData() {
        return extraData;
    }

    /**
     * @return payments's flow identification.
     */
    @Nullable
    public String getFlow() {
        return flow;
    }

    public static class Builder {
        /* default */ JSONObject extraData;
        /* default */ String flow;

        public Builder() {
            extraData = new JSONObject();
            flow = "";
        }

        /**
         * Set additional data needed to apply a specific discount.
         *
         * @param extraData additional data needed to apply a specific discount.
         */
        public void setExtraData(@NonNull final JSONObject extraData) {
            this.extraData = extraData;
        }

        /**
         * Enable discounts for any specific payment's flow identification setting it through this method.
         *
         * @param flow payment's flow identification.
         * @return builder to keep operating
         */
        public Builder setFlow(@NonNull final String flow) {
            this.flow = flow;
            return this;
        }

        public DiscountParamsConfiguration build() {
            return new DiscountParamsConfiguration(this);
        }
    }
}
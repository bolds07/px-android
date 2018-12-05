package com.mercadopago.android.px.configuration;

import android.support.annotation.NonNull;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("unused")
public class DiscountParamsConfiguration {

    @NonNull private final Set<String> labels;
    @NonNull private final String flow;
    @NonNull private final String marketplace;

    /* default */ DiscountParamsConfiguration(@NonNull final Builder builder) {
        labels = builder.labels;
        flow = builder.flow;
        marketplace = builder.marketplace;
    }

    /**
     * Let us know what the labels is
     *
     * @return set of labels
     */
    @NonNull
    public Set<String> getLabels() {
        return labels;
    }

    /**
     * Let us know what the flow is
     *
     * @return flow id
     */
    @NonNull
    public String getFlow() {
        return flow;
    }

    /**
     * Let us know what the marketplace is
     *
     * @return marketplace id
     */
    @NonNull
    public String getMarketplace() {
        return marketplace;
    }

    public static class Builder {

        public static final String DEFAULT_MARKETPLACE = "none";

        /* default */ Set<String> labels;
        /* default */ String flow;
        /* default */ String marketplace;

        public Builder() {
            labels = new HashSet<>();
            flow = "";
            marketplace = DEFAULT_MARKETPLACE;
        }

        /**
         * This are filters for enable particular discounts.
         *
         * @param labels set of Mercado Pago filters
         * @return builder to keep operating
         */
        public Builder setLabels(@NonNull final Set<String> labels) {
            this.labels = labels;
            return this;
        }

        /**
         * Payment flow let us to enable discounts for the flow specified.
         *
         * @param flow payment flow id
         * @return builder to keep operating
         */
        public Builder setFlow(@NonNull final String flow) {
            this.flow = flow;
            return this;
        }

        /**
         * Marketplace let us to enable discounts for the marketplace specified.
         *
         * @param marketplace marketplace id
         * @return builder to keep operating
         */
        public Builder setMarketplace(@NonNull final String marketplace) {
            this.marketplace = marketplace;
            return this;
        }

        public DiscountParamsConfiguration build() {
            return new DiscountParamsConfiguration(this);
        }
    }
}

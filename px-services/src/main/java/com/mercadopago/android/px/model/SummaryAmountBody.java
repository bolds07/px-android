package com.mercadopago.android.px.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.math.BigDecimal;

public class SummaryAmountBody {

    @NonNull private String siteId;
    @NonNull private BigDecimal transactionAmount;
    @NonNull private String marketplace;
    @NonNull private String email;
    @NonNull private String flow;
    @NonNull private String paymentMethodId;
    @NonNull private String paymentType;
    @NonNull private String bin;
    @NonNull private Long issuerId;
    @NonNull private Integer defaultInstallments;
    @Nullable private Integer differentialPricingId;
    @Nullable private String processingMode;

    public SummaryAmountBody(@NonNull final String siteId, @NonNull final BigDecimal transactionAmount,
        @NonNull final String marketplace, @NonNull final String email, @NonNull final String flow,
        @NonNull final String paymentMethodId,
        @NonNull final String paymentType, @NonNull final String bin, @NonNull final Long issuerId,
        @NonNull final Integer defaultInstallments, @Nullable final Integer differentialPricingId,
        @Nullable final String processingMode) {
        this.siteId = siteId;
        this.transactionAmount = transactionAmount;
        this.marketplace = marketplace;
        this.email = email;
        this.flow = flow;
        this.paymentMethodId = paymentMethodId;
        this.paymentType = paymentType;
        this.bin = bin;
        this.issuerId = issuerId;
        this.defaultInstallments = defaultInstallments;
        this.differentialPricingId = differentialPricingId;
        this.processingMode = processingMode;
    }

    @NonNull
    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(@NonNull final String siteId) {
        this.siteId = siteId;
    }

    @NonNull
    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(@NonNull final BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    @NonNull
    public String getMarketplace() {
        return marketplace;
    }

    public void setMarketplace(@NonNull final String marketplace) {
        this.marketplace = marketplace;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull final String email) {
        this.email = email;
    }

    @NonNull
    public String getFlow() {
        return flow;
    }

    public void setFlow(@NonNull final String flow) {
        this.flow = flow;
    }

    @NonNull
    public String getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(@NonNull final String paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    @NonNull
    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(@NonNull final String paymentType) {
        this.paymentType = paymentType;
    }

    @NonNull
    public String getBin() {
        return bin;
    }

    public void setBin(@NonNull final String bin) {
        this.bin = bin;
    }

    @NonNull
    public Long getIssuerId() {
        return issuerId;
    }

    public void setIssuerId(@NonNull final Long issuerId) {
        this.issuerId = issuerId;
    }

    @NonNull
    public Integer getDefaultInstallments() {
        return defaultInstallments;
    }

    public void setDefaultInstallments(@NonNull final Integer defaultInstallments) {
        this.defaultInstallments = defaultInstallments;
    }

    @Nullable
    public Integer getDifferentialPricingId() {
        return differentialPricingId;
    }

    public void setDifferentialPricingId(@Nullable final Integer differentialPricingId) {
        this.differentialPricingId = differentialPricingId;
    }

    @Nullable
    public String getProcessingMode() {
        return processingMode;
    }

    public void setProcessingMode(@Nullable final String processingMode) {
        this.processingMode = processingMode;
    }
}

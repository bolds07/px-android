package com.mercadopago.android.px.tracking.internal.model;

import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import com.mercadopago.android.px.model.PayerCost;
import java.math.BigDecimal;

@SuppressWarnings("unused")
@Keep
public class PayerCostTrackModel extends TrackModel {

    @NonNull private Integer quantity;
    @NonNull private BigDecimal installmentAmount;
    @NonNull private BigDecimal visibleTotalPrice;
    @NonNull private BigDecimal interestRate;

    public PayerCostTrackModel(@NonNull final PayerCost selected) {
        quantity = selected.getInstallments();
        installmentAmount = selected.getInstallmentAmount();
        visibleTotalPrice = selected.getTotalAmount();
        interestRate = selected.getInstallmentRate();
    }
}
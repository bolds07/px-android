package com.mercadopago.android.px.tracking.internal.model;

import android.support.annotation.NonNull;
import com.mercadopago.android.px.model.ExpressMetadata;
import com.mercadopago.android.px.model.PayerCost;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ExpressInstallmentsView implements Serializable {

    @NonNull private String paymentMethodType;
    @NonNull private String paymentMethodId;
    @NonNull private Long issuerId;
    @NonNull private String cardId;
    @NonNull private BigDecimal totalAmount;
    @NonNull private List<PayerCostInfo> availableInstallments;
    @NonNull private String currencyId;

    public ExpressInstallmentsView(@NonNull final String paymentMethodType, @NonNull final String paymentMethodId,
        @NonNull final Long issuerId,
        @NonNull final String cardId,
        @NonNull final BigDecimal totalAmount,
        @NonNull final List<PayerCostInfo> payerCostTrackModels, @NonNull final String currencyId) {
        this.paymentMethodType = paymentMethodType;
        this.paymentMethodId = paymentMethodId;
        this.issuerId = issuerId;
        this.cardId = cardId;
        this.totalAmount = totalAmount;
        availableInstallments = payerCostTrackModels;
        this.currencyId = currencyId;
    }

    public static ExpressInstallmentsView createFrom(@NonNull final ExpressMetadata expressMetadata,
        @NonNull final String currencyId, @NonNull final BigDecimal totalAmount) {
        final String paymentMethodType = expressMetadata.getPaymentTypeId();
        final String paymentMethodId = expressMetadata.getPaymentMethodId();
        final String cardId = expressMetadata.getCard().getId();
        final Long issuerId = expressMetadata.getCard().getDisplayInfo().issuerId;
        final List<PayerCostInfo> payerCostTrackModels = new ArrayList<>();
        for (final PayerCost payerCost : expressMetadata.getCard().getPayerCosts()) {
            payerCostTrackModels.add(new PayerCostInfo(payerCost));
        }

        return new ExpressInstallmentsView(paymentMethodType, paymentMethodId, issuerId, cardId, totalAmount,
            payerCostTrackModels, currencyId);
    }
}

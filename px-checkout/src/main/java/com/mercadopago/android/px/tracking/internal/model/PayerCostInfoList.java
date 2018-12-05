package com.mercadopago.android.px.tracking.internal.model;

import android.support.annotation.NonNull;
import com.mercadopago.android.px.model.PayerCost;
import java.util.ArrayList;
import java.util.List;

public class PayerCostInfoList extends TrackingMapModel {

    @NonNull private final List<PayerCostInfo> availableInstallments;

    public PayerCostInfoList(@NonNull final Iterable<PayerCost> payerCosts) {
        availableInstallments = new ArrayList<>();
        for (final PayerCost payerCost : payerCosts) {
            availableInstallments.add(new PayerCostInfo(payerCost));
        }
    }
}

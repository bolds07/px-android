package com.mercadopago.android.px.tracking.internal.model;

import android.support.annotation.NonNull;
import java.math.BigDecimal;
import java.util.List;

public class ReviewAndConfirmData extends AvailableMethod {

    @NonNull final List<ItemInfo> items;
    @NonNull final BigDecimal totalAmount;

    public ReviewAndConfirmData(@NonNull final AvailableMethod method, @NonNull final List<ItemInfo> itemInfos,
        @NonNull final BigDecimal totalAmount) {
        super(method.paymentMethodId, method.paymentMethodType);
        items = itemInfos;
        this.totalAmount = totalAmount;
    }
}

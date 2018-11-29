package com.mercadopago.android.px.tracking.internal.model;

import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.math.BigDecimal;

@SuppressWarnings("unused")
@Keep
public class ItemInfo extends TrackingMapModel {

    private int quantity;
    private ItemDetail item;

    public ItemInfo(@Nullable final String id,
        @Nullable final String description,
        @NonNull final BigDecimal price,
        final int quantity) {
        this.quantity = quantity;
        item = new ItemDetail(id, description, price);
    }
}

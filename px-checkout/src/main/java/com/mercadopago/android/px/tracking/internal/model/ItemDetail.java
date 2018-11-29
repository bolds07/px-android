package com.mercadopago.android.px.tracking.internal.model;

import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import com.mercadopago.android.px.model.Item;
import java.math.BigDecimal;

@SuppressWarnings("unused")
@Keep
public class ItemDetail extends TrackingMapModel {

    private final String id;
    private final String description;
    private final BigDecimal price;

    public ItemDetail(@NonNull final Item val) {
        id = val.getId();
        description = val.getDescription();
        price = val.getUnitPrice();
    }
}

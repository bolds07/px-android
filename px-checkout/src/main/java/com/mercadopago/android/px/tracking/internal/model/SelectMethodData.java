package com.mercadopago.android.px.tracking.internal.model;

import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import java.util.List;

@SuppressWarnings("unused")
@Keep
public class SelectMethodData extends TrackModel {

    private final List<AvailableMethod> availableMethods;
    private final List<ItemInfo> items;

    public SelectMethodData(@NonNull final List<AvailableMethod> availableMethods,
        @NonNull final List<ItemInfo> items) {
        this.availableMethods = availableMethods;
        this.items = items;
    }
}

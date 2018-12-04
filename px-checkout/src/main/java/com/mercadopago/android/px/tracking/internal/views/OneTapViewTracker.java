package com.mercadopago.android.px.tracking.internal.views;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.mercadopago.android.px.model.Campaign;
import com.mercadopago.android.px.model.Discount;
import com.mercadopago.android.px.model.ExpressMetadata;
import com.mercadopago.android.px.model.Item;
import com.mercadopago.android.px.tracking.internal.model.OneTapData;
import java.math.BigDecimal;
import java.util.Map;

public class OneTapViewTracker extends ViewTracker {

    private static final String PATH_REVIEW_ONE_TAP_VIEW = BASE_VIEW_PATH + "/review/one_tap";

    private final Map<String, Object> data;

    public OneTapViewTracker(@NonNull final Iterable<ExpressMetadata> expressMetadata,
        @NonNull final BigDecimal total,
        @Nullable final Discount discount,
        @Nullable final Campaign campaign,
        @NonNull final Iterable<Item> items) {
        data = OneTapData.createFrom(expressMetadata, total, discount, campaign, items).toMap();
    }

    @NonNull
    @Override
    public Map<String, Object> getData() {
        return data;
    }

    @NonNull
    @Override
    public String getViewPath() {
        return PATH_REVIEW_ONE_TAP_VIEW;
    }
}

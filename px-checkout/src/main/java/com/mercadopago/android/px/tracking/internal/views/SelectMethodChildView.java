package com.mercadopago.android.px.tracking.internal.views;

import android.support.annotation.NonNull;
import com.mercadopago.android.px.model.Item;
import com.mercadopago.android.px.model.PaymentMethodSearch;
import com.mercadopago.android.px.model.PaymentMethodSearchItem;
import com.mercadopago.android.px.tracking.internal.mapper.FromItemToItemInfo;
import com.mercadopago.android.px.tracking.internal.mapper.FromPaymentMethodSearchItemToAvailableMethod;
import com.mercadopago.android.px.tracking.internal.model.SelectMethodData;
import java.util.List;
import java.util.Map;

public class SelectMethodChildView extends ViewTracker {

    private static final String CONTEXT_VALUE = BASE_VIEW_PATH + "/payments";
    private static final String PATH_PAYMENT_VAULT = CONTEXT_VALUE + "/select_method";
    @NonNull private final String parentId;
    @NonNull private final Map<String, Object> data;

    public SelectMethodChildView(final PaymentMethodSearch paymentMethodSearch,
        @NonNull final PaymentMethodSearchItem selected, @NonNull final Iterable<Item> items) {
        parentId = selected.getId();
        final List<PaymentMethodSearchItem> children = selected.getChildren();
        data = new SelectMethodData(new FromPaymentMethodSearchItemToAvailableMethod(paymentMethodSearch).map(children),
            new FromItemToItemInfo().map(items)).toMap();
    }

    @NonNull
    @Override
    public String getViewPath() {
        return PATH_PAYMENT_VAULT + "/" + parentId;
    }

    @NonNull
    @Override
    public Map<String, Object> getData() {
        return data;
    }
}

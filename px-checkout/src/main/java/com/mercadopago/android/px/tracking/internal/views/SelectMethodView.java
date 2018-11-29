package com.mercadopago.android.px.tracking.internal.views;

import android.support.annotation.NonNull;
import com.mercadopago.android.px.model.Item;
import com.mercadopago.android.px.model.PaymentMethodSearch;
import com.mercadopago.android.px.tracking.internal.mapper.FromCustomItemToAvailableMethod;
import com.mercadopago.android.px.tracking.internal.mapper.FromItemToItemInfo;
import com.mercadopago.android.px.tracking.internal.mapper.FromPaymentMethodToAvailableMethods;
import com.mercadopago.android.px.tracking.internal.model.AvailableMethod;
import com.mercadopago.android.px.tracking.internal.model.ItemInfo;
import com.mercadopago.android.px.tracking.internal.model.SelectMethodData;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SelectMethodView extends ViewTracker {

    private static final String CONTEXT_VALUE = BASE_VIEW_PATH + "/payments";
    private static final String PATH_PAYMENT_VAULT = CONTEXT_VALUE + "/select_method";
    @NonNull private final List<AvailableMethod> availableMethods;
    @NonNull private final List<ItemInfo> items;

    public SelectMethodView(@NonNull final PaymentMethodSearch paymentMethodSearch,
        @NonNull final Set<String> escCardIds,
        @NonNull final Iterable<Item> items) {
        availableMethods =
            new ArrayList<>(
                new FromCustomItemToAvailableMethod(escCardIds).map(paymentMethodSearch.getCustomSearchItems()));
        availableMethods.addAll(new FromPaymentMethodToAvailableMethods().map(paymentMethodSearch.getPaymentMethods()));
        this.items = new FromItemToItemInfo().map(items);
    }

    @NonNull
    @Override
    public String getViewPath() {
        return PATH_PAYMENT_VAULT;
    }

    @NonNull
    @Override
    public Map<String, Object> getData() {
        return new SelectMethodData(availableMethods, items).toMap();
    }
}

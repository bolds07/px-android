package com.mercadopago.android.px.tracking.internal.views;

import android.support.annotation.NonNull;

public class AppliedDiscountViewTracker extends ViewTracker {

    private static final String PATH_APPLIED_DISCOUNT = BASE_VIEW_PATH + "/payments/select_method/applied_discount";

    @NonNull
    @Override
    public String getViewPath() {
        return PATH_APPLIED_DISCOUNT;
    }
}

package com.mercadopago.android.px.tracking.internal.views;

import android.support.annotation.NonNull;

public class AppliedDiscountOneTapView extends ViewTracker {

    private static final String PATH_EXPRESS_DISCOUNT_VIEW = BASE_VIEW_PATH + "/review/one_tap/applied_discount";

    @NonNull
    @Override
    public String getViewPath() {
        return PATH_EXPRESS_DISCOUNT_VIEW;
    }
}

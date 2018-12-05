package com.mercadopago.android.px.tracking.internal.views;

import android.support.annotation.NonNull;

public class BankDealsViewTracker extends ViewTracker {

    private static final String PATH = BASE_VIEW_PATH + ADD_PAYMENT_METHOD + "/promotions";

    @NonNull
    @Override
    public String getViewPath() {
        return PATH;
    }
}

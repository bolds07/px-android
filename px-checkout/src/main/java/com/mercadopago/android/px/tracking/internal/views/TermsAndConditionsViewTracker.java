package com.mercadopago.android.px.tracking.internal.views;

import android.support.annotation.NonNull;

public class TermsAndConditionsViewTracker extends ViewTracker {

    private static final String PATH = BASE_VIEW_PATH + "/review/traditional/terms_and_conditions";

    @NonNull
    @Override
    public String getViewPath() {
        return PATH;
    }
}

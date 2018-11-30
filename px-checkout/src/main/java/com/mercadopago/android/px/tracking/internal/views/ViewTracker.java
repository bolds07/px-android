package com.mercadopago.android.px.tracking.internal.views;

import android.support.annotation.NonNull;
import com.mercadopago.android.px.tracking.internal.MPTracker;
import java.util.HashMap;
import java.util.Map;

public abstract class ViewTracker {

    /* default */ static final String BASE_VIEW_PATH = "/px_checkout";

    public final void track() {
        MPTracker.getInstance().trackView(getViewPath(), getData());
    }

    @NonNull
    public abstract String getViewPath();

    protected Map<String, Object> empty() {
        return new HashMap<>();
    }

    @NonNull
    public Map<String, Object> getData() {
        return new HashMap<>();
    }
}

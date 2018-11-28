package com.mercadopago.android.px.tracking.internal;

import android.support.annotation.NonNull;
import java.util.HashMap;
import java.util.Map;

public abstract class ViewTracker {

    public final void track() {
        MPTracker.getInstance().trackView(getViewPath(), getEventData());
    }

    @NonNull
    public abstract String getViewPath();

    @NonNull
    public Map<String, Object> getEventData() {
        return new HashMap<>();
    }
}

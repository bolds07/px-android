package com.mercadopago.android.px.tracking.internal.events;

import android.support.annotation.NonNull;
import com.mercadopago.android.px.tracking.internal.MPTracker;
import java.util.HashMap;
import java.util.Map;

public abstract class EventTracker {

    public final void track() {
        MPTracker.getInstance().trackEvent(getEventPath(), getEventData());
    }

    @NonNull
    public abstract String getEventPath();

    @NonNull
    public Map<String, Object> getEventData() {
        return new HashMap<>();
    }
}

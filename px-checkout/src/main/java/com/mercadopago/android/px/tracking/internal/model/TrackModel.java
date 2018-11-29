package com.mercadopago.android.px.tracking.internal.model;

import android.support.annotation.NonNull;
import com.mercadopago.android.px.internal.util.JsonUtil;
import java.util.Map;

public class TrackModel {

    @NonNull
    public Map<String, Object> transform() {
        return transform(this);
    }

    public static Map<String, Object> transform(final Object object) {
        return JsonUtil.getInstance().getMapFromObject(object);
    }
}

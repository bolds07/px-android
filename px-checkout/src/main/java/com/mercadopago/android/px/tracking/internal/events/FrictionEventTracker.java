package com.mercadopago.android.px.tracking.internal.events;

import android.support.annotation.NonNull;
import com.mercadopago.android.px.model.exceptions.MercadoPagoError;
import com.mercadopago.android.px.tracking.internal.model.ApiErrorData;
import java.util.HashMap;
import java.util.Map;

public class FrictionEventTracker extends EventTracker {

    public static final String PATH = "/friction";
    private static final String ATTR_PATH = "path";
    private static final String ATTR_ATTRIBUTABLE = "attributable_to";
    private static final String VALUE_ATTRIBUTABLE = "mercadopago";
    private static final String ATTR_EXTRA_INFO = "extra_info";

    @NonNull private final String path;
    @NonNull private final Id fId;
    @NonNull private final Style style;
    @NonNull private final Map<Object, Object> extraInfo;

    public enum Id {

        INVALID_CC_NUMBER("invalid_cc_number"),
        GENERIC("px_generic_error");

        private static final String ATTR = "id";

        /* default */ final String value;

        /* default */ Id(final String value) {
            this.value = value;
        }
    }

    public enum Style {
        SNACKBAR("snackbar"),
        SCREEN("screen"),
        CUSTOM_COMPONENT("custom_component");

        private static final String ATTR = "style";

        /* default */ final String value;

        /* default */ Style(final String value) {
            this.value = value;
        }
    }

    public FrictionEventTracker(@NonNull final String path,
        @NonNull final Id fId,
        @NonNull final Style style) {
        this.path = path;
        this.fId = fId;
        this.style = style;
        extraInfo = new HashMap<>();
    }

    public FrictionEventTracker(@NonNull final String path,
        @NonNull final Id fId,
        @NonNull final Style style,
        @NonNull final MercadoPagoError mercadoPagoError) {
        this(path, fId, style);
        extraInfo.put("api_error", new ApiErrorData(mercadoPagoError).toMap());
    }

    @NonNull
    @Override
    public Map<String, Object> getEventData() {
        final Map<String, Object> eventData = super.getEventData();
        eventData.put(Id.ATTR, fId.value);
        eventData.put(Style.ATTR, style.value);
        eventData.put(ATTR_PATH, path);
        eventData.put(ATTR_ATTRIBUTABLE, VALUE_ATTRIBUTABLE);
        eventData.put(ATTR_EXTRA_INFO, extraInfo);
        return eventData;
    }

    @NonNull
    @Override
    public String getEventPath() {
        return PATH;
    }
}

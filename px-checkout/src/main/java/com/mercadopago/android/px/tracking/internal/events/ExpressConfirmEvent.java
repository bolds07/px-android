package com.mercadopago.android.px.tracking.internal.events;

import android.support.annotation.NonNull;
import com.mercadopago.android.px.model.ExpressMetadata;
import com.mercadopago.android.px.tracking.internal.mapper.FromSelectedExpressMetadataToAvailableMethods;
import com.mercadopago.android.px.tracking.internal.model.ExpressConfirmEventData;
import java.util.Map;
import java.util.Set;

public class ExpressConfirmEvent extends EventTracker {

    private static final String EVENT_PATH_REVIEW_CONFIRM = "/px_checkout/review/confirm";

    private final Map<String, Object> data;

    public ExpressConfirmEvent(@NonNull final Set<String> cardsWithEsc, @NonNull final ExpressMetadata expressMetadata,
        final int selectedPayerCost) {
        data = new ExpressConfirmEventData(new FromSelectedExpressMetadataToAvailableMethods(cardsWithEsc,
            selectedPayerCost).map(expressMetadata))
            .toMap();
    }

    @NonNull
    @Override
    public Map<String, Object> getEventData() {
        return data;
    }

    @NonNull
    @Override
    public String getEventPath() {
        return EVENT_PATH_REVIEW_CONFIRM;
    }
}

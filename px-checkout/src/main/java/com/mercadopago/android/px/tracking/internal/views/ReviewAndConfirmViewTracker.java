package com.mercadopago.android.px.tracking.internal.views;

import android.support.annotation.NonNull;
import com.mercadopago.android.px.internal.repository.PaymentSettingRepository;
import com.mercadopago.android.px.internal.repository.UserSelectionRepository;
import com.mercadopago.android.px.tracking.internal.mapper.FromItemToItemInfo;
import com.mercadopago.android.px.tracking.internal.mapper.FromUserSelectionToAvailableMethod;
import com.mercadopago.android.px.tracking.internal.model.ReviewAndConfirmData;
import java.util.Map;
import java.util.Set;

public class ReviewAndConfirmViewTracker extends ViewTracker {

    private static final String PATH = BASE_VIEW_PATH + "/review/traditional";

    private final Set<String> escCardIds;
    @NonNull private final UserSelectionRepository userSelectionRepository;
    @NonNull private final PaymentSettingRepository paymentSettings;

    public ReviewAndConfirmViewTracker(final Set<String> escCardIds,
        @NonNull final UserSelectionRepository userSelectionRepository,
        @NonNull final PaymentSettingRepository paymentSettings) {
        this.escCardIds = escCardIds;
        this.userSelectionRepository = userSelectionRepository;
        this.paymentSettings = paymentSettings;
    }

    @NonNull
    @Override
    public Map<String, Object> getData() {
        // given scenarios of recovery failure this protection is needed.
        try {
            return new ReviewAndConfirmData(new FromUserSelectionToAvailableMethod(escCardIds)
                .map(userSelectionRepository),
                new FromItemToItemInfo()
                    .map(paymentSettings.getCheckoutPreference().getItems()),
                paymentSettings.getCheckoutPreference()
                    .getTotalAmount())
                .toMap();
        } catch (final Exception e) {
            return super.getData();
        }
    }

    @NonNull
    @Override
    public String getViewPath() {
        return PATH;
    }
}

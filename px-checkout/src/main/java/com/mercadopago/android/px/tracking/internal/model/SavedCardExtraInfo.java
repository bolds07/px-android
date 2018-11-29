package com.mercadopago.android.px.tracking.internal.model;

import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

@SuppressWarnings("unused")
@Keep
public class SavedCardExtraInfo extends TrackModel {

    @NonNull private final String cardId;
    private final boolean hasEsc;
    @Nullable private final PayerCostTrackModel selectedInstallment;
    private final String issuerId;

    public SavedCardExtraInfo(@NonNull final String cardId, final boolean hasEsc,
        @Nullable final String issuerId) {
        this.cardId = cardId;
        this.hasEsc = hasEsc;
        selectedInstallment = null;
        this.issuerId = issuerId;
    }

    public SavedCardExtraInfo(@NonNull final String cardId, final boolean hasEsc,
        @Nullable final String issuerId,
        @Nullable final PayerCostTrackModel payerCostTrackModel) {
        this.cardId = cardId;
        this.hasEsc = hasEsc;
        selectedInstallment = payerCostTrackModel;
        this.issuerId = issuerId;
    }
}

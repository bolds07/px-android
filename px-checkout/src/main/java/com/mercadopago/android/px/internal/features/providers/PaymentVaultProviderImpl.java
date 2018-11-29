package com.mercadopago.android.px.internal.features.providers;

import android.content.Context;
import android.support.annotation.NonNull;
import com.mercadopago.android.px.R;
import com.mercadopago.android.px.internal.di.Session;
import com.mercadopago.android.px.internal.tracker.Tracker;
import com.mercadopago.android.px.model.PaymentMethodSearchItem;

public class PaymentVaultProviderImpl implements PaymentVaultProvider {

    private final Context context;

    public PaymentVaultProviderImpl(final Context context) {
        this.context = context;
    }

    @Override
    public String getTitle() {
        final String mainVerb = context.getString(Session.getSession(context).getMainVerb());
        return context.getString(R.string.px_title_activity_payment_vault, mainVerb);
    }

    @Override
    public String getAllPaymentTypesExcludedErrorMessage() {
        return context.getString(R.string.px_error_message_excluded_all_payment_type);
    }

    @Override
    public String getInvalidDefaultInstallmentsErrorMessage() {
        return context.getString(R.string.px_error_message_invalid_default_installments);
    }

    @Override
    public String getInvalidMaxInstallmentsErrorMessage() {
        return context.getString(R.string.px_error_message_invalid_max_installments);
    }

    @Override
    public String getStandardErrorMessage() {
        return context.getString(R.string.px_standard_error_message);
    }

    @Override
    public String getEmptyPaymentMethodsErrorMessage() {
        return context.getString(R.string.px_no_payment_methods_found);
    }

    public void trackChildrenScreen(@NonNull final PaymentMethodSearchItem paymentMethodSearchItem,
        @NonNull final String siteId) {
        Tracker.trackPaymentVaultChildrenScreen(context, paymentMethodSearchItem);
    }
}

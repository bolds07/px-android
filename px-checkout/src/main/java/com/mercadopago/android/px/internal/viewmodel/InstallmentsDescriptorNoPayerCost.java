package com.mercadopago.android.px.internal.viewmodel;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.widget.TextView;
import com.mercadopago.android.px.R;
import com.mercadopago.android.px.internal.repository.PaymentSettingRepository;
import com.mercadopago.android.px.internal.util.textformatter.CurrencyFormatter;
import com.mercadopago.android.px.internal.util.textformatter.InstallmentFormatter;
import com.mercadopago.android.px.internal.util.textformatter.TextFormatter;
import com.mercadopago.android.px.internal.view.InstallmentsDescriptorView;
import com.mercadopago.android.px.model.CardMetadata;
import com.mercadopago.android.px.model.PayerCost;
import com.mercadopago.android.px.preferences.CheckoutPreference;
import java.util.List;

/**
 * Model used to instantiate InstallmentsDescriptorView
 * For payment methods without payer costs: debit_card, account_money, prepaid_card
 */
public final class InstallmentsDescriptorNoPayerCost extends InstallmentsDescriptorView.Model {

    @NonNull
    public static InstallmentsDescriptorView.Model createFrom(@NonNull final PaymentSettingRepository configuration,
        @Nullable final CardMetadata card) {
        final CheckoutPreference checkoutPreference = configuration.getCheckoutPreference();
        final String currencyId = checkoutPreference.getSite().getCurrencyId();
        if (card == null) {
            return new InstallmentsDescriptorNoPayerCost(currencyId, null);
        } else {
            return new InstallmentsDescriptorNoPayerCost(currencyId, card.getPayerCosts(),
                card.getDefaultPayerCostIndex());
        }
    }

    private InstallmentsDescriptorNoPayerCost(@NonNull final String currencyId,
        @Nullable final List<PayerCost> payerCostList) {
        super(currencyId, payerCostList);
    }

    private InstallmentsDescriptorNoPayerCost(@NonNull final String currencyId,
        @NonNull final List<PayerCost> payerCostList, final int currentPayerCost) {
        super(currencyId, payerCostList, currentPayerCost);
    }

    @Override
    public void updateInstallmentsDescriptionSpannable(@NonNull final SpannableStringBuilder spannableStringBuilder,
        @NonNull final Context context, @NonNull final TextView textView) {

        final CurrencyFormatter currencyFormatter = TextFormatter.withCurrencyId(getCurrencyId());
        final Spannable amount = currencyFormatter.amount(getCurrentPayerCost().getInstallmentAmount())
            .normalDecimals()
            .into(textView)
            .toSpannable();

        final InstallmentFormatter installmentFormatter = new InstallmentFormatter(spannableStringBuilder, context)
            .withTextColor(ContextCompat.getColor(context, R.color.ui_meli_black))
            .withSemiBoldStyle();
        installmentFormatter.build(amount);
    }
}

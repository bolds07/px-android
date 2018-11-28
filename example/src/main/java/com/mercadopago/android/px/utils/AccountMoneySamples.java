package com.mercadopago.android.px.utils;

import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import com.mercadopago.android.px.configuration.PaymentConfiguration;
import com.mercadopago.android.px.core.MercadoPagoCheckout;
import com.mercadopago.android.px.core.PaymentProcessor;
import com.mercadopago.android.px.internal.features.plugins.SamplePaymentProcessorNoView;
import com.mercadopago.android.px.model.GenericPayment;
import com.mercadopago.android.px.model.Item;
import com.mercadopago.android.px.model.Payment;
import com.mercadopago.android.px.model.Sites;
import com.mercadopago.android.px.preferences.CheckoutPreference;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class AccountMoneySamples {

    private static final String ACCOUNT_MONEY_PUBLIC_KEY = "TEST-4763b824-93d7-4ca2-a7f7-93539c3ee5bd";
    private static final String PAYER_EMAIL_DUMMY = "prueba@gmail.com";

    private AccountMoneySamples() {

    }

    public static void addAll(final Collection<Pair<String, MercadoPagoCheckout.Builder>> options) {
        options.add(new Pair<>("Account money without plugin", startCheckoutWithoutAccountMoneyPlugin()));
    }

    private static MercadoPagoCheckout.Builder startCheckoutWithoutAccountMoneyPlugin() {
        final GenericPayment payment = new GenericPayment(123L, Payment.StatusCodes.STATUS_APPROVED,
            Payment.StatusDetail.STATUS_DETAIL_ACCREDITED);
        final PaymentProcessor paymentProcessor = new SamplePaymentProcessorNoView(payment);
        final PaymentConfiguration paymentConfiguration = new PaymentConfiguration.Builder(paymentProcessor).build();

        return new MercadoPagoCheckout.Builder(ACCOUNT_MONEY_PUBLIC_KEY,
            getCheckoutPreferenceWithPayerEmail(100), paymentConfiguration);
        //TODO add private key
//            .setPrivateKey(privateKey);
    }

    private static CheckoutPreference getCheckoutPreferenceWithPayerEmail(final int amount) {
        final List<Item> items = new ArrayList<>();
        final Item item = new Item.Builder("Android", 1, new BigDecimal(amount))
            .setDescription("Androide")
            .setPictureUrl("https://www.androidsis.com/wp-content/uploads/2015/08/marshmallow.png")
            .setId("1234")
            .build();
        items.add(item);
        return new CheckoutPreference.Builder(Sites.ARGENTINA,
            PAYER_EMAIL_DUMMY, items)
            .build();
    }
}

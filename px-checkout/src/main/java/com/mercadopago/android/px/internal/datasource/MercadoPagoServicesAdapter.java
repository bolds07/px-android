package com.mercadopago.android.px.internal.datasource;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.gson.reflect.TypeToken;
import com.mercadopago.android.px.internal.util.JsonUtil;
import com.mercadopago.android.px.model.Payment;
import com.mercadopago.android.px.model.PaymentBody;
import com.mercadopago.android.px.model.SummaryAmount;
import com.mercadopago.android.px.model.SummaryAmountBody;
import com.mercadopago.android.px.services.Callback;
import com.mercadopago.android.px.services.MercadoPagoServices;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class MercadoPagoServicesAdapter extends MercadoPagoServices {

    public MercadoPagoServicesAdapter(@NonNull final Context mContext, @NonNull final String mPublicKey) {
        this(mContext, mPublicKey, null);
    }

    public MercadoPagoServicesAdapter(@NonNull final Context mContext,
        @NonNull final String mPublicKey,
        @Nullable final String mPrivateKey) {
        super(mContext, mPublicKey, mPrivateKey);
    }

    public void createPayment(final PaymentBody paymentBody, final Callback<Payment> callback) {

        final Map<String, Object> payload = convertBodyToMap(paymentBody);

        payload.put("issuer_id", paymentBody.getIssuerId());
        payload.put("installments", paymentBody.getInstallments());
        payload.put("campaign_id", paymentBody.getCampaignId());

        createPayment(paymentBody.getTransactionId(),
            payload,
            new HashMap<String, String>(),
            callback);
    }

    public void createSummaryAmount(final SummaryAmountBody summaryAmountBody,
        final Callback<SummaryAmount> callback) {
        final Map<String, Object> payload = convertBodyToMap(summaryAmountBody);

        payload.put("site_id", summaryAmountBody.getSiteId());
        payload.put("transaction_amount", summaryAmountBody.getTransactionAmount());
        payload.put("marketplace", summaryAmountBody.getMarketplace());
        payload.put("email", summaryAmountBody.getEmail());
        payload.put("flow", summaryAmountBody.getFlow());
        payload.put("payment_method_id", summaryAmountBody.getPaymentMethodId());
        payload.put("payment_type", summaryAmountBody.getPaymentType());
        payload.put("bin", summaryAmountBody.getBin());
        payload.put("issuer_id", summaryAmountBody.getIssuerId());
        payload.put("default_installments", summaryAmountBody.getDefaultInstallments());
        payload.put("differential_pricing_id", summaryAmountBody.getDifferentialPricingId());
        payload.put("processing_mode", summaryAmountBody.getProcessingMode());

        createSummaryAmount(payload, callback);
    }

    private Map<String, Object> convertBodyToMap(@NonNull final Object body) {
        final Type type = new TypeToken<Map<String, Object>>() {
        }.getType();

        return JsonUtil.getInstance()
            .getGson()
            .fromJson(JsonUtil.getInstance().toJson(body), type);
    }
}

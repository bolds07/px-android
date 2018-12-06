package com.mercadopago.android.px.internal.datasource;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.mercadopago.android.px.internal.util.JsonUtil;
import com.mercadopago.android.px.model.Campaign;
import com.mercadopago.android.px.model.Discount;
import org.json.JSONException;
import org.json.JSONObject;

public class DiscountStorageService {

    private static final String PREF_CAMPAIGN = "pref_campaign";
    private static final String PREF_DISCOUNT = "pref_discount";
    private static final String PREF_CAMPAIGNS = "pref_campaigns";
    private static final String PREF_EXTRA_DATA = "pref_extra_data";
    private static final String PREF_FLOW = "pref_flow";
    private static final String PREF_NOT_AVAILABLE_DISCOUNT = "pref_not_available_discount";

    @NonNull
    private final SharedPreferences sharedPreferences;
    @NonNull
    private final JsonUtil jsonUtil;

    public DiscountStorageService(@NonNull final SharedPreferences sharedPreferences,
        @NonNull final JsonUtil jsonUtil) {
        this.sharedPreferences = sharedPreferences;
        this.jsonUtil = jsonUtil;
    }

    public void configureDiscountManually(@Nullable final Discount discount, @Nullable final Campaign campaign,
        final boolean notAvailableDiscount) {
        configure(campaign);
        configure(discount);
        configure(notAvailableDiscount);
    }

    public void configureExtraData(@Nullable final JSONObject extraData, @Nullable final String flow) {
        configure(extraData);
        configure(flow);
    }

    public void reset() {
        sharedPreferences.edit().remove(PREF_CAMPAIGNS).apply();
        sharedPreferences.edit().remove(PREF_CAMPAIGN).apply();
        sharedPreferences.edit().remove(PREF_DISCOUNT).apply();
        sharedPreferences.edit().remove(PREF_NOT_AVAILABLE_DISCOUNT).apply();
    }

    @Nullable
    public Discount getDiscount() {
        return jsonUtil.fromJson(sharedPreferences.getString(PREF_DISCOUNT, ""), Discount.class);
    }

    @Nullable
    public Campaign getCampaign() {
        return jsonUtil.fromJson(sharedPreferences.getString(PREF_CAMPAIGN, ""), Campaign.class);
    }

    @Nullable
    public JSONObject getExtraData() {
        final String json = sharedPreferences.getString(PREF_EXTRA_DATA, null);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Nullable
    public String getFlow() {
        return sharedPreferences.getString(PREF_FLOW, "");
    }

    public boolean isNotAvailableDiscount() {
        return sharedPreferences.getBoolean(PREF_NOT_AVAILABLE_DISCOUNT, false);
    }

    private void configure(@Nullable final Discount discount) {
        if (discount == null) {
            sharedPreferences.edit().remove(PREF_DISCOUNT).apply();
        } else {
            final SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putString(PREF_DISCOUNT, jsonUtil.toJson(discount));
            edit.apply();
        }
    }

    private void configure(@Nullable final Campaign campaign) {
        if (campaign == null) {
            sharedPreferences.edit().remove(PREF_CAMPAIGN).apply();
        } else {
            final SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putString(PREF_CAMPAIGN, jsonUtil.toJson(campaign));
            edit.apply();
        }
    }

    private void configure(final boolean notAvailableDiscount) {
        final SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean(PREF_NOT_AVAILABLE_DISCOUNT, notAvailableDiscount);
        edit.apply();
    }

    private void configure(@Nullable final JSONObject extraData) {
        if (extraData == null) {
            sharedPreferences.edit().remove(PREF_EXTRA_DATA).apply();
        } else {
            final SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putString(PREF_EXTRA_DATA, extraData.toString());
            edit.apply();
        }
    }

    private void configure(@Nullable final String flow) {
        if (flow == null) {
            sharedPreferences.edit().remove(PREF_FLOW).apply();
        } else {
            final SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putString(PREF_FLOW, flow);
            edit.apply();
        }
    }
}

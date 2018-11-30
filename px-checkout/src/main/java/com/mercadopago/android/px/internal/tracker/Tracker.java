package com.mercadopago.android.px.internal.tracker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import com.mercadopago.android.px.internal.features.review_and_confirm.models.PaymentModel;
import com.mercadopago.android.px.internal.features.review_and_confirm.models.SummaryModel;
import com.mercadopago.android.px.internal.util.JsonUtil;
import com.mercadopago.android.px.model.ActionEvent;
import com.mercadopago.android.px.model.ExpressMetadata;
import com.mercadopago.android.px.model.PaymentMethodSearchItem;
import com.mercadopago.android.px.model.PaymentResult;
import com.mercadopago.android.px.model.PaymentTypes;
import com.mercadopago.android.px.model.ScreenViewEvent;
import com.mercadopago.android.px.model.exceptions.MercadoPagoError;
import com.mercadopago.android.px.tracking.internal.MPTracker;
import com.mercadopago.android.px.tracking.internal.model.ErrorView;
import com.mercadopago.android.px.tracking.internal.model.ExpressInstallmentsView;
import com.mercadopago.android.px.tracking.internal.utils.TrackingUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class Tracker {

    private Tracker() {
    }

    public static void trackSwipeExpress() {
        MPTracker.getInstance()
            .trackEvent(TrackingUtil.Event.EVENT_PATH_SWIPE_EXPRESS, new HashMap<String, Object>());
    }

    public static void trackAbortExpress() {
        MPTracker.getInstance()
            .trackEvent(TrackingUtil.Event.EVENT_PATH_ABORT_EXPRESS, new HashMap<String, Object>());
    }

    public static void trackExpressInstallmentsView(@NonNull final ExpressMetadata expressMetadata,
        @NonNull final String currencyId, @NonNull final BigDecimal totalAmount) {
        final ExpressInstallmentsView expressInstallmentsView =
            ExpressInstallmentsView.createFrom(expressMetadata, currencyId, totalAmount);
        final Map<String, Object> data = JsonUtil.getInstance().getMapFromObject(expressInstallmentsView);
        MPTracker.getInstance().trackView(TrackingUtil.View.PATH_EXPRESS_INSTALLMENTS_VIEW, data);
    }

    public static void trackGenericError(@Nullable final String path, @NonNull final ErrorView.ErrorType errorType,
        @NonNull final MercadoPagoError mercadoPagoError, @NonNull final String visibleMessage) {
        final ErrorView errorView = ErrorView.createFrom(path, mercadoPagoError, errorType, visibleMessage);
        final Map<String, Object> data = JsonUtil.getInstance().getMapFromObject(errorView);
        MPTracker.getInstance().trackEvent(TrackingUtil.Event.EVENT_PATH_FRICTION, data);
    }

    private static void addProperties(final ScreenViewEvent.Builder builder,
        @Nullable final Iterable<Pair<String, String>> propertyList) {
        if (propertyList != null) {
            for (final Pair<String, String> property : propertyList) {
                builder.addProperty(property.first, property.second);
            }
        }
    }

    public static void trackScreen(final String screenId,
        final String screenName) {

        trackScreen(screenId, screenName, new ArrayList<Pair<String, String>>());
    }

    public static void trackScreen(final String screenId,
        final String screenName,
        @Nullable final Iterable<Pair<String, String>> properties) {

        final MPTracker mpTrackingContext = MPTracker.getInstance();

        final ScreenViewEvent.Builder builder = new ScreenViewEvent.Builder()
            .setFlowId(FlowHandler.getInstance().getFlowId())
            .setScreenId(screenId)
            .setScreenName(screenName);

        addProperties(builder, properties);
        final ScreenViewEvent event = builder.build();
        mpTrackingContext.trackEvent(event);
    }

    public static void trackReviewAndConfirmScreen(final Context context,
        final PaymentModel paymentModel) {

        final Collection<Pair<String, String>> properties = new ArrayList<>();
        properties.add(new Pair<>(TrackingUtil.PROPERTY_SHIPPING_INFO, TrackingUtil.HAS_SHIPPING_DEFAULT_VALUE));
        properties.add(new Pair<>(TrackingUtil.PROPERTY_PAYMENT_TYPE_ID, paymentModel.getPaymentType()));
        properties.add(new Pair<>(TrackingUtil.PROPERTY_PAYMENT_METHOD_ID, paymentModel.paymentMethodId));
        properties.add(new Pair<>(TrackingUtil.PROPERTY_ISSUER_ID, String.valueOf(paymentModel.issuerId)));

        trackScreen(TrackingUtil.View.PATH_REVIEW_AND_CONFIRM,
            TrackingUtil.View.PATH_REVIEW_AND_CONFIRM,
            properties);
    }

    public static void trackReviewAndConfirmTermsAndConditions() {

        final MPTracker mpTrackingContext = MPTracker.getInstance();

        final ScreenViewEvent.Builder builder = new ScreenViewEvent.Builder()
            .setFlowId(FlowHandler.getInstance().getFlowId())
            .setScreenId(TrackingUtil.View.PATH_REVIEW_TERMS_AND_CONDITIONS)
            .setScreenName(TrackingUtil.View.PATH_REVIEW_TERMS_AND_CONDITIONS);

        mpTrackingContext.trackEvent(builder.build());
    }

    public static void trackCheckoutConfirm(
        final PaymentModel paymentModel,
        final SummaryModel summaryModel) {

        final MPTracker mpTrackingContext = MPTracker.getInstance();

        final ActionEvent.Builder builder = new ActionEvent.Builder()
            .setFlowId(FlowHandler.getInstance().getFlowId())
            .setAction(TrackingUtil.ACTION_CHECKOUT_CONFIRMED)
            .setScreenId(TrackingUtil.View.PATH_REVIEW_AND_CONFIRM)
            .setScreenName(TrackingUtil.View.PATH_REVIEW_AND_CONFIRM)
            .addProperty(TrackingUtil.PROPERTY_PAYMENT_TYPE_ID, paymentModel.getPaymentType())
            .addProperty(TrackingUtil.PROPERTY_PAYMENT_METHOD_ID, paymentModel.paymentMethodId)
            .addProperty(TrackingUtil.PROPERTY_PURCHASE_AMOUNT, summaryModel.getAmountToPay().toString());

        if (PaymentTypes.isCreditCardPaymentType(summaryModel.getPaymentTypeId())) {
            builder.addProperty(TrackingUtil.PROPERTY_INSTALLMENTS, String.valueOf(summaryModel.getInstallments()));
        }

        //If is saved card
        final String cardId = paymentModel.getCardId();
        if (cardId != null) {
            builder.addProperty(TrackingUtil.PROPERTY_CARD_ID, cardId);
        }

        final ActionEvent actionEvent = builder.build();
        mpTrackingContext.trackEvent(actionEvent);
    }

    public static void trackPaymentVaultChildrenScreen(@NonNull final Context context,
        @NonNull final PaymentMethodSearchItem selectedItem) {

        final String selectedItemId = selectedItem.getId();

        if (TrackingUtil.GROUP_CARDS.equals(selectedItemId)) {
            trackScreen(TrackingUtil.View.PATH_PAYMENT_VAULT_CARDS,
                TrackingUtil.View.PATH_PAYMENT_VAULT_CARDS,
                null);
        } else {
            trackScreen(TrackingUtil.View.PATH_PAYMENT_VAULT_TICKET,
                TrackingUtil.View.PATH_PAYMENT_VAULT_TICKET,
                null);
        }
    }

    public static void trackBusinessPaymentResultScreen(@NonNull final String paymentStatus,
        @NonNull final String paymentStatusDetail) {
        final PaymentResult paymentResult =
            new PaymentResult.Builder()
                .setPaymentStatus(paymentStatus)
                .setPaymentStatusDetail(paymentStatusDetail)
                .build();
        final String screenId = getScreenIdByPaymentResult(paymentResult);

        trackScreen(screenId, screenId);
    }

    @NonNull
    public static String getScreenIdByPaymentResult(@NonNull final PaymentResult paymentResult) {
        if (paymentResult.isApproved() || paymentResult.isInstructions()) {
            return TrackingUtil.View.PATH_PAYMENT_RESULT_APPROVED;
        } else if (paymentResult.isRejected()) {
            return TrackingUtil.View.PATH_PAYMENT_RESULT_REJECTED;
        } else if (paymentResult.isPending()) {
            return TrackingUtil.View.PATH_PAYMENT_RESULT_PENDING;
        }
        return "";
    }
}

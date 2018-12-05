package com.mercadopago.android.px.internal.tracker;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import com.mercadopago.android.px.internal.util.JsonUtil;
import com.mercadopago.android.px.model.ExpressMetadata;
import com.mercadopago.android.px.model.PaymentResult;
import com.mercadopago.android.px.model.ScreenViewEvent;
import com.mercadopago.android.px.model.exceptions.MercadoPagoError;
import com.mercadopago.android.px.tracking.internal.MPTracker;
import com.mercadopago.android.px.tracking.internal.model.ErrorView;
import com.mercadopago.android.px.tracking.internal.model.ExpressInstallmentsView;
import com.mercadopago.android.px.tracking.internal.utils.TrackingUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
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

    public static void trackReviewAndConfirmTermsAndConditions() {

        final MPTracker mpTrackingContext = MPTracker.getInstance();

        final ScreenViewEvent.Builder builder = new ScreenViewEvent.Builder()
            .setFlowId(FlowHandler.getInstance().getFlowId())
            .setScreenId(TrackingUtil.View.PATH_REVIEW_TERMS_AND_CONDITIONS)
            .setScreenName(TrackingUtil.View.PATH_REVIEW_TERMS_AND_CONDITIONS);

        mpTrackingContext.trackEvent(builder.build());
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

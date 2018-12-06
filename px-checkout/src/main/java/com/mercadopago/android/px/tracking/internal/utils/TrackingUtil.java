package com.mercadopago.android.px.tracking.internal.utils;

public final class TrackingUtil {

    public static final class View {
        //View Paths
        public static final String PATH_BOLBRADESCO_CPF = "/px_checkout/select_method/ticket/cpf";
        public static final String PATH_BOLBRADESCO_NAME = "/px_checkout/select_method/ticket/name";
        public static final String PATH_BOLBRADESCO_LASTNAME = "/px_checkout/select_method/ticket/lastname";
        public static final String PATH_EXPRESS_INSTALLMENTS_VIEW = "/px_checkout/review/one_tap/installments";


        private View() {
        }
    }

    public static final class Event {
        //Event Paths
        public static final String EVENT_PATH_SWIPE_EXPRESS = "/px_checkout/review/one_tap/swipe";
        public static final String EVENT_PATH_ABORT_EXPRESS = "/px_checkout/review/one_tap/abort";

        private Event() {
        }
    }

    private TrackingUtil() {
    }
}
package com.mercadopago.android.px.tracking.internal.utils;

public final class TrackingUtil {

    public static final class View {
        //View Paths
        public static final String PATH_BOLBRADESCO_CPF = "/px_checkout/select_method/ticket/cpf";
        public static final String PATH_BOLBRADESCO_NAME = "/px_checkout/select_method/ticket/name";
        public static final String PATH_BOLBRADESCO_LASTNAME = "/px_checkout/select_method/ticket/lastname";

        public static final String PATH_ISSUERS = "/px_checkout/payments/card_issuer";
        public static final String PATH_INSTALLMENTS = "/px_checkout/payments/installments";
        public static final String PATH_REVIEW_TERMS_AND_CONDITIONS =
            "/px_checkout/review/traditional/terms_and_conditions";
        public static final String PATH_EXPRESS_CHECKOUT = "/px_checkout/review/one_tap";
        public static final String PATH_EXPRESS_INSTALLMENTS_VIEW = "/px_checkout/review/one_tap/installments";

        private View() {
        }
    }

    public static final class Event {
        //Event Paths

        public static final String EVENT_PATH_SWIPE_EXPRESS = "/px_checkout/review/one_tap/swipe";
        public static final String EVENT_PATH_ABORT_EXPRESS = "/px_checkout/review/one_tap/abort";
        public static final String EVENT_PATH_FRICTION = "/friction";
        public static final String EVENT_PATH_GENERIC_ERROR = "/px_checkout/generic_error";
        public static final String EVENT_PATH_GENERIC_ERROR_ID = "px_generic_error";
        public static final String EVENT_PATH_GENERIC_ERROR_ATTRIBUTABLE_TO = "mercadopago";

        private Event() {
        }
    }

    //Card Association result screen
    public static final String SCREEN_ID_CARD_ASSOCIATION_SUCCESS = "/px_checkout/card_association_result/success";
    public static final String SCREEN_ID_CARD_ASSOCIATION_ERROR = "/px_checkout/card_association_result/error";

    //Security Code Reason
    public static final String SECURITY_CODE_REASON_CALL = "call_for_auth";
    public static final String SECURITY_CODE_REASON_SAVED_CARD = "saved_card";
    public static final String SECURITY_CODE_REASON_ESC = "invalid_esc";

    //Additional Info Keys
    public static final String PROPERTY_PAYMENT_METHOD_ID = "payment_method_id";
    public static final String PROPERTY_PAYMENT_TYPE_ID = "payment_method_type";
    public static final String PROPERTY_ISSUER_ID = "issuer_id";
    public static final String PROPERTY_PAYMENT_STATUS = "payment_status";
    public static final String PROPERTY_PAYMENT_ID = "payment_id";
    public static final String PROPERTY_PAYMENT_STATUS_DETAIL = "payment_status_detail";
    public static final String PROPERTY_PAYMENT_IS_EXPRESS = "is_express";
    public static final String PROPERTY_ERROR_STATUS = "error_status";
    public static final String PROPERTY_ERROR_CODE = "error_code";
    public static final String PROPERTY_ERROR_REQUEST = "error_request_origin";
    public static final String PROPERTY_ERROR_MESSAGE = "error_message";

    public static final String IS_EXPRESS_DEFAULT_VALUE = "false";

    private TrackingUtil() {
    }
}
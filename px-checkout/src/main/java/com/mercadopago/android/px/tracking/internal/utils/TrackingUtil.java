package com.mercadopago.android.px.tracking.internal.utils;

public final class TrackingUtil {

    public static final class View {
        //View Paths
        public static final String PATH_BOLBRADESCO_CPF = "/px_checkout/select_method/ticket/cpf";
        public static final String PATH_BOLBRADESCO_NAME = "/px_checkout/select_method/ticket/name";
        public static final String PATH_BOLBRADESCO_LASTNAME = "/px_checkout/select_method/ticket/lastname";

        public static final String PATH_ISSUERS = "/px_checkout/payments/card_issuer";
        public static final String PATH_INSTALLMENTS = "/px_checkout/payments/installments";
        public static final String PATH_PROMOTIONS = "/px_checkout/add_payment_method/promotions";
        public static final String PATH_PROMOTIONS_TERMS_AND_CONDITIONS =
            "/px_checkout/add_payment_method/promotions/terms_and_conditions";
        public static final String PATH_EXCLUDED_CARD =
            "/px_checkout/add_payment_method/credit_card/number/error_more_info";
        public static final String PATH_REVIEW_AND_CONFIRM = "/px_checkout/review/traditional";
        public static final String PATH_REVIEW_TERMS_AND_CONDITIONS =
            "/px_checkout/review/traditional/terms_and_conditions";
        public static final String PATH_EXPRESS_CHECKOUT = "/px_checkout/review/one_tap";
        public static final String PATH_EXPRESS_INSTALLMENTS_VIEW = "/px_checkout/review/one_tap/installments";
        public static final String PATH_PAYMENT_RESULT_APPROVED = "/px_checkout/result/success";
        public static final String PATH_PAYMENT_RESULT_PENDING = "/px_checkout/result/further_action_needed";
        public static final String PATH_PAYMENT_RESULT_REJECTED = "/px_checkout/result/error";

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

    //Action IDs
    public static final String ACTION_CHECKOUT_CONFIRMED = "/checkout_confirmed";

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
    public static final String PROPERTY_SHIPPING_INFO = "has_shipping";
    public static final String PROPERTY_PAYMENT_STATUS = "payment_status";
    public static final String PROPERTY_PAYMENT_ID = "payment_id";
    public static final String PROPERTY_PAYMENT_STATUS_DETAIL = "payment_status_detail";
    public static final String PROPERTY_PAYMENT_IS_EXPRESS = "is_express";
    public static final String PROPERTY_ERROR_STATUS = "error_status";
    public static final String PROPERTY_ERROR_CODE = "error_code";
    public static final String PROPERTY_ERROR_REQUEST = "error_request_origin";
    public static final String PROPERTY_ERROR_MESSAGE = "error_message";
    public static final String PROPERTY_CARD_ID = "card_id";
    public static final String PROPERTY_INSTALLMENTS = "installments";
    public static final String PROPERTY_PURCHASE_AMOUNT = "purchase_amount";

    //Default values
    public static final String HAS_SHIPPING_DEFAULT_VALUE = "false";
    public static final String IS_EXPRESS_DEFAULT_VALUE = "false";

    private TrackingUtil() {
    }
}
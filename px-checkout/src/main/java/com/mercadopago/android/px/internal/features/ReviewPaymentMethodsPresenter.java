package com.mercadopago.android.px.internal.features;

import com.mercadopago.android.px.internal.base.MvpPresenter;
import com.mercadopago.android.px.internal.features.providers.ReviewPaymentMethodsProvider;
import com.mercadopago.android.px.model.PaymentMethod;
import com.mercadopago.android.px.tracking.internal.views.ErrorMoreInfoCardViewTracker;
import java.util.List;

public class ReviewPaymentMethodsPresenter
    extends MvpPresenter<ReviewPaymentMethodsView, ReviewPaymentMethodsProvider> {

    private final List<PaymentMethod> supportedPaymentMethods;

    public ReviewPaymentMethodsPresenter(final List<PaymentMethod> supportedPaymentMethods) {
        this.supportedPaymentMethods = supportedPaymentMethods;
    }

    public void initialize() {
        new ErrorMoreInfoCardViewTracker(supportedPaymentMethods.get(0).getPaymentTypeId()).track();
        getView().initializeSupportedPaymentMethods(supportedPaymentMethods);
    }
}

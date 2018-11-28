package com.mercadopago.android.px.internal.features.review_and_confirm.components.payment_method;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import com.mercadopago.android.px.internal.features.review_and_confirm.models.PaymentAccountMoneyModel;
import com.mercadopago.android.px.internal.view.CompactComponent;

class MethodAccountMoney extends CompactComponent<MethodAccountMoney.Props, Void> {

    /* default */ static class Props {

        /* default */ private final String paymentMethodName;
        /* default */ private final boolean invested;

        /* default */ Props(final String paymentMethodName, final boolean invested) {
            this.paymentMethodName = paymentMethodName;
            this.invested = invested;
        }

        /* default */
        static Props createFrom(final PaymentAccountMoneyModel props) {
            return new Props(props.getPaymentMethodName(), props.isInvested());
        }
    }

    /* default */ MethodAccountMoney(final Props props) {
        super(props);
    }

    @Override
    public View render(@NonNull final ViewGroup parent) {
        // TODO: render
        return null;
    }
}

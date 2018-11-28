package com.mercadopago.android.px.internal.features.review_and_confirm.components.payment_method;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.view.View;
import android.view.ViewGroup;
import com.mercadopago.android.px.R;
import com.mercadopago.android.px.internal.features.review_and_confirm.models.PaymentAccountMoneyModel;
import com.mercadopago.android.px.internal.features.review_and_confirm.models.PaymentCardModel;
import com.mercadopago.android.px.internal.features.review_and_confirm.models.PaymentMethodOffModel;
import com.mercadopago.android.px.internal.features.review_and_confirm.models.PaymentModel;
import com.mercadopago.android.px.internal.features.review_and_confirm.models.PaymentPluginModel;
import com.mercadopago.android.px.internal.view.Button;
import com.mercadopago.android.px.internal.view.ButtonLink;
import com.mercadopago.android.px.internal.view.CompactComponent;
import com.mercadopago.android.px.model.Action;
import com.mercadopago.android.px.model.PaymentTypes;

public class PaymentMethodComponent extends CompactComponent<PaymentModel, PaymentMethodComponent.Actions> {

    public interface Actions {
        void onPaymentMethodChangeClicked();
    }

    public PaymentMethodComponent(final PaymentModel props, final Actions actions) {
        super(props, actions);
    }

    @VisibleForTesting()
    CompactComponent resolveComponent() {
        if (PaymentTypes.isCardPaymentType(props.getPaymentType())) {
            return new MethodCard(MethodCard.Props.createFrom((PaymentCardModel) props));
        } else if (PaymentTypes.isPlugin(props.getPaymentType())) {
            return new MethodPlugin(MethodPlugin.Props.createFrom((PaymentPluginModel) props));
        } else if (PaymentTypes.isAccountMoney(props.getPaymentType())) {
            return new MethodAccountMoney(MethodAccountMoney.Props.createFrom((PaymentAccountMoneyModel) props));
        } else {
            return new MethodOff(MethodOff.Props.createFrom((PaymentMethodOffModel) props));
        }
    }

    @Override
    public View render(@NonNull final ViewGroup parent) {

        final ViewGroup paymentMethodView = (ViewGroup) resolveComponent().render(parent);

        if (shouldShowPaymentMethodButton()) {
            final String changeLabel = parent.getContext().getString(R.string.px_change_payment);
            final ButtonLink buttonLink = new ButtonLink(new Button.Props(changeLabel, null), new Button.Actions() {
                @Override
                public void onClick(final Action action) {
                    if (getActions() != null) {
                        getActions().onPaymentMethodChangeClicked();
                    }
                }

            });

            compose(paymentMethodView, buttonLink.render(paymentMethodView));
        }

        return paymentMethodView;
    }

    @VisibleForTesting
    boolean shouldShowPaymentMethodButton() { // TODO: we should add this field into the abstract firm
        return false; //return props.hasMoreThanOnePaymentMethod() || PaymentTypes.isCardPaymentType(props.getPaymentType());
    }
}

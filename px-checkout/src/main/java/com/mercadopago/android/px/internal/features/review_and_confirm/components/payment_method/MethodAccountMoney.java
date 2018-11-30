package com.mercadopago.android.px.internal.features.review_and_confirm.components.payment_method;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.mercadopago.android.px.R;
import com.mercadopago.android.px.internal.features.review_and_confirm.models.PaymentModel;
import com.mercadopago.android.px.internal.util.ResourceUtil;
import com.mercadopago.android.px.internal.view.CompactComponent;

class MethodAccountMoney extends CompactComponent<MethodAccountMoney.Props, Void> {

    static final class Props {

        final String paymentMethodId;
        final String title;
        final boolean invested;

        private Props(@NonNull final String paymentMethodId, @NonNull final String title,
            final boolean invested) {
            this.paymentMethodId = paymentMethodId;
            this.title = title;
            this.invested = invested;
        }

        static Props createFrom(final PaymentModel props) {
            return new Props(props.paymentMethodId,
                props.paymentMethodName,
                props.isInvested());
        }
    }

    /* default */ MethodAccountMoney(final Props props) {
        super(props);
    }

    @Override
    public View render(@NonNull final ViewGroup parent) {
        final View paymentView = inflate(parent, R.layout.px_payment_method_account_money);

        final TextView title = paymentView.findViewById(R.id.title);
        title.setText(props.title);

        if (props.invested) {
            final TextView description = paymentView.findViewById(R.id.description);
            description.setText(parent.getResources().getString(R.string.px_invested_account_money));
            description.setVisibility(View.VISIBLE);
        }

        final ImageView imageView = paymentView.findViewById(R.id.icon);
        imageView.setImageResource(ResourceUtil.getIconResource(imageView.getContext(), props.paymentMethodId));

        return paymentView;
    }
}

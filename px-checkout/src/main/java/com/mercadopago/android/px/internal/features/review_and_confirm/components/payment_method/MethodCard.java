package com.mercadopago.android.px.internal.features.review_and_confirm.components.payment_method;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.mercadopago.android.px.R;
import com.mercadopago.android.px.internal.features.review_and_confirm.models.PaymentCardModel;
import com.mercadopago.android.px.internal.util.ResourceUtil;
import com.mercadopago.android.px.internal.util.TextUtil;
import com.mercadopago.android.px.internal.view.CompactComponent;
import java.util.Locale;

class MethodCard extends CompactComponent<MethodCard.Props, Void> {

    static class Props {

        private final String id;
        private final String cardName;
        private final String lastFourDigits;
        private final String bankName;

        Props(final String id,
            final String cardName,
            final String lastFourDigits,
            final String bankName) {
            this.id = id;
            this.cardName = cardName;
            this.lastFourDigits = lastFourDigits;
            this.bankName = bankName;
        }

        static Props createFrom(final PaymentCardModel props) {
            return new Props(props.getPaymentMethodId(),
                props.getPaymentType(),
                props.lastFourDigits,
                props.issuerName);
        }
    }

    MethodCard(final Props props) {
        super(props);
    }

    @Override
    public View render(@NonNull final ViewGroup parent) {

        final View paymentView = inflate(parent, R.layout.px_payment_method_card);

        final TextView title = paymentView.findViewById(R.id.title);
        title.setText(formatTitle(title.getContext()));

        final TextView subtitle = paymentView.findViewById(R.id.subtitle);
        subtitle.setText(props.bankName);

        subtitle.setVisibility(shouldShowSubtitle() ? View.VISIBLE : View.GONE);

        final ImageView imageView = paymentView.findViewById(R.id.icon);
        imageView.setImageResource(ResourceUtil.getIconResource(imageView.getContext(), props.id));

        return paymentView;
    }

    @VisibleForTesting
    boolean shouldShowSubtitle() {
        return TextUtil.isNotEmpty(props.bankName) && !props.bankName.equals(props.cardName);
    }

    private String formatTitle(final Context context) {
        final String ending = context.getString(R.string.px_ending_in);
        return String.format(Locale.getDefault(), "%s %s %s",
            props.cardName,
            ending,
            props.lastFourDigits);
    }
}

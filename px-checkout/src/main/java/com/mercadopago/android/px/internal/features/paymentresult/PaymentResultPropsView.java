package com.mercadopago.android.px.internal.features.paymentresult;

import android.support.annotation.NonNull;
import com.mercadopago.android.px.internal.base.MvpView;
import com.mercadopago.android.px.model.Instruction;
import com.mercadopago.android.px.model.Item;
import com.mercadopago.android.px.model.PaymentResult;
import java.util.List;

public interface PaymentResultPropsView extends MvpView {

    void setPropPaymentResult(@NonNull final String currencyId,
        @NonNull final List<Item> items,
        @NonNull final PaymentResult paymentResult,
        final boolean showLoading);

    void setPropInstruction(@NonNull final Instruction instruction,
        @NonNull final String processingModeString,
        final boolean showLoading);

    void notifyPropsChanged();
}

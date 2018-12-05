package com.mercadopago.android.px.tracking.internal.mapper;

import android.support.annotation.NonNull;
import com.mercadopago.android.px.internal.repository.UserSelectionRepository;
import com.mercadopago.android.px.internal.viewmodel.mappers.Mapper;
import com.mercadopago.android.px.model.Card;
import com.mercadopago.android.px.model.PaymentMethod;
import com.mercadopago.android.px.model.PaymentTypes;
import com.mercadopago.android.px.tracking.internal.model.AvailableMethod;
import com.mercadopago.android.px.tracking.internal.model.PayerCostInfo;
import com.mercadopago.android.px.tracking.internal.model.SavedCardExtraInfo;
import java.util.Set;

public class FromUserSelectionToAvailableMethod extends Mapper<UserSelectionRepository, AvailableMethod> {

    @NonNull private final Set<String> escCardIds;

    public FromUserSelectionToAvailableMethod(@NonNull final Set<String> escCardIds) {
        this.escCardIds = escCardIds;
    }

    @Override
    public AvailableMethod map(@NonNull final UserSelectionRepository val) {
        final PaymentMethod paymentMethod = val.getPaymentMethod();
        final Card card = val.getCard();
        if (PaymentTypes.isCardPaymentType(paymentMethod.getPaymentTypeId()) && card != null) {
            return new AvailableMethod(paymentMethod.getId(), paymentMethod.getPaymentTypeId(),
                new SavedCardExtraInfo(card.getId(), escCardIds.contains(card.getId()),
                    //TODO verify why this model is long.
                    String.valueOf(card.getIssuer().getId()),
                    new PayerCostInfo(val.getPayerCost())).toMap());
        } else if (PaymentTypes.isAccountMoney(paymentMethod.getPaymentTypeId())) {
            //TODO when first member add balance and invested data.
            return new AvailableMethod(paymentMethod.getId(), paymentMethod.getPaymentTypeId());
        } else {
            return new AvailableMethod(paymentMethod.getId(), paymentMethod.getPaymentTypeId());
        }
    }
}

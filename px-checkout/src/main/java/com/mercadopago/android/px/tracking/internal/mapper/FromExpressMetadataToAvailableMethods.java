package com.mercadopago.android.px.tracking.internal.mapper;

import android.support.annotation.NonNull;
import com.mercadopago.android.px.internal.viewmodel.mappers.Mapper;
import com.mercadopago.android.px.model.AccountMoneyMetadata;
import com.mercadopago.android.px.model.CardMetadata;
import com.mercadopago.android.px.model.ExpressMetadata;
import com.mercadopago.android.px.tracking.internal.model.AccountMoneyExtraInfo;
import com.mercadopago.android.px.tracking.internal.model.AvailableMethod;
import com.mercadopago.android.px.tracking.internal.model.SavedCardExtraInfo;
import java.util.Set;

public class FromExpressMetadataToAvailableMethods extends Mapper<ExpressMetadata, AvailableMethod> {

    @NonNull private final Set<String> cardsWithEsc;

    public FromExpressMetadataToAvailableMethods(@NonNull final Set<String> cardsWithEsc) {
        this.cardsWithEsc = cardsWithEsc;
    }

    @Override
    public AvailableMethod map(@NonNull final ExpressMetadata expressMetadata) {
        if (expressMetadata.isCard()) {
            final CardMetadata card = expressMetadata.getCard();
            return new AvailableMethod(expressMetadata.getPaymentMethodId(), expressMetadata.getPaymentTypeId(),
                new SavedCardExtraInfo(card.getId(), cardsWithEsc.contains(card.getId()),
                    //TODO verify why this model is long.
                    String.valueOf(card.getDisplayInfo().issuerId), null));
        } else if (expressMetadata.getAccountMoney() != null) {
            final AccountMoneyMetadata accountMoney = expressMetadata.getAccountMoney();
            return new AvailableMethod(expressMetadata.getPaymentMethodId(), expressMetadata.getPaymentTypeId(),
                new AccountMoneyExtraInfo(accountMoney.getBalance(), accountMoney.isInvested()));
        } else {
            return new AvailableMethod(expressMetadata.getPaymentMethodId(), expressMetadata.getPaymentTypeId());
        }
    }
}

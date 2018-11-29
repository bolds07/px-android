package com.mercadopago.android.px.tracking.internal.mapper;

import android.support.annotation.NonNull;
import com.mercadopago.android.px.internal.viewmodel.mappers.Mapper;
import com.mercadopago.android.px.model.CustomSearchItem;
import com.mercadopago.android.px.model.PaymentTypes;
import com.mercadopago.android.px.tracking.internal.model.AccountMoneyExtraInfo;
import com.mercadopago.android.px.tracking.internal.model.AvailableMethod;
import com.mercadopago.android.px.tracking.internal.model.SavedCardExtraInfo;
import java.util.Set;

public class FromCustomItemToAvailableMethod extends Mapper<CustomSearchItem, AvailableMethod> {

    @NonNull private final Set<String> escCardIds;

    public FromCustomItemToAvailableMethod(@NonNull final Set<String> escCardIds) {
        this.escCardIds = escCardIds;
    }

    @Override
    public AvailableMethod map(@NonNull final CustomSearchItem val) {
        //TODO add issuer info.
        if (PaymentTypes.isAccountMoney(val.getType())) {
            return new AvailableMethod(val.getId(), val.getId(),
                //TODO fix balance tracking when comes first class member data.
                new AccountMoneyExtraInfo(null, false).toMap());
        } else if (PaymentTypes.isCardPaymentType(val.getType())) {
            return new AvailableMethod(val.getPaymentMethodId(), val.getType(),
                new SavedCardExtraInfo(val.getId(),
                    escCardIds.contains(val.getId()), null).toMap());
        } else {
            return new AvailableMethod(val.getId(), val.getType());
        }
    }
}

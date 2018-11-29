package com.mercadopago.android.px.tracking.internal.model;

import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.mercadopago.android.px.model.Campaign;
import com.mercadopago.android.px.model.Discount;
import com.mercadopago.android.px.model.ExpressMetadata;
import com.mercadopago.android.px.model.Item;
import com.mercadopago.android.px.tracking.internal.mapper.FromExpressMetadataToAvailableMethods;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unused")
@Keep
public class OneTapData extends SelectMethodData {

    @Nullable private BigDecimal totalAmount;
    @Nullable private DiscountInfo discount;

    public OneTapData(@NonNull final List<AvailableMethod> availableMethods,
        @NonNull final BigDecimal totalAmount,
        @Nullable final DiscountInfo discount,
        @NonNull final List<ItemInfo> items) {
        super(availableMethods, items);
        this.totalAmount = totalAmount;
        this.discount = discount;
    }

    //TODO refactor with mapper.
    public static OneTapData createFrom(@NonNull final Iterable<ExpressMetadata> expressMetadataList,
        @NonNull final BigDecimal totalAmount,
        @Nullable final Discount discount,
        @Nullable final Campaign campaign,
        @NonNull final Iterable<Item> items) {
        DiscountInfo discountInfo = null;
        //A discount always comes with a campaign
        if (discount != null && campaign != null) {
            if (discount.hasPercentOff()) {
                discountInfo = new PercentageDiscountInfo(discount.getPercentOff(), discount.getCouponAmount(),
                    campaign.getMaxCouponAmount(), campaign.getMaxRedeemPerUser());
            } else {
                discountInfo = new FixedDiscountInfo(discount.getAmountOff(), discount.getCouponAmount(),
                    campaign.getMaxCouponAmount(), campaign.getMaxRedeemPerUser());
            }
        }
        final List<ItemInfo> itemInfoList = new ArrayList<>();
        for (final Item item : items) {
            final ItemInfo itemInfo =
                new ItemInfo(item.getId(), item.getDescription(), item.getUnitPrice(), item.getQuantity());
            itemInfoList.add(itemInfo);
        }

        return new OneTapData(
            new FromExpressMetadataToAvailableMethods(Collections.<String>emptySet()).map(expressMetadataList),
            totalAmount, discountInfo, itemInfoList);
    }
}

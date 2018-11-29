package com.mercadopago.android.px.tracking.internal.views;

import com.mercadopago.android.px.model.CustomSearchItem;
import com.mercadopago.android.px.model.Item;
import com.mercadopago.android.px.model.PaymentMethod;
import com.mercadopago.android.px.model.PaymentMethodSearch;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SelectMethodViewTest {

    private static final String EXPECTED_PATH = "/px_checkout/payments/select_method";

    private static final String EXPECTED_ONE_CARD_SAVED_WITH_ESC =
        "{available_methods=[{payment_method_id=visa, payment_method_type=credit_card, extra_info={issuer_id=null, card_id=123456, selected_installment=null, has_esc=true}}], items=[{quantity=1.0, item={id=1234, description=description, price=10.0}}]}";

    private static final String EXPECTED_ONE_CARD_SAVED_NO_ESC =
        "{available_methods=[{payment_method_id=visa, payment_method_type=credit_card, extra_info={issuer_id=null, card_id=123456, selected_installment=null, has_esc=false}}], items=[{quantity=1.0, item={id=1234, description=description, price=10.0}}]}";

    private static final String EXPECTED_JUST_ACCOUNT_MONEY =
        "{available_methods=[{payment_method_id=account_money, payment_method_type=account_money, extra_info={balance=null, invested=false}}], items=[{quantity=1.0, item={id=1234, description=description, price=10.0}}]}";

    private static final String EXPECTED_JUST_ONE_RANDOM_PM =
        "{available_methods=[{payment_method_id=random_value, payment_method_type=random_value, extra_info=null}], items=[{quantity=1.0, item={id=1234, description=description, price=10.0}}]}";

    private static final String CARD_ID = "123456";

    @Mock private Set<String> cardsWithEsc;
    @Mock private PaymentMethodSearch paymentMethodSearch;
    @Mock private CustomSearchItem customSearchItem;
    @Mock private PaymentMethod paymentMethod;

    private List<Item> items = new ArrayList<>();

    @Before
    public void setUp() {
        items.add(new Item.Builder("title", 1, BigDecimal.TEN)
            .setDescription("description")
            .setId("1234")
            .build());
    }

    @Test
    public void whenGetPathObtainedIsCorrect() {

        final SelectMethodView selectMethodView = new SelectMethodView(paymentMethodSearch, cardsWithEsc,
            items);
        assertEquals(EXPECTED_PATH, selectMethodView.getViewPath());
    }

    @Test
    public void whenGetDataOneSavedCardWithEscReturnFormatIsAsExpected() {
        when(cardsWithEsc.contains(CARD_ID)).thenReturn(true);
        when(paymentMethodSearch.getCustomSearchItems()).thenReturn(Collections.singletonList(customSearchItem));
        when(customSearchItem.getId()).thenReturn(CARD_ID);
        when(customSearchItem.getPaymentMethodId()).thenReturn("visa");
        when(customSearchItem.getType()).thenReturn("credit_card");

        final SelectMethodView selectMethodView = new SelectMethodView(paymentMethodSearch, cardsWithEsc,
            items);

        assertEquals(EXPECTED_ONE_CARD_SAVED_WITH_ESC, selectMethodView.getData().toString());
    }

    @Test
    public void whenGetDataOneSavedCardWithNoEscReturnFormatIsAsExpected() {
        when(cardsWithEsc.contains(CARD_ID)).thenReturn(false);
        when(paymentMethodSearch.getCustomSearchItems()).thenReturn(Collections.singletonList(customSearchItem));
        when(customSearchItem.getId()).thenReturn(CARD_ID);
        when(customSearchItem.getPaymentMethodId()).thenReturn("visa");
        when(customSearchItem.getType()).thenReturn("credit_card");

        final SelectMethodView selectMethodView = new SelectMethodView(paymentMethodSearch, cardsWithEsc,
            items);

        assertEquals(EXPECTED_ONE_CARD_SAVED_NO_ESC, selectMethodView.getData().toString());
    }

    @Test
    public void whenGetDataOnePaymentMethodAccountMoneyReturnFormatIsAsExpected() {
        when(paymentMethodSearch.getCustomSearchItems()).thenReturn(Collections.singletonList(customSearchItem));
        when(customSearchItem.getId()).thenReturn("account_money");
        when(customSearchItem.getType()).thenReturn("account_money");

        final SelectMethodView selectMethodView = new SelectMethodView(paymentMethodSearch, cardsWithEsc,
            items);

        assertEquals(EXPECTED_JUST_ACCOUNT_MONEY, selectMethodView.getData().toString());
    }

    @Test
    public void whenGetDataOnePaymentMethodReturnFormatIsAsExpected() {
        when(paymentMethodSearch.getPaymentMethods()).thenReturn(Collections.singletonList(paymentMethod));
        when(paymentMethod.getId()).thenReturn("random_value");
        when(paymentMethod.getPaymentTypeId()).thenReturn("random_value");

        final SelectMethodView selectMethodView = new SelectMethodView(paymentMethodSearch, cardsWithEsc,
            items);

        assertEquals(EXPECTED_JUST_ONE_RANDOM_PM, selectMethodView.getData().toString());
    }
}
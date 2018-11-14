package com.mercadopago.android.px.internal.features.review_and_confirm;

import com.mercadopago.android.px.configuration.DynamicDialogConfiguration;
import com.mercadopago.android.px.internal.callbacks.FailureRecovery;
import com.mercadopago.android.px.internal.features.explode.ExplodeDecoratorMapper;
import com.mercadopago.android.px.internal.repository.PaymentRepository;
import com.mercadopago.android.px.internal.viewmodel.mappers.BusinessModelMapper;
import com.mercadopago.android.px.model.Sites;
import com.mercadopago.android.px.preferences.CheckoutPreference;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReviewAndConfirmPresenterTest {

    @Mock
    private ReviewAndConfirm.View view;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private BusinessModelMapper businessModelMapper;

    @Mock
    private DynamicDialogConfiguration dynamicDialogConfiguration;

    @Mock
    private CheckoutPreference checkoutPreference;

    @Mock
    private ExplodeDecoratorMapper explodeDecoratorMapper;

    @Mock
    private FailureRecovery recovery;

    private ReviewAndConfirmPresenter reviewAndConfirmPresenter;

    @Before
    public void setUp() {
        final CheckoutPreference preference = mock(CheckoutPreference.class);
        when(preference.getSite()).thenReturn(Sites.ARGENTINA);

        reviewAndConfirmPresenter =
            new ReviewAndConfirmPresenter(paymentRepository, businessModelMapper, dynamicDialogConfiguration,
                checkoutPreference);

        reviewAndConfirmPresenter.attachView(view);
    }

    @Test
    public void whenIsPaymentOrGenericPaymentAndAnimationIsFinishedThenShowResult(){

    }


}

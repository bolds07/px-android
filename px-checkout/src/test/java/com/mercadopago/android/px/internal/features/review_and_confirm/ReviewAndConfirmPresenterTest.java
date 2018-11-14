package com.mercadopago.android.px.internal.features.review_and_confirm;

import com.mercadopago.android.px.configuration.DynamicDialogConfiguration;
import com.mercadopago.android.px.internal.callbacks.FailureRecovery;
import com.mercadopago.android.px.internal.features.explode.ExplodeDecoratorMapper;
import com.mercadopago.android.px.internal.repository.PaymentRepository;
import com.mercadopago.android.px.internal.viewmodel.BusinessPaymentModel;
import com.mercadopago.android.px.internal.viewmodel.mappers.BusinessModelMapper;
import com.mercadopago.android.px.model.BusinessPayment;
import com.mercadopago.android.px.model.GenericPayment;
import com.mercadopago.android.px.model.IPayment;
import com.mercadopago.android.px.model.Payment;
import com.mercadopago.android.px.model.PaymentResult;
import com.mercadopago.android.px.preferences.CheckoutPreference;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
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

        reviewAndConfirmPresenter =
            new ReviewAndConfirmPresenter(paymentRepository, businessModelMapper, dynamicDialogConfiguration,
                checkoutPreference);

        verifyAttachView();
    }

    private void verifyAttachView() {
        reviewAndConfirmPresenter.attachView(view);

        verify(paymentRepository).attach(reviewAndConfirmPresenter);
        verifyNoMoreInteractions(view);
        verifyNoMoreInteractions(paymentRepository);
    }

    @Test
    public void whenIsPaymentAndAnimationIsFinishedThenShowResult() {
        final IPayment payment = mock(Payment.class);
        whenIPaymentAndAnimationIsFinishedThenShowResult(payment);
    }

    @Test
    public void whenIsGenericPaymentAndAnimationIsFinishedThenShowResult() {
        final IPayment payment = mock(GenericPayment.class);
        whenIPaymentAndAnimationIsFinishedThenShowResult(payment);
    }

    @Test
    public void whenIsBusinessPaymentAndAnimationIsFinishedThenMapItAndShowResult() {
        final BusinessPayment payment = mock(BusinessPayment.class);
        final BusinessPaymentModel businessPaymentModel = mock(BusinessPaymentModel.class);

        when(paymentRepository.getPayment()).thenReturn(payment);
        when(businessModelMapper.map(payment)).thenReturn(businessPaymentModel);

        reviewAndConfirmPresenter.hasFinishPaymentAnimation();

        verify(paymentRepository).getPayment();
        verify(view).showResult(businessPaymentModel);
        verify(businessModelMapper).map(payment);
        verifyNoMoreInteractions(view);
        verifyNoMoreInteractions(paymentRepository);
    }

    private void whenIPaymentAndAnimationIsFinishedThenShowResult(final IPayment payment) {
        final PaymentResult paymentResult = mock(PaymentResult.class);

        when(paymentRepository.getPayment()).thenReturn(payment);
        when(paymentRepository.createPaymentResult(payment)).thenReturn(paymentResult);

        reviewAndConfirmPresenter.hasFinishPaymentAnimation();

        verify(paymentRepository).getPayment();
        verify(view).showResult(paymentResult);
        verify(paymentRepository).createPaymentResult(payment);
        verifyNoMoreInteractions(view);
        verifyNoMoreInteractions(paymentRepository);
    }
}

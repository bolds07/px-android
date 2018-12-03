package com.mercadopago.android.px.internal.features.express;

import com.mercadopago.android.px.internal.repository.AmountRepository;
import com.mercadopago.android.px.internal.repository.DiscountRepository;
import com.mercadopago.android.px.internal.repository.GroupsRepository;
import com.mercadopago.android.px.internal.repository.PaymentRepository;
import com.mercadopago.android.px.internal.repository.PaymentSettingRepository;
import com.mercadopago.android.px.internal.view.ElementDescriptorView;
import com.mercadopago.android.px.internal.view.PaymentMethodDescriptorView;
import com.mercadopago.android.px.internal.view.SummaryView;
import com.mercadopago.android.px.internal.viewmodel.drawables.DrawableFragmentItem;
import com.mercadopago.android.px.internal.viewmodel.mappers.ElementDescriptorMapper;
import com.mercadopago.android.px.model.CardMetadata;
import com.mercadopago.android.px.model.ExpressMetadata;
import com.mercadopago.android.px.model.PayerCost;
import com.mercadopago.android.px.model.PaymentMethodSearch;
import com.mercadopago.android.px.model.Site;
import com.mercadopago.android.px.model.Sites;
import com.mercadopago.android.px.preferences.CheckoutPreference;
import com.mercadopago.android.px.utils.StubSuccessMpCall;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyListOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExpressPaymentPresenterTest {

    @Mock
    private ExpressPayment.View view;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private PaymentSettingRepository configuration;

    @Mock
    private ElementDescriptorMapper elementDescriptorMapper;

    @Mock
    private GroupsRepository groupsRepository;

    @Mock
    private DiscountRepository discountRepository;

    @Mock
    private AmountRepository amountRepository;

    @Mock
    private PaymentMethodSearch paymentMethodSearch;

    @Mock
    private ExpressMetadata expressMetadata;

    @Mock
    private CardMetadata cardMetadata;

    private ExpressPaymentPresenter expressPaymentPresenter;

    @Before
    public void setUp() {
        //This is needed for the presenter constructor
        final CheckoutPreference preference = mock(CheckoutPreference.class);
        when(preference.getSite()).thenReturn(Sites.ARGENTINA);
        when(configuration.getCheckoutPreference()).thenReturn(preference);

        when(groupsRepository.getGroups())
            .thenReturn(new StubSuccessMpCall<>(paymentMethodSearch));
        when(paymentMethodSearch.getExpress()).thenReturn(Arrays.asList(expressMetadata));
        when(expressMetadata.getCard()).thenReturn(cardMetadata);

        expressPaymentPresenter = new ExpressPaymentPresenter(paymentRepository, configuration, discountRepository,
            amountRepository, elementDescriptorMapper, groupsRepository);

        verifyAttachView();
    }

    @Test
    public void whenCanceledThenCancelAndTrack() {
        expressPaymentPresenter.cancel();
        verify(view).cancel();
        verifyNoMoreInteractions(view);
    }

    @Test
    public void whenViewIsResumedThenPaymentRepositoryIsAttached() {
        verifyOnViewResumed();
        verifyNoMoreInteractions(paymentRepository);
        verifyNoMoreInteractions(view);
    }

    @Test
    public void whenViewIsPausedThenPaymentRepositoryIsDetached() {
        verifyOnViewResumed();
        expressPaymentPresenter.onViewPaused();
        verify(paymentRepository).detach(expressPaymentPresenter);
        verifyNoMoreInteractions(paymentRepository);
        verifyNoMoreInteractions(view);
    }

    @Test
    public void whenViewIsResumedAndPaymentRepositoryHasPaymentThenCancelLoading() {
        when(paymentRepository.hasPayment()).thenReturn(true);
        verifyOnViewResumed();
        verify(view).enableToolbarBack();
        verify(view).cancelLoading();
        verifyNoMoreInteractions(paymentRepository);
        verifyNoMoreInteractions(view);
    }

    @Test
    public void whenSliderOptionSelectedThenShowInstallmentsRow() {
        final int currentElementPosition = 1;
        expressPaymentPresenter.onSliderOptionSelected(currentElementPosition);
        verify(view).hideInstallmentsSelection();
        verify(view).showInstallmentsDescriptionRow(currentElementPosition,
            PaymentMethodDescriptorView.Model.SELECTED_PAYER_COST_NONE);
        verifyNoMoreInteractions(view);
    }

    @Test
    public void whenPayerCostSelectedThenItsReflectedOnView() {
        final int paymentMethodIndex = 0;
        final int selectedPayerCostIndex = 1;
        final List<PayerCost> payerCostList =
            Arrays.asList(mock(PayerCost.class), mock(PayerCost.class), mock(PayerCost.class));

        when(cardMetadata.getPayerCosts()).thenReturn(payerCostList);

        expressPaymentPresenter
            .onPayerCostSelected(paymentMethodIndex, payerCostList.get(selectedPayerCostIndex));

        verify(view).hideInstallmentsSelection();
        verify(view).showInstallmentsDescriptionRow(paymentMethodIndex, selectedPayerCostIndex);
        verify(view).collapseInstallmentsSelection();
        verifyNoMoreInteractions(view);
    }

    private void verifyAttachView() {
        final ElementDescriptorView.Model model = mock(ElementDescriptorView.Model.class);

        when(elementDescriptorMapper.map(configuration.getCheckoutPreference())).thenReturn(model);

        expressPaymentPresenter.attachView(view);

        verify(view).updateSummary(any(SummaryView.Model.class));
        verify(view).showToolbarElementDescriptor(model);
        verify(view).configurePagerAndInstallments(anyListOf(DrawableFragmentItem.class), any(Site.class),
            anyInt(), anyListOf(PaymentMethodDescriptorView.Model.class));
    }

    private void verifyOnViewResumed() {
        expressPaymentPresenter.onViewResumed();
        verify(paymentRepository).hasPayment();
        verify(paymentRepository).attach(expressPaymentPresenter);
    }
}
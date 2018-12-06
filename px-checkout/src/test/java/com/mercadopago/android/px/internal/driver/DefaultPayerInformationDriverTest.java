package com.mercadopago.android.px.internal.driver;

import com.mercadopago.android.px.internal.navigation.DefaultPayerInformationDriver;
import com.mercadopago.android.px.model.Identification;
import com.mercadopago.android.px.model.Payer;
import com.mercadopago.android.px.model.Payment;
import com.mercadopago.android.px.model.PaymentMethod;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultPayerInformationDriverTest {
    private static final String TEST_NAME = "Test Name";
    private static final String TEST_LASTNAME = "Test Lastname";
    private static final String TEST_ID_TYPE = "CPF";
    private static final String TEST_ID_NUMBER = "12312312312";
    private static final String TEST_PAYMNENT_METHOD_ID_BOLBRADESCO = "bolbradesco";
    /* default */ static final List<String> ADDITIONAL_INFO;

    static {
        final List<String> list = new ArrayList<>();
        list.add("bolbradesco_name");
        list.add("bolbradesco_identification_type");
        list.add("bolbradesco_identification_number");
        ADDITIONAL_INFO = Collections.unmodifiableList(list);
    }

    private DefaultPayerInformationDriver handler;

    @Mock private DefaultPayerInformationDriver.PayerInformationDriverCallback payerInfoDriverCallback;
    @Mock private Payer payerMock;
    @Mock private PaymentMethod paymentMethod;
    @Mock private Identification identification;

    @Before
    public void setUp() {
        when(paymentMethod.getId()).thenReturn(TEST_PAYMNENT_METHOD_ID_BOLBRADESCO);
        when(paymentMethod.getAdditionalInfoNeeded()).thenReturn(ADDITIONAL_INFO);

        handler = new DefaultPayerInformationDriver(payerMock, paymentMethod);
        when(identification.getNumber()).thenReturn(TEST_ID_NUMBER);
        when(identification.getType()).thenReturn(TEST_ID_TYPE);
        when(payerMock.getIdentification()).thenReturn(identification);
        when(payerMock.getFirstName()).thenReturn(TEST_NAME);
        when(payerMock.getLastName()).thenReturn(TEST_LASTNAME);
    }

    @Test
    public void whenPayerIsNullAndWithoutAdditionalInfoThenDriveToReviewAndConfirm() {
        when(paymentMethod.getAdditionalInfoNeeded()).thenReturn(null);
        new DefaultPayerInformationDriver(null, paymentMethod).drive(payerInfoDriverCallback);
        verify(payerInfoDriverCallback).driveToReviewConfirm();
        verifyNoMoreInteractions(payerInfoDriverCallback);
    }

    @Test
    public void whenPayerIsNullThenCollectPayerInfo() {
        new DefaultPayerInformationDriver(null, paymentMethod).drive(payerInfoDriverCallback);
        verify(payerInfoDriverCallback).driveToNewPayerData();
        verifyNoMoreInteractions(payerInfoDriverCallback);
    }

    @Test
    public void whenPayerIsNotNullAndHasInvalidNameThenCollectPayerInfo() {
        when(payerMock.getFirstName()).thenReturn(StringUtils.EMPTY);
        handler.drive(payerInfoDriverCallback);
        verify(payerInfoDriverCallback).driveToNewPayerData();
        verifyNoMoreInteractions(payerInfoDriverCallback);
    }

    @Test
    public void whenPayerIsNotNullAndHasNullNameThenCollectPayerInfo() {
        when(payerMock.getFirstName()).thenReturn(null);
        handler.drive(payerInfoDriverCallback);
        verify(payerInfoDriverCallback).driveToNewPayerData();
        verifyNoMoreInteractions(payerInfoDriverCallback);
    }

    @Test
    public void whenPayerIsNotNullAndHasInvalidLastNameThenCollectPayerInfo() {
        when(payerMock.getLastName()).thenReturn(StringUtils.EMPTY);
        handler.drive(payerInfoDriverCallback);
        verify(payerInfoDriverCallback).driveToNewPayerData();
        verifyNoMoreInteractions(payerInfoDriverCallback);
    }

    @Test
    public void whenPayerIsNotNullAndHasNullLastNameThenCollectPayerInfo() {
        when(payerMock.getLastName()).thenReturn(null);
        handler.drive(payerInfoDriverCallback);
        verify(payerInfoDriverCallback).driveToNewPayerData();
        verifyNoMoreInteractions(payerInfoDriverCallback);
    }

    @Test
    public void whenPayerIsNotNullAndHasInvalidIdentificationNumberThenCollectPayerInfo() {
        when(identification.getNumber()).thenReturn(StringUtils.EMPTY);
        handler.drive(payerInfoDriverCallback);
        verify(payerInfoDriverCallback).driveToNewPayerData();
        verifyNoMoreInteractions(payerInfoDriverCallback);
    }

    @Test
    public void whenPayerIsNotNullAndHasInvalidIdentificationTypeThenCollectPayerInfo() {
        when(identification.getType()).thenReturn(StringUtils.EMPTY);
        handler.drive(payerInfoDriverCallback);
        verify(payerInfoDriverCallback).driveToNewPayerData();
        verifyNoMoreInteractions(payerInfoDriverCallback);
    }

    @Test
    public void whenPayerIsNotNullAndHasNullIdentificationThenCollectPayerInfo() {
        when(payerMock.getIdentification()).thenReturn(null);
        handler.drive(payerInfoDriverCallback);
        verify(payerInfoDriverCallback).driveToNewPayerData();
        verifyNoMoreInteractions(payerInfoDriverCallback);
    }

    @Test
    public void whenPayerIsNotNullAndHasInfoThenDriveToReviewConfirm() {
        handler.drive(payerInfoDriverCallback);
        verify(payerInfoDriverCallback).driveToReviewConfirm();
        verifyNoMoreInteractions(payerInfoDriverCallback);
    }
}
package com.mercadopago.android.px.tracking.internal.views;

import java.util.Collections;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class OneTapViewTest {

    private static final String EXPECTED_PATH = "/px_checkout/review/one_tap";

    @Test
    public void verifyPath() {
        assertEquals(EXPECTED_PATH, new OneTapView(Collections.EMPTY_LIST, null,
            null, null, Collections.EMPTY_LIST).getViewPath());
    }
}
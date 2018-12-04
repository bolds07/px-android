package com.mercadopago.android.px.tracking.internal.views;

import android.support.annotation.NonNull;
import com.mercadopago.android.px.tracking.PXTracker;
import com.mercadopago.android.px.tracking.PXTrackingListener;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class OneTapViewTest {

    private static final String EXPECTED_PATH = "/px_checkout/review/one_tap";

    @Test
    public void verifyPath() {
        assertEquals(EXPECTED_PATH, new OneTapViewTracker(Collections.EMPTY_LIST, null,
            null, null, Collections.EMPTY_LIST).getViewPath());
    }

    @Test
    public void verifyListenerCalled() {
        final PXTrackingListener listener = mock(PXTrackingListener.class);
        PXTracker.setListener(listener);
        final Map<String, Object> data = emptyOneTapData();
        new OneTapViewTracker(Collections.EMPTY_LIST, null, null, null, Collections.EMPTY_LIST).track();
        verify(listener).onView(EXPECTED_PATH, data);
    }

    @NonNull
    private Map<String, Object> emptyOneTapData() {
        final Map<String, Object> data = new HashMap<>();
        data.put("total_amount", null);
        data.put("discount", null);
        data.put("available_methods", Collections.EMPTY_LIST);
        data.put("items", Collections.EMPTY_LIST);
        data.put("flow", null);
        data.put("flow_detail", Collections.EMPTY_MAP);
        return data;
    }
}
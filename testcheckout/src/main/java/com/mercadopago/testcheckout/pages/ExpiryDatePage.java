package com.mercadopago.testcheckout.pages;

import android.support.test.espresso.action.ViewActions;
import android.view.View;

import com.mercadopago.testcheckout.assertions.CheckoutValidator;
import com.mercadopago.testlib.pages.PageObject;

import org.hamcrest.Matcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class ExpiryDatePage extends PageObject<CheckoutValidator> {

    public ExpiryDatePage() {
        // This constructor is intentionally empty. Nothing special is needed here.
    }

    public ExpiryDatePage(CheckoutValidator validator) {
        super(validator);
    }

    @Override
    public ExpiryDatePage validate(CheckoutValidator validator) {
        validator.validate(this);
        return this;
    }

    public SecurityCodePage enterExpiryDate(final String s) {
        Matcher<View> cardExpiryDateEditTextMatcher = withId(com.mercadopago.R.id.mpsdkCardExpiryDate);
        Matcher<View> cardNextButtonTextMatcher = withId(com.mercadopago.R.id.mpsdkNextButtonText);
        onView(cardExpiryDateEditTextMatcher).perform(typeText("0922"));
        onView(cardNextButtonTextMatcher).perform(click());
        return new SecurityCodePage(validator);
    }

    public NoCheckoutPage pressBack() {
        onView(isRoot()).perform(ViewActions.pressBack());
        return new NoCheckoutPage(validator);
    }

    public NamePage pressPrevious() {
        onView(withId(com.mercadopago.R.id.mpsdkBackButton)).perform(click());
        return new NamePage(validator);
    }
}
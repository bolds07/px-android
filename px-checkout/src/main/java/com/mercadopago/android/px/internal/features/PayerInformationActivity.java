package com.mercadopago.android.px.internal.features;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import com.google.gson.reflect.TypeToken;
import com.mercadopago.android.px.R;
import com.mercadopago.android.px.internal.adapters.IdentificationTypesAdapter;
import com.mercadopago.android.px.internal.callbacks.card.TicketIdentificationNameEditTextCallback;
import com.mercadopago.android.px.internal.callbacks.card.TicketIdentificationNumberEditTextCallback;
import com.mercadopago.android.px.internal.di.Session;
import com.mercadopago.android.px.internal.features.card.TicketIdentificationNameTextWatcher;
import com.mercadopago.android.px.internal.features.card.TicketIdentificationNumberTextWatcher;
import com.mercadopago.android.px.internal.features.providers.PayerInformationProviderImpl;
import com.mercadopago.android.px.internal.features.uicontrollers.identification.IdentificationTicketView;
import com.mercadopago.android.px.internal.tracker.Tracker;
import com.mercadopago.android.px.internal.util.ErrorUtil;
import com.mercadopago.android.px.internal.util.JsonUtil;
import com.mercadopago.android.px.internal.util.ScaleUtil;
import com.mercadopago.android.px.internal.util.ViewUtils;
import com.mercadopago.android.px.internal.view.MPEditText;
import com.mercadopago.android.px.internal.view.MPTextView;
import com.mercadopago.android.px.model.Identification;
import com.mercadopago.android.px.model.IdentificationType;
import com.mercadopago.android.px.model.exceptions.ApiException;
import com.mercadopago.android.px.model.exceptions.MercadoPagoError;
import com.mercadopago.android.px.tracking.internal.utils.TrackingUtil;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PayerInformationActivity extends MercadoPagoBaseActivity implements PayerInformationView {

    public static final String IDENTIFICATION_NUMBER_BUNDLE = "mIdentificationNumber";
    public static final String IDENTIFICATION_NAME_BUNDLE = "mIdentificationName";
    public static final String IDENTIFICATION_LAST_NAME_BUNDLE = "mIdentificationLastName";
    public static final String IDENTIFICATION_BUNDLE = "mIdentification";
    public static final String IDENTIFICATION_TYPE_BUNDLE = "mIdentificationType";
    public static final String IDENTIFICATION_TYPES_LIST_BUNDLE = "mIdentificationTypesList";

    public static final String IDENTIFICATION_NUMBER_INPUT = "identificationNumber";
    public static final String IDENTIFICATION_NAME_INPUT = "identificationName";
    public static final String IDENTIFICATION_LAST_NAME_INPUT = "identificationLastName";
    public static final String IDENTIFICATION_BUSINESS_NAME_INPUT = "identificationBusinessName";

    public static final String IDENTIFICATION_TYPE_CNPJ = "CNPJ";

    public static final String ERROR_STATE = "textview_error";
    public static final String NORMAL_STATE = "textview_normal";

    public static void start(final Activity activity, final int reqCode) {
        final Intent payerInformationIntent = new Intent(activity, PayerInformationActivity.class);
        activity.startActivityForResult(payerInformationIntent, reqCode);
    }

    // Local vars
    protected PayerInformationPresenter mPresenter;
    private boolean mActivityActive;

    //View controls
    private ScrollView mScrollView;
    private ViewGroup mProgressLayout;

    //Input controls
    private String mCurrentEditingEditText;
    private String mErrorState;
    private LinearLayout mInputContainer;
    private LinearLayout mIdentificationNumberInput;
    private LinearLayout mIdentificationNameInput;
    private LinearLayout mIdentificationLastNameInput;
    private LinearLayout mIdentificationTypeContainer;
    private LinearLayout mButtonContainer;
    private FrameLayout mNextButton;
    private FrameLayout mBackButton;
    private FrameLayout mIdentificationCardContainer;
    private FrameLayout mErrorContainer;
    private Spinner mIdentificationTypeSpinner;
    private MPEditText mIdentificationNumberEditText;
    private MPEditText mIdentificationNameEditText;
    private MPEditText mIdentificationLastNameEditText;
    private MPTextView mErrorTextView;
    private Toolbar mLowResToolbar;
    private MPTextView mLowResTitleToolbar;
    private Toolbar mNormalToolbar;

    private boolean mLowResActive;

    private IdentificationTicketView mIdentificationTicketView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityActive = true;

        createPresenter();
        analyzeLowRes();
        configurePresenter();
        setContentView();
        initializeControls();
        initializeToolbar();
        setListeners();
        initialize();
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(IDENTIFICATION_NUMBER_BUNDLE, mPresenter.getIdentificationNumber());
        outState.putString(IDENTIFICATION_NAME_BUNDLE, mPresenter.getIdentificationName());
        outState.putString(IDENTIFICATION_LAST_NAME_BUNDLE, mPresenter.getIdentificationLastName());
        outState.putString(IDENTIFICATION_BUNDLE, JsonUtil.getInstance().toJson(mPresenter.getIdentification()));
        outState
            .putString(IDENTIFICATION_TYPE_BUNDLE, JsonUtil.getInstance().toJson(mPresenter.getIdentificationType()));
        outState.putString(IDENTIFICATION_TYPES_LIST_BUNDLE,
            JsonUtil.getInstance().toJson(mPresenter.getIdentificationTypes()));
    }

    @Override
    protected void onRestoreInstanceState(final Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null) {
            String identificationNumber = savedInstanceState.getString(IDENTIFICATION_NUMBER_BUNDLE);
            String identificationName = savedInstanceState.getString(IDENTIFICATION_NAME_BUNDLE);
            String identificationLastName = savedInstanceState.getString(IDENTIFICATION_LAST_NAME_BUNDLE);
            Identification identification = JsonUtil.getInstance()
                .fromJson(savedInstanceState.getString(IDENTIFICATION_BUNDLE), Identification.class);
            IdentificationType identificationType = JsonUtil.getInstance()
                .fromJson(savedInstanceState.getString(IDENTIFICATION_TYPE_BUNDLE), IdentificationType.class);
            List<IdentificationType> identificationTypesList;
            try {
                Type listType = new TypeToken<List<IdentificationType>>() {
                }.getType();
                identificationTypesList = JsonUtil.getInstance().getGson().fromJson(
                    savedInstanceState.getString(IDENTIFICATION_TYPES_LIST_BUNDLE), listType);
            } catch (Exception ex) {
                identificationTypesList = new ArrayList<>();
            }

            mPresenter.setIdentificationNumber(identificationNumber);
            mPresenter.setIdentificationName(identificationName);
            mPresenter.setIdentificationLastName(identificationLastName);
            mPresenter.setIdentification(identification);
            mPresenter.setIdentificationType(identificationType);
            mPresenter.setIdentificationTypesList(identificationTypesList);
        }
    }

    private void initializeToolbar() {
        if (mLowResActive) {
            initializeToolbar(mLowResToolbar);
        } else {
            initializeToolbar(mNormalToolbar);
        }
    }

    private void initializeToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setResult(RESULT_CANCELED, new Intent());
                    finish();
                }
            });
        }
    }

    private void analyzeLowRes() {
        mLowResActive = ScaleUtil.isLowRes(this);
    }

    private void createPresenter() {
        mPresenter =
            new PayerInformationPresenter(Session.getSession(this).getConfigurationModule().getPaymentSettings());
    }

    private void configurePresenter() {
        mPresenter.attachView(this);
        mPresenter.attachResourcesProvider(new PayerInformationProviderImpl(this));
    }

    private void setContentView() {
        if (mLowResActive) {
            setContentViewLowRes();
        } else {
            setContentViewNormal();
        }
    }

    private void setContentViewLowRes() {
        setContentView(R.layout.px_activity_payer_information_lowres);
    }

    private void setContentViewNormal() {
        setContentView(R.layout.px_activity_payer_information_normal);
    }

    private void initializeControls() {
        mInputContainer = findViewById(R.id.mpsdkInputContainer);
        mIdentificationNumberInput = findViewById(R.id.mpsdkCardIdentificationInput);
        mIdentificationNameInput = findViewById(R.id.mpsdkNameInput);
        mIdentificationLastNameInput = findViewById(R.id.mpsdkLastNameInput);

        mIdentificationNumberEditText = findViewById(R.id.mpsdkCardIdentificationNumber);
        mIdentificationNameEditText = findViewById(R.id.mpsdkName);
        mIdentificationLastNameEditText = findViewById(R.id.mpsdkLastName);

        mIdentificationTypeSpinner = findViewById(R.id.mpsdkCardIdentificationType);
        mIdentificationTypeContainer = findViewById(R.id.mpsdkCardIdentificationTypeContainer);

        mNextButton = findViewById(R.id.mpsdkNextButton);
        mBackButton = findViewById(R.id.mpsdkBackButton);

        mScrollView = findViewById(R.id.mpsdkScrollViewContainer);
        mProgressLayout = findViewById(R.id.mpsdkProgressLayout);

        mIdentificationCardContainer = findViewById(R.id.mpsdkIdentificationCardContainer);

        mIdentificationTicketView = new IdentificationTicketView(this);
        mIdentificationTicketView.inflateInParent(mIdentificationCardContainer, true);
        mIdentificationTicketView.initializeControls();

        mButtonContainer = findViewById(R.id.mpsdkButtonContainer);
        mErrorContainer = findViewById(R.id.mpsdkErrorContainer);
        mErrorTextView = findViewById(R.id.mpsdkErrorTextView);

        if (mLowResActive) {
            mLowResToolbar = findViewById(R.id.mpsdkLowResToolbar);
            mLowResTitleToolbar = findViewById(R.id.mpsdkTitle);
            mLowResTitleToolbar.setText(getResources().getText(R.string.px_fill_your_data));
            mLowResToolbar.setVisibility(View.VISIBLE);
        } else {
            mNormalToolbar = findViewById(R.id.mpsdkTransparentToolbar);
        }

        showProgressBar();
        fullScrollDown();
    }

    @Override
    public void initializeIdentificationTypes(final List<IdentificationType> identificationTypes) {
        mIdentificationTicketView.setIdentificationType(identificationTypes.get(0));
        mIdentificationTicketView.drawIdentificationTypeName();
        mIdentificationTypeSpinner.setAdapter(new IdentificationTypesAdapter(identificationTypes));
        mIdentificationTypeContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void showProgressBar() {
        mInputContainer.setVisibility(View.GONE);
        mButtonContainer.setVisibility(View.GONE);
        mProgressLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mInputContainer.setVisibility(View.VISIBLE);
        mButtonContainer.setVisibility(View.VISIBLE);
        mProgressLayout.setVisibility(View.GONE);
    }

    private void setListeners() {
        setIdentificationTypeListeners();
        setIdentificationNumberEditTextListeners();
        setIdentificationNameEditTextListeners();
        setIdentificationLastNameEditTextListeners();
        setNextButtonListeners();
        setBackButtonListeners();
    }

    private void setIdentificationNumberEditTextListeners() {
        mIdentificationNumberEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return onNextKey(actionId, event);
            }
        });

        mIdentificationNumberEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                setAlphaColorText();
                onTouchEditText(mIdentificationNumberEditText, event);
                return true;
            }
        });

        mIdentificationNumberEditText.addTextChangedListener(
            new TicketIdentificationNumberTextWatcher(new TicketIdentificationNumberEditTextCallback() {
                @Override
                public void checkOpenKeyboard() {
                    setAlphaColorText();
                    openKeyboard(mIdentificationNumberEditText);
                }

                @Override
                public void saveIdentificationNumber(CharSequence string) {
                    mPresenter.saveIdentificationNumber(string.toString());

                    mIdentificationTicketView.setIdentificationNumber(string.toString());

                    setAlphaColorText();
                    mIdentificationTicketView.draw();
                }

                @Override
                public void changeErrorView() {
                    setAlphaColorText();
                    checkChangeErrorView();
                }

                @Override
                public void toggleLineColorOnError(boolean toggle) {
                    setAlphaColorText();
                    mIdentificationNumberEditText.toggleLineColorOnError(toggle);
                }
            }));
    }

    private void setAlphaColorText() {
        mIdentificationTicketView.setAlphaColorNameText();
        mIdentificationTicketView.setAlphaColorLastNameText();
    }

    private void setIdentificationNameEditTextListeners() {
        mIdentificationNameEditText.setFilters(new InputFilter[] { new InputFilter.AllCaps() });

        mIdentificationNameEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return onNextKey(actionId, event);
            }
        });

        mIdentificationNameEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                onTouchEditText(mIdentificationNameEditText, event);
                return true;
            }
        });

        mIdentificationNameEditText.addTextChangedListener(
            new TicketIdentificationNameTextWatcher(new TicketIdentificationNameEditTextCallback() {
                @Override
                public void checkOpenKeyboard() {
                    openKeyboard(mIdentificationNameEditText);
                }

                @Override
                public void saveIdentificationName(CharSequence string) {
                    mPresenter.saveIdentificationName(string.toString());

                    mIdentificationTicketView.setIdentificationName(string.toString());
                    mIdentificationTicketView.draw();
                }

                @Override
                public void changeErrorView() {
                    checkChangeErrorView();
                }

                @Override
                public void toggleLineColorOnError(boolean toggle) {
                    mIdentificationNameEditText.toggleLineColorOnError(toggle);
                }
            }));
    }

    private void setIdentificationLastNameEditTextListeners() {
        mIdentificationLastNameEditText.setFilters(new InputFilter[] { new InputFilter.AllCaps() });

        mIdentificationLastNameEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return onNextKey(actionId, event);
            }
        });

        mIdentificationLastNameEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                onTouchEditText(mIdentificationLastNameEditText, event);
                return true;
            }
        });

        mIdentificationLastNameEditText.addTextChangedListener(
            new TicketIdentificationNameTextWatcher(new TicketIdentificationNameEditTextCallback() {
                @Override
                public void checkOpenKeyboard() {
                    openKeyboard(mIdentificationLastNameEditText);
                }

                @Override
                public void saveIdentificationName(CharSequence string) {
                    mPresenter.saveIdentificationLastName(string.toString());

                    mIdentificationTicketView.setIdentificationLastName(string.toString());
                    mIdentificationTicketView.draw();
                }

                @Override
                public void changeErrorView() {
                    checkChangeErrorView();
                }

                @Override
                public void toggleLineColorOnError(boolean toggle) {
                    mIdentificationLastNameEditText.toggleLineColorOnError(toggle);
                }
            }));
    }

    public void setIdentificationTypeListeners() {
        mIdentificationTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                IdentificationType identificationType =
                    (IdentificationType) mIdentificationTypeSpinner.getSelectedItem();

                mIdentificationTicketView.setIdentificationType(identificationType);
                mIdentificationTicketView.drawIdentificationTypeName();

                mPresenter.saveIdentificationType(identificationType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //Do something
            }
        });
        mIdentificationTypeSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                openKeyboard(mIdentificationNumberEditText);
                return false;
            }
        });
    }

    public void setNextButtonListeners() {
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateCurrentEditText();
            }
        });
    }

    public void setBackButtonListeners() {
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentEditingEditText.equals(IDENTIFICATION_NUMBER_INPUT)) {
                    finishWithCancelResult();
                } else {
                    checkIsEmptyOrValid();
                }
            }
        });
    }

    private void finishWithCancelResult() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("backButtonPressed", true);
        setResult(RESULT_CANCELED, returnIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("backButtonPressed", true);
        setResult(RESULT_CANCELED, returnIntent);
        finish();
    }

    private void initialize() {
        mErrorState = NORMAL_STATE;
        mPresenter.initialize();
    }

    @Override
    public void showInputContainer() {
        requestIdentificationNumberFocus();
    }

    @Override
    public void setIdentificationNumberRestrictions(String type) {
        setInputMaxLength(mIdentificationNumberEditText, mPresenter.getIdentificationNumberMaxLength());
        if ("number".equals(type)) {
            mIdentificationNumberEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else {
            mIdentificationNumberEditText.setInputType(InputType.TYPE_CLASS_TEXT);
        }
        if (!mIdentificationNumberEditText.getText().toString().isEmpty()) {
            mPresenter.validateIdentificationNumber();
        }
    }

    private void setInputMaxLength(MPEditText text, int maxLength) {
        InputFilter[] fArray = new InputFilter[1];
        fArray[0] = new InputFilter.LengthFilter(maxLength);
        text.setFilters(fArray);
    }

    private boolean onNextKey(int actionId, KeyEvent event) {
        if (isNextKey(actionId, event)) {
            validateCurrentEditText();
            return true;
        }
        return false;
    }

    private boolean isNextKey(int actionId, KeyEvent event) {
        return actionId == EditorInfo.IME_ACTION_NEXT ||
            (event != null && event.getAction() == KeyEvent.ACTION_DOWN &&
                event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
    }

    private void onTouchEditText(MPEditText editText, MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);
        if (action == MotionEvent.ACTION_DOWN) {
            openKeyboard(editText);
        }
    }

    private void openKeyboard(MPEditText ediText) {
        ediText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(ediText, InputMethodManager.SHOW_IMPLICIT);
        fullScrollDown();
    }

    private void fullScrollDown() {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                mScrollView.fullScroll(View.FOCUS_DOWN);
            }
        };
        mScrollView.post(r);
        r.run();
    }

    private boolean validateCurrentEditText() {
        switch (mCurrentEditingEditText) {
        case IDENTIFICATION_NUMBER_INPUT:
            if (mPresenter.validateIdentificationNumber()) {
                mIdentificationNumberInput.setVisibility(View.GONE);

                mIdentificationTicketView.setNormalColorNameText();

                if (mPresenter.getIdentificationType().getId().equals(IDENTIFICATION_TYPE_CNPJ)) {
                    requestIdentificationBusinessNameFocus();
                } else {
                    requestIdentificationNameFocus();
                }
                return true;
            }
            return false;
        case IDENTIFICATION_NAME_INPUT:
            if (mPresenter.validateName()) {
                mIdentificationNameInput.setVisibility(View.GONE);
                mIdentificationTicketView.setNormalColorLastNameText();
                requestIdentificationLastNameFocus();
                return true;
            }
            return false;
        case IDENTIFICATION_LAST_NAME_INPUT:
            if (mPresenter.validateLastName()) {
                mIdentificationLastNameInput.setVisibility(View.GONE);
                showFinishCardFlow();
                return true;
            }
            return false;
        case IDENTIFICATION_BUSINESS_NAME_INPUT:
            if (mPresenter.validateBusinessName()) {
                mIdentificationNameInput.setVisibility(View.GONE);
                showFinishCardFlow();
                return true;
            }
            return false;
        default:
            return false;
        }
    }

    private void showFinishCardFlow() {
        ViewUtils.hideKeyboard(this);
        showProgressBar();
        mPresenter.createPayer();
        finishWithPayer();
    }

    private boolean checkIsEmptyOrValid() {
        switch (mCurrentEditingEditText) {
        case IDENTIFICATION_NAME_INPUT:
            if (mPresenter.checkIsEmptyOrValidName()) {
                mIdentificationNumberInput.setVisibility(View.VISIBLE);
                mIdentificationTicketView.setAlphaColorNameText();
                mIdentificationTicketView.setAlphaColorLastNameText();
                requestIdentificationNumberFocus();
                return true;
            }
            return false;
        case IDENTIFICATION_LAST_NAME_INPUT:
            if (mPresenter.checkIsEmptyOrValidLastName()) {
                mIdentificationNameInput.setVisibility(View.VISIBLE);
                requestIdentificationNameFocus();
                return true;
            }
            return false;
        default:
            return false;
        }
    }

    private void requestIdentificationNumberFocus() {
        Tracker.trackScreen(TrackingUtil.VIEW_PATH_BOLBRADESCO_CPF, TrackingUtil.VIEW_PATH_BOLBRADESCO_CPF, getApplicationContext());
        mCurrentEditingEditText = IDENTIFICATION_NUMBER_INPUT;
        openKeyboard(mIdentificationNumberEditText);
    }

    private void requestIdentificationNameFocus() {
        Tracker.trackScreen(TrackingUtil.VIEW_PATH_BOLBRADESCO_NAME, TrackingUtil.VIEW_PATH_BOLBRADESCO_NAME, getApplicationContext());
        mCurrentEditingEditText = IDENTIFICATION_NAME_INPUT;
        openKeyboard(mIdentificationNameEditText);
    }

    private void requestIdentificationLastNameFocus() {
        Tracker.trackScreen(TrackingUtil.VIEW_PATH_BOLBRADESCO_LASTNAME, TrackingUtil.VIEW_PATH_BOLBRADESCO_LASTNAME, getApplicationContext());
        mCurrentEditingEditText = IDENTIFICATION_LAST_NAME_INPUT;
        openKeyboard(mIdentificationLastNameEditText);
    }

    private void requestIdentificationBusinessNameFocus() {
        mCurrentEditingEditText = IDENTIFICATION_BUSINESS_NAME_INPUT;
        openKeyboard(mIdentificationNameEditText);
    }

    @Override
    public void showError(MercadoPagoError error, String requestOrigin) {
        if (error.isApiException()) {
            showApiException(error.getApiException(), requestOrigin);
        } else {
            ErrorUtil.startErrorActivity(this, error);
        }
    }

    public void showApiException(ApiException apiException, String requestOrigin) {
        if (mActivityActive) {
            ErrorUtil.showApiExceptionError(this, apiException, requestOrigin);
        }
    }

    private void checkChangeErrorView() {
        if (ERROR_STATE.equals(mErrorState)) {
            clearErrorView();
        }
    }

    @Override
    public void clearErrorView() {
        mButtonContainer.setVisibility(View.VISIBLE);
        mErrorContainer.setVisibility(View.GONE);
        mErrorTextView.setText("");
        setErrorState(NORMAL_STATE);
    }

    @Override
    public void setErrorView(String message) {
        mButtonContainer.setVisibility(View.GONE);
        mErrorContainer.setVisibility(View.VISIBLE);
        mErrorTextView.setText(message);
        setErrorState(ERROR_STATE);
    }

    private void setErrorState(String mErrorState) {
        this.mErrorState = mErrorState;
    }

    @Override
    public void clearErrorIdentificationNumber() {
        mIdentificationNumberEditText.toggleLineColorOnError(false);
    }

    @Override
    public void clearErrorName() {
        mIdentificationNameEditText.toggleLineColorOnError(false);
    }

    @Override
    public void clearErrorLastName() {
        mIdentificationLastNameEditText.toggleLineColorOnError(false);
    }

    @Override
    public void setErrorIdentificationNumber() {
        ViewUtils.openKeyboard(mIdentificationNumberEditText);
        mIdentificationNumberEditText.toggleLineColorOnError(true);
        mIdentificationNumberEditText.requestFocus();
    }

    @Override
    public void setErrorName() {
        ViewUtils.openKeyboard(mIdentificationNameEditText);
        mIdentificationNameEditText.toggleLineColorOnError(true);
        mIdentificationNameEditText.requestFocus();
    }

    @Override
    public void setErrorLastName() {
        ViewUtils.openKeyboard(mIdentificationLastNameEditText);
        mIdentificationLastNameEditText.toggleLineColorOnError(true);
        mIdentificationLastNameEditText.requestFocus();
    }

    private void finishWithPayer() {
        setResult(RESULT_OK);
        finish();
    }
}

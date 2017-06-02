package in.playcerteam.playces;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import in.playcerteam.playces.utilities.AppConstants;
import in.playcerteam.playces.utilities.SharedPreference;
import in.playcerteam.playces.utilities.Utility;

/**
 * Created by OFFICE on 9/29/2015.
 */
public class RegistrationActivity extends Activity {
    private ImageView logoIV;
    private EditText eemailET;
    private EditText phoneNumET;

    public String phoneStr = "";
    public String emailStr = "";

    private String nonce = "";
    private TextInputLayout inputLayoutEmail, inputLayoutPassword;

    private CoordinatorLayout coordinatorLayout;
    Snackbar snackbar ;
    ViewGroup group ;

    ProgressDialog ringProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        overridePendingTransition(R.anim.act_pull_in_right, R.anim.act_push_out_left);

        Utility.setDimensions(this);

        logoIV = (ImageView) findViewById(R.id.logo);
        logoIV.getLayoutParams().height = (int) (Utility.screenHeight / 5.3);

        eemailET = (EditText) findViewById(R.id.eemailETID);
        eemailET.getLayoutParams().height = (int) (Utility.screenWidth / 7.3);
        eemailET.setHintTextColor(getResources().getColor(R.color.grey));

        phoneNumET = (EditText) findViewById(R.id.phoneNumETID);
        phoneNumET.getLayoutParams().height = (int) (Utility.screenWidth / 7.3);
        phoneNumET.setHintTextColor(getResources().getColor(R.color.grey));

        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);


        //phoneNumET.setHint("10 digits phone number");
        eemailET.addTextChangedListener(new MyTextWatcher(eemailET));
        phoneNumET.addTextChangedListener(new MyTextWatcher(phoneNumET));

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        /*boolean isNetAvailable = Utility.isOnline(RegistrationActivity.this);
        if (isNetAvailable) {
            sendGetNonceRequesttoServer(AppConstants.GET_NONCE_URL);
        } else {
            Utility.showCustomToast(getResources().getString(R.string.pls_connect_internet), RegistrationActivity.this);
            finish();
        }*/

        Button loginBtnID = (Button) findViewById(R.id.loginBtnID);
        loginBtnID.getLayoutParams().height = (int) (Utility.screenWidth / 9.8);
        loginBtnID.getLayoutParams().width = (int) (Utility.screenWidth / 9.8);

        /*eemailET.setText("test@gmail.com");
        phoneNumET.setText("9948737414");*/

        loginBtnID.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                emailStr = eemailET.getText().toString();
                phoneStr = phoneNumET.getText().toString();
                boolean isNetAvailable = Utility.isOnline(RegistrationActivity.this);
                if (isNetAvailable) {
                    if (emailStr.trim().length() > 0 && phoneStr.trim().length() > 0) {
                        if (Utility.isEmailValid(emailStr)) {
                            if (phoneStr.length() >= 10) {
                                try {
                                    ringProgressDialog = ProgressDialog.show(RegistrationActivity.this, null, "Please Wait...", true);
                                    ringProgressDialog.setCancelable(true);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                RequestParams params = new RequestParams();
                                //params.put("u", phoneStr);
                                params.put("email", emailStr);
                                params.put("mobile", phoneStr);
                                sendOtpRequesttoServer(AppConstants.LOGIN_URL, params);
                            } else {
                                //Utility.showCustomToast(getResources().getString(R.string.invalid_no), RegistrationActivity.this);
                                snackbar = Snackbar.make(coordinatorLayout, getResources().getString(R.string.invalid_no), Snackbar.LENGTH_LONG);
                                snackbarMethod();
                            }
                        } else {
                            //Utility.showCustomToast(getResources().getString(R.string.invalid_email), RegistrationActivity.this);
                            snackbar = Snackbar.make(coordinatorLayout, getResources().getString(R.string.invalid_email), Snackbar.LENGTH_LONG);
                            snackbarMethod();
                        }
                    } else {
                        //Utility.showCustomToast(getResources().getString(R.string.all_fields_mandatory), RegistrationActivity.this);
                        snackbar = Snackbar.make(coordinatorLayout, getResources().getString(R.string.all_fields_mandatory), Snackbar.LENGTH_LONG);
                        snackbarMethod();
                    }
                } else {
                    //Utility.showCustomToast(getResources().getString(R.string.pls_connect_internet), RegistrationActivity.this);
                    snackbar = Snackbar.make(coordinatorLayout, getResources().getString(R.string.pls_connect_internet), Snackbar.LENGTH_LONG);
                    snackbarMethod();
                }
            }
        });
    }

    private void snackbarMethod() {
        group = (ViewGroup) snackbar.getView();
        group.setBackgroundColor(ContextCompat.getColor(RegistrationActivity.this, R.color.snack_bar_bg));
        snackbar.show();
    }

    /*private void sendGetNonceRequesttoServer(String getNonceUrl) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(getNonceUrl, null, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(String response) {
                if (response != null) {
                    System.out.println(response);
                    try {
                        */

    /******
     * Creates a new JSONObject with name/value mappings from the JSON string.
     ********//*
                        JSONObject jsonResponse = new JSONObject(response);
                        String statuss = jsonResponse.optString("status").toString();
                        String controller = jsonResponse.optString("controller").toString();
                        String register = jsonResponse.optString("method").toString();
                        nonce = jsonResponse.optString("nonce").toString();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
                // sCServiceListener.onServiceFailed(statusCode, error, content, REQUEST_EVENT_TYPE);
            }
        });
    }*/
    public void sendOtpRequesttoServer(String _Url, RequestParams params) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(_Url, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(String response) {
                if (response != null) {
                    System.out.println(response);
                    if (ringProgressDialog != null) {
                        ringProgressDialog.dismiss();
                        ringProgressDialog = null;
                    }
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        String message = jsonResponse.optString("message").toString();
                        boolean success = jsonResponse.getBoolean("success");
                        if (success) {
                            Utility.showCustomToast(message, RegistrationActivity.this);
                            Intent verifyIntent = new Intent(RegistrationActivity.this, VerificationActivity.class);
                            verifyIntent.putExtra(Utility.KEY_PHONE_NUM, phoneStr);
                            verifyIntent.putExtra(Utility.KEY_EMAIL_ID, emailStr);
                            startActivity(verifyIntent);
                            finish();
                        } else {
                            //Utility.showCustomToast(message, RegistrationActivity.this);
                            snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG);
                            //snackbar.show();
                            snackbarMethod();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
                if (ringProgressDialog != null) {
                    ringProgressDialog.dismiss();
                    ringProgressDialog = null;
                }
                if (statusCode == 404) {
                    //Utility.showCustomToast(getResources().getString(R.string.request_not_found), RegistrationActivity.this);
                    snackbar = Snackbar.make(coordinatorLayout, getResources().getString(R.string.request_not_found), Snackbar.LENGTH_LONG);
                    //snackbar.show();
                    snackbarMethod();
                } else if (statusCode == 500) {
                    //Utility.showCustomToast(getResources().getString(R.string.some_went_wrong), RegistrationActivity.this);
                    snackbar = Snackbar.make(coordinatorLayout, getResources().getString(R.string.some_went_wrong), Snackbar.LENGTH_LONG);
                    //snackbar.show();
                    snackbarMethod();
                } else {
                    //Utility.showCustomToast(getResources().getString(R.string.unexpected_error), RegistrationActivity.this);
                    snackbar = Snackbar.make(coordinatorLayout, getResources().getString(R.string.unexpected_error), Snackbar.LENGTH_LONG);
                    //snackbar.show();
                    snackbarMethod();
                }
            }
        });
    }

    ///Materal Design
    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.unameETID:
                    validateName();
                    break;
                case R.id.eemailETID:
                    // validateEmail();
                    break;
                case R.id.phoneNumETID:
                    // validatePhoneNo();
                    break;
                /*case R.id.referralCodeETID:
                    validateRefferalCode();
                    break;*/
            }
        }
    }


    private boolean validateName() {
        /*if (unameET.getText().toString().trim().isEmpty()) {
            //inputLayoutName.setError(getString(R.string.err_msg_name));
            inputLayoutName.setError(Html.fromHtml("<font color='white'>Enter your full name</font>"));
            requestFocus(unameET);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }*/
        return true;
    }

    private boolean validateEmail() {
        String email = eemailET.getText().toString().trim();
        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
            requestFocus(eemailET);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePhoneNo() {
        if (phoneNumET.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError(getString(R.string.err_msg_phoneno)); //err_msg_refferalcode
            requestFocus(phoneNumET);
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
    ///


    @Override
    public void onResume() {
        super.onResume();
        // Tracking the screen view
        //MyApplication.getInstance().trackScreenView("Registration");
    }
}
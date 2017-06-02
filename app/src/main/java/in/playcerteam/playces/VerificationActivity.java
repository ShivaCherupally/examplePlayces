package in.playcerteam.playces;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.playcerteam.playces.model.CourtDetailsData;
import in.playcerteam.playces.model.SportsAndCenterListData;
import in.playcerteam.playces.utilities.AppConstants;
import in.playcerteam.playces.utilities.AppDataBaseHelper;
import in.playcerteam.playces.utilities.SharedPreference;
import in.playcerteam.playces.utilities.Utility;

/**
 * Created by OFFICE on 9/29/2015.
 */
public class VerificationActivity extends Activity {
    private EditText otpET;
    private String mobileNumber = "";
    private AppDataBaseHelper dbHelper = new AppDataBaseHelper(this);

    private String nameStr = "";
    private String emailStr = "";
    private String nonceStr = "";
    public String referralCodeStr = "";

    String OTPstr = "";
    private String COMING_FROM = "";

    ProgressDialog ringProgressDialog;
    InputMethodManager imm;

    ArrayList<SportsAndCenterListData> _sportsAndCenterListData = new ArrayList<SportsAndCenterListData>();
    private CoordinatorLayout coordinatorLayout;
    Snackbar snackbar ;
    ViewGroup group ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_mobile_verification_register);
        overridePendingTransition(R.anim.act_pull_in_right, R.anim.act_push_out_left);

        Utility.setDimensions(this);

        if (getIntent() != null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                mobileNumber = bundle.getString(Utility.KEY_PHONE_NUM);
                nameStr = bundle.getString(Utility.KEY_NAME);
                emailStr = bundle.getString(Utility.KEY_EMAIL_ID);
                nonceStr = bundle.getString(Utility.KEY_NONCE);
                COMING_FROM = bundle.getString(Utility.KEY_COMING_FROM);
                if (COMING_FROM == null) {
                    COMING_FROM = "";
                }
                referralCodeStr = bundle.getString(Utility.KEY_REFERRAL_CODE);

                TextView mb = (TextView) findViewById(R.id.mobileTVID);
                //mb.setText("We have sent an OTP via SMS to "+mobileNumber+" please enter it here to continue.");
                mb.setText("Verification code is sent to the mobile number " +"\n" + "+91 " + mobileNumber + ".Kindly, Enter"+"\n" + " and submit to verify your mobile");
            }
        }
        //setupNavigation();

        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        otpET = (EditText) findViewById(R.id.phoneNumETID);
        otpET.getLayoutParams().width = (int) (Utility.screenWidth / 2.8);
        //otpET.getLayoutParams().height = (int) (Utility.screenWidth / 13.5);
        otpET.setHintTextColor(getResources().getColor(R.color.grey));

        RelativeLayout parentPanelRL = (RelativeLayout) findViewById(R.id.parentPanelRLID);
        parentPanelRL.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                imm.hideSoftInputFromWindow(otpET.getWindowToken(), 0);
            }
        });

        Button verifyBtn = (Button) findViewById(R.id.verifyBtnID);
        verifyBtn.getLayoutParams().height = (int) (Utility.screenWidth / 11.8);
        verifyBtn.getLayoutParams().width = (int) (Utility.screenWidth / 11.8);

        Button refreshBtnID = (Button) findViewById(R.id.refreshBtnID);
        refreshBtnID.getLayoutParams().height = (int) (Utility.screenWidth / 13.5);
        refreshBtnID.getLayoutParams().width = (int) (Utility.screenWidth / 13.5);

        LinearLayout resendLLID = (LinearLayout) findViewById(R.id.resendLLID);
        resendLLID.getLayoutParams().width = (int) (Utility.screenWidth / 2.8);
        // resendLLID.getLayoutParams().height = (int) (Utility.screenWidth / 13.5);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        verifyBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // if (mobileNumber.trim().length() > 2) {
                OTPstr = otpET.getText().toString();
                boolean isNetAvailable = Utility.isOnline(VerificationActivity.this);
                if (isNetAvailable) {
                    if (OTPstr.trim().length() > 0) {
                        if (OTPstr.length() >= 6) {
                            try {
                                ringProgressDialog = ProgressDialog.show(VerificationActivity.this, "Please wait ...", "Verifying OTP...", true);
                                ringProgressDialog.setCancelable(true);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            RequestParams params = new RequestParams();
                            params.put("mobile", mobileNumber);
                            params.put("otp", OTPstr);
                            //startActivity(new Intent(VerificationActivity.this, MainActivity.class));
                            sendOtpRequesttoServer(AppConstants.GET_AUTH_OTP_URL, params);
                        }
                        else {
                            //Utility.showCustomToast(getResources().getString(R.string.otp_must_not_empty), VerificationActivity.this);
                            snackbar = Snackbar.make(coordinatorLayout, getResources().getString(R.string.enter_valid_otp), Snackbar.LENGTH_LONG);
                            //snackbar.show();
                            //snackbar = Snackbar.make(coordinatorLayout, getResources().getString(R.string.invalid_no), Snackbar.LENGTH_LONG);
                            snackbarMethod();
                        }
                    } else {
                        //Utility.showCustomToast(getResources().getString(R.string.otp_must_not_empty), VerificationActivity.this);
                        snackbar = Snackbar.make(coordinatorLayout, getResources().getString(R.string.otp_must_not_empty), Snackbar.LENGTH_LONG);
                        //snackbar.show();
                        snackbarMethod();
                    }
                } else {
                    //Utility.showCustomToast(getResources().getString(R.string.pls_connect_internet), VerificationActivity.this);
                    snackbar = Snackbar.make(coordinatorLayout, getResources().getString(R.string.pls_connect_internet), Snackbar.LENGTH_LONG);
                    //snackbar.show();
                    snackbarMethod();
                }
                    /*} else {
                        startActivity(new Intent(VerificationActivity.this, RegistrationActivity.class));
                        onBackPressedAnimationByCHK();
                        Utility.showCustomToast(getResources().getString(R.string.pls_enter_mbl_no_for_otp), VerificationActivity.this);
                    }*/


                imm.hideSoftInputFromWindow(otpET.getWindowToken(), 0);
            }
        });

        resendLLID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendingOtp();
            }
        });
        refreshBtnID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendingOtp();
            }
        });
    }

        private void snackbarMethod() {
            group = (ViewGroup) snackbar.getView();
            group.setBackgroundColor(ContextCompat.getColor(VerificationActivity.this, R.color.snack_bar_bg));
            snackbar.show();
        }

    private void resendingOtp() {
        boolean isNetAvailable = Utility.isOnline(VerificationActivity.this);
        if (isNetAvailable) {
            /*try {
                ringProgressDialog = ProgressDialog.show(VerificationActivity.this, getResources().getString(R.string.resend), null, true);
                ringProgressDialog.setCancelable(true);
            } catch (Exception e) {
                e.printStackTrace();
            }*/
           // Utility.showCustomToast(getResources().getString(R.string.resend), VerificationActivity.this);
            snackbar = Snackbar.make(coordinatorLayout, getResources().getString(R.string.resend), Snackbar.LENGTH_LONG);
            //snackbar.show();
            snackbarMethod();
            RequestParams params = new RequestParams();
            params.put("email", emailStr);
            params.put("mobile", mobileNumber);
            resendOtpRequesttoServer(AppConstants.LOGIN_URL, params);
        } else {
            //Utility.showCustomToast(getResources().getString(R.string.pls_connect_internet), VerificationActivity.this);
            snackbar = Snackbar.make(coordinatorLayout, getResources().getString(R.string.pls_connect_internet), Snackbar.LENGTH_LONG);
            //snackbar.show();
            snackbarMethod();
        }
    }


    public void setupNavigation() {
        {
            RelativeLayout headerImage = (RelativeLayout) findViewById(R.id.headerRLID);
            headerImage.getLayoutParams().height = (int) (Utility.screenHeight / 10.2);

            RelativeLayout backAllRL = (RelativeLayout) findViewById(R.id.backallRLID);
            /*backAllRL.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (COMING_FROM.equalsIgnoreCase("Existing")) {
                        startActivity(new Intent(VerificationActivity.this, VerificationExistingActivity.class));
                    } else {
                        startActivity(new Intent(VerificationActivity.this, RegistrationActivity.class));
                    }
                    onBackPressedAnimationByCHK();
                }
            });*/

            TextView titleTV = (TextView) findViewById(R.id.titleTVID);
            titleTV.setText("VERIFICATION");

            TextView subTitleTV = (TextView) findViewById(R.id.titleSubTVID);
            subTitleTV.setVisibility(View.GONE);

            Button backBtn = (Button) findViewById(R.id.backBtnID);
            backBtn.getLayoutParams().width = (int) (Utility.screenHeight / 20.0);
            backBtn.getLayoutParams().height = (int) (Utility.screenHeight / 20.0);
            backBtn.setVisibility(View.VISIBLE);
            backBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (COMING_FROM.equalsIgnoreCase("Existing")) {
                        //startActivity(new Intent(VerificationActivity.this, VerificationExistingActivity.class));
                    } else {
                        startActivity(new Intent(VerificationActivity.this, RegistrationActivity.class));
                    }
                    onBackPressedAnimationByCHK();
                }
            });

            Button moreBtn = (Button) findViewById(R.id.moreBtnID);
            moreBtn.getLayoutParams().width = (int) (Utility.screenHeight / 20.0);
            moreBtn.getLayoutParams().height = (int) (Utility.screenHeight / 20.0);
        }
    }

    public void sendOtpRequesttoServer(String _Url, RequestParams params) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(_Url, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(String response) {
                if (response != null) {
                    System.out.println(response);
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        if (success) {
                            if (ringProgressDialog != null) {
                                ringProgressDialog.dismiss();
                                ringProgressDialog = null;
                            }
                            JSONArray courtDetails = jsonResponse.getJSONArray("userDetails");
                            _sportsAndCenterListData = new ArrayList<SportsAndCenterListData>();
                            /*for(bookings : booking){
                                for(booking : bookingObj) {

                                }
                                }
                            }*/
                            for (int j = 0; j < courtDetails.length(); j++) {
                                JSONObject courtDetailsOBJ = courtDetails.getJSONObject(0);
                                String userId = courtDetailsOBJ.optString("userId").toString(); //13
                                String fullname = courtDetailsOBJ.optString("fullname").toString(); //Test
                                String email = courtDetailsOBJ.optString("email").toString(); //test@gmail.com
                                String facilityId = courtDetailsOBJ.optString("facilityId").toString();
                                String facilityName = courtDetailsOBJ.optString("facilityName").toString();
                                String playces_code = courtDetailsOBJ.optString("playces_code").toString();
                                String location = courtDetailsOBJ.optString("location");
                                //_sportsAndCenterListData.add(new SportsAndCenterListData(facilityId, facilityName, playces_code, location));
                                SharedPreference.setPreference(getApplicationContext(), "KEY_MOBILE_NO", mobileNumber);
                                SharedPreference.setPreference(getApplicationContext(), "KEY_FULL_NAME", fullname);
                                SharedPreference.setPreference(getApplicationContext(), "EMAIL", email);
                                SharedPreference.setPreference(getApplicationContext(), "OTP", OTPstr);
                                SharedPreference.setPreference(getApplicationContext(), "KEY_FACILITY_ID", facilityId);
                                SharedPreference.setPreference(getApplicationContext(), "USER_ID", userId);
                                SharedPreference.setPreference(getApplicationContext(), "PLAYCES_CODE", playces_code);
                                SharedPreference.setPreference(getApplicationContext(), "LOCATION", location);
                                SharedPreference.setPreference(getApplicationContext(), "KEY_NOT_SELECT", "SPINNER");
                                SharedPreference.setPreference(getApplicationContext(), "KEY_FACILITY_NAME", facilityName);
                            }
                            //startActivity(new Intent(VerificationActivity.this, MainActivity.class));
                            String deviceId = Settings.Secure.getString(VerificationActivity.this.getContentResolver(), Settings.Secure.ANDROID_ID);
                            dbHelper.addRegisteredUDID(deviceId);
                            Intent i = new Intent(VerificationActivity.this, MainActivity.class);
                            //i.putExtra(Utility.DASHBOARD_ICON_ID, 2);
                            i.putExtra("AVAILABLE_SPINNER", "HOME");
                            startActivity(i);
                            VerificationActivity.this.finish();
                        } else {
                            if (ringProgressDialog != null) {
                                ringProgressDialog.dismiss();
                                ringProgressDialog = null;
                            }
                            String message = jsonResponse.optString("message").toString();
                            //Utility.showCustomToast(message, VerificationActivity.this);
                            snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG);
                            //snackbar.show();
                            snackbarMethod();
                        }
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
                    //Utility.showCustomToast(getResources().getString(R.string.request_not_found), VerificationActivity.this);
                    snackbar = Snackbar.make(coordinatorLayout, getResources().getString(R.string.request_not_found), Snackbar.LENGTH_LONG);
                    //snackbar.show();
                    snackbarMethod();
                } else if (statusCode == 500) {
                    //Utility.showCustomToast(getResources().getString(R.string.some_went_wrong), VerificationActivity.this);
                    snackbar = Snackbar.make(coordinatorLayout, getResources().getString(R.string.request_not_found), Snackbar.LENGTH_LONG);
                    //snackbar.show();
                    snackbarMethod();
                } else {
                    //Utility.showCustomToast(getResources().getString(R.string.unexpected_error), VerificationActivity.this);
                    snackbar = Snackbar.make(coordinatorLayout, getResources().getString(R.string.unexpected_error), Snackbar.LENGTH_LONG);
                    //snackbar.show();
                    snackbarMethod();
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    private void onBackPressedAnimationByCHK() {
        finish();
        overridePendingTransition(R.anim.act_pull_in_left, R.anim.act_push_out_right);
    }

    public void resendOtpRequesttoServer(String _Url, RequestParams params) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(_Url, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(String response) {
                if (response != null) {
                    System.out.println(response);
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        String message = jsonResponse.optString("message").toString();
                        boolean success = jsonResponse.getBoolean("success");
                        if (success) {
                            /*if (ringProgressDialog != null) {
                                ringProgressDialog.dismiss();
                                ringProgressDialog = null;
                            }*/
                            //Utility.showCustomToast(message, VerificationActivity.this);
                            /*snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG);
                            snackbar.show();*/
                            /*Intent verifyIntent = new Intent(VerificationActivity.this, VerificationActivity.class);
                            verifyIntent.putExtra(Utility.KEY_PHONE_NUM, mobileNumber);
                            verifyIntent.putExtra(Utility.KEY_EMAIL_ID, emailStr);
                            startActivity(verifyIntent);
                            finish();*/
                        } else {
                            //Utility.showCustomToast(message, VerificationActivity.this);
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
                /*if (ringProgressDialog != null) {
                    ringProgressDialog.dismiss();
                    ringProgressDialog = null;
                }*/
                if (statusCode == 404) {
                    //Utility.showCustomToast(getResources().getString(R.string.request_not_found), VerificationActivity.this);
                    snackbar = Snackbar.make(coordinatorLayout, getResources().getString(R.string.request_not_found), Snackbar.LENGTH_LONG);
                    //snackbar.show();
                    snackbarMethod();
                } else if (statusCode == 500) {
                    //Utility.showCustomToast(getResources().getString(R.string.some_went_wrong), VerificationActivity.this);
                    snackbar = Snackbar.make(coordinatorLayout, getResources().getString(R.string.some_went_wrong), Snackbar.LENGTH_LONG);
                    //snackbar.show();
                    snackbarMethod();
                } else {
                    //Utility.showCustomToast(getResources().getString(R.string.unexpected_error), VerificationActivity.this);
                    snackbar = Snackbar.make(coordinatorLayout, getResources().getString(R.string.unexpected_error), Snackbar.LENGTH_LONG);
                    //snackbar.show();
                    snackbarMethod();
                }
            }
        });
    }
}

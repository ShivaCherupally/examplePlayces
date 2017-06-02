package in.playcerteam.playces;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.jsoup.Jsoup;

import in.playcerteam.playces.utilities.AppDataBaseHelper;
import in.playcerteam.playces.utilities.SharedPreference;
import in.playcerteam.playces.utilities.Utility;

/**
 * Developed by HARIKRISHNA on 9/18/2015 at Carettech.
 */
public class SplashScreenActivity extends Activity {
    public static final int SPLASH_TIME_OUT = 1500;
    private AppDataBaseHelper dbHelper = new AppDataBaseHelper(this);

    LinearLayout noNetLL;
    int repeat = 1;
    ProgressDialog ringProgressDialog;
    Integer currentAPILevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            noNetLL = (LinearLayout) findViewById(R.id.noNetworkConnectRLID);
            currentAPILevel = Integer.valueOf(android.os.Build.VERSION.SDK);
        } catch (Exception e) {
            if (e != null) {
                e.printStackTrace();
                Log.w("HARI-->", e);
            }
        }

        if (Utility.isOnline(SplashScreenActivity.this)) {
            boolean newVersion = false;
            if (currentAPILevel >= 8) {
                //newVersion = web_update();
                if (newVersion == true) {
                    showDialog(12227);
                } else {
                    netWorkISAvailableHere();
                }
            } else {
                netWorkISAvailableHere();
            }
        } else {
            String udid = dbHelper.getRegisteredDeviceID();
            if (udid != null) {
                String deviceId = Settings.Secure.getString(SplashScreenActivity.this.getContentResolver(), Settings.Secure.ANDROID_ID);
                SharedPreference.setPreference(SplashScreenActivity.this, "DEVICE_ID", deviceId);
                if (udid.equals(deviceId)) {
                    alreadyRegisteredAvailableHere();
                } else {
                    if (Utility.isOnline(SplashScreenActivity.this)) {
                        netWorkISAvailableHere();
                    } else {
                        noInterenetUnregistered();
                    }
                }
            } else {
                if (Utility.isOnline(SplashScreenActivity.this)) {
                    netWorkISAvailableHere();
                } else {
                    noInterenetUnregistered();
                }
            }
        }
    }


    private void noInterenetUnregistered() {
        noNetLL.setVisibility(View.VISIBLE);
        Button noNetWorkTryAgainBtn = (Button) findViewById(R.id.noNetWorkTryAgainBtnID);
        noNetWorkTryAgainBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                noNetLL.setVisibility(View.GONE);
                ringProgressDialog = ProgressDialog.show(SplashScreenActivity.this, "", "Loading wait...", true);
                ringProgressDialog.setCancelable(true);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        if (Utility.isOnline(SplashScreenActivity.this)) {
                            netWorkISAvailableHere();
                        } else {
                            ringProgressDialog.dismiss();
                            noNetLL.setVisibility(View.VISIBLE);
                        }
                    }
                }, 500);
            }
        });
    }

    private void netWorkISAvailableHere() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                noNetLL.setVisibility(View.GONE);
                String deviceId = Settings.Secure.getString(SplashScreenActivity.this.getContentResolver(), Settings.Secure.ANDROID_ID);
                String udid = dbHelper.getRegisteredDeviceID();
                if (udid != null) {
                   // String deviceId = Settings.Secure.getString(SplashScreenActivity.this.getContentResolver(), Settings.Secure.ANDROID_ID);
                    //DEVICE_ID
                    SharedPreference.setPreference(SplashScreenActivity.this, "DEVICE_ID", deviceId);
                    if (udid.equals(deviceId)) {
                        // Intent intent = new Intent(SplashScreenActivity.this, DashboardActivity.class);
                        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                        startActivity(intent);
                        SplashScreenActivity.this.finish();
                    } else {
                        Intent intent = new Intent(SplashScreenActivity.this, RegistrationActivity.class);
                        startActivity(intent);
                        SplashScreenActivity.this.finish();
                    }
                } else {
                    SharedPreference.setPreference(SplashScreenActivity.this, "DEVICE_ID", deviceId);
                    Intent intent = new Intent(SplashScreenActivity.this, RegistrationActivity.class);
                    startActivity(intent);
                    SplashScreenActivity.this.finish();
                }

                if (ringProgressDialog != null) {
                    ringProgressDialog.dismiss();
                    ringProgressDialog = null;
                }
            }
        }, SPLASH_TIME_OUT);// delay in milliseconds (1500)
    }

    private void alreadyRegisteredAvailableHere() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                noNetLL.setVisibility(View.GONE);
                //Intent intent = new Intent(SplashScreenActivity.this, DashboardActivity.class);
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
                SplashScreenActivity.this.finish();
            }
        }, SPLASH_TIME_OUT);// delay in milliseconds (1500)
    }

 /*   @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 12227) {
            AlertDialog callMobiledialog = null;
            LayoutInflater liYes = LayoutInflater.from(this);
            View callAddressView = liYes.inflate(R.layout.dialog_layout_update_version, null);
            AlertDialog.Builder adbrok = new AlertDialog.Builder(this);
            adbrok.setView(callAddressView);
            adbrok.setCancelable(false);
            callMobiledialog = adbrok.create();
            callMobiledialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationByHari;
            callMobiledialog.show();
            return callMobiledialog;
        }
        return super.onCreateDialog(id);
    }*/

    /*@Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        switch (id) {
            case 12227:
                final AlertDialog alt3 = (AlertDialog) dialog;
                TextView alertTitle = (TextView) alt3.findViewById(R.id.favGINTitleTVID);

               // TextView tv22 = (TextView) alt3.findViewById(R.id.addFavTVID);
                //tv22.setText(getResources().getString(R.string.update_version_message));
                Button addFavYesBtn = (Button) alt3.findViewById(R.id.add_fav_yesBtnID);
                Button addNoFavBtn = (Button) alt3.findViewById(R.id.add_fav_noBtnID);
                addFavYesBtn.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        try {
                            final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                            try {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                SplashScreenActivity.this.finish();
                            } catch (android.content.ActivityNotFoundException anfe) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
                                SplashScreenActivity.this.finish();
                            }
                            SplashScreenActivity.this.finish();
                        } catch (Exception e) {
                            if (e != null) {
                                e.printStackTrace();
                                Log.w("Hari-->", e);
                            }
                        }
                        alt3.dismiss();
                    }
                });
                addNoFavBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alt3.dismiss();
                        netWorkISAvailableHere();
                    }
                });
                break;
        }

        super.onPrepareDialog(id, dialog);
    }*/

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
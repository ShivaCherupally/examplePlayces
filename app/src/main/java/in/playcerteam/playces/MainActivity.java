package in.playcerteam.playces;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import in.playcerteam.playces.model.SportAvailableListData;
import in.playcerteam.playces.utilities.AppConstants;
import in.playcerteam.playces.utilities.SharedPreference;
import in.playcerteam.playces.utilities.Utility;

public class MainActivity extends TabActivity implements View.OnClickListener {
    Button Btn1, Btn2, Btn3, Btn4, Btn5;
    LinearLayout accountLLID, availabiltyLLID, bookingLLID;
    TextView profileTVID, availabiltyTVID, bookingTVID;
    TabHost tabHost;
    public static LinearLayout threeiconLLID;

    TabHost.TabSpec tabSpec1, tabSpec2, tabSpec3, tabSpec4, tabSpec5;


    private int countList = 0;

    //date Format Code
    private static final int DATE_DIALOG_ID = 0;
    Calendar c;
    int mYear;
    int mMonth;
    int mDay;
    String dayLongName = "";
    Date date;
    SimpleDateFormat sdf;
    String dayOfTheWeek = "";
    String monthName = "";
    String monthNo = "";
    String datestr;
    String daydate;
    String previousDateStr = "";
    ArrayList<String> sportTitle = new ArrayList<String>();
    ArrayList<SportAvailableListData> _sportavailableListData = new ArrayList<SportAvailableListData>();

    private static int SPLASH_TIME_OUT = 1000;
    TextView availableDateTVID;
    TextView sports_locationTVID;
    Spinner sportsSPID;
    RelativeLayout spiAndCalRLID;
    String sports_id;
    String playceCodeAndLocation = "";
    String spinnerItemSelected = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Utility.setDimensions(this);

        //setupNavigation();

        accountLLID = (LinearLayout) findViewById(R.id.accountLLID);
        availabiltyLLID = (LinearLayout) findViewById(R.id.availabiltyLLID);
        bookingLLID = (LinearLayout) findViewById(R.id.bookingLLID);

        threeiconLLID = (LinearLayout) findViewById(R.id.threeiconLLID);

        sports_locationTVID = (TextView) findViewById(R.id.sports_locationTVID);
        playceCodeAndLocation = "" + SharedPreference.getPreferences(MainActivity.this, "PLAYCES_CODE") + " - " + SharedPreference.getPreferences(MainActivity.this, "LOCATION");
        sports_locationTVID.setText("" + playceCodeAndLocation);


        Btn1 = (Button) findViewById(R.id.profileBtnID);
        Btn2 = (Button) findViewById(R.id.availabiltyBtnID);
        Btn3 = (Button) findViewById(R.id.bookingBtnID);

        profileTVID = (TextView) findViewById(R.id.profileTVID);
        availabiltyTVID = (TextView) findViewById(R.id.availabiltyTVID);
        bookingTVID = (TextView) findViewById(R.id.bookingTVID);
        setButtonsDimentions();

        accountLLID.setOnClickListener(this);
        availabiltyLLID.setOnClickListener(this);
        bookingLLID.setOnClickListener(this);

        /*Btn1.setOnClickListener(this);
        Btn2.setOnClickListener(this);
        Btn3.setOnClickListener(this);*/

        tabHost = (TabHost) findViewById(android.R.id.tabhost);
        tabSpec1 = tabHost.newTabSpec("tabSpec1");
        tabSpec2 = tabHost.newTabSpec("tabSpec2");
        tabSpec3 = tabHost.newTabSpec("tabSpec3");


        tabSpec1.setIndicator("").setContent(new Intent(this, AccountListFragment.class));
        tabSpec2.setIndicator("").setContent(new Intent(this, AvailableListFragment.class));
        tabSpec3.setIndicator("").setContent(new Intent(this, BookingListFragment.class));

        tabHost.addTab(tabSpec1);
        tabHost.addTab(tabSpec2);
        tabHost.addTab(tabSpec3);

        /*Bundle dataBundle = getIntent().getExtras();
        if (dataBundle != null) {
            spinnerItemSelected = dataBundle.getString("AVAILABLE_SPINNER");
        }*/

        getTabHost().setCurrentTabByTag("tabSpec" + 2);
        //sportsSPID.setOnClickListener(this);
        //date display


        //if (spinnerItemSelected != null) {
        /*if (spinnerItemSelected.equals("AVAILABLE")) {
        } else {*/
        sportsSPID = (Spinner) findViewById(R.id.sportsSPID);
        availableDateTVID = (TextView) findViewById(R.id.availableDateTVID);
        spiAndCalRLID = (RelativeLayout) findViewById(R.id.spiAndCalRLID);
        c = Calendar.getInstance();
        sdf = new SimpleDateFormat("dd-M-yyyy");
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        monthAndDayNamesFetch(mYear, mMonth, mDay, "");
        if (Utility.isOnline(getApplicationContext())) {
            //sportAvailbleRequest(AppConstants.SPORTS_NAMES_LIST);
        } else {
            //loadingSportsTroubleConnecting();
        }
        //}


        /*availableDateTVID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createdDialog(DATE_DIALOG_ID).show();
            }
        });*/

        /*sportsSPID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String SPINNER = SharedPreference.getPreferences(MainActivity.this, "KEY_NOT_SELECT");
                if (position != 0) {
                    sports_id = _sportavailableListData.get(sportsSPID.getSelectedItemPosition()).getSportsId();
                    //SharedPreference.setPreference(MainActivity.this, "SPORT_ID", sports_id);
                    SharedPreference.setPreference(MainActivity.this, "SELECTED_SPORT_ID", sports_id);
                    SharedPreference.setPreference(MainActivity.this, "DATE", datestr);
                    Toast.makeText(MainActivity.this, "Selected " + sports_id + datestr, Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this, MainActivity.class);
                    i.putExtra("AVAILABLE_SPINNER", "AVAILABLE");
                    startActivity(i);
                } else {
                    sports_id = _sportavailableListData.get(sportsSPID.getSelectedItemPosition()).getSportsId();
                    SharedPreference.setPreference(MainActivity.this, "SPORT_ID", sports_id);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.w("HARI-->", "Called onNothingSelected()");
            }
        });
        */
    }

    private void setButtonsDimentions() {
        setButtonDimentions(Btn1);
        setButtonDimentions(Btn2);
        setButtonDimentions(Btn3);
        //setButtonDimentions(Btn4);
        //setButtonDimentions(Btn5);
    }

    private void setButtonDimentions(Button Btn) {
        Btn.getLayoutParams().width = (int) (Utility.screenWidth / 20.0);
        Btn.getLayoutParams().height = (int) (Utility.screenWidth / 20.0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //case R.id.profileBtnID:
            case R.id.accountLLID:
                // profileViewClick();
                if (Utility.isOnline(this)) {
                    getTabHost().setCurrentTabByTag("tabSpec1");
                    profileTVID.setTextColor(this.getResources().getColor(R.color.black));
                    Btn1.setBackground(getDrawable(R.drawable.profile_dark));

                    availabiltyTVID.setTextColor(this.getResources().getColor(R.color.grey));
                    Btn2.setBackground(getDrawable(R.drawable.available));

                    bookingTVID.setTextColor(this.getResources().getColor(R.color.grey));
                    Btn3.setBackground(getDrawable(R.drawable.booking));
                    enableMenuButtons(Btn1);

                    availableDateTVID.setVisibility(View.GONE);
                    sports_locationTVID.setVisibility(View.GONE);
                    sportsSPID.setVisibility(View.GONE);
                    spiAndCalRLID.setVisibility(View.GONE);
                } else {
                    // The Custom Toast Layout Imported here
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toast_no_netowrk,
                            (ViewGroup) findViewById(R.id.custom_toast_layout_id));

                    // The actual toast generated here.
                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
                }
                break;
            //case R.id.availabiltyBtnID:
            case R.id.availabiltyLLID:
                //availbleViewClick();
                if (Utility.isOnline(this)) {
                    getTabHost().setCurrentTabByTag("tabSpec2");
                    availabiltyTVID.setTextColor(this.getResources().getColor(R.color.black));
                    Btn2.setBackground(getDrawable(R.drawable.available_dark));

                    profileTVID.setTextColor(this.getResources().getColor(R.color.grey));
                    Btn1.setBackground(getDrawable(R.drawable.profile));

                    bookingTVID.setTextColor(this.getResources().getColor(R.color.grey));
                    Btn3.setBackground(getDrawable(R.drawable.booking));

                    /*availableDateTVID.setVisibility(View.VISIBLE);
                    sports_locationTVID.setVisibility(View.VISIBLE);
                    sportsSPID.setVisibility(View.VISIBLE);
                    spiAndCalRLID.setVisibility(View.VISIBLE);*/
                    enableMenuButtons(Btn2);
                } else {
                    // The Custom Toast Layout Imported here
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toast_no_netowrk,
                            (ViewGroup) findViewById(R.id.custom_toast_layout_id));

                    // The actual toast generated here.
                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
                }
                break;
            //case R.id.bookingBtnID:
            case R.id.bookingLLID:
                //bookingViewClick();
                if (Utility.isOnline(this)) {
                    getTabHost().setCurrentTabByTag("tabSpec3");
                    Btn3.setBackground(getDrawable(R.drawable.booking_dark));
                    bookingTVID.setTextColor(this.getResources().getColor(R.color.black));

                    profileTVID.setTextColor(this.getResources().getColor(R.color.grey));
                    Btn1.setBackground(getDrawable(R.drawable.profile));

                    availabiltyTVID.setTextColor(this.getResources().getColor(R.color.grey));
                    Btn2.setBackground(getDrawable(R.drawable.available));

                    /*availableDateTVID.setVisibility(View.VISIBLE);
                    sports_locationTVID.setVisibility(View.VISIBLE);
                    sportsSPID.setVisibility(View.VISIBLE);
                    spiAndCalRLID.setVisibility(View.VISIBLE);*/
                    enableMenuButtons(Btn3);
                } else {
                    // The Custom Toast Layout Imported here
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toast_no_netowrk,
                            (ViewGroup) findViewById(R.id.custom_toast_layout_id));

                    // The actual toast generated here.
                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
                }
                break;
        }
    }

    private void enableMenuButtons(Button disableBtn) {
        Btn1.setEnabled(true);
        Btn2.setEnabled(true);
        Btn3.setEnabled(true);
        // Btn4.setEnabled(true);
        // Btn5.setEnabled(true);
        disableBtn.setEnabled(false);
    }

    public void setupNavigation() {
        {
            RelativeLayout headerImage = (RelativeLayout) findViewById(R.id.headerRLID);
            headerImage.getLayoutParams().height = (int) (Utility.screenHeight / 15.0);

            /*RelativeLayout backAllRL = (RelativeLayout) findViewById(R.id.backallRLID);
            backAllRL.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    onBackPressedAnimationByCHK();
                }
            });*/
            Button backBtn = (Button) findViewById(R.id.backBtnID);
            backBtn.getLayoutParams().width = (int) (Utility.screenWidth / 10.5);
            backBtn.getLayoutParams().height = (int) (Utility.screenHeight / 10.5);
            backBtn.setVisibility(View.VISIBLE);
        }
    }

    private void onBackPressedAnimationByCHK() {
        finish();
        overridePendingTransition(R.anim.act_pull_in_left, R.anim.act_push_out_right);
    }

    protected void sportAvailbleRequest(String sportsUrl) {
        // Make RESTful Web Service call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("facilityId", SharedPreference.getPreferences(MainActivity.this, "KEY_FACILITY_ID"));
        client.post(sportsUrl, params, new AsyncHttpResponseHandler() {
            //client.get(registrationUrl1, null, new AsyncHttpResponseHandler() {
            // When the response returned by REST has HTTP response code // '200'
            public void onSuccess(String response) {
                // Hide Progress Dialog
                try {
                    //progressWheel_CENTER_SPORTS.setVisibility(View.VISIBLE);
                    JSONArray jArray = new JSONArray(response);
                    countList = jArray.length();
                    sportTitle.clear();
                    _sportavailableListData.clear();
                    if (countList != 0) {
                        _sportavailableListData = new ArrayList<SportAvailableListData>();
                    }
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject jsnobj = jArray.getJSONObject(i);
                        String p_type_id = jsnobj.getString("id");
                        String p_type = jsnobj.getString("name");
                        _sportavailableListData.add(new SportAvailableListData(p_type_id, p_type));
                    }
                    if (_sportavailableListData.size() > 0) {
                        for (int i = 0; i < _sportavailableListData.size(); i++) {
                            sportTitle.add(_sportavailableListData.get(i).getSportsTitle());
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            if (countList != 0) {
                                /*if (progressWheel_CENTER_SPORTS != null) {
                                    progressWheel_CENTER_SPORTS.setVisibility(View.GONE);
                                }*/
                                if (getApplication() != null) {
                                    sportsSPID.setAdapter(new ArrayAdapter<String>(MainActivity.this, R.layout.dropdowntext, sportTitle));
                                    sports_id = _sportavailableListData.get(sportsSPID.getSelectedItemPosition()).getSportsId();
                                    SharedPreference.setPreference(getApplicationContext(), "SPORT_ID", sports_id);
                                    //sendRequestToGetAvailbleList(AppConstants.AVAILABLE_LIST_URL);
                                } else {
                                    sportsSPID.setVisibility(View.GONE);
                                }
                            } else {
                                //progressWheel_CENTER_SPORTS.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, SPLASH_TIME_OUT);
            }

            public void onFailure(int statusCode, Throwable error, String content) {
                try {
                    /*if (progressWheel_CENTER_SPORTS != null) {
                        progressWheel_CENTER_SPORTS.setVisibility(View.GONE);
                    }*/
                    if (statusCode == 404) {
                        Utility.showCustomToast("Requested resource not found", MainActivity.this);
                    }
                    // When HTTP response code is '500'
                    else if (statusCode == 500) {
                        Utility.showCustomToast("Something went wrong at server end", MainActivity.this);
                    }
                    //When HTTP response code other than 404, 500
                    else {
                        Utility.showCustomToast("Unexpected Error occcured! Please Try Again", MainActivity.this);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    protected Dialog createdDialog(int id) {

        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(MainActivity.this, mDateSetListener, mYear, mMonth, mDay);
        }
        return null;
    }


    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            monthAndDayNamesFetch(mYear, mMonth, mDay, "");
        }
    };

    private void monthAndDayNamesFetch(int mYeartemp, int monthOfYear, int mDaytemp, String dayarrow) {
        mYear = mYeartemp;
        mMonth = monthOfYear;
        mDay = mDaytemp;

        try {
            String dateInString = Integer.toString(mDay) + "-" + "0" + Integer.toString(monthOfYear + 1) + "-" + Integer.toString(mYear);
            date = sdf.parse(dateInString);
            daydate = (String) android.text.format.DateFormat.format("dd", date);
            dayOfTheWeek = (String) android.text.format.DateFormat.format("EEE", date);
            monthName = (String) android.text.format.DateFormat.format("MMM", date);
            monthNo = (String) android.text.format.DateFormat.format("MM", date);
            availableDateTVID.setText(new StringBuilder().append(dayOfTheWeek + "," + " ").append(mDay).append(" ").append(monthName).append(" ").append(mYear));
            //Toast.makeText(getApplicationContext(), "" + monthName, Toast.LENGTH_SHORT).show();
            datestr = mYear + "-" + monthNo + "-" + daydate;
            SharedPreference.setPreference(getApplicationContext(), "DATE", datestr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Do you want to exit the application?").setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //startActivity(new Intent(AccountListFragment.this, RegistrationActivity.class));
                        finish();
                        //moveTaskToBack(true);
                        // System.exit(0);
                        //android.os.Process.killProcess(android.os.Process.myPid());
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
        //super.onBackPressed();
    }
}
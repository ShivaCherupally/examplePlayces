package in.playcerteam.playces;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.playcerteam.playces.adapters.SportAndCenterAdapter;
import in.playcerteam.playces.libs.ProgressWheel;
import in.playcerteam.playces.model.SportsAndCenterListData;
import in.playcerteam.playces.utilities.AppConstants;
import in.playcerteam.playces.utilities.AppDataBaseHelper;
import in.playcerteam.playces.utilities.SharedPreference;
import in.playcerteam.playces.utilities.Utility;

/**
 * Created by PlaycerTeam on 5/27/2016.
 */
public class AccountListFragment extends Activity {
    RecyclerView mRecyclerView;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private SportAndCenterAdapter mAdapter;
    private int countList = 0;
    private ProgressWheel progressWheel_CENTER_EVENTS;
    ArrayList<SportsAndCenterListData> _sportsAndCenterListData = new ArrayList<SportsAndCenterListData>();
    Activity activity;
    TextView noSportsAvailableTV;

    TextView userNameTVID, userEmailTVID, mobileTVID;
    TextView logoutTVID;
    Button logoutBtnID;
    RelativeLayout logoutRLID;

    AppDataBaseHelper dataBaseHelper;
    LinearLayout noNewsNetLL;
    //    View view;
    int i = 0;
    Context mContext;
    String mobileNumber;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_account_list_screen);
        activity = AccountListFragment.this;
        dataBaseHelper = new AppDataBaseHelper(getApplication());

        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        progressWheel_CENTER_EVENTS = (ProgressWheel) findViewById(R.id.progress_wheel1);
        progressWheel_CENTER_EVENTS.setBarColor(getResources().getColor(R.color.colorPrimary));
        progressWheel_CENTER_EVENTS.setRimColor(Color.LTGRAY);

        noSportsAvailableTV = (TextView) findViewById(R.id.noSportsAvailableTVID);

        userNameTVID = (TextView) findViewById(R.id.userNameTVID);
        userEmailTVID = (TextView) findViewById(R.id.userEmailTVID);
        mobileTVID = (TextView) findViewById(R.id.mobileTVID);
        logoutTVID = (TextView) findViewById(R.id.logoutTVID);
        logoutRLID = (RelativeLayout) findViewById(R.id.logoutRLID);

        mobileNumber = Utility.KEY_PHONE_NUM;

        userNameTVID.setText("" + SharedPreference.getPreferences(AccountListFragment.this, "KEY_FULL_NAME"));
        userEmailTVID.setText("" + SharedPreference.getPreferences(AccountListFragment.this, "EMAIL"));
        mobileTVID.setText("+91 " + SharedPreference.getPreferences(AccountListFragment.this, "KEY_MOBILE_NO"));

        /*try {
            MainActivity.availableDateTVID.setVisibility(View.GONE);
            MainActivity.sports_locationTVID.setVisibility(View.GONE);
            MainActivity.sportsSPID.setVisibility(View.GONE);
        }
        catch (Exception e){
            e.printStackTrace();
        }*/


        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).color(this.getResources().getColor(R.color.transparent)).sizeResId(R.dimen.divider).marginResId(R.dimen.leftmargin, R.dimen.rightmargin).build());

        logoutRLID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppDataBaseHelper dbnew = new AppDataBaseHelper(AccountListFragment.this);
                dbnew.deleteEntry(SharedPreference.getPreferences(AccountListFragment.this, "DEVICE_ID"));
                startActivity(new Intent(AccountListFragment.this, RegistrationActivity.class));
                /*AlertDialog.Builder builder = new AlertDialog.Builder(AccountListFragment.this);
                builder.setMessage("Are you sure you want to Logout?").setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(AccountListFragment.this, RegistrationActivity.class));
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();*/
            }
        });

        try {
            mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(mStaggeredGridLayoutManager);
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (Utility.isOnline(AccountListFragment.this)) {
            ifNetisAvilableSports();
        } else {
            loadingSportsTroubleConnecting();
        }

        logoutBtnID = (Button) findViewById(R.id.logoutBtnID);
        logoutTVID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i == 0) {
                    logoutRLID.setVisibility(View.VISIBLE);
                    Animation slide_down = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
                    logoutRLID.startAnimation(slide_down);
                    i = 1;
                } else {
                    logoutRLID.setVisibility(View.GONE);
                    i =0;
                }

            }
        });
    }

    private void loadingSportsTroubleConnecting() {
        noNewsNetLL = (LinearLayout) findViewById(R.id.noNetworkConnectRLID);
        noNewsNetLL.setVisibility(View.VISIBLE);
        Button noNetWorkTryAgainBtn = (Button) findViewById(R.id.noNetWorkTryAgainBtnID);
        noNetWorkTryAgainBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                noNewsNetLL.setVisibility(View.GONE);
                progressWheel_CENTER_EVENTS.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        if (Utility.isOnline(getApplicationContext())) {
                            ifNetisAvilableSports();
                        } else {
                            progressWheel_CENTER_EVENTS.setVisibility(View.GONE);
                            noNewsNetLL.setVisibility(View.VISIBLE);
                        }
                    }
                }, 500);
            }
        });
    }

    private void ifNetisAvilableSports() {
        sendRequestToGetSportsList(AppConstants.USER_DETAILS_URL + SharedPreference.getPreferences(AccountListFragment.this, "USER_ID"));
    }

    private void sendRequestToGetSportsList(String eventsListUrl) {
        AsyncHttpClient client = new AsyncHttpClient();
        // RequestParams params = new RequestParams();
        // params.put("mobile", SharedPreference.getPreferences(AccountListFragment.this, "KEY_MOBILE_NO"));
        // params.put("otp", SharedPreference.getPreferences(AccountListFragment.this, "OTP"));
        client.get(eventsListUrl, null, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(String response) {
                JSONArray sportsList;
                if (response != null) {
                    mRecyclerView.setVisibility(View.VISIBLE);
                    try {
                        System.out.println(response);

                        String OutputData = "";
                        JSONObject jsonResponse;

                        try {
                            //  jsonResponse = new JSONObject(response);
                            JSONArray jsonMainNode = new JSONArray(response);
                            countList = jsonMainNode.length();
                            if (countList != 0) {
                                _sportsAndCenterListData = new ArrayList<SportsAndCenterListData>();

                                JSONArray courtDetails = new JSONArray(response);
                                _sportsAndCenterListData = new ArrayList<SportsAndCenterListData>();

                                for (int j = 0; j < courtDetails.length(); j++) {
                                    JSONObject courtDetailsOBJ = courtDetails.getJSONObject(j);
                                    /*String userId = courtDetailsOBJ.optString("userId").toString(); //13
                                    String fullname = courtDetailsOBJ.optString("fullname").toString(); //Test
                                    String email = courtDetailsOBJ.optString("email").toString(); //test@gmail.com*/
                                    String facilityId = courtDetailsOBJ.optString("facilityId").toString();
                                    String facilityName = courtDetailsOBJ.optString("facilityName").toString();
                                    String playces_code = courtDetailsOBJ.optString("playces_code").toString();
                                    String location = courtDetailsOBJ.optString("location");
                                    sportsList = courtDetailsOBJ.getJSONArray("sportsList");
                                    _sportsAndCenterListData.add(new SportsAndCenterListData(facilityId, facilityName, playces_code, location, sportsList));
                                }
                                /************ Show Output on screen/activity **********/
                                new Handler().postDelayed(new Runnable() {

                                    @Override
                                    public void run() {
                                        // This method will be executed once the timer is over
                                        if (countList != 0) {
                                            if (activity != null) {
                                                mAdapter = new SportAndCenterAdapter(activity, _sportsAndCenterListData);
                                                mRecyclerView.setAdapter(mAdapter);
                                                mAdapter.setOnItemClickListener(onItemClickListener);
                                            } else {
                                                noSportsAvailableTV.setVisibility(View.VISIBLE);
                                            }
                                            //}
                                            progressWheel_CENTER_EVENTS.setVisibility(View.GONE);
                                        } else {
                                            progressWheel_CENTER_EVENTS.setVisibility(View.GONE);
                                            noSportsAvailableTV.setVisibility(View.VISIBLE);
                                        }
                                    }
                                }, 900);
                            } else {
                                progressWheel_CENTER_EVENTS.setVisibility(View.GONE);
                                mRecyclerView.setVisibility(View.GONE);
                                noSportsAvailableTV.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            progressWheel_CENTER_EVENTS.setVisibility(View.GONE);
                            noSportsAvailableTV.setVisibility(View.GONE);
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        progressWheel_CENTER_EVENTS.setVisibility(View.GONE);
                        noSportsAvailableTV.setVisibility(View.GONE);
                        e.printStackTrace();
                    }
                } else {
                    noSportsAvailableTV.setVisibility(View.VISIBLE);
                    progressWheel_CENTER_EVENTS.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
                if (progressWheel_CENTER_EVENTS != null) {
                    progressWheel_CENTER_EVENTS.setVisibility(View.GONE);
                }
                if (statusCode == 404) {
                    Utility.showCustomToast(getResources().getString(R.string.request_not_found), AccountListFragment.this);
                } else if (statusCode == 500) {
                    Utility.showCustomToast(getResources().getString(R.string.some_went_wrong), AccountListFragment.this);
                } else {
                    Utility.showCustomToast(getResources().getString(R.string.unexpected_error), AccountListFragment.this);
                }
            }
        });
    }


    SportAndCenterAdapter.OnItemClickListener onItemClickListener = new SportAndCenterAdapter.OnItemClickListener() {

        @Override
        public void onItemClick(View v, int position) {
            try {
                SharedPreference.setPreference(getApplicationContext(), "PLAYCES_CODE", _sportsAndCenterListData.get(position).getPlacesCode());
                SharedPreference.setPreference(getApplicationContext(), "LOCATION", _sportsAndCenterListData.get(position).getsSportsCenter());
                SharedPreference.setPreference(getApplicationContext(), "KEY_FACILITY_ID", _sportsAndCenterListData.get(position).getFacilityId());
                SharedPreference.setPreference(getApplicationContext(), "KEY_FACILITY_NAME", _sportsAndCenterListData.get(position).getFacilityName());
                Intent newsIntent = new Intent(AccountListFragment.this, MainActivity.class);
                startActivity(newsIntent);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    /*@Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AccountListFragment.this);
        builder.setMessage("Do you want to exit the application?").setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //startActivity(new Intent(AccountListFragment.this, RegistrationActivity.class));
                        finish();
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
    }*/
}


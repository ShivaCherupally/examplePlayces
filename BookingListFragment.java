package in.playcerteam.playces;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.acl.Group;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


import in.playcerteam.playces.libs.ProgressWheel;
import in.playcerteam.playces.model.BookingListData;
import in.playcerteam.playces.model.ExpandableBookingListData;
import in.playcerteam.playces.model.NestedBookingListData;
import in.playcerteam.playces.model.SportAvailableListData;
import in.playcerteam.playces.utilities.AppConstants;
import in.playcerteam.playces.utilities.AppDataBaseHelper;
import in.playcerteam.playces.utilities.SharedPreference;
import in.playcerteam.playces.utilities.Utility;

/**
 * Created by PlaycerTeam on 5/27/2016.
 */
public class BookingListFragment extends Activity implements AbsListView.OnScrollListener {
    public BookingExpandableListAdapter mBookAdapter;
    Activity activity;
    ExpandableListView bookingListViewLV;
    ArrayList<BookingListData> bookdataList;
    ArrayList<BookingListData> bookdataListTemp = new ArrayList<BookingListData>();

    ArrayList<ExpandableBookingListData> _expandableBookingListData = new ArrayList<ExpandableBookingListData>();

    ArrayList<NestedBookingListData> _nestedbookingListData = new ArrayList<NestedBookingListData>();
    ArrayList<NestedBookingListData> nestedbookingListTemp = new ArrayList<NestedBookingListData>();
    TextView noBookingsAvailableTV;
    LinearLayout noNewsNetLL;
    //View newsView;
    private ProgressWheel progressWheel_CENTER_NEWS;
    private ProgressWheel progressWheel_BOTTOM;
    //My Bookings Member variables
    private int myBookingCountList = 0;
    AppDataBaseHelper dataBaseHelper;

    int page_Num = 1;
    int booksListCount = 0;
    //private int mvisibleItemCount = -1;
    private String fetchDirectionUP = Utility.FETCH_DIRECTION_UP;
    private String fetchDirection = "";

    private static final String ARG_KEY_SORT = "arg_key_sort";
    private String sort;
    private LinearLayoutManager mStaggeredGridLayoutManager;

    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    EditText searchETID;
    Button searchBtnID;
    String searchId = "";


    String sportsId;
    String sportsName;
    String bookingId;
    JSONArray itemArray;

    //private SwipeRefreshLayout swipeRefreshLayout;

    /////////////////Date and Spinner Related Code

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
    TextView sports_locationTVID;

    TextView spinnerItemTVID;
    Spinner sportsSPID;
    String sports_id = "";
    RelativeLayout calenderRLID;
    TextView availableDateTVID;
    Button calenderFilterBtnID;
    private int countList = 0;
    InputMethodManager imm;
    boolean action;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_booking_list_screen);
        activity = BookingListFragment.this;

        progressWheel_CENTER_NEWS = (ProgressWheel) findViewById(R.id.progress_wheel1);
        progressWheel_CENTER_NEWS.setBarColor(getResources().getColor(R.color.colorPrimary));
        progressWheel_CENTER_NEWS.setRimColor(Color.LTGRAY);

        progressWheel_BOTTOM = (ProgressWheel) findViewById(R.id.progress_wheel);
        progressWheel_BOTTOM.setBarColor(getResources().getColor(R.color.colorPrimary));
        progressWheel_BOTTOM.setRimColor(Color.LTGRAY);

        noBookingsAvailableTV = (TextView) findViewById(R.id.noBookingsAvailableTVID);
        bookingListViewLV = (ExpandableListView) findViewById(R.id.bookingListViewLVID);

        searchBtnID = (Button) findViewById(R.id.searchBtnID);

        // swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        //location, Date and Spinner
        sports_locationTVID = (TextView) findViewById(R.id.sports_locationTVID);
        String playceCodeAndLocation = "" + SharedPreference.getPreferences(BookingListFragment.this, "PLAYCES_CODE") + " - " + SharedPreference.getPreferences(BookingListFragment.this, "KEY_FACILITY_NAME") + ", " + SharedPreference.getPreferences(BookingListFragment.this, "LOCATION");
        sports_locationTVID.setText("" + playceCodeAndLocation);
        spinnerItemTVID = (TextView) findViewById(R.id.spinnerItemTVID);
        sportsSPID = (Spinner) findViewById(R.id.sportsSPID);
        availableDateTVID = (TextView) findViewById(R.id.availableDateTVID);  // Availabe Dates
        calenderRLID = (RelativeLayout) findViewById(R.id.calenderRLID);  // Availabe Dates
        calenderFilterBtnID = (Button) findViewById(R.id.calenderFilterBtnID);  // Availabe Dates

        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        RelativeLayout parentPanelRL = (RelativeLayout) findViewById(R.id.parentPanelBookingRLID);
        parentPanelRL.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                imm.hideSoftInputFromWindow(searchETID.getWindowToken(), 0);
            }
        });

        searchETID = (EditText) findViewById(R.id.searchETID);
        noBookingsAvailableTV = (TextView) findViewById(R.id.noBookingsAvailableTVID);
        searchETID.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                //yourEditText. ...
                /*if (action)*/
                searchETID.getText().toString();
                MainActivity.threeiconLLID.setVisibility(View.VISIBLE);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                noBookingsAvailableTV.setVisibility(View.GONE);
                //MainActivity.threeiconLLID.setVisibility(View.GONE);
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                action = true;
                noBookingsAvailableTV.setVisibility(View.GONE);
                // MainActivity.threeiconLLID.setVisibility(View.VISIBLE);
            }
        });

        searchETID.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                noBookingsAvailableTV.setVisibility(View.GONE);
                MainActivity.threeiconLLID.setVisibility(View.GONE);
                return false;
            }
        });
        searchETID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MainActivity.threeiconLLID.setVisibility(View.VISIBLE);
            }
        });

        /*swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setRefreshing(false);*/


        if (Utility.isOnline(BookingListFragment.this)) {
            page_Num = 1;
            try {
                searchETID.setText("");
                searchId = "";
            } catch (Exception e) {
                e.printStackTrace();
            }
            progressWheel_CENTER_NEWS.setVisibility(View.VISIBLE);
            //ifNetisAvilableGetNews(BookingListFragment.this, page_Num);
            sportAvailbleRequest(AppConstants.SPORTS_NAMES_LIST);
        } else {
            progressWheel_BOTTOM.setVisibility(View.GONE);
            Utility.showCustomToast(getResources().getString(R.string.pls_connect_internet), BookingListFragment.this);
        }

        bookingListViewLV.setChildIndicator(null);
        bookingListViewLV.setChildDivider(getResources().getDrawable(R.color.transparent));
        bookingListViewLV.setDivider(getResources().getDrawable(R.color.black));
        //bookingListViewLV.setDividerHeight(10);

        //bookingListViewLV.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).color(this.getResources().getColor(R.color.grey)).sizeResId(R.dimen.divider).marginResId(R.dimen.leftmargin, R.dimen.rightmargin).build());
        /*swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);
                                        try {
                                            searchETID.setText("");
                                            searchId = "";
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        if (Utility.isOnline(BookingListFragment.this)) {
                                            //page_Num = page_Num + 1;
                                            page_Num = 1;
                                            ifNetisAvilableGetNews(BookingListFragment.this, page_Num);
                                        } else {
                                            progressWheel_BOTTOM.setVisibility(View.GONE);
                                            Utility.showCustomToast(getResources().getString(R.string.pls_connect_internet), BookingListFragment.this);
                                        }
                                        //fetchMovies();
                                    }
                                }
        );*/

        ///Date and Spinner Relaeted Code
        //date display
        c = Calendar.getInstance();
        sdf = new SimpleDateFormat("dd-M-yyyy");
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        //monthAndDayNamesFetch(mYear, mMonth, mDay, "");

        availableDateTVID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createdDialog(DATE_DIALOG_ID).show();
                searchETID.setText("");
            }
        });

        calenderRLID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createdDialog(DATE_DIALOG_ID).show();
                searchETID.setText("");
            }
        });
        calenderFilterBtnID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createdDialog(DATE_DIALOG_ID).show();
                searchETID.setText("");
            }
        });


        sportsSPID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int arg2, long arg3) {
                sports_id = _sportavailableListData.get(sportsSPID.getSelectedItemPosition()).getSportsId();
                searchId = "";
                searchETID.setText("");
                //if (!sports_id.equals("0")) {
                progressWheel_CENTER_NEWS.setVisibility(View.VISIBLE);
                SharedPreference.setPreference(BookingListFragment.this, "SPORT_ID", sports_id);
                SharedPreference.setPreference(BookingListFragment.this, "SEARCH", "SEARCH1");
                if (Utility.isOnline(BookingListFragment.this)) {
                    page_Num = 1;
                    try {
                        searchETID.setText("");
                        searchId = "";
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    progressWheel_CENTER_NEWS.setVisibility(View.VISIBLE);
                    ifNetisAvilableGetNews(BookingListFragment.this, page_Num);
                } else {
                    getNewsTroubleConnecting(activity);
                }
                //}
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        searchBtnID.setOnClickListener(new View.OnClickListener()

                                       {
                                           @Override
                                           public void onClick(View v) {
                                               searchId = searchETID.getText().toString();
                                               if (searchId.trim().length() > 0) {
                                                   try {
                                                       progressWheel_CENTER_NEWS.setVisibility(View.VISIBLE);
                                                       if (Utility.isOnline(BookingListFragment.this)) {
                                                           //int tmppage_Num ;
                                                           SharedPreference.setPreference(getApplicationContext(), "SEARCH", "SEARCH");
                                                           mBookAdapter = null;
                                                           ifNetisAvilableGetNews(activity, 0);
                                                       } else {
                                                           getNewsTroubleConnecting(activity);
                                                       }
                                                   } catch (Exception e) {
                                                       e.printStackTrace();
                                                   }
                                               } else {
                                                   Utility.showCustomToast(getResources().getString(R.string.enter_booking_id), BookingListFragment.this);
                                               }
                                               imm.hideSoftInputFromWindow(searchETID.getWindowToken(), 0);
                                           }

                                       }

        );

    }


    private void getNewsTroubleConnecting(final Activity _activity) {
        noNewsNetLL = (LinearLayout) findViewById(R.id.noNetworkConnectRLID);
        noNewsNetLL.setVisibility(View.VISIBLE);
        Button noNetWorkTryAgainBtn = (Button) findViewById(R.id.noNetWorkTryAgainBtnID);
        noNetWorkTryAgainBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                noNewsNetLL.setVisibility(View.GONE);
                progressWheel_CENTER_NEWS.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        if (Utility.isOnline(getApplicationContext())) {
                            page_Num = 1;
                            ifNetisAvilableGetNews(_activity, page_Num);
                        } else {
                            progressWheel_CENTER_NEWS.setVisibility(View.GONE);
                            noNewsNetLL.setVisibility(View.VISIBLE);
                        }
                    }
                }, 500);
            }
        });
    }

    public final void ifNetisAvilableGetNews(Activity _activity, int _page_Num) {
        sendRequestToGetBookingList(AppConstants.BOOKING_LIST_URL, _activity, _page_Num);
    }

    private void sendRequestToGetBookingList(String getBbookingListUrl, final Activity _activity, int page_no) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("facilityId", SharedPreference.getPreferences(BookingListFragment.this, "KEY_FACILITY_ID"));
        if (!SharedPreference.getPreferences(BookingListFragment.this, "SEARCH").equals("SEARCH")) {
            params.put("page_no", page_no);
        }
        if (!sports_id.equals("0")) {
            params.put("sport_id", sports_id);
        }
        if (!SharedPreference.getPreferences(BookingListFragment.this, "KEY_BOOK_DATE").equals("")) {
            params.put("date", SharedPreference.getPreferences(BookingListFragment.this, "KEY_BOOK_DATE"));
        }
        if (searchId != null) {
            params.put("booking_id", searchId);
        }
        client.post(getBbookingListUrl, params, new AsyncHttpResponseHandler() {
            //client.post(getBbookingListUrl, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(String Serverresponse) {
                try {
                    System.out.println(Serverresponse);
                    myBookingCountList = Serverresponse.length();
                    String OutputData = "";
                    try {
                        if (myBookingCountList != 0) {
                            bookingListViewLV.setVisibility(View.VISIBLE);
                            noBookingsAvailableTV.setVisibility(View.GONE);
                            _expandableBookingListData.clear();
                            mBookAdapter = null;
                        } else {
                            mBookAdapter = null;
                            _expandableBookingListData.clear();
                            noBookingsAvailableTV.setVisibility(View.VISIBLE);
                            progressWheel_CENTER_NEWS.setVisibility(View.GONE);
                            progressWheel_BOTTOM.setVisibility(View.GONE);
                        }
                        progressWheel_CENTER_NEWS.setVisibility(View.GONE);
                        JSONObject response = new JSONObject(Serverresponse);
                        Iterator<String> key = response.keys();
                        while (key.hasNext()) {
                            String k = key.next();
                            ExpandableBookingListData _tempexpandablebookingListData = new ExpandableBookingListData();
                            _tempexpandablebookingListData.set_BookingId(k);
                            JSONArray jsonMainNodes = response.getJSONArray(k);

                            bookdataList = new ArrayList<BookingListData>();
                            //JSONArray ja = jsonMainNode.getJSONArray(k);
                            for (int l = 0; l < jsonMainNodes.length(); l++) {
                                JSONObject jsonChildNode = jsonMainNodes.getJSONObject(l);
                                BookingListData tempbookSubList = new BookingListData();
                                tempbookSubList.set_BookingId(jsonChildNode.optString("bookingId"));
                                tempbookSubList.set_SportsId(jsonChildNode.optString("sportsId"));
                                tempbookSubList.set_SportsName(jsonChildNode.optString("sportsName"));
                                tempbookSubList.set_BookedDate(jsonChildNode.optString("bookedDate"));
                                tempbookSubList.set_SlotTime(jsonChildNode.optString("slotTime"));
                                tempbookSubList.set_CourtName(jsonChildNode.optString("courtName"));
                                tempbookSubList.set_Booking_source(jsonChildNode.optString("booking_type"));
                                tempbookSubList.set_Amount_paid(jsonChildNode.optString("amount"));
                                tempbookSubList.set_Is_amount_paid(jsonChildNode.optString("is_amount_paid"));
                                tempbookSubList.set_Payment_mode(jsonChildNode.optString("payment_mode"));
                                tempbookSubList.set_bookingCancelled(jsonChildNode.optString("bookingCancelled"));
                                tempbookSubList.set_CourtCancelled(jsonChildNode.optString("courtCancelled"));
                                bookdataList.add(tempbookSubList);
                                Log.i("JSON parse", OutputData);
                            } //for loop End
                            _tempexpandablebookingListData.setBookingListData(bookdataList);
                            _expandableBookingListData.add(_tempexpandablebookingListData);
                        }//While loop End
                        //}

                        booksListCount = booksListCount + bookdataList.size();

                        //*//************ Show Output on screen/activity **********//*
                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                try {
                                    // This method will be executed once the timer is over
                                    if (myBookingCountList != 0) {
                                        if (fetchDirection.equalsIgnoreCase(fetchDirectionUP)) {
                                            //int position = bookdataListTemp.size();
                                            bookdataListTemp.addAll(bookdataList);
                                            bookdataList = bookdataListTemp;

                                            // add elements to al, including duplicates
                                            Set<BookingListData> hs = new HashSet<>();
                                            hs.addAll(bookdataList);
                                            bookdataList.clear();
                                            bookdataList.addAll(hs);

                                            if (activity != null) {
                                                mBookAdapter = new BookingExpandableListAdapter(BookingListFragment.this, _expandableBookingListData);
                                                bookingListViewLV.setAdapter(mBookAdapter);
                                                noBookingsAvailableTV.setVisibility(View.GONE);
                                                progressWheel_CENTER_NEWS.setVisibility(View.GONE);
                                                progressWheel_BOTTOM.setVisibility(View.GONE);
                                                // swipeRefreshLayout.setRefreshing(false);
                                            } else {
                                                progressWheel_CENTER_NEWS.setVisibility(View.VISIBLE);
                                            }
                                        }

                                        if (mBookAdapter != null) {
                                            try {
                                                mBookAdapter.notifyDataSetChanged();
                                            } catch (ArrayIndexOutOfBoundsException e) {
                                                e.printStackTrace();
                                            }

                                        } else {
                                            if (activity != null) {
                                                //Set<BookingListData> hs = new HashSet<>();
                                                //hs.addAll(bookdataList);
                                                //bookdataList.clear();
                                                //_expandableBookingListData.addAll(hs);
                                                mBookAdapter = new BookingExpandableListAdapter(BookingListFragment.this, _expandableBookingListData);
                                                bookingListViewLV.setAdapter(mBookAdapter);
                                                noBookingsAvailableTV.setVisibility(View.GONE);
                                                progressWheel_CENTER_NEWS.setVisibility(View.GONE);
                                                progressWheel_BOTTOM.setVisibility(View.GONE);
                                                // swipeRefreshLayout.setRefreshing(false);
                                            } else {
                                                progressWheel_CENTER_NEWS.setVisibility(View.VISIBLE);
                                            }
                                        }
                                        progressWheel_CENTER_NEWS.setVisibility(View.GONE);
                                        // stopping swipe refresh
                                        //swipeRefreshLayout.setRefreshing(false);
                                        progressWheel_BOTTOM.setVisibility(View.GONE);
                                    } else {
                                        progressWheel_BOTTOM.setVisibility(View.GONE);
                                    }
                                } catch (OutOfMemoryError oom) {
                                    oom.printStackTrace();
                                } catch (Exception ee) {
                                    ee.printStackTrace();
                                }
                            }
                        }, 700);
                        // swipeRefreshLayout.setRefreshing(false);

                    } catch (JSONException e) {
                        // stopping swipe refresh
                        //  swipeRefreshLayout.setRefreshing(false);
                        _expandableBookingListData.clear();
                        bookingListViewLV.setVisibility(View.GONE);
                        progressWheel_CENTER_NEWS.setVisibility(View.GONE);
                        noBookingsAvailableTV.setVisibility(View.VISIBLE);
                        progressWheel_BOTTOM.setVisibility(View.GONE);
                        e.printStackTrace();
                    }

                } catch (Exception e) {
                    // stopping swipe refresh
                    //swipeRefreshLayout.setRefreshing(false);
                    progressWheel_CENTER_NEWS.setVisibility(View.GONE);
                    noBookingsAvailableTV.setVisibility(View.VISIBLE);
                    progressWheel_BOTTOM.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
                try {
                    // stopping swipe refresh
                    //swipeRefreshLayout.setRefreshing(false);
                    progressWheel_CENTER_NEWS.setVisibility(View.GONE);
                    noBookingsAvailableTV.setVisibility(View.GONE);
                    getNewsTroubleConnecting(_activity);
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
                if (statusCode == 404) {
                    Utility.showCustomToast(getResources().getString(R.string.request_not_found), BookingListFragment.this);
                } else if (statusCode == 500) {
                    Utility.showCustomToast(getResources().getString(R.string.some_went_wrong), BookingListFragment.this);
                } else {
                    Utility.showCustomToast(getResources().getString(R.string.unexpected_error), BookingListFragment.this);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mBookAdapter != null) {
            mBookAdapter.notifyDataSetChanged();
        }
    }

    private void sendRequestWithScrollDirection(String DirectiontoFetch, int totalItemCount) {
        if (bookdataList.size() == totalItemCount) {
            fetchDirection = DirectiontoFetch;
            bookdataListTemp = bookdataList;
            if (myBookingCountList != 0) {
                if ((booksListCount - 10) >= 0) {
                    progressWheel_BOTTOM.setVisibility(View.VISIBLE);
                    if (Utility.isOnline(BookingListFragment.this)) {
                        page_Num = page_Num + 1;
                        // ifNetisAvilableGetNews(BookingListFragment.this, page_Num);
                    } else {
                        progressWheel_BOTTOM.setVisibility(View.GONE);
                        Utility.showCustomToast(getResources().getString(R.string.pls_connect_internet), BookingListFragment.this);
                    }
                } else {
                    progressWheel_BOTTOM.setVisibility(View.GONE);
                }
            }
        }
    }


    protected void sportAvailbleRequest(String sportsUrl) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("facilityId", SharedPreference.getPreferences(BookingListFragment.this, "KEY_FACILITY_ID"));
        client.post(sportsUrl, params, new AsyncHttpResponseHandler() {
            public void onSuccess(String response) {
                try {
                    JSONArray jArray = new JSONArray(response);
                    countList = jArray.length();
                    sportTitle.clear();
                    _sportavailableListData.clear();
                    if (countList != 0) {
                        _sportavailableListData = new ArrayList<SportAvailableListData>();
                    }
                    _sportavailableListData.add(new SportAvailableListData("0", "All"));
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
                                if (progressWheel_CENTER_NEWS != null) {
                                    progressWheel_CENTER_NEWS.setVisibility(View.GONE);
                                }
                                if (activity != null) {
                                    //sportsSPID.setAdapter(new ArrayAdapter<String>(BookingListFragment.this, R.layout.dropdowntext, sportTitle));
                                    sportsSPID.setAdapter(new ArrayAdapter<String>(BookingListFragment.this, android.R.layout.simple_spinner_dropdown_item, sportTitle));
                                    //sports_id = _sportavailableListData.get(sportsSPID.getSelectedItemPosition()).getSportsId();
                                } else {
                                    sportsSPID.setVisibility(View.GONE);
                                }
                            } else {
                                progressWheel_CENTER_NEWS.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, SPLASH_TIME_OUT);
            }

            public void onFailure(int statusCode, Throwable error, String content) {
                try {
                    if (progressWheel_CENTER_NEWS != null) {
                        progressWheel_CENTER_NEWS.setVisibility(View.GONE);
                    }
                    if (content != null && content.contains("Invalid use of Null")) {
                        Utility.showCustomToast("Customer Not Found!", BookingListFragment.this);
                    } else if (content != null && content.contains("Type mismatch: 'GetCustomer'")) {
                        Toast.makeText(getApplicationContext(), "Invalid Customer Id!", Toast.LENGTH_SHORT).show();
                    } else {
                        // When HTTP response code is '404'
                        if (statusCode == 404) {
                            Utility.showCustomToast("Requested resource not found", BookingListFragment.this);
                        }
                        // When HTTP response code is '500'
                        else if (statusCode == 500) {
                            Utility.showCustomToast("Something went wrong at server end", BookingListFragment.this);
                        }
                        //When HTTP response code other than 404, 500
                        else {
                            Utility.showCustomToast("Unexpected Error occcured! Please Try Again", BookingListFragment.this);
                        }
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
                return new DatePickerDialog(BookingListFragment.this, mDateSetListener, mYear, mMonth, mDay);
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
            datestr = mYear + "-" + monthNo + "-" + daydate;
            progressWheel_CENTER_NEWS.setVisibility(View.VISIBLE);
            searchId = "";
            SharedPreference.setPreference(BookingListFragment.this, "KEY_BOOK_DATE", datestr);
            SharedPreference.setPreference(getApplicationContext(), "SEARCH", "SEARCH1");

            if (Utility.isOnline(BookingListFragment.this)) {
                ifNetisAvilableGetNews(BookingListFragment.this, 1);
            } else {
                //loadingSportsTroubleConnecting();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * This method is called when swipe refresh is pulled down
     */
   /* @Override
    public void onRefresh() {
        //swipeRefreshLayout.setRefreshing(false);

        *//*if (Utility.isOnline(BookingListFragment.this)) {
            page_Num = 1;
            try {
                searchETID.setText("");
                searchId = "";
            } catch (Exception e) {
                e.printStackTrace();
            }
            ifNetisAvilableGetNews(BookingListFragment.this, page_Num);
        } else {
            progressWheel_BOTTOM.setVisibility(View.GONE);
            Utility.showCustomToast(getResources().getString(R.string.pls_connect_internet), BookingListFragment.this);
        }*//*
    }*/
    public void onItemSelected(AdapterView parent, View v, int position, long id) {
        //   mText.setText("Position " + position);
    }

    public void onNothingSelected(AdapterView parent) {
        //mText.setText("Nothing");
    }

    @Override
    public void onScroll(AbsListView view, int firstCell, int cellCount, int itemCount) {
        int last = firstCell + cellCount - 1;
        // mText.setText("Showing " + firstCell + "-" + last + "/" + itemCount);
        if (itemCount > 0) //check for scroll down
        {
            visibleItemCount = itemCount;
            totalItemCount = cellCount;
            pastVisiblesItems = firstCell;
            if (loading) {
                fetchDirection = fetchDirectionUP;
                if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                    sendRequestWithScrollDirection(fetchDirectionUP, totalItemCount);
                    loading = true;
                    Log.v("...", "Last Item Wow !");
                    //Do pagination.. i.e. fetch new data
                    //Utility.showCustomToast("Loading more news....",getActivity());
                }
            }
        }
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    public class BookingExpandableListAdapter extends BaseExpandableListAdapter {
        private Context _context;
        private ArrayList<ExpandableBookingListData> _listDataHeader;
        ProgressDialog ringProgressDialog;
        String bookingCancelled;
        //public BookingListData childbookList;    /// Today taken
        public ArrayList<BookingListData> chList;
        ArrayList<Integer> bookingList;
        BookingListData childbookList;
        private LayoutInflater layoutInflater = null;


        class ViewHolder {
            LinearLayout sportsCardCV;
            ImageView statusColorIVID;
            TextView sportsNameTVID;
            TextView sportsTimeTVID;
            TextView sportsDayAndTimeTVID;
            TextView courtNoTVID;
            TextView amontPaidTVID;
            ImageView courtCancelIVID;
            TextView bookingidPerTVID;
            RelativeLayout bookinRowRLID;
            ImageView bookCancelIVID;

            ///Hide Show Ids in Confirm Payment
            Button confirmPaymentBtnID;
            LinearLayout confirmLLID;
            LinearLayout amounPaidLLID;
        }

        public BookingExpandableListAdapter(Context context, ArrayList<ExpandableBookingListData> _listDataHeader) {
            this._context = context;
            this._listDataHeader = _listDataHeader;
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public Object getChild(int groupPosition, int childPosititon) {
            try {
                chList = _listDataHeader.get(groupPosition).getBookingListData();
                return chList.get(childPosititon);
            } catch (ArrayIndexOutOfBoundsException ee) {
                ee.printStackTrace();
            } catch (Exception eee) {
                eee.printStackTrace();
            }
            return null;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }


        @Override
        public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ViewHolder mViewHolder = null;
            if (convertView == null) {
                mViewHolder = new ViewHolder();
                //LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                //convertView = infalInflater.inflate(R.layout.sub_list_row_item_booking_order_list, null);
                convertView = layoutInflater.inflate(R.layout.sub_list_row_item_booking_order_list, null, false);
                mViewHolder.sportsCardCV = (LinearLayout) convertView.findViewById(R.id.sportsCardSubListCVID);
                mViewHolder.statusColorIVID = (ImageView) convertView.findViewById(R.id.statusColorTVID);
                mViewHolder.sportsNameTVID = (TextView) convertView.findViewById(R.id.sportsNameTVID);
                mViewHolder.sportsDayAndTimeTVID = (TextView) convertView.findViewById(R.id.sportsDayAndTimeTVID);
                mViewHolder.sportsTimeTVID = (TextView) convertView.findViewById(R.id.sportsTimeTVID);
                mViewHolder.courtNoTVID = (TextView) convertView.findViewById(R.id.courtNoTVID);
                mViewHolder.amontPaidTVID = (TextView) convertView.findViewById(R.id.amontPaidTVID);
                mViewHolder.courtCancelIVID = (ImageView) convertView.findViewById(R.id.courtCancelIVID);
                mViewHolder.bookingidPerTVID = (TextView) convertView.findViewById(R.id.bookingidPerTVID);
                mViewHolder.bookinRowRLID = (RelativeLayout) convertView.findViewById(R.id.bookinRowRLID);
                mViewHolder.bookCancelIVID = (ImageView) convertView.findViewById(R.id.bookCancelIVID);

                /////Hide and Show Ids Yellow Color Based
                mViewHolder.confirmPaymentBtnID = (Button) convertView.findViewById(R.id.confirmPaymentBtnID);
                mViewHolder.confirmLLID = (LinearLayout) convertView.findViewById(R.id.confirmLLID);
                mViewHolder.amounPaidLLID = (LinearLayout) convertView.findViewById(R.id.confirmLLID);

                childbookList = (BookingListData) getChild(groupPosition, childPosition);
                bookingList = new ArrayList<Integer>();
                bookingList.add(childPosition);
                bookingList.add(groupPosition);
                //mViewHolder.sportsCardCV.setTag(bookingList);
                convertView.setTag(mViewHolder);

            } else {
                mViewHolder = (ViewHolder) convertView.getTag();
            }

          //  mViewHolder.sportsCardCV.setTag(bookingList);


            mViewHolder.sportsNameTVID.setText(childbookList.get_SportsName().toString());
            mViewHolder.sportsDayAndTimeTVID.setText(childbookList.get_BookedDate().toString());
            mViewHolder.sportsTimeTVID.setText(childbookList.get_SlotTime().toString());
            mViewHolder.courtNoTVID.setText(childbookList.get_CourtName().toString());
            mViewHolder.amontPaidTVID.setText(childbookList.get_Amount_paid().toString());

            String booking_source = childbookList.get_Booking_source().toString();
            mViewHolder.confirmPaymentBtnID.setTag(groupPosition);
            String is_amount_paid = childbookList.get_Is_amount_paid().toString();
            String courtCancelled = childbookList.get_courtCancelled().toString();

            try {
                if (childbookList.get_bookingCancelled().toString() != null) {
                    bookingCancelled = childbookList.get_bookingCancelled().toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (booking_source.equals("other")) {
                mViewHolder.amounPaidLLID.setVisibility(View.GONE);
                mViewHolder.statusColorIVID.setBackgroundColor(Color.parseColor("#00000000"));
                mViewHolder.statusColorIVID.setBackgroundColor(Color.parseColor("#2B66B1"));   //darkblue
            } else {
                if (!booking_source.equals("prepaid")) { //here Come postpaid
                    if (!is_amount_paid.equals("0")) {
                        mViewHolder.statusColorIVID.setBackgroundColor(Color.parseColor("#00000000"));
                        mViewHolder.statusColorIVID.setBackgroundColor(Color.parseColor("#FDCA00"));  //Yellow
                        mViewHolder.amontPaidTVID.setVisibility(View.VISIBLE);
                        mViewHolder.confirmPaymentBtnID.setVisibility(View.VISIBLE);
                        mViewHolder.amounPaidLLID.setVisibility(View.GONE);
                    } else {
                    }
                } else {
                    mViewHolder.amounPaidLLID.setVisibility(View.GONE);
                    mViewHolder.statusColorIVID.setBackgroundColor(Color.parseColor("#00000000"));
                    mViewHolder.statusColorIVID.setBackgroundColor(Color.parseColor("#00aecd"));   // Ligth Blue
                }
            }

            if (bookingCancelled != null) {
                if (bookingCancelled.equals("1")) {
                    mViewHolder.bookCancelIVID.setVisibility(View.VISIBLE);
                } else {
                    mViewHolder.bookCancelIVID.setVisibility(View.GONE);
                }
            }
            if (courtCancelled != null) {
                if (courtCancelled.equals("1")) {
                    mViewHolder.courtCancelIVID.setVisibility(View.VISIBLE);
                } else {
                    mViewHolder.courtCancelIVID.setVisibility(View.GONE);
                }
            } else {

            }

            /////////Confirm Button Showing Here When Booking Source Postpaid
            /*mViewHolder.sportsCardCV.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //int pos = (Integer)v.getTag();
                    ArrayList<Integer> bookingListIDS = (ArrayList<Integer>) v.getTag();
                    chList = _listDataHeader.get(bookingListIDS.get(1)).getBookingListData();
                    for (int i = 0; i < chList.size(); i++) {
                        if (chList.size() - 1 == bookingListIDS.get(0)) {
                            if (childbookList.get_Booking_source().toString().equals("postpaid")) { //postpaid
                                // mViewHolder.confirmPaymentBtnID.setVisibility(View.VISIBLE);
                                Toast.makeText(getApplicationContext(), "Hi", Toast.LENGTH_LONG).show();
                            } else {
                                //mViewHolder.confirmPaymentBtnID.setVisibility(View.GONE);
                            }
                        } else {
                            //holder.confirmPaymentBtnID.setVisibility(View.GONE);
                        }
                    }
                }
            });*/
            //////////////

            mViewHolder.confirmPaymentBtnID.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (Integer) v.getTag();
                    if (pos == groupPosition) {
                        int tempchildPosition = chList.size() - 1;
                        boolean isNetAvailable = Utility.isOnline(_context);
                        if (isNetAvailable) {
                            try {
                                ringProgressDialog = ProgressDialog.show(_context, null, "Please Wait...", true);
                                ringProgressDialog.setCancelable(true);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            RequestParams params = new RequestParams();
                            params.put("paid_by", SharedPreference.getPreferences(_context, "USER_ID"));
                            params.put("booking_id", childbookList.get_BookingId());
                            params.put("payment_mode", childbookList.get_Payment_mode());
                            confirmPaymentRequesttoServer(AppConstants.CONFIRM_PAYMENT_URL, params, pos, tempchildPosition);
                        } else {
                            //Utility.showCustomToast(_context.getResources().getString(R.string.pls_connect_internet), _context);
                        }

                    } else {
                    }
                }
            });
            return convertView;

        }

        @Override
        public int getChildrenCount(int groupPosition) {
            ArrayList<BookingListData> chList = _listDataHeader.get(groupPosition).getBookingListData();
            return chList.size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return _listDataHeader.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return _listDataHeader.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            //String headerTitle = (String) getGroup(groupPosition);
            ExpandableBookingListData group = (ExpandableBookingListData) getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_items_row__booking_order_list, null);
            }


            ExpandableListView mExpandableListView = (ExpandableListView) parent;
            mExpandableListView.expandGroup(groupPosition);
            mExpandableListView.setDivider(_context.getResources().getDrawable(R.color.black));

            TextView bookingidTVID = (TextView) convertView.findViewById(R.id.bookingidPerTVID);
            ImageView bookCancelIVID = (ImageView) convertView.findViewById(R.id.bookCancelIVID);
            //bookingidTVID.setTypeface(null, Typeface.BOLD);
            bookingidTVID.setText("#" + group.get_BookingId());
            //bookingidTVID.setText(childbookList.get_SportsName().toString());

            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }


        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }


        public void confirmPaymentRequesttoServer(String _Url, RequestParams params, final int finalgroupPosition, final int finalchildPosition) {
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
                                if (chList.size() - 1 == finalchildPosition) {
                                    Toast.makeText(_context, "" + message, Toast.LENGTH_LONG).show();
                                    ifNetisAvilableGetNews(BookingListFragment.this, 1);
                                    //BookingListFragment.ifNetisAvilableGetNews();
                                    //confirmLLID.setVisibility(View.GONE);
                                    // confirmPaymentBtnID.setVisibility(View.GONE);
                                    //amounPaidLLID.setVisibility(View.VISIBLE);
                                } else {
                                    //confirmLLID.setVisibility(View.VISIBLE);
                                    //confirmPaymentBtnID.setVisibility(View.VISIBLE);
                                    //amounPaidLLID.setVisibility(View.GONE);
                                }
                            } else {
                                if (chList.size() - 1 == finalchildPosition) {
                                    Toast.makeText(_context, "" + message, Toast.LENGTH_LONG).show();
                                    //confirmLLID.setVisibility(View.GONE);
                                    ///confirmPaymentBtnID.setVisibility(View.GONE);
                                    //amounPaidLLID.setVisibility(View.VISIBLE);
                                } else {
                                    //confirmLLID.setVisibility(View.VISIBLE);
                                    //confirmPaymentBtnID.setVisibility(View.VISIBLE);
                                    //amounPaidLLID.setVisibility(View.GONE);
                                }
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
                        // Utility.showCustomToast(_context.getResources().getString(R.string.request_not_found), _context);
                    } else if (statusCode == 500) {
                        // Utility.showCustomToast(_context.getResources().getString(R.string.some_went_wrong), mActivity);
                    } else {
                        // Utility.showCustomToast(_context.getResources().getString(R.string.unexpected_error), mActivity);
                    }
                }
            });
        }

    }


}



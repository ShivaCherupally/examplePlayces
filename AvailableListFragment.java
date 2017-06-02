package in.playcerteam.playces;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
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

import in.playcerteam.playces.adapters.GridItemView;
import in.playcerteam.playces.adapters.GridViewAdapter;
import in.playcerteam.playces.adapters.SingleAvailbleSlotBooking;
import in.playcerteam.playces.libs.ExpandableHeightGridView;
import in.playcerteam.playces.libs.ProgressWheel;
import in.playcerteam.playces.model.CourtDetailsData;
import in.playcerteam.playces.model.SingleAvailbleListData;
import in.playcerteam.playces.model.SportAvailableListData;
import in.playcerteam.playces.utilities.AppConstants;
import in.playcerteam.playces.utilities.AppDataBaseHelper;
import in.playcerteam.playces.utilities.SharedPreference;
import in.playcerteam.playces.utilities.Utility;

/**
 * Created by PlaycerTeam on 5/27/2016.
 */
public class AvailableListFragment extends Activity {
    public SingleAvailbleSlotBooking mAdapter;
    Activity activity;
    TextView noAvailblesTVID;
    // TextView prevousDateTVID;
    // TextView nextDayTVID;
    ArrayList<Integer> resultList;

    LinearLayout noSportsNetLL;
    private int countList = 0;
    private ProgressWheel progressWheel_CENTER_SPORTS;
    AppDataBaseHelper dataBaseHelper;
    private static final String ARG_KEY_SORT = "arg_key_sort";
    private String sort;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;

    TextView availableDateTVID;
    TextView prevousDateTVID;
    TextView nextDayTVID;
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

    TextView sports_locationTVID;
    private static int SPLASH_TIME_OUT = 1000;
    Spinner sportsSPID;
    String sports_id;
    String sport_name;

    String slotId;     //Individual Slot id

    LinearLayout rowOneCourtsLLID;
    LinearLayout rowtwoCourtsLLID;
    LinearLayout rowthreeCourtsLLID;
    LinearLayout rowfourCourtsLLID;
    LinearLayout rowfiveCourtsLLID;
    LinearLayout rowsixCourtsLLID;
    LinearLayout rowsevenCourtsLLID;
    LinearLayout roweigthCourtsLLID;

    ArrayList<SingleAvailbleListData> _singleAvailbleListData = new ArrayList<SingleAvailbleListData>();
    ArrayList<CourtDetailsData> _courtDetailsData = new ArrayList<CourtDetailsData>();

    // Slot 1
    LinearLayout FirstRowFirstColumnLLID;
    TextView slottime1TVID;
    TextView availableCourts1TVID;
    TextView totalCourts1TVID;
    TextView avaialable1TVID;

    //Slot 2
    LinearLayout FirstRowSecondColumnLLID;
    TextView slottime2TVID;
    TextView availableCourts2TVID;
    TextView totalCourts2TVID;
    TextView avaialable2TVID;

    //Slot 3
    LinearLayout FirstRowThirdColumnLLID;
    TextView slottime3TVID;
    TextView availableCourts3TVID;
    TextView totalCourts3TVID;
    TextView avaialable3TVID;

    //Slot 4
    LinearLayout SecondRowFirstColumnLLID;
    TextView slottime4TVID;
    TextView availableCourts4TVID;
    TextView totalCourts4TVID;
    TextView avaialable4TVID;

    //Slot 5
    LinearLayout SecondRowSecondColumnLLID;
    TextView slottime5TVID;
    TextView availableCourts5TVID;
    TextView totalCourts5TVID;
    TextView avaialable5TVID;

    //Slot 6
    LinearLayout SecondRowThirdColumnLLID;
    TextView slottime6TVID;
    TextView availableCourts6TVID;
    TextView totalCourts6TVID;
    TextView avaialable6TVID;

    //Slot 7
    LinearLayout ThirdRowFirstColumnLLID;
    TextView slottime7TVID;
    TextView availableCourts7TVID;
    TextView totalCourts7TVID;
    TextView avaialable7TVID;

    //Slot 8
    LinearLayout ThirdRowSecondColumnLLID;
    TextView slottime8TVID;
    TextView availableCourts8TVID;
    TextView totalCourts8TVID;
    TextView avaialable8TVID;

    //Slot 9
    LinearLayout ThirdRowThirdColumnLLID;
    TextView slottime9TVID;
    TextView availableCourts9TVID;
    TextView totalCourts9TVID;
    TextView avaialable9TVID;

    //Slot 10
    LinearLayout FourthRowFirstColumnLLID;
    TextView slottime10TVID;
    TextView availableCourts10TVID;
    TextView totalCourts10TVID;
    TextView avaialable10TVID;

    //Slot 11
    LinearLayout FourthRowSecondColumnLLID;
    TextView slottime11TVID;
    TextView availableCourts11TVID;
    TextView totalCourts11TVID;
    TextView avaialable11TVID;


    //Slot 12
    LinearLayout FourthRowThirdColumnLLID;
    TextView slottime12TVID;
    TextView availableCourts12TVID;
    TextView totalCourts12TVID;
    TextView avaialable12TVID;


    //Slot 13
    LinearLayout FifthRowFirstColumnLLID;
    TextView slottime13TVID;
    TextView availableCourts13TVID;
    TextView totalCourts13TVID;
    TextView avaialable13TVID;


    //Slot 14
    LinearLayout FifthRowSecondColumnLLID;
    TextView slottime14TVID;
    TextView availableCourts14TVID;
    TextView totalCourts14TVID;
    TextView avaialable14TVID;

    //Slot 15
    LinearLayout FifthRowThirdColumnLLID;
    TextView slottime15TVID;
    TextView availableCourts15TVID;
    TextView totalCourts15TVID;
    TextView avaialable15TVID;

    //Slot 16
    LinearLayout SixthRowFirstColumnLLID;
    TextView slottime16TVID;
    TextView availableCourts16TVID;
    TextView totalCourts16TVID;
    TextView avaialable16TVID;

    //Slot 17
    LinearLayout SixthRowSecondColumnLLID;
    TextView slottime17TVID;
    TextView availableCourts17TVID;
    TextView totalCourts17TVID;
    TextView avaialable17TVID;

    //Slot 18
    LinearLayout SixthRowThirdColumnLLID;
    TextView slottime18TVID;
    TextView availableCourts18TVID;
    TextView totalCourts18TVID;
    TextView avaialable18TVID;

    //Slot 19
    LinearLayout SeventhRowFirstColumnLLID;
    TextView slottime19TVID;
    TextView availableCourts19TVID;
    TextView totalCourts19TVID;
    TextView avaialable19TVID;

    //Slot 20
    LinearLayout SeventhRowSecondColumnLLID;
    TextView slottime20TVID;
    TextView availableCourts20TVID;
    TextView totalCourts20TVID;
    TextView avaialable20TVID;


    //Slot 21
    LinearLayout SeventhRowThirdColumnLLID;
    TextView slottime21TVID;
    TextView availableCourts21TVID;
    TextView totalCourts21TVID;
    TextView avaialable21TVID;
    //Slot 22
    LinearLayout EigthRowFirstColumnLLID;
    TextView slottime22TVID;
    TextView availableCourts22TVID;
    TextView totalCourts22TVID;
    TextView avaialable22TVID;
    //Slot 23
    LinearLayout EigthRowSecondColumnLLID;
    TextView slottime23TVID;
    TextView availableCourts23TVID;
    TextView totalCourts23TVID;
    TextView avaialable23TVID;
    //Slot 24
    LinearLayout EigthRowThirdColumnLLID;
    TextView slottime24TVID;
    TextView availableCourts24TVID;
    TextView totalCourts24TVID;
    TextView avaialable24TVID;

    private ExpandableHeightGridView gridView;
    private ExpandableHeightGridView gridrow2;
    private ExpandableHeightGridView gridrow3;
    private ExpandableHeightGridView gridrow4;
    private ExpandableHeightGridView gridrow5;
    private ExpandableHeightGridView gridrow6;
    private ExpandableHeightGridView gridrow7;
    private ExpandableHeightGridView gridrow8;

    private ArrayList<String> selectedStrings;
    // private ArrayList<Integer> idsList = new ArrayList<Integer>();
    GridViewAdapter adapter1;
    ArrayList<CourtDetailsData> courtDetailsDataLocal1 = new ArrayList<CourtDetailsData>();
    String playceCodeAndLocation = "";
    ScrollView completeslotsSVID;
    View FirstRowFirstColumn_SELECT_VID, FirstRowSecondColumn_SELECT_VID, FirstRowThirdColumn_SELECT_VID;
    View SecondRowFirstColumn_SELECT_VID, SecondRowSecondColumn_SELECT_VID, SecondRowThirdColumn_SELECT_VID;
    View ThirdRowFirstColumn_SELECT_VID, ThirdRowSecondColumn_SELECT_VID, ThirdRowThirdColumn_SELECT_VID;
    View FourthRowFirstColumn_SELECT_VID, FourthRowSecondColumn_SELECT_VID, FourthRowThirdColumn_SELECT_VID;
    View FifthRowFirstColumn_SELECT_VID, FifthRowSecondColumn_SELECT_VID, FifthRowThirdColumn_SELECT_VID;
    View SixthRowFirstColumn_SELECT_VID, SixthRowSecondColumn_SELECT_VID, SixthRowThirdColumn_SELECT_VID;
    View SeventhRowFirstColumn_SELECT_VID, SeventhRowSecondColumn_SELECT_VID, SeventhRowThirdColumn_SELECT_VID;
    View EigthRowFirstColumn_SELECT_VID, EigthRowSecondColumn_SELECT_VID, EigthRowThirdColumn_SELECT_VID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_available_list_screen);

        activity = AvailableListFragment.this;
        dataBaseHelper = new AppDataBaseHelper(getApplicationContext());

        xmlIdCalling();

        selectedViewsIds();

        SlotSelectDividerShowAllWiseGoneView();  //First Time All are gone

        //date display
        c = Calendar.getInstance();
        sdf = new SimpleDateFormat("dd-M-yyyy");
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        monthAndDayNamesFetch(mYear, mMonth, mDay, "");

        completeslotsSVID = (ScrollView) findViewById(R.id.completeslotsSVID);


        availableDateTVID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createdDialog(DATE_DIALOG_ID).show();
            }
        });

        prevousDateTVID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*String formattedDate;
                c.add(Calendar.DATE, -1);
                formattedDate = sdf.format(c.getTime());
                Log.v("PREVIOUS DATE : ", formattedDate);
                availableDateTVID.setText(formattedDate);*/
                previousDateStr = "SELECT_PREVIOUS_DAY";
                monthAndDayNamesFetch(mYear, mMonth, mDay, previousDateStr);
                //prevousDateFetching();
            }
        });

        nextDayTVID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*String formattedDate;
                c.add(Calendar.DATE, 1);
                formattedDate = sdf.format(c.getTime());
                Log.v("Next DATE : ", formattedDate);
                availableDateTVID.setText(formattedDate);*/
                previousDateStr = "SELECT_NEXT_DAY";
                monthAndDayNamesFetch(mYear, mMonth, mDay, previousDateStr);
            }
        });

        sportsSPID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int arg2, long arg3) {

                refreshLinearlayoutBackgrounds();
                SlotSelectDividerShowAllWiseGoneView();
                progressWheel_CENTER_SPORTS.setVisibility(View.VISIBLE);
                String selected_val = sportsSPID.getSelectedItem().toString();
                sports_id = _sportavailableListData.get(sportsSPID.getSelectedItemPosition()).getSportsId();
                //sports_id = "2";  //hard Coded
                SharedPreference.setPreference(getApplicationContext(), "SPORT_ID", sports_id);
                sport_name = _sportavailableListData.get(sportsSPID.getSelectedItemPosition()).getSportsTitle();
                SharedPreference.setPreference(getApplicationContext(), "SPORT_NAME", sport_name);
                //Toast.makeText(getApplicationContext(), selected_val, Toast.LENGTH_SHORT).show();
                if (Utility.isOnline(getApplicationContext())) {
                    ifNetisAvilableAvailbleList();
                } else {
                    loadingSportsTroubleConnecting();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        if (Utility.isOnline(getApplicationContext())) {
            progressWheel_CENTER_SPORTS.setVisibility(View.VISIBLE);
            ifNetisAvilableSports();
        } else {
            loadingSportsTroubleConnecting();
        }

        setGridForCourts();

    }

    private void selectedViewsIds() {
        FirstRowFirstColumn_SELECT_VID = (View) findViewById(R.id.FirstRowFirstColumn_SELECT_VID);
        FirstRowSecondColumn_SELECT_VID = (View) findViewById(R.id.FirstRowSecondColumn_SELECT_VID);
        FirstRowThirdColumn_SELECT_VID = (View) findViewById(R.id.FirstRowThirdColumn_SELECT_VID);

        SecondRowFirstColumn_SELECT_VID = (View) findViewById(R.id.SecondRowFirstColumn_SELECT_VID);
        SecondRowSecondColumn_SELECT_VID = (View) findViewById(R.id.SecondRowSecondColumn_SELECT_VID);
        SecondRowThirdColumn_SELECT_VID = (View) findViewById(R.id.SecondRowThirdColumn_SELECT_VID);

        ThirdRowFirstColumn_SELECT_VID = (View) findViewById(R.id.ThirdRowFirstColumn_SELECT_VID);
        ThirdRowSecondColumn_SELECT_VID = (View) findViewById(R.id.ThirdRowSecondColumn_SELECT_VID);
        ThirdRowThirdColumn_SELECT_VID = (View) findViewById(R.id.ThirdRowThirdColumn_SELECT_VID);

        FourthRowFirstColumn_SELECT_VID = (View) findViewById(R.id.FourthRowFirstColumn_SELECT_VID);
        FourthRowSecondColumn_SELECT_VID = (View) findViewById(R.id.FourthRowSecondColumn_SELECT_VID);
        FourthRowThirdColumn_SELECT_VID = (View) findViewById(R.id.FourthRowThirdColumn_SELECT_VID);

        FifthRowFirstColumn_SELECT_VID = (View) findViewById(R.id.FifthRowFirstColumn_SELECT_VID);
        FifthRowSecondColumn_SELECT_VID = (View) findViewById(R.id.FifthRowSecondColumn_SELECT_VID);
        FifthRowThirdColumn_SELECT_VID = (View) findViewById(R.id.FifthRowThirdColumn_SELECT_VID);

        SixthRowFirstColumn_SELECT_VID = (View) findViewById(R.id.SixthRowFirstColumn_SELECT_VID);
        SixthRowSecondColumn_SELECT_VID = (View) findViewById(R.id.SixthRowSecondColumn_SELECT_VID);
        SixthRowThirdColumn_SELECT_VID = (View) findViewById(R.id.SixthRowThirdColumn_SELECT_VID);

        SeventhRowFirstColumn_SELECT_VID = (View) findViewById(R.id.SeventhRowFirstColumn_SELECT_VID);
        SeventhRowSecondColumn_SELECT_VID = (View) findViewById(R.id.SeventhRowSecondColumn_SELECT_VID);
        SeventhRowThirdColumn_SELECT_VID = (View) findViewById(R.id.SeventhRowThirdColumn_SELECT_VID);

        EigthRowFirstColumn_SELECT_VID = (View) findViewById(R.id.EigthRowFirstColumn_SELECT_VID);
        EigthRowSecondColumn_SELECT_VID = (View) findViewById(R.id.EigthRowSecondColumn_SELECT_VID);
        EigthRowThirdColumn_SELECT_VID = (View) findViewById(R.id.EigthRowThirdColumn_SELECT_VID);
    }

    private void xmlIdCalling() {
        //  setupNavigation();
        progressWheel_CENTER_SPORTS = (ProgressWheel) findViewById(R.id.progress_wheel1);
        progressWheel_CENTER_SPORTS.setBarColor(getResources().getColor(R.color.colorPrimary));
        progressWheel_CENTER_SPORTS.setRimColor(Color.LTGRAY);

        noAvailblesTVID = (TextView) findViewById(R.id.noAvailblesTVIDID);
        sports_locationTVID = (TextView) findViewById(R.id.sports_locationTVID);

        try {
            playceCodeAndLocation = "" + SharedPreference.getPreferences(AvailableListFragment.this, "PLAYCES_CODE") + " - " + SharedPreference.getPreferences(AvailableListFragment.this, "KEY_FACILITY_NAME") + ", " + SharedPreference.getPreferences(AvailableListFragment.this, "LOCATION");
            sports_locationTVID.setText("" + playceCodeAndLocation);
            SharedPreference.setPreference(AvailableListFragment.this, "KEY_KEY_FACILITY_ID", SharedPreference.getPreferences(AvailableListFragment.this, "KEY_FACILITY_ID"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        sportsSPID = (Spinner) findViewById(R.id.sportsSPID);

        prevousDateTVID = (TextView) findViewById(R.id.prevousDateTVID);   // Previousdate
        availableDateTVID = (TextView) findViewById(R.id.availableDateTVID);  // Calender Display Text
        nextDayTVID = (TextView) findViewById(R.id.nextDayTVID);   // Nextdate

        rowOneCourtsLLID = (LinearLayout) findViewById(R.id.rowOneCourtsLLID);
        rowtwoCourtsLLID = (LinearLayout) findViewById(R.id.rowtwoCourtsLLID);
        rowthreeCourtsLLID = (LinearLayout) findViewById(R.id.rowthreeCourtsLLID);
        rowfourCourtsLLID = (LinearLayout) findViewById(R.id.rowfourCourtsLLID);
        rowfiveCourtsLLID = (LinearLayout) findViewById(R.id.rowfiveCourtsLLID);
        rowsixCourtsLLID = (LinearLayout) findViewById(R.id.rowsixCourtsLLID);
        rowsevenCourtsLLID = (LinearLayout) findViewById(R.id.rowsevenCourtsLLID);
        roweigthCourtsLLID = (LinearLayout) findViewById(R.id.roweigthCourtsLLID);


        FirstRowFirstColumnLLID = (LinearLayout) findViewById(R.id.FirstRowFirstColumnLLID);
        FirstRowSecondColumnLLID = (LinearLayout) findViewById(R.id.FirstRowSecondColumnLLID);
        FirstRowThirdColumnLLID = (LinearLayout) findViewById(R.id.FirstRowThirdColumnLLID);

        SecondRowFirstColumnLLID = (LinearLayout) findViewById(R.id.SecondRowFirstColumnLLID);
        SecondRowSecondColumnLLID = (LinearLayout) findViewById(R.id.SecondRowSecondColumnLLID);
        SecondRowThirdColumnLLID = (LinearLayout) findViewById(R.id.SecondRowThirdColumnLLID);

        ThirdRowFirstColumnLLID = (LinearLayout) findViewById(R.id.ThirdRowFirstColumnLLID);
        ThirdRowSecondColumnLLID = (LinearLayout) findViewById(R.id.ThirdRowSecondColumnLLID);
        ThirdRowThirdColumnLLID = (LinearLayout) findViewById(R.id.ThirdRowThirdColumnLLID);


        FourthRowFirstColumnLLID = (LinearLayout) findViewById(R.id.FourthRowFirstColumnLLID);
        FourthRowSecondColumnLLID = (LinearLayout) findViewById(R.id.FourthRowSecondColumnLLID);
        FourthRowThirdColumnLLID = (LinearLayout) findViewById(R.id.FourthRowThirdColumnLLID);

        FifthRowFirstColumnLLID = (LinearLayout) findViewById(R.id.FifthRowFirstColumnLLID);
        FifthRowSecondColumnLLID = (LinearLayout) findViewById(R.id.FifthRowSecondColumnLLID);
        FifthRowThirdColumnLLID = (LinearLayout) findViewById(R.id.FifthRowThirdColumnLLID);

        SixthRowFirstColumnLLID = (LinearLayout) findViewById(R.id.SixthRowFirstColumnLLID);
        SixthRowSecondColumnLLID = (LinearLayout) findViewById(R.id.SixthRowSecondColumnLLID);
        SixthRowThirdColumnLLID = (LinearLayout) findViewById(R.id.SixthRowThirdColumnLLID);

        SeventhRowFirstColumnLLID = (LinearLayout) findViewById(R.id.SeventhRowFirstColumnLLID);
        SeventhRowSecondColumnLLID = (LinearLayout) findViewById(R.id.SeventhRowSecondColumnLLID);
        SeventhRowThirdColumnLLID = (LinearLayout) findViewById(R.id.SeventhRowThirdColumnLLID);

        EigthRowFirstColumnLLID = (LinearLayout) findViewById(R.id.EigthRowFirstColumnLLID);
        EigthRowSecondColumnLLID = (LinearLayout) findViewById(R.id.EigthRowSecondColumnLLID);
        EigthRowThirdColumnLLID = (LinearLayout) findViewById(R.id.EigthRowThirdColumnLLID);


        slottime1TVID = (TextView) findViewById(R.id.slottime1TVID);
        slottime2TVID = (TextView) findViewById(R.id.slottime2TVID);
        slottime3TVID = (TextView) findViewById(R.id.slottime3TVID);
        slottime4TVID = (TextView) findViewById(R.id.slottime4TVID);
        slottime5TVID = (TextView) findViewById(R.id.slottime5TVID);
        slottime6TVID = (TextView) findViewById(R.id.slottime6TVID);
        slottime7TVID = (TextView) findViewById(R.id.slottime7TVID);
        slottime8TVID = (TextView) findViewById(R.id.slottime8TVID);
        slottime9TVID = (TextView) findViewById(R.id.slottime9TVID);
        slottime10TVID = (TextView) findViewById(R.id.slottime10TVID);
        slottime11TVID = (TextView) findViewById(R.id.slottime11TVID);
        slottime12TVID = (TextView) findViewById(R.id.slottime12TVID);
        slottime13TVID = (TextView) findViewById(R.id.slottime13TVID);
        slottime14TVID = (TextView) findViewById(R.id.slottime14TVID);
        slottime15TVID = (TextView) findViewById(R.id.slottime15TVID);
        slottime16TVID = (TextView) findViewById(R.id.slottime16TVID);
        slottime17TVID = (TextView) findViewById(R.id.slottime17TVID);
        slottime18TVID = (TextView) findViewById(R.id.slottime18TVID);
        slottime19TVID = (TextView) findViewById(R.id.slottime19TVID);
        slottime20TVID = (TextView) findViewById(R.id.slottime20TVID);
        slottime21TVID = (TextView) findViewById(R.id.slottime21TVID);
        slottime22TVID = (TextView) findViewById(R.id.slottime22TVID);
        slottime23TVID = (TextView) findViewById(R.id.slottime23TVID);
        slottime24TVID = (TextView) findViewById(R.id.slottime24TVID);

        //Availble Courts
        availableCourts1TVID = (TextView) findViewById(R.id.availableCourts1TVID);
        availableCourts2TVID = (TextView) findViewById(R.id.availableCourts2TVID);
        availableCourts3TVID = (TextView) findViewById(R.id.availableCourts3TVID);
        availableCourts4TVID = (TextView) findViewById(R.id.availableCourts4TVID);
        availableCourts5TVID = (TextView) findViewById(R.id.availableCourts5TVID);
        availableCourts6TVID = (TextView) findViewById(R.id.availableCourts6TVID);
        availableCourts7TVID = (TextView) findViewById(R.id.availableCourts7TVID);
        availableCourts8TVID = (TextView) findViewById(R.id.availableCourts8TVID);
        availableCourts9TVID = (TextView) findViewById(R.id.availableCourts9TVID);
        availableCourts10TVID = (TextView) findViewById(R.id.availableCourts10TVID);
        availableCourts11TVID = (TextView) findViewById(R.id.availableCourts11TVID);
        availableCourts12TVID = (TextView) findViewById(R.id.availableCourts12TVID);
        availableCourts13TVID = (TextView) findViewById(R.id.availableCourts13TVID);
        availableCourts14TVID = (TextView) findViewById(R.id.availableCourts14TVID);
        availableCourts15TVID = (TextView) findViewById(R.id.availableCourts15TVID);
        availableCourts16TVID = (TextView) findViewById(R.id.availableCourts16TVID);
        availableCourts17TVID = (TextView) findViewById(R.id.availableCourts17TVID);
        availableCourts18TVID = (TextView) findViewById(R.id.availableCourts18TVID);
        availableCourts19TVID = (TextView) findViewById(R.id.availableCourts19TVID);
        availableCourts20TVID = (TextView) findViewById(R.id.availableCourts20TVID);
        availableCourts21TVID = (TextView) findViewById(R.id.availableCourts21TVID);
        availableCourts22TVID = (TextView) findViewById(R.id.availableCourts22TVID);
        availableCourts23TVID = (TextView) findViewById(R.id.availableCourts23TVID);
        availableCourts24TVID = (TextView) findViewById(R.id.availableCourts24TVID);


        //Total Courts
        totalCourts1TVID = (TextView) findViewById(R.id.totalCourts1TVID);
        totalCourts2TVID = (TextView) findViewById(R.id.totalCourts2TVID);
        totalCourts3TVID = (TextView) findViewById(R.id.totalCourts3TVID);
        totalCourts4TVID = (TextView) findViewById(R.id.totalCourts4TVID);
        totalCourts5TVID = (TextView) findViewById(R.id.totalCourts5TVID);
        totalCourts6TVID = (TextView) findViewById(R.id.totalCourts6TVID);
        totalCourts7TVID = (TextView) findViewById(R.id.totalCourts7TVID);
        totalCourts8TVID = (TextView) findViewById(R.id.totalCourts8TVID);
        totalCourts9TVID = (TextView) findViewById(R.id.totalCourts9TVID);
        totalCourts10TVID = (TextView) findViewById(R.id.totalCourts10TVID);
        totalCourts11TVID = (TextView) findViewById(R.id.totalCourts11TVID);
        totalCourts12TVID = (TextView) findViewById(R.id.totalCourts12TVID);
        totalCourts13TVID = (TextView) findViewById(R.id.totalCourts13TVID);
        totalCourts14TVID = (TextView) findViewById(R.id.totalCourts14TVID);
        totalCourts15TVID = (TextView) findViewById(R.id.totalCourts15TVID);
        totalCourts16TVID = (TextView) findViewById(R.id.totalCourts16TVID);
        totalCourts17TVID = (TextView) findViewById(R.id.totalCourts17TVID);
        totalCourts18TVID = (TextView) findViewById(R.id.totalCourts18TVID);
        totalCourts19TVID = (TextView) findViewById(R.id.totalCourts19TVID);
        totalCourts20TVID = (TextView) findViewById(R.id.totalCourts20TVID);
        totalCourts21TVID = (TextView) findViewById(R.id.totalCourts21TVID);
        totalCourts22TVID = (TextView) findViewById(R.id.totalCourts22TVID);
        totalCourts23TVID = (TextView) findViewById(R.id.totalCourts23TVID);
        totalCourts24TVID = (TextView) findViewById(R.id.totalCourts24TVID);


        //Availlable text
        avaialable1TVID = (TextView) findViewById(R.id.avaialable1TVID);
        avaialable2TVID = (TextView) findViewById(R.id.avaialable2TVID);
        avaialable3TVID = (TextView) findViewById(R.id.avaialable3TVID);
        avaialable4TVID = (TextView) findViewById(R.id.avaialable4TVID);
        avaialable5TVID = (TextView) findViewById(R.id.avaialable5TVID);
        avaialable6TVID = (TextView) findViewById(R.id.avaialable6TVID);
        avaialable7TVID = (TextView) findViewById(R.id.avaialable7TVID);
        avaialable8TVID = (TextView) findViewById(R.id.avaialable8TVID);
        avaialable9TVID = (TextView) findViewById(R.id.avaialable9TVID);
        avaialable10TVID = (TextView) findViewById(R.id.avaialable10TVID);
        avaialable11TVID = (TextView) findViewById(R.id.avaialable11TVID);
        avaialable12TVID = (TextView) findViewById(R.id.avaialable12TVID);
        avaialable13TVID = (TextView) findViewById(R.id.avaialable13TVID);
        avaialable14TVID = (TextView) findViewById(R.id.avaialable14TVID);
        avaialable15TVID = (TextView) findViewById(R.id.avaialable15TVID);
        avaialable16TVID = (TextView) findViewById(R.id.avaialable16TVID);
        avaialable17TVID = (TextView) findViewById(R.id.avaialable17TVID);
        avaialable18TVID = (TextView) findViewById(R.id.avaialable18TVID);
        avaialable19TVID = (TextView) findViewById(R.id.avaialable19TVID);
        avaialable20TVID = (TextView) findViewById(R.id.avaialable20TVID);
        avaialable21TVID = (TextView) findViewById(R.id.avaialable21TVID);
        avaialable22TVID = (TextView) findViewById(R.id.avaialable22TVID);
        avaialable23TVID = (TextView) findViewById(R.id.avaialable23TVID);
        avaialable24TVID = (TextView) findViewById(R.id.avaialable24TVID);
    }

    private void refreshLinearlayoutBackgrounds() {
        FirstRowFirstColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_availe_bg_color));
        FirstRowFirstColumnLLID.setVisibility(View.GONE);
        FirstRowSecondColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_availe_bg_color));
        FirstRowSecondColumnLLID.setVisibility(View.GONE);
        FirstRowThirdColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_availe_bg_color));
        FirstRowThirdColumnLLID.setVisibility(View.GONE);

        SecondRowFirstColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_availe_bg_color));
        SecondRowFirstColumnLLID.setVisibility(View.GONE);
        SecondRowSecondColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_availe_bg_color));
        SecondRowSecondColumnLLID.setVisibility(View.GONE);
        SecondRowThirdColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_availe_bg_color));
        SecondRowThirdColumnLLID.setVisibility(View.GONE);

        ThirdRowFirstColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_availe_bg_color));
        ThirdRowFirstColumnLLID.setVisibility(View.GONE);
        ThirdRowSecondColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_availe_bg_color));
        ThirdRowSecondColumnLLID.setVisibility(View.GONE);
        ThirdRowThirdColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_availe_bg_color));
        ThirdRowThirdColumnLLID.setVisibility(View.GONE);
        FourthRowFirstColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_availe_bg_color));
        FourthRowFirstColumnLLID.setVisibility(View.GONE);
        FourthRowSecondColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_availe_bg_color));
        FourthRowSecondColumnLLID.setVisibility(View.GONE);
        FourthRowThirdColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_availe_bg_color));
        FourthRowThirdColumnLLID.setVisibility(View.GONE);
        FifthRowFirstColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_availe_bg_color));
        FifthRowFirstColumnLLID.setVisibility(View.GONE);
        FifthRowSecondColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_availe_bg_color));
        FifthRowSecondColumnLLID.setVisibility(View.GONE);
        FifthRowThirdColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_availe_bg_color));
        FifthRowThirdColumnLLID.setVisibility(View.GONE);

        SixthRowFirstColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_availe_bg_color));
        SixthRowFirstColumnLLID.setVisibility(View.GONE);
        SixthRowSecondColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_availe_bg_color));
        SixthRowSecondColumnLLID.setVisibility(View.GONE);
        SixthRowThirdColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_availe_bg_color));
        SixthRowThirdColumnLLID.setVisibility(View.GONE);

        SeventhRowFirstColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_availe_bg_color));
        SeventhRowFirstColumnLLID.setVisibility(View.GONE);
        SeventhRowSecondColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_availe_bg_color));
        SeventhRowSecondColumnLLID.setVisibility(View.GONE);
        SeventhRowThirdColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_availe_bg_color));
        SeventhRowThirdColumnLLID.setVisibility(View.GONE);

        EigthRowFirstColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_availe_bg_color));
        EigthRowFirstColumnLLID.setVisibility(View.GONE);
        EigthRowSecondColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_availe_bg_color));
        EigthRowSecondColumnLLID.setVisibility(View.GONE);
        EigthRowThirdColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_availe_bg_color));
        EigthRowThirdColumnLLID.setVisibility(View.GONE);
        gridView.setVisibility(View.GONE);
        gridrow2.setVisibility(View.GONE);
        gridrow3.setVisibility(View.GONE);
        gridrow4.setVisibility(View.GONE);
        gridrow5.setVisibility(View.GONE);
        gridrow6.setVisibility(View.GONE);
        gridrow7.setVisibility(View.GONE);
        gridrow8.setVisibility(View.GONE);

        rowOneCourtsLLID.setVisibility(View.GONE);
        rowtwoCourtsLLID.setVisibility(View.GONE);
        rowthreeCourtsLLID.setVisibility(View.GONE);
        rowfourCourtsLLID.setVisibility(View.GONE);
        rowfiveCourtsLLID.setVisibility(View.GONE);
        rowsixCourtsLLID.setVisibility(View.GONE);
        rowsevenCourtsLLID.setVisibility(View.GONE);
        roweigthCourtsLLID.setVisibility(View.GONE);
    }

    private void setGridForCourts() {
        Button confirmBookingBtn = (Button) findViewById(R.id.confirmBookingBtnID);
        confirmBookingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utility.isOnline(getApplicationContext())) {
                    sendConfirmationRequest(AppConstants.CONFIRM_BOOKING_URL);
                } else {
                    loadingSportsTroubleConnecting();
                }
                Toast.makeText(AvailableListFragment.this, "" + selectedStrings, Toast.LENGTH_SHORT).show();
            }
        });

        Button confirmBookingBtn2 = (Button) findViewById(R.id.confirmBookingBtn2ID);
        confirmBookingBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utility.isOnline(getApplicationContext())) {
                    sendConfirmationRequest(AppConstants.CONFIRM_BOOKING_URL);
                } else {
                    loadingSportsTroubleConnecting();
                }
                Toast.makeText(AvailableListFragment.this, "" + selectedStrings, Toast.LENGTH_SHORT).show();
            }
        });


        Button confirmBookingBtn3 = (Button) findViewById(R.id.confirmBookingBtn3ID);

        confirmBookingBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utility.isOnline(getApplicationContext())) {
                    sendConfirmationRequest(AppConstants.CONFIRM_BOOKING_URL);
                } else {
                    loadingSportsTroubleConnecting();
                }
                Toast.makeText(AvailableListFragment.this, "" + selectedStrings, Toast.LENGTH_SHORT).show();
            }
        });

        Button confirmBookingBtn4 = (Button) findViewById(R.id.confirmBookingBtn4ID);
        confirmBookingBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utility.isOnline(getApplicationContext())) {
                    sendConfirmationRequest(AppConstants.CONFIRM_BOOKING_URL);
                } else {
                    loadingSportsTroubleConnecting();
                }
                Toast.makeText(AvailableListFragment.this, "" + selectedStrings, Toast.LENGTH_SHORT).show();
            }
        });


        Button confirmBookingBtn5 = (Button) findViewById(R.id.confirmBookingBtn5ID);
        confirmBookingBtn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utility.isOnline(getApplicationContext())) {
                    sendConfirmationRequest(AppConstants.CONFIRM_BOOKING_URL);
                } else {
                    loadingSportsTroubleConnecting();
                }
                Toast.makeText(AvailableListFragment.this, "" + selectedStrings, Toast.LENGTH_SHORT).show();
            }
        });

        Button confirmBookingBtn6 = (Button) findViewById(R.id.confirmBookingBtn6ID);
        confirmBookingBtn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utility.isOnline(getApplicationContext())) {
                    sendConfirmationRequest(AppConstants.CONFIRM_BOOKING_URL);
                } else {
                    loadingSportsTroubleConnecting();
                }
                Toast.makeText(AvailableListFragment.this, "" + selectedStrings, Toast.LENGTH_SHORT).show();
            }
        });

        Button confirmBookingBtn7 = (Button) findViewById(R.id.confirmBookingBtn7ID);
        confirmBookingBtn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utility.isOnline(getApplicationContext())) {
                    sendConfirmationRequest(AppConstants.CONFIRM_BOOKING_URL);
                } else {
                    loadingSportsTroubleConnecting();
                }
                Toast.makeText(AvailableListFragment.this, "" + selectedStrings, Toast.LENGTH_SHORT).show();
            }
        });

        Button confirmBookingBtn8 = (Button) findViewById(R.id.confirmBookingBtn8ID);
        confirmBookingBtn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utility.isOnline(getApplicationContext())) {
                    sendConfirmationRequest(AppConstants.CONFIRM_BOOKING_URL);
                } else {
                    loadingSportsTroubleConnecting();
                }
                Toast.makeText(AvailableListFragment.this, "" + selectedStrings, Toast.LENGTH_SHORT).show();
            }
        });

        gridView = (ExpandableHeightGridView) findViewById(R.id.grid);
        gridrow2 = (ExpandableHeightGridView) findViewById(R.id.gridrow2);
        gridrow3 = (ExpandableHeightGridView) findViewById(R.id.gridrow3);
        gridrow4 = (ExpandableHeightGridView) findViewById(R.id.gridrow4);
        gridrow5 = (ExpandableHeightGridView) findViewById(R.id.gridrow5);
        gridrow6 = (ExpandableHeightGridView) findViewById(R.id.gridrow6);
        gridrow7 = (ExpandableHeightGridView) findViewById(R.id.gridrow7);
        gridrow8 = (ExpandableHeightGridView) findViewById(R.id.gridrow8);

        gridView.setExpanded(true);
        gridrow2.setExpanded(true);
        gridrow3.setExpanded(true);
        gridrow4.setExpanded(true);
        gridrow5.setExpanded(true);
        gridrow6.setExpanded(true);
        gridrow7.setExpanded(true);
        gridrow8.setExpanded(true);

        //1 Grid
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                int selectedIndex = adapter1.selectedPositions.indexOf(position);
                if (selectedIndex > -1) {
                    adapter1.selectedPositions.remove(selectedIndex);
                    ((GridItemView) v).display(false);
                    selectedStrings.remove(courtDetailsDataLocal1.get(position).get_courts_id());
                } else {
                    if (courtDetailsDataLocal1.get(position).get_available()) {
                        adapter1.selectedPositions.add(position);
                        ((GridItemView) v).display(true);
                        selectedStrings.add(courtDetailsDataLocal1.get(position).get_courts_id());
                    }
                }
            }
        });


        //2 Grid
        gridrow2.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                int selectedIndex = adapter1.selectedPositions.indexOf(position);
                if (selectedIndex > -1) {
                    adapter1.selectedPositions.remove(selectedIndex);
                    ((GridItemView) v).display(false);
                    selectedStrings.remove(courtDetailsDataLocal1.get(position).get_courts_id());
                } else {
                    if (courtDetailsDataLocal1.get(position).get_available()) {
                        adapter1.selectedPositions.add(position);
                        ((GridItemView) v).display(true);
                        selectedStrings.add(courtDetailsDataLocal1.get(position).get_courts_id());
                    }
                }
            }
        });

        //3 Grid
        gridrow3.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                int selectedIndex = adapter1.selectedPositions.indexOf(position);
                if (selectedIndex > -1) {
                    adapter1.selectedPositions.remove(selectedIndex);
                    ((GridItemView) v).display(false);
                    selectedStrings.remove(courtDetailsDataLocal1.get(position).get_courts_id());
                } else {
                    if (courtDetailsDataLocal1.get(position).get_available()) {
                        adapter1.selectedPositions.add(position);
                        ((GridItemView) v).display(true);
                        selectedStrings.add(courtDetailsDataLocal1.get(position).get_courts_id());
                    }
                }
            }
        });

        //4 Grid
        gridrow4.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                int selectedIndex = adapter1.selectedPositions.indexOf(position);
                if (selectedIndex > -1) {
                    adapter1.selectedPositions.remove(selectedIndex);
                    ((GridItemView) v).display(false);
                    selectedStrings.remove(courtDetailsDataLocal1.get(position).get_courts_id());
                } else {
                    if (courtDetailsDataLocal1.get(position).get_available()) {
                        adapter1.selectedPositions.add(position);
                        ((GridItemView) v).display(true);
                        selectedStrings.add(courtDetailsDataLocal1.get(position).get_courts_id());
                    }
                }
            }
        });

        //5 Grid
        gridrow5.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                int selectedIndex = adapter1.selectedPositions.indexOf(position);
                if (selectedIndex > -1) {
                    adapter1.selectedPositions.remove(selectedIndex);
                    ((GridItemView) v).display(false);
                    selectedStrings.remove(courtDetailsDataLocal1.get(position).get_courts_id());
                } else {
                    if (courtDetailsDataLocal1.get(position).get_available()) {
                        adapter1.selectedPositions.add(position);
                        ((GridItemView) v).display(true);
                        selectedStrings.add(courtDetailsDataLocal1.get(position).get_courts_id());
                    }
                }
            }
        });

        //6 Grid
        gridrow6.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                int selectedIndex = adapter1.selectedPositions.indexOf(position);
                if (selectedIndex > -1) {
                    adapter1.selectedPositions.remove(selectedIndex);
                    ((GridItemView) v).display(false);
                    selectedStrings.remove(courtDetailsDataLocal1.get(position).get_courts_id());
                } else {
                    if (courtDetailsDataLocal1.get(position).get_available()) {
                        adapter1.selectedPositions.add(position);
                        ((GridItemView) v).display(true);
                        selectedStrings.add(courtDetailsDataLocal1.get(position).get_courts_id());
                    }
                }
            }
        });

        //7 Grid
        gridrow7.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                int selectedIndex = adapter1.selectedPositions.indexOf(position);
                if (selectedIndex > -1) {
                    adapter1.selectedPositions.remove(selectedIndex);
                    ((GridItemView) v).display(false);
                    selectedStrings.remove(courtDetailsDataLocal1.get(position).get_courts_id());
                } else {
                    if (courtDetailsDataLocal1.get(position).get_available()) {
                        adapter1.selectedPositions.add(position);
                        ((GridItemView) v).display(true);
                        selectedStrings.add(courtDetailsDataLocal1.get(position).get_courts_id());
                    }
                }
            }
        });

        //8 Grid
        gridrow8.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                int selectedIndex = adapter1.selectedPositions.indexOf(position);
                if (selectedIndex > -1) {
                    adapter1.selectedPositions.remove(selectedIndex);
                    ((GridItemView) v).display(false);
                    selectedStrings.remove(courtDetailsDataLocal1.get(position).get_courts_id());
                } else {
                    if (courtDetailsDataLocal1.get(position).get_available()) {
                        adapter1.selectedPositions.add(position);
                        ((GridItemView) v).display(true);
                        selectedStrings.add(courtDetailsDataLocal1.get(position).get_courts_id());
                    }
                }
            }
        });
    }

    public void sendConfirmationRequest(String sportsUrl) {
        // Make RESTful Web Service call using AsyncHttpClient object
        //192.168.0.12/playcer/public/bookslot?courts[]=[33]&slot_id=1&date=2016-06-06&user_id=13
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        for (int i = 0; i < selectedStrings.size(); i++) {
            // idsList.add(Integer.parseInt(selectedStrings.get(i).toString()));
        }
        /*ArrayList<Integer> innerarray = new ArrayList<Integer>();
        ArrayList<Integer> resultList = getIntegerArray(selectedStrings);
        innerarray.add(resultList.);*/ //idbu
        //Log.v("result", "Shiva" + idsList);
        //params.put("courts[]", idsList); //1
        String[] ids = new String[selectedStrings.size()];
        for (int i = 0; i < selectedStrings.size(); i++) {
            if (i == 0) {
                ids[i] = "[" + selectedStrings.get(i) + "";
            } else if ((selectedStrings.size() - 1) == i) {
                ids[i] = "" + selectedStrings.get(i) + "]";
            } else {
                ids[i] = "," + selectedStrings.get(i) + "";
            }
        }
        ArrayList<Integer> resultList = getIntegerArray(selectedStrings);
        String courtsIds = "[" + resultList + "]";
        courtsIds = courtsIds.substring(1, courtsIds.length() - 1);
        courtsIds = courtsIds.substring(1, courtsIds.length() - 1);
        courtsIds = courtsIds.trim();
        courtsIds = courtsIds.replaceAll("\\s+", "");
        //params.put("courts[]", courtsIds); //1
        params.put("courts", courtsIds); //1
        params.put("slot_id", slotId);
        params.put("date", datestr);
        params.put("user_id", SharedPreference.getPreferences(AvailableListFragment.this, "USER_ID"));
        client.post(sportsUrl, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(String response) {
                if (response != null) {
                    System.out.println(response);
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        String message = jsonResponse.optString("message").toString();
                        boolean success = jsonResponse.getBoolean("success");
                        if (success) {
                            Utility.showCustomToast(message, AvailableListFragment.this);
                            /*Intent verifyIntent = new Intent(AvailableListFragment.this, MainActivity.class);
                            startActivity(verifyIntent);*/
                            //finish();
                            if (Utility.isOnline(getApplicationContext())) {
                                progressWheel_CENTER_SPORTS.setVisibility(View.VISIBLE);
                                refreshLinearlayoutBackgrounds();

                                completeslotsSVID.setVisibility(View.GONE);
                                SlotSelectDividerShowAllWiseGoneView();
                                ifNetisAvilableAvailbleList();
                            } else {
                                loadingSportsTroubleConnecting();
                            }
                        } else {
                            Utility.showCustomToast(message, AvailableListFragment.this);
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
                if (statusCode == 404) {
                    Utility.showCustomToast(getResources().getString(R.string.request_not_found), AvailableListFragment.this);
                } else if (statusCode == 500) {
                    Utility.showCustomToast(getResources().getString(R.string.some_went_wrong), AvailableListFragment.this);
                } else {
                    Utility.showCustomToast(getResources().getString(R.string.unexpected_error), AvailableListFragment.this);
                }
            }
        });
    }

    private ArrayList<Integer> getIntegerArray(ArrayList<String> selectedStrings) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        for (String stringValue : selectedStrings) {
            try {
                //Convert String to Integer, and store it into integer array list.
                result.add(Integer.parseInt(stringValue));
            } catch (NumberFormatException nfe) {
                //System.out.println("Could not parse " + nfe);
                Log.w("NumberFormat", "Parsing failed! " + stringValue + " can not be an integer");
            }
        }
        return result;
    }


    private void loadingSportsTroubleConnecting() {
        noSportsNetLL = (LinearLayout) findViewById(R.id.noNetworkConnectRLID);
        noSportsNetLL.setVisibility(View.VISIBLE);
        Button noNetWorkTryAgainBtn = (Button) findViewById(R.id.noNetWorkTryAgainBtnID);
        noNetWorkTryAgainBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                noSportsNetLL.setVisibility(View.GONE);
                completeslotsSVID.setVisibility(View.GONE);
                progressWheel_CENTER_SPORTS.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        if (Utility.isOnline(getApplicationContext())) {
                            ifNetisAvilableAvailbleList();
                        } else {
                            progressWheel_CENTER_SPORTS.setVisibility(View.GONE);
                            noSportsNetLL.setVisibility(View.VISIBLE);
                        }
                    }
                }, 500);
            }
        });
    }

    private void ifNetisAvilableSports() {
        sportAvailbleRequest(AppConstants.SPORTS_NAMES_LIST);
    }

    public final void ifNetisAvilableAvailbleList() {
        sendRequestToGetAvailbleList(AppConstants.AVAILABLE_LIST_URL);
    }

    private final void sendRequestToGetAvailbleList(String sportsListUrl) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("facility_id", SharedPreference.getPreferences(AvailableListFragment.this, "KEY_FACILITY_ID")); //5
        /*String SlectedSpinner = SharedPreference.getPreferences(AvailableListFragment.this, "SELECTED_SPORT_ID");
        if (SlectedSpinner != null) {
            params.put("sports_id", SlectedSpinner); //1
        }
        else {
            params.put("sports_id", SharedPreference.getPreferences(AvailableListFragment.this, "SPORT_ID")); //1
        }*/
        //if (!SharedPreference.getPreferences(AvailableListFragment.this, "SPORT_ID").equals("0")){}
        params.put("sports_id", SharedPreference.getPreferences(AvailableListFragment.this, "SPORT_ID")); //1
        params.put("date", SharedPreference.getPreferences(AvailableListFragment.this, "DATE"));//DATE  //2016-06-07
        client.post(sportsListUrl, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(String response) {
                if (response != null) {
                    try {
                        System.out.println(response);
                        try {
                            /****** Creates a new JSONObject with name/value mappings from the JSON string. ********/
                            /// jsonResponse = new JSONObject(response);
                            JSONArray jsonArray = new JSONArray(response);
                            /*********** Process each JSON Node ************/
                            countList = jsonArray.length();

                            if (countList != 0) {
                                _singleAvailbleListData = new ArrayList<SingleAvailbleListData>();
                                _courtDetailsData = new ArrayList<CourtDetailsData>();
                                for (int i = 0; i < countList; i++) {
                                    /****** Get Object for each JSON node.***********/
                                    JSONObject jsonChildNode = jsonArray.getJSONObject(i);

                                    /******* Fetch node values **********/
                                    String sport_id = jsonChildNode.optString("slotId").toString();
                                    String sport_name = jsonChildNode.optString("slotName").toString();
                                    int totalCourts = Integer.parseInt(jsonChildNode.optString("totalCourts").toString());
                                    int availableCourts = Integer.parseInt(jsonChildNode.optString("availableCourts").toString());
                                    boolean disabled = jsonChildNode.getBoolean("disabled");

                                    if (availableCourts != 0) {
                                        _courtDetailsData = new ArrayList<CourtDetailsData>(); // This will create new object every time
                                        JSONArray courtDetails = jsonChildNode.getJSONArray("courtDetails");
                                        for (int j = 0; j < courtDetails.length(); j++) {
                                            JSONObject courtDetailsOBJ = courtDetails.getJSONObject(j);
                                            String sportsId = courtDetailsOBJ.optString("sportsId").toString();
                                            String courtName = courtDetailsOBJ.optString("courtName").toString();
                                            String day = courtDetailsOBJ.optString("day").toString();
                                            String courts_id = courtDetailsOBJ.optString("courts_id").toString();
                                            String start_time = courtDetailsOBJ.optString("start_time").toString();
                                            String end_time = courtDetailsOBJ.optString("end_time").toString();
                                            boolean available = courtDetailsOBJ.getBoolean("available");
                                            _courtDetailsData.add(new CourtDetailsData(sportsId, courtName, day, courts_id, start_time, end_time, available));
                                        }
                                    }
                                    _singleAvailbleListData.add(new SingleAvailbleListData(sport_id, sport_name, totalCourts, availableCourts, disabled, _courtDetailsData));
                                }

                                /************ Show Output on screen/activity **********/
                                new Handler().postDelayed(new Runnable() {

                                    @Override
                                    public void run() {
                                        // This method will be executed once the timer is over
                                        if (countList != 0) {
                                            progressWheel_CENTER_SPORTS.setVisibility(View.GONE);
                                            completeslotsSVID.setVisibility(View.VISIBLE);
                                            //gridViewsVisible();
                                            for (int i = 0; i < _singleAvailbleListData.size(); i++) {
                                                if (i == 0) {
                                                    FirstRowFirstColumnLLID.setTag(i); // Based on this tag we will retrive courts.......
                                                    slottime1TVID.setText("" + _singleAvailbleListData.get(i).get_slotName());
                                                    final String slotId1 = _singleAvailbleListData.get(i).get_slotId();     //Individual Slot id
                                                    final int courtEnable = _singleAvailbleListData.get(i).get_availbleCourts();
                                                    final boolean slotdisable = _singleAvailbleListData.get(i).get_disbled();
                                                    totalCourts1TVID.setText("" + "/"+_singleAvailbleListData.get(i).get_totalCourts()); //based on that show aval or un avail
                                                    availableCourts1TVID.setText("" + _singleAvailbleListData.get(i).get_availbleCourts());
                                                    if (slotdisable){
                                                        slottime1TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        totalCourts1TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        availableCourts1TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        avaialable1TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                    }
                                                    else {
                                                        slottime1TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        totalCourts1TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        availableCourts1TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        avaialable1TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                    }
                                                    if (_singleAvailbleListData.get(i).get_disbled()) {
                                                        avaialable1TVID.setText(getResources().getString(R.string.slot_un_available_slot));
                                                        avaialable1TVID.setVisibility(View.VISIBLE);

                                                        FirstRowFirstColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_unavaile_bg_color));
                                                    } else {
                                                        if (_singleAvailbleListData.get(i).get_availbleCourts() == 0) {
                                                            FirstRowFirstColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_unavaile_bg_color));
                                                            avaialable1TVID.setText(getResources().getString(R.string.un_available_slot));
                                                        }
                                                        avaialable1TVID.setText(getResources().getString(R.string.available_slot));
                                                    }
                                                    FirstRowFirstColumnLLID.setVisibility(View.VISIBLE);


                                                    FirstRowFirstColumnLLID.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            if (!slotdisable) {
                                                                if (courtEnable != 0) {
                                                                    if (rowOneCourtsLLID.getVisibility() == View.GONE) {
                                                                        firstSlotSelectDividerShowView();
                                                                        //FirstRowFirstColumnLLID.setIma
                                                                        slotId = slotId1;
                                                                        int kk = (Integer) v.getTag();
                                                                        courtDetailsDataLocal1 = _singleAvailbleListData.get(kk).get_courtDetails();

                                                                        if (courtDetailsDataLocal1.size() > 0) {
                                                                            rowOneCourtsLLID.setVisibility(View.VISIBLE);
                                                                            gridView.setVisibility(View.VISIBLE);
                                                                            rowtwoCourtsLLID.setVisibility(View.GONE);
                                                                            rowthreeCourtsLLID.setVisibility(View.GONE);
                                                                            rowfourCourtsLLID.setVisibility(View.GONE);
                                                                            rowfiveCourtsLLID.setVisibility(View.GONE);
                                                                            rowsixCourtsLLID.setVisibility(View.GONE);
                                                                            rowsevenCourtsLLID.setVisibility(View.GONE);
                                                                            roweigthCourtsLLID.setVisibility(View.GONE);

                                                                            selectedStrings = new ArrayList<>();
                                                                            adapter1 = new GridViewAdapter(courtDetailsDataLocal1, AvailableListFragment.this);

                                                                            gridView.setAdapter(adapter1);
                                                                        } else {
                                                                            Toast.makeText(AvailableListFragment.this, "No Courts avaialable!", Toast.LENGTH_SHORT).show();
                                                                        }

                                                                    } else {
                                                                        SlotSelectDividerShowAllWiseGoneView();
                                                                        rowOneCourtsLLID.setVisibility(View.GONE);
                                                                    }
                                                                }
                                                                Toast.makeText(AvailableListFragment.this, "No Courts avaialable!", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                                }
                                                if (i == 1) {
                                                    FirstRowSecondColumnLLID.setTag(i); // Based on this tag we will retrive courts.......
                                                    slottime2TVID.setText("" + _singleAvailbleListData.get(i).get_slotName());
                                                    final String slotId1 = _singleAvailbleListData.get(i).get_slotId();     //Individual Slot id
                                                    final int courtEnable = _singleAvailbleListData.get(i).get_availbleCourts();
                                                    final boolean slotdisable = _singleAvailbleListData.get(i).get_disbled();

                                                    if (slotdisable){
                                                        slottime2TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        totalCourts2TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        availableCourts2TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        avaialable2TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                    }
                                                    else {
                                                        slottime2TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        totalCourts2TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        availableCourts2TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        avaialable2TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                    }

                                                    totalCourts2TVID.setText("" + "/"+_singleAvailbleListData.get(i).get_totalCourts()); //based on that show aval or un avail
                                                    availableCourts2TVID.setText("" + _singleAvailbleListData.get(i).get_availbleCourts());

                                                    if (_singleAvailbleListData.get(i).get_disbled()) {
                                                        avaialable2TVID.setText(getResources().getString(R.string.slot_un_available_slot));
                                                        FirstRowSecondColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_unavaile_bg_color));
                                                    } else {
                                                        if (_singleAvailbleListData.get(i).get_availbleCourts() == 0) {
                                                            FirstRowSecondColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_unavaile_bg_color));
                                                            avaialable2TVID.setText(getResources().getString(R.string.un_available_slot));
                                                        }
                                                        avaialable2TVID.setText(getResources().getString(R.string.available_slot));
                                                    }
                                                    FirstRowSecondColumnLLID.setVisibility(View.VISIBLE);

                                                    FirstRowSecondColumnLLID.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            if (!slotdisable) {
                                                                if (courtEnable != 0) {
                                                                    slotId = slotId1;
                                                                    if (rowOneCourtsLLID.getVisibility() == View.VISIBLE) {
                                                                        secondSlotSelectDividerShowView();
                                                                        int kk = (Integer) v.getTag();
                                                                        courtDetailsDataLocal1 = _singleAvailbleListData.get(kk).get_courtDetails();

                                                                        if (courtDetailsDataLocal1.size() > 0) {
                                                                            gridView.setVisibility(View.VISIBLE);
                                                                            rowOneCourtsLLID.setVisibility(View.VISIBLE);
                                                                            rowtwoCourtsLLID.setVisibility(View.GONE);
                                                                            rowthreeCourtsLLID.setVisibility(View.GONE);
                                                                            rowfourCourtsLLID.setVisibility(View.GONE);
                                                                            rowfiveCourtsLLID.setVisibility(View.GONE);
                                                                            rowsixCourtsLLID.setVisibility(View.GONE);
                                                                            rowsevenCourtsLLID.setVisibility(View.GONE);
                                                                            roweigthCourtsLLID.setVisibility(View.GONE);

                                                                            selectedStrings = new ArrayList<>();
                                                                            adapter1 = new GridViewAdapter(courtDetailsDataLocal1, AvailableListFragment.this);
                                                                            gridView.setAdapter(adapter1);
                                                                        } else {
                                                                            Toast.makeText(AvailableListFragment.this, "No Courts avaialable!", Toast.LENGTH_SHORT).show();
                                                                        }

                                                                    } else {
                                                                        SlotSelectDividerShowAllWiseGoneView();
                                                                        rowOneCourtsLLID.setVisibility(View.GONE);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    });
                                                }
                                                if (i == 2) {
                                                    FirstRowThirdColumnLLID.setTag(i); // Based on this tag we will retrive courts.......
                                                    slottime3TVID.setText("" + _singleAvailbleListData.get(i).get_slotName());
                                                    final String slotId1 = _singleAvailbleListData.get(i).get_slotId();     //Individual Slot id
                                                    final int courtEnable = _singleAvailbleListData.get(i).get_availbleCourts();
                                                    final boolean slotdisable = _singleAvailbleListData.get(i).get_disbled();
                                                    totalCourts3TVID.setText("" + "/"+_singleAvailbleListData.get(i).get_totalCourts()); //based on that show aval or un avail
                                                    availableCourts3TVID.setText("" + _singleAvailbleListData.get(i).get_availbleCourts());
                                                    if (slotdisable){
                                                        slottime3TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        totalCourts3TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        availableCourts3TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        avaialable3TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                    }
                                                    else {
                                                        slottime3TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        totalCourts3TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        availableCourts3TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        avaialable3TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                    }

                                                    if (_singleAvailbleListData.get(i).get_disbled()) {
                                                        avaialable3TVID.setText(getResources().getString(R.string.slot_un_available_slot));
                                                        FirstRowThirdColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_unavaile_bg_color));
                                                    } else {
                                                        if (_singleAvailbleListData.get(i).get_availbleCourts() == 0) {
                                                            FirstRowThirdColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_unavaile_bg_color));
                                                            avaialable3TVID.setText(getResources().getString(R.string.un_available_slot));
                                                        }
                                                        avaialable3TVID.setText(getResources().getString(R.string.available_slot));
                                                    }
                                                    FirstRowThirdColumnLLID.setVisibility(View.VISIBLE);

                                                    FirstRowThirdColumnLLID.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            if (!slotdisable) {
                                                                if (courtEnable != 0) {
                                                                    slotId = slotId1;
                                                                    if (rowOneCourtsLLID.getVisibility() == View.VISIBLE) {
                                                                        int kk = (Integer) v.getTag();
                                                                        thirdSlotSelectDividerShowView();
                                                                        courtDetailsDataLocal1 = _singleAvailbleListData.get(kk).get_courtDetails();

                                                                        if (courtDetailsDataLocal1.size() > 0) {
                                                                            gridView.setVisibility(View.VISIBLE);
                                                                            rowOneCourtsLLID.setVisibility(View.VISIBLE);
                                                                            rowtwoCourtsLLID.setVisibility(View.GONE);
                                                                            rowthreeCourtsLLID.setVisibility(View.GONE);
                                                                            rowfourCourtsLLID.setVisibility(View.GONE);
                                                                            rowfiveCourtsLLID.setVisibility(View.GONE);
                                                                            rowsixCourtsLLID.setVisibility(View.GONE);
                                                                            rowsevenCourtsLLID.setVisibility(View.GONE);
                                                                            roweigthCourtsLLID.setVisibility(View.GONE);

                                                                            selectedStrings = new ArrayList<>();
                                                                            adapter1 = new GridViewAdapter(courtDetailsDataLocal1, AvailableListFragment.this);
                                                                            gridView.setAdapter(adapter1);
                                                                        } else {
                                                                            Toast.makeText(AvailableListFragment.this, "No Courts avaialable!", Toast.LENGTH_SHORT).show();
                                                                        }

                                                                    } else {
                                                                        SlotSelectDividerShowAllWiseGoneView();
                                                                        rowOneCourtsLLID.setVisibility(View.GONE);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    });
                                                }
                                                if (i == 3) {
                                                    SecondRowFirstColumnLLID.setTag(i); // Based on this tag we will retrive courts.......
                                                    slottime4TVID.setText("" + _singleAvailbleListData.get(i).get_slotName());
                                                    final String slotId1 = _singleAvailbleListData.get(i).get_slotId();     //Individual Slot id
                                                    final int courtEnable = _singleAvailbleListData.get(i).get_availbleCourts();
                                                    final boolean slotdisable = _singleAvailbleListData.get(i).get_disbled();
                                                    totalCourts4TVID.setText("" + "/"+_singleAvailbleListData.get(i).get_totalCourts()); //based on that show aval or un avail
                                                    availableCourts4TVID.setText("" + _singleAvailbleListData.get(i).get_availbleCourts());
                                                    if (slotdisable){
                                                        slottime4TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        totalCourts4TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        availableCourts4TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        avaialable4TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                    }
                                                    else {
                                                        slottime4TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        totalCourts4TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        availableCourts4TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        avaialable4TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                    }

                                                    if (_singleAvailbleListData.get(i).get_disbled()) {
                                                        avaialable4TVID.setText(getResources().getString(R.string.slot_un_available_slot));
                                                        SecondRowFirstColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_unavaile_bg_color));
                                                    } else {
                                                        if (_singleAvailbleListData.get(i).get_availbleCourts() == 0) {
                                                            SecondRowFirstColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_unavaile_bg_color));
                                                            avaialable4TVID.setText(getResources().getString(R.string.un_available_slot));
                                                        }
                                                        avaialable4TVID.setText(getResources().getString(R.string.available_slot));
                                                    }
                                                    SecondRowFirstColumnLLID.setVisibility(View.VISIBLE);

                                                    SecondRowFirstColumnLLID.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            if (!slotdisable) {
                                                                if (courtEnable != 0) {
                                                                    slotId = slotId1;
                                                                    if (rowtwoCourtsLLID.getVisibility() == View.GONE) {

                                                                        int kk = (Integer) v.getTag();
                                                                        courtDetailsDataLocal1 = _singleAvailbleListData.get(kk).get_courtDetails();

                                                                        if (courtDetailsDataLocal1.size() > 0) {
                                                                            rowOneCourtsLLID.setVisibility(View.GONE);
                                                                            rowtwoCourtsLLID.setVisibility(View.VISIBLE);
                                                                            fourthSlotSelectDividerShowView();
                                                                            gridrow2.setVisibility(View.VISIBLE);
                                                                            rowthreeCourtsLLID.setVisibility(View.GONE);
                                                                            rowfourCourtsLLID.setVisibility(View.GONE);
                                                                            rowfiveCourtsLLID.setVisibility(View.GONE);
                                                                            rowsixCourtsLLID.setVisibility(View.GONE);
                                                                            rowsevenCourtsLLID.setVisibility(View.GONE);
                                                                            roweigthCourtsLLID.setVisibility(View.GONE);

                                                                            selectedStrings = new ArrayList<>();
                                                                            adapter1 = new GridViewAdapter(courtDetailsDataLocal1, AvailableListFragment.this);
                                                                            gridrow2.setAdapter(adapter1);
                                                                        } else {
                                                                            Toast.makeText(AvailableListFragment.this, "No Courts avaialable!", Toast.LENGTH_SHORT).show();
                                                                        }

                                                                    } else {
                                                                        SlotSelectDividerShowAllWiseGoneView();
                                                                        rowtwoCourtsLLID.setVisibility(View.GONE);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    });
                                                }
                                                if (i == 4) {
                                                    SecondRowSecondColumnLLID.setTag(i); // Based on this tag we will retrive courts.......
                                                    slottime5TVID.setText("" + _singleAvailbleListData.get(i).get_slotName());
                                                    final String slotId1 = _singleAvailbleListData.get(i).get_slotId();     //Individual Slot id
                                                    final int courtEnable = _singleAvailbleListData.get(i).get_availbleCourts();
                                                    final boolean slotdisable = _singleAvailbleListData.get(i).get_disbled();

                                                    availableCourts5TVID.setText("" +_singleAvailbleListData.get(i).get_availbleCourts());
                                                    totalCourts5TVID.setText("" + "/" +_singleAvailbleListData.get(i).get_totalCourts()); //based on that show aval or un avail
                                                    if (slotdisable){
                                                        slottime5TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        totalCourts5TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        availableCourts5TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        avaialable5TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                    }
                                                    else {
                                                        slottime5TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        totalCourts5TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        availableCourts5TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        avaialable5TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                    }

                                                    if (_singleAvailbleListData.get(i).get_disbled()) {
                                                        avaialable5TVID.setText(getResources().getString(R.string.slot_un_available_slot));
                                                        SecondRowSecondColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_unavaile_bg_color));
                                                    } else {
                                                        if (_singleAvailbleListData.get(i).get_availbleCourts() == 0) {
                                                            SecondRowSecondColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_unavaile_bg_color));
                                                            avaialable5TVID.setText(getResources().getString(R.string.un_available_slot));
                                                        }
                                                        avaialable5TVID.setText(getResources().getString(R.string.available_slot));
                                                    }
                                                    SecondRowSecondColumnLLID.setVisibility(View.VISIBLE);

                                                    SecondRowSecondColumnLLID.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            if (!slotdisable) {
                                                                if (courtEnable != 0) {
                                                                    slotId = slotId1;
                                                                    if (rowtwoCourtsLLID.getVisibility() == View.GONE) {
                                                                        int kk = (Integer) v.getTag();
                                                                        courtDetailsDataLocal1 = _singleAvailbleListData.get(kk).get_courtDetails();

                                                                        if (courtDetailsDataLocal1.size() > 0) {
                                                                            rowOneCourtsLLID.setVisibility(View.GONE);
                                                                            fifthSlotSelectDividerShowView();
                                                                            rowtwoCourtsLLID.setVisibility(View.VISIBLE);
                                                                            gridrow2.setVisibility(View.VISIBLE);
                                                                            rowthreeCourtsLLID.setVisibility(View.GONE);
                                                                            rowfourCourtsLLID.setVisibility(View.GONE);
                                                                            rowfiveCourtsLLID.setVisibility(View.GONE);
                                                                            rowsixCourtsLLID.setVisibility(View.GONE);
                                                                            rowsevenCourtsLLID.setVisibility(View.GONE);
                                                                            roweigthCourtsLLID.setVisibility(View.GONE);

                                                                            selectedStrings = new ArrayList<>();
                                                                            adapter1 = new GridViewAdapter(courtDetailsDataLocal1, AvailableListFragment.this);
                                                                            gridrow2.setAdapter(adapter1);
                                                                        } else {
                                                                            Toast.makeText(AvailableListFragment.this, "No Courts avaialable!", Toast.LENGTH_SHORT).show();
                                                                        }

                                                                    } else {
                                                                        SlotSelectDividerShowAllWiseGoneView();
                                                                        SecondRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
                                                                        rowtwoCourtsLLID.setVisibility(View.GONE);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    });
                                                }
                                                if (i == 5) {
                                                    SecondRowThirdColumnLLID.setTag(i); // Based on this tag we will retrive courts.......
                                                    slottime6TVID.setText("" + _singleAvailbleListData.get(i).get_slotName());
                                                    final String slotId1 = _singleAvailbleListData.get(i).get_slotId();     //Individual Slot id
                                                    final int courtEnable = _singleAvailbleListData.get(i).get_availbleCourts();
                                                    final boolean slotdisable = _singleAvailbleListData.get(i).get_disbled();
                                                    availableCourts6TVID.setText("" + _singleAvailbleListData.get(i).get_availbleCourts());
                                                    totalCourts6TVID.setText("" + "/" +_singleAvailbleListData.get(i).get_totalCourts()); //based on that show aval or un avail
                                                    if (slotdisable){
                                                        slottime6TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        totalCourts6TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        availableCourts6TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        avaialable6TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                    }
                                                    else {
                                                        slottime6TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        totalCourts6TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        availableCourts6TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        avaialable6TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                    }

                                                    if (_singleAvailbleListData.get(i).get_disbled()) {
                                                        avaialable6TVID.setText(getResources().getString(R.string.slot_un_available_slot));
                                                        SecondRowThirdColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_unavaile_bg_color));
                                                    } else {
                                                        if (_singleAvailbleListData.get(i).get_availbleCourts() == 0) {
                                                            SecondRowThirdColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_unavaile_bg_color));
                                                            avaialable6TVID.setText(getResources().getString(R.string.un_available_slot));
                                                        }
                                                        avaialable6TVID.setText(getResources().getString(R.string.available_slot));
                                                    }
                                                    SecondRowThirdColumnLLID.setVisibility(View.VISIBLE);

                                                    SecondRowThirdColumnLLID.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            if (!slotdisable) {
                                                                if (courtEnable != 0) {
                                                                    slotId = slotId1;
                                                                    if (rowtwoCourtsLLID.getVisibility() == View.GONE) {
                                                                        sixthSlotSelectDividerShowView();
                                                                        int kk = (Integer) v.getTag();
                                                                        courtDetailsDataLocal1 = _singleAvailbleListData.get(kk).get_courtDetails();

                                                                        if (courtDetailsDataLocal1.size() > 0) {
                                                                            rowOneCourtsLLID.setVisibility(View.GONE);
                                                                            rowtwoCourtsLLID.setVisibility(View.VISIBLE);
                                                                            gridrow2.setVisibility(View.VISIBLE);
                                                                            rowthreeCourtsLLID.setVisibility(View.GONE);
                                                                            rowfourCourtsLLID.setVisibility(View.GONE);
                                                                            rowfiveCourtsLLID.setVisibility(View.GONE);
                                                                            rowsixCourtsLLID.setVisibility(View.GONE);
                                                                            rowsevenCourtsLLID.setVisibility(View.GONE);
                                                                            roweigthCourtsLLID.setVisibility(View.GONE);

                                                                            selectedStrings = new ArrayList<>();
                                                                            adapter1 = new GridViewAdapter(courtDetailsDataLocal1, AvailableListFragment.this);
                                                                            gridrow2.setAdapter(adapter1);
                                                                        } else {
                                                                            Toast.makeText(AvailableListFragment.this, "No Courts avaialable!", Toast.LENGTH_SHORT).show();
                                                                        }

                                                                    } else {
                                                                        SlotSelectDividerShowAllWiseGoneView();
                                                                        SecondRowThirdColumn_SELECT_VID.setVisibility(View.GONE);
                                                                        rowtwoCourtsLLID.setVisibility(View.GONE);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    });
                                                }
                                                if (i == 6) {
                                                    ThirdRowFirstColumnLLID.setTag(i); // Based on this tag we will retrive courts.......
                                                    slottime7TVID.setText("" + _singleAvailbleListData.get(i).get_slotName());
                                                    final String slotId1 = _singleAvailbleListData.get(i).get_slotId();     //Individual Slot id
                                                    final int courtEnable = _singleAvailbleListData.get(i).get_availbleCourts();
                                                    final boolean slotdisable = _singleAvailbleListData.get(i).get_disbled();
                                                    totalCourts7TVID.setText("" + "/" +_singleAvailbleListData.get(i).get_totalCourts()); //based on that show aval or un avail
                                                    if (slotdisable){
                                                        slottime7TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        totalCourts7TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        availableCourts7TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        avaialable7TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                    }
                                                    else {
                                                        slottime7TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        totalCourts7TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        availableCourts7TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        avaialable7TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                    }

                                                    availableCourts7TVID.setText("" + _singleAvailbleListData.get(i).get_availbleCourts());

                                                    if (_singleAvailbleListData.get(i).get_disbled()) {
                                                        avaialable7TVID.setText(getResources().getString(R.string.slot_un_available_slot));
                                                        ThirdRowFirstColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_unavaile_bg_color));
                                                    } else {
                                                        if (_singleAvailbleListData.get(i).get_availbleCourts() == 0) {
                                                            ThirdRowFirstColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_unavaile_bg_color));
                                                            avaialable7TVID.setText(getResources().getString(R.string.un_available_slot));
                                                        }
                                                        avaialable7TVID.setText(getResources().getString(R.string.available_slot));
                                                    }
                                                    ThirdRowFirstColumnLLID.setVisibility(View.VISIBLE);

                                                    ThirdRowFirstColumnLLID.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            if (!slotdisable) {
                                                                if (courtEnable != 0) {
                                                                    slotId = slotId1;
                                                                    if (rowthreeCourtsLLID.getVisibility() == View.GONE) {

                                                                        int kk = (Integer) v.getTag();
                                                                        courtDetailsDataLocal1 = _singleAvailbleListData.get(kk).get_courtDetails();

                                                                        if (courtDetailsDataLocal1.size() > 0) {
                                                                            rowOneCourtsLLID.setVisibility(View.GONE);
                                                                            rowtwoCourtsLLID.setVisibility(View.GONE);
                                                                            seventhSlotSelectDividerShowView();
                                                                            rowthreeCourtsLLID.setVisibility(View.VISIBLE);
                                                                            gridrow3.setVisibility(View.VISIBLE);
                                                                            rowfourCourtsLLID.setVisibility(View.GONE);
                                                                            rowfiveCourtsLLID.setVisibility(View.GONE);
                                                                            rowsixCourtsLLID.setVisibility(View.GONE);
                                                                            rowsevenCourtsLLID.setVisibility(View.GONE);
                                                                            roweigthCourtsLLID.setVisibility(View.GONE);

                                                                            selectedStrings = new ArrayList<>();
                                                                            adapter1 = new GridViewAdapter(courtDetailsDataLocal1, AvailableListFragment.this);
                                                                            gridrow3.setAdapter(adapter1);
                                                                        } else {
                                                                            Toast.makeText(AvailableListFragment.this, "No Courts avaialable!", Toast.LENGTH_SHORT).show();
                                                                        }

                                                                    } else {
                                                                        SlotSelectDividerShowAllWiseGoneView();
                                                                        ThirdRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
                                                                        rowthreeCourtsLLID.setVisibility(View.GONE);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    });
                                                }
                                                if (i == 7) {
                                                    ThirdRowSecondColumnLLID.setTag(i); // Based on this tag we will retrive courts.......
                                                    slottime8TVID.setText("" + _singleAvailbleListData.get(i).get_slotName());
                                                    final String slotId1 = _singleAvailbleListData.get(i).get_slotId();     //Individual Slot id
                                                    final int courtEnable = _singleAvailbleListData.get(i).get_availbleCourts();
                                                    final boolean slotdisable = _singleAvailbleListData.get(i).get_disbled();

                                                    totalCourts8TVID.setText("" + "/" +_singleAvailbleListData.get(i).get_totalCourts()); //based on that show aval or un avail
                                                    if (slotdisable){
                                                        slottime8TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        totalCourts8TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        availableCourts8TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        avaialable8TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                    }
                                                    else {
                                                        slottime8TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        totalCourts8TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        availableCourts8TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        avaialable8TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                    }

                                                    availableCourts8TVID.setText("" + _singleAvailbleListData.get(i).get_availbleCourts());

                                                    if (_singleAvailbleListData.get(i).get_disbled()) {
                                                        avaialable8TVID.setText(getResources().getString(R.string.slot_un_available_slot));
                                                        ThirdRowSecondColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_unavaile_bg_color));
                                                    } else {
                                                        if (_singleAvailbleListData.get(i).get_availbleCourts() == 0) {
                                                            ThirdRowSecondColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_unavaile_bg_color));
                                                            avaialable8TVID.setText(getResources().getString(R.string.un_available_slot));
                                                        }
                                                        avaialable8TVID.setText(getResources().getString(R.string.available_slot));
                                                    }
                                                    ThirdRowSecondColumnLLID.setVisibility(View.VISIBLE);

                                                    ThirdRowSecondColumnLLID.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            if (!slotdisable) {
                                                                if (courtEnable != 0) {
                                                                    slotId = slotId1;
                                                                    if (rowthreeCourtsLLID.getVisibility() == View.GONE) {

                                                                        int kk = (Integer) v.getTag();
                                                                        courtDetailsDataLocal1 = _singleAvailbleListData.get(kk).get_courtDetails();

                                                                        if (courtDetailsDataLocal1.size() > 0) {
                                                                            rowOneCourtsLLID.setVisibility(View.GONE);
                                                                            rowtwoCourtsLLID.setVisibility(View.GONE);
                                                                            rowthreeCourtsLLID.setVisibility(View.VISIBLE);
                                                                            eigthSlotSelectDividerShowView();
                                                                            gridrow3.setVisibility(View.VISIBLE);
                                                                            rowfourCourtsLLID.setVisibility(View.GONE);
                                                                            rowfiveCourtsLLID.setVisibility(View.GONE);
                                                                            rowsixCourtsLLID.setVisibility(View.GONE);
                                                                            rowsevenCourtsLLID.setVisibility(View.GONE);
                                                                            roweigthCourtsLLID.setVisibility(View.GONE);

                                                                            selectedStrings = new ArrayList<>();
                                                                            adapter1 = new GridViewAdapter(courtDetailsDataLocal1, AvailableListFragment.this);
                                                                            gridrow3.setAdapter(adapter1);
                                                                        } else {
                                                                            Toast.makeText(AvailableListFragment.this, "No Courts avaialable!", Toast.LENGTH_SHORT).show();
                                                                        }

                                                                    } else {
                                                                        SlotSelectDividerShowAllWiseGoneView();
                                                                        ThirdRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
                                                                        rowthreeCourtsLLID.setVisibility(View.GONE);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    });
                                                }
                                                if (i == 8) {
                                                    ThirdRowThirdColumnLLID.setTag(i); // Based on this tag we will retrive courts.......
                                                    slottime9TVID.setText("" + _singleAvailbleListData.get(i).get_slotName());
                                                    final String slotId1 = _singleAvailbleListData.get(i).get_slotId();     //Individual Slot id
                                                    final int courtEnable = _singleAvailbleListData.get(i).get_availbleCourts();
                                                    final boolean slotdisable = _singleAvailbleListData.get(i).get_disbled();

                                                    totalCourts9TVID.setText("" + "/" +_singleAvailbleListData.get(i).get_totalCourts()); //based on that show aval or un avail
                                                    if (slotdisable){
                                                        slottime9TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        totalCourts9TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        availableCourts9TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        avaialable9TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                    }
                                                    else {
                                                        slottime9TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        totalCourts9TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        availableCourts9TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        avaialable9TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                    }
                                                    availableCourts9TVID.setText("" + _singleAvailbleListData.get(i).get_availbleCourts());

                                                    if (_singleAvailbleListData.get(i).get_disbled()) {
                                                        avaialable9TVID.setText(getResources().getString(R.string.slot_un_available_slot));
                                                        ThirdRowThirdColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_unavaile_bg_color));
                                                    } else {
                                                        if (_singleAvailbleListData.get(i).get_availbleCourts() == 0) {
                                                            ThirdRowThirdColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_unavaile_bg_color));
                                                            avaialable9TVID.setText(getResources().getString(R.string.un_available_slot));
                                                        }
                                                        avaialable9TVID.setText(getResources().getString(R.string.available_slot));
                                                    }
                                                    ThirdRowThirdColumnLLID.setVisibility(View.VISIBLE);

                                                    ThirdRowThirdColumnLLID.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            if (!slotdisable) {
                                                                if (courtEnable != 0) {
                                                                    slotId = slotId1;
                                                                    if (rowthreeCourtsLLID.getVisibility() == View.GONE) {
                                                                        int kk = (Integer) v.getTag();
                                                                        courtDetailsDataLocal1 = _singleAvailbleListData.get(kk).get_courtDetails();

                                                                        if (courtDetailsDataLocal1.size() > 0) {
                                                                            rowOneCourtsLLID.setVisibility(View.GONE);
                                                                            rowtwoCourtsLLID.setVisibility(View.GONE);
                                                                            rowthreeCourtsLLID.setVisibility(View.VISIBLE);
                                                                            gridrow3.setVisibility(View.VISIBLE);
                                                                            ninthSlotSelectDividerShowView();
                                                                            rowfourCourtsLLID.setVisibility(View.GONE);
                                                                            rowfiveCourtsLLID.setVisibility(View.GONE);
                                                                            rowsixCourtsLLID.setVisibility(View.GONE);
                                                                            rowsevenCourtsLLID.setVisibility(View.GONE);
                                                                            roweigthCourtsLLID.setVisibility(View.GONE);

                                                                            selectedStrings = new ArrayList<>();
                                                                            adapter1 = new GridViewAdapter(courtDetailsDataLocal1, AvailableListFragment.this);
                                                                            gridrow3.setAdapter(adapter1);
                                                                        } else {
                                                                            Toast.makeText(AvailableListFragment.this, "No Courts avaialable!", Toast.LENGTH_SHORT).show();
                                                                        }

                                                                    } else {
                                                                        SlotSelectDividerShowAllWiseGoneView();
                                                                        ThirdRowThirdColumn_SELECT_VID.setVisibility(View.GONE);
                                                                        rowthreeCourtsLLID.setVisibility(View.GONE);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    });
                                                }

                                                if (i == 9) {
                                                    FourthRowFirstColumnLLID.setTag(i); // Based on this tag we will retrive courts.......
                                                    slottime10TVID.setText("" + _singleAvailbleListData.get(i).get_slotName());
                                                    final String slotId1 = _singleAvailbleListData.get(i).get_slotId();     //Individual Slot id
                                                    final int courtEnable = _singleAvailbleListData.get(i).get_availbleCourts();
                                                    final boolean slotdisable = _singleAvailbleListData.get(i).get_disbled();
                                                    totalCourts10TVID.setText("" + "/" +_singleAvailbleListData.get(i).get_totalCourts()); //based on that show aval or un avail
                                                    if (slotdisable){
                                                        slottime10TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        totalCourts10TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        availableCourts10TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        avaialable10TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                    }
                                                    else {
                                                        slottime10TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        totalCourts10TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        availableCourts10TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        avaialable10TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                    }

                                                    availableCourts10TVID.setText("" + _singleAvailbleListData.get(i).get_availbleCourts());

                                                    if (_singleAvailbleListData.get(i).get_disbled()) {
                                                        avaialable10TVID.setText(getResources().getString(R.string.slot_un_available_slot));
                                                        FourthRowFirstColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_unavaile_bg_color));
                                                    } else {
                                                        if (_singleAvailbleListData.get(i).get_availbleCourts() == 0) {
                                                            FourthRowFirstColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_unavaile_bg_color));
                                                            avaialable10TVID.setText(getResources().getString(R.string.un_available_slot));
                                                        }
                                                        avaialable10TVID.setText(getResources().getString(R.string.available_slot));
                                                    }
                                                    FourthRowFirstColumnLLID.setVisibility(View.VISIBLE);

                                                    FourthRowFirstColumnLLID.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            if (!slotdisable) {
                                                                if (courtEnable != 0) {
                                                                    slotId = slotId1;
                                                                    if (rowfourCourtsLLID.getVisibility() == View.GONE) {
                                                                        tenthSlotSelectDividerShowView();
                                                                        int kk = (Integer) v.getTag();
                                                                        courtDetailsDataLocal1 = _singleAvailbleListData.get(kk).get_courtDetails();

                                                                        if (courtDetailsDataLocal1.size() > 0) {
                                                                            rowOneCourtsLLID.setVisibility(View.GONE);
                                                                            rowtwoCourtsLLID.setVisibility(View.GONE);
                                                                            rowthreeCourtsLLID.setVisibility(View.GONE);
                                                                            rowfourCourtsLLID.setVisibility(View.VISIBLE);
                                                                            gridrow4.setVisibility(View.VISIBLE);
                                                                            rowfiveCourtsLLID.setVisibility(View.GONE);
                                                                            rowsixCourtsLLID.setVisibility(View.GONE);
                                                                            rowsevenCourtsLLID.setVisibility(View.GONE);
                                                                            roweigthCourtsLLID.setVisibility(View.GONE);

                                                                            selectedStrings = new ArrayList<>();
                                                                            adapter1 = new GridViewAdapter(courtDetailsDataLocal1, AvailableListFragment.this);
                                                                            gridrow4.setAdapter(adapter1);
                                                                        } else {
                                                                            Toast.makeText(AvailableListFragment.this, "No Courts avaialable!", Toast.LENGTH_SHORT).show();
                                                                        }

                                                                    } else {
                                                                        SlotSelectDividerShowAllWiseGoneView();
                                                                        FourthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
                                                                        rowfourCourtsLLID.setVisibility(View.GONE);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    });
                                                }

                                                if (i == 10) {
                                                    FourthRowSecondColumnLLID.setTag(i); // Based on this tag we will retrive courts.......
                                                    slottime11TVID.setText("" + _singleAvailbleListData.get(i).get_slotName());
                                                    final String slotId1 = _singleAvailbleListData.get(i).get_slotId();     //Individual Slot id
                                                    final int courtEnable = _singleAvailbleListData.get(i).get_availbleCourts();
                                                    final boolean slotdisable = _singleAvailbleListData.get(i).get_disbled();
                                                    totalCourts11TVID.setText("" + "/" +_singleAvailbleListData.get(i).get_totalCourts()); //based on that show aval or un avail
                                                    if (slotdisable){
                                                        slottime11TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        totalCourts11TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        availableCourts11TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        avaialable11TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                    }
                                                    else {
                                                        slottime11TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        totalCourts11TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        availableCourts11TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        avaialable11TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                    }

                                                    availableCourts11TVID.setText("" + _singleAvailbleListData.get(i).get_availbleCourts());

                                                    if (_singleAvailbleListData.get(i).get_disbled()) {
                                                        avaialable11TVID.setText(getResources().getString(R.string.slot_un_available_slot));
                                                        FourthRowSecondColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_unavaile_bg_color));
                                                    } else {
                                                        if (_singleAvailbleListData.get(i).get_availbleCourts() == 0) {
                                                            FourthRowSecondColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_unavaile_bg_color));
                                                            avaialable11TVID.setText(getResources().getString(R.string.un_available_slot));
                                                        }
                                                        avaialable11TVID.setText(getResources().getString(R.string.available_slot));
                                                    }
                                                    FourthRowSecondColumnLLID.setVisibility(View.VISIBLE);

                                                    FourthRowSecondColumnLLID.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            if (!slotdisable) {
                                                                if (courtEnable != 0) {
                                                                    slotId = slotId1;
                                                                    if (rowfourCourtsLLID.getVisibility() == View.GONE) {
                                                                        int kk = (Integer) v.getTag();
                                                                        courtDetailsDataLocal1 = _singleAvailbleListData.get(kk).get_courtDetails();

                                                                        if (courtDetailsDataLocal1.size() > 0) {
                                                                            rowOneCourtsLLID.setVisibility(View.GONE);
                                                                            rowtwoCourtsLLID.setVisibility(View.GONE);
                                                                            rowthreeCourtsLLID.setVisibility(View.GONE);
                                                                            elevenSlotSelectDividerShowView();
                                                                            rowfourCourtsLLID.setVisibility(View.VISIBLE);
                                                                            gridrow4.setVisibility(View.VISIBLE);
                                                                            rowfiveCourtsLLID.setVisibility(View.GONE);
                                                                            rowsixCourtsLLID.setVisibility(View.GONE);
                                                                            rowsevenCourtsLLID.setVisibility(View.GONE);
                                                                            roweigthCourtsLLID.setVisibility(View.GONE);

                                                                            selectedStrings = new ArrayList<>();
                                                                            adapter1 = new GridViewAdapter(courtDetailsDataLocal1, AvailableListFragment.this);
                                                                            gridrow4.setAdapter(adapter1);
                                                                        } else {
                                                                            Toast.makeText(AvailableListFragment.this, "No Courts avaialable!", Toast.LENGTH_SHORT).show();
                                                                        }

                                                                    } else {
                                                                        SlotSelectDividerShowAllWiseGoneView();
                                                                        FourthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
                                                                        rowfourCourtsLLID.setVisibility(View.GONE);
                                                                    }
                                                                }
                                                            }

                                                        }
                                                    });
                                                }

                                                if (i == 11) {
                                                    FourthRowThirdColumnLLID.setTag(i); // Based on this tag we will retrive courts.......
                                                    slottime12TVID.setText("" + _singleAvailbleListData.get(i).get_slotName());
                                                    final String slotId1 = _singleAvailbleListData.get(i).get_slotId();     //Individual Slot id
                                                    final int courtEnable = _singleAvailbleListData.get(i).get_availbleCourts();
                                                    final boolean slotdisable = _singleAvailbleListData.get(i).get_disbled();
                                                    totalCourts12TVID.setText("" + "/" +_singleAvailbleListData.get(i).get_totalCourts()); //based on that show aval or un avail
                                                    if (slotdisable){
                                                        slottime12TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        totalCourts12TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        availableCourts12TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        avaialable12TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                    }
                                                    else {
                                                        slottime12TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        totalCourts12TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        availableCourts12TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        avaialable12TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                    }

                                                    availableCourts12TVID.setText("" + _singleAvailbleListData.get(i).get_availbleCourts());

                                                    if (_singleAvailbleListData.get(i).get_disbled()) {
                                                        avaialable12TVID.setText(getResources().getString(R.string.slot_un_available_slot));
                                                        FourthRowThirdColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_unavaile_bg_color));
                                                    } else {
                                                        if (_singleAvailbleListData.get(i).get_availbleCourts() == 0) {
                                                            FourthRowThirdColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_unavaile_bg_color));
                                                            avaialable12TVID.setText(getResources().getString(R.string.un_available_slot));
                                                        }
                                                        avaialable12TVID.setText(getResources().getString(R.string.available_slot));
                                                    }
                                                    FourthRowThirdColumnLLID.setVisibility(View.VISIBLE);

                                                    FourthRowThirdColumnLLID.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            if (!slotdisable) {
                                                                if (courtEnable != 0) {
                                                                    slotId = slotId1;
                                                                    if (rowfourCourtsLLID.getVisibility() == View.GONE) {
                                                                        int kk = (Integer) v.getTag();
                                                                        courtDetailsDataLocal1 = _singleAvailbleListData.get(kk).get_courtDetails();

                                                                        if (courtDetailsDataLocal1.size() > 0) {
                                                                            rowOneCourtsLLID.setVisibility(View.GONE);
                                                                            rowtwoCourtsLLID.setVisibility(View.GONE);
                                                                            rowthreeCourtsLLID.setVisibility(View.GONE);
                                                                            twelveSlotSelectDividerShowView();
                                                                            rowfourCourtsLLID.setVisibility(View.VISIBLE);
                                                                            gridrow4.setVisibility(View.VISIBLE);
                                                                            rowfiveCourtsLLID.setVisibility(View.GONE);
                                                                            rowsixCourtsLLID.setVisibility(View.GONE);
                                                                            rowsevenCourtsLLID.setVisibility(View.GONE);
                                                                            roweigthCourtsLLID.setVisibility(View.GONE);

                                                                            selectedStrings = new ArrayList<>();
                                                                            adapter1 = new GridViewAdapter(courtDetailsDataLocal1, AvailableListFragment.this);
                                                                            gridrow4.setAdapter(adapter1);
                                                                        } else {
                                                                            Toast.makeText(AvailableListFragment.this, "No Courts avaialable!", Toast.LENGTH_SHORT).show();
                                                                        }

                                                                    } else {
                                                                        SlotSelectDividerShowAllWiseGoneView();
                                                                        FourthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);
                                                                        rowfourCourtsLLID.setVisibility(View.GONE);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    });
                                                }

                                                if (i == 12) {
                                                    FifthRowFirstColumnLLID.setTag(i); // Based on this tag we will retrive courts.......
                                                    slottime13TVID.setText("" + _singleAvailbleListData.get(i).get_slotName());
                                                    final String slotId1 = _singleAvailbleListData.get(i).get_slotId();     //Individual Slot id
                                                    final int courtEnable = _singleAvailbleListData.get(i).get_availbleCourts();
                                                    final boolean slotdisable = _singleAvailbleListData.get(i).get_disbled();
                                                    totalCourts13TVID.setText("" + "/" +_singleAvailbleListData.get(i).get_totalCourts()); //based on that show aval or un avail
                                                    if (slotdisable){
                                                        slottime13TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        totalCourts13TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        availableCourts13TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        avaialable13TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                    }
                                                    else {
                                                        slottime13TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        totalCourts13TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        availableCourts13TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        avaialable13TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                    }
                                                    availableCourts13TVID.setText("" + _singleAvailbleListData.get(i).get_availbleCourts());

                                                    if (_singleAvailbleListData.get(i).get_disbled()) {
                                                        avaialable13TVID.setText(getResources().getString(R.string.slot_un_available_slot));
                                                        FifthRowFirstColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_unavaile_bg_color));
                                                    } else {
                                                        if (_singleAvailbleListData.get(i).get_availbleCourts() == 0) {
                                                            FifthRowFirstColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_unavaile_bg_color));
                                                            avaialable13TVID.setText(getResources().getString(R.string.un_available_slot));
                                                        }
                                                        avaialable13TVID.setText(getResources().getString(R.string.available_slot));
                                                    }
                                                    FifthRowFirstColumnLLID.setVisibility(View.VISIBLE);

                                                    FifthRowFirstColumnLLID.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            if (!slotdisable) {
                                                                if (courtEnable != 0) {
                                                                    slotId = slotId1;

                                                                    if (rowfiveCourtsLLID.getVisibility() == View.GONE) {
                                                                        int kk = (Integer) v.getTag();
                                                                        courtDetailsDataLocal1 = _singleAvailbleListData.get(kk).get_courtDetails();

                                                                        if (courtDetailsDataLocal1.size() > 0) {
                                                                            rowOneCourtsLLID.setVisibility(View.GONE);
                                                                            rowtwoCourtsLLID.setVisibility(View.GONE);
                                                                            rowthreeCourtsLLID.setVisibility(View.GONE);
                                                                            rowfourCourtsLLID.setVisibility(View.GONE);
                                                                            rowfiveCourtsLLID.setVisibility(View.VISIBLE);
                                                                            thirteenSlotSelectDividerShowView();
                                                                            gridrow5.setVisibility(View.VISIBLE);
                                                                            rowsixCourtsLLID.setVisibility(View.GONE);
                                                                            rowsevenCourtsLLID.setVisibility(View.GONE);
                                                                            roweigthCourtsLLID.setVisibility(View.GONE);

                                                                            selectedStrings = new ArrayList<>();
                                                                            adapter1 = new GridViewAdapter(courtDetailsDataLocal1, AvailableListFragment.this);
                                                                            gridrow5.setAdapter(adapter1);
                                                                        } else {
                                                                            Toast.makeText(AvailableListFragment.this, "No Courts avaialable!", Toast.LENGTH_SHORT).show();
                                                                        }

                                                                    } else {
                                                                        SlotSelectDividerShowAllWiseGoneView();
                                                                        FifthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
                                                                        rowfiveCourtsLLID.setVisibility(View.GONE);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    });
                                                }

                                                if (i == 13) {
                                                    FifthRowSecondColumnLLID.setTag(i); // Based on this tag we will retrive courts.......
                                                    slottime14TVID.setText("" + _singleAvailbleListData.get(i).get_slotName());
                                                    final String slotId1 = _singleAvailbleListData.get(i).get_slotId();     //Individual Slot id
                                                    final int courtEnable = _singleAvailbleListData.get(i).get_availbleCourts();
                                                    final boolean slotdisable = _singleAvailbleListData.get(i).get_disbled();

                                                    totalCourts14TVID.setText("" + "/" +_singleAvailbleListData.get(i).get_totalCourts()); //based on that show aval or un avail
                                                    if (slotdisable){
                                                        slottime14TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        totalCourts14TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        availableCourts14TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        avaialable14TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                    }
                                                    else {
                                                        slottime14TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        totalCourts14TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        availableCourts14TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        avaialable14TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                    }
                                                    availableCourts14TVID.setText("" + _singleAvailbleListData.get(i).get_availbleCourts());

                                                    if (_singleAvailbleListData.get(i).get_disbled()) {
                                                        avaialable14TVID.setText(getResources().getString(R.string.slot_un_available_slot));
                                                        FifthRowSecondColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_unavaile_bg_color));
                                                    } else {
                                                        if (_singleAvailbleListData.get(i).get_availbleCourts() == 0) {
                                                            FifthRowSecondColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_unavaile_bg_color));
                                                            avaialable14TVID.setText(getResources().getString(R.string.un_available_slot));
                                                        }
                                                        avaialable14TVID.setText(getResources().getString(R.string.available_slot));
                                                    }
                                                    FifthRowSecondColumnLLID.setVisibility(View.VISIBLE);

                                                    FifthRowSecondColumnLLID.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            if (!slotdisable) {
                                                                if (courtEnable != 0) {
                                                                    slotId = slotId1;
                                                                    if (rowfiveCourtsLLID.getVisibility() == View.GONE) {
                                                                        int kk = (Integer) v.getTag();
                                                                        courtDetailsDataLocal1 = _singleAvailbleListData.get(kk).get_courtDetails();

                                                                        if (courtDetailsDataLocal1.size() > 0) {
                                                                            rowOneCourtsLLID.setVisibility(View.GONE);
                                                                            rowtwoCourtsLLID.setVisibility(View.GONE);
                                                                            rowthreeCourtsLLID.setVisibility(View.GONE);
                                                                            rowfourCourtsLLID.setVisibility(View.GONE);
                                                                            rowfiveCourtsLLID.setVisibility(View.VISIBLE);
                                                                            fountainSlotSelectDividerShowView();
                                                                            gridrow5.setVisibility(View.VISIBLE);
                                                                            rowsixCourtsLLID.setVisibility(View.GONE);
                                                                            rowsevenCourtsLLID.setVisibility(View.GONE);
                                                                            roweigthCourtsLLID.setVisibility(View.GONE);

                                                                            selectedStrings = new ArrayList<>();
                                                                            adapter1 = new GridViewAdapter(courtDetailsDataLocal1, AvailableListFragment.this);
                                                                            gridrow5.setAdapter(adapter1);
                                                                        } else {
                                                                            Toast.makeText(AvailableListFragment.this, "No Courts avaialable!", Toast.LENGTH_SHORT).show();
                                                                        }

                                                                    } else {
                                                                        SlotSelectDividerShowAllWiseGoneView();
                                                                        FifthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
                                                                        rowfiveCourtsLLID.setVisibility(View.GONE);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    });
                                                }

                                                if (i == 14) {
                                                    FifthRowThirdColumnLLID.setTag(i); // Based on this tag we will retrive courts.......
                                                    slottime15TVID.setText("" + _singleAvailbleListData.get(i).get_slotName());
                                                    final String slotId1 = _singleAvailbleListData.get(i).get_slotId();     //Individual Slot id
                                                    final int courtEnable = _singleAvailbleListData.get(i).get_availbleCourts();
                                                    final boolean slotdisable = _singleAvailbleListData.get(i).get_disbled();
                                                    totalCourts15TVID.setText("" + "/" +_singleAvailbleListData.get(i).get_totalCourts()); //based on that show aval or un avail
                                                    if (slotdisable){
                                                        slottime15TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        totalCourts15TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        availableCourts15TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        avaialable15TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                    }
                                                    else {
                                                        slottime15TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        totalCourts15TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        availableCourts15TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        avaialable15TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                    }

                                                    availableCourts15TVID.setText("" + _singleAvailbleListData.get(i).get_availbleCourts());

                                                    if (_singleAvailbleListData.get(i).get_disbled()) {
                                                        avaialable15TVID.setText(getResources().getString(R.string.slot_un_available_slot));
                                                        FifthRowThirdColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_unavaile_bg_color));
                                                    } else {
                                                        if (_singleAvailbleListData.get(i).get_availbleCourts() == 0) {
                                                            FifthRowThirdColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_unavaile_bg_color));
                                                            avaialable15TVID.setText(getResources().getString(R.string.un_available_slot));
                                                        }
                                                        avaialable15TVID.setText(getResources().getString(R.string.available_slot));
                                                    }

                                                    FifthRowThirdColumnLLID.setVisibility(View.VISIBLE);

                                                    FifthRowThirdColumnLLID.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            if (!slotdisable) {
                                                                if (courtEnable != 0) {
                                                                    slotId = slotId1;
                                                                    if (rowfiveCourtsLLID.getVisibility() == View.GONE) {
                                                                        int kk = (Integer) v.getTag();
                                                                        courtDetailsDataLocal1 = _singleAvailbleListData.get(kk).get_courtDetails();

                                                                        if (courtDetailsDataLocal1.size() > 0) {
                                                                            rowOneCourtsLLID.setVisibility(View.GONE);
                                                                            rowtwoCourtsLLID.setVisibility(View.GONE);
                                                                            rowthreeCourtsLLID.setVisibility(View.GONE);
                                                                            rowfourCourtsLLID.setVisibility(View.GONE);
                                                                            fifteenSlotSelectDividerShowView();
                                                                            rowfiveCourtsLLID.setVisibility(View.VISIBLE);
                                                                            gridrow5.setVisibility(View.VISIBLE);
                                                                            rowsixCourtsLLID.setVisibility(View.GONE);
                                                                            rowsevenCourtsLLID.setVisibility(View.GONE);
                                                                            roweigthCourtsLLID.setVisibility(View.GONE);

                                                                            selectedStrings = new ArrayList<>();
                                                                            adapter1 = new GridViewAdapter(courtDetailsDataLocal1, AvailableListFragment.this);
                                                                            gridrow5.setAdapter(adapter1);
                                                                        } else {
                                                                            Toast.makeText(AvailableListFragment.this, "No Courts avaialable!", Toast.LENGTH_SHORT).show();
                                                                        }

                                                                    } else {
                                                                        SlotSelectDividerShowAllWiseGoneView();
                                                                        FifthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);
                                                                        rowfiveCourtsLLID.setVisibility(View.GONE);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    });
                                                }

                                                if (i == 15) {
                                                    SixthRowFirstColumnLLID.setTag(i); // Based on this tag we will retrive courts.......
                                                    slottime16TVID.setText("" + _singleAvailbleListData.get(i).get_slotName());
                                                    final String slotId1 = _singleAvailbleListData.get(i).get_slotId();     //Individual Slot id
                                                    final int courtEnable = _singleAvailbleListData.get(i).get_availbleCourts();
                                                    final boolean slotdisable = _singleAvailbleListData.get(i).get_disbled();
                                                    totalCourts16TVID.setText("" + "/" +_singleAvailbleListData.get(i).get_totalCourts()); //based on that show aval or un avail
                                                    if (slotdisable){
                                                        slottime16TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        totalCourts16TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        availableCourts16TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        avaialable16TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                    }
                                                    else {
                                                        slottime16TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        totalCourts16TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        availableCourts16TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        avaialable16TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                    }

                                                    availableCourts16TVID.setText("" + _singleAvailbleListData.get(i).get_availbleCourts());

                                                    if (_singleAvailbleListData.get(i).get_disbled()) {
                                                        avaialable16TVID.setText(getResources().getString(R.string.slot_un_available_slot));
                                                        SixthRowFirstColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_unavaile_bg_color));
                                                    } else {
                                                        if (_singleAvailbleListData.get(i).get_availbleCourts() == 0) {
                                                            SixthRowFirstColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_unavaile_bg_color));
                                                            avaialable16TVID.setText(getResources().getString(R.string.un_available_slot));
                                                        }
                                                        avaialable16TVID.setText(getResources().getString(R.string.available_slot));
                                                    }
                                                    SixthRowFirstColumnLLID.setVisibility(View.VISIBLE);

                                                    SixthRowFirstColumnLLID.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            if (!slotdisable) {
                                                                if (courtEnable != 0) {
                                                                    slotId = slotId1;
                                                                    if (rowsixCourtsLLID.getVisibility() == View.GONE) {
                                                                        int kk = (Integer) v.getTag();
                                                                        courtDetailsDataLocal1 = _singleAvailbleListData.get(kk).get_courtDetails();

                                                                        if (courtDetailsDataLocal1.size() > 0) {
                                                                            rowOneCourtsLLID.setVisibility(View.GONE);
                                                                            rowtwoCourtsLLID.setVisibility(View.GONE);
                                                                            rowthreeCourtsLLID.setVisibility(View.GONE);
                                                                            rowfourCourtsLLID.setVisibility(View.GONE);
                                                                            rowfiveCourtsLLID.setVisibility(View.GONE);
                                                                            sixteenSlotSelectDividerShowView();
                                                                            rowsixCourtsLLID.setVisibility(View.VISIBLE);
                                                                            gridrow6.setVisibility(View.VISIBLE);
                                                                            rowsevenCourtsLLID.setVisibility(View.GONE);
                                                                            roweigthCourtsLLID.setVisibility(View.GONE);

                                                                            selectedStrings = new ArrayList<>();
                                                                            adapter1 = new GridViewAdapter(courtDetailsDataLocal1, AvailableListFragment.this);
                                                                            gridrow6.setAdapter(adapter1);
                                                                        } else {
                                                                            Toast.makeText(AvailableListFragment.this, "No Courts avaialable!", Toast.LENGTH_SHORT).show();
                                                                        }

                                                                    } else {
                                                                        SlotSelectDividerShowAllWiseGoneView();
                                                                        SixthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
                                                                        rowsixCourtsLLID.setVisibility(View.GONE);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    });
                                                }

                                                if (i == 16) {
                                                    SixthRowSecondColumnLLID.setTag(i); // Based on this tag we will retrive courts.......
                                                    slottime17TVID.setText("" + _singleAvailbleListData.get(i).get_slotName());
                                                    final String slotId1 = _singleAvailbleListData.get(i).get_slotId();     //Individual Slot id
                                                    final int courtEnable = _singleAvailbleListData.get(i).get_availbleCourts();
                                                    final boolean slotdisable = _singleAvailbleListData.get(i).get_disbled();

                                                    totalCourts17TVID.setText("" + "/" +_singleAvailbleListData.get(i).get_totalCourts()); //based on that show aval or un avail
                                                    if (slotdisable){
                                                        slottime17TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        totalCourts17TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        availableCourts17TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        avaialable17TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                    }
                                                    else {
                                                        slottime17TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        totalCourts17TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        availableCourts17TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        avaialable17TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                    }
                                                    availableCourts17TVID.setText("" + _singleAvailbleListData.get(i).get_availbleCourts());

                                                    if (_singleAvailbleListData.get(i).get_disbled()) {
                                                        avaialable17TVID.setText(getResources().getString(R.string.slot_un_available_slot));
                                                        SixthRowSecondColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_unavaile_bg_color));
                                                    } else {
                                                        if (_singleAvailbleListData.get(i).get_availbleCourts() == 0) {
                                                            SixthRowSecondColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_unavaile_bg_color));
                                                            avaialable17TVID.setText(getResources().getString(R.string.un_available_slot));
                                                        }
                                                        avaialable17TVID.setText(getResources().getString(R.string.available_slot));
                                                    }
                                                    SixthRowSecondColumnLLID.setVisibility(View.VISIBLE);

                                                    SixthRowSecondColumnLLID.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            if (!slotdisable) {
                                                                if (courtEnable != 0) {
                                                                    slotId = slotId1;
                                                                    if (rowsixCourtsLLID.getVisibility() == View.GONE) {
                                                                        int kk = (Integer) v.getTag();
                                                                        courtDetailsDataLocal1 = _singleAvailbleListData.get(kk).get_courtDetails();

                                                                        if (courtDetailsDataLocal1.size() > 0) {
                                                                            rowOneCourtsLLID.setVisibility(View.GONE);
                                                                            rowtwoCourtsLLID.setVisibility(View.GONE);
                                                                            rowthreeCourtsLLID.setVisibility(View.GONE);
                                                                            rowfourCourtsLLID.setVisibility(View.GONE);
                                                                            rowfiveCourtsLLID.setVisibility(View.GONE);
                                                                            seventeenSlotSelectDividerShowView();
                                                                            rowsixCourtsLLID.setVisibility(View.VISIBLE);
                                                                            gridrow6.setVisibility(View.VISIBLE);
                                                                            rowsevenCourtsLLID.setVisibility(View.GONE);
                                                                            roweigthCourtsLLID.setVisibility(View.GONE);

                                                                            selectedStrings = new ArrayList<>();
                                                                            adapter1 = new GridViewAdapter(courtDetailsDataLocal1, AvailableListFragment.this);
                                                                            gridrow6.setAdapter(adapter1);
                                                                        } else {
                                                                            Toast.makeText(AvailableListFragment.this, "No Courts avaialable!", Toast.LENGTH_SHORT).show();
                                                                        }

                                                                    } else {
                                                                        SlotSelectDividerShowAllWiseGoneView();
                                                                        SixthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
                                                                        rowsixCourtsLLID.setVisibility(View.GONE);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    });
                                                }

                                                if (i == 17) {
                                                    SixthRowThirdColumnLLID.setTag(i); // Based on this tag we will retrive courts.......
                                                    slottime18TVID.setText("" + _singleAvailbleListData.get(i).get_slotName());
                                                    final String slotId1 = _singleAvailbleListData.get(i).get_slotId();     //Individual Slot id
                                                    final int courtEnable = _singleAvailbleListData.get(i).get_availbleCourts();
                                                    final boolean slotdisable = _singleAvailbleListData.get(i).get_disbled();
                                                    totalCourts18TVID.setText("" + "/" +_singleAvailbleListData.get(i).get_totalCourts()); //based on that show aval or un avail
                                                    if (slotdisable){
                                                        slottime18TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        totalCourts18TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        availableCourts18TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        avaialable18TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                    }
                                                    else {
                                                        slottime18TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        totalCourts18TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        availableCourts18TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        avaialable18TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                    }

                                                    availableCourts18TVID.setText("" + _singleAvailbleListData.get(i).get_availbleCourts());

                                                    if (_singleAvailbleListData.get(i).get_disbled()) {
                                                        avaialable18TVID.setText(getResources().getString(R.string.slot_un_available_slot));
                                                        SixthRowThirdColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_unavaile_bg_color));
                                                    } else {
                                                        if (_singleAvailbleListData.get(i).get_availbleCourts() == 0) {
                                                            SixthRowThirdColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_unavaile_bg_color));
                                                            avaialable18TVID.setText(getResources().getString(R.string.un_available_slot));
                                                        }
                                                        avaialable18TVID.setText(getResources().getString(R.string.available_slot));
                                                    }
                                                    SixthRowThirdColumnLLID.setVisibility(View.VISIBLE);

                                                    SixthRowThirdColumnLLID.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            if (!slotdisable) {
                                                                if (courtEnable != 0) {
                                                                    slotId = slotId1;
                                                                    if (rowsixCourtsLLID.getVisibility() == View.GONE) {
                                                                        int kk = (Integer) v.getTag();
                                                                        courtDetailsDataLocal1 = _singleAvailbleListData.get(kk).get_courtDetails();

                                                                        if (courtDetailsDataLocal1.size() > 0) {
                                                                            rowOneCourtsLLID.setVisibility(View.GONE);
                                                                            rowtwoCourtsLLID.setVisibility(View.GONE);
                                                                            rowthreeCourtsLLID.setVisibility(View.GONE);
                                                                            rowfourCourtsLLID.setVisibility(View.GONE);
                                                                            rowfiveCourtsLLID.setVisibility(View.GONE);
                                                                            eigtheenSlotSelectDividerShowView();
                                                                            rowsixCourtsLLID.setVisibility(View.VISIBLE);
                                                                            gridrow6.setVisibility(View.VISIBLE);
                                                                            rowsevenCourtsLLID.setVisibility(View.GONE);
                                                                            roweigthCourtsLLID.setVisibility(View.GONE);

                                                                            selectedStrings = new ArrayList<>();
                                                                            adapter1 = new GridViewAdapter(courtDetailsDataLocal1, AvailableListFragment.this);
                                                                            gridrow6.setAdapter(adapter1);
                                                                        } else {
                                                                            Toast.makeText(AvailableListFragment.this, "No Courts avaialable!", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    } else {
                                                                        SlotSelectDividerShowAllWiseGoneView();
                                                                        SixthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);
                                                                        rowsixCourtsLLID.setVisibility(View.GONE);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    });
                                                }

                                                if (i == 18) {
                                                    SeventhRowFirstColumnLLID.setTag(i); // Based on this tag we will retrive courts.......
                                                    slottime19TVID.setText("" + _singleAvailbleListData.get(i).get_slotName());
                                                    final String slotId1 = _singleAvailbleListData.get(i).get_slotId();     //Individual Slot id
                                                    final int courtEnable = _singleAvailbleListData.get(i).get_availbleCourts();
                                                    final boolean slotdisable = _singleAvailbleListData.get(i).get_disbled();
                                                    totalCourts19TVID.setText("" + "/" +_singleAvailbleListData.get(i).get_totalCourts()); //based on that show aval or un avail
                                                    if (slotdisable){
                                                        slottime19TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        totalCourts19TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        availableCourts19TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        avaialable19TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                    }
                                                    else {
                                                        slottime19TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        totalCourts19TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        availableCourts19TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        avaialable19TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                    }
                                                    availableCourts19TVID.setText("" + _singleAvailbleListData.get(i).get_availbleCourts());

                                                    if (_singleAvailbleListData.get(i).get_disbled()) {
                                                        avaialable19TVID.setText(getResources().getString(R.string.slot_un_available_slot));
                                                        SeventhRowFirstColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_unavaile_bg_color));
                                                    } else {
                                                        if (_singleAvailbleListData.get(i).get_availbleCourts() == 0) {
                                                            SeventhRowFirstColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_unavaile_bg_color));
                                                            avaialable19TVID.setText(getResources().getString(R.string.un_available_slot));
                                                        }
                                                        avaialable19TVID.setText(getResources().getString(R.string.available_slot));
                                                    }
                                                    SeventhRowFirstColumnLLID.setVisibility(View.VISIBLE);

                                                    SeventhRowFirstColumnLLID.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            if (!slotdisable) {
                                                                if (courtEnable != 0) {
                                                                    slotId = slotId1;
                                                                    if (rowsevenCourtsLLID.getVisibility() == View.GONE) {
                                                                        int kk = (Integer) v.getTag();
                                                                        courtDetailsDataLocal1 = _singleAvailbleListData.get(kk).get_courtDetails();

                                                                        if (courtDetailsDataLocal1.size() > 0) {
                                                                            rowOneCourtsLLID.setVisibility(View.GONE);
                                                                            rowtwoCourtsLLID.setVisibility(View.GONE);
                                                                            rowthreeCourtsLLID.setVisibility(View.GONE);
                                                                            rowfourCourtsLLID.setVisibility(View.GONE);
                                                                            rowfiveCourtsLLID.setVisibility(View.GONE);
                                                                            rowsixCourtsLLID.setVisibility(View.GONE);
                                                                            nineteenSlotSelectDividerShowView();
                                                                            rowsevenCourtsLLID.setVisibility(View.VISIBLE);
                                                                            gridrow7.setVisibility(View.VISIBLE);
                                                                            roweigthCourtsLLID.setVisibility(View.GONE);

                                                                            selectedStrings = new ArrayList<>();
                                                                            adapter1 = new GridViewAdapter(courtDetailsDataLocal1, AvailableListFragment.this);
                                                                            gridrow7.setAdapter(adapter1);
                                                                        } else {
                                                                            Toast.makeText(AvailableListFragment.this, "No Courts avaialable!", Toast.LENGTH_SHORT).show();
                                                                        }

                                                                    } else {
                                                                        SlotSelectDividerShowAllWiseGoneView();
                                                                        SeventhRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
                                                                        rowsevenCourtsLLID.setVisibility(View.GONE);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    });
                                                }

                                                if (i == 19) {
                                                    SeventhRowSecondColumnLLID.setTag(i); // Based on this tag we will retrive courts.......
                                                    slottime20TVID.setText("" + _singleAvailbleListData.get(i).get_slotName());
                                                    final String slotId1 = _singleAvailbleListData.get(i).get_slotId();     //Individual Slot id
                                                    final int courtEnable = _singleAvailbleListData.get(i).get_availbleCourts();
                                                    final boolean slotdisable = _singleAvailbleListData.get(i).get_disbled();

                                                    totalCourts20TVID.setText("" + "/" +_singleAvailbleListData.get(i).get_totalCourts()); //based on that show aval or un avail
                                                    if (slotdisable){
                                                        slottime20TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        totalCourts20TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        availableCourts20TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        avaialable20TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                    }
                                                    else {
                                                        slottime20TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        totalCourts20TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        availableCourts20TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        avaialable20TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                    }

                                                    availableCourts20TVID.setText("" + _singleAvailbleListData.get(i).get_availbleCourts());

                                                    if (_singleAvailbleListData.get(i).get_disbled()) {
                                                        avaialable20TVID.setText(getResources().getString(R.string.slot_un_available_slot));
                                                        SeventhRowSecondColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_unavaile_bg_color));
                                                    } else {
                                                        if (_singleAvailbleListData.get(i).get_availbleCourts() == 0) {
                                                            SeventhRowSecondColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_unavaile_bg_color));
                                                            avaialable20TVID.setText(getResources().getString(R.string.un_available_slot));
                                                        }
                                                        avaialable20TVID.setText(getResources().getString(R.string.available_slot));
                                                    }
                                                    SeventhRowSecondColumnLLID.setVisibility(View.VISIBLE);

                                                    SeventhRowSecondColumnLLID.setOnClickListener(new View.OnClickListener() {

                                                        @Override
                                                        public void onClick(View v) {
                                                            if (!slotdisable) {
                                                                if (courtEnable != 0) {
                                                                    slotId = slotId1;
                                                                    if (rowsevenCourtsLLID.getVisibility() == View.GONE) {
                                                                        int kk = (Integer) v.getTag();
                                                                        courtDetailsDataLocal1 = _singleAvailbleListData.get(kk).get_courtDetails();

                                                                        if (courtDetailsDataLocal1.size() > 0) {
                                                                            rowOneCourtsLLID.setVisibility(View.GONE);
                                                                            rowtwoCourtsLLID.setVisibility(View.GONE);
                                                                            rowthreeCourtsLLID.setVisibility(View.GONE);
                                                                            rowfourCourtsLLID.setVisibility(View.GONE);
                                                                            rowfiveCourtsLLID.setVisibility(View.GONE);
                                                                            rowsixCourtsLLID.setVisibility(View.GONE);
                                                                            twentySlotSelectDividerShowView();
                                                                            rowsevenCourtsLLID.setVisibility(View.VISIBLE);
                                                                            gridrow7.setVisibility(View.VISIBLE);
                                                                            roweigthCourtsLLID.setVisibility(View.GONE);

                                                                            selectedStrings = new ArrayList<>();
                                                                            adapter1 = new GridViewAdapter(courtDetailsDataLocal1, AvailableListFragment.this);
                                                                            gridrow7.setAdapter(adapter1);
                                                                        } else {
                                                                            Toast.makeText(AvailableListFragment.this, "No Courts avaialable!", Toast.LENGTH_SHORT).show();
                                                                        }

                                                                    } else {
                                                                        SlotSelectDividerShowAllWiseGoneView();
                                                                        SeventhRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
                                                                        rowsevenCourtsLLID.setVisibility(View.GONE);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    });
                                                }

                                                if (i == 20) {
                                                    SeventhRowThirdColumnLLID.setTag(i); // Based on this tag we will retrive courts.......
                                                    slottime21TVID.setText("" + _singleAvailbleListData.get(i).get_slotName());
                                                    final String slotId1 = _singleAvailbleListData.get(i).get_slotId();     //Individual Slot id
                                                    final int courtEnable = _singleAvailbleListData.get(i).get_availbleCourts();
                                                    final boolean slotdisable = _singleAvailbleListData.get(i).get_disbled();
                                                    totalCourts21TVID.setText("" + "/" +_singleAvailbleListData.get(i).get_totalCourts()); //based on that show aval or un avail
                                                    if (slotdisable){
                                                        slottime21TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        totalCourts21TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        availableCourts21TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        avaialable21TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                    }
                                                    else {
                                                        slottime21TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        totalCourts21TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        availableCourts21TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        avaialable21TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                    }
                                                    availableCourts21TVID.setText("" + _singleAvailbleListData.get(i).get_availbleCourts());

                                                    if (_singleAvailbleListData.get(i).get_disbled()) {
                                                        avaialable21TVID.setText(getResources().getString(R.string.slot_un_available_slot));
                                                        SeventhRowThirdColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_unavaile_bg_color));
                                                    } else {
                                                        if (_singleAvailbleListData.get(i).get_availbleCourts() == 0) {
                                                            SeventhRowThirdColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_unavaile_bg_color));
                                                            avaialable21TVID.setText(getResources().getString(R.string.un_available_slot));
                                                        }
                                                        avaialable21TVID.setText(getResources().getString(R.string.available_slot));
                                                    }
                                                    SeventhRowThirdColumnLLID.setVisibility(View.VISIBLE);

                                                    SeventhRowThirdColumnLLID.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            if (!slotdisable) {
                                                                if (courtEnable != 0) {
                                                                    slotId = slotId1;
                                                                    if (rowsevenCourtsLLID.getVisibility() == View.GONE) {
                                                                        int kk = (Integer) v.getTag();
                                                                        courtDetailsDataLocal1 = _singleAvailbleListData.get(kk).get_courtDetails();

                                                                        if (courtDetailsDataLocal1.size() > 0) {
                                                                            rowOneCourtsLLID.setVisibility(View.GONE);
                                                                            rowtwoCourtsLLID.setVisibility(View.GONE);
                                                                            rowthreeCourtsLLID.setVisibility(View.GONE);
                                                                            rowfourCourtsLLID.setVisibility(View.GONE);
                                                                            rowfiveCourtsLLID.setVisibility(View.GONE);
                                                                            rowsixCourtsLLID.setVisibility(View.GONE);
                                                                            twentyOneSlotSelectDividerShowView();
                                                                            rowsevenCourtsLLID.setVisibility(View.VISIBLE);
                                                                            gridrow7.setVisibility(View.VISIBLE);
                                                                            roweigthCourtsLLID.setVisibility(View.GONE);

                                                                            selectedStrings = new ArrayList<>();
                                                                            adapter1 = new GridViewAdapter(courtDetailsDataLocal1, AvailableListFragment.this);
                                                                            gridrow7.setAdapter(adapter1);
                                                                        } else {
                                                                            Toast.makeText(AvailableListFragment.this, "No Courts avaialable!", Toast.LENGTH_SHORT).show();
                                                                        }

                                                                    } else {
                                                                        SlotSelectDividerShowAllWiseGoneView();
                                                                        SeventhRowThirdColumn_SELECT_VID.setVisibility(View.GONE);
                                                                        rowsevenCourtsLLID.setVisibility(View.GONE);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    });
                                                }


                                                if (i == 21) {
                                                    EigthRowFirstColumnLLID.setTag(i); // Based on this tag we will retrive courts.......
                                                    slottime22TVID.setText("" + _singleAvailbleListData.get(i).get_slotName());
                                                    final String slotId1 = _singleAvailbleListData.get(i).get_slotId();     //Individual Slot id
                                                    final int courtEnable = _singleAvailbleListData.get(i).get_availbleCourts();
                                                    final boolean slotdisable = _singleAvailbleListData.get(i).get_disbled();

                                                    totalCourts22TVID.setText("" + "/" +_singleAvailbleListData.get(i).get_totalCourts()); //based on that show aval or un avail
                                                    if (slotdisable){
                                                        slottime22TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        totalCourts22TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        availableCourts22TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        avaialable22TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                    }
                                                    else {
                                                        slottime22TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        totalCourts22TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        availableCourts22TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        avaialable22TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                    }
                                                    availableCourts22TVID.setText("" + _singleAvailbleListData.get(i).get_availbleCourts());

                                                    if (_singleAvailbleListData.get(i).get_disbled()) {
                                                        avaialable22TVID.setText(getResources().getString(R.string.slot_un_available_slot));
                                                        EigthRowFirstColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_unavaile_bg_color));
                                                    } else {
                                                        if (_singleAvailbleListData.get(i).get_availbleCourts() == 0) {
                                                            EigthRowFirstColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_unavaile_bg_color));
                                                            avaialable22TVID.setText(getResources().getString(R.string.un_available_slot));
                                                        }
                                                        avaialable22TVID.setText(getResources().getString(R.string.available_slot));
                                                    }
                                                    EigthRowFirstColumnLLID.setVisibility(View.VISIBLE);

                                                    EigthRowFirstColumnLLID.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            if (!slotdisable) {
                                                                if (courtEnable != 0) {
                                                                    slotId = slotId1;
                                                                    if (roweigthCourtsLLID.getVisibility() == View.GONE) {
                                                                        int kk = (Integer) v.getTag();
                                                                        courtDetailsDataLocal1 = _singleAvailbleListData.get(kk).get_courtDetails();

                                                                        if (courtDetailsDataLocal1.size() > 0) {
                                                                            rowOneCourtsLLID.setVisibility(View.GONE);
                                                                            rowtwoCourtsLLID.setVisibility(View.GONE);
                                                                            rowthreeCourtsLLID.setVisibility(View.GONE);
                                                                            rowfourCourtsLLID.setVisibility(View.GONE);
                                                                            rowfiveCourtsLLID.setVisibility(View.GONE);
                                                                            rowsixCourtsLLID.setVisibility(View.GONE);
                                                                            rowsevenCourtsLLID.setVisibility(View.GONE);
                                                                            twentytwoSlotSelectDividerShowView();
                                                                            roweigthCourtsLLID.setVisibility(View.VISIBLE);
                                                                            gridrow8.setVisibility(View.VISIBLE);

                                                                            selectedStrings = new ArrayList<>();
                                                                            adapter1 = new GridViewAdapter(courtDetailsDataLocal1, AvailableListFragment.this);
                                                                            gridrow8.setAdapter(adapter1);
                                                                        } else {
                                                                            Toast.makeText(AvailableListFragment.this, "No Courts avaialable!", Toast.LENGTH_SHORT).show();
                                                                        }

                                                                    } else {
                                                                        SlotSelectDividerShowAllWiseGoneView();
                                                                        EigthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
                                                                        roweigthCourtsLLID.setVisibility(View.GONE);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    });
                                                }


                                                if (i == 22) {
                                                    EigthRowSecondColumnLLID.setTag(i); // Based on this tag we will retrive courts.......
                                                    slottime23TVID.setText("" + _singleAvailbleListData.get(i).get_slotName());
                                                    final String slotId1 = _singleAvailbleListData.get(i).get_slotId();     //Individual Slot id
                                                    final int courtEnable = _singleAvailbleListData.get(i).get_availbleCourts();
                                                    final boolean slotdisable = _singleAvailbleListData.get(i).get_disbled();
                                                    totalCourts23TVID.setText("" + "/" +_singleAvailbleListData.get(i).get_totalCourts()); //based on that show aval or un avail
                                                    if (slotdisable){
                                                        slottime23TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        totalCourts23TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        availableCourts23TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        avaialable23TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                    }
                                                    else {
                                                        slottime23TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        totalCourts23TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        availableCourts23TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        avaialable23TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                    }
                                                    availableCourts23TVID.setText("" + _singleAvailbleListData.get(i).get_availbleCourts());

                                                    if (_singleAvailbleListData.get(i).get_disbled()) {
                                                        avaialable23TVID.setText(getResources().getString(R.string.slot_un_available_slot));
                                                        EigthRowSecondColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_unavaile_bg_color));
                                                    } else {
                                                        if (_singleAvailbleListData.get(i).get_availbleCourts() == 0) {
                                                            EigthRowSecondColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_unavaile_bg_color));
                                                            avaialable23TVID.setText(getResources().getString(R.string.un_available_slot));
                                                        }
                                                        avaialable23TVID.setText(getResources().getString(R.string.available_slot));
                                                    }
                                                    EigthRowSecondColumnLLID.setVisibility(View.VISIBLE);

                                                    EigthRowSecondColumnLLID.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            if (!slotdisable) {
                                                                if (courtEnable != 0) {
                                                                    slotId = slotId1;
                                                                    if (roweigthCourtsLLID.getVisibility() == View.GONE) {
                                                                        int kk = (Integer) v.getTag();
                                                                        courtDetailsDataLocal1 = _singleAvailbleListData.get(kk).get_courtDetails();
                                                                        //slotId = _singleAvailbleListData.get(kk).get_slotId();

                                                                        if (courtDetailsDataLocal1.size() > 0) {
                                                                            rowOneCourtsLLID.setVisibility(View.GONE);
                                                                            rowtwoCourtsLLID.setVisibility(View.GONE);
                                                                            rowthreeCourtsLLID.setVisibility(View.GONE);
                                                                            rowfourCourtsLLID.setVisibility(View.GONE);
                                                                            rowfiveCourtsLLID.setVisibility(View.GONE);
                                                                            rowsixCourtsLLID.setVisibility(View.GONE);
                                                                            rowsevenCourtsLLID.setVisibility(View.GONE);
                                                                            twentythreeSlotSelectDividerShowView();
                                                                            roweigthCourtsLLID.setVisibility(View.VISIBLE);
                                                                            gridrow8.setVisibility(View.VISIBLE);

                                                                            selectedStrings = new ArrayList<>();
                                                                            adapter1 = new GridViewAdapter(courtDetailsDataLocal1, AvailableListFragment.this);
                                                                            gridrow8.setAdapter(adapter1);
                                                                        } else {
                                                                            Toast.makeText(AvailableListFragment.this, "No Courts avaialable!", Toast.LENGTH_SHORT).show();
                                                                        }

                                                                    } else {
                                                                        SlotSelectDividerShowAllWiseGoneView();
                                                                        EigthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
                                                                        roweigthCourtsLLID.setVisibility(View.GONE);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    });
                                                }

                                                if (i == 23) {
                                                    EigthRowThirdColumnLLID.setTag(i); // Based on this tag we will retrive courts.......
                                                    slottime24TVID.setText("" + _singleAvailbleListData.get(i).get_slotName());
                                                    final String slotId1 = _singleAvailbleListData.get(i).get_slotId();     //Individual Slot id
                                                    final int courtEnable = _singleAvailbleListData.get(i).get_availbleCourts();
                                                    final boolean slotdisable = _singleAvailbleListData.get(i).get_disbled();
                                                    totalCourts24TVID.setText("" + "/" +_singleAvailbleListData.get(i).get_totalCourts()); //based on that show aval or un avail
                                                    if (slotdisable){
                                                        slottime24TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        totalCourts24TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        availableCourts24TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                        avaialable24TVID.setTextColor(getResources().getColor(R.color.slot_unavailble_text_color));
                                                    }
                                                    else {
                                                        slottime24TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        totalCourts24TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        availableCourts24TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                        avaialable24TVID.setTextColor(getResources().getColor(R.color.normal_text_color));
                                                    }
                                                    availableCourts24TVID.setText("" + _singleAvailbleListData.get(i).get_availbleCourts());

                                                    if (_singleAvailbleListData.get(i).get_disbled()) {
                                                        avaialable24TVID.setText(getResources().getString(R.string.slot_un_available_slot));
                                                        EigthRowThirdColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_unavaile_bg_color));
                                                    } else {
                                                        if (_singleAvailbleListData.get(i).get_availbleCourts() == 0) {
                                                            EigthRowThirdColumnLLID.setBackgroundColor(getResources().getColor(R.color.slot_unavaile_bg_color));
                                                            avaialable24TVID.setText(getResources().getString(R.string.un_available_slot));
                                                        }
                                                        avaialable24TVID.setText(getResources().getString(R.string.available_slot));
                                                    }
                                                    EigthRowThirdColumnLLID.setVisibility(View.VISIBLE);
                                                    EigthRowThirdColumnLLID.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            if (!slotdisable) {
                                                                if (courtEnable != 0) {
                                                                    slotId = slotId1;
                                                                    if (roweigthCourtsLLID.getVisibility() == View.GONE) {
                                                                        int kk = (Integer) v.getTag();
                                                                        courtDetailsDataLocal1 = _singleAvailbleListData.get(kk).get_courtDetails();

                                                                        if (courtDetailsDataLocal1.size() > 0) {
                                                                            rowOneCourtsLLID.setVisibility(View.GONE);
                                                                            rowtwoCourtsLLID.setVisibility(View.GONE);
                                                                            rowthreeCourtsLLID.setVisibility(View.GONE);
                                                                            rowfourCourtsLLID.setVisibility(View.GONE);
                                                                            rowfiveCourtsLLID.setVisibility(View.GONE);
                                                                            rowsixCourtsLLID.setVisibility(View.GONE);
                                                                            rowsevenCourtsLLID.setVisibility(View.GONE);
                                                                            twentyfourSlotSelectDividerShowView();
                                                                            roweigthCourtsLLID.setVisibility(View.VISIBLE);

                                                                            selectedStrings = new ArrayList<>();
                                                                            adapter1 = new GridViewAdapter(courtDetailsDataLocal1, AvailableListFragment.this);
                                                                            gridrow8.setAdapter(adapter1);
                                                                        } else {
                                                                            Toast.makeText(AvailableListFragment.this, "No Courts avaialable!", Toast.LENGTH_SHORT).show();
                                                                        }

                                                                    } else {
                                                                        EigthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);
                                                                        roweigthCourtsLLID.setVisibility(View.GONE);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    });
                                                }
                                            }

                                        } else {
                                            progressWheel_CENTER_SPORTS.setVisibility(View.GONE);
                                            noAvailblesTVID.setVisibility(View.VISIBLE);
                                        }
                                    }
                                }, 800);
                            } else {
                                progressWheel_CENTER_SPORTS.setVisibility(View.GONE);
                                noAvailblesTVID.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            progressWheel_CENTER_SPORTS.setVisibility(View.GONE);
                            noAvailblesTVID.setVisibility(View.GONE);
                            e.printStackTrace();
                            loadingSportsTroubleConnecting();
                        }
                    } catch (Exception e) {
                        progressWheel_CENTER_SPORTS.setVisibility(View.GONE);
                        noAvailblesTVID.setVisibility(View.GONE);
                        e.printStackTrace();
                        loadingSportsTroubleConnecting();
                    }
                } else {
                    noAvailblesTVID.setVisibility(View.VISIBLE);
                    progressWheel_CENTER_SPORTS.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
                try {
                    progressWheel_CENTER_SPORTS.setVisibility(View.GONE);
                    noAvailblesTVID.setVisibility(View.GONE);
                    if (content != null && content.contains("authfailed")) {
                        Intent authFailed = new Intent(getApplicationContext(), RegistrationActivity.class);
                        authFailed.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        authFailed.putExtra("EXIT", true);
                        authFailed.putExtra("EXIT_AUTH_FAILED", "authfailed");
                        startActivity(authFailed);
                        finish();
                    } else {
                        loadingSportsTroubleConnecting();
                    }
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
            }
        });
    }


    private void gridViewsVisible() {
        gridView.setVisibility(View.VISIBLE);
        gridrow2.setVisibility(View.VISIBLE);
        gridrow3.setVisibility(View.VISIBLE);
        gridrow4.setVisibility(View.VISIBLE);
        gridrow5.setVisibility(View.VISIBLE);
        gridrow6.setVisibility(View.VISIBLE);
        gridrow7.setVisibility(View.VISIBLE);
        gridrow8.setVisibility(View.VISIBLE);
    }

    protected void sportAvailbleRequest(String sportsUrl) {
        // Make RESTful Web Service call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("facilityId", SharedPreference.getPreferences(AvailableListFragment.this, "KEY_FACILITY_ID"));
        client.post(sportsUrl, params, new AsyncHttpResponseHandler() {
            //client.get(registrationUrl1, null, new AsyncHttpResponseHandler() {
            // When the response returned by REST has HTTP response code // '200'
            public void onSuccess(String response) {
                // Hide Progress Dialog
                try {
                    progressWheel_CENTER_SPORTS.setVisibility(View.VISIBLE);
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
                                if (progressWheel_CENTER_SPORTS != null) {
                                    progressWheel_CENTER_SPORTS.setVisibility(View.GONE);
                                }
                                if (activity != null) {
                                    sportsSPID.setAdapter(new ArrayAdapter<String>(AvailableListFragment.this, android.R.layout.simple_spinner_dropdown_item, sportTitle));
                                    //sportsSPID.setAdapter();
                                    sports_id = _sportavailableListData.get(sportsSPID.getSelectedItemPosition()).getSportsId();
                                    sport_name = _sportavailableListData.get(sportsSPID.getSelectedItemPosition()).getSportsTitle();
                                    SharedPreference.setPreference(getApplicationContext(), "SPORT_NAME", sport_name);
                                    SharedPreference.setPreference(getApplicationContext(), "SPORT_ID", sports_id);

                                    sendRequestToGetAvailbleList(AppConstants.AVAILABLE_LIST_URL);
                                } else {
                                    sportsSPID.setVisibility(View.GONE);
                                }
                            } else {
                                progressWheel_CENTER_SPORTS.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, SPLASH_TIME_OUT);
            }

            public void onFailure(int statusCode, Throwable error, String content) {
                try {
                    if (progressWheel_CENTER_SPORTS != null) {
                        progressWheel_CENTER_SPORTS.setVisibility(View.GONE);
                    }
                    if (content != null && content.contains("Invalid use of Null")) {
                        Utility.showCustomToast("Customer Not Found!", AvailableListFragment.this);
                    } else if (content != null && content.contains("Type mismatch: 'GetCustomer'")) {
                        Toast.makeText(getApplicationContext(), "Invalid Customer Id!", Toast.LENGTH_SHORT).show();
                    } else {
                        // When HTTP response code is '404'
                        if (statusCode == 404) {
                            Utility.showCustomToast("Requested resource not found", AvailableListFragment.this);
                        }
                        // When HTTP response code is '500'
                        else if (statusCode == 500) {
                            Utility.showCustomToast("Something went wrong at server end", AvailableListFragment.this);
                        }
                        //When HTTP response code other than 404, 500
                        else {
                            Utility.showCustomToast("Unexpected Error occcured! Please Try Again", AvailableListFragment.this);
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
                return new DatePickerDialog(AvailableListFragment.this, mDateSetListener, mYear, mMonth, mDay);
        }
        return null;

    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            if (year < mYear)
                view.updateDate(mYear, mMonth, mDay);

            if (monthOfYear < mMonth && year == mYear)
                view.updateDate(mYear, mMonth, mDay);

            if (dayOfMonth < mDay && year == mYear && monthOfYear == mMonth)
                view.updateDate(mYear, mMonth, mDay);

        }

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
            /*String dateInString = Integer.toString(mDay) + "-" + "0" + Integer.toString(monthOfYear + 1) + "-" + Integer.toString(mYear);
            date = sdf.parse(dateInString);
            dayOfTheWeek = (String) android.text.format.DateFormat.format("EEE", date);
            monthName = (String) android.text.format.DateFormat.format("MMM", date);
            Toast.makeText(AvailableListFragment.this, "" + monthName, Toast.LENGTH_SHORT).show();*/
            String dateInString = Integer.toString(mDay) + "-" + "0" + Integer.toString(monthOfYear + 1) + "-" + Integer.toString(mYear);
            date = sdf.parse(dateInString);
            daydate = (String) android.text.format.DateFormat.format("dd", date);
            dayOfTheWeek = (String) android.text.format.DateFormat.format("EEE", date);
            monthName = (String) android.text.format.DateFormat.format("MMM", date);
            monthNo = (String) android.text.format.DateFormat.format("MM", date);
            availableDateTVID.setText(new StringBuilder().append(dayOfTheWeek + "," + " ").append(mDay).append(" ").append(monthName).append(" ").append(mYear));
            //Toast.makeText(getApplicationContext(), "" + monthName, Toast.LENGTH_SHORT).show();
            datestr = mYear + "-" + monthNo + "-" + daydate;
            refreshLinearlayoutBackgrounds();
            SlotSelectDividerShowAllWiseGoneView();
            progressWheel_CENTER_SPORTS.setVisibility(View.VISIBLE);
            SharedPreference.setPreference(getApplicationContext(), "DATE", datestr);
            if (Utility.isOnline(getApplicationContext())) {
                completeslotsSVID.setVisibility(View.GONE);
                ifNetisAvilableAvailbleList();
            } else {
                loadingSportsTroubleConnecting();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (dayarrow.equals("SELECT_PREVIOUS_DAY")) {
            c.setTime(date);
            c.add(Calendar.DATE, -1);
            date = c.getTime();
            dayOfTheWeek = (String) android.text.format.DateFormat.format("EEE", date);
            monthName = (String) android.text.format.DateFormat.format("MMM", date);
            String monthNo = (String) android.text.format.DateFormat.format("MM", date);
            mMonth = Integer.parseInt(monthNo);
            mMonth = mMonth - 1;
            String dayDate = (String) android.text.format.DateFormat.format("dd", date);
            mDay = Integer.parseInt(dayDate);
            //mDay = mDay - 1;
            String year = (String) android.text.format.DateFormat.format("yyyy", date);
            mYear = Integer.parseInt(year);
            datestr = mYear + "-" + monthNo + "-" + daydate;
            refreshLinearlayoutBackgrounds();
            SlotSelectDividerShowAllWiseGoneView();
            progressWheel_CENTER_SPORTS.setVisibility(View.VISIBLE);
            SharedPreference.setPreference(getApplicationContext(), "DATE", datestr);
            if (Utility.isOnline(getApplicationContext())) {
                completeslotsSVID.setVisibility(View.GONE);
                ifNetisAvilableAvailbleList();
            } else {
                loadingSportsTroubleConnecting();
            }

            availableDateTVID.setText(new StringBuilder().append(dayOfTheWeek + "," + " ").append(dayDate).append(" ").append(monthName).append(" ").append(year));
        } else if (dayarrow.equals("SELECT_NEXT_DAY")) {
            c.setTime(date);
            c.add(Calendar.DATE, 1);
            date = c.getTime();
            dayOfTheWeek = (String) android.text.format.DateFormat.format("EEE", date);
            monthName = (String) android.text.format.DateFormat.format("MMM", date);
            String monthNo = (String) android.text.format.DateFormat.format("MM", date);
            mMonth = Integer.parseInt(monthNo);
            mMonth = mMonth - 1;
            String dayDate = (String) android.text.format.DateFormat.format("dd", date);
            mDay = Integer.parseInt(dayDate);
            //mDay = mDay - 1;
            String year = (String) android.text.format.DateFormat.format("yyyy", date);
            mYear = Integer.parseInt(year);
            datestr = mYear + "-" + monthNo + "-" + daydate;
            refreshLinearlayoutBackgrounds();
            SlotSelectDividerShowAllWiseGoneView();
            progressWheel_CENTER_SPORTS.setVisibility(View.VISIBLE);
            SharedPreference.setPreference(getApplicationContext(), "DATE", datestr);
            if (Utility.isOnline(getApplicationContext())) {
                completeslotsSVID.setVisibility(View.GONE);
                ifNetisAvilableAvailbleList();
            } else {
                loadingSportsTroubleConnecting();
            }
            availableDateTVID.setText(new StringBuilder().append(dayOfTheWeek + "," + " ").append(dayDate).append(" ").append(monthName).append(" ").append(year));
        } else {
            availableDateTVID.setText(new StringBuilder().append(dayOfTheWeek + "," + " ").append(mDay).append(" ").append(monthName).append(" ").append(mYear));
        }
        //availableDateTVID.setText(new StringBuilder().append(dayOfTheWeek + "," + " ").append(mDay).append(" ").append(monthName).append(" ").append(mYear));
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    private void firstSlotSelectDividerShowView() {
        FirstRowFirstColumn_SELECT_VID.setVisibility(View.VISIBLE);
        FirstRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FirstRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SecondRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SecondRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SecondRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        ThirdRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        ThirdRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        ThirdRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        FourthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FourthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FourthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);


        FifthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FifthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FifthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SixthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SixthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SixthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SeventhRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SeventhRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SeventhRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        EigthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        EigthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        EigthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);
    }

    private void secondSlotSelectDividerShowView() {
        FirstRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FirstRowSecondColumn_SELECT_VID.setVisibility(View.VISIBLE);
        FirstRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SecondRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SecondRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SecondRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        ThirdRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        ThirdRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        ThirdRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        FourthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FourthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FourthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);


        FifthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FifthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FifthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SixthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SixthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SixthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SeventhRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SeventhRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SeventhRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        EigthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        EigthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        EigthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);
    }

    private void thirdSlotSelectDividerShowView() {
        FirstRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FirstRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FirstRowThirdColumn_SELECT_VID.setVisibility(View.VISIBLE);

        SecondRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SecondRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SecondRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        ThirdRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        ThirdRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        ThirdRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        FourthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FourthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FourthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        FifthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FifthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FifthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SixthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SixthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SixthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SeventhRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SeventhRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SeventhRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        EigthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        EigthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        EigthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);
    }

    private void fourthSlotSelectDividerShowView() {
        FirstRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FirstRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FirstRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SecondRowFirstColumn_SELECT_VID.setVisibility(View.VISIBLE);
        SecondRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SecondRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        ThirdRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        ThirdRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        ThirdRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        FourthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FourthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FourthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);


        FifthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FifthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FifthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SixthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SixthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SixthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SeventhRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SeventhRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SeventhRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        EigthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        EigthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        EigthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);
    }

    private void fifthSlotSelectDividerShowView() {
        FirstRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FirstRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FirstRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SecondRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SecondRowSecondColumn_SELECT_VID.setVisibility(View.VISIBLE);
        SecondRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        ThirdRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        ThirdRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        ThirdRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        FourthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FourthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FourthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);


        FifthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FifthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FifthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SixthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SixthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SixthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SeventhRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SeventhRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SeventhRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        EigthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        EigthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        EigthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);
    }

    private void sixthSlotSelectDividerShowView() {
        FirstRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FirstRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FirstRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SecondRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SecondRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SecondRowThirdColumn_SELECT_VID.setVisibility(View.VISIBLE);

        ThirdRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        ThirdRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        ThirdRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        FourthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FourthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FourthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);


        FifthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FifthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FifthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SixthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SixthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SixthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SeventhRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SeventhRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SeventhRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        EigthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        EigthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        EigthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);
    }

    private void seventhSlotSelectDividerShowView() {
        FirstRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FirstRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FirstRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SecondRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SecondRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SecondRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        ThirdRowFirstColumn_SELECT_VID.setVisibility(View.VISIBLE);
        ThirdRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        ThirdRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        FourthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FourthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FourthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);


        FifthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FifthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FifthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SixthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SixthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SixthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SeventhRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SeventhRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SeventhRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        EigthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        EigthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        EigthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);
    }

    private void eigthSlotSelectDividerShowView() {

        FirstRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FirstRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FirstRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SecondRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SecondRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SecondRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        ThirdRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        ThirdRowSecondColumn_SELECT_VID.setVisibility(View.VISIBLE);
        ThirdRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        FourthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FourthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FourthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);


        FifthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FifthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FifthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SixthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SixthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SixthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SeventhRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SeventhRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SeventhRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        EigthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        EigthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        EigthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);
    }

    private void ninthSlotSelectDividerShowView() {
        FirstRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FirstRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FirstRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SecondRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SecondRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SecondRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        ThirdRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        ThirdRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        ThirdRowThirdColumn_SELECT_VID.setVisibility(View.VISIBLE);

        FourthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FourthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FourthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);


        FifthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FifthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FifthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SixthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SixthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SixthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SeventhRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SeventhRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SeventhRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        EigthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        EigthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        EigthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);
    }

    private void tenthSlotSelectDividerShowView() {

        FirstRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FirstRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FirstRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SecondRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SecondRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SecondRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        ThirdRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        ThirdRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        ThirdRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        FourthRowFirstColumn_SELECT_VID.setVisibility(View.VISIBLE);
        FourthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FourthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);


        FifthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FifthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FifthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SixthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SixthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SixthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SeventhRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SeventhRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SeventhRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        EigthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        EigthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        EigthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);
    }

    private void elevenSlotSelectDividerShowView() {

        FirstRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FirstRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FirstRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SecondRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SecondRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SecondRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        ThirdRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        ThirdRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        ThirdRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        FourthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FourthRowSecondColumn_SELECT_VID.setVisibility(View.VISIBLE);
        FourthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);


        FifthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FifthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FifthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SixthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SixthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SixthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SeventhRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SeventhRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SeventhRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        EigthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        EigthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        EigthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);
    }

    private void twelveSlotSelectDividerShowView() {
        FirstRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FirstRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FirstRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SecondRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SecondRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SecondRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        ThirdRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        ThirdRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        ThirdRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        FourthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FourthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FourthRowThirdColumn_SELECT_VID.setVisibility(View.VISIBLE);


        FifthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FifthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FifthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SixthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SixthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SixthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SeventhRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SeventhRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SeventhRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        EigthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        EigthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        EigthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);
    }

    private void thirteenSlotSelectDividerShowView() {

        FirstRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FirstRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FirstRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SecondRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SecondRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SecondRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        ThirdRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        ThirdRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        ThirdRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        FourthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FourthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FourthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);


        FifthRowFirstColumn_SELECT_VID.setVisibility(View.VISIBLE);
        FifthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FifthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SixthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SixthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SixthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SeventhRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SeventhRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SeventhRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        EigthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        EigthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        EigthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);
    }

    private void fountainSlotSelectDividerShowView() {

        FirstRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FirstRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FirstRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SecondRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SecondRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SecondRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        ThirdRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        ThirdRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        ThirdRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        FourthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FourthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FourthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);


        FifthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FifthRowSecondColumn_SELECT_VID.setVisibility(View.VISIBLE);
        FifthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SixthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SixthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SixthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SeventhRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SeventhRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SeventhRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        EigthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        EigthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        EigthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);
    }





    private void fifteenSlotSelectDividerShowView() {
        FirstRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FirstRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FirstRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SecondRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SecondRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SecondRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        ThirdRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        ThirdRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        ThirdRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        FourthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FourthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FourthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);


        FifthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FifthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FifthRowThirdColumn_SELECT_VID.setVisibility(View.VISIBLE);

        SixthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SixthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SixthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SeventhRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SeventhRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SeventhRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        EigthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        EigthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        EigthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);
    }

    private void sixteenSlotSelectDividerShowView() {
        FirstRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FirstRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FirstRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SecondRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SecondRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SecondRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        ThirdRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        ThirdRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        ThirdRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        FourthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FourthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FourthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);


        FifthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FifthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FifthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SixthRowFirstColumn_SELECT_VID.setVisibility(View.VISIBLE);
        SixthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SixthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SeventhRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SeventhRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SeventhRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        EigthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        EigthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        EigthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);
    }

    private void seventeenSlotSelectDividerShowView() {
        FirstRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FirstRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FirstRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SecondRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SecondRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SecondRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        ThirdRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        ThirdRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        ThirdRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        FourthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FourthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FourthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);


        FifthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FifthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FifthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SixthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SixthRowSecondColumn_SELECT_VID.setVisibility(View.VISIBLE);
        SixthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SeventhRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SeventhRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SeventhRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        EigthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        EigthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        EigthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);
    }

    private void eigtheenSlotSelectDividerShowView() {
        FirstRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FirstRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FirstRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SecondRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SecondRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SecondRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        ThirdRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        ThirdRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        ThirdRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        FourthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FourthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FourthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);


        FifthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FifthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FifthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SixthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SixthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SixthRowThirdColumn_SELECT_VID.setVisibility(View.VISIBLE);

        SeventhRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SeventhRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SeventhRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        EigthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        EigthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        EigthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);
    }

    private void nineteenSlotSelectDividerShowView() {
        FirstRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FirstRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FirstRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SecondRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SecondRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SecondRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        ThirdRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        ThirdRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        ThirdRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        FourthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FourthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FourthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);


        FifthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FifthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FifthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SixthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SixthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SixthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SeventhRowFirstColumn_SELECT_VID.setVisibility(View.VISIBLE);
        SeventhRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SeventhRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        EigthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        EigthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        EigthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);
    }

    private void twentySlotSelectDividerShowView() {
        FirstRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FirstRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FirstRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SecondRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SecondRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SecondRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        ThirdRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        ThirdRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        ThirdRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        FourthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FourthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FourthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);


        FifthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FifthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FifthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SixthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SixthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SixthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SeventhRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SeventhRowSecondColumn_SELECT_VID.setVisibility(View.VISIBLE);
        SeventhRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        EigthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        EigthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        EigthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);
    }

    private void twentyOneSlotSelectDividerShowView() {
        FirstRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FirstRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FirstRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SecondRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SecondRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SecondRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        ThirdRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        ThirdRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        ThirdRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        FourthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FourthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FourthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);


        FifthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FifthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FifthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SixthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SixthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SixthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SeventhRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SeventhRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SeventhRowThirdColumn_SELECT_VID.setVisibility(View.VISIBLE);

        EigthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        EigthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        EigthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);
    }

    private void twentytwoSlotSelectDividerShowView() {
        FirstRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FirstRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FirstRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SecondRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SecondRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SecondRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        ThirdRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        ThirdRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        ThirdRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        FourthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FourthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FourthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);


        FifthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FifthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FifthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SixthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SixthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SixthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SeventhRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SeventhRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SeventhRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        EigthRowFirstColumn_SELECT_VID.setVisibility(View.VISIBLE);
        EigthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        EigthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);
    }

    private void twentythreeSlotSelectDividerShowView() {
        FirstRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FirstRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FirstRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SecondRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SecondRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SecondRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        ThirdRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        ThirdRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        ThirdRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        FourthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FourthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FourthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);


        FifthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FifthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FifthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SixthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SixthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SixthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SeventhRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SeventhRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SeventhRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        EigthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        EigthRowSecondColumn_SELECT_VID.setVisibility(View.VISIBLE);
        EigthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);
    }

    private void twentyfourSlotSelectDividerShowView() {
        FirstRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FirstRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FirstRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SecondRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SecondRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SecondRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        ThirdRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        ThirdRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        ThirdRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        FourthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FourthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FourthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);


        FifthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FifthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FifthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SixthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SixthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SixthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SeventhRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SeventhRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SeventhRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        EigthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        EigthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        EigthRowThirdColumn_SELECT_VID.setVisibility(View.VISIBLE);
    }

    private void SlotSelectDividerShowAllWiseGoneView() {
        FirstRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FirstRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FirstRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SecondRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SecondRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SecondRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        ThirdRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        ThirdRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        ThirdRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        FourthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FourthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FourthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);


        FifthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        FifthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        FifthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SixthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SixthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SixthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        SeventhRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        SeventhRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        SeventhRowThirdColumn_SELECT_VID.setVisibility(View.GONE);

        EigthRowFirstColumn_SELECT_VID.setVisibility(View.GONE);
        EigthRowSecondColumn_SELECT_VID.setVisibility(View.GONE);
        EigthRowThirdColumn_SELECT_VID.setVisibility(View.GONE);
    }
}


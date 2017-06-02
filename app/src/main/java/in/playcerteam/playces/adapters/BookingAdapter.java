package in.playcerteam.playces.adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.playcerteam.playces.BookingListFragment;
import in.playcerteam.playces.R;
import in.playcerteam.playces.RegistrationActivity;
import in.playcerteam.playces.VerificationActivity;
import in.playcerteam.playces.model.BookingListData;
import in.playcerteam.playces.model.NestedBookingListData;
import in.playcerteam.playces.utilities.AppConstants;
import in.playcerteam.playces.utilities.SharedPreference;
import in.playcerteam.playces.utilities.Utility;

/**
 * Created by PlaycerTeam on 5/27/2016.
 */
public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {
    OnItemClickListener mItemClickListener;

    ArrayList<NestedBookingListData> nestedbookDataList;
    BookingListFragment mActivity;
    String tempbookId = "-1";
    ViewHolder tempHolder;
    JSONArray mainArray;
    public NestedBookingAdapter mNestedAdapter;
    ProgressDialog ringProgressDialog;
    ArrayList<BookingListData> subList;
    ArrayList<BookingListData> bookDataList;

    public BookingAdapter(BookingListFragment _activity, ArrayList<BookingListData> _bookDataList, ArrayList<NestedBookingListData> _nestedbookDataList) {
        this.mActivity = _activity;
        this.bookDataList = _bookDataList;
        //this.mainArray = mainArray;
        this.nestedbookDataList = _nestedbookDataList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public LinearLayout mainHolder;
        public ImageView statusColorTVID;
        public TextView sportsNameTVID;
        public TextView bookingidTVID;
        public TextView sportsDayAndTimeTVID;
        public TextView sportsTimeTVID;
        public TextView courtNoTVID;
        public TextView amontPaidTVID;
        public LinearLayout confirmLLID;
        public Button confirmPaymentBtnID;
        public LinearLayout amounPaidLLID;
        public RecyclerView nestedBookingListViewLVID;
        ImageView bookCancelIVID;
        ImageView courtCancelIVID;

        public ViewHolder(View itemView) {
            super(itemView);
            mainHolder = (LinearLayout) itemView.findViewById(R.id.mainHolder);
            statusColorTVID = (ImageView) itemView.findViewById(R.id.statusColorTVID);
            sportsNameTVID = (TextView) itemView.findViewById(R.id.sportsNameTVID);
            //bookingidTVID = (TextView) itemView.findViewById(R.id.bookingidTVID);
            sportsDayAndTimeTVID = (TextView) itemView.findViewById(R.id.sportsDayAndTimeTVID);
            sportsTimeTVID = (TextView) itemView.findViewById(R.id.sportsTimeTVID);
            courtNoTVID = (TextView) itemView.findViewById(R.id.courtNoTVID);
            amontPaidTVID = (TextView) itemView.findViewById(R.id.amontPaidTVID);
            confirmLLID = (LinearLayout) itemView.findViewById(R.id.confirmLLID);
            amounPaidLLID = (LinearLayout) itemView.findViewById(R.id.amounPaidLLID);
            confirmPaymentBtnID = (Button) itemView.findViewById(R.id.confirmPaymentBtnID);

            bookCancelIVID = (ImageView) itemView.findViewById(R.id.bookCancelIVID);
            courtCancelIVID = (ImageView) itemView.findViewById(R.id.courtCancelIVID);

            mainHolder.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(itemView, getPosition());
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public int getItemCount() {
        return bookDataList.size();
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items_row__booking_order_list, parent, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(final ViewHolder holder, final int position) {
        tempHolder = holder;
        if (!tempbookId.equals(bookDataList.get(position).get_BookingId())) {
            holder.sportsNameTVID.setText("" + Html.fromHtml(bookDataList.get(position).get_SportsName()));
            holder.bookingidTVID.setText("" + "#" + Html.fromHtml(bookDataList.get(position).get_BookingId()));
            holder.sportsNameTVID.setVisibility(View.VISIBLE);
            holder.bookingidTVID.setVisibility(View.VISIBLE);
            tempbookId = bookDataList.get(position).get_BookingId();
        } else {
            //holder.sportsNameTVID.setVisibility(View.GONE);
            //holder.bookingidTVID.setVisibility(View.GONE);
        }

        /*if (bookDataList.get(position).get_BookingId().equals(nestedbookDataList.get(position).get_BookingId())) {
            if (mActivity != null) {
                mNestedAdapter = new NestedBookingAdapter(mActivity, nestedbookDataList);
                holder.nestedBookingListViewLVID.setAdapter(mNestedAdapter);
            }
        }*/
        holder.sportsDayAndTimeTVID.setText("" + Html.fromHtml(bookDataList.get(position).get_BookedDate()));
        holder.sportsTimeTVID.setText("" + Html.fromHtml(bookDataList.get(position).get_SlotTime()));
        holder.courtNoTVID.setText("" + Html.fromHtml(bookDataList.get(position).get_CourtName()));
        String booking_source = bookDataList.get(position).get_Booking_source();
        String amount_paid = bookDataList.get(position).get_Amount_paid();
        String payment_mode = bookDataList.get(position).get_Payment_mode();
        String is_amount_paid = bookDataList.get(position).get_Is_amount_paid();
        String bookingCancelled = bookDataList.get(position).get_bookingCancelled();
        String courtCancelled = bookDataList.get(position).get_courtCancelled();
        holder.amontPaidTVID.setText("" + amount_paid);

        if (booking_source.equals("other")) {
            holder.confirmPaymentBtnID.setVisibility(View.GONE);
            holder.amounPaidLLID.setVisibility(View.GONE);
            holder.statusColorTVID.setBackgroundColor(Color.parseColor("#00000000"));
            holder.statusColorTVID.setBackgroundColor(Color.parseColor("#2B66B1"));   //darkblue
        } else {
            if (!booking_source.equals("prepaid")) {
                if (!is_amount_paid.equals("0")) {
                    holder.statusColorTVID.setBackgroundColor(Color.parseColor("#00000000"));
                    holder.statusColorTVID.setBackgroundColor(Color.parseColor("#FDCA00"));  //Yellow
                    holder.amontPaidTVID.setText(amount_paid);
                    holder.amontPaidTVID.setVisibility(View.VISIBLE);
                    holder.confirmPaymentBtnID.setVisibility(View.VISIBLE);
                    holder.amounPaidLLID.setVisibility(View.GONE);
                } else {
                    //holder.statusColorTVID.setBackgroundColor(Color.parseColor("#00aecd"));   // Ligth Blue
                }
            } else {
                holder.confirmPaymentBtnID.setVisibility(View.GONE);
                holder.amounPaidLLID.setVisibility(View.GONE);
                holder.statusColorTVID.setBackgroundColor(Color.parseColor("#00000000"));
                holder.statusColorTVID.setBackgroundColor(Color.parseColor("#00aecd"));   // Ligth Blue
            }
        }
        if (bookingCancelled != null) {
            if (bookingCancelled.equals("1")) {
                holder.bookCancelIVID.setVisibility(View.VISIBLE);
            } else {
                holder.bookCancelIVID.setVisibility(View.GONE);
            }
        }
        if (courtCancelled != null) {
            if (courtCancelled.equals("1")) {
                holder.courtCancelIVID.setVisibility(View.VISIBLE);
            } else {
                holder.courtCancelIVID.setVisibility(View.GONE);
            }
        } else {

        }

        /////////////////
        /*try {
            for (int i = 0; i < bookDataList.size(); i++) {
                if (i == position) {
                    subList = new ArrayList<BookingListData>();
                    ArrayList<String> slotsTimesList = new ArrayList<String>();
                    subList.clear();
                    slotsTimesList.clear();
                    subList = bookDataList.get(i).get_BookingId();

                    for (int k = 0; k < subList.size(); k++) {
                        holder.allViews[k].setVisibility(View.VISIBLE);
                        if (subList.get(k).get_slotStatus() == 0) {
                            holder.allViews[k].setText(" " + subList.get(k).get_slotTime() + " ");
                            holder.allViews[k].setTextColor(mContext.getResources().getColor(R.color.slot_available));
                        } else {
                            holder.allViews[k].setText(" " + subList.get(k).get_slotTime() + " ");
                            holder.allViews[k].setTextColor(mContext.getResources().getColor(R.color.slot_not_available));
                        }
                    }
                    break;
                }
            }
        } catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }  catch (Exception e){
            e.printStackTrace();
        }*/
        ///////////////////////

        holder.confirmPaymentBtnID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isNetAvailable = Utility.isOnline(mActivity);
                if (isNetAvailable) {
                    try {
                        ringProgressDialog = ProgressDialog.show(mActivity, null, "Please Wait...", true);
                        ringProgressDialog.setCancelable(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    RequestParams params = new RequestParams();
                    params.put("paid_by", SharedPreference.getPreferences(mActivity, "USER_ID"));
                    params.put("booking_id", bookDataList.get(position).get_BookingId());
                    params.put("payment_mode", bookDataList.get(position).get_Payment_mode());
                    confirmPaymentRequesttoServer(AppConstants.CONFIRM_PAYMENT_URL, params);
                    /*holder.confirmLLID.setVisibility(View.GONE);
                    holder.amounPaidLLID.setVisibility(View.VISIBLE);*/
                } else {
                    Utility.showCustomToast(mActivity.getResources().getString(R.string.pls_connect_internet), mActivity);
                }
            }
        });

    }

    public void confirmPaymentRequesttoServer(String _Url, RequestParams params) {
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
                            Utility.showCustomToast(message, mActivity);
                            tempHolder.confirmLLID.setVisibility(View.GONE);
                            tempHolder.amounPaidLLID.setVisibility(View.VISIBLE);
                        } else {
                            Utility.showCustomToast(message, mActivity);
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
                    Utility.showCustomToast(mActivity.getResources().getString(R.string.request_not_found), mActivity);
                } else if (statusCode == 500) {
                    Utility.showCustomToast(mActivity.getResources().getString(R.string.some_went_wrong), mActivity);
                } else {
                    Utility.showCustomToast(mActivity.getResources().getString(R.string.unexpected_error), mActivity);
                }
            }
        });
    }

}


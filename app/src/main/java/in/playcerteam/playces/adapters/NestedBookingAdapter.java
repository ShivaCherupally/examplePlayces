package in.playcerteam.playces.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import in.playcerteam.playces.BookingListFragment;
import in.playcerteam.playces.R;
import in.playcerteam.playces.model.BookingListData;
import in.playcerteam.playces.model.NestedBookingListData;

/**
 * Created by PlaycerTeam on 6/12/2016.
 */
public class NestedBookingAdapter extends RecyclerView.Adapter<NestedBookingAdapter.ViewHolder> {
    OnItemClickListener mItemClickListener;
    ArrayList<NestedBookingListData> bookDataList;
    BookingListFragment mActivity;
    String tempbookId = "-1";

    public NestedBookingAdapter(BookingListFragment _activity, ArrayList<NestedBookingListData> _bookDataList) {
        this.mActivity = _activity;
        this.bookDataList = _bookDataList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public LinearLayout mainHolder;
        public LinearLayout placeNameHolder;
        public ImageView statusColorTVID;
        public TextView sportsNameTVID;
        public TextView bookingidTVID;
        public TextView sportsDayAndTimeTVID;
        public TextView sportsTimeTVID;
        public TextView courtNoTVID;

        public RecyclerView nestedBookingListViewLVID;

        public TextView amontPaidTVID;
        public LinearLayout confirmLLID;
        public Button confirmPaymentBtnID;
        public LinearLayout amounPaidLLID;


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
            //nestedBookingListViewLVID = (RecyclerView) itemView.findViewById(R.id.nestedBookingListViewLVID);

            mainHolder.setOnClickListener(this);
            confirmPaymentBtnID.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    confirmLLID.setVisibility(View.GONE);
                    amounPaidLLID.setVisibility(View.VISIBLE);
                }
            });
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_list_row_item_booking_order_list, parent, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(final ViewHolder holder, final int position) {

        /*if (!tempbookId.equals(bookDataList.get(position).get_BookingId())) {
            //if (bookDataList.get(position).get_SportsName().isEmpty() && bookDataList.get(position).get_BookingId().isEmpty()) {
                holder.sportsNameTVID.setText("" + Html.fromHtml(bookDataList.get(position).get_SportsName()));
                holder.bookingidTVID.setText("" + "#" + Html.fromHtml(bookDataList.get(position).get_BookingId()));
                holder.sportsNameTVID.setVisibility(View.VISIBLE);
                holder.bookingidTVID.setVisibility(View.VISIBLE);
            //}
            tempbookId = bookDataList.get(position).get_BookingId();
        }
        else {
            //holder.sportsNameTVID.setVisibility(View.GONE);
            //holder.bookingidTVID.setVisibility(View.GONE);
        }*/

            holder.sportsDayAndTimeTVID.setText("" + Html.fromHtml(bookDataList.get(position).get_BookedDate()));
            holder.sportsTimeTVID.setText("" + Html.fromHtml(bookDataList.get(position).get_SlotTime()));
            holder.courtNoTVID.setText("" + Html.fromHtml(bookDataList.get(position).get_CourtName()));
            String booking_source = bookDataList.get(position).get_Booking_source();
            String amount_paid = bookDataList.get(position).get_Amount_paid();
            String payment_mode = bookDataList.get(position).get_Payment_mode();
            String is_amount_paid = bookDataList.get(position).get_Is_amount_paid();
            if (booking_source.equals("other")) {
                holder.statusColorTVID.setBackgroundColor(Color.parseColor("#2B66B1"));   //darkblue

            } else if (!booking_source.equals("prepaid")) {
                //if (payment_mode.equals("online")) {
                if (!is_amount_paid.equals("0")) {
                    holder.statusColorTVID.setBackgroundColor(Color.parseColor("#fdca00"));  //Yellow
                    holder.amontPaidTVID.setText("Amount to be paid: INR " + amount_paid);
                    holder.amontPaidTVID.setVisibility(View.VISIBLE);
                    holder.confirmPaymentBtnID.setVisibility(View.VISIBLE);
                } else {
                    holder.statusColorTVID.setBackgroundColor(Color.parseColor("#00aecd"));   // Ligth Blue
                }
                //}
            } else {
                holder.statusColorTVID.setBackgroundColor(Color.parseColor("#00aecd"));   // Ligth Blue
            }

    }

}




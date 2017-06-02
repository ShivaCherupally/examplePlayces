package in.playcerteam.playces.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import in.playcerteam.playces.MainActivity;
import in.playcerteam.playces.R;
import in.playcerteam.playces.model.SportsAndCenterListData;
import in.playcerteam.playces.utilities.SharedPreference;

/**
 * Created by PlaycerTeam on 5/26/2016.
 */
public class SportAndCenterAdapter extends RecyclerView.Adapter<SportAndCenterAdapter.ViewHolder> {
    OnItemClickListener mItemClickListener;
    ArrayList<SportsAndCenterListData> sportsandCenterList;
    Activity mActivity;

    public SportAndCenterAdapter(Activity _activity, ArrayList<SportsAndCenterListData> _sportsandCenterList) {
        this.mActivity = _activity;
        this.sportsandCenterList = _sportsandCenterList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public LinearLayout placeHolder;
        public TextView placesCodeTVID;
        public TextView sportsCenterTVID;
        public TextView sportsNameTVID;
        public TextView statusTVID;

        public ViewHolder(View itemView) {
            super(itemView);
            placeHolder = (LinearLayout) itemView.findViewById(R.id.mainHolder);
            placesCodeTVID = (TextView) itemView.findViewById(R.id.placesCodeTVID);
            sportsCenterTVID = (TextView) itemView.findViewById(R.id.sportsCenterTVID);
            sportsNameTVID = (TextView) itemView.findViewById(R.id.sportsNameTVID);
            statusTVID = (TextView) itemView.findViewById(R.id.statusTVID);
            /*try {
                MainActivity.availableDateTVID.setVisibility(View.GONE);
                MainActivity.sports_locationTVID.setVisibility(View.GONE);
                MainActivity.sportsSPID.setVisibility(View.GONE);
            }
            catch (Exception e){
                e.printStackTrace();
            }*/

            placeHolder.setOnClickListener(this);
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
        return sportsandCenterList.size();
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sports_centers_list_items_row, parent, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(final ViewHolder holder, final int position) {
        String sportsName = sportsandCenterList.get(position).getSportsName().toString();
        String regex = "\\[|\\]";
        sportsName = sportsName.replace("\"", "").replaceAll(regex, "").replaceAll(",", ", ");
        //sportsName  = sportsName.replaceAll(regex, "");

        holder.placesCodeTVID.setText("" + "Playces Code: " + Html.fromHtml(sportsandCenterList.get(position).getPlacesCode()));
        holder.sportsCenterTVID.setText("" + Html.fromHtml(sportsandCenterList.get(position).getFacilityName()) + ", " + Html.fromHtml(sportsandCenterList.get(position).getsSportsCenter()));
        holder.sportsNameTVID.setText("" + "Sports: " + sportsName);
        String facilityIdLocal = sportsandCenterList.get(position).getFacilityId();
        /*int positiontemp = 0;
        try {
            if (sportsandCenterList.get(position).getsSportsCenter().equals(SharedPreference.getPreferences(mActivity, "LOCATION"))) {
                holder.statusTVID.setBackgroundColor(Color.parseColor("#0084b4"));
            } else if (position == positiontemp) {
                holder.statusTVID.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }*//*else {
                holder.statusTVID.setBackgroundColor(Color.parseColor("#0084b4"));
                *//**//*if (position == 0) {
                }*//**//*
            }*//*
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        if (facilityIdLocal.equals(SharedPreference.getPreferences(mActivity, "KEY_KEY_FACILITY_ID"))) {
            holder.statusTVID.setBackgroundColor(Color.parseColor("#0084b4"));
        } else {
            holder.statusTVID.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }


    }
}
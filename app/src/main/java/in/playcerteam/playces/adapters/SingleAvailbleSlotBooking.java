package in.playcerteam.playces.adapters;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import in.playcerteam.playces.R;
import in.playcerteam.playces.model.SingleAvailbleListData;

/**
 * Created by PlaycerTeam on 5/16/2016.
 */
public class SingleAvailbleSlotBooking extends BaseAdapter {
    private ArrayList<SingleAvailbleListData> listData;
    private LayoutInflater layoutInflater = null;
    Context mContext;
    //The "x" and "y" position of the "Show Button" on screen.
    Point p;


    public SingleAvailbleSlotBooking(Context context, ArrayList<SingleAvailbleListData> listData) {
        this.mContext = context;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int positionvalue) {
        return listData.get(positionvalue);
    }

    @Override
    public long getItemId(int positionvalue) {
        return positionvalue;
    }

    @Override
    public View getView(int positionNo, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.grid_items_row_single_slot_booking, null, false);
            holder.slotTimeTVID = (TextView) convertView.findViewById(R.id.slotTimeTVID);
            holder.availbleCourtsTVID = (TextView) convertView.findViewById(R.id.availbleCourtsTVID);
            holder.totalCourtsTVID = (TextView) convertView.findViewById(R.id.totalCourtsTVID);
            holder.singleSlotLLID = (LinearLayout) convertView.findViewById(R.id.singleSlotLLID);
            // This will now execute only for the first time of each row
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        try {
            /*String Status = listData.get(positionNo).get_slotStatus();
            int typeId = listData.get(positionNo).get_typeId();
            holder.slotTimeTVID.setText(listData.get(positionNo).get_slotTime());
            holder.availbleCourtsTVID.setText(listData.get(positionNo).get_availbleCourts());
            holder.totalCourtsTVID.setText(listData.get(positionNo).get_TotalCourts());
            holder.singleSlotLLID.setTag(listData.get(positionNo).get_typeId());*/

            holder.singleSlotLLID.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        final View vv = v;

                        String imageResIdg = (String) v.getTag().toString();
                        Toast.makeText(mContext, "Result " + "Hi Shiva", Toast.LENGTH_LONG).show();
                        //if (p != null)
                        //showPopup(vv, p);
                        // showPopup(p);
                        int y;
                        if (v.getTag() == null) {
                            y = 100;
                        } else {
                            int position = (Integer) v.getTag();
                            y = (1 + position) * v.getHeight();
                        }

                        int x = (int) v.getRight();
         //               showPopup(x, y);// calls popup


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    static class ViewHolder {
        TextView slotTimeTVID;
        TextView availbleCourtsTVID;
        TextView totalCourtsTVID;
        LinearLayout singleSlotLLID;
    }

    // The method that displays the popup.
    //private void showPopup(View context, Point p) {


}

package in.playcerteam.playces.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import in.playcerteam.playces.R;

public class GridItemView extends FrameLayout {

    private TextView textView;
    private View borderViewTVID;

    public GridItemView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.item_grid_courts, this);
        textView = (TextView) getRootView().findViewById(R.id.text);
        borderViewTVID = (View) getRootView().findViewById(R.id.borderViewTVID);
    }

    public void display(String text, boolean isSelected) {
        textView.setBackgroundColor(getResources().getColor(R.color.courtsBgColor));
        textView.setText(text);
        display(isSelected);
    }

    public void display(boolean isSelected) {
        //textView.setBackgroundResource(isSelected ? R.drawable.green_square : R.drawable.gray_square);
        textView.setBackgroundColor(isSelected ? getResources().getColor(R.color.courtsBgColor) : getResources().getColor(R.color.courtsBgColor));
        if (isSelected) {
            borderViewTVID.setVisibility(View.VISIBLE);
        } else {
            borderViewTVID.setVisibility(View.GONE);
        }
        //textView.setBackgroundResource(isSelected ? getResources().getColor(R.color.courtsBgColor));
    }

    public void courtDisble(String text, boolean isSelected) {
        textView.setBackgroundColor(isSelected ? getResources().getColor(R.color.court_unavailble) : getResources().getColor(R.color.court_unavailble));
        textView.setText(text);
        textView.setTextColor(getResources().getColor(R.color.slot_inside_hide_text_color));
        borderViewTVID.setVisibility(View.VISIBLE);
        borderViewTVID.setBackgroundColor(getResources().getColor(R.color.court_unavailble));
        //display(isSelected);
    }
}
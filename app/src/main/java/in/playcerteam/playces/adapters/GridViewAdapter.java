package in.playcerteam.playces.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import in.playcerteam.playces.model.CourtDetailsData;

public class GridViewAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<CourtDetailsData> courtDetailsDataLocal;
    public List<Integer> selectedPositions;

    public GridViewAdapter(ArrayList<CourtDetailsData> _courtDetailsDataLocal, Activity activity) {
        this.courtDetailsDataLocal = _courtDetailsDataLocal;
        this.activity = activity;
        selectedPositions = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return courtDetailsDataLocal.size();
    }

    @Override
    public Object getItem(int position) {
        return courtDetailsDataLocal.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GridItemView customView = (convertView == null) ? new GridItemView(activity) : (GridItemView) convertView;
        customView.display(courtDetailsDataLocal.get(position).get_courtName(), selectedPositions.contains(position));
        if (!courtDetailsDataLocal.get(position).get_available()) {
            customView.courtDisble(courtDetailsDataLocal.get(position).get_courtName(), selectedPositions.contains(position));
        }
        return customView;
    }
}

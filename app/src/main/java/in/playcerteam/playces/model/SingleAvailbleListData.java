package in.playcerteam.playces.model;

import java.util.ArrayList;

/**
 * Created by PlaycerTeam on 5/17/2016.
 */
public class SingleAvailbleListData {
    private String slotId;
    private String slotName;
    private int totalCourts;
    private int availableCourts;
    private boolean disabled;
    ArrayList<CourtDetailsData> courtDetails;

    public SingleAvailbleListData(String slotId, String slotName, int totalCourts, int availableCourts, boolean disabled, ArrayList<CourtDetailsData> courtDetails) {
        this.slotId = slotId;
        this.slotName = slotName;
        this.totalCourts = totalCourts;
        this.availableCourts = availableCourts;
        this.disabled = disabled;
        this.courtDetails = courtDetails;
    }

    public SingleAvailbleListData() {
    }

    //Slot Id
    public void set_slotId(String slotId) {
        this.slotId = slotId;
    }

    public String get_slotId() {
        return slotId;
    }

    //Slot Name
    public void set_slotName(String slotName) {
        this.slotName = slotName;
    }

    public String get_slotName() {
        return slotName;
    }

    //totalCourts Courts
    public void set_totalCourts(int totalCourts) {
        this.totalCourts = totalCourts;
    }

    public int get_totalCourts() {
        return totalCourts;
    }

    //Availble Courts
    public void set_availbleCourts(int availbleCourts) {
        this.availableCourts = availbleCourts;
    }

    public int get_availbleCourts() {
        return availableCourts;
    }

    //Disble
    public void set_disbled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean get_disbled() {
        return disabled;
    }

    //Court Detail
    public void set_courtDetails(ArrayList<CourtDetailsData> courtDetails) {
        this.courtDetails = courtDetails;
    }

    public ArrayList<CourtDetailsData> get_courtDetails() {
        return courtDetails;
    }


}



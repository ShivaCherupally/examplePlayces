package in.playcerteam.playces.model;

import java.util.ArrayList;

/**
 * Created by PlaycerTeam on 5/30/2016.
 */
public class CourtDetailsData {
    private String sportsId;
    private String courtName;
    private String day;
    private String courts_id;
    private String start_time;
    private String end_time;
    private boolean available;


    public CourtDetailsData(String sportsId, String courtName, String day, String courts_id, String start_time, String end_time, boolean available) {
        this.sportsId = sportsId;
        this.courtName = courtName;
        this.day = day;
        this.courts_id = courts_id;
        this.start_time = start_time;
        this.end_time = end_time;
        this.available = available;
    }

    public CourtDetailsData() {
    }

    //Sports Id
    public void set_sportsId(String sportsId) {
        this.sportsId = sportsId;
    }

    public String get_sportsId() {
        return sportsId;
    }

    //CourtName Name
    public void set_courtName(String courtName) {
        this.courtName = courtName;
    }

    public String get_courtName() {
        return courtName;
    }

    //day Courts
    public void set_day(String day) {
        this.day = day;
    }

    public String get_day() {
        return day;
    }

    //Slot time
    public void set_slotTime(String courts_id) {
        this.courts_id = courts_id;
    }

    public String get_slotTime() {
        return courts_id;
    }

    //courts_id
    public void set_courts_id(String courts_id) {
        this.courts_id = courts_id;
    }

    public String get_courts_id() {
        return courts_id;
    }

    //start_time
    public void set_start_time(String start_time) {
        this.start_time = start_time;
    }

    public String get_start_time() {
        return start_time;
    }

    //end_time
    public void set_end_time(String end_time) {
        this.end_time = end_time;
    }

    public String get_end_time() {
        return end_time;
    }

    //available
    public void set_available(boolean available) {
        this.available = available;
    }

    public boolean get_available() {
        return available;
    }
}





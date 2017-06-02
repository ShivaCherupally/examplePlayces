package in.playcerteam.playces.model;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by PlaycerTeam on 5/26/2016.
 */
public class SportsAndCenterListData {

    private String facilityId;
    private String facilityName;
    private String placesCode;
    private String sportsCenter;
    private JSONArray sportsList;

    public SportsAndCenterListData(String facilityId, String facilityName, String placesCode, String sportsCenter, JSONArray sportsList) {
        this.facilityId = facilityId;
        this.facilityName = facilityName;
        this.placesCode = placesCode;
        this.sportsCenter = sportsCenter;
        this.sportsList = sportsList;
    }

    public SportsAndCenterListData() {
    }

    //placesCode
    public void set_FacilityId(String facilityId) {
        this.facilityId = facilityId;
    }

    public String getFacilityId() {
        return facilityId;
    }

    //placesCode
    public void set_PlacesCode(String placesCode) {
        this.placesCode = placesCode;
    }

    public String getPlacesCode() {
        return placesCode;
    }

    //sportsCenter
    public void set_SportsCenter(String sportsCenter) {
        this.sportsCenter = sportsCenter;
    }

    public String getsSportsCenter() {
        return sportsCenter;
    }

    //
    public void set_FacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public String getFacilityName() {
        return facilityName;
    }

    //Status
    //sportsName
    public void set_SportsName(JSONArray sportsList) {
        this.sportsList = sportsList;
    }

    public JSONArray getSportsName() {
        return sportsList;
    }
}




package in.playcerteam.playces.model;

import java.util.ArrayList;

/**
 * Created by PlaycerTeam on 6/14/2016.
 */
public class ExpandableBookingListData {
    private String bookingId;
    private ArrayList<BookingListData> _bookingListData;

    /*public ExpandableBookingListData() {
    }

    public ExpandableBookingListData(String bookingId, ArrayList<BookingListData> _bookingListData) {
        this.bookingId = bookingId;
        this._bookingListData = _bookingListData;
    }*/

    //bookingId
    public void set_BookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String get_BookingId() {
        return bookingId;
    }


    public ArrayList<BookingListData> getBookingListData() {
        return _bookingListData;
    }

    public void setBookingListData(ArrayList<BookingListData> _bookingListData) {
        this._bookingListData = _bookingListData;
    }
}

package in.playcerteam.playces.model;

/**
 * Created by PlaycerTeam on 6/12/2016.
 */
public class NestedBookingListData {
    private String sportsId;
    private String sportsName;
    private String bookingId;
    private String bookedDate;
    private String slotTime;
    private String courtName;
    private String booking_source;
    private String amount_paid;
    private String is_amount_paid;
    private String payment_mode;
    private int isOpened;      //This is For Show item on this screen

    public NestedBookingListData() {
    }

    public NestedBookingListData(String sportsId, String sportsName, String bookingId, String bookedDate, String slotTime, String courtName,
                                 String booking_source, String amount_paid, String is_amount_paid, String payment_mode, int isOpened) {
        this.sportsId = sportsId;
        this.sportsName = sportsName;
        this.bookingId = bookingId;
        this.bookedDate = bookedDate;
        this.slotTime = slotTime;
        this.courtName = courtName;
        this.booking_source = booking_source;
        this.amount_paid = amount_paid;
        this.is_amount_paid = is_amount_paid;
        this.payment_mode = payment_mode;
        this.isOpened = isOpened;
    }


    //sportsId
    public void set_SportsId(String sportsId) {
        this.sportsId = sportsId;
    }

    public String get_SportsId() {
        return sportsId;
    }


    //sportsName
    public void set_SportsName(String sportsName) {
        this.sportsName = sportsName;
    }

    public String get_SportsName() {
        return sportsName;
    }

    //bookingId
    public void set_BookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String get_BookingId() {
        return bookingId;
    }

    //bookedDate
    public void set_BookedDate(String bookedDate) {
        this.bookedDate = bookedDate;
    }

    public String get_BookedDate() {
        return bookedDate;
    }

    //slotTime
    public void set_SlotTime(String slotTime) {
        this.slotTime = slotTime;
    }

    public String get_SlotTime() {
        return slotTime;
    }

    //courtName
    public void set_CourtName(String courtName) {
        this.courtName = courtName;
    }

    public String get_CourtName() {
        return courtName;
    }

    //booking_source
    public void set_Booking_source(String booking_source) {
        this.booking_source = booking_source;
    }

    public String get_Booking_source() {
        return booking_source;
    }

    //amount_paid
    public void set_Amount_paid(String amount_paid) {
        this.amount_paid = amount_paid;
    }

    public String get_Amount_paid() {
        return amount_paid;
    }

    //is_amount_paid
    public void set_Is_amount_paid(String is_amount_paid) {
        this.is_amount_paid = is_amount_paid;
    }

    public String get_Is_amount_paid() {
        return is_amount_paid;
    }

    //payment_mode
    public void set_Payment_mode(String payment_mode) {
        this.payment_mode = payment_mode;
    }

    public String get_Payment_mode() {
        return payment_mode;
    }

    //Open
    public void set_isOpened(int isOpened) {
        this.isOpened = isOpened;
    }

    public int get_isOpened() {
        return isOpened;
    }
}
package in.playcerteam.playces.model;

/**
 * Created by PlaycerTeam on 5/28/2016.
 */

public class SportAvailableListData {
    public String sport_id;
    public String sport_title;

    public SportAvailableListData(String sport_id, String sport_title) {
        this.sport_id = sport_id;
        this.sport_title = sport_title;
    }

    ///Project Id
    public void setSportsId(String sport_id) {
        this.sport_id = sport_id;
    }

    public String getSportsId() {
        return sport_id;
    }

    /////Project Type
    public void setSportsTitle(String sport_title) {
        this.sport_title = sport_title;
    }

    public String getSportsTitle() {
        return sport_title;
    }
}

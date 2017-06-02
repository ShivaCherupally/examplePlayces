package in.playcerteam.playces.utilities;

/**
 * Created by HARIKRISHNA on 8/20/2015.
 * at CaretTech
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AppDataBaseHelper extends SQLiteOpenHelper {
    public static final String dbName ="PlaycesDB";
    public static final String oneTimeRegistration ="oneTimeRegistration";
    public static final String deviceID ="deviceID";
    public static final int dbVersion = 1;

    public AppDataBaseHelper(Context _ctx) {
        super(_ctx, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + oneTimeRegistration + " (" + deviceID + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + oneTimeRegistration);
        onCreate(db);
    }

    public void addRegisteredUDID(String _UDID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(deviceID, _UDID);
        db.insert(oneTimeRegistration, deviceID, cv);
        db.close();
    }

    public void updateStatusRecords(String updateStatus) {

    }

    public String getRegisteredDeviceID() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM " + oneTimeRegistration, new String[]{});
        String UDID = null;
        if (cur.getCount() > 0) {
            if (cur.moveToFirst()) {
                do {
                    UDID = cur.getString(cur.getColumnIndex(deviceID));
                } while (cur.moveToNext());
            }
        }
        cur.close();
        db.close();
        return UDID;
    }


    public int deleteEntry(String deviceid)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String where="deviceID=?";
        int numberOFEntriesDeleted= db.delete(oneTimeRegistration, where, new String[]{deviceid}) ;
        db.close();
        return numberOFEntriesDeleted;
    }
}

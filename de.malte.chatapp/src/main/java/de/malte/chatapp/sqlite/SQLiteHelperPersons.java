package de.malte.chatapp.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Malte on 05.03.14.
 */
public class SQLiteHelperPersons extends SQLiteOpenHelper {
    public static final String TABLE_PERSONS = "persons";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NICKNAME = "name";
    public static final String COLUMN_EMAIL = "email";

    // TODO: implement profile pic
    //public static final String COLUMN_PICTURE = "picture";

    private static final String DATABASE_NAME = "contacts.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_PERSONS + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_NICKNAME
            + " text not null, " + COLUMN_EMAIL + " text not null);";


    public SQLiteHelperPersons(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        Log.v("DB", DATABASE_CREATE);
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SQLiteHelperPersons.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSONS);
        onCreate(db);
    }
}

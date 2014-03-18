package de.malte.chatapp.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Malte on 06.03.14.
 */
public class SQLiteHelperConversation extends SQLiteOpenHelper {
    public static final String TABLE_CONVERSATION = "conversations";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TEXT = "text";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_WITH_ID = "with";
    public static final String COLUMN_FROM_CURRENT_USER = "fromcurrentuser";

    // TODO: implement profile pic
    //public static final String COLUMN_PICTURE = "picture";

    private static final String DATABASE_NAME = "conversations.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_CONVERSATION + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_TEXT
            + " text not null, "+ COLUMN_DATE +" integer not null, "+ COLUMN_WITH_ID +" integer not null, "+ COLUMN_FROM_CURRENT_USER +" integer not null);";


    public SQLiteHelperConversation(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        Log.v("DB", DATABASE_CREATE);
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SQLiteHelperConversation.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONVERSATION);
        onCreate(db);
    }
}

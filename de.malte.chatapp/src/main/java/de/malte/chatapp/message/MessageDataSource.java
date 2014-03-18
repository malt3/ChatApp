package de.malte.chatapp.message;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.malte.chatapp.person.Person;
import de.malte.chatapp.sqlite.SQLiteHelperConversation;

/**
 * Created by Malte on 06.03.14.
 */
public class MessageDataSource {
    // Database fields
    private SQLiteDatabase database;
    private SQLiteHelperConversation dbHelper;
    private String[] allColumns = {SQLiteHelperConversation.COLUMN_ID, SQLiteHelperConversation.COLUMN_TEXT, SQLiteHelperConversation.COLUMN_DATE, SQLiteHelperConversation.COLUMN_WITH_ID, SQLiteHelperConversation.COLUMN_FROM_CURRENT_USER};

    //private final SimpleDateFormat messageTimeFormat = new SimpleDateFormat("")

    public MessageDataSource(Context context) {
        dbHelper = new SQLiteHelperConversation(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Message createMessage(String text, long with, Date date, boolean fromCurrentUser) {
        Log.v("MessageDataSource datelong", String.valueOf(date.getTime()));
        ContentValues values = new ContentValues();
        values.put(SQLiteHelperConversation.COLUMN_TEXT, text);
        values.put(SQLiteHelperConversation.COLUMN_WITH_ID, with);
        values.put(SQLiteHelperConversation.COLUMN_DATE, date.getTime());
        values.put(SQLiteHelperConversation.COLUMN_FROM_CURRENT_USER, fromCurrentUser);
        long insertId = database.insert(SQLiteHelperConversation.TABLE_CONVERSATION, null,
                values);
        Cursor cursor = database.query(SQLiteHelperConversation.TABLE_CONVERSATION,
                allColumns, SQLiteHelperConversation.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Message newMessage = cursorToMessage(cursor);
        cursor.close();
        return newMessage;
    }

    public void deleteMessage(Message message) {
        long id = message.id;
        Log.v("DB", "Comment deleted with id: " + id);
        database.delete(SQLiteHelperConversation.TABLE_CONVERSATION, SQLiteHelperConversation.COLUMN_ID
                + " = " + id, null);
    }

    public Message getMessage(long id) {
        String whereClause = SQLiteHelperConversation.COLUMN_ID+"="+id;
        Cursor cursor = database.query(SQLiteHelperConversation.TABLE_CONVERSATION, allColumns, whereClause, null, null, null, null);
        cursor.moveToFirst();
        Message message = cursorToMessage(cursor);
        cursor.close();
        return message;
    }

    public Message getLastMessageWithPerson(long personId){
        String query = "select * from "+SQLiteHelperConversation.TABLE_CONVERSATION+" where "+SQLiteHelperConversation.COLUMN_WITH_ID+"="+personId+" order by "+SQLiteHelperConversation.COLUMN_ID+" ASC limit 1;";
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        Message message = cursorToMessage(cursor);
        cursor.close();
        return message;
    }

    public List<Message> getAllMessagesWithPerson(long id) {
        List<Message> messages = new ArrayList<Message>();
        String whereClause = SQLiteHelperConversation.COLUMN_WITH_ID+"="+id;
        Cursor cursor = database.query(SQLiteHelperConversation.TABLE_CONVERSATION,
                allColumns, whereClause, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Message message = cursorToMessage(cursor);
            messages.add(message);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return messages;
    }

    private Message cursorToMessage(Cursor cursor) {
        long id = cursor.getLong(0);
        String text = cursor.getString(1);
        long time_millis = cursor.getLong(2);
        long withID = cursor.getLong(3);
        boolean fromCurrentUser;
        if (cursor.getInt(4) == 1){
            fromCurrentUser = true;
        }else{
            fromCurrentUser = false;
        }
        // TODO: Convert to real Date
        Date date = new Date(time_millis);

        Message message = new Message(id, text, withID, date, fromCurrentUser);
        return message;
    }
}
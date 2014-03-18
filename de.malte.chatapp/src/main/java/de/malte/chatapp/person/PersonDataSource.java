package de.malte.chatapp.person;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import de.malte.chatapp.sqlite.SQLiteHelperPersons;

/**
 * Created by Malte on 05.03.14.
 */
public class PersonDataSource {
    // Database fields
    private SQLiteDatabase database;
    private SQLiteHelperPersons dbHelper;
    private String[] allColumns = { SQLiteHelperPersons.COLUMN_ID,
            SQLiteHelperPersons.COLUMN_NICKNAME, SQLiteHelperPersons.COLUMN_EMAIL };

    public PersonDataSource(Context context) {
        dbHelper = new SQLiteHelperPersons(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Person createPerson(String name, String email) {
        ContentValues values = new ContentValues();
        values.put(SQLiteHelperPersons.COLUMN_NICKNAME, name);
        values.put(SQLiteHelperPersons.COLUMN_EMAIL, email);
        long insertId = database.insert(SQLiteHelperPersons.TABLE_PERSONS, null,
                values);
        Cursor cursor = database.query(SQLiteHelperPersons.TABLE_PERSONS,
                allColumns, SQLiteHelperPersons.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Person newPerson = cursorToPerson(cursor);
        cursor.close();
        return newPerson;
    }

    public void deletePerson(Person person) {
        long id = person.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(SQLiteHelperPersons.TABLE_PERSONS, SQLiteHelperPersons.COLUMN_ID
                + " = " + id, null);
    }

    public Person getPerson(long id) {
        String whereClause = SQLiteHelperPersons.COLUMN_ID+"="+id;
        Cursor cursor = database.query(SQLiteHelperPersons.TABLE_PERSONS, allColumns, whereClause, null, null, null, null);
        cursor.moveToFirst();
        Person person = cursorToPerson(cursor);
        cursor.close();
        return person;
    }

    public List<Person> getAllPersons() {
        List<Person> persons = new ArrayList<Person>();

        Cursor cursor = database.query(SQLiteHelperPersons.TABLE_PERSONS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Person person = cursorToPerson(cursor);
            persons.add(person);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return persons;
    }

    private Person cursorToPerson(Cursor cursor) {
        long id = cursor.getLong(0);
        String name = cursor.getString(1);
        String email = cursor.getString(2);
        Person person = new Person(id, name, email);
        return person;
    }
}

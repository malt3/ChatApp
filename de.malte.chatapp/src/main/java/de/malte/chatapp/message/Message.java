package de.malte.chatapp.message;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.malte.chatapp.person.Person;

/**
 * Created by Malte on 04.03.14.
 */
public class Message implements Comparable<Message>{
    public long id;
    public String text;
    public long withID;
    public Date date;
    public boolean fromCurrentUser;

    private final SimpleDateFormat time = new SimpleDateFormat("kk:mm");

    public Message(long id, String text, long withID, Date date, boolean fromCurrentUser) {
        this.id = id;
        this.text = text;
        this.withID = withID;
        this.date = date;
        this.fromCurrentUser = fromCurrentUser;
    }


    public String getText() {
        return text;
    }
    public String getTime() {
        return time.format(date);
    }

    public int compareTo(Message compareObject){
        return this.date.compareTo(compareObject.date);
    }
}

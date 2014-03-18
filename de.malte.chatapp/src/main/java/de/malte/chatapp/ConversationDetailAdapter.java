package de.malte.chatapp;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import de.malte.chatapp.message.Message;
import de.malte.chatapp.message.MessageDataSource;
import de.malte.chatapp.person.Person;
import de.malte.chatapp.person.PersonDataSource;

/**
 * Created by Malte on 05.03.14.
 */
class ConversationDetailAdapter extends BaseAdapter {

    private final LayoutInflater mInflater;
    private final Context mContext;
    private List<Message> messages;
    private PersonDataSource personDataSource;
    private MessageDataSource messageDataSource;
    private Person with;

    public ConversationDetailAdapter(Context context, Person person) {
        mContext = context;
        mInflater = (LayoutInflater)mContext.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        personDataSource = new PersonDataSource(mContext);
        messageDataSource = new MessageDataSource(mContext);

        with = person;
        messageDataSource.open();
        messages = (messageDataSource.getAllMessagesWithPerson(with.getId()));
        messageDataSource.close();

    }

    public int getCount(){
        return messages.size();
    }

    public Message getItem(int position){
        return messages.get(position);
    }

    public long getItemId(int position) {
        // TODO: implement real ID
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        Message message = getItem(position);
        RelativeLayout view;

        if (message.fromCurrentUser){
            view = (RelativeLayout) mInflater.inflate(R.layout.message_row_by_user, parent, false);
        }else{
            view = (RelativeLayout) mInflater.inflate(R.layout.message_row_by_contact, parent, false);
        }

        TextView time = (TextView)view.findViewById(R.id.time);
        TextView text = (TextView)view.findViewById(R.id.message_text);

        time.setText(message.getTime());
        text.setText(message.getText());

        return view;
    }

}
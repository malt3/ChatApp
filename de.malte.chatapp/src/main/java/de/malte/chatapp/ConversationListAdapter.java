package de.malte.chatapp;

import android.content.Context;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import de.malte.chatapp.message.Message;
import de.malte.chatapp.message.MessageDataSource;
import de.malte.chatapp.person.Person;
import de.malte.chatapp.person.PersonDataSource;
import de.malte.chatapp.sqlite.SQLiteHelperConversation;

/**
 * Created by Malte on 05.03.14.
 */
class ConversationListAdapter extends BaseAdapter {

    private final LayoutInflater mInflater;
    private final Context mContext;
    private PersonDataSource personDataSource;
    private MessageDataSource messageDataSource;
    private List<Person> persons;

    public ConversationListAdapter(Context context) {
        mContext = context;
        mInflater = (LayoutInflater)mContext.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        personDataSource = new PersonDataSource(context);
        messageDataSource = new MessageDataSource(context);

        personDataSource.open();
        persons = personDataSource.getAllPersons();
        personDataSource.close();

        if (getCount() == 0) {
            personDataSource.open();
            if (Person.isValidEmail("moritz.poll@web.de"))
            {Person moritz = personDataSource.createPerson("Moritz Poll", "moritz.poll@web.de");
            Person pete = personDataSource.createPerson("Peter Ankermann", "pete@anker.com");
            Date date = new Date();
            date.setTime(System.currentTimeMillis());
            messageDataSource.open();
            messageDataSource.createMessage("Hallo Welt", moritz.getId(), date, false);
            messageDataSource.createMessage("Hallo Moritz", moritz.getId(), date, true);
            messageDataSource.createMessage("Ich bin Pete", pete.getId(), date, false);
            messageDataSource.createMessage("Ich bin Malte", pete.getId(), date, true);}
            messageDataSource.close();
            persons = personDataSource.getAllPersons();
            personDataSource.close();
        }
    }

    public int getCount(){
        return persons.size();
    }

    public void addItem(Person person){
        persons.add(person);
    }

    public void removeItem(int position){
        persons.remove(position);
    }

    public Person getItem(int position){
        return persons.get(position);
    }

    public long getItemId(int position) {
        return getItem(position).getId();
    }

    public View getView(int position, View convertView, ViewGroup parent){
        Person person = getItem(position);
        RelativeLayout view;

        view = (RelativeLayout) mInflater.inflate(R.layout.conversation_list_row, parent, false);

        ImageView iv_contactPic = (ImageView)view.findViewById(R.id.cl_contactPic);
        TextView tv_contactName = (TextView)view.findViewById(R.id.cl_contactName);
        TextView tv_lastMessage = (TextView)view.findViewById(R.id.cl_lastMessage);

        tv_contactName.setText(person.getNickname());

        messageDataSource.open();
        Message lm = messageDataSource.getLastMessageWithPerson(person.getId());
        messageDataSource.close();
        tv_lastMessage.setText(lm.getText());
        //TODO: assign real values
        iv_contactPic.setImageResource(R.drawable.ic_launcher);



        return view;
    }

}


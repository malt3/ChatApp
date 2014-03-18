package de.malte.chatapp.person;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import java.net.URI;

import de.malte.chatapp.message.Message;
import de.malte.chatapp.singletons.Singletons;

/**
 * Created by Malte on 04.03.14.
 */
public class Person {
    String nickname;
    String email;
    long id;
    //String picture;

    public Person(long id, String nickname, String email) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public String getNickname() {
        return nickname;
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public URI getContactURIByEmail(Context context){

        Uri uri = Uri.withAppendedPath(ContactsContract.CommonDataKinds.Email.CONTENT_LOOKUP_URI, Uri.encode(email));
        Cursor c = context.getContentResolver().query(uri,
                new String[]{ContactsContract.CommonDataKinds.Email.CONTACT_ID},
                null, null, null);
        c.moveToFirst();
        URI contactURI;
        if (c.getCount()>0){
            contactURI = URI.create(c.getString(0));
            return contactURI;
        }
        return null;


    }
    /*public URI getPhotoThumbnailURI(Context context){
        URI contactURI = getContactURIByEmail(context);
        // Sets the columns to retrieve for the user profile
        String[] mProjection = new String[]
                {
                        ContactsContract.Profile.PHOTO_THUMBNAIL_URI
                };

        String whereClause = ContactsContract.Profile._ID+"="+
        // Retrieves the profile from the Contacts Provider
        Cursor mProfileCursor =
                context.getContentResolver().query(
                        ContactsContract.Profile.CONTENT_URI,
                        mProjection ,
                        null,
                        null,
                        null);
        mProfileCursor.moveToFirst();
        URI pictureURI;
        if (mProfileCursor.getCount()>0){
            pictureURI = URI.create(mProfileCursor.getString(0));
            return pictureURI;
        }
        return null;
    }*/

}

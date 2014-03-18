package de.malte.chatapp.singletons;

import de.malte.chatapp.person.Person;

/**
 * Created by Malte on 05.03.14.
 */
public class Singletons {
        private static Singletons mInstance= null;

        public Person currentUser;

        protected Singletons(){}

        public static synchronized Singletons getInstance(){
            if(null == mInstance){
                mInstance = new Singletons();
            }
            return mInstance;
        }

        public void setCurrentUser(Person person){
            currentUser = person;
        }

        public Person getCurrentUser(){
            return currentUser;
        }
}

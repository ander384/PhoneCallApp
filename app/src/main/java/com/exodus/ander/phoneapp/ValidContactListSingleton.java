package com.exodus.ander.phoneapp;

//This is the class where each individual contact is saved
public class ValidContactListSingleton {

    //Will be useful for enabling user input of contacts
    private int numContacts = 2;

    private Contact[] ValidContactsArray = new Contact[2];
    private static final ValidContactListSingleton ourInstance = new ValidContactListSingleton();

    public static ValidContactListSingleton getInstance() {
        return ourInstance;
    }

    public Contact[] getValidContactsArray() {
        return ValidContactsArray;
    }

    private ValidContactListSingleton() {
        //For this simple usage, I am hardcoding my contacts
        //TODO: FIGURE OUT WHAT FILE PATH CAN BE CORRECTLY CONVERTED TO URI
        String filename = "android.resource://app/raw/hazardous_material_alarm.mp3";

        int message1 = R.raw.manin_confession;
        int message2 = R.raw.hazardous_material_alarm;



        Contact LudovicoManin = new Contact("Ludovico Manin", "6265550129", message1);
        Contact Police = new Contact("NYPD Crime Line", "7143343351", message2);

        ValidContactsArray[0] = LudovicoManin;
        ValidContactsArray[1] = Police;
    }

    //returns index of location in array if it is a contact.  Otherwise return -1 (Contact not found)
    int isValidNumber(String typedNumber){
        for(int i = 0; i<numContacts; ++i){
            Contact contact = ValidContactsArray[i];
            String number = contact.getContactNumber();
            if(typedNumber.equals(number)){
                return i;
            }
        }
        //if the contact is not found, return -1
        return -1;

    }

}

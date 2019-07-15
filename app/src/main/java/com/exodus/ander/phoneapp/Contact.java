package com.exodus.ander.phoneapp;

//Class for phone contacts.  Includes name to be displayed, number to be called, and name of message to be played
public class Contact {
    private String contactName;
    private String contactNumber;
    private int contactMessage;

    public Contact(){
        contactName = "Blank Contact";
        contactNumber = "0";
        contactMessage = -1;
    }

    public Contact(String contactName, String contactNumber, int contactMessage){
        this.contactName = contactName;
        this.contactNumber = contactNumber;
        this.contactMessage = contactMessage;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public int getContactMessage() {
        return contactMessage;
    }

    public void setContactMessage(int contactMessage) {
        this.contactMessage = contactMessage;
    }
}


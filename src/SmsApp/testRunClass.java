package SmsApp;

import PhoneBookApp.PhoneBookUtils.Contact;
import PhoneBookApp.PhoneBookUtils.setContact;

import java.util.Scanner;

public class testRunClass {
    public static void main(String[] args) {

        MessageCorrespondence correspondence = new MessageCorrespondence("Tuvia");

        correspondence.sendMessage("Hi Booki");
        correspondence.receiveMessage("Yes...");
        correspondence.sendMessage("Where is my food?");
        correspondence.printMessages();



        setContact validate = new setContact();
        Contact contact = validate.setContactFullInfo();
        System.out.println(contact.getFullName());
        System.out.println(contact.getCompanyName());
        System.out.println(contact.getPhoneNumber());

    }
}

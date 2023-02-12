package SmsApp;

import PhoneBookApp.PhoneBookUtils.Contact;
import PhoneBookApp.PhoneBookUtils.myPhoneBook;
import PhoneBookApp.PhoneBookUtils.setContact;


public class testRunClass {
    public static void main(String[] args) {

        setContact validate = new setContact();
        Contact contact = validate.setContactFullInfo();


        myPhoneBook phoneBook = new myPhoneBook();
        phoneBook.printContact(contact);


    }
}

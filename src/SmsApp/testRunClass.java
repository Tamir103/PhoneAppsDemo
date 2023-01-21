package SmsApp;

import PhoneBookApp.PhoneBookUtils.validations;

import java.util.Scanner;

public class testRunClass {
    public static void main(String[] args) {

        MessageCorrespondence correspondence = new MessageCorrespondence("Tuvia");

        correspondence.sendMessage("Hi Booki");
        correspondence.receiveMessage("Yes...");
        correspondence.sendMessage("Where is my food?");
        correspondence.printMessages();



        Scanner scan = new Scanner(System.in);
        String firstName = scan.nextLine();
        String middleName = scan.nextLine();
        String lastName = scan.nextLine();


    }
}

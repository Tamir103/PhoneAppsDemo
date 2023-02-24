package PhoneBookApp.PhoneBookUtils;

import SmsApp.MessageCorrespondence;

/**
 * Phone book contact object.
 * Contains first, middle, and last name, phone number, company name and a default message correspondence for each contact object
 */
public class Contact extends setContact {
    private String firstName;
    private String middleName;
    private String lastName;
    private String phoneNumber;
    private String companyName;

    private MessageCorrespondence correspondence = new MessageCorrespondence();

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setCompanyName(String companyName) {
            this.companyName = companyName;
    }

    public String getFirstName() {
        return this.firstName;
    }
    public String getMiddleName() { return this.middleName;}
    public String getLastName() { return this.lastName;}
    public String getCompanyName() {return this.companyName;}
    public String getFullName() {
        if (this.middleName != null) {
            return getFirstName() + " " + getMiddleName() + " " + getLastName();
        } else {
            return getFirstName() + " " + getLastName();
        }
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }
}

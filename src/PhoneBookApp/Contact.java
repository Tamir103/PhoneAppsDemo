package PhoneBookApp;

import java.io.Serializable;

/**
 * Phone book contact object.
 * Contains first, middle, and last name, phone number, company name and a default message correspondence for each contact object
 */
public class Contact extends setContact implements Serializable {
    private String firstName;
    private String middleName;
    private String lastName;
    private String phoneNumber;
    private String companyName;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) { return true;}
        if (obj == null || this.getClass() != obj.getClass()) {return false;}
        Contact c = (Contact) obj;
        try {
            if (this.getFullName().equals(c.getFullName()) && this.companyName.equals(c.getCompanyName())
                    && this.phoneNumber.equals(c.getPhoneNumber())) {return true;}
            else if (this.phoneNumber.equals(c.getPhoneNumber())) {return true;}
            else {return false;}
        } catch (NullPointerException npe) {
            return this.getFullName().equals(c.getFullName()) && this.phoneNumber.equals(c.getPhoneNumber());
        }
    }

}

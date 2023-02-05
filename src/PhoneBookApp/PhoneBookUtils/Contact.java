package PhoneBookApp.PhoneBookUtils;

import java.util.HashMap;

/**
 * Phone book contact object.
 * Contains first, middle, and last name, phone number, and company name for each contact object
 */
public class Contact {
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
    public HashMap<String,String> getFullContactInfo() {
        HashMap<String,String> contatcInfo = new HashMap<>();
        contatcInfo.put("firstName", this.firstName);
        if(this.middleName != null) { contatcInfo.put("middleName", this.middleName); }
        contatcInfo.put("lastName", this.lastName);
        contatcInfo.put("companyName", this.companyName);
        contatcInfo.put("phoneNumber", this.phoneNumber);

        return contatcInfo;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }
}

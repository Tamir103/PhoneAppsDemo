package PhoneBookApp;

public enum ContactVars {
    FIRST_NAME("First Name"),
    MIDDLE_NAME("Middle Name"),
    LAST_NAME("Last Name"),
    COMPANY_NAME("Company Name"),
    PHONE_NUMBER("Phone Number");

    final String strValue;
    ContactVars(String strValue) {
        this.strValue = strValue;
    }
}

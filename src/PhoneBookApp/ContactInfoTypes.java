package PhoneBookApp;

public enum ContactInfoTypes {
    NAME("name", "NAME", "Name"),
    PHONE("phone number", "PHONE NUMBER", "Phone Number"),
    COMPANY("company", "COMPANY", "Company");

    String lowerCase;
    String upperCase;
    String firstCapital;

    ContactInfoTypes(String lowerCase, String upperCase, String firstCapital) {
        this.lowerCase = lowerCase;
        this.upperCase = upperCase;
        this.firstCapital = firstCapital;
    }
}

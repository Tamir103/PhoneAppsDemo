package Main;

public enum PhoneApps {

    EXIT (0),
    PHONEBOOK(1),
    MESSAGES(2),
    CALENDER(3);

    final int serialNumber;

    PhoneApps(int serialNumber) {
        this.serialNumber = serialNumber;
    }
}

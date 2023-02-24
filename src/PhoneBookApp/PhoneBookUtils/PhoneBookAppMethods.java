package PhoneBookApp.PhoneBookUtils;

import java.util.Map;

public class PhoneBookAppMethods extends myPhoneBook {

    public void printMenu() {
        for (Map.Entry<Integer, String> entry : menu.entrySet()) {
            if (entry.getKey() < 10) {
                System.out.print(entry.getKey());
                printMenuDots(8);
                System.out.println(entry.getValue());
            } else if (entry.getKey() < 100) {
                System.out.print(entry.getKey());
                printMenuDots(7);
                System.out.println(entry.getValue());
            }
        }
        myPhoneBook.printTextsFromMap("menuInstructions");
    }

    public static void printMenuDots(int amount) {
        for (int i = 0; i < amount; i++) {
            System.out.print(".");
        }
    }
    public static void printFrame(int amount) {
        for (int i = 0; i < amount; i++) {
            System.out.print("~");
        }
        System.out.println();
    }

    /**
     * Getting user choice from menu and validate it
     *
     * @return int input or 0 if input invalid 3 times
     */
    public int menuChoice() {
        for (int i = 0; i < 3; i++) {
            String input = scan.nextLine();
            if (setContact.isNumbersOnly(input)) {
                int inputInt = Integer.parseInt(input);
                if (isItemNumValid(inputInt, menu)) {
                    return inputInt;
                }
            }
            printErrorMessages(calculateMessageIndex(i, true, true, true));
        }
        return 0;
    }
    public static boolean isItemNumValid(int num, Map map) {
        return num <= map.size() && num > 0;
    }

    public static boolean isListsSizesEquals(int listSize1, int listSize2) {
        return listSize1 == listSize2;
    }

    /**
     * Printing error text according to message index
     *
     * @param messageIndex Index of message to print
     *                     1 - Generic invalid input
     *                     2 - Name length restriction
     *                     3 - Phone format and length restriction
     *                     4 - Last input chance
     *                     5 - Name length + last chance
     *                     6 - Phone format + last chance
     *                     7 - Max tries
     *                     8 - Contact not in phone book
     *                     9 - Writing to file error
     *                     10 - Empty phone book list
     *                     11 - File format unsupported
     */
    public static void printErrorMessages(int messageIndex) {
        switch (messageIndex) {
            case 1 -> System.err.println(myPhoneBook.textsMap.get("invalidInputWarn"));
            case 2 -> System.err.println(myPhoneBook.textsMap.get("nameLengthWarn"));
            case 3 -> System.err.println(myPhoneBook.textsMap.get("phoneFormat"));
            case 4 -> System.err.println(myPhoneBook.textsMap.get("lastInvalidInputWarn") + " - " + myPhoneBook.textsMap.get("invalidInputWarn"));
            case 5 -> System.err.println(myPhoneBook.textsMap.get("lastInvalidInputWarn") + " - " + myPhoneBook.textsMap.get("nameLengthWarn"));
            case 6 -> System.err.println(myPhoneBook.textsMap.get("lastInvalidInputWarn") + " - " + myPhoneBook.textsMap.get("phoneFormat"));
            case 7 -> System.err.println(myPhoneBook.textsMap.get("inputErrMsg"));
            case 8 -> System.err.println(myPhoneBook.textsMap.get("contactNotExist"));
            case 9 -> System.err.println(myPhoneBook.textsMap.get("writeToFileErr"));
            case 10 -> System.err.println(myPhoneBook.textsMap.get("emptyList"));
            case 11 -> System.err.println(myPhoneBook.textsMap.get("unsupportedFile"));
        }
    }

    //TODO not a good method - replace if there is time
    public static int calculateMessageIndex(int i, boolean isGenericInputErrors, boolean isLengthValid, boolean isNameField) {
        if (isGenericInputErrors) {
            return genericInputErrors(i);
        } else if (i == 0) {
            if (isNameField) {
                if (isLengthValid) {
                    return 1;
                } else {
                    return 2;
                }
            } else {
                if (isLengthValid) {
                    return 1;
                } else {
                    return 3;
                }
            }
        } else if (i == 1) {
            if (isNameField) {
                if (isLengthValid) {
                    return 4;
                } else {
                    return 5;
                }
            } else {
                if (isLengthValid) {
                    return 4;
                } else {
                    return 6;
                }
            }
        } else {
            return 7;
        }
    }
    public static int genericInputErrors(int index) {
        int i = 0;
        switch (index) {
            case 0 -> i = 1;
            case 1 -> i = 4;
            case 2 -> i = 7;
        }
        return i;
    }

    /**
     * Accepts and validates Y or N input
     * @param startMessage - Which message to prompt the user with
     * @return - 1 for Y, 0 for N, 2 for invalid input
     */
    public static int enterAndValidateYorN(String startMessage) {
        myPhoneBook.printTextsFromMap(startMessage);
        for (int i = 0; i < 3; i++) {
            int errorMessage = PhoneBookAppMethods.calculateMessageIndex(i, true, true, true);
            try {
                String input = myPhoneBook.scan.nextLine();
                if (isYorN(input).equalsIgnoreCase("Y")) {
                    return 1;
                } else if (isYorN(input).equalsIgnoreCase("N")) {
                    return 0;
                }
            } catch (NullPointerException npe) {
                PhoneBookAppMethods.printErrorMessages(errorMessage);
            }
        }
        return 2;
    }

    public static String isYorN(String input) {
        if (input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("N")) {
            return input;
        }
        return null;
    }
}

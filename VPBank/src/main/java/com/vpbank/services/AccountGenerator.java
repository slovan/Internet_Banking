/**
 * 
 */
package com.vpbank.services;

import java.util.Date;

import com.vpbank.models.Client;

/**
 * Class for generation account numbers
 * @author Volodymyr Ponomarenko
 * @version 1.0
 */
public class AccountGenerator {

    /**
     * Generate bank account number (basic)
     * 
     * According to the National Bank of Slovakia, to validate the basic
     * part of the account number, the digits are multiplied by their 
     * respective weights (6,3,7,9,10,5,8,4,2,1), added up and divided by 11.
     * The remainder must be zero.
     * 
     * @param c instance of client - owner of the account will be created
     * @return account number (basic)
     */
    public static long genAccountNumberBasic(Client c) {
        int firstNameLength = c.getFirstName().length(); // take first name
                                                         // length
        int lastNameLength = c.getLastName().length(); // take last name length
        int id = c.getId(); // take id number
        long birthdayNum = c.getDob().getTime(); // take date of birth in number
        long todayNum = new Date().getTime(); // take current date in number

        long accountNumberBasic = ((todayNum - birthdayNum) / id) % 10000000000L; // initialization
        accountNumberBasic = (accountNumberBasic * firstNameLength * lastNameLength) % 10000000000L;

        byte[] accountNumArray = new byte[10]; // because 10 digits of account

        // get string representation of account
        String accountNumStr = String.valueOf(accountNumberBasic);
        while (accountNumStr.length() < 10) {
            accountNumStr = "0" + accountNumStr;
        }

        // get digits from the number to array
        for (int i = 0; i < accountNumArray.length; i++) {
            accountNumArray[i] = Byte.parseByte(String.valueOf(accountNumStr.charAt(i)));
        }

        byte[] weights = { 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 };

        // check remainder
        int remainder = 0;
        for (int i = 0; i < accountNumArray.length; i++) {
            remainder += accountNumArray[i] * weights[i];
        }
        remainder %= 11;
        if ((remainder == 0) && (accountNumberBasic != 0)) {
            return accountNumberBasic;
        } else {
            // we have to get reminder = 0, use loop for this
            do {
                switch (remainder) {
                    case 1:
                        // increase with 10, because of value of weight
                        accountNumArray[4] = (byte)(++accountNumArray[4] % 10);
                        break;
                    case 2:
                        accountNumArray[3] = (byte)(++accountNumArray[3] % 10);
                        break;// increase with 9
                    case 3:
                        accountNumArray[6] = (byte)(++accountNumArray[6] % 10);
                        break;// increase with 8
                    case 4:
                        accountNumArray[2] = (byte)(++accountNumArray[2] % 10);
                        break;// increase with 7
                    case 5:
                        accountNumArray[0] = (byte)(++accountNumArray[0] % 10);
                        break;// increase with 6
                    case 6:
                        accountNumArray[5] = (byte)(++accountNumArray[5] % 10);
                        break;// increase with 5
                    case 7:
                        accountNumArray[7] = (byte)(++accountNumArray[7] % 10);
                        break;// increase with 4
                    case 8:
                        accountNumArray[1] = (byte)(++accountNumArray[1] % 10);
                        break;// increase with 3
                    case 9:
                        accountNumArray[8] = (byte)(++accountNumArray[8] % 10);
                        break;// increase with 2
                    case 10:
                        accountNumArray[9] = (byte)(++accountNumArray[9] % 10);
                        break;// increase with 1
                    default:
                        accountNumArray[0] = 1;// in case of (accountNumberBasic == 0)
                }
                remainder = 0;
                for (int i = 0; i < accountNumArray.length; i++) {
                    remainder += accountNumArray[i] * weights[i];
                }
                remainder %= 11;
            } while (remainder != 0);

            // take correct accountNumberBasic from array of byte
            accountNumberBasic = 0;
            for (int i = accountNumArray.length - 1; i >= 0; i--) {
                accountNumberBasic += accountNumArray[i] * Math.pow(10, (9 - i));
            }
            return accountNumberBasic;
        }
    }


    /**
     * Generate bank account number (prefix)
     * 
     * According to the National Bank of Slovakia, to validate the prefix part
     * of the account number, the digits are multiplied by their respective 
     * weights (10,5,8,4,2,1), added up and divided by 11.
     * The remainder must be zero.
     * 
     * @param start set initialization value for account number generator
     * @return account number (prefix)
     */
    public static int genAccountNumberPrefix(int start) {

        int accountNumberPrefix = start + 1;

        byte[] accountNumArray = new byte[6]; // because 6 digits of account

        // get string representation of account
        String accountNumStr = String.valueOf(accountNumberPrefix);
        while (accountNumStr.length() < 6) {
            accountNumStr = "0" + accountNumStr;
        }
        // get digits from the number to array
        for (int i = 0; i < accountNumArray.length; i++) {
            accountNumArray[i] = Byte.parseByte(String.valueOf(accountNumStr.charAt(i)));
        }

        byte[] weights = { 10, 5, 8, 4, 2, 1 };

        // check remainder
        int remainder = 0;
        for (int i = 0; i < accountNumArray.length; i++) {
            remainder += accountNumArray[i] * weights[i];
        }
        remainder %= 11;
        if (remainder == 0) {
            return accountNumberPrefix;
        } else {
            // we have to get reminder = 0, use loop for this
            do {
                switch (remainder) {
                    case 1:
                        accountNumArray[0] = (byte)(++accountNumArray[0] % 10);
                        break;// increase with 10
                    case 2: {
                        // increase with 9
                        accountNumArray[1] = (byte)(++accountNumArray[1] % 10);// with 5
                        accountNumArray[3] = (byte)(++accountNumArray[3] % 10);// with 4
                        break;
                    }
                    case 3:
                        accountNumArray[2] = (byte)(++accountNumArray[2] % 10);
                        break;// increase with 8
                    case 4: {
                        // increase with 7
                        accountNumArray[1] = (byte)(++accountNumArray[1] % 10);// with 5
                        accountNumArray[4] = (byte)(++accountNumArray[4] % 10);// with 2
                        break;
                    }
                    case 5: {
                        // increase with 6
                        accountNumArray[3] = (byte)(++accountNumArray[3] % 10);// with 4
                        accountNumArray[4] = (byte)(++accountNumArray[4] % 10);// with 2
                        break;
                    }
                    case 6:
                        accountNumArray[1] = (byte)(++accountNumArray[1] % 10);
                        break;// increase with 5
                    case 7:
                        accountNumArray[3] = (byte)(++accountNumArray[3] % 10);
                        break;// increase with 4
                    case 8: {
                        // increase with 3
                        accountNumArray[5] = (byte)(++accountNumArray[5] % 10);// with 1
                        accountNumArray[4] = (byte)(++accountNumArray[4] % 10);// with 2
                        break;
                    }
                    case 9:
                        accountNumArray[4] = (byte)(++accountNumArray[4] % 10);
                        break;// increase with 2
                    case 10:
                        accountNumArray[5] = (byte)(++accountNumArray[5] % 10);
                        break;// increase with 1
                }
                remainder = 0;
                for (int i = 0; i < accountNumArray.length; i++) {
                    remainder += accountNumArray[i] * weights[i];
                }
                remainder %= 11;
            } while (remainder != 0);

            // take correct accountNumberPrefix from array of byte
            accountNumberPrefix = 0;
            for (int i = accountNumArray.length - 1; i >= 0; i--) {
                accountNumberPrefix += accountNumArray[i] * Math.pow(10, (6 - i - 1));
            }
            return accountNumberPrefix;
        }
    }


    /**
     * Generate IBAN form of the account number
     * 
     * @param accountNumberBasic bank account number (basic)
     * @param accountNumberPrefix bank account number (prefix)
     * @param bankCode bank national code
     * @param countryCode code of the country (e.g. SK for Slovakia)
     * @return account number in IBAN format
     */
    public static String genAccountNumberIBAN(long accountNumberBasic, int accountNumberPrefix, short bankCode,
            String countryCode) {
        // convert country code to a number form and write it accountNumberIBAN
        // variable
        String accountNumberIBAN = "";
        for (int i = 0; i < countryCode.length(); i++) {
            // Replace each letter in the string with two digits,
            // thereby expanding the string, where A = 10, B = 11, ..., Z = 35
            accountNumberIBAN = accountNumberIBAN + ((countryCode.charAt(i) - 'A') + 10);
        }
        // add 2 zeros at the end of the string ("check digits")
        accountNumberIBAN = accountNumberIBAN + "00";

        // add account basic number to IBAN row
        // at first, it needs exactly 10 symbols and we have to provide it
        String strBasic = Long.toString(accountNumberBasic);
        while (strBasic.length() < 10) {
            strBasic = "0" + strBasic;
        }
        accountNumberIBAN = strBasic + accountNumberIBAN;

        // add account prefix number to IBAN row
        // at first, it needs exactly 6 symbols and we have to provide it
        String strPrefix = Integer.toString(accountNumberPrefix);
        while (strPrefix.length() < 6) {
            strPrefix = "0" + strPrefix;
        }
        accountNumberIBAN = strPrefix + accountNumberIBAN;

        // add bank code to IBAN row
        // at first, it needs exactly 4 symbols and we have to provide it
        String strBankCode = Short.toString(bankCode);
        while (strBankCode.length() < 4) {
            strBankCode = "0" + strBankCode;
        }
        accountNumberIBAN = strBankCode + accountNumberIBAN;

        // compute the remainder of number in accountNumberIBAN on division by
        // 97
        // this number is bigger than Long type can contain, so divide it to 2
        // parts
        // first (last 18 symbols)
        long first = Long
                .parseLong(accountNumberIBAN.substring(accountNumberIBAN.length() - 18, accountNumberIBAN.length()));
        // second
        long second = Long.parseLong(accountNumberIBAN.substring(0, accountNumberIBAN.length() - 18));
        // let's compute remainder
        int remainder = (int)(first % 97 + (second * (Math.pow(10, 18) % 97)) % 97) % 97;

        // compute check digits
        String checkDigits = Integer.toString(98 - remainder);
        // must consists of 2 symbols
        if (checkDigits.length() == 1) {
            checkDigits = "0" + checkDigits;
        }

        // now let's form correct accountNumberIBAN
        accountNumberIBAN = countryCode + checkDigits + strBankCode + strPrefix + strBasic;

        return accountNumberIBAN;
    }
}

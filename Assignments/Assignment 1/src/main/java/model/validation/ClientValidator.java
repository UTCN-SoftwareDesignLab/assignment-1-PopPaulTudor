package model.validation;

import model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientValidator {

    private static final int MIN_NAME_LENGTH = 3;
    private static final int MIN_ADDRESS = 10;
    private static final int MIN_PASSWORD_LENGTH = 8;

    private final List<String> errors;
    private final Client client;

    public ClientValidator(Client client) {
        errors = new ArrayList<>();
        this.client = client;
    }

    public List<String> getErrors() {
        return errors;
    }

    public boolean validate() {
        validateAddress(client.getAddress());
        validateName(client.getName());
        validatePassword(client.getPassword());

        return errors.isEmpty();
    }

    private void validateName(String name) {
        if (name.length() < MIN_NAME_LENGTH) {
            errors.add("Name is too short");
        }
    }

    private void validateAddress(String address) {

        if (address.length() < MIN_ADDRESS) {
            errors.add("Address too short");
        }
        if (!(address.toLowerCase(Locale.ROOT).contains("street") ||
                address.toLowerCase(Locale.ROOT).contains("str."))) {
            errors.add("please add the street");
        }

        if (!(address.toLowerCase(Locale.ROOT).contains("no.") ||
                address.toLowerCase(Locale.ROOT).contains("nr.") ||
                address.toLowerCase(Locale.ROOT).contains("number"))) {
            errors.add("please add the number of the street");
        }

    }


    private void validatePassword(String password) {
        if (password.length() < MIN_PASSWORD_LENGTH) {
            errors.add("Password too short!");
        }
        if (!containsSpecialCharacter(password)) {
            errors.add("Password must contain at least one special character!");
        }
        if (!containsDigit(password)) {
            errors.add("Password must contain at least one number!");
        }
    }


    private boolean containsSpecialCharacter(String s) {
        if (s == null || s.trim().isEmpty()) {
            return false;
        }
        Pattern p = Pattern.compile("[^A-Za-z0-9]");
        Matcher m = p.matcher(s);
        return m.find();
    }

    private boolean containsDigit(String s) {
        if (s != null && !s.isEmpty()) {
            for (char c : s.toCharArray()) {
                if (Character.isDigit(c)) {
                    return true;
                }
            }
        }
        return false;
    }


}

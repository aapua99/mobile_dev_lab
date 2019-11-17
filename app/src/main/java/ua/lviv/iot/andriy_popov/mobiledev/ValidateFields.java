package ua.lviv.iot.andriy_popov.mobiledev;

import android.provider.Settings;
import android.util.Patterns;
import android.widget.EditText;


public class ValidateFields {

    private static final String ERROR_EMAIL = "Error email";
    private static final String ERROR_NAME = "Error name";
    private static final String ERROR_PASS = "Error password";
    private static final String ERROR_PHONE = "Error phone";
    private static final int MIN_LENGTH_PASS = 8;

    public static boolean validateEmail(EditText emailEdit) {
        if (!Patterns.EMAIL_ADDRESS.matcher(emailEdit.getText().toString()).matches()) {
            emailEdit.setError(ERROR_EMAIL);
            return false;
        } else {
            emailEdit.setError(null);
        }
        return true;
    }

    public static boolean validatePass(EditText passEdit) {
        if (passEdit.getText().toString().length() < MIN_LENGTH_PASS) {
            passEdit.setError("Error pass");
            return false;
        } else {
            passEdit.setError(null);
        }
        return true;
    }

    public static boolean validatePhone(EditText phoneEdit) {
        if (!Patterns.PHONE.matcher(phoneEdit.getText().toString()).matches()) {
            phoneEdit.setError("Error phone");
            return false;
        } else {
            phoneEdit.setError(null);
        }
        return true;
    }

    public static boolean validateName(EditText nameEdit) {
        if (nameEdit.getText().toString().isEmpty()) {
            nameEdit.setError("Error name");
            return false;
        } else {
            nameEdit.setError(null);
        }
        return true;
    }
}

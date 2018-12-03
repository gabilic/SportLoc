package hr.foi.air.sportloc.view.util;

import android.widget.EditText;

public final class DataInputValidator {
    private DataInputValidator() {
    }

    private static Boolean validateEmptyField(EditText editText, String emptyFieldErrorMessage) {
        if (editText.getText().toString().isEmpty()) {
            editText.setError(emptyFieldErrorMessage);
            return false;
        }
        return true;
    }

    private static Boolean validateMinimumLength(EditText editText, int minCharLength, String minLengthErrorMessage) {
        if (editText.getText().toString().length() < minCharLength) {
            editText.setError(minLengthErrorMessage);
            return false;
        }
        return true;
    }

    public static Boolean validateInput(EditText editText, String emptyFieldErrorMessage, int minCharLength, String minLengthErrorMessage) {
        if (validateEmptyField(editText, emptyFieldErrorMessage)) {
            return validateMinimumLength(editText, minCharLength, minLengthErrorMessage);
        }
        return false;
    }
}

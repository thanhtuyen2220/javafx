package sample.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VnPhoneValidator {
    // digit + lowercase char + uppercase char + punctuation + symbol
    private static final String PHONE_PATTERN =
            "(09|03|07|08|05)+([0-9]{8})\b";

    private static final Pattern pattern = Pattern.compile(PHONE_PATTERN);

    public static boolean isValid(final String phone) {
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }
}

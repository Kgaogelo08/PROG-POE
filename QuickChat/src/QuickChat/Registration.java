package quickchat;

public class Registration {
    public static boolean checkUserName(String username) {
        return username != null && username.contains("_") && username.length() <= 5;
    }
    public static boolean checkPasswordComplexity(String password) {
        if (password == null || password.length() < 8) return false;
        boolean cap = false, num = false, spec = false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) cap = true;
            if (Character.isDigit(c)) num = true;
            if (!Character.isLetterOrDigit(c)) spec = true;
        }
        return cap && num && spec;
    }
    public static boolean checkCellPhoneNumber(String cell) {
        return cell != null && cell.length() == 12 && cell.startsWith("+27") && cell.substring(1).matches("\\d+");
    }
    public static String registerUser(String username, String password, String cell) {
        if (!checkUserName(username)) return "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.";
        if (!checkPasswordComplexity(password)) return "Password is not correctly formatted, please ensure that the password contains at least eight characters, a capital letter, a number and a special character.";
        if (!checkCellPhoneNumber(cell)) return "Cell phone number incorrectly formatted or does not contain international code.";
        return "Username, password and cell phone number successfully captured.";
    }
}
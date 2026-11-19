package quickchat;

public class Login {
    private String storedUsername;
    private String storedPassword;
    private String firstName;
    private String lastName;

    public void storeUser(String username, String password, String firstName, String lastName) {
        this.storedUsername = username;
        this.storedPassword = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    public boolean loginUser(String username, String password) {
        return username != null && username.equals(storedUsername) && password != null && password.equals(storedPassword);
    }
    public String returnLoginStatus(String username, String password) {
        if (loginUser(username, password)) return "Welcome " + firstName + " " + lastName + ", it is great to see you again.";
        else return "Username or password incorrect, please try again.";
    }
}

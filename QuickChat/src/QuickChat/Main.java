package quickchat;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        String firstName = JOptionPane.showInputDialog("Enter First Name:");
        String lastName = JOptionPane.showInputDialog("Enter Last Name:");
        String username = JOptionPane.showInputDialog("Enter Username (must contain _ and <=5 chars):");
        String password = JOptionPane.showInputDialog("Enter Password (8+ chars, capital, number, special):");
        String cell = JOptionPane.showInputDialog("Enter Cell (+27... e.g. +27834557896):");
        String regResult = Registration.registerUser(username, password, cell);
        JOptionPane.showMessageDialog(null, regResult);
        if (!regResult.contains("successfully")) return;

        Login login = new Login();
        login.storeUser(username, password, firstName, lastName);
        String loginUser = JOptionPane.showInputDialog("LOGIN - Enter Username:");
        String loginPass = JOptionPane.showInputDialog("LOGIN - Enter Password:");
        JOptionPane.showMessageDialog(null, login.returnLoginStatus(loginUser, loginPass));
        if (!login.loginUser(loginUser, loginPass)) return;

        Massage.populateTestData();
        Massage.saveStoredToJSON();
        Massage.loadStoredFromJSON();

        while (true) {
            String choice = JOptionPane.showInputDialog("Welcome to QuickChat\n1) Send Messages\n2) Show recently sent messages\n3) Quit\n4) Stored Messages\nChoose an option:");
            if (choice == null || choice.equals("3")) {
                JOptionPane.showMessageDialog(null, "Total messages sent: " + Massage.returnTotalMessages());
                break;
            }
            switch (choice) {
                case "1": sendMassagesFlow(); break;
                case "2": JOptionPane.showMessageDialog(null, Massage.printMassages()); break;
                case "4": storedMenu(); break;
                default: JOptionPane.showMessageDialog(null, "Invalid option");
            }
        }
    }

    public static void sendMassagesFlow() {
        String numStr = JOptionPane.showInputDialog("How many messages do you wish to send?");
        int num = Integer.parseInt(numStr);
        for (int i = 0; i < num; i++) {
            String recipient = JOptionPane.showInputDialog("Enter recipient number:");
            if (!Massage.checkRecipientCell(recipient)) {
                JOptionPane.showMessageDialog(null, "Cell phone number is incorrectly formatted or does not contain an international code.");
                i--; continue;
            }
            String msg = JOptionPane.showInputDialog("Enter message (max 250 chars):");
            if (!Massage.checkMassageLength(msg)) {
                JOptionPane.showMessageDialog(null, "Please enter a message of less than 250 characters.");
                i--; continue;
            }
            String option = JOptionPane.showInputDialog("1) Send Message\n2) Disregard Message\n3) Store Message to send later");
            String flag = "Sent";
            if (option.equals("2")) flag = "Disregard";
            if (option.equals("3")) flag = "Stored";
            String hash = Massage.addMassage(recipient, msg, flag);
            Massage.saveStoredToJSON();
            JOptionPane.showMessageDialog(null, "Message Hash: " + hash + "\nRecipient: " + recipient + "\nMessage: " + msg);
        }
    }

    public static void storedMenu() {
        while (true) {
            String opt = JOptionPane.showInputDialog("Stored Messages\na) Display sender and recipient\nb) Display longest stored message\nc) Search for a Message ID\nd) Search for all messages for a recipient\ne) Delete a message using Message Hash\nf) Display report\ng) Back\nChoose:");
            if (opt == null || opt.equalsIgnoreCase("g")) break;
            switch (opt.toLowerCase()) {
                case "a": JOptionPane.showMessageDialog(null, Massage.displaySenderAndRecipient()); break;
                case "b": JOptionPane.showMessageDialog(null, "Longest Stored Message:\n" + Massage.displayLongestMassage()); break;
                case "c":
                    String id = JOptionPane.showInputDialog("Enter Message ID:");
                    JOptionPane.showMessageDialog(null, Massage.searchByMassageID(id)); break;
                case "d":
                    String rec = JOptionPane.showInputDialog("Enter recipient (e.g. +27838884567):");
                    JOptionPane.showMessageDialog(null, Massage.searchByRecipient(rec)); break;
                case "e":
                    String hash = JOptionPane.showInputDialog("Enter Message Hash to delete:");
                    JOptionPane.showMessageDialog(null, Massage.deleteByHash(hash)); break;
                case "f": JOptionPane.showMessageDialog(null, Massage.displayReport()); break;
            }
        }
    }
}

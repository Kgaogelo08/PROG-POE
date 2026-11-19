package quickchat;

import java.util.*;
import java.nio.file.*;

public class Massage {

    // PART 3 ARRAYS
    public static ArrayList<String> sentMassages = new ArrayList<>();
    public static ArrayList<String> disregardedMassages = new ArrayList<>();
    public static ArrayList<String> storedMassages = new ArrayList<>();
    public static ArrayList<String> massageHashes = new ArrayList<>();
    public static ArrayList<String> massageIDs = new ArrayList<>();
    public static ArrayList<String> sentRecipients = new ArrayList<>();
    public static ArrayList<String> storedRecipients = new ArrayList<>();
    public static ArrayList<String> disregardedRecipients = new ArrayList<>();
    public static ArrayList<String> storedHashes = new ArrayList<>();
    public static ArrayList<String> storedIDs = new ArrayList<>();
    public static ArrayList<String> sentHashes = new ArrayList<>();
    public static ArrayList<String> sentIDs = new ArrayList<>();
    private static int massageCounter = 0;
    private static Random rand = new Random();

    // PART 2
    public static boolean checkRecipientCell(String cell) {
        return cell != null && cell.length() <= 13 && (cell.startsWith("+") || cell.startsWith("0"));
    }
    public static boolean checkMassageLength(String massage) {
        return massage != null && massage.length() <= 250;
    }
    public static String createMassageID() {
        String id = "";
        for (int i = 0; i < 10; i++) id += rand.nextInt(10);
        return id;
    }
    public static String createMassageHash(String massageID, int msgNum, String massage) {
        String[] words = massage.trim().split("\\s+");
        String first = words[0].replaceAll("[^A-Za-z]", "").toUpperCase();
        String last = words[words.length - 1].replaceAll("[^A-Za-z]", "").toUpperCase();
        return massageID.substring(0, 2) + ":" + msgNum + ":" + first + " " + last;
    }

    // PART 3 - 5 TEST MASSAGES
    public static void populateTestData() {
        sentMassages.clear(); disregardedMassages.clear(); storedMassages.clear();
        massageHashes.clear(); massageIDs.clear();
        sentRecipients.clear(); storedRecipients.clear(); disregardedRecipients.clear();
        storedHashes.clear(); storedIDs.clear(); sentHashes.clear(); sentIDs.clear();
        massageCounter = 0;
        addMassage("+27834557896", "Did you get the cake?", "Sent");
        addMassage("+27838884567", "Where are you? You are late! I have asked you to be on time.", "Stored");
        addMassage("+27834484567", "Yohoooo, I am at your gate.", "Disregard");
        addMassage("08388884567", "It is dinner time !", "Sent");
        addMassage("+27838884567", "OK, I am leaving without you.", "Stored");
    }

    public static String addMassage(String recipient, String massage, String flag) {
        massageCounter++;
        String id = createMassageID();
        String hash = createMassageHash(id, massageCounter, massage);
        massageIDs.add(id);
        massageHashes.add(hash);
        if (flag.equalsIgnoreCase("Sent")) {
            sentMassages.add(massage); sentRecipients.add(recipient); sentHashes.add(hash); sentIDs.add(id);
        } else if (flag.equalsIgnoreCase("Stored")) {
            storedMassages.add(massage); storedRecipients.add(recipient); storedHashes.add(hash); storedIDs.add(id);
        } else if (flag.equalsIgnoreCase("Disregard")) {
            disregardedMassages.add(massage); disregardedRecipients.add(recipient);
        }
        return hash;
    }

   
    // This method creates the file storedMassages.json
    public static void saveStoredToJSON() {
        try {
            StringBuilder json = new StringBuilder();
            json.append("[\n");
            for (int i = 0; i < storedMassages.size(); i++) {
                json.append("  {\"recipient\":\"").append(storedRecipients.get(i)).append("\",");
                json.append("\"massage\":\"").append(storedMassages.get(i).replace("\"", "'")).append("\",");
                json.append("\"hash\":\"").append(storedHashes.get(i)).append("\",");
                json.append("\"id\":\"").append(storedIDs.get(i)).append("\"}");
                if (i < storedMassages.size() - 1) json.append(",");
                json.append("\n");
            }
            json.append("]");
            Files.write(Paths.get("storedMassages.json"), json.toString().getBytes());
        } catch (Exception e) {
            System.out.println("Error saving JSON: " + e.getMessage());
        }
    }

    // This method reads the file storedMassages.json back
    public static void loadStoredFromJSON() {
        try {
            if (!Files.exists(Paths.get("storedMassages.json"))) return;
            String content = new String(Files.readAllBytes(Paths.get("storedMassages.json")));
            if (content.trim().isEmpty() || content.trim().equals("[]")) return;
            storedMassages.clear(); storedRecipients.clear(); storedHashes.clear(); storedIDs.clear();
            String[] parts = content.split("\\{");
            for (String p : parts) {
                if (!p.contains("recipient")) continue;
                String rec = p.split("\"recipient\":\"")[1].split("\"")[0];
                String msg = p.split("\"massage\":\"")[1].split("\",\"hash\"")[0];
                String hash = p.split("\"hash\":\"")[1].split("\"")[0];
                String id = p.split("\"id\":\"")[1].split("\"")[0];
                storedRecipients.add(rec);
                storedMassages.add(msg);
                storedHashes.add(hash);
                storedIDs.add(id);
            }
        } catch (Exception e) {
            System.out.println("Error loading JSON: " + e.getMessage());
        }
    }

    // PART 3 a-f
    public static String displaySenderAndRecipient() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < storedMassages.size(); i++) {
            sb.append("Sender: Developer | Recipient: ").append(storedRecipients.get(i))
              .append(" | Massage: ").append(storedMassages.get(i)).append("\n");
        }
        return sb.toString();
    }
    public static String displayLongestMassage() {
        if (storedMassages.isEmpty()) return "No stored massages.";
        String longest = storedMassages.get(0);
        for (String m : storedMassages) if (m.length() > longest.length()) longest = m;
        return longest;
    }
    public static String searchByMassageID(String id) {
        for (int i = 0; i < sentIDs.size(); i++) if (sentIDs.get(i).equals(id)) return "Recipient: " + sentRecipients.get(i) + "\nMessage: " + sentMassages.get(i);
        for (int i = 0; i < storedIDs.size(); i++) if (storedIDs.get(i).equals(id)) return "Recipient: " + storedRecipients.get(i) + "\nMessage: " + storedMassages.get(i);
        return "Massage ID not found.";
    }
    public static String searchByRecipient(String recipient) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sentRecipients.size(); i++) if (sentRecipients.get(i).equals(recipient)) sb.append(sentMassages.get(i)).append("\n");
        for (int i = 0; i < storedRecipients.size(); i++) if (storedRecipients.get(i).equals(recipient)) sb.append(storedMassages.get(i)).append("\n");
        if (sb.length() == 0) return "No messages found for " + recipient;
        return sb.toString();
    }
    public static String deleteByHash(String hash) {
        for (int i = 0; i < storedHashes.size(); i++) {
            if (storedHashes.get(i).equals(hash)) {
                String msg = storedMassages.get(i);
                storedMassages.remove(i); storedRecipients.remove(i); storedHashes.remove(i); storedIDs.remove(i);
                massageHashes.remove(hash);
                saveStoredToJSON();
                return "Message \"" + msg + "\" successfully deleted.";
            }
        }
        return "Message hash not found.";
    }
    public static String displayReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== STORED MESSAGES REPORT ===\n");
        for (int i = 0; i < storedMassages.size(); i++) {
            sb.append("Message Hash: ").append(storedHashes.get(i)).append("\n");
            sb.append("Recipient: ").append(storedRecipients.get(i)).append("\n");
            sb.append("Message: ").append(storedMassages.get(i)).append("\n-------------------------\n");
        }
        sb.append("=== SENT MESSAGES ===\n");
        for (int i = 0; i < sentMassages.size(); i++) {
            sb.append("Message Hash: ").append(sentHashes.get(i)).append("\n");
            sb.append("Recipient: ").append(sentRecipients.get(i)).append("\n");
            sb.append("Message: ").append(sentMassages.get(i)).append("\n-------------------------\n");
        }
        return sb.toString();
    }
    public static String printMassages() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sentMassages.size(); i++) {
            sb.append("Message ").append(i + 1).append("\nHash: ").append(sentHashes.get(i)).append("\nRecipient: ").append(sentRecipients.get(i)).append("\nMessage: ").append(sentMassages.get(i)).append("\n\n");
        }
        return sb.toString();
    }
    public static int returnTotalMessages() { return sentMassages.size(); }
}
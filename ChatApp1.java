 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.chatapp1;
import javax.swing.JOptionPane;
import java.util.*;
import java.io.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


/**
 *
 * @author RC_Student_lab
 */
public class ChatApp1 {
    private static int totalMessage = 0;
    private static int messageCounter = 0;
    private static JSONArray messageStorage = new JSONArray();
    private static List<JSONObject>sentMessages= new ArrayList<>();
    
    private static boolean exit;

    public static void main(String[] args) {
        if (!login()) return;

        JOptionPane.showMessageDialog(null, "Welcome to we communicate.");

        int maxMessages = 0;
        while (true) {
            String input = JOptionPane.showInputDialog("How many messages would you like to enter?");
            try {
                maxMessages = Integer.parseInt(input);
                if (maxMessages > 0) break;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid positive number.");
            }
        }

        exit = false;
        while (!exit) {
            String[] options = {
                "Send Message", "Show Recently Sent Messages",
                "Display all sender & Recipients", "Search Message by Recipient",
                "Delete Message by Hash", "Full Message Report", "Quit"
            };

            int choice = JOptionPane.showOptionDialog(
                null, "Choose an option", "We Communicate Menu",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, options, options[0]);

            switch (choice) {
                case 0: // Send Message
                    if (messageCounter < maxMessages) {
                        sendMessage();
                    } else {
                        JOptionPane.showMessageDialog(null, "Message limit reached.");
                    }
                    break;

                case 1: // Show Recently Sent Messages
                    displayFullReport();
                    break;

                case 2: // Display All Senders & Recipients
                    displaySendersAndRecipients();
                    break;

                case 3: // Search by Recipient
                    searchByRecipient();
                    break;

                case 4: // Delete by Hash
                    deleteByMessageHash();
                    break;

                case 5: // Full Report
                    displayFullReport();
                    break;

                case 6: // Quit
                    exit = true;
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Invalid selection.");
                    break;
            }
        }

        JOptionPane.showMessageDialog(null, "Total messages sent: " + totalMessage);
        saveMessagesToJSON();
    }

    static boolean login() {
        String user = JOptionPane.showInputDialog("Enter username:");
        String pass = JOptionPane.showInputDialog("Enter password:");

        if ("Sphelele".equals(user) && "Thabekhulu5@".equals(pass)) {
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Login failed.");
            return false;
        }
    }

    static void sendMessage() {
        long messageId = 1000000000L + new Random().nextInt(900000000);
        messageCounter++;

        String recipient = JOptionPane.showInputDialog("Enter recipient number (+CCXXXXXXXXXX):");
        if (recipient == null || !recipient.matches("\\+\\d{9,12}")) {
            JOptionPane.showMessageDialog(null, "Invalid number. Must include international code and be <= 12 digits.");
            return;
        }

        String message = JOptionPane.showInputDialog("Enter your message (max 500 chars):");
        if (message == null || message.length() > 500) {
            JOptionPane.showMessageDialog(null, "Please enter a message of less than 500 characters.");
            return;
        }

        String[] words = message.trim().split("\\s+");
        String hash = String.format("802d:%d:%s",
            Long.parseLong(Long.toString(messageId).substring(0, 2)),
            words.length > 1 ? words[words.length - 1].toUpperCase() : "");

        String[] actions = { "Send", "Disregard", "Store" };
        int action = JOptionPane.showOptionDialog(
            null, "Choose what to do with this message:", "Message Options",
            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
            null, actions, actions[0]);

        if (action == 1) {
            JOptionPane.showMessageDialog(null, "Message disregarded.");
            return;
        }

        JSONObject jsonMessage = new JSONObject();
        jsonMessage.put("MessageID", "" + messageId);
        jsonMessage.put("MessageHash", hash);
        jsonMessage.put("Recipient", recipient);
        jsonMessage.put("Message", message);
        jsonMessage.put("Sender", "User");

        if (action == 2) {
            messageStorage.add(jsonMessage);
            JOptionPane.showMessageDialog(null, "Message stored.");
            return;
        }

        sentMessages.add(jsonMessage);
        totalMessage++;

        JOptionPane.showMessageDialog(null,
            "Message Sent!\n" +
            "Message ID: " + messageId + "\n" +
            "Recipient: " + recipient + "\n" +
            "Message: " + message);
    }

    static void displaySendersAndRecipients() {
        StringBuilder sb = new StringBuilder("Senders and Recipients:\n");
        for (JSONObject msg : sentMessages) {
            sb.append("Sender: ").append(msg.get("Sender"))
              .append(" --- Recipient: ").append(msg.get("Recipient"))
              .append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    static void searchByRecipient() {
        String input = JOptionPane.showInputDialog("Enter recipient number to search:");
        StringBuilder sb = new StringBuilder("Messages for " + input + ":\n");
        boolean found = false;

        for (JSONObject msg : sentMessages) {
            if (msg.get("Recipient").toString().equals(input)) {
                found = true;
                sb.append("ID: ").append(msg.get("MessageID"))
                  .append("\nMessage: ").append(msg.get("Message"))
                  .append("\n\n");
            }
        }

        JOptionPane.showMessageDialog(null,
            found ? sb.toString() : "No messages found for recipient.");
    }

    static void deleteByMessageHash() {
        String input = JOptionPane.showInputDialog("Enter message hash to delete:");
        Iterator<JSONObject> iterator = sentMessages.iterator();

        while (iterator.hasNext()) {
            JSONObject msg = iterator.next();
            if (msg.get("MessageHash").toString().equals(input)) {
                iterator.remove();
                messageCounter--;
                JOptionPane.showMessageDialog(null, "Message deleted.");
                return;
            }
        }

        JOptionPane.showMessageDialog(null, "Message not found.");
    }

    static void displayFullReport() {
        StringBuilder sb = new StringBuilder("Full Message Report:\n");
        for (JSONObject msg : sentMessages) {
            sb.append("ID: ").append(msg.get("MessageID"))
              .append(", Hash: ").append(msg.get("MessageHash"))
              .append(", Sender: ").append(msg.get("Sender"))
              .append(", Recipient: ").append(msg.get("Recipient"))
              .append(", Message: ").append(msg.get("Message"))
              .append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    static void saveMessagesToJSON() {
        try (FileWriter file = new FileWriter("storedMessages.json")) {
            file.write(messageStorage.toJSONString());
            file.flush();
            System.out.println("Stored messages saved to storedMessages.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
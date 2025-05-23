/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.chatapp1;
import javax.swing.JOptionPane;
import java.util.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


/**
 *
 * @author RC_Student_lab
 */
public class CHATAPP1 {

    private static int messageCounter =0;
    private static int totalMessages = 0;
    private static JSONArray messageStorage = new JSONArray();
    
    public static void main(String[] args) {
        if (! login()) return;
        
       
        JOptionPane.showMessageDialog(null,"Welcome to QuickChat.");
        
        
        int maxMessages = 0;
        while (true) {
        String input = JOptionPane.showMessageDialog(null,"Welcome to QuickChat.");
            try {
                maxMessages = Integer.parseInt(input);
                if (maxMessages > 0) break;
            } catch (Exception e) {
                        JOptiomPane.showInputDialog("How many message would you like to enter?");
                    
           
            }}
        
   
        
        
        String[] options = {"send Message", "show Recently sent MEssage", "Quit"};
        int choice = JOptionPane.showOptionDialog(null , "choose an option:","QiutChat menu",JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,null,options,options[0]);
        
        switch (choice) {
            case 1://Coming soon
                JOptionPane.showMessageDialog(null,"coming soon.");
                break;
            case 2: //Quit
            boolean exit = true;
                break;

            default:
                JOptionPane.showMessageDialog(null,"Invalid option.");
        }
    }
static boolean login(){
String username=JOptionPane.showInputDialog("Enter your username");
String password=JOptionPane.showInputDialog("Enter your password");
    

if ("admin".equals(username)&&"1234".equals(password)){ 
    return true;
} else {
    JOptionPane.showMessageDialog(null,"Login failed.");
  return false;
}}

static void sendMessage(){
    long messageId = 10000000000L + new Random().nextInt(900000000);
    messageCounter++;
    String recipient = JOptionPane.showInputDialog("Enter recipient number(+CCXXXXXXXXXXX):");
    if (recipient ==null||!recipient.matches("\\+\\d{9,12}")){
        JOptionPane.showMessageDialog(null,"Invalid number.Must include international code and be <= 12 digits.");
        return;
        
    
}
    String message = JOptionPane.showInputDialog("Enter your message (max 250 charts):");
    if(message == null|| message.length () > 250){
         JOptionPane.showMessageDialog(null ,"Please enter a message of less than 250 characters.");
        return;
    }
    
    String[] words = message.trim().split("\\s+");
    String hash = String.format("%02d:%d:%s%s",
            Long.parseLong(Long.toString(messageId).substring(0,2)),
            messageCounter,
            words[0].toUpperCase(),
            words.length > 1 ?words[words.length - 1].toUpperCase() :"");
    
    String[]actions = {"send","Disregard", "Store"};
   int action = JOptionPane.showOptionDialog(null,
           "Choose what to do with this message:",
            "Message Options",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,null, actions,actions[0]);
      
   if (action ==1){
       JOptionPane.showMessageDialog(null, "Message disregarded.");
       return;
   }
   
   JSONObject jsonMessage = new JSONObject();
   jsonMessage.put("MessageID", messageId);
   //jsonMessage.put("MEssageHash",hash);
   jsonMessage.put("Recipient", recipient);
   jsonMessage.put("Message", message);
   
   if(action ==2) {
    messageStorage.add(jsonMessage);
      JOptionPane.showMessageDialog(null,"Message stored.");
      return;
      
   }
  totalMessages++;
  JOptionPane.showMessageDialog(null,
          "Message sent!\n" +
                  "Message ID: " + messageId + "\n" +
                  //"Message Hash: " + hash + "\n" +
                  "Recipient: " + recipient + "\n" +
                  "Message: " + message);
  
}

static void saveMessagesToJSON() {
    try (FileWriter file = new FileWriter("storedMessages.json")) {
        file.write(messageStorage.toJSONString());
        file.flush();
        System.out.println("stored message saved to storedMessgaes.json");
    }catch (IOException e) {
        e.printStackTrace();
    }
}}
   
  
  
  



 
    
    
    
    
   

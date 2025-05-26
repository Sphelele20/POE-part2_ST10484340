/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */




package com.mycompany.chatapp1;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    @Test
    public void testCreateMessage() {
        long id = 1234567890L;
        String recipient = "+27831234567";
        String messageText = "Hello from the test!";

        JSONObject result = Message.createMessage(id, recipient, messageText);

        assertEquals(id, result.get("MessageID"));
        assertEquals(recipient, result.get("Recipient"));
        assertEquals(messageText, result.get("Message"));
    }

    @Test
    public void testValidPhoneNumbers() {
        assertTrue(Message.isValidPhoneNumber("+27831234567"));
        assertTrue(Message.isValidPhoneNumber("+123456789012"));
    }

    @Test
    public void testInvalidPhoneNumbers() {
        assertFalse(Message.isValidPhoneNumber("0831234567"));   // No '+' and not international
        assertFalse(Message.isValidPhoneNumber("+123"));         // Too short
        assertFalse(Message.isValidPhoneNumber(null));           // Null value
        assertFalse(Message.isValidPhoneNumber("+abcdefghijk")); // Non-numeric
    }

    @Test
    public void testValidMessageLength() {
        assertTrue(Message.isValidMessageLength("Short message."));
        assertTrue(Message.isValidMessageLength("a".repeat(500))); // Edge case
    }

    @Test
    public void testInvalidMessageLength() {
        assertFalse(Message.isValidMessageLength("a".repeat(501))); // Over limit
        assertFalse(Message.isValidMessageLength(null));            // Null value
    }
}

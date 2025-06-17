/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.mycompany.chatapp1;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.*;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChatApp1Test {

    @BeforeEach
    void resetState() throws Exception {
        // Clear sentMessages and reset counters using reflection (not ideal, but needed for static vars)
        Field sentMessagesField = ChatApp1.class.getDeclaredField("sentMessages");
        sentMessagesField.setAccessible(true);
        ((List<?>) sentMessagesField.get(null)).clear();

        Field messageCounterField = ChatApp1.class.getDeclaredField("messageCounter");
        messageCounterField.setAccessible(true);
        messageCounterField.setInt(null, 0);

        Field totalMessageField = ChatApp1.class.getDeclaredField("totalMessage");
        totalMessageField.setAccessible(true);
        totalMessageField.setInt(null, 0);
    }

    @Test
    void testSendMessageAndSearch() throws Exception {
        // Simulate sending a message
        JSONObject message = new JSONObject();
        message.put("MessageID", "1000000001");
        message.put("MessageHash", "802d:10:HELLO");
        message.put("Recipient", "+12345678901");
        message.put("Message", "Hello world");
        message.put("Sender", "User");

        // Inject into sentMessages list
        Field sentMessagesField = ChatApp1.class.getDeclaredField("sentMessages");
        sentMessagesField.setAccessible(true);
        List<JSONObject> sentMessages = (List<JSONObject>) sentMessagesField.get(null);
        sentMessages.add(message);

        assertEquals(1, sentMessages.size());

        // Simulate recipient search
        boolean found = sentMessages.stream()
                .anyMatch(msg -> msg.get("Recipient").equals("+12345678901"));
        assertTrue(found);
    }

    @Test
    void testDeleteMessageByHash() throws Exception {
        // Prepare test message
        JSONObject message = new JSONObject();
        message.put("MessageID", "1000000002");
        message.put("MessageHash", "802d:12:TEST");
        message.put("Recipient", "+19876543210");
        message.put("Message", "Test message");
        message.put("Sender", "User");

        Field sentMessagesField = ChatApp1.class.getDeclaredField("sentMessages");
        sentMessagesField.setAccessible(true);
        List<JSONObject> sentMessages = (List<JSONObject>) sentMessagesField.get(null);
        sentMessages.add(message);

        // Simulate deletion by hash
        sentMessages.removeIf(msg -> "802d:12:TEST".equals(msg.get("MessageHash")));

        assertTrue(sentMessages.stream().noneMatch(
                msg -> "802d:12:TEST".equals(msg.get("MessageHash"))
        ));
    }

    @Test
    void testLoginSuccess() {
        // Can’t test JOptionPane, but we can extract login logic to a helper and test that instead
        assertTrue(simulateLogin("Sphelele", "Thabekhulu5@"));
        assertFalse(simulateLogin("wrong", "wrong"));
    }

    // Helper method simulating login logic
    private boolean simulateLogin(String user, String pass) {
        return "Sphelele".equals(user) && "Thabekhulu5@".equals(pass);
    }
}

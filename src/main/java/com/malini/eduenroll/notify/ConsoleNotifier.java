package com.malini.eduenroll.notify;

import org.springframework.stereotype.Component;

@Component
public class ConsoleNotifier implements Notifier {

    @Override
    public void send(String to, String message) {
        // TODO: print in a readable format
        System.out.println("[NOTIFY] To: " + to + " | " + message);
    }
}

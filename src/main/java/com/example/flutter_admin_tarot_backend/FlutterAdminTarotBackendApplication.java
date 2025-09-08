package com.example.flutter_admin_tarot_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@SpringBootApplication
public class FlutterAdminTarotBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlutterAdminTarotBackendApplication.class, args);

        // Mở trình duyệt lên login.html
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI("http://localhost:8080/login.html"));
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}

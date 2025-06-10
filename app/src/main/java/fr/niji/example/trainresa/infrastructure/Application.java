package fr.niji.example.trainresa.infrastructure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "fr.niji.example.trainresa")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
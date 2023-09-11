package service;


import utils.Utils;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PasswordGeneratorService {

    private static final int MAX_ROUNDS = 5;
    private static final String PASSWORDS_LOCATION = "src/main/java/files/passwords.txt";
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private void generatePasswords(String hashSalt, String hashSeed) {

        StringBuilder sb = new StringBuilder();
        LocalDateTime currentDateTime = LocalDateTime.now();
        //The second isn't considered, because the time is used to generate the passwords in client and server and sync the password.
        //So, to don't occur some problem with sync, the second isn't considered.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        String formattedDateTime = currentDateTime.format(formatter);

        String hashDate = Utils.generateSHA256Hash(formattedDateTime);
        String newPassword = hashSeed;
        for(int i = 0; i < MAX_ROUNDS; i++) {
            newPassword = Utils.generateSHA256Hash(newPassword + hashSalt + hashDate);
            sb.append(newPassword, 0, 7).append("\n");
        }

        try (FileWriter fw = new FileWriter(PASSWORDS_LOCATION) ) {
            fw.write(sb.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("NEW PASSWORDS WAS GENERATED IN FILE");
    }

    public void startGeneratingPasswords(String hashSalt, String hashSeed) {
        Runnable task = () -> {
            generatePasswords(hashSalt, hashSeed);
        };
        scheduler.scheduleAtFixedRate(task, 0, 1, TimeUnit.MINUTES);
    }

    public void stopGeneratingPasswords() {
        scheduler.shutdown();
    }
}

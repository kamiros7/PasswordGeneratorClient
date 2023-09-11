import service.PasswordGeneratorService;
import utils.Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String fileName = "src/main/java/files/client_resources.txt"; // Replace with the path to your file
        boolean isAuthenticated = false;
        /*
        Remember that:
        0 -> salt
        1 -> local pass
        2 -> user
        3 -> seed
         */

        //Read the constraints in file
        List<String> clientResources = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                clientResources.add(parts[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(clientResources.isEmpty()) {
            System.out.println("File with constraints is empty, is necessary fill the file");
            System.exit(1);
        }

        Scanner scanner = new Scanner(System.in);
        while(!isAuthenticated) {
            System.out.println("Enter your user: ");
            String user = scanner.nextLine();
            System.out.println("Enter your local password: ");
            String localPassword = scanner.nextLine();
            String localPasswordHash = Utils.generateSHA256Hash(localPassword);
            if(user.equals(clientResources.get(2)) && localPasswordHash.equals(clientResources.get(1))) {
               isAuthenticated = true;
            } else {
                System.out.println("Your credentials are wrong!");
            }
        }

        PasswordGeneratorService service = new PasswordGeneratorService();
        service.startGeneratingPasswords(clientResources.get(0), clientResources.get(3));
    }
}
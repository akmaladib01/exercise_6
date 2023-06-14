package exercise_6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        try {
            // Connect to the server at localhost
            Socket socket = new Socket(InetAddress.getLocalHost(), 4224);

            // Create input stream
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());

            // Create output stream
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

            // Read user input
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the text to translate: ");
            String inputClient = scanner.nextLine();

            System.out.print("Enter the target language: ");
            String targetedLanguage = scanner.nextLine();

            // Write text and target language to the server
            outputStream.writeUTF(inputClient);
            outputStream.writeUTF(targetedLanguage);
            outputStream.flush();

            // Read from the network and display data
            String translatedText = inputStream.readUTF();
            System.out.println("Translated Text: " + translatedText);

            // Close everything
            inputStream.close();
            outputStream.close();
            socket.close();
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
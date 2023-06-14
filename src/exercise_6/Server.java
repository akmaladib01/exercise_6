package exercise_6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;

        try {
            int portNo = 4224;
            serverSocket = new ServerSocket(portNo);

            while (true) {
                // Accept client connection
                Socket clientSocket = serverSocket.accept();

                // Create input stream to receive data from the client
                DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream());

                // Read text and target language from the client
                String text = dataInputStream.readUTF();
                String language = dataInputStream.readUTF();
                
                // Translate the text
                String translation = translateText(text, language);

                // Create output stream to send the translation to the client
                DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());

                // Send the translation back to the client
                dataOutputStream.writeUTF(translation);

                // Close streams and client socket
                dataInputStream.close();
                dataOutputStream.close();
                clientSocket.close();
            }
        } catch (IOException ioe) {
            // Handle any IO exceptions and close the server socket
            if (serverSocket != null)
                serverSocket.close();

            ioe.printStackTrace();
        }
    }

    public static String translateText(String text, String language) {
        String translation;

        // Create a map to store language translations
        Map<String, Map<String, String>> translations = new HashMap<>();

        // English to Bahasa Malaysia translations
        Map<String, String> englishToBahasa = new HashMap<>();
        englishToBahasa.put("Good morning", "Selamat pagi");
        englishToBahasa.put("Good night", "Selamat malam");
        englishToBahasa.put("How are you?", "Apa khabar?");
        englishToBahasa.put("Thank You", "Terima kasih");
        englishToBahasa.put("Goodbye", "Selamat tinggal");
        englishToBahasa.put("What’s up?", "Ada apa?");
        translations.put("Bahasa Malaysia", englishToBahasa); // Store under "Bahasa" key

        // English to Arabic translations
        Map<String, String> englishToArabic = new HashMap<>();
        englishToArabic.put("Good morning", "صباح الخير");
        englishToArabic.put("Good night", "طاب مساؤك");
        englishToArabic.put("How are you?", "كيف حالك؟");
        englishToArabic.put("Thank You", "شكرا لك");
        englishToArabic.put("Goodbye", "مع السلامة");
        englishToArabic.put("What’s up?", "ما أخبارك؟");
        translations.put("Arabic", englishToArabic); // Store under "Arabic" key

        // English to Korean translations
        Map<String, String> englishToKorean = new HashMap<>();
        englishToKorean.put("Good morning", "좋은 아침");
        englishToKorean.put("Good night", "안녕히 주무세요");
        englishToKorean.put("How are you?", "어떻게 지내세요?");
        englishToKorean.put("Thank You", "감사합니다");
        englishToKorean.put("Goodbye", "안녕");
        englishToKorean.put("What’s up?", "뭐야?");
        translations.put("Korean", englishToKorean); // Store under "Korean" key

        // Get the translation based on the language and text
        if (translations.containsKey(language)) {
            Map<String, String> languageTranslations = translations.get(language);
            if (languageTranslations.containsKey(text)) {
                translation = languageTranslations.get(text);
            } else {
                translation = "Text cannot be translated";
            }
        } else {
            translation = "Invalid language";
        }

        return translation;
    }
}
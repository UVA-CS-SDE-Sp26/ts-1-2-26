import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.util.List;

public class Cipher {

    private String plain;
    private String cipher;

    public Cipher() {
        loadKey("ciphers/key.txt");
    }

    private void loadKey(String keyPath) {
        try {
            List<String> lines = Files.readAllLines(Path.of(keyPath));

            if (lines.size() != 2) {
                throw new IllegalArgumentException("Invalid key format");
            }

            plain = lines.get(0);
            cipher = lines.get(1);

            if (plain.length() != cipher.length()) {
                throw new IllegalArgumentException("Key length mismatch");
            }

        } catch (IOException e) {
            throw new RuntimeException("Key file not found");
        }
    }

    public String decipher(String text) {
        String result = "";

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);

            if (Character.isLetter(ch)) {
                char upper = Character.toUpperCase(ch);
                int index = cipher.indexOf(upper);

                if (index == -1) {
                    throw new IllegalArgumentException("Invalid cipher character");
                }

                char decoded = plain.charAt(index);

                if (Character.isLowerCase(ch)) {
                    decoded = Character.toLowerCase(decoded);
                }

                result += decoded;
            } else {
                result += ch;
            }
        }

        return result;
    }
}

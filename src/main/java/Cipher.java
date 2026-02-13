import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Cipher {

    // These store the two lines from the key file
    // plain = actual characters
    // cipher = encrypted characters
    private String plain;
    private String cipher;
    private FileHandler fh;

    // When we create a Cipher object,
    // automatically load the default key
    public Cipher() {
        loadKey("ciphers/key.txt");
    }

    // Reads and validates a key file
    private void loadKey(String keyPath) {
        try {
            // Read all lines from the file
            fh = new FileHandler("");
            List<String> lines = fh.readFileLines(keyPath);

            // Remove any empty lines just in case
            lines = lines.stream()
                    .filter(line -> !line.trim().isEmpty())
                    .collect(Collectors.toList());

            // A valid key must have exactly 2 lines
            if (lines.size() != 2) {
                throw new IllegalArgumentException("Invalid key format");
            }

            // First line = normal characters
            // Second line = cipher characters
            plain = lines.get(0);
            cipher = lines.get(1);

            // Both lines must be the same length
            if (plain.length() != cipher.length()) {
                throw new IllegalArgumentException("Key length mismatch");
            }

        } catch (IOException e) {
            // If the file cannot be found or read
            throw new RuntimeException("Key file not found: " + keyPath);
        }
    }

    // Decipher using the default key
    public String decipher(String text) {
        return decipherWithKey(text, plain, cipher);
    }

    // Decipher using a custom key file
    public String decipher(String text, String keyPath) {
        try {
            fh = new FileHandler("");
            List<String> lines = fh.readFileLines(keyPath);

            lines = lines.stream()
                    .filter(line -> !line.trim().isEmpty())
                    .collect(Collectors.toList());

            if (lines.size() != 2) {
                throw new IllegalArgumentException("Invalid key format");
            }

            String customPlain = lines.get(0);
            String customCipher = lines.get(1);

            if (customPlain.length() != customCipher.length()) {
                throw new IllegalArgumentException("Key length mismatch");
            }

            return decipherWithKey(text, customPlain, customCipher);

        } catch (IOException e) {
            throw new RuntimeException("Key file not found: " + keyPath);
        }
    }

    // This method does the actual character replacement
    private String decipherWithKey(String text, String plainKey, String cipherKey) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);

            // Find where this character appears in the cipher line
            int index = cipherKey.indexOf(ch);

            if (index != -1) {
                // Replace it with the matching character from the plain line
                result.append(plainKey.charAt(index));
            } else {
                // If it’s not in the key, leave it unchanged
                result.append(ch);
            }
        }

        return result.toString();
    }
}

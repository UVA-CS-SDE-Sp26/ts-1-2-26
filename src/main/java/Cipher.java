import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Cipher {

    private String plain;
    private String cipher;

    // Constructor - loads default key
    public Cipher() {
        loadKey("ciphers/key.txt");
    }

    private void loadKey(String keyPath) {
        try {
            List<String> lines = Files.readAllLines(Path.of(keyPath));

            lines = lines.stream()
                    .filter(line -> !line.trim().isEmpty())
                    .collect(Collectors.toList());

            if (lines.size() != 2) {
                throw new IllegalArgumentException("Invalid key format");
            }

            plain = lines.get(0);
            cipher = lines.get(1);

            if (plain.length() != cipher.length()) {
                throw new IllegalArgumentException("Key length mismatch");
            }

        } catch (IOException e) {
            throw new RuntimeException("Key file not found: " + keyPath);
        }
    }

    // Decipher using default key
    public String decipher(String text) {
        return decipherWithKey(text, plain, cipher);
    }

    // Decipher using alternate key
    public String decipher(String text, String keyPath) {
        try {
            List<String> lines = Files.readAllLines(Path.of(keyPath));

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

    private String decipherWithKey(String text, String plainKey, String cipherKey) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            int index = cipherKey.indexOf(ch);

            if (index != -1) {
                result.append(plainKey.charAt(index));
            } else {
                result.append(ch);
            }
        }

        return result.toString();
    }
}

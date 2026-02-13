import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CipherTest {

    // Make sure creating a Cipher object does not crash
    @Test
    void testConstructorLoadsDefaultKey() {
        assertDoesNotThrow(Cipher::new);
    }

    // Empty string should return empty string
    @Test
    void testEmptyStringReturnsEmpty() {
        Cipher cipher = new Cipher();
        assertEquals("", cipher.decipher(""));
    }

    // Characters not in the key should stay the same
    @Test
    void testUnmappedCharactersRemain() {
        Cipher cipher = new Cipher();
        assertEquals("!@#", cipher.decipher("!@#"));
    }

    // Basic single character mapping check
    @Test
    void testSingleCharacterMapping() {
        Cipher cipher = new Cipher();
        assertEquals("a", cipher.decipher("b"));
    }

    // Uppercase characters should also map correctly
    @Test
    void testUppercaseMapping() {
        Cipher cipher = new Cipher();
        assertEquals("A", cipher.decipher("B"));
    }

    // Numbers should map correctly if included in key
    @Test
    void testNumberMapping() {
        Cipher cipher = new Cipher();
        assertEquals("0", cipher.decipher("1"));
    }

    // Spaces should not be removed or changed
    @Test
    void testWhitespacePreserved() {
        Cipher cipher = new Cipher();
        assertEquals("a a", cipher.decipher("b b"));
    }

    // Invalid alternate key path should throw an error
    @Test
    void testAlternateKeyInvalidPathThrows() {
        Cipher cipher = new Cipher();
        assertThrows(RuntimeException.class,
                () -> cipher.decipher("test", "bad/path.txt"));
    }

    // Valid alternate key should work the same as default
    @Test
    void testAlternateKeyValidPath() {
        Cipher cipher = new Cipher();
        String result = cipher.decipher("b", "ciphers/key.txt");
        assertEquals("a", result);
    }

    // Mixed characters (letters + numbers and punctuation)
    @Test
    void testMixedCharacters() {
        Cipher cipher = new Cipher();
        String input = "bC1!";
        String expected = "aB0!";
        assertEquals(expected, cipher.decipher(input));
    }
}

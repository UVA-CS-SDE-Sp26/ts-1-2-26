import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CipherTest {

    // Creating a Cipher object should not throw errors
    @Test
    void testConstructorLoadsDefaultKey() {
        assertDoesNotThrow(Cipher::new);
    }

    // Empty string should stay empty
    @Test
    void testEmptyStringReturnsEmpty() {
        Cipher cipher = new Cipher();
        assertEquals("", cipher.decipher(""));
    }

    // Characters not in the key should remain unchanged
    @Test
    void testUnmappedCharactersRemain() {
        Cipher cipher = new Cipher();
        assertEquals("!@#", cipher.decipher("!@#"));
    }

    // Simple lowercase mapping check
    @Test
    void testSingleCharacterMapping() {
        Cipher cipher = new Cipher();
        assertEquals("a", cipher.decipher("b"));
    }

    // Uppercase letters should map correctly
    @Test
    void testUppercaseMapping() {
        Cipher cipher = new Cipher();
        assertEquals("A", cipher.decipher("B"));
    }

    // Based on professor key: 1 maps to Z
    @Test
    void testNumberMapping() {
        Cipher cipher = new Cipher();
        assertEquals("Z", cipher.decipher("1"));
    }

    // Whitespace should not change
    @Test
    void testWhitespacePreserved() {
        Cipher cipher = new Cipher();
        assertEquals("a a", cipher.decipher("b b"));
    }

    // Invalid key path should throw a RuntimeException
    @Test
    void testAlternateKeyInvalidPathThrows() {
        Cipher cipher = new Cipher();
        assertThrows(RuntimeException.class,
                () -> cipher.decipher("test", "bad/path.txt"));
    }

    // Valid alternate key should behave like default
    @Test
    void testAlternateKeyValidPath() {
        Cipher cipher = new Cipher();
        String result = cipher.decipher("b", "ciphers/key.txt");
        assertEquals("a", result);
    }

    // Mixed characters test (letters + numbers + punctuations)
    @Test
    void testMixedCharacters() {
        Cipher cipher = new Cipher();
        String input = "bC1!";
        String expected = "aBZ!";   // matches professor key
        assertEquals(expected, cipher.decipher(input));
    }
}

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CipherTest {

    @Test
    void testConstructorLoadsDefaultKey() {
        assertDoesNotThrow(Cipher::new);
    }

    @Test
    void testEmptyStringReturnsEmpty() {
        Cipher cipher = new Cipher();
        assertEquals("", cipher.decipher(""));
    }

    @Test
    void testUnmappedCharactersRemain() {
        Cipher cipher = new Cipher();
        assertEquals("!@#", cipher.decipher("!@#"));
    }

    @Test
    void testSingleCharacterMapping() {
        Cipher cipher = new Cipher();
        // Based on professor key: b → a
        assertEquals("a", cipher.decipher("b"));
    }

    @Test
    void testUppercaseMapping() {
        Cipher cipher = new Cipher();
        // Based on professor key: B → A
        assertEquals("A", cipher.decipher("B"));
    }

    @Test
    void testNumberMapping() {
        Cipher cipher = new Cipher();
        // Based on professor key: 1 → 0
        assertEquals("0", cipher.decipher("1"));
    }

    @Test
    void testWhitespacePreserved() {
        Cipher cipher = new Cipher();
        assertEquals("a a", cipher.decipher("b b"));
    }

    @Test
    void testAlternateKeyInvalidPathThrows() {
        Cipher cipher = new Cipher();
        assertThrows(RuntimeException.class,
                () -> cipher.decipher("test", "bad/path.txt"));
    }

    @Test
    void testAlternateKeyValidPath() {
        Cipher cipher = new Cipher();
        String result = cipher.decipher("b", "ciphers/key.txt");
        assertEquals("a", result);
    }

    @Test
    void testLongStringMapping() {
        Cipher cipher = new Cipher();
        String input = "bC1!";
        String expected = "aB0!";
        assertEquals(expected, cipher.decipher(input));
    }
}

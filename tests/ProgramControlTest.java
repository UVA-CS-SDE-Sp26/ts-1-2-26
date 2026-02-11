import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProgramControlTest {

    @Mock
    private FileHandler mockFileHandler;

    @Mock
    private CipherService mockCipherService;

    private ProgramControl programControl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        programControl = new ProgramControl(mockFileHandler, mockCipherService);
    }

    // TEST 1: displayFileList() with files present
    @Test
    void testDisplayFileList_formatsCorrectly() {
        when(mockFileHandler.listFiles())
                .thenReturn(Arrays.asList("filea.txt", "fileb.txt", "filec.txt"));

        String result = programControl.displayFileList();

        assertTrue(result.contains("01 filea.txt"));
        assertTrue(result.contains("02 fileb.txt"));
        assertTrue(result.contains("03 filec.txt"));
        verify(mockFileHandler).listFiles();
    }

    // TEST 2: displayFileList() with empty folder
    @Test
    void testDisplayFileList_emptyFolder() {
        when(mockFileHandler.listFiles())
                .thenReturn(Arrays.asList());

        String result = programControl.displayFileList();

        assertEquals("", result);
    }

    // TEST 3: displayFileContents() with valid file number
    @Test
    void testDisplayFileContents_validNumber() {
        List<String> files = Arrays.asList("filea.txt", "fileb.txt");
        when(mockFileHandler.listFiles()).thenReturn(files);
        when(mockFileHandler.readFile("fileb.txt")).thenReturn("ciphered data");
        when(mockCipherService.decipher("ciphered data"))
                .thenReturn("secret mission info");

        String result = programControl.displayFileContents("2", null);

        assertEquals("secret mission info", result);
        verify(mockFileHandler).readFile("fileb.txt");
        verify(mockCipherService).decipher("ciphered data");
    }

    // TEST 4: displayFileContents() with invalid file number
    @Test
    void testDisplayFileContents_numberTooHigh() {
        when(mockFileHandler.listFiles())
                .thenReturn(Arrays.asList("filea.txt"));

        String result = programControl.displayFileContents("99", null);

        assertTrue(result.contains("Error"));
        verify(mockFileHandler, never()).readFile(anyString());
    }

    @Test
    void testDisplayFileContents_negativeNumber() {
        when(mockFileHandler.listFiles())
                .thenReturn(Arrays.asList("filea.txt"));

        String result = programControl.displayFileContents("-1", null);

        assertTrue(result.contains("Error"));
        verify(mockFileHandler, never()).readFile(anyString());
    }

    @Test
    void testDisplayFileContents_zero() {
        when(mockFileHandler.listFiles())
                .thenReturn(Arrays.asList("filea.txt"));

        String result = programControl.displayFileContents("0", null);

        assertTrue(result.contains("Error"));
        verify(mockFileHandler, never()).readFile(anyString());
    }

    // TEST 5: displayFileContents() with non-numeric argument
    @Test
    void testDisplayFileContents_nonNumericArg() {
        when(mockFileHandler.listFiles())
                .thenReturn(Arrays.asList("filea.txt"));

        String result = programControl.displayFileContents("abc", null);

        assertTrue(result.contains("Error"));
        verify(mockFileHandler, never()).readFile(anyString());
    }

    // TEST 6: displayFileContents() with custom cipher key
    @Test
    void testDisplayFileContents_withCustomKey() {
        List<String> files = Arrays.asList("filea.txt", "fileb.txt");
        when(mockFileHandler.listFiles()).thenReturn(files);
        when(mockFileHandler.readFile("filea.txt")).thenReturn("ciphered data");
        when(mockCipherService.decipher("ciphered data", "customkey.txt"))
                .thenReturn("deciphered with custom key");

        String result = programControl.displayFileContents("1", "customkey.txt");

        assertEquals("deciphered with custom key", result);
        verify(mockCipherService).decipher("ciphered data", "customkey.txt");
    }

    // TEST 7: displayFileContents() with missing file
    @Test
    void testDisplayFileContents_fileHandlerThrows() {
        List<String> files = Arrays.asList("filea.txt");
        when(mockFileHandler.listFiles()).thenReturn(files);
        when(mockFileHandler.readFile("filea.txt"))
                .thenThrow(new RuntimeException("File not found"));

        String result = programControl.displayFileContents("1", null);

        assertTrue(result.contains("Error"));
        assertTrue(result.contains("File not found"));
    }
    // TEST 8: displayFileContents() with cipher failure
    @Test
    void testDisplayFileContents_cipherServiceThrows() {
        List<String> files = Arrays.asList("filea.txt");
        when(mockFileHandler.listFiles()).thenReturn(files);
        when(mockFileHandler.readFile("filea.txt")).thenReturn("ciphered data");
        when(mockCipherService.decipher("ciphered data"))
                .thenThrow(new RuntimeException("Invalid cipher key"));

        String result = programControl.displayFileContents("1", null);

        assertTrue(result.contains("Error"));
        assertTrue(result.contains("Invalid cipher key"));
    }
}
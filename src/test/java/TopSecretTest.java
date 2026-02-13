import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.io.IOException;
import java.util.List;

public class TopSecretTest {

    private ProgramControl pc;
    private UserInterface ui;

    @Mock
    private FileHandler mockFileHandler;

    @Mock
    private Cipher mockCipher;

    @BeforeEach
    void setUp() {
        // Initialize Mocks
        MockitoAnnotations.openMocks(this);

        // Inject mocks into the real ProgramControl
        pc = new ProgramControl(mockFileHandler, mockCipher);

        // Inject ProgramControl into the real UserInterface
        ui = new UserInterface(pc);
    }

    @Test
    void testListFilesIntegration() throws IOException {
        // Arrange: Mock the file handler to return a specific list
        when(mockFileHandler.listFiles()).thenReturn(List.of("secret.txt", "notes.txt"));

        // Act: Request file list (no args)
        String result = ui.requestHandling(new String[]{});

        // Assert: Verify formatting
        assertTrue(result.contains("01 secret.txt"));
        assertTrue(result.contains("02 notes.txt"));
    }

    @Test
    void testDecipherFileWithDefaultKey() throws IOException {
        // Arrange
        when(mockFileHandler.listFiles()).thenReturn(List.of("secret.txt"));
        when(mockFileHandler.readFile("secret.txt")).thenReturn("KHOOR");
        // Mock the cipher behavior
        when(mockCipher.decipher("KHOOR")).thenReturn("HELLO");

        // Act: User requests file 1
        String result = ui.requestHandling(new String[]{"1"});

        // Assert
        assertEquals("HELLO", result);
        verify(mockCipher).decipher("KHOOR");
    }

    @Test
    void testDecipherWithCustomKeyArg() throws IOException {
        // Arrange
        String customKeyPath = "custom_key.txt";
        when(mockFileHandler.listFiles()).thenReturn(List.of("secret.txt"));
        when(mockFileHandler.readFile("secret.txt")).thenReturn("KHOOR");
        when(mockCipher.decipher("KHOOR", customKeyPath)).thenReturn("BYE");

        // Act: User provides file index and custom key path
        String result = ui.requestHandling(new String[]{"1", customKeyPath});

        // Assert
        assertEquals("BYE", result);
        // Verify the custom key decipher method was called specifically
        verify(mockCipher).decipher("KHOOR", customKeyPath);
    }

    @Test
    void testInvalidFileNumberError() throws IOException {
        // Arrange
        when(mockFileHandler.listFiles()).thenReturn(List.of("only_one.txt"));

        // Act: User asks for file 5 (doesn't exist)
        String result = ui.requestHandling(new String[]{"5"});

        // Assert
        assertEquals("Error: Invalid file number.", result);
    }
}
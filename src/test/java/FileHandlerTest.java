import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileHandlerTest {

    @TempDir
    Path tempDir;

    @Test
    void readFile_returnsFileContents() throws IOException {
        Path file = tempDir.resolve("test.txt");
        Files.writeString(file, "Hello World");

        FileHandler handler = new FileHandler(tempDir.toString());

        String content = handler.readFile("test.txt");

        assertEquals("Hello World", content);
    }

    @Test
    void readFile_throwsException_whenFileDoesNotExist() {
        FileHandler handler = new FileHandler(tempDir.toString());

        assertThrows(IOException.class, () ->
                handler.readFile("missing.txt"));
    }

    @Test
    void readFile_returnsEmptyString_forEmptyFile() throws IOException {
        Path file = tempDir.resolve("empty.txt");
        Files.createFile(file);

        FileHandler handler = new FileHandler(tempDir.toString());
        String content = handler.readFile("empty.txt");

        assertEquals("", content);
    }

    @Test
    void readFileLines_returnsAllLines() throws IOException {
        // Arrange
        Path file = tempDir.resolve("lines.txt");
        Files.write(file, List.of("Line1", "Line2", "Line3"));

        FileHandler handler = new FileHandler(tempDir.toString());

        // Act
        List<String> lines = handler.readFileLines("lines.txt");

        // Assert
        assertEquals(3, lines.size());
        assertEquals("Line1", lines.get(0));
        assertEquals("Line3", lines.get(2));
    }

    @Test
    void listFiles_returnsOnlyTxtFiles_sorted() throws IOException {
        // Arrange
        Files.writeString(tempDir.resolve("b.txt"), "B");
        Files.writeString(tempDir.resolve("a.txt"), "A");
        Files.writeString(tempDir.resolve("ignore.md"), "ignore");

        FileHandler handler = new FileHandler(tempDir.toString());

        // Act
        List<String> files = handler.listFiles();

        // Assert
        assertEquals(List.of("a.txt", "b.txt"), files);
    }

    @Test
    void listFiles_returnsEmptyList_whenNoTxtFiles() throws IOException {
        // Arrange
        Files.writeString(tempDir.resolve("file.md"), "markdown");

        FileHandler handler = new FileHandler(tempDir.toString());

        // Act
        List<String> files = handler.listFiles();

        // Assert
        assertTrue(files.isEmpty());
    }
}
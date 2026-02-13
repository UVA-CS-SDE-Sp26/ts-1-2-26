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
    void testReadFile_returnsFileContents() throws IOException {
        Path file = tempDir.resolve("test.txt");
        Files.writeString(file, "Hello World");

        FileHandler handler = new FileHandler(tempDir.toString());

        String content = handler.readFile("test.txt");

        assertEquals("Hello World", content);
    }

    @Test
    void testReadFile_throwsException_whenFileDoesNotExist() {
        FileHandler handler = new FileHandler(tempDir.toString());

        assertThrows(IOException.class, () ->
                handler.readFile("missing.txt"));
    }

    @Test
    void testReadFile_returnsEmptyString_forEmptyFile() throws IOException {
        Path file = tempDir.resolve("empty.txt");
        Files.createFile(file);

        FileHandler handler = new FileHandler(tempDir.toString());
        String content = handler.readFile("empty.txt");

        assertEquals("", content);
    }

    @Test
    void testReadFileLines_returnsAllLines() throws IOException {
        Path file = tempDir.resolve("lines.txt");
        Files.write(file, List.of("Line1", "Line2", "Line3"));

        FileHandler handler = new FileHandler(tempDir.toString());

        List<String> lines = handler.readFileLines("lines.txt");

        assertEquals(3, lines.size());
        assertEquals("Line1", lines.get(0));
        assertEquals("Line3", lines.get(2));
    }

    @Test
    void testListFiles_returnsOnlyTxtFiles_sorted() throws IOException {
        Files.writeString(tempDir.resolve("b.txt"), "B");
        Files.writeString(tempDir.resolve("a.txt"), "A");
        Files.writeString(tempDir.resolve("ignore.md"), "ignore");

        FileHandler handler = new FileHandler(tempDir.toString());

        List<String> files = handler.listFiles();

        assertEquals(List.of("a.txt", "b.txt"), files);
    }

    @Test
    void testListFiles_returnsEmptyList_whenNoTxtFiles() throws IOException {
        Files.writeString(tempDir.resolve("file.md"), "markdown");

        FileHandler handler = new FileHandler(tempDir.toString());

        List<String> files = handler.listFiles();

        assertTrue(files.isEmpty());
    }
}
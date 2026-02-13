import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class FileHandler {
    private final String rootDir;

    public FileHandler(String rootDir){
        this.rootDir = rootDir;
    }

    public String readFile(String filename) throws IOException {
        Path filepath = Paths.get(rootDir, filename);

        return Files.readString(filepath);
    }

    public List<String> readFileLines(String filename) throws IOException {
        return Files.readAllLines(Paths.get(rootDir, filename));
    }

    public List<String> listFiles() throws IOException {
        Path dirPath = Paths.get(rootDir);

        try (Stream<Path> fileStream = Files.list(dirPath)) {
            return fileStream
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().endsWith(".txt"))
                    .map(path -> path.getFileName().toString())
                    .sorted() // sort to preserve order
                    .toList();

        }
    }
}

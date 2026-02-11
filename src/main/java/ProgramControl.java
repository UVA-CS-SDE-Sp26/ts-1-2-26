import java.util.List;
public class ProgramControl{
    private FileHandler fileHandler;
    private CipherService cipherService;

    public ProgramControl(FileHandler fileHandler, CipherService cipherService) {
        this.fileHandler = fileHandler;
        this.cipherService = cipherService;
    }
    public void run(String[] args) {
        String result;

        if (args.length == 0) {
            result = displayFileList();
        } else if (args.length == 1) {
            result = displayFileContents(args[0], null);
        } else {
            result = displayFileContents(args[0], args[1]);
        }

        System.out.println(result);
    }

    public String displayFileList() {
        List<String> files = fileHandler.listFiles();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < files.size(); i++) {
            sb.append(String.format("%02d %s%n", i + 1, files.get(i)));
        }
        return sb.toString().trim();
    }
    public String displayFileContents(String fileId, String optionalKey) {
        try {
            List<String> files = fileHandler.listFiles();
            int index = Integer.parseInt(fileId) - 1;

            if (index < 0 || index >= files.size()) {
                return "Error: Invalid file number.";
            }

            String contents = fileHandler.readFile(files.get(index));

            if (optionalKey != null) {
                contents = cipherService.decipher(contents, optionalKey);
            } else {
                contents = cipherService.decipher(contents);
            }

            return contents;

        } catch (NumberFormatException e) {
            return "Error: Argument must be a number.";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
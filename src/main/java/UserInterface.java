import java.io.IOException;
public class UserInterface{
    private ProgramControl controller;

    public UserInterface(ProgramControl controller){
        this.controller = controller;
    }
    //code provided by William
    public String requestHandling(String[] args) throws IOException {
        if (args.length > 0 && (args[0].equals("-h")||args[0].equals("-help"))) {
            return helpGuide();
        } else if (args.length > 2){
            return "Too many arguments";
        }else if (args.length == 0) {
            return controller.displayFileList();
        } else if(!isNumeric(args[0])){
            return "Not a valid file number";
        }else if (args.length == 1) {
            return controller.displayFileContents(args[0], null);
        } else
            return controller.displayFileContents(args[0], args[1]);
    }

    private static String helpGuide() { //help guide method
        return "--- File Portal Help ---\n" +
                "Usage: java TopSecret {file_number} {optional_key}\n" +
                "Commands:\n" +
                "no args: List all available files\n" +
                "{file_number}: View contents of a specific file\n" +
                "{file_number} [optional_key]: Decrypt and view file contents\n" +
                "-h, -help : Show  help menu";
    }

    private boolean isNumeric(String str) {
        return str != null && str.matches("\\d+");
    }

}
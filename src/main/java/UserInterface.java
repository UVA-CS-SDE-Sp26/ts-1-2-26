public class UserInterface{

    private ProgramControl controller;

    public UserInterface(ProgramControl controller){
        this.controller = controller;
    }

    public static void main(String[] args){
        try {
            System.out.println("Welcome to the file portal. All file are encrypted");
            System.out.println("Type -h or -help to see how to use the program");

            //instantiate classes
            FileHandler fh = new FileHandler("data");
            Cipher cph = new Cipher();
            ProgramControl pc = new ProgramControl(fh, cph);

            //run program
            UserInterface ui = new UserInterface(pc);

            String output = ui.requestHandling(args);
            System.out.println(output);
        } catch (RuntimeException e){
            System.err.println("SYSTEM ERROR: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("UNEXPECTED ERROR: " + e.getMessage());
        }
    }
    //code provided by William
    public String requestHandling(String[] args){
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
            return "Not a valid alternate cipher";
    }

    private static String helpGuide() { //help guide method
        return "--- File Portal Help ---\n" +
                "Usage: java topsecret {file_number} {optional_key}\n" +
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
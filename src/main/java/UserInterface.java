public class UserInterface{
    public static void main(String[] args){
        println("Welcome to the file portal. All file are encrypted.");
        println("Type -h or -help to see how to use the program.");
        FileHandler fh = new FileHandler();
        Cipher cph = new Cipher();

        ProgramControl controller = new ProgramControl(fh,cph);
        controller.run(args);

        //code provided by William
        String output;
        if (args.length > 0 && (args[0].equals("-h")||args[0].equals("-help")) {
            System.out.println(helpGuide());
        }
        else if (args.length == 0) {
            output = controller.displayFileList();
        } else if (args.length == 1) {
            output = controller.displayFileContents(args[0], null);
        } else {
            output = controller.displayFileContents(args[0], args[1]);
        }
        System.out.println(output);
    }

    private String helpGuide() {
        return "--- File Portal Help ---\n" +
                "Usage: java topsecret {file_number} {optional_key}\n" +
                "Commands:\n" +
                "no args: List all available files\n" +
                "{file_number}: View contents of a specific file\n" +
                "{file_number} [optional_key]: Decrypt and view file contents\n" +
                "-h, -help : Show  help menu";
    }

}
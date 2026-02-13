public class TopSecret {
    public static void main(String[] args){
        try {
            System.out.println("All files are encrypted");
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
}

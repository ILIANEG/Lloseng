package client;
import java.util.StringTokenizer;

public class Command {
    
    private String command;
    private String argument;


    public Command (String message) {
        if (!isCommand(message) || message == null) {
            throw new IllegalArgumentException();
        } else {
            StringTokenizer st = new StringTokenizer(message);
            command = st.nextToken().replace("#", "");
            if (st.hasMoreTokens()) {
                try {
                    argument = st.nextToken().replace("<","").replace(">","");
                } catch (NumberFormatException e) {
                    argument = null;
                }
            } else {
                argument = null;
            }
        }
    }

    public String getCommand() {
        return command;
    }

    public String getArgument() {
        return argument;
    }

    public static boolean isCommand (String s) {
         if (s.charAt(0) == '#') {
            return true;
        } else {
            return false;
        }
    }
}
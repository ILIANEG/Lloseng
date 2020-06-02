import java.io.*;
import client.*;
import common.*;

public class ServerConsole implements ChatIF {

    /**
     * Instance of server that cleates this console
     */
    EchoServer server;

    /**
    * The default port to listen on.
    */
  final public static int DEFAULT_PORT = 5555;


    public ServerConsole(int port) {
        server = new EchoServer(port);
    }

    /**
     * Method accepts user input from console
     * idetifying if it is a command or message,
     * if it is command, this command is sent to 
     * server to be executed, otherwise message
     * is echoed to all users as well as
     * displaed to user
     */
    public void accept() {
        try {
            BufferedReader fromConsole = 
                new BufferedReader(new InputStreamReader(System.in));
            String message;

            while (true) {
                message = fromConsole.readLine();
                if (Command.isCommand(message)) {
                  Command command = new Command(message);
                  server.execute(command);
                } else {
                  server.sendToAllClients("SERVER MSG> " + message);
                  display(message);
                }
            }
        } 
        catch (Exception ex) {
        System.out.println
            ("Unexpected error while reading from console!");
        }
    }

    public void display(String message) {
        System.out.println("SERVER MSG> "+message);
    }

    public static void main(String[] args) 
    {
      int port = 0; //Port to listen on
  
      try
      {
        port = Integer.parseInt(args[0]); //Get port from command line
      }
      catch(Throwable t)
      {
        port = DEFAULT_PORT; //Set port to 5555
      }
      
      ServerConsole sc = new ServerConsole(port);
      
      try 
      {
        sc.server.listen(); //Start listening for connections
      } 
      catch (Exception ex) 
      {
        System.out.println("ERROR - Could not listen for clients!");
      }
      sc.accept();
    }
}
import java.io.*;
import client.*;
import common.*;
import command.*;
import server.*;
public class ServerConsole implements ChatIF {

  /**
  * Instance of server that creates this console
  */
  private EchoServer server;

  /**
  * The default port to listen to.
  */
  final public static int DEFAULT_PORT = 5555;

  /**
   * Command invoker for this server
   */
  private ServerCommandInvoker commander;


    public ServerConsole(int port) {
        server = new EchoServer(port, this);
        commander = new ServerCommandInvoker(server);
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
                  commander.execute(new Command(message));
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
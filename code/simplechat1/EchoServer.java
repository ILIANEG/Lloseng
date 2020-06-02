// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;
import common.*;
import ocsf.server.*;

/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */
public class EchoServer extends AbstractServer 
{
  //Class variables *************************************************
  
  /**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT = 5555;

  /**
   * The console UI
   */
  ChatIF serverUI;
  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public EchoServer(int port) 
  {
    super(port);
  }

  
  //Instance methods ************************************************
  
  /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   */
  public void handleMessageFromClient
    (Object msg, ConnectionToClient client)
  {
    /*try {
      if (Command.isCommand((String) msg)) {
        Command command = new Command((String) msg);
        execute(command);
      }
    }*/
    System.out.println("Message received: " + msg + " from " + client);
    this.sendToAllClients(msg);
  }
    
  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted()
  {
    System.out.println
      ("Server listening for connections on port " + getPort());
  }
  
  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped()
  {
    System.out.println
      ("Server has stopped listening for connections.");
  }

  /**
   * Method receives argument of type Command and interpretates
   * such command in context of server
   * @param c
   * @throws IllegalArgumentException
   */
  public void execute(Command c) throws IllegalArgumentException {
    switch(c.getCommand()) {
      case "quit":
        stopListening();
        try {
          close();
        } catch (IOException e) {
          c.commandError();
          serverUI.display("Can not close connection");
        } 
        System.exit(0);
        break;
      case "stop":
        stopListening();
        break;
      case "close":
        stopListening();
        try {
          close();
        } catch (IOException e) {
          c.commandError();
          serverUI.display("Can not close connection");
        } 
        break;
      case "setport":
        if (c.getArgument() == null || 5 < c.getArgument().length()
            || c.getArgument().length() == 0) {
          c.commandError();
          serverUI.display("Invalid port number");
          break;
        }
        Integer p = null;
        try {
          p = Integer.parseInt(c.getArgument());
        } catch (NumberFormatException e) {
          c.commandError();
          serverUI.display("Invalid port number");
          break;
        }
        if (p != null) {
            setPort(p);
        }
        break;
      case "start":
        if (!isListening()) {
          try {
            listen();
          } catch (IOException e) {
            c.commandError();
            serverUI.display("Can not listen for connections");
          }
        } else {
          c.commandError();
          serverUI.display("Server is already listening for connection");
        }
        break;
      case "getport":
        serverUI.display(Integer.toString(getPort()));
        break;
      default:
        c.commandError();
        serverUI.display("Can not recognize the command");
      }
    }

    /**
     * Prints notification about connected client
     */
    protected void clientConnected(ConnectionToClient client) {
      System.out.println("Client "+client.toString()+" connected to the server");
    }
    
    /**
     * Prints notification about disconnected client
     */
    synchronized protected void clientDisconnected(
      ConnectionToClient client) {
        System.out.println("Client disconnected from the server");
    }
    
    /**
     * prints notification if connection with client was interrupted
     */
    synchronized protected void clientException(
      ConnectionToClient client, Throwable exception) {
        System.out.println("Connection with client was interrupted");
      }
  
  //Class methods ***************************************************
  
  /**
   * This method is responsible for the creation of 
   * the server instance (there is no UI in this phase).
   *
   * @param args[0] The port number to listen on.  Defaults to 5555 
   * if no argument is entered.
   */
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
	
    EchoServer sv = new EchoServer(port);
    
    try 
    {
      sv.listen(); //Start listening for connections
    } 
    catch (Exception ex) 
    {
      System.out.println("ERROR - Could not listen for clients!");
    }
  }

}
//End of EchoServer class

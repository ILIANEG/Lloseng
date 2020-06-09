// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 
package server;

import java.io.*;
import java.util.LinkedList;

import command.Command;
import common.*;
import ocsf.server.*;

/**
 * This class overrides some of the methods in the abstract superclass in order
 * to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */
public class EchoServer extends AbstractServer {
  // Class variables *************************************************

  /**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT = 5555;

  /**
   * The console UI
   */
  ChatIF serverUI;

  // Constructors ****************************************************

  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public EchoServer(int port, ChatIF serverUI) {
    super(port);
    this.serverUI = serverUI;
  }

  // Instance methods ************************************************

  /**
   * This method handles any messages received from the client.
   *
   * @param msg    The message received from the client.
   * @param client The connection from which the message originated.
   */
  public void handleMessageFromClient(Object msg, ConnectionToClient client) {
    if (Command.isCommand((String) msg)) {
      Command c = new Command((String) msg);
      client.setInfo("loginID", c.getArgument());
      System.out.println("<"+client.getInfo("loginID")+">"+" has logged on");
      this.sendToAllClients("<"+client.getInfo("loginID")+">"+" has logged on");
    } else if (!(Command.isCommand((String) msg)) && client.getInfo("loginID") == null) {
      try {
        client.sendToClient("Invalid ID");
      } catch (Exception e) {
      } finally {
        try {
          client.close();
        } catch (Exception e) {
          client = null;
        }
      }
    } else {
      System.out.println("Message received: "+msg+" from "+"<"+client.getInfo("loginID")+">");
      this.sendToAllClients(msg);
    }
  }

  /**
   * This method overrides the one in the superclass. Called when the server
   * starts listening for connections.
   */
  protected void serverStarted() {
    System.out.println("Server listening for connections on port " + getPort());
  }

  /**
   * This method overrides the one in the superclass. Called when the server stops
   * listening for connections.
   */
  protected void serverStopped() {
    System.out.println("Server has stopped listening for connections.");
    sendToAllClients("WARNING - Server has stopped listening for connections.");
  }

  public void displaySystem(String sys) {
    System.out.println(sys);
  }

  /**
   * Prints notification about connected client
   */
  protected void clientConnected(ConnectionToClient client) {
    System.out.println("A new client is attempting to connect to the server.");
  }

  /**
   * Prints notification about disconnected client
   */
  synchronized protected void clientDisconnected(ConnectionToClient client) {
    String msg = "<"+client.getInfo("loginID")+">"+" has disconnected";
    System.out.println(msg);
    sendToAllClients(msg);
  }

  /**
   * prints notification if connection with client was interrupted
   */
  synchronized protected void clientException(ConnectionToClient client, Throwable exception) {
    clientDisconnected(client);
  }

  //Class methods ***************************************************
  
  /**
   * This method is responsible for the creation of 
   * the server instance (there is no UI in this phase).
   *
   * @param args[0] The port number to listen on.  Defaults to 5555 
   *          if no argument is entered.
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
	
    EchoServer sv = new EchoServer(port, null);
    
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
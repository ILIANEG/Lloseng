// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import ocsf.client.*;
import common.*;
import java.io.*;

/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */
public class ChatClient extends AbstractClient
{
  //Instance variables **********************************************
  
  /**
   * The interface type variable.  It allows the implementation of 
   * the display method in the client.
   */
  ChatIF clientUI; 

  /**
   * login id that is used by server to recognize the user
   */
  String loginID;

  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the chat client.
   *
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param clientUI The interface type variable.
   */
  
  public ChatClient(String loginID,String host, int port, ChatIF clientUI) 
    throws IOException, IllegalArgumentException
  {
    super(host, port); //Call the superclass constructor
    this.clientUI = clientUI;
    if (loginID == null) {
      throw new IllegalArgumentException();
    } else {
      this.loginID = loginID;
    }
    openConnection();
  }

  
  //Instance methods ************************************************
    
  /**
   * This method handles all data that comes in from the server.
   *
   * @param msg The message from the server.
   */
  public void handleMessageFromServer(Object msg) 
  {
    clientUI.display(msg.toString());
  }

  /**
   * This method handles all data coming from the UI            
   *
   * @param message The message from the UI.    
   */
  public void handleMessageFromClientUI(String message)
  {
    /*if (Command.isCommand(message)) {
      Command c = new Command(message);
    } else {*/
      try
      {
        sendToServer(message);
      }
      catch(IOException e)
      {
        clientUI.display
          ("Could not send message to server.  Terminating client.");
        quit();
      }
    }
  
  /**
   * This method terminates the client.
   */
  public void quit()
  {
    try
    {
      closeConnection();
    }
    catch(IOException e) {}
    System.exit(0);
  }

  /**
   * Method sends system message to client, such as command output
   * For example when command was not executed successfully
   * Warning will be displayed
   * @param warning
   */
  public void displaySystem(String sys) {
    clientUI.display("SYSTEM> "+sys);
  }

  /**
   * Overriden hook method that informs client about connection
   * being orderly closed
   */
  protected void connectionClosed () {
    clientUI.display("Connection with server was shut down");
  }

  /**
   * Overriden hook method that informs about
   * unexpectedly interrupted connection with 
   * the server
   */
  protected void connectionException (Exception exception) {
    clientUI.display("Connection with server was interrupted");
  }

  /**
   * Overriden hook method that sends login information
   * upon establishing connection with the server
   */
  protected void connectionEstablished() {
    try {
      sendToServer("#login <"+loginID+">");
    } catch (IOException e) {
      clientUI.display
        ("Could not send message to server.  Terminating client.");
      quit();
    }
  }
}

//End of ChatClient class

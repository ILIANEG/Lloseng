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

  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the chat client.
   *
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param clientUI The interface type variable.
   */
  
  public ChatClient(String host, int port, ChatIF clientUI) 
    throws IOException 
  {
    super(host, port); //Call the superclass constructor
    this.clientUI = clientUI;
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
    if (Command.isCommand(message)) {
      Command c = new Command(message);
      execute(c);
    } else {
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

public void execute(Command c) throws IllegalArgumentException {
  switch(c.getCommand()) {
    case "quit":
      quit();
      break;
    case "logoff":
        try {
            closeConnection(); 
        } catch (IOException e) {
            clientUI.display("Attempt to log off was unsuccesfull");
        }
        break;
    case "sethost":
        setHost(c.getArgument());
        break;
    case "setport":
        if (5 < c.getArgument().length()) {
          clientUI.display("Invalid port number");
          break;
        }
        Integer p = null;
        try {
          p = Integer.parseInt(c.getArgument());
        } catch (NumberFormatException e) {
          clientUI.display("Invalid port number"); 
        }
        if (p != null) {
            setPort(p);
        }
        break;
      case "login":
        try {
            openConnection();
            if (!(isConnected())) {
              clientUI.display("Can not connect");
              closeConnection();
            }
            break;
        } catch (IOException e) {
            break;
        }
      case "gethost":
        clientUI.display(getHost());
        break;
      case "getport":
        clientUI.display(Integer.toString(getPort()));
        break;
      default:
        clientUI.display("Unknown command");
      
    }
  }

  protected void connectionClosed () {
    clientUI.display("Connection with server lost");
  }

  protected void connectionException (Exception exception) {
    clientUI.display("Connection with server failed");
  }
}

//End of ChatClient class

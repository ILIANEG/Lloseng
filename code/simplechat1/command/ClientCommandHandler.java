package command;

import java.io.IOException;
import client.*;

/**
 * Handling commands envoked by client. Uses ChatClient instance
 * as Receiver
 */
class ClientCommandHandler {

    //initiate command receiver 
    private ChatClient client;

    /**
     * Constructor of ClientCommand that will be invoked 
     * by subclasses 
     * @param client
     */
    public ClientCommandHandler(ChatClient client) {
        this.client = client;
    }

    /**
     * Disconnects client form server and closing application
     */
    public void quit() {
        client.quit();
    }

    /**
     * Connects ti the server
     */
    public void login() {
        try {
            client.openConnection();
            if (!(client.isConnected())) {;
              client.displaySystem("Can not connect");
              client.closeConnection();
            }
        } catch (IOException e) {
            client.displaySystem("Can not connect");
        }
    }

    /**
     * Disconnects from the server
     */
    public void logoff() {
        try {
            client.closeConnection(); 
        } catch (IOException e) {
            client.displaySystem("Attempt to log off was unsuccesfull");
        }
    }
    /**
     * Sets host of current ChatClient
     */
    public void setHost(String host) {
        if (host == null) {
            client.displaySystem("Attempt to set host was unsuccesfull");
        } else {
            client.setHost(host); 
            client.displaySystem("Host set to: " + client.getHost());
        }
    }

    /**
     * Setting port of the ChatClient
     */
    public void setPort(String port) {
        if (5 < port.length() || port == null) {
            client.displaySystem("Invalid port number");
            return;
          }
          Integer p = null;
          try {
            p = Integer.parseInt(port);
          } catch (NumberFormatException e) {
            client.displaySystem("Illegal port number"); 
            return;
          }
        client.setPort(p);
        client.displaySystem("port set to: " + Integer.toString(client.getPort()));
    }

    /**
     * Displays ChatClient's host
     */
    public void getHost() {
        client.displaySystem(client.getHost());
    }

    /**
     * Displays ChatClient's port number
     */
    public void getPort() {
        client.displaySystem("connected to port #"+Integer.toString(client.getPort()));
    }

    public void unknown() {
        client.displaySystem("Command is unknown");
    }
}
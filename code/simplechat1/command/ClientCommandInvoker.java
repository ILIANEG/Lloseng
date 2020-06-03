package command;

import client.ChatClient;

/**
 * This class works as additional layer between UI and 
 * implementation of client. This class also
 * defines a protocol by which command sent by user can be 
 * interpretated and change the state of ChatCLient
 */
public class ClientCommandInvoker {

    //Instance variables
    private ClientCommandHandler handler;

    //Constructor
    public ClientCommandInvoker(ChatClient client) {
        handler = new ClientCommandHandler(client);
    }

    //Instance methods
    public void execute(Command command) {
        switch(command.getCommand()) {
            case "quit":
                handler.quit();
            case "logoff":
                handler.logoff();
            case "login":
                handler.login();
            case "sethost":
                handler.setHost(command.getArgument());
            case "setport":
                handler.setPort(command.getArgument());
            case "gethost":
                handler.getHost();
            case "getport":
                handler.getPort();
            default:
                handler.unknown();
        }
    }
}
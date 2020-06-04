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
                break;
            case "logoff":
                handler.logoff();
                break;
            case "login":
                handler.login();
                break;
            case "sethost":
                handler.setHost(command.getArgument());
                break;
            case "setport":
                handler.setPort(command.getArgument());
                break;
            case "gethost":
                handler.getHost();
                break;
            case "getport":
                handler.getPort();
                break;
            default:
                handler.unknown();
        }
    }
}
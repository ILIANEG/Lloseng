package command;

import server.EchoServer;

/**
 * This class works as additional layer between UI and 
 * implementation of server. This class also
 * defines a protocol by which command sent by user can be 
 * interpretated and change the state of EchoServer
 */
public class ServerCommandInvoker {

    //Instance variables
    private ServerCommandHandler handler;

    //Constructor
    public ServerCommandInvoker(EchoServer server) {
        handler = new ServerCommandHandler(server);
    }

    //Instance methods
    public void execute(Command command) {
        switch(command.getCommand()) {
            case "quit":
                handler.quit();
                break;
            case "stop":
                handler.stop();
                break;
            case "close":
                handler.close();
                break;
            case "setport":
                handler.setPort(command.getArgument());
                break;
            case "start":
                handler.start();
                break;
            case "getport":
                handler.getPort();
                break;
            default:
                handler.unknown();
        }
    }
}
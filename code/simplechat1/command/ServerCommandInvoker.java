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
            case "stop":
                handler.stop();
            case "close":
                handler.close();
            case "setport":
                handler.setPort(command.getArgument());
            case "start":
                handler.start();
            case "getport":
                handler.getPort();
            default:
                handler.unknown();
        }
    }
}
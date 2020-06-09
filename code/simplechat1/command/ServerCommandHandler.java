package command;

import java.io.IOException;
import server.*;

public class ServerCommandHandler {

    private EchoServer server;

    public ServerCommandHandler(EchoServer server) {
        this.server = server;
    }

    public void quit() {
      close();
      System.exit(0);
    }

    public void stop() {
        server.stopListening();
    }

    public void close() {
        server.stopListening();
        try {
          server.close();
        } catch (IOException e) {
          server.displaySystem("Can not close connection");
        } 
    }

    public void setPort(String port) {
        if (port == null || 5 < port.length()
            || port.length() == 0) {
          server.displaySystem("Invalid port number");
          return;
        }
        Integer p = null;
        try {
          p = Integer.parseInt(port);
        } catch (NumberFormatException e) {
          server.displaySystem("Invalid port number");
          return;
        }
        server.setPort(p);
        server.displaySystem("port set to: "+Integer.toString(server.getPort()));
    }

    public void start() {
        if (!server.isListening()) {
            try {
              server.listen();
            } catch (IOException e) {
              server.displaySystem("Can not listen for connections");
            }
          } else {
            server.displaySystem("Server is already listening for connection");
          }
    }

    public void getPort() {
        server.displaySystem(Integer.toString(server.getPort()));
    }
    
    public void unknown() {
        server.displaySystem("Unknown command");
    }
}
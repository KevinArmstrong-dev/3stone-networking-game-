package pkg3stone.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import pkg3stone.network.NetworkServerBroadcaster;
import pkg3stone.network.NetworkServerPlayer;

/**
 * Server Class
 *
 * @author Svitlana Myronova
 */
public class Server {

    private static final Logger LOG = Logger.getLogger(Server.class.getName());

    private static int getServerPort(String[] args) {
        int serverPort = NetworkServerPlayer.DEFAULT_PORT;
        try {
            serverPort = Integer.parseInt(args[0]);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException ex) {
            LOG.log(Level.INFO, "Server port number is not provided. Will use port {0}", NetworkServerPlayer.DEFAULT_PORT);
        }
        return serverPort;
    }

    private static int getBroadcasterPort(String[] args) {
        int broadcasterPort = NetworkServerBroadcaster.DEFAULT_PORT;
        try {
            broadcasterPort = Integer.parseInt(args[1]);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException ex) {
            LOG.log(Level.INFO, "Broadcaster port number is not provided. Will use port {0}", broadcasterPort);
        }
        return broadcasterPort;
    }

    /**
     * Main method of the Server Class
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int serverPort = getServerPort(args);
        int boardcasterPort = getBroadcasterPort(args);

        try {
            NetworkServerBroadcaster broadcaster = new NetworkServerBroadcaster(boardcasterPort, serverPort);
            broadcaster.start();

            try (ServerSocket serverSocket = new ServerSocket(serverPort)) {
                while (true) {
                    LOG.log(Level.INFO, "Waiting for connection");
                    Socket connectedClientSocket = serverSocket.accept();
                    LOG.log(Level.INFO, "Got connection from {0}", connectedClientSocket.toString());
                    new Thread(new ServerGame(connectedClientSocket)).start();
                }
            }
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }

    }
}

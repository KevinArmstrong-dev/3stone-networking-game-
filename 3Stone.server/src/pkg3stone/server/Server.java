package pkg3stone.server;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
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
     * Returns list of network interfaces
     * @return
     */
    private static List<String> getListOfInterfaces() {
        List<String> result = new ArrayList<>();
        // Get the network interfaces and associated addresses for this host
        try {
            Enumeration<NetworkInterface> interfaceList = NetworkInterface.getNetworkInterfaces();
            if (interfaceList == null) {
                LOG.log(Level.WARNING, "--No interfaces found--");
                return result;
            }
            
            while (interfaceList.hasMoreElements()) {
                NetworkInterface iface = interfaceList.nextElement();

                LOG.log(Level.INFO, "Interface {0}:", iface.getName());
                Enumeration<InetAddress> addrList = iface.getInetAddresses();
                if (!addrList.hasMoreElements()) {
                    LOG.log(Level.INFO, "\t(No addresses for this interface)");
                }
                while (addrList.hasMoreElements()) {
                    InetAddress address = addrList.nextElement();
                    String addressType = address instanceof Inet4Address ? "(v4)"
                            : (address instanceof Inet6Address ? "(v6)" : "(?)");
                    LOG.log(Level.INFO, "\tAddress {0} : {1}", new Object[]{addressType, address.getHostAddress()});
                    if(address instanceof Inet4Address)
                    {
                        result.add(address.getHostAddress());
                    }
                }
            }
        } catch (SocketException se) {
            LOG.log(Level.SEVERE, "Error getting network interfaces:{0}", se.getMessage());
        }
        return result;
    }

    /**
     * Main method of the Server Class
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int serverPort = getServerPort(args);
        int boardcasterPort = getBroadcasterPort(args);

        List<String> interfaces = getListOfInterfaces();
        
        try {
            NetworkServerBroadcaster broadcaster = new NetworkServerBroadcaster(boardcasterPort, serverPort);
            broadcaster.start();

            try (ServerSocket serverSocket = new ServerSocket(serverPort)) {
                while (true) {
                    LOG.log(Level.INFO, "Waiting for connection on interfaces {0}", interfaces.toString());
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

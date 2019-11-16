/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg3stone.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Svitlana Myronova
 */
public class NetworkServerBroadcaster implements Runnable {

        /**
     * Logger
     */
    private static final Logger LOG = Logger.getLogger(NetworkServerBroadcaster.class.getName());

    public static final int DEFAULT_PORT = 50001;
    private static final int BUFFER_SIZE = 256;

    private final byte[] serverPortBytes;
    private final DatagramSocket broadcastSocket;
    private Thread thread;

    /**
     *
     * @param broadcasterPort
     * @param serverPort
     * @throws IOException
     */
    public NetworkServerBroadcaster(int broadcasterPort, int serverPort) throws IOException {
        this.serverPortBytes = ByteBuffer.allocate(4).putInt(serverPort).array();
        this.broadcastSocket = new DatagramSocket(broadcasterPort);
    }

    /**
     * This method will start a new thread
     */
    public void start() {
        this.thread = new Thread(this);
        this.thread.start();
    }

    /**
     * This will start the server a specified buffer size
     */
    @Override
    public void run() {
        while (true) {
            try {
                byte[] buf = new byte[BUFFER_SIZE];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                this.broadcastSocket.receive(packet);

                DatagramPacket replyPacket = new DatagramPacket(serverPortBytes, serverPortBytes.length, packet.getSocketAddress());
                this.broadcastSocket.send(replyPacket);
            } catch (IOException ex) {
                LOG.log(Level.SEVERE, null, ex);
            }
        }
    }
}

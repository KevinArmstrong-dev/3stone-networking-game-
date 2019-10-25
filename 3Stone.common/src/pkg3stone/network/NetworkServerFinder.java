/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg3stone.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 *
 * @author Svitlana Myronova
 */
public class NetworkServerFinder {

    private static final int RECEIVE_TIMEOUT = 1000; //1sec
    private static final int BUFFER_SIZE = 256;
    
    private final DatagramSocket finderSocket;

    public NetworkServerFinder() throws SocketException {
        this.finderSocket = new DatagramSocket();
        this.finderSocket.setBroadcast(true);
        this.finderSocket.setSoTimeout(RECEIVE_TIMEOUT);
    }

    /**
     * This method will look for the listening severs and return their ip and 
     * port
     * 
     * @param broadcasterPort
     * @return
     * @throws IOException 
     */
    public ArrayList<InetSocketAddress> findServers(int broadcasterPort) throws IOException {
        ArrayList<InetSocketAddress> result = new ArrayList<>();
        
        sendSearchBroadcast(broadcasterPort);
        
        while(true)
        {
            try
            {
                byte[] data = new byte[BUFFER_SIZE];
                DatagramPacket packet = new DatagramPacket(data, data.length);
                this.finderSocket.receive(packet);
                
                final int serverPort = ByteBuffer.wrap(data).getInt();
                result.add( new InetSocketAddress(packet.getAddress(), serverPort) );
            }
            catch(java.net.SocketTimeoutException e)
            {
                break;
            }
        }
        
        return result;
    }

    /**
     * This method will search for all servers listening to a specific port
     * 
     * 
     * @param broadcasterPort
     * @throws IOException
     * @throws UnknownHostException 
     */
    private void sendSearchBroadcast(int broadcasterPort) throws IOException, UnknownHostException {
        byte[] data = new byte[1];
        DatagramPacket broadcastPacket = new DatagramPacket(data, data.length, InetAddress.getByName("255.255.255.255"), broadcasterPort);
        this.finderSocket.send(broadcastPacket);
    }
    
    /**
     * This method will close any socket found during the broadcast 
     * 
     */
    public void close()
    {
        this.finderSocket.close();
    }
}

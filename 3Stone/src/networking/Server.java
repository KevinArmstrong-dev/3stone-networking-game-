/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

/**
 *
 * @author 1638876
 */
public class Server {
    
   private static final int BUFSIZE = 32;
   
   public static void main(String[] args) throws IOException {
       
    int servPort = 50000;

    // Create a server socket to accept client connection requests
    ServerSocket servSock = new ServerSocket(servPort);

    int recvMsgSize;   // Size of received message
    byte[] receiveBuf = new byte[BUFSIZE];  // Receive buffer

    while (true) { // Run forever, accepting and servicing connections
      Socket clntSock = servSock.accept();     // Get client connection

      SocketAddress clientAddress = clntSock.getRemoteSocketAddress();
      
      
      System.out.println(clntSock.getChannel()+ " Testing");
      System.out.println("Handling client at " + clientAddress);
      
      InputStream in = clntSock.getInputStream();
      OutputStream out = clntSock.getOutputStream();

      // Receive until client closes connection, indicated by -1 return
      while ((recvMsgSize = in.read(receiveBuf)) != -1) {
           
        out.write(receiveBuf, 0, recvMsgSize);
      }

      clntSock.close();  // Close the socket.  We are done with this client!
    }
  }
   
   
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;

/**
 *
 * @author KEVIN
 */
public class ClientPlayer {

   private String serverIp;
   private int portNumber;
   private Socket socket;
   private InputStream in;
   private OutputStream out;
   private String message;
   
   public ClientPlayer(String hostServer){
       try{
            String server =hostServer;       // Server name or IP address
        // Convert argument String to bytes using the default character encoding
        String message = "The client says";
         byte[] data = message.getBytes();

        int servPort = 50000;

         // Create socket that is connected to server on specified port
         Socket socket = new Socket(hostServer, servPort);
         System.out.println("Connected to server...sending echo string");

         InputStream in = socket.getInputStream();
          out = socket.getOutputStream();

         out.write(data);  // Send the encoded string to the server

         // Receive the same string back from the server
        int totalBytesRcvd = 0;  // Total bytes received so far
        int bytesRcvd;           // Bytes received in last read
        while (totalBytesRcvd < data.length) {
             if ((bytesRcvd = in.read(data, totalBytesRcvd,  
                        data.length - totalBytesRcvd)) == -1)
            throw new SocketException("Connection closed prematurely");
             totalBytesRcvd += bytesRcvd;
        }  // data array is full

         System.out.println("Received: " + new String(data));

         socket.close();  // Close the socket and its streams
       }
       catch(IOException ex){
           System.out.println("Input ourput error" + ex);
       }
     
  }
   
   public void connectToServer() throws IOException{
       
       socket = new Socket(this.serverIp,this.portNumber);
        in = socket.getInputStream();
        out = socket.getOutputStream();
   }
   
   public void startGame(String msgTest){
     
   }
   

}

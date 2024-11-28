package OS_project;

import java.net.Socket;

public class Client2 {
    public static void main(String args[]) {
        try {
            // Get a Socket to the daytime service,
            Socket client1 = new Socket("192.168.10.12", 1300);
            System.out.println("Connected with server " +
            client1.getInetAddress() + ":" +
            client1.getPort());
            // Set the socket option just in case server stalls
            client1.setSoTimeout(2000);
            // Read from the server
            // Close the connection
            client1.close();
        } catch(Exception ioe) {
            System.out.println("Error" + ioe);
        }
    }
}
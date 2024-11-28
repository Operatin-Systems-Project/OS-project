package OS_project;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client1 {
    public static void main(String args[]) {
        BufferedReader from_server = null;
		BufferedReader from_user = null;
		PrintWriter to_server = null;
		String serverInput, userInput;
        
        try {
            Socket client1 = new Socket("192.168.10.12", 1300);
			// from_server=new BufferedReader(new InputStreamReader(client1.getInputStream()));
			// from_user=new BufferedReader(new InputStreamReader(System.in));
			// to_server = new PrintWriter(client1.getOutputStream());
            // Get a Socket to the daytime service,
            System.out.println("Connected with server " +
            client1.getInetAddress() + ":" +
            client1.getPort());
            // System.out.println(from_server.readLine());
            // System.out.println("Enter your request");
            // userInput = from_user.readLine();
            // to_server.println(userInput);
			// to_server.flush();

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

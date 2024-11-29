package OS_project;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client1 {
    public static void main(String args[]) {
        BufferedReader from_server = null;
		Scanner from_user = null;
		PrintWriter to_server = null;
		String serverMessage, userInput = null;
        
        try {
            Socket client1 = new Socket("localhost", 1300);
			 from_server=new BufferedReader(new InputStreamReader(client1.getInputStream()));
			 from_user=new Scanner(System.in);
			 to_server = new PrintWriter(client1.getOutputStream(), true);
            System.out.println("Connected with server " +client1.getInetAddress() + ":" +client1.getPort());
            client1.setSoTimeout(2000);

            while ((userInput == null) || !(userInput.equals("0"))) {
            	serverMessage = from_server.readLine();
            	if(serverMessage.equals("PUT")) {
            		
            	}
            	System.out.println(serverMessage);
            	userInput = from_user.next();
            	to_server.println(userInput);
            }

            client1.close();
        } catch(Exception ioe) {
            System.out.println("Error" + ioe);
        }
    }
}

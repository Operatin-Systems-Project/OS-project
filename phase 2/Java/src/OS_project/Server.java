package OS_project;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server {
	private static ArrayList<ArrayList<Long>> clientsRequests= new ArrayList<>();

	public static void main(String args[]) {
		ServerSocket server = null;
		Socket client = null;
		NetworkService n1 = null;
		MainService mainService = null;
		int clientIndex = 0;
		InfoService infoService = null;
		try {

			server = new ServerSocket(1300);
			System.out.println(server.getInetAddress().getHostAddress());
			System.out.println("Server is up!");
			infoService = new InfoService();
			while (true) {
				client = server.accept();
				System.out.println("Established connection with Client "+ (clientIndex+1) + " (" + client.getInetAddress() +":"+ client.getPort()+")");
				clientsRequests.add(new ArrayList<Long>());
				if (n1 == null) {
					n1 = new NetworkService(client);
					n1.start();
				}
				mainService = new MainService(client, clientIndex, clientsRequests.get(clientIndex), n1, infoService);
				mainService.start();
				
				clientIndex++;
			}
		} catch (IOException ioe) {
			System.out.println("Error" + ioe);
		} finally {
			try {
				if (server != null)
					server.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
	
	}
package OS_project;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class MainService extends Thread {

	private Socket client;
	private PrintWriter toClient = null;
	private BufferedReader fromClient = null;
	private final int COOLDOWN = 300000; // 5 mins in ms
	private NetworkService n1;
	private int index;
	private ArrayList<Long> requests;
	private String msgToClient1 = "Request System Info? [Y to request, N to disconnect]";
	private String msgToClient2 ="";
	private String msgToClientFull;
	InfoService infoService;



	public MainService(Socket client, int index, ArrayList<Long> requests, NetworkService n1, InfoService infoService) {
		this.client = client;
		this.n1 = n1;
		this.index = index;
		this.requests = requests;
		this.infoService = infoService;
		requests.add(System.currentTimeMillis() - COOLDOWN);	
	}

	public void run() {
		try {

			if (index == 1) {
				n1.addSocket(client);
			}
				while (n1.isAlive())
					;

				toClient = new PrintWriter(client.getOutputStream(), true);
				fromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));

				String ans = null;
				while (true) {
					msgToClientFull = msgToClient2 + msgToClient1;
					msgToClient2="";
					toClient.println(msgToClientFull);
					
					ans = fromClient.readLine();

					if (ans.equalsIgnoreCase("Y")) {

						if ((System.currentTimeMillis() - requests.get(requests.size() - 1) > COOLDOWN)) {
							requests.add(System.currentTimeMillis());
							// Run system.sh & send file
							toClient.println("PUT");
							toClient.flush();
							infoService.transferFile(client);
							
						} else {
							msgToClient2 = "You have to wait 5 mins before making that request again ";
						}
					} else {
						break;
					}
				}
				System.out.println("Client " + (index+1) + "Disconnecting...");
				infoService.printRequestLog(requests);

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (toClient != null)
					toClient.close();
				if (client != null)
					client.close();
				if (fromClient != null)
					fromClient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} // end of finally

	} // end of run
}

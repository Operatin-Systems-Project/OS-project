package OS_project;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
public class NetworkService extends Thread{
	private String script = "/home/vm1/Network.sh";
	private InetAddress client1ip;
	private InetAddress client2ip;
	private ArrayList<Socket> myClients = new ArrayList<>();
	private BufferedReader terminalReader = null;
	
	public NetworkService(Socket client1) {
		client1ip = client1.getInetAddress();
		myClients.add(client1);
	}
	
	public void run()
	{
		System.out.println("We Need Two clients to connect");
		do {
			System.out.print("");
		} while (myClients.size() < 2)
			;

		try {
//			System.out.println("Testing the connection to both clients");
//			ProcessBuilder processBuilder = new ProcessBuilder("bash",script,client1ip.getHostAddress(),client2ip.getHostAddress());
//			processBuilder.redirectErrorStream(true); // Combine standard error and output streams
//			Process NetwrokTest = processBuilder.start();
//
//			terminalReader = new  BufferedReader(new InputStreamReader (NetwrokTest.getInputStream()));
//			
//			String terminaloutput = terminalReader.readLine();
//			while ( terminaloutput!= null){
//				System.out.println(terminaloutput);
//				terminaloutput = terminalReader.readLine();
//			}
//			NetwrokTest.waitFor();
			
			System.out.println("Tested conncetion");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
				try {
					if(terminalReader !=null)
					terminalReader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}//end of finally
		
	}//end of run

	
	public void addSocket(Socket client2) {
		client2ip = client2.getInetAddress();
		myClients.add(client2);
	}
	
	
	

}
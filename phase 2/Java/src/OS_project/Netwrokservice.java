package OS_project;
import java.io.*;
import java.net.InetAddress;
public class Netwrokservice extends Thread{
	String script = "/home/vm1/Network.sh";
	InetAddress client1ip;
	InetAddress client2ip;
	BufferedReader terminalReader = null;
	
	public Netwrokservice(InetAddress client1ip,InetAddress client2ip) {
		super();
		this.client1ip = client1ip;
		this.client2ip = client2ip;
		
	}
	
	public void run()
	{
		try {
			ProcessBuilder processBuilder = new ProcessBuilder("bash",script,client1ip.getHostAddress(),client2ip.getHostAddress());
			processBuilder.redirectErrorStream(true); // Combine standard error and output streams
			Process NetwrokTest = processBuilder.start();

			terminalReader = new  BufferedReader(new InputStreamReader (NetwrokTest.getInputStream()));
			
			String terminaloutput = terminalReader.readLine();
			while ( terminaloutput!= null){
				System.out.println(terminaloutput);
				terminaloutput = terminalReader.readLine();
			}
			NetwrokTest.waitFor();
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
	

}
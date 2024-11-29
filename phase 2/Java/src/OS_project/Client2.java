package OS_project;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client2 {
    public static void main(String args[]) {
        BufferedReader from_server=null;
        BufferedReader from_user=null;
        PrintWriter to_server=null;
        BufferedReader catoutput =null;
        try {
            // Get a Socket to the daytime service,
            Socket client1 = new Socket("192.168.10.12", 1300);
           
            from_server= new BufferedReader(new InputStreamReader(client1.getInputStream()));
            from_user=new BufferedReader(new InputStreamReader(System.in));
            to_server= new PrintWriter(client1.getOutputStream());
            System.out.println("Connected with server " +
            client1.getInetAddress() + ":" +
            client1.getPort());
            String username=System.getProperty("user.name");
            to_server.println(username);
            to_server.flush();
            Thread.sleep(5000);
            ProcessBuilder catInfo = new ProcessBuilder("cat","/home/vm3/info.txt");
            catInfo.redirectErrorStream(true);
            Process execCatInfo = catInfo.start();
            catoutput = new BufferedReader(new InputStreamReader(execCatInfo.getInputStream()));
            String OutputInfo= catoutput.readLine();
            while(OutputInfo!=null){
                System.out.println(OutputInfo);
                OutputInfo= catoutput.readLine();
        }
            catoutput.close();
            execCatInfo.waitFor();

            // Set the socket option just in case server stalls
            client1.setSoTimeout(2000);
            // Read from the server
            // Close the connection
            client1.close();
        } catch(Exception e) {
            System.out.println("Error" + e);
        }
    }
}
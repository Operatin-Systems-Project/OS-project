package OS_project;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import java.io.*;
import java.net.Socket;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.SimpleFormatter;

public class InfoService {
	SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
	
    public synchronized void transferFile(Socket nextClient) {
        String IP = nextClient.getInetAddress().getHostAddress();       
        BufferedReader from_client;
        String clientusername;
        JSch jsch = new JSch();
        Session session = null;
        ChannelSftp channelSftp = null;
        PrintWriter to_server =  null;
        String password = null;
        
        try {
                     
            from_client = new BufferedReader(new InputStreamReader(nextClient.getInputStream()));
            to_server = new PrintWriter(nextClient.getOutputStream());
            
            clientusername=from_client.readLine();
            
            File outputFile = new File("info.txt");
            ProcessBuilder SystemRunCommand = new ProcessBuilder("bash", "/home/vm1/System.sh");
            SystemRunCommand.redirectOutput(outputFile);
            SystemRunCommand.redirectErrorStream(true); // Combine standard error and output streams
            Process Systeminfo = SystemRunCommand.start();
            Systeminfo.waitFor();
            
            Thread.sleep(500);
            session = jsch.getSession(clientusername, IP, 22);
            to_server.println("Enter SFTP password: ");
            to_server.flush();
            
            password = from_client.readLine();
            session.setPassword(password);
            
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            session.connect();
            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();
            
            File file = new File("/home/vm1/info.txt");
            channelSftp.put(new FileInputStream(outputFile), outputFile.getName());

        } catch (Exception e) {
            System.err.println("Error initializing client communication streams: " + e.getMessage());
        }finally {
            // Disconnect the channel and session
            if (channelSftp != null) {
                channelSftp.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
        }
    }
    
    public synchronized void printRequestLog(ArrayList<Long> requests) {
    	Date date = new Date(requests.get(0) + 300000);
    	System.out.printf("[%s] Connection Established\n", format.format(date));
    	for(int i = 1; i < requests.size(); i++) {
    		date.setTime(requests.get(i));
    		System.out.printf("[%s] System info sent to client\n", format.format(date));
    	}
    }


}

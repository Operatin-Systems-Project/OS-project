package OS_project;

import java.io.*;
import java.net.Socket;

public class FileTransferService extends Thread {

	private Socket client;
	private DataOutputStream toClient = null;
	private FileInputStream fileInput = null;
	private File systemInfoFile = null;
	private final String filePath = "C:\\Users\\ali23\\Desktop\\QU\\Fall 2024\\CMPS 405\\Project\\Phase 2\\Code\\testFile.txt";
	private final int bufferSize = 4096;

	
	public FileTransferService(Socket client) {
		this.client = client;
	}

	public void run() {
		try {
			toClient = new DataOutputStream(client.getOutputStream());
			systemInfoFile = new File(filePath);
			fileInput = new FileInputStream(systemInfoFile);

			toClient.writeLong(systemInfoFile.length()); //Send file size ahead of transfer
			int bytes = 0;
			byte[] buffer = new byte[bufferSize];
			bytes = fileInput.read(buffer);
			while(bytes != -1) { //while there still exists input from file
				toClient.write(buffer, 0, bytes);
				toClient.flush();
				bytes = fileInput.read(buffer);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (toClient != null)
					toClient.close();
				if (client != null)
					client.close();
				if (fileInput != null)
					fileInput.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} // end of finally

	}// end of run

}



import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client2 {
    static BufferedReader from_server = null;
    static PrintWriter to_server = null;
    static BufferedReader catoutput = null;
    static Scanner from_user;

    public static void main(String args[]) {

        try {
            String Clientinfo="/home/vm3/Desktop/vm3/Clientinfo.sh";
            String Search= "/home/vm3/Desktop/vm3/Search.sh";
            Thread clientInfoThread = new Thread(new threadd(Clientinfo, "Clientinfo"));
            Thread searchThread = new Thread(new threadd(Search, "Search"));
            clientInfoThread.start();
            searchThread.start();
            Socket client1 = new Socket("192.168.10.12", 1300);
            //Socket client1 = new Socket("localhost", 1300);

            String serverMessage;
            String userInput = null;
            from_user = new Scanner(System.in);
            from_server = new BufferedReader(new InputStreamReader(client1.getInputStream()));
            to_server = new PrintWriter(client1.getOutputStream());
            System.out.println("Connected with server " + client1.getInetAddress() + ":" + client1.getPort());

            while ((userInput == null) || !(userInput.equals("0"))) {
                serverMessage = from_server.readLine();
                if(serverMessage.equals("PUT")) {
                    System.out.println("Wait...");
                    transfer();
                } else {
                    System.out.println(serverMessage);
                    userInput = from_user.next();
                    to_server.println(userInput);
                    to_server.flush();
                }
            }

            // Set the socket option just in case server stalls
            client1.setSoTimeout(2000);

            client1.close();
        } catch (Exception e) {
            System.out.println("Error" + e);
        }
    }

    public static void transfer() {
        String input;
        try {

            String username = System.getProperty("user.name");
            to_server.println(username);
            to_server.flush();

            System.out.println(from_server.readLine());
            input = from_user.next();
            to_server.println(input);
            to_server.flush();

            Thread.sleep(5000);
            ProcessBuilder catInfo = new ProcessBuilder("cat", "/home/vm3/info.txt");
            catInfo.redirectErrorStream(true);
            Process execCatInfo = catInfo.start();
            catoutput = new BufferedReader(new InputStreamReader(execCatInfo.getInputStream()));
            String OutputInfo = catoutput.readLine();

            while (OutputInfo != null) {
                System.out.println(OutputInfo);
                OutputInfo = catoutput.readLine();
            }

            catoutput.close();
            execCatInfo.waitFor();
        } catch (Exception e) {
            System.out.println("Error" + e);
        }
    }
}


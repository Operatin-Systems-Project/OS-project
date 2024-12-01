

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client1 {
    static BufferedReader from_server = null;
    static PrintWriter to_server = null;
    static BufferedReader catoutput = null;
    static Scanner from_user;
    public static void main(String args[]) {

        try {
            runterminal();
            String check= "/home/vm2/Desktop/vm2/check.sh";
            Thread checkThread = new Thread(new threadd(check, "check"));
            checkThread.start();
            checkThread.join();
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
            ProcessBuilder catInfo = new ProcessBuilder("cat", "/home/vm2/info.txt");
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
        }
    }
        public static void runterminal() {
            try {
                // Command to open a new terminal and run the script
                String[] command = {
                        "gnome-terminal", // Or "xterm", "konsole", etc.
                        "--",             // Separator for arguments
                        "bash",           // Shell to run the script
                        "-c",             // Command flag
                        "/home/vm2/Desktop/vm2/login.sh; exec bash" // Run script and keep terminal open
                };

                // Start the process
                ProcessBuilder pb = new ProcessBuilder(command);
                pb.start();

                System.out.println("Script is running in a new terminal.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


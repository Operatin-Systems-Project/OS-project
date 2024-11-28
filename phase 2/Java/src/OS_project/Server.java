package OS_project;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server {
    private static ArrayList<Socket> myScokets = new ArrayList<>();  
    public static void main(String args[]){
        
    try{
        
        ServerSocket server = new ServerSocket(1300);
        System.out.println(server.getInetAddress().getHostAddress());
        while(true){
            do{
                if (myScokets.size()<2)
                 System.out.println("We Need Two clients to connect");
                Socket Client = server.accept();
                myScokets.add(Client);
            }while(myScokets.size()<2);
            System.out.println("Testing the connection to both clinets");
            Netwrokservice N1 = new Netwrokservice(myScokets.get(0).getInetAddress(),myScokets.get(1).getInetAddress());
            N1.start();
             for (Socket client: myScokets){
                client.close();
            }
            
        }
    }catch(IOException ioe){
        System.out.println("Error" + ioe);
    }
   
}
}
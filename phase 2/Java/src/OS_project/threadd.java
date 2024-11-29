import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


class threadd extends Thread{
    private InputStream inp;
    private String name;

    public threadd(InputStream inp, String name){
        this.inp = inp;
        this.name = name;
    }
    public void run(){
        try(BufferedReader buff=new BufferedReader(new  InputStreamReader(inp))) {
            String line;
            while((line= buff.readLine())!=null) {
                System.out.println("["+name+ "] " +line);
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}


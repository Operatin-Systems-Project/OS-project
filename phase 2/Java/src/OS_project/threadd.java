import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


class threadd extends Thread{
    private String path;
    private String name;
    Process process;

    public threadd(String path, String name){
        this.path = path;
        this.name = name;
    }
    public Process getProcess(){
        return process;
    }
    public void run(){
        ProcessBuilder builder=new ProcessBuilder(path);
        builder.redirectErrorStream(true);
        try{
            process = builder.start();

            try(BufferedReader buff=new BufferedReader(new  InputStreamReader(process.getInputStream()))) {

                String line;
                while((line= buff.readLine())!=null) {
                    System.out.println("["+name+ "] " +line);
                }
                process.waitFor();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        catch (IOException e) {
            System.err.println("error while executing " + name);
        }

        catch (InterruptedException e) {
            System.err.println(name + " got interrupted.");


        }


    }
}

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


class threadd extends Thread{
    private String inp;
    private String name;

    public threadd(String inp, String name){
        this.inp = inp;
        this.name = name;
    }
    public void run(){
        ProcessBuilder builder=new ProcessBuilder();
        builder.redirectErrorStream(true);
        builder.command("bash", "-c", "stdbuf -oL " + inp + " 2>&1");
        try{
            Process process = builder.start();

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


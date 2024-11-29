import java.io.IOException;

class Main implements Runnable{

    private String path;
    private String name;

    public Main(String path, String name) {
        this.path = path;
        this.name = name;
    }

    public static void main(String[] args){
        String Clientinfo="/home/vm2/Desktop/Clientinfo.sh";
        String Search= "/home/vm2/Desktop/vm2/Search.sh";
        Thread ClientinfoThread=new Thread(new Main(Clientinfo,"Clientinfo"));
        Thread SearchThread=new Thread(new Main(Search,"Search"));
        ClientinfoThread.start();
        SearchThread.start();
    }

    public void run(){
        ProcessBuilder builder=new ProcessBuilder();
        builder.redirectErrorStream(true);
        builder.command("bash", "-c", "stdbuf -oL " + path + " 2>&1");
        try {
            Process process = builder.start();
            threadd th=new threadd(process.getInputStream(), name);
            th.start();
            process.waitFor();
        } catch (IOException e) {
            System.err.println("error while executing " + name);
        }

        catch (InterruptedException e) {
            System.err.println(name + " got interrupted.");


        }
    }
}








public class Announcer extends Thread {
    private static int currentNum;

    public Announcer() {
        System.out.println("Announcer created");
        currentNum = 1;
    }

    public void run() {
        while(!DMV.finished){
            try {
                DMV.announcer.acquire();
                DMV.agent_line.acquire();
                callNumber();
                DMV.call.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void callNumber(){
        System.out.printf("Announcer calls number %d\n", currentNum++);
    }
}

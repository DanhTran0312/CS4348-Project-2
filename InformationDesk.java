public class InformationDesk extends Thread {
    private static int unique_number;

    InformationDesk() {
        unique_number = 0;
        System.out.println("Information desk created");
    }

    public void run() {
        while (!DMV.finished) {
            try {
                DMV.info_line.acquire();
                IncreaseUniqueNumber();
                DMV.number.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static int getUniqueNumbner() {
        return unique_number;
    }

    public void IncreaseUniqueNumber(){
        unique_number++;
    }
}

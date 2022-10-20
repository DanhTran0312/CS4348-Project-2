import java.util.concurrent.Semaphore;

public class DMV {
    public static Semaphore info_line = new Semaphore(0, true);
    public static Semaphore number = new Semaphore(0, true);

    public static Semaphore announcer = new Semaphore(0, true);
    public static Semaphore call = new Semaphore(0, true);

    public static Semaphore agent_line = new Semaphore(4, true);
    public static Semaphore enter_line = new Semaphore(0, true);
    public static Semaphore agent_available = new Semaphore(2, true);
    public static Semaphore cust_ready = new Semaphore(0, true);
    public static Semaphore exam = new Semaphore(0, true);
    public static Semaphore finish_exam = new Semaphore(0, true);
    public static Semaphore serve = new Semaphore(0, true);
    public static Semaphore license = new Semaphore(0, true);
    public static Semaphore exit = new Semaphore(0, true);

    private static final int CUSTOMER_NUM = 20;
    private static final int AGENT_NUM = 2;
    static boolean finished = false;

    public static void main(String[] args) {
        Announcer announcer = new Announcer();
        InformationDesk infoDesk = new InformationDesk();
        announcer.start();
        infoDesk.start();
        Agent[] agents = new Agent[AGENT_NUM];
        Customer[] customers = new Customer[CUSTOMER_NUM];

        for (int i = 0; i < agents.length; i++) {
            agents[i] = new Agent(i + 1);
            agents[i].start();
        }

        for (int i = 0; i < customers.length; i++) {
            customers[i] = new Customer(i + 1);
            customers[i].start();
        }

        for (int i = 0; i < customers.length; i++) {
            try {
                customers[i].join();
                System.out.printf("Customer %d was joined\n", customers[i].getCustId());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        finished = true;
        System.out.println("Done");
        System.exit(1);
        // for (int i = 0; i < agents.length; i++) {
        //     try {
        //         agents[i].join();

        //     } catch (InterruptedException e) {
        //         e.printStackTrace();
        //     }
        // }

        // try {
        //     announcer.join();
        //     infoDesk.join();
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }

    }
}

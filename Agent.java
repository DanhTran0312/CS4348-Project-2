import java.util.*;

public class Agent extends Thread {
    private int id;
    private static Queue<Customer> customerQueue = new LinkedList<>();
    private Customer currentCustomer;

    Agent(int id) {
        this.id = id;
        System.out.printf("Agent %d created\n", this.id);
    }

    public void run() {
        while (!DMV.finished) {
            try {
                DMV.enter_line.acquire();
                DMV.cust_ready.acquire();
                getCustomer();
                serve();
                DMV.serve.release();
                askExam();
                DMV.exam.release();
                DMV.finish_exam.acquire();
                giveLicense();
                DMV.license.release();
                DMV.exit.acquire();
                DMV.agent_available.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int getAgentId() {
        return id;
    }

    public static int getQueueLength(){
        return customerQueue.size();
    }

    void getCustomer() {
        currentCustomer = customerQueue.poll();
        currentCustomer.setAgent(this.id);
    }

    public static void enQueue(Customer customer) {
        customerQueue.add(customer);
    }

    void serve() {
        System.out.printf("Agent %d is serving customer %d\n", id, currentCustomer.getCustId());
    }

    void askExam() {
        System.out.printf("Agent %d asks customer %d to take photo and eye exam\n", id, currentCustomer.getCustId());
    }

    void giveLicense() {
        System.out.printf("Agent %d gives license to customer %d\n", id, currentCustomer.getCustId());
    }
}

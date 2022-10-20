public class Customer extends Thread {
    private int id;
    private int number;
    private int agentId;

    Customer(int id) {
        this.id = id;
        System.out.printf("Customer %d, created enters DMV\n", this.id);
    }

    public void run() {
        try {
            DMV.info_line.release();
            DMV.number.acquire();
            getNumber(InformationDesk.getUniqueNumbner());
            DMV.announcer.release();
            DMV.call.acquire();
            enterAgentLine();
            DMV.enter_line.release();
            DMV.agent_available.acquire();
            DMV.cust_ready.release();
            DMV.serve.acquire();
            beingServed();
            DMV.agent_line.release();
            DMV.exam.acquire();
            takeExam();
            DMV.finish_exam.release();
            DMV.license.acquire();
            exit();
            DMV.exit.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setAgent(int id) {
        agentId = id;
    }

    public int getCustId(){
        return this.id;
    }

    void beingServed(){
        System.out.printf("Customer %s is being served by agent %d\n", id, agentId);
    }

    void getNumber(int n) {
        number = n;
        System.out.printf("Customer %d gets number %d, enters waiting room\n", id, number);
    }

    void enterAgentLine() {
        Agent.enQueue(this);
        System.out.printf("Customer %d moves to agent line\n", id);
    }

    void takeExam() {
        System.out.printf("Customer %d compltes photo and eye exam for agent %d\n", id, agentId);
    }

    void exit() {
        System.out.printf("Customer %d gets license and departs\n", id);
    }
}

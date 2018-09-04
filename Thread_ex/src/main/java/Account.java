public class Account {
    String holderName;
    float amount;
    public Account(String name, float amt) {
        holderName = name;
        amount = amt;
    }
    public  synchronized void deposit(float amt) { // 注意synchronized字段
        amount += amt;
    }
    public synchronized void withdraw(float amt) { // 注意synchronized字段
        amount -= amt;
    }
    public float checkBalance() {
        return amount;
    }
}

class BankAccount {
    private int balance = 1000;

    // Synchronized method to ensure thread-safe withdrawal
    public synchronized void withdraw(int amount) {
        if (balance >= amount) {
            balance -= amount;
            System.out.println(Thread.currentThread().getName() + " withdrew " + amount + " | Remaining Balance: " + balance);
        } else {
            System.out.println(Thread.currentThread().getName() + " tried to withdraw " + amount + " | Insufficient Balance");
        }
    }
}

class Customer implements Runnable {
    private BankAccount account;

    public Customer(BankAccount account) {
        this.account = account;
    }

    public void run() {
        for (int i = 0; i < 3; i++) {
            int withdrawalAmount = (int) (Math.random() * 1000) + 1;
            account.withdraw(withdrawalAmount);
        }
    }
}

public class MultiThreadedApplication {

    public static void main(String[] args) {
        BankAccount account = new BankAccount();

        // Create multiple threads (customers) and start them
        Thread[] threads = new Thread[5];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new Customer(account), "Customer-" + (i + 1));
            threads[i].start();
        }

        // Wait for all threads to finish
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("All transactions completed.");
    }
}

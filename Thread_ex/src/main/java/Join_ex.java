public class Join_ex {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new Runnable() {
            public void run() {
                System.out.println("First task started");
                System.out.println("Sleep for 2 seconds");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("First task completed");
            }
        });
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                System.out.println("Second task completed");
            }
        });
        t.start();
        t.join();  // 可以注释掉试试看
        t1.start();
    }
}

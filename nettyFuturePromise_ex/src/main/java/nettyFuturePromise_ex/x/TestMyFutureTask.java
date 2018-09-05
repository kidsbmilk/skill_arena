package nettyFuturePromise_ex.x;

public class TestMyFutureTask extends MyFutureTask<Integer> {
    public static void main(String[] args) {

        final TestMyFutureTask testMyFutureTask = new TestMyFutureTask();
        testMyFutureTask.addListener(new IFutureListener<Integer>() {
            @Override
            public void operationCompleted(IFuture iFuture) {
                System.out.println("完成了");
            }
        });
        Thread thread1 = new Thread(testMyFutureTask);
        thread1.setName("excutor");
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int t = testMyFutureTask.get();
                    System.out.println(t);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread2.setName("get2");
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int t = testMyFutureTask.get();
                    System.out.println(t);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread3.setName("get3");

        thread1.start();
        thread2.start();
        thread3.start();
    }

    @Override
    public java.lang.Integer call() throws Exception {
        int a = 1;
        int b = 3;
        Thread.sleep(100);
        return a + b;
    }
}

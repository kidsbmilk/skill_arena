import java.io.IOException;

public class Setdaemon_ex extends Thread {
    public void run() {
        for (int i=0;;++i) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {}
            System.out.println(i);
        }
    }
    public static void main(String[] args) {
        Setdaemon_ex test = new Setdaemon_ex();
        test.setDaemon(true); // 调试时，可以设置为false，那么程序是个死循环，没有退出条件。设置为true，即可主线程结束，test线程也结束。
        test.start();
        System.out.println("isDaemon = " + test.isDaemon());
        try {
            System.in.read(); // 接受输入使程序在此停顿，一旦接收到用户输入，main线程结束，守护线程自动结束。
        } catch (IOException ex) {}
    }
}

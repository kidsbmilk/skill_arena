public class BSync {
    int totalThreads;
    int currentThreads;
    public BSync(int x) {
        totalThreads = x;
        currentThreads = 0;
    }

    /**
     * 当对一个线程调用 wait() 时，该线程就被有效阻塞，只到另一个线程对同一个对象调用 notify() 或 notifyAll() 为止。
     *
     * 因此，在前一个示例中，不同的线程在完成它们的工作以后将调用 waitForAll() 函数，最后一个线程将触发 notifyAll() 函数，
     *
     * 该函数将释放所有的线程。第三个函数 notify() 只通知一个正在等待的线程，当对每次只能由一个线程使用的资源进行访问限制时，
     *
     * 这个函数很有用。但是，不可能预知哪个线程会获得这个通知，因为这取决于 Java 虚拟机 (JVM) 调度算法。
     */
    public synchronized void waitForAll() {
        currentThreads ++;
        if(currentThreads < totalThreads) {
            try {
                wait(); // 屏蔽同步
            } catch (Exception e) {}
        } else {
            currentThreads = 0;
            notifyAll(); // 屏蔽同步
        }
    }
}

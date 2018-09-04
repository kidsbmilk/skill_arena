package callback;

public class Worker {

    public void doWork() {
        Fetcher fetcher = new MyFatcher(new Data(4, 2));
        fetcher.fetchData(new FetcherCallback() {
            @Override
            public void onData(Data data) throws Exception {
                System.out.println("Data received: " + data);
            }

            @Override
            public void onError(Throwable cause) {
                System.out.println("An error accour: " + cause.getMessage());
            }
        });
    }

    public static void main(String[] args) {
        Worker w = new Worker();
        w.doWork();
    }
}

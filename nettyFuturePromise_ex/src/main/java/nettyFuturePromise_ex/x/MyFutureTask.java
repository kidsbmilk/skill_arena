package nettyFuturePromise_ex.x;

public abstract class MyFutureTask<V> extends AbstractFuture<V> implements Runnable {

    @Override
    public void run() {
        try {
            V t = call();
            setSuccess(t);
        } catch (Exception e) {
            setFailure(e);
        }
    }

    @Override
    public abstract V call() throws Exception;
}

package nettyFuturePromise_ex.x;

public interface IFutureListener<V> {

    void operationCompleted(IFuture iFuture);
}

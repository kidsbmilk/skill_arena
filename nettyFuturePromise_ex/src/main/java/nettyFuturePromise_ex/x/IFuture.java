package nettyFuturePromise_ex.x;

import java.util.concurrent.Future;

public interface IFuture<V> extends Future<V> {

    boolean isSuccess(); // 判断是否成功
    V getNow(); // 获取值
    Throwable cause(); // 获取异常
    boolean isCancellable(); // 是否可以取消， 一旦开始不能取消
    IFuture<V> await() throws InterruptedException;
    IFuture<V> awaitUninterruptibly();
    IFuture<V> addListener(IFutureListener<V> listener); // 添加监听器
    IFuture<V> removeListener(IFutureListener<V> listener); // 移除监听器
}

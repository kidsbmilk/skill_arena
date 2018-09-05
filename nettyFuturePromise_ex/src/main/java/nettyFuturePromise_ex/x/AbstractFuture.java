package nettyFuturePromise_ex.x;

import java.util.Collection;
import java.util.concurrent.*;

public abstract class AbstractFuture<V> implements IFuture<V>, Callable<V> {

    private volatile Object result; // 保证多线程的可见性

    protected Collection<IFutureListener<V>> listeners = new CopyOnWriteArrayList<IFutureListener<V>>();

    private static final SuccessSignal SUCCESS_SIGNAL = new SuccessSignal();

    @Override
    public boolean isSuccess() {
        return result == null ? false : !(result instanceof CauseHolder);
    }

    @Override
    public V getNow() {
        return (V) (result.equals(SUCCESS_SIGNAL) ? null : result);
    }

    @Override
    public Throwable cause() {
        if(result != null && result instanceof CauseHolder) {
            return ((CauseHolder) result).cause;
        }
        return null;
    }

    @Override
    public boolean isCancellable() {
        return false;
    }

    @Override
    public IFuture<V> await() throws InterruptedException {
        return await0(true);
    }

    private IFuture<V> await0(boolean interruptable) throws InterruptedException {
        if(!isDone()) { // 已完成直接返回
            // 若允许中断且被中断，而抛出异常
            if(interruptable && Thread.interrupted()) {
                throw new InterruptedException("thread" + Thread.currentThread() + "has been interrupted");
            }

            boolean interrupted = false;
            synchronized (this) {
                while(!isDone()) {
                    try {
                        wait(); // 线程等待
                    } catch (InterruptedException e) {
                        if(interruptable) {
                            throw e;
                        } else {
                            interrupted = true;
                        }
                    }
                }
            }
            if(interrupted) {
                // 设置标志位的原因是，应为wait返回后，标志位会被clear
                // 重新设置标志位
                Thread.currentThread().interrupt();
            }
        }
        return this;
    }

    @Override
    public IFuture<V> awaitUninterruptibly() {
        try {
            return await0(false);
        } catch (InterruptedException e) {
            throw new InternalError();
        }
    }

    @Override
    public IFuture<V> addListener(IFutureListener<V> listener) {
        if(listener == null) {
            throw new NullPointerException("listener not null");
        }
        if(isDone()) {
            notifyListener(listener);
            return this;
        }
        synchronized (this) {
            if(!isDone()) {
                listeners.add(listener);
                return this;
            }
        }
        notifyListener(listener);
        return this;
    }

    @Override
    public IFuture<V> removeListener(IFutureListener<V> listener) {
        if(listener == null) {
            throw new NullPointerException("listener not null");
        }
        if(!isDone()) {
            listeners.remove(listener);
        }
        return this;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        if(isDone()) {
            return false;
        }
        synchronized (this) {
            if(isDone()) {
                return false;
            }
            result = new RuntimeException("主动取消");
            notifyAll();
        }
        notifyListeners();
        return true;
    }

    @Override
    public boolean isCancelled() {
        return result != null && result instanceof CauseHolder && ((CauseHolder) result).cause instanceof CancellationException;
    }

    @Override
    public boolean isDone() {
        return result != null;
    }

    @Override
    public V get() throws InterruptedException, ExecutionException {
        await();
        Throwable cause = cause();
        if(cause == null) {
            return getNow();
        }
        if(cause instanceof CancellationException) {
            throw (CancellationException) cause;
        }
        throw new ExecutionException(cause);
    }

    //

    @Override
    public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }

    protected void notifyListeners() {
        for (IFutureListener<V> listener : listeners) {
            notifyListener(listener);
        }

    }

    protected void notifyListener(IFutureListener listener) {
        listener.operationCompleted(this);
    }

    protected IFuture<V> setFailure(Throwable cause) {
        if (setFailure0(cause)) {
            notifyListeners();
            return this;
        }
        throw new IllegalStateException("complete already:" + this);
    }

    private boolean setFailure0(Throwable cause) {
        if (isDone()) {
            return false;
        }
        synchronized (this) {
            if (isDone()) {
                return false;
            }
            result = new CauseHolder(cause);
            notifyAll();
        }
        return true;
    }

    protected IFuture<V> setSuccess(Object result) {
        if (setSuccess0(result)) {
            notifyListeners();
            return this;
        }
        throw new IllegalStateException("complete already:" + this);
    }

    private boolean setSuccess0(Object result) {
        if (isDone()) {
            return false;
        }
        synchronized (this) {
            if (isDone()) {
                return false;
            }
            if (result == null) {//正常结束，返回值为空
                this.result = SUCCESS_SIGNAL;
            } else {
                this.result = result;
            }
            notifyAll();
        }
        return true;
    }

    private static class SuccessSignal {
    }

    private static final class CauseHolder {
        final Throwable cause;

        CauseHolder(Throwable cause) {
            this.cause = cause;
        }

    }

}

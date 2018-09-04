package io.concurrencymodel.example.SayHello;

import io.concurrencymodel.util.ActiveObject;
import io.concurrencymodel.util.MethodRequest;

public class ServiceProxy implements Service {

    public ServiceProxy() {
        _service = new ServiceImpl();
        _active_object = new ActiveObject(); // 这算是开启了一个后台线程，只要Stack中有对象，就调用对象的call()方法
    }

    public void sayHello() { // 相当于前台线程调用这个方法，将一个SayHello对象放入Stack中，此时，后台线程唤醒，然后取出对象，执行其call()方法。
        MethodRequest mr = new SayHello(_service);
        _active_object.enqueue(mr);
    }

    private Service _service;
    private ActiveObject _active_object;
}

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class RunMethodInterceptor implements MethodInterceptor {

    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("before run method");

        Object rtnObj = proxy.invokeSuper(obj, args);

        System.out.println("after run method");

        return rtnObj;
    }
}

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class EatMethodInterceptor implements MethodInterceptor {

    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("before eat method");

        Object rtnObj = proxy.invokeSuper(obj, args);

        System.out.println("after eat method");

        return rtnObj;
    }
}

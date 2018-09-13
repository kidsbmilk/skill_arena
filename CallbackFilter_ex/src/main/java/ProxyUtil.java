import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;

public class ProxyUtil {

    public static Object createProxy(Class targetClass, Callback[] callbacks, CallbackFilter filter, Class[] argTypes, Object[] argObjs) {
        Enhancer enhancer = new Enhancer();
        enhancer.setInterfaces(new Class[]{TestInterface.class});
        enhancer.setSuperclass(targetClass);
        enhancer.setCallbacks(callbacks);
        enhancer.setCallbackFilter(filter);
        return enhancer.create(argTypes, argObjs);
    }
}

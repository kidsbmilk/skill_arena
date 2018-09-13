import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.NoOp;

public class CglibTest {

    public static void main(String[] args) {
        Class[] argTypes = {String.class};
        Object[] argObjs = {"jerry"};
        Callback[] callbacks = {NoOp.INSTANCE, new EatMethodInterceptor(), new RunMethodInterceptor(), new TestInterfaceInterceptor()};
        CallbackFilter filter = new PersonCallbackFilter();
        Person person = (Person) ProxyUtil.createProxy(Person.class, callbacks, filter, argTypes, argObjs);
        person.run();
        person.eat();
        ((TestInterface) person)._test();
        person.sleep();
    }
}

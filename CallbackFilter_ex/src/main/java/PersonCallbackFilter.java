import net.sf.cglib.proxy.CallbackFilter;

import java.lang.reflect.Method;

public class PersonCallbackFilter implements CallbackFilter {

    public int accept(Method method) {
        if(method.getName().equals("eat")) {
            return 1;
        } else if(method.getName().equals("run")) {
            return 2;
        } else if(method.getName().equals("_test")) {
            return 3;
        }

        return 0;
    }
}

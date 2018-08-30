package com.zz4955.jmx_ex;

import javax.management.*;
import javax.management.remote.*;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

public class Client {

    public static void main(String[] args) throws IOException,
            MalformedObjectNameException, InstanceNotFoundException,
            AttributeNotFoundException, InvalidAttributeValueException,
            MBeanException, ReflectionException, IntrospectionException {
        String domainName = "MyMBean";
        int rmiPort = 1099;
        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:" + rmiPort + "/" + domainName);
        // 可以类比HelloAgent.java中的那名：
//        JMXConnectorServer jmxConnectorServer = JMXConnectorServerFactory.newJMXConnectorServer(url, null, mbs);
        JMXConnector jmxc = JMXConnectorFactory.connect(url);
        MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();

        // print domains
        System.out.println("Domains:-----------------------------");
        String[] domains = mbsc.getDomains();
        for(int i = 0; i < domains.length; i ++) {
            System.out.println("\t Domains[" + i + "] = " + domains[i]);
        }
        // MBean count
        System.out.println("MBean count = " + mbsc.getMBeanCount());
        // process attribute
        ObjectName mBeanName = new ObjectName(domainName + ":name=HelloWorld");
        mbsc.setAttribute(mBeanName, new Attribute("Name", "zz")); // 注意这里是Name而不是name
        System.out.println("Name = " + mbsc.getAttribute(mBeanName, "Name"));

        // 接下去是执行Hello中的printHello方法，分别通过代理和rmi的方式执行
        // via proxy
        HelloMBean proxy = MBeanServerInvocationHandler.newProxyInstance(mbsc, mBeanName, HelloMBean.class, false);
        proxy.printHello();
        proxy.printHello("soybeanmilk");
        // via rmi
        mbsc.invoke(mBeanName, "printHello", null, null);
        mbsc.invoke(mBeanName, "printHello", new String[]{"soybeanmilk"}, new String[]{String.class.getName()});

        // get mbean information
        MBeanInfo info = mbsc.getMBeanInfo(mBeanName);
        System.out.println("Hello Clas: " + info.getClassName());
        for(int i = 0; i < info.getAttributes().length; i ++) {
            System.out.println("Hello Attribute: " + info.getAttributes()[i].getName());
        }
        for(int i = 0; i < info.getOperations().length; i ++) {
            System.out.println("Hello Operation: " + info.getOperations()[i].getName());
        }

        // ObjectName of MBean
        System.out.println("all ObjectName:-----------------");
        Set<ObjectInstance> set = mbsc.queryMBeans(null, null);
        for(Iterator<ObjectInstance> it = set.iterator(); it.hasNext();) {
            ObjectInstance oi = it.next();
            System.out.println("\t" + oi.getObjectName());
        }
        jmxc.close();
    }
}

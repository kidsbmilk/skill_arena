package io.concurrencymodel.example.SayHello;

// 构建Java并发模型框架：https://www.ibm.com/developerworks/cn/java/l-multithreading/
class ClientTest1 {
    public static void main(String[] args) {
        Service s = new ServiceImpl();
        Client c = new Client(s);
        c.requestService();
    }
}

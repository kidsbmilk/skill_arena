public class Person {

    private String name;

    public Person(String name) {
        this.name = name;
    }

    public void run() {
        System.out.println(name + " is running");
    }

    public void eat() {
        System.out.println(name + " is eating");
    }

    public void sleep() {
        System.out.println(name + " is sleeping");
    }
}

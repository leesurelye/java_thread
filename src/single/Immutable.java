package src.single;

final class Person {
    private final String name;
    private final String address;

    public Person(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return this.name;
    }

    public String getAddress() {
        return address;
    }

    public String toString() {
        return "[Person : name=" + name + ", address=" + address + "]";
    }
}


class PrintPersonThread implements Runnable {
    private Person person;

    public PrintPersonThread(Person person) {
        this.person = person;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println(Thread.currentThread().getName() + " prints " + person.toString());
        }
    }
}

public class Immutable {
    public static void main(String[] args) {

    }
}


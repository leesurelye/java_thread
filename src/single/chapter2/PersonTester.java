package src.single.chapter2;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

/**
 * comes from Page.79
 * <p>
 * 2-9
 */

final class MutablePerson {
    private String name;
    private String address;

    public MutablePerson(String name, String address) {
        this.name = name;
        this.address = address;
    }

    //immutable person --> mutable person
    public MutablePerson(ImmutablePerson person) {
        this.name = person.getName();
        this.address = person.getAddress();
    }

    public synchronized void setPerson(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public synchronized ImmutablePerson getImmutablePerson() {
        return new ImmutablePerson(this);
    }

    String getName() {
        return name;
    }

    String getAddress() {
        return address;
    }

    public synchronized String toString() {
        return "[ MutablePerson:\t" + this.name + ",\t" + this.address + "]";
    }
}

final class ImmutablePerson {
    private final String name;
    private final String address;

    public ImmutablePerson(String name, String address) {
        this.name = name;
        this.address = address;
    }
    public ImmutablePerson(@NotNull MutablePerson person) {
        //此处是构造函数,传入的对象可能在传入后，被别的线程修改person的值，因此需要对person进行加锁
        synchronized (person){
            this.name = person.getName();
            this.address = person.getAddress();
        }
    }

    public MutablePerson getPerson() {
        return new MutablePerson(this);
    }

    public String getName() {
        return this.name;
    }

    public String getAddress() {
        return this.address;
    }

    public String toString() {
        return "[ ImmutablePerson:\t" + this.name + ",\t" + this.address + "]";
    }
}


class CrackerThread extends Thread {
    private final MutablePerson mutablePerson;

    public CrackerThread(MutablePerson mutablePerson) {
        this.mutablePerson = mutablePerson;
    }

    @Override
    public void run() {
        while (true) {
            ImmutablePerson immutablePerson = new ImmutablePerson(this.mutablePerson);
            if (!immutablePerson.getName().equals(immutablePerson.getAddress())) {
                System.out.println(currentThread().getName() + "========BROKEN=======" + immutablePerson);
            }
        }
    }
}

public class PersonTester {
    //数据出现错误
    public static void main(String[] args) {
        MutablePerson person = new MutablePerson("start", "start");
        new CrackerThread(person).start();
        new CrackerThread(person).start();
        new CrackerThread(person).start();
        for (int i = 0; true; i++) {
            person.setPerson("" + i, "" + i);
        }
    }
}

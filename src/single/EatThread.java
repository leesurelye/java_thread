package src.single;

import java.util.concurrent.Semaphore;

class Tool {
    private final String name;

    public Tool(String name) {
        this.name = name;
    }

    public String toString() {
        return "[" + name + "]";
    }
}

public class EatThread extends Thread {
    private final String name;
    private final Tool leftHand;
    private final Tool rightHand;

    public EatThread(String name, Tool leftHand, Tool rightHand) {
        this.name = name;
        this.leftHand = leftHand;
        this.rightHand = rightHand;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (leftHand) {
                System.out.println(name + " takes up " + leftHand + " left ");
                synchronized (rightHand) {
                    System.out.println(name + " takes up " + rightHand + " right ");
                    System.out.println("Eating ...");
                    System.out.println(name + " puts down " + leftHand + " left");
                }
                System.out.println(name + " puts down " + rightHand + " right");
            }
        }
    }
}

class Tester {
    public static void main(String[] args) {
        Tool fork = new Tool("fork");
        Tool spoon = new Tool("spoon");
        new EatThread("Alice", fork, spoon).start();
        new EatThread("Bobby", fork, spoon).start();

    }
}

package src.chapter3;

import java.util.*;
import java.util.stream.Collectors;

class Request {
    /**
     * Request entity only name
     */
    private final String name;

    public Request(String name) {
        this.name = name;
    }

    public String toString() {
        return "[Request: " + name + "]";
    }
}


class RequestQueue {
    /**
     * Request Queue: put the Queue
     */
    private final Queue<Request> queue = new LinkedList<>();

    public synchronized Request getRequest() {
        // synchronized 获取的是this锁
        // Guarded Suspension 守护条件
        while (queue.peek() == null) {
            try {
                wait();
            } catch (InterruptedException e) {

            }
        }
        return queue.remove();
    }

    public synchronized void putRequest(Request request) {
        queue.offer(request);
        notifyAll();
    }
}

class ClientThread extends Thread {
    private final RequestQueue queue;
    private final Random random;

    public ClientThread(RequestQueue queue, long seed, String name) {
        super(name);
        this.queue = queue;
        this.random = new Random(seed);
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            Request request = new Request("No." + i);
            System.out.println(Thread.currentThread().getName() + " request:" + request);
            queue.putRequest(request);
            try {
                Thread.sleep(this.random.nextInt(1000));

            } catch (InterruptedException e) {
                // null
            }

        }
    }
}

class ServerThread extends Thread {
    private final RequestQueue queue;
    private final Random random;

    public ServerThread(RequestQueue queue, long seed, String name) {
        super(name);
        this.queue = queue;
        this.random = new Random(seed);
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            Request request = queue.getRequest();
            System.out.println(Thread.currentThread().getName() + " handles " + request);
        }
        try {
            Thread.sleep(random.nextInt(2000));
        } catch (InterruptedException e) {
        }
    }
}


class Student {
    String name;

    public Student(String name) {
        this.name = name;
    }

}

public class ClientServerTester {
    public static void main(String[] args) {
        Integer[] a = {1,2,3};
//        List<Integer> list = Arrays.asList(a); 不推荐
        List<Integer> b = Arrays.stream(a).collect(Collectors.toList());
        System.out.println(b.getClass());



    }
}

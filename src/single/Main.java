package src.single;

import java.util.Random;
import java.util.concurrent.Semaphore;

class Log{
    public static void println(String s){
        System.out.println(Thread.currentThread().getName() + ": " + s);
    }
}
// Semaphore是放在资源中
class BoundResource {
    private final Semaphore semaphore;
    private final int limits;
    private final static Random random = new Random(12345);
    public BoundResource(int limits){
        this.limits = limits;
        this.semaphore = new Semaphore(limits);
    }
    public void use() throws InterruptedException{
        semaphore.acquire();
        try{
            doUse();
        }finally {
            semaphore.release();
        }
    }
    protected void doUse() throws InterruptedException{
        Log.println("begin: used = " + (limits - semaphore.availablePermits()));
        Thread.sleep(random.nextInt(5000));
        Log.println("end: used = " + (limits - semaphore.availablePermits()));
    }
}

class UserThread extends Thread{
    private final BoundResource resource;
    private final Random random = new Random(12345);

    public UserThread(BoundResource resource){
        this.resource = resource;
    }

    @Override
    public void run() {
        try{
            while (true){
                resource.use();
                Thread.sleep(random.nextInt(2000));
            }
        }catch (InterruptedException e){

        }

    }
}

public class Main{
    public static void main(String[] args) {
        BoundResource resource = new BoundResource(3);
        //开启十个线程
        for(int i=0;i<10;i++){
            new UserThread(resource).start();
        }

    }
}



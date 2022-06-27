package src.chapter3;

import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


class MyRunnable implements Runnable{

    private final String command;

    MyRunnable(String s){
        this.command = s;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " Start. Time= " + new Date());
        processCommand();
        System.out.println(Thread.currentThread().getName() + " End. Time= " + new Date());
    }

    private void processCommand(){
        try {
            Thread.sleep(5000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public String toString(){
        return this.command;
    }
}

public class ThreadPoolDemo {
    private static final int CORE_POOL_SIZE = 5;
    private static final int MAX_POOL_SIZE = 10;
    private static final int QUEUE_CAPACITY = 100;
    private static final Long KEEP_ALIVE_TIME = 1L;

    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(QUEUE_CAPACITY),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
        for(int i=0;i<10;i++){
            Runnable work = new MyRunnable(""+i);
            executor.execute(work);
        }
//        关闭线程池
        executor.shutdown();
        while (!executor.isTerminated()){

        }
        System.out.println("finished all thread");

    }

}

package src.chapter3;

//import javafx.scene.input.DataFormat;

import java.text.SimpleDateFormat;
import java.util.Random;

public class ThreadLocalExample implements Runnable {

    private static final ThreadLocal<SimpleDateFormat> formatter = ThreadLocal.withInitial(
            () -> new SimpleDateFormat("yyyyMMdd")
    );
    private static final Random random = new Random(1234);

    @Override
    public void run() {
        System.out.println(" Thread name= "+Thread.currentThread().getName() + " default formatter= " +formatter.get().toPattern());
        try{
            Thread.sleep(random.nextInt(1000));
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        formatter.set(new SimpleDateFormat());
        System.out.println(" Thread name = " + Thread.currentThread().getName() + " formatter = " + formatter.get().toPattern());
    }

    public static void main(String[] args) throws InterruptedException{
        ThreadLocalExample example = new ThreadLocalExample();
        for(int i=0;i<10;i++){
            Thread t = new Thread(example, "" + i);
            Thread.sleep(random.nextInt(1000));
            t.start();
        }
    }
}

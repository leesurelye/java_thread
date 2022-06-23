package src.single;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class WriterThread extends Thread {
    private final List<Integer> list;

    public WriterThread(List<Integer> list) {
        super("Writer Thread");
        this.list = list;
    }

    @Override
    public void run() {
        for (int i = 0; true; i++) {
            list.add(i);
            list.remove(0);
        }
    }
}

class ReadThread extends Thread {
    private final List<Integer> list;

    public ReadThread(List<Integer> list) {
        super("Read Thread");
        this.list = list;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (list){
                for (int n : list) {
                    System.out.println(n);
                }
            }
        }
    }
}

public class WriteRead {
    public static void main(String[] args) {
//        List<Integer> list = new ArrayList<>();
//        ArrayList是线程非安全的，但是使用 synchronizedList 将ArrayList进行包装，就可以实现ArrayList的线程安全
        final List<Integer> list = Collections.synchronizedList(new ArrayList<>());
        new WriterThread(list).start();
        new ReadThread(list).start();
    }
}

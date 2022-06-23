package src.single;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

class WriterThread1 implements Runnable {
    private final List<Integer> list;

    public WriterThread1(List<Integer> list) {
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

class ReadThread1 implements Runnable {
    private final List<Integer> list;

    public ReadThread1(List<Integer> list) {
        this.list = list;
    }

    @Override
    public void run() {
        while (true){
            for (int n : list) {
                System.out.println(n);
            }
        }
    }
}

/**
 * 使用Copy on Writer 模式，就是当对集合进行写操作时，内部已确保安全的数组就会
 * 被整体复制。
 *
 * 使用copy on writer时，每次执行写操作都会执行复制，因此，程序频繁执行写操作，
 * 就会很耗费时间，但是如果执行读操作，使用copy on writer 会很节省时间。
 * 提升性能
 */
public class CopyOnWrite {
    public static void main(String[] args) {
        final List<Integer> list = new CopyOnWriteArrayList<>();
        new Thread(new WriterThread1(list)).start();
        new Thread(new ReadThread1(list)).start();
    }
}

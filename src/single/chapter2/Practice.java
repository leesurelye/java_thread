package src.single.chapter2;


public class Practice {
    private static final long CALL_COUNT = 1000000000L;

    public static void main(String[] args) {
        trial("Not Sync", CALL_COUNT, new NotSync());
        trial("Sync", CALL_COUNT, new Sync());

    }

    private static void trial(String msg, long count, Object obj) {
        System.out.println(msg + " Begin");
        long start_time = System.currentTimeMillis();
        for (long i = 0; i < count; i++) {
            obj.toString();
        }
        System.out.println(msg + "End");
        System.out.println("Elapsed time = " + (System.currentTimeMillis() - start_time) + " ms.");
    }
}

class NotSync {
    private final String name = "NotSync";

    public String toString() {
        return "[" + name + "]";
    }
}

class Sync {
    private final String name = "Sync";

    public synchronized String toString() {
        return "[" + name + "]";
    }
}

/*
    使用synchronized 的类执行时间为： 14749ms
    使用immutable 的类执行时间为：251ms
 */

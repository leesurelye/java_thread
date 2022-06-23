# 0. 前言

顺序、并行与并发的概念

- 顺序(Sequential) 用于表示多个操作"依次处理"。比如把十个操作交给一个人处理时，这个人要一个一个地按顺序来处理。
- 并行(parallel)用于表示多个操作"同时处理"。比如十个操作分给两个人处理时，这两个人就会并行来处理。
- 并发(concurrent)用于表示"将一个操作分割成多个部分并且允许无序处理"。比如将十个操作分成相对独立的两个类，这样便能开始并发处理了。如果一个人来处理，这个人就是顺序处理分开的并发操作，如果是两个人，这两个人就可以并行处理同一操作。

多线程编程时，即使能够并行运行，也必须确保程序能够完全正确地执行。也就是说，必须正确编写线程的互斥处理和同步处理。

# 1. 线程的状态迁移

线程状态(Thread.Stat)中定义的Enum名，定义了线程的几种状态

NEW, RUNNABLE, TERMINATED,WAITTING, TIMED_WAITTING,BLOCKED.



# 2. Java线程的创建

>  1. 继承Thread类，书写run()方法

- 在主程序中调用start()方法，启动线程，虽然run()方法也可以调用，但是该方法不会创建新的线程，调用start()方法才可以启动新的线程。
  - 调用start方法后，程序会在后台启动新的线程。然后，由这个新线程调用run()方法。

```java
// Thread 线程
public class PrintThread extends Thread {
    private String message;

    public PrintThread(String message) {
        this.message = message;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            System.out.print(this.message);
        }
    }
}
```

```java
//Main.java
public class Main {
    public static void main(String[] args) {
        new PrintThread("Good!").start();
        new PrintThread("Nice!").start();
        //主线程的main类中启动了两个线程，随后main()方法后主线程就终止了，但是整个程序并不会终止，
        //只有当两个线程都执行完成，程序才会终止。
    }
}
```

> 2. 实现Runnable接口的实现类的实列启动线程

- 实现Runnable接口，书写run()方法
- 但是Runnable接口没有start()方法，所以需要使用Thread类的start()方法类启动线程

```java
// Thread.java
public class PrintThread implements Runnable {
    private String message;

    public PrintThread(String message) {
        this.message = message;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            System.out.print(this.message);
        }
    }
}
```

```java
// Main.java
public class Main {
    public static void main(String[] args) {
        new Thread(new PrintThread("Nice!")).start();
        new Thread(new PrintThread("Good!")).start();
    }
}

```

- 不管是利用Thread类，还是使用Runnable接口，启动线程的方法始终都是Thread类的start方法。
- Thread类本身还实现了Runnable接口，但是run()方法是空的，需要子类来实现。

> Java.util.concurrent.ThreadFactory

Java.util.concurrent包含了一个将线程抽象化的ThreadFactory接口。利用该接口，我们可以将Runnable作为参数传入并通过new创建Thread实例的处理隐藏在ThreadFactory内部。

默认的ThreadFactory对象是通过Executors.defaultThreadFactory方法获取的。

```java
public class Main {
    public static void main(String[] args) {
        ThreadFactory factory = Executors.defaultThreadFactory();
        factory.newThread(new PrintThread("Good!")).start();
    }
}
```

# 3. 线程的互斥处理

当多个线程同时处理同一个实例时，就会引发一些问题。这种由于线程之间相互竞争==race==而引起的的问题称为数据竞争==data race==或者==race condition==竞态条件。需要互斥(mutual exclusion)来解决这个问题。

java使用关键字`synchronized`来执行线程的互斥处理。

## 3.1 Synchronized

同步方法使用了synchronized关键字来控制该方法只能由一个线程运行。每一个实列拥有一个独立的锁。

线程的互斥机制称为监视(monitor)，获取锁有时也叫“拥有(own)监视”或“持有(hold)锁”。

当前线程是否已获取某一对象的锁，可以通过==Thread.holdLock()==方法确认。当前线程已获取对象obj的锁时，可以使用assert来判断。

```java
assert Thread.holdLock(obj)
```

> synchronized代码块

```java
synchronized (obj){
    ... // 表示需要获取obj对象锁后才能执行代码
}
//以下两个方法执行的效果是一样的
synchronized void method(){
    //...
}
//
void method(){
    synchronized(this){
        //...
    }
}
```

## 3.2 线程的协作

线程之间的协作使用notify()、notifyall()、wait()方法，这些方法都是Object类的方法。

该使用notify方法还是notifyall()方法?

notify()和notifyall()非常相似，到底该选择哪一个，其实很难选择，由于notify唤醒的线程较少，所以处理速度要比使用notifyall时快。但是使用notify()时，如果处理不好，程序便可能停止。一般来说，使用notifyall()的代码要比notify()的要更加健壮。除非开发人员完全理解代码的含义和范围，否则使用notifyall()更为稳妥。

# 4. Single Threaded Execution

有一个独木桥，非常细，每次只允许一个人经过，如果这个人还没有走到桥的另一头，则下一个人无法过桥，如果同时两个人上桥，桥就会塌掉，掉进河里。所谓`Single Threaded Execution`就是以一个线程执行，就像独木桥同一时间只允许一个人通行一样，该模式用于设置限制，以确保同一时间内只能让一个线程执行处理。

`Single Threaded Execution`有时候也称为临界区(critical section) 或 临界域(critical region)。`Single Threaded Execution`这个名字侧重于执行处理的线程，临界区或临界域侧重于代码执行的范围。


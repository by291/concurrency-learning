package fun.by291.concurrency.chapter01;

/**
 * join() 方法阻塞的是调用 join() 方法的线程。
 *
 * @author Bystander
 */
public class JoinBlockDemo {

    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            System.out.println("threadA 开始运行");
            for (; ; ) {
            }
        });

        Thread mainThread = Thread.currentThread();
        // 设置主线程的阻塞状态
        mainThread.interrupt();

        thread.start();
        try {
            // 由于 join() 会阻塞调用它的线程，在这里是主线程，
            // 而主线程已经设置了中断状态，所以调用 join() 时会
            // 抛出 InterruptedException
            thread.join();
        } catch (InterruptedException e) {
            System.out.println("主线程抛出了 InterruptedException");
        }
        // 当主线程运行结束后，JVM 还要等待所有非守护线程运行结束
        // 所以这个程序不会结束
    }
}


package fun.by291.concurrency.chapter01;

/**
 * sleep() 静态方法 demo
 * <br>
 * <br>
 * <b>sleep() 并不会让出锁</b>
 *
 * @author Bystander
 */
public class SleepDemo {
    private static final Object obj = new Object();

    public static void main(String[] args) {
        Thread threadA = new Thread(() -> {
            synchronized (obj) {
                try {
                    System.out.println("threadA 调用了 sleep() 方法");
                    Thread.sleep(3000);
                    System.out.println("threadA 结束了 sleep()");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread threadB = new Thread(() -> {
            synchronized (obj) {
                try {
                    System.out.println("threadB 调用了 sleep() 方法");
                    Thread.sleep(3000);
                    System.out.println("threadB 结束了 sleep()");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // 由于 sleep() 并不会释放锁，所以控制台的线程的打印一定是一一对应的，
        // 不可能出现两个线程交替打印的情况
        threadA.start();
        threadB.start();
    }
}

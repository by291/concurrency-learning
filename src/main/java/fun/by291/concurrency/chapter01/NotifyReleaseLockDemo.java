package fun.by291.concurrency.chapter01;

/**
 * notify() 释放锁的时机演示
 *<br>
 * <br>
 * <b>notify() 释放锁的时机是在对应 synchronized 块结束的地方</b>
 * @author Bystander
 */
public class NotifyReleaseLockDemo {
    // 模拟共享资源
    private static final Object resource = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread threadA = new Thread(() -> {
            try {
                // 获取 resource 上的锁
                synchronized (resource) {
                    System.out.println("threadA 调用 wait()");
                    // 释放 resource 上的锁
                    resource.wait();
                    // 由于无法再次拿到锁，所以这段代码不会到达
                    System.out.println("thread 结束了 wait()");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread threadB = new Thread(() -> {
            // 获取 resource 上的锁
            synchronized (resource) {
                System.out.println("threadB 调用 notifyAll()");
                // 调用 notifyAll()
                resource.notifyAll();
                // 进入死循环
                // 由于 notifyAll() 释放锁的时机是在 synchronized 块结束的地方，
                // 所以 resource 上的锁永远无法被释放
                // threadA 永远无法拿到 resource 上的锁
                // 可以将这个循环注释掉看效果
                while (true) {

                }
            }
        });

        threadA.start();
        // 保证 threadA 已经调用了 wait()
        Thread.sleep(500);
        threadB.start();

        threadA.join();
        // 死锁！
        // 以下的代码永远无法到达
        threadB.join();

        System.out.println("运行结束");
    }
}

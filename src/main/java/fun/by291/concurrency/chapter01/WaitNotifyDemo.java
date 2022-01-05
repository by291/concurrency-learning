package fun.by291.concurrency.chapter01;

/**
 * wait() 和 notify()/notifyAll() 方法的 demo
 * <br>
 * <br>
 * <b>注意：notify() / notifyAll() 并不会立刻释放锁</b>
 *
 * @author Bystander
 */
public class WaitNotifyDemo {
    // 模拟多线程共享的资源
    private static final Object resource = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread threadA = new Thread(() -> {
            // threadA 获取 resource 的锁
            // synchronized 即获取对应资源的锁
            synchronized (resource) {
                System.out.println("threadA 获得了 resource 的锁");
                try {
                    System.out.println("threadA 调用了 wait()");
                    // wait() 即释放当前资源上的锁，其它线程可获得锁
                    resource.wait();
                    System.out.println("threadA 结束了 wait()");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // 这个和上面的同理
        Thread threadB = new Thread(() -> {
            // 获取锁
            synchronized (resource) {
                System.out.println("threadB 获得了 resource 的锁");
                try {
                    System.out.println("threadB 调用了 wait()");
                    // wait 并释放锁
                    resource.wait();
                    System.out.println("threadB 结束了 wait()");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread threadC = new Thread(() -> {
            // 获得锁
            synchronized (resource) {
                System.out.println("threadC 调用了 notifyAll()");
                // notifyAll() 会让所有将所有在等待的线程唤醒，然后这些线程开始竞争
                // notifyAll() 并不会立刻释放锁，必须等到 synchronized 块结束才会释放
                resource.notifyAll();
            }
        });

        threadA.start();
        threadB.start();

        // 保证 threadA 和 threadB 都已经 wait 过了
        Thread.sleep(1000);
        threadC.start();

        threadA.join();
        threadB.join();
        threadC.join();
    }
}

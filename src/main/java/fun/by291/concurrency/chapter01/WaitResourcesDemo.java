package fun.by291.concurrency.chapter01;

/**
 * wait() 方法只会释放当前 (调用 wait() 方法的变量) 共享变量的锁
 * <br>
 * <br>
 * <b>死锁的必要条件是持有并等待</b>
 *
 * @author Bystander
 */
public class WaitResourcesDemo {
    // 模拟两个共享资源
    private static final Object resourcesA = new Object();
    private static final Object resourcesB = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread threadA = new Thread(() -> {
            try {
                // 获取 resourceA 上的锁
                synchronized (resourcesA) {
                    System.out.println("threadA 获得了 resourceA 的锁");
                    // 获取 resourceB 上的锁
                    synchronized (resourcesB) {
                        System.out.println("threadA 获得了 resourceB 的锁");
                        System.out.println("threadA 释放 resourceA 的锁");
                        // 释放 resourceA 的锁
                        resourcesA.wait();
                        // 并没有释放 resourceB 的锁，wait() 后面的代码不会执行，所以
                        // 退不出 synchronized 块，resourceB 的锁不会释放
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread threadB = new Thread(() -> {
            try {
                // 确保 resourceA 上的锁已经被释放
                Thread.sleep(1000);

                // 获取 resourceA 上的锁
                synchronized (resourcesA) {
                    System.out.println("threadB 获得了 resourceA 的锁");

                    System.out.println("threadB 尝试获取 resourceB 上的锁...");
                    // 获取 resourceB 上的锁，实际上获取不到
                    synchronized (resourcesB) {
                        // 以下代码都执行不到
                        System.out.println("threadB 获得了 resourceB 的锁");
                        System.out.println("threadB 释放了 resourceA 的锁");
                        resourcesA.wait();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        threadA.start();
        threadB.start();

        // 没有线程 notify resourceA，所以 threadA 永远无法执行完，
        // threadB 永远无法获取到 resourceB 上的锁，所以 threadB 永远无法执行完
        // 死锁！！！
        threadA.join();
        // 其实从这里就无法到达了
        threadB.join();

        // 永远无法到达
        System.out.println("main over");
    }
}

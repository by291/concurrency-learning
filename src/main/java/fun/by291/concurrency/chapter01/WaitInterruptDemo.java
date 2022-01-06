package fun.by291.concurrency.chapter01;

/**
 * wait() 和 interrupt() 方法示例
 * <br>
 * <br>
 * <b>线程 A 调用了 wait() 进入了等待状态，可以用 interrupt() 取消。不
 * 过这时候要注意锁定的问题。线程在进入等待区,会把锁定解除,当对等待中的
 * 线程调用 interrupt() 时，会先重新获取锁定，再抛出异常。在获取锁定之前，
 * 是无法抛出异常的。</b>
 *
 * @author Bystander
 */
public class WaitInterruptDemo {
    // 模拟共享资源
    private static final Object obj = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            try {
                System.out.println("开始进入 synchronized 块");
                synchronized (obj) {
                    // 调用 wait() 释放 obj 上的锁
                    // 没有其它线程调用 notify() 方法，所以
                    // thread 会一直阻塞在 synchronized 块中
                    obj.wait();
                    // 由于 thread 一直阻塞在 synchronized 块中，而后 thread 又被
                    // 中断，所以 thread 会在调用 wait() 方法的地方抛出 InterruptedException，
                    // 然后进入 catch 块中，wait() 以下的代码都不会到达
                    // 抛出 InterruptedException 的同时会清除中断状态
                }
                System.out.println("退出 synchronized 块");
            } catch (InterruptedException e) {
                System.out.println("thread 抛出 InterruptedException 异常");
            }
        });

        thread.start();
        // 确保 thread 已经调用了 wait()
        Thread.sleep(1000);

        System.out.println("调用 thread 的 interrupt() 方法");
        // interrupt() 方法只是设置了中断状态，并不会中断一个正在正常运行的线程
        // 当线程由于调用了 wait(), join() 和 sleep() 方法及其重载方法中的其中一个
        // 而阻塞时，线程会立即抛出一个 InterruptedException，进而退出阻塞状态
        thread.interrupt();
        System.out.println("已经调用了 thread 的 interrupt() 方法");
    }
}

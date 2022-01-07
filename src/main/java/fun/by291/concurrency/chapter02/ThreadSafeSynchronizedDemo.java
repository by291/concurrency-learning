package fun.by291.concurrency.chapter02;

/**
 * <p>线程安全的类举例，使用 synchronized 关键字</p>
 * <p><b>注意：synchronized 关键字能保证同一时刻只有一个线程能操作这个实例</b></p>
 * <p>进入 synchronized 块就会刷新线程的工作内存，从而保证是从主内存读取到最新值，退出
 * synchronized 块会将 synchronized 块内对共享变量的修改刷新到主内存</p>
 * <p><b>synchronized 即保证了内存可见性，也保证了操作的原子性</b></p>
 * <p>synchronized 时独占锁，会大大降低并发性</p>
 *
 * @author Bystander
 * @see ThreadSafeVolatileDemo
 */
public class ThreadSafeSynchronizedDemo {
    private int value;

    // 注意，这里的 synchronized 关键字也是必须的，否则就有可能读取不到最新值
    // 这是为了保证 value 的内存可见性
    // 参见 package-info.java
    public synchronized int getValue() {
        return value;
    }

    public synchronized void setValue(int value) {
        this.value = value;
    }
}

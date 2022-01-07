package fun.by291.concurrency.chapter02;

/**
 * <p>线程安全的类举例，使用 volatile 关键字</p>
 * <p>与 synchronized 一样，volatile 也能保证线程在操作实例时刷新工作内存</p>
 * <p><b>注意：volatile 不能保证同一时刻操作这个实例的线程数，只能保证共享变量的内存可见性，不保证操作的原子性</b></p>
 * <p><b>因此在应该在写入变量值不依赖于旧值的情况下使用 volatile 关键字</b></p>
 *
 * @author Bystander
 * @see ThreadSafeSynchronizedDemo
 */
public class ThreadSafeVolatileDemo {
    private volatile int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}

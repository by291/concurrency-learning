package fun.by291.concurrency.chapter02;

/**
 * <p>线程不安全的类举例</p>
 * <p>当有多个线程同时操作时，就有可能读不到最新值</p>
 *
 * @author Bystander
 *
 * @see package-info.java
 */
public class ThreadUnsafeDemoDemo {
    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}

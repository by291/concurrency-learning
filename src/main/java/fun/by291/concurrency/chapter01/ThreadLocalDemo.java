package fun.by291.concurrency.chapter01;

/**
 * ThreadLocal 使用 demo
 *
 * @author Bystander
 */
public class ThreadLocalDemo {
    // 每个线程从 ThreadLocal 中取的值都是存在对应线程的一个副本
    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) {
        Thread threadA = new Thread(() -> {
            // 给当前线程的 ThreadLocal 设置值
            threadLocal.set("threadA 的 threadLocal 中的值");
            print(Thread.currentThread().getName());
            // 移除当前线程的 ThreadLocal 中的值
            threadLocal.remove();
            print(Thread.currentThread().getName());
        });

        Thread threadB = new Thread(() -> {
            threadLocal.set("threadB 的 threadLocal 中的值");
            print(Thread.currentThread().getName());
            threadLocal.remove();
            print(Thread.currentThread().getName());
        });

        threadA.start();
        threadB.start();
    }

    /**
     * 打印当前线程中的 threadLocal 中的值
     *
     * @param threadName 线程 名称
     */
    static void print(String threadName) {
        System.out.println(threadName + ":" + threadLocal.get());
    }
}

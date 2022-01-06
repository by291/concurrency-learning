package fun.by291.concurrency.chapter01;

/**
 * 将线程设置为守护线程的 demo
 *
 * @author Bystander
 */
public class DaemonDemo {

    public static void main(String[] args) {
        // JVM 在 main 方法退出时会等待所有非守护线程运行结束，然后结束虚拟机
        // 所以一旦 t 不是守护线程，那么虚拟机就无法关闭，这个程序即无法运行结束
        Thread t = new Thread(() -> {
            for (; ; ) {
            }
        });

        // 设置 t 为守护线程
        // 可以将下面一行代码注释掉看运行结果
        t.setDaemon(true);
        t.start();
        System.out.println("main over");
    }
}

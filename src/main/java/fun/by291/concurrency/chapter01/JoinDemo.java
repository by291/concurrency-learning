package fun.by291.concurrency.chapter01;

/**
 * 等待线程完成的 join() 方法的 demo
 *
 * @author Bystander
 */
public class JoinDemo {

    public static void main(String[] args) throws InterruptedException {
        Thread threadOne = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("threadOne 运行结束");
        });

        Thread threadTwo = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("threadTwo 运行结束");
        });

        threadOne.start();
        threadTwo.start();

        System.out.println("等待 threadOne 和 threadTwo 执行结束!");
        // 调用 join() 方法等待线程执行结束，主线程会在调用 join() 的地方阻塞
        // 这其实并不能保证 threadOne 和 threadTwo 的执行结束顺序
        // 但可以保证在执行这两个 join() 方法下面的代码前这两个线程都已经执行结束
        threadOne.join();
        threadTwo.join();

        System.out.println("所有线程执行结束");
    }
}
package fun.by291.concurrency.chapter01;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 创建线程的三种方式
 *
 * @author Bystander
 */
public class ThreadDemo {

    public static void main(String[] args) {
        // 继承自 Thread 类
        System.out.println("当前线程 id: " + Thread.currentThread().getId());
        new MyThread().start();

        // 实现 Runnable 接口
        System.out.println("当前线程 id: " + Thread.currentThread().getId());
        RunnableTask runnableTask = new RunnableTask();
        new Thread(runnableTask).start();
        new Thread(runnableTask).start();

        // 实现 Callable 接口
        System.out.println("当前线程 id: " + Thread.currentThread().getId());
        FutureTask<String> futureTask = new FutureTask<>(new CallableTask());
        new Thread(futureTask).start();
        try {
            // 阻塞知道获取到结果
            String result = futureTask.get();
            System.out.println("当前线程 id: " + result);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 继承 Thread 类
     *
     * @author Bystander
     */
    public static class MyThread extends Thread {

        @Override
        public void run() {
            System.out.println("当前线程 id: " + Thread.currentThread().getId());
        }
    }

    /**
     * 实现 Runnable 接口
     *
     * @author Bystander
     */
    public static class RunnableTask implements Runnable {

        @Override
        public void run() {
            System.out.println("当前线程 id: " + Thread.currentThread().getId());
        }
    }

    /**
     * 实现 Callable 接口，这种情况下有返回值
     *
     * @author Bystander
     */
    public static class CallableTask implements Callable<String> {

        @Override
        public String call() {
            return String.valueOf(Thread.currentThread().getId());
        }
    }
}

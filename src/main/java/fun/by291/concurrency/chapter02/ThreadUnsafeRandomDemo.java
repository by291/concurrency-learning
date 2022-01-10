package fun.by291.concurrency.chapter02;

import java.util.Random;

/**
 * 线程不安全的 Random 类使用示例
 *
 * @author Bystander
 */
public class ThreadUnsafeRandomDemo {

    public static void main(String[] args) {
        // 创建一个包含默认种子的随机数生成器
        Random random = new Random();
        // 输出 10 个在 0~5 之间的随机数生成
        for (int i = 0; i < 10; i++) {
            // 生成随机数的步骤：
            // 1.首先根据老的种子生成新的种子
            // 2.根据新的种子来生成新的随机数
            //
            // 这不是一个具有原子性的过程
            // 根据这个原理，Random 类在多线程环境下就有可能多个线程生成同一个值
            System.out.println(random.nextInt(5));
        }
    }
}

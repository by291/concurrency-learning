package fun.by291.concurrency.chapter02;

import java.util.Random;

/**
 * 传统的 Random 类使用示例
 *
 * @author Bystander
 */
public class TraditionalRandomDemo {

    public static void main(String[] args) {
        // 创建一个包含默认种子的随机数生成器
        Random random = new Random();
        // 输出 10 个在 0~5 之间的随机数生成
        for (int i = 0; i < 10; i++) {
            // 生成随机数的步骤：
            // 1.首先根据老的种子生成新的种子
            // 2.根据新的种子来生成新的随机数
            //
            // 这不是一个原子性的过程，但是 JDK 也考虑到了这点，通过 CAS 和一个
            // 原子变量防止多线程环境下多个线程拿到同一个老种子，生成相同的随机数
            //
            // 但是这个是有局限性的，详解请看我的另一个项目: jdk-source
            // 详细注释都在源码里
            System.out.println(random.nextInt(5));
        }
    }
}

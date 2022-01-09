package fun.by291.concurrency.chapter02;

import java.lang.reflect.Field;
import sun.misc.Unsafe;

/**
 * Unsafe 类的使用 Demo
 *
 * @author Bystander
 */
public class UnsafeDemo {
    // 获取 Unsafe 类实例，这样是不行的，因为 getUnsafe() 会检查调用者的 ClassLoader
    // 是否是 BootstrapClassLoader 或 PlatformClassLoader ，但是我们可以通过反射获取
    // private static final Unsafe UNSAFE = Unsafe.getUnsafe();

    // 记录 state 类在 UnsafeDemo 类中的 offset
    private static Unsafe unsafe;
    private static long stateOffset;

    private volatile long state = 0;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
            // 获取 state 在 UnsafeDemo 中的 offset
            stateOffset = unsafe.objectFieldOffset(UnsafeDemo.class.getDeclaredField("state"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // 创建实例
        UnsafeDemo unsafeDemo = new UnsafeDemo();
        // 通过 CAS 设置 state 的值为 1，返回值为是否设置成功
        //
        // CAS 全称为 CompareAndSwap，CAS 是一个原子操作，它比较一个内存位置的值并且
        // 只有相等时修改这个内存位置的值为新的值，保证了新的值总是基于最新的信息计算的，
        // 如果有其他线程在这期间修改了这个值则 CAS 失败。 CAS 返回是否成功或者内存位置原来的
        // 值用于判断是否 CAS 成功。 JVM 中的 CAS 操作是利用了处理器提供的 CMPXCHG 指令实现的。
        //
        // 一般我们将 CAS 操作放在循环中，通过不断的重试来排除其它线程的干扰（其它线程可能会修改
        // CAS 操作目标），当且仅当目标的值与期望值相等时更改它的值
        //
        // 按下面的调用举例，当 unsafeDemo 实例中 stateOffset 处的一个 long 类型的
        // 变量的值为 0(expect) 时，设置它的值为 1(x)
        //
        // CAS 的优点是不用加锁，在竞争不大的时候系统开销小
        // 缺点是：1.循环时间长开销大。2.ABA问题。3.只能保证一个共享变量的原子操作
        //
        // 什么是 ABA 问题：线程 1：获取出数据的初始值是 A，后续计划实施 CAS，期望数据仍是 A 的时候，修改才能成功
        //                线程 2：将数据修改成 B
        //                线程 3：将数据修改回 A
        //                线程 1：CAS 操作，检测发现初始值还是A，进行数据修改
        //
        // ABA 问题原因：CAS 的比较只能比较目标值是否和期待值相等，并不能比较出目标值是否改变过！
        //             所以才会导致 ABA 问题
        boolean success = unsafe.compareAndSwapLong(unsafeDemo, stateOffset, 0, 1);
        System.out.println(success);
        System.out.println(unsafeDemo.getState());
    }

    public long getState() {
        return state;
    }
}

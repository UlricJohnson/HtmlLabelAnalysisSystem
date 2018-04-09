package ink.ulricj.constructor$codeblock;

/**
 * 当 new 一个对象的时候：
 * <p>
 * 父类静态变量和静态代码块--> 子类静态变量和静态代码块 --> 父类代码块
 * <p>
 * --> 父类构造函数 --> 子类代码块 --> 子类构造函数
 * <p>
 * 静态变量和静态代码块（包括父类和子类）只在JVM初始化类的时候执行一次，而非静态变量和代码块则 new 一次执行一次
 * created by Ulric on 2018/3/17
 */

public class Parent {
    static {
        System.out.println("父类-代码块-静态");
    }

    {
        System.out.println("父类-代码块");
    }

    private static String pStaticStr = "父类-静态-变量";
    private String pInstanceStr = "父类-实例-变量";


    public Parent() {
        System.out.println("父类-构造函数");
    }

    public static void parentStaticMethod() {
        System.out.println("父类-静态函数");
    }

    public void parentInstanceMethod() {
        System.out.println("父类-实例函数");
    }
}

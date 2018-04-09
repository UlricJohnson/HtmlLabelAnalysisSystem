package ink.ulricj.constructor$codeblock;

/**
 * created by Ulric on 2018/3/17
 */

public class Sub extends Parent {
    private static String sStaticStr = "子类-静态-变量";
    private String sInstanceStr = "子类-实例-变量";

    static {
        System.out.println("子类-代码块-静态");
    }

    {
        System.out.println("子类-代码块");
    }

    public Sub() {
        System.out.println("子类-构造函数");
    }

    public static void subStaticMethod() {
        System.out.println("子类-静态函数");
    }

    public void subInstanceMethod() {
        System.out.println("子类-实例函数");
        parentInstanceMethod();
        parentStaticMethod();
        subStaticMethod();
    }
}

package ink.ulricj.super$sub;

/**
 * created by Ulric on 2018/3/24
 */

public class Sub extends Super {
    void subInstanceMethod() {
        System.out.println("子类-实例方法");
        superInstanceMethod();
        super.superStaticMethod();
    }

    void superInstanceMethod() {
        System.out.println("子类重写的superInstanceMethod()方法");
    }
}

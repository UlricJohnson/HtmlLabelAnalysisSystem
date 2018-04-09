package ink.ulricj.inner$outter;

/**
 * created by Ulric on 2018/3/17
 */

public class Outter {

    private int oData = 10;

    public class Inner{
        public void innerMethod(){
            System.out.println(oData);
        }
    }
}

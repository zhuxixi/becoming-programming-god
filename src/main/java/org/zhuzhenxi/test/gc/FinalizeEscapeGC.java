package org.zhuzhenxi.test.gc;

/**
 * 不要用finalize方法做任何事情
 */
public class FinalizeEscapeGC {
    public static FinalizeEscapeGC saveMe = null;
    public void isAlive(){
        System.out.println("yes I am still alive :)");
    }
    @Override
    protected void finalize()throws Throwable {
        super.finalize();
        System.out.println("Finalize method executed!");
        FinalizeEscapeGC.saveMe = this;
    }

    public static void main(String[] args) throws Throwable{
        saveMe = new FinalizeEscapeGC();
        saveMe = null;
        System.gc();
        Thread.sleep(500);
        if (saveMe!=null){
            saveMe.isAlive();
        }else {
            System.out.println("no ,I am Dead :(");
        }

        saveMe = null;
        System.gc();
        Thread.sleep(500);
        if (saveMe!=null){
            saveMe.isAlive();
        }else {
            System.out.println("no ,I am Dead :(");
        }
    }
}

package org.zhuzhenxi.test.forkjoin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

public class TestTask extends RecursiveAction {

    private List<TestForkVo> vo;
    private int start;
    private int end;

    /**
     *
     * @param testForkVos
     * @param start1
     * @param end1
     */
    public TestTask(List<TestForkVo> testForkVos,int start1,int end1){
        vo = testForkVos;
        start = start1;
        end = end1;
    }

    @Override
    protected void compute() {
        if (end-start<10){
            for (int i = 0; i < (end-start); i++) {
                TestForkResultCollector.increment();
            }
        }else {
            int middle = (end+start)/2;
            TestTask task1 = new TestTask(vo,start,middle+1);
            TestTask task2 = new TestTask(vo,middle+1,end);
            invokeAll(task1,task2);
        }
    }
}

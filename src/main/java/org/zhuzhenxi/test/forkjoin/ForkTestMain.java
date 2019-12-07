package org.zhuzhenxi.test.forkjoin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class ForkTestMain {
    public static void main(String[] args){
        List<TestForkVo> testCase = new ArrayList<>(getInitialCapacity());
        for (int i = 0; i < getInitialCapacity(); i++) {
            testCase.add(new TestForkVo(i));
        }
        TestTask task = new TestTask(testCase,0, getInitialCapacity());
        ForkJoinPool pool = new ForkJoinPool();
        pool.execute(task);
        do {
            System.out.printf("Main: Thread Count: %d\n",pool.getActiveThreadCount());
            System.out.printf("Main: Thread steal: %d\n",pool.getStealCount());
            System.out.printf("Main: Parallelism : %d\n",pool.getParallelism());
            try {
                TimeUnit.MILLISECONDS.sleep(5);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }while (!task.isDone());

        TestForkResultCollector.print();
    }

    private static int getInitialCapacity() {
        return 10000000;
    }
}

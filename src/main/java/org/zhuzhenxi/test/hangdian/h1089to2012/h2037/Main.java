package org.zhuzhenxi.test.hangdian.h1089to2012.h2037;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * @author zhuzhenxi
 * @date 2019.04.01
 */
public class Main {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()){
            int lines = sc.nextInt();
            if (lines==0){
                continue;
            }
            List<Show> showList = new ArrayList<>(lines);
            while (sc.hasNext()&&lines>0){
                int start = sc.nextInt();
                int end = sc.nextInt();
                showList.add(new Show(start,end));
                lines--;
            }
            showList.sort(Comparator.comparing(Show::getEnd));
            Watcher watcher = new Watcher(showList);
            watcher.watch();
            System.out.println(watcher.getWatched());
        }
    }

    /**
     * 节目
     */
    private static class Show{
        private int start;
        private int end;
        private int last;

        public Show(int start1,int end1){
            start = start1;
            end = end1;
            last = end-start;
        }

        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public int getEnd() {
            return end;
        }

        public void setEnd(int end) {
            this.end = end;
        }

        public int getLast() {
            return last;
        }

        public void setLast(int last) {
            this.last = last;
        }
    }

    private static class Watcher{
        private List<Show> shows;
        private int time = 32;
        private int watched = 0;
        private int start=0;
        private int end=0;
        public Watcher(List<Show> shows1){
            shows = shows1;
        }

        public int getWatched() {
            return watched;
        }

        public void setWatched(int watched) {
            this.watched = watched;
        }

        public void watch(){
            for (Show show:shows){
                if (canWatch(show)){
                    watched +=1;
                    end = show.getEnd();
                    time = time-show.getLast();
                }
            }

        }

        private boolean canWatch(Show show){
            boolean hasTime = time>=show.getLast();
            boolean timeIsNotUp = end<=show.getStart();
            return hasTime&&timeIsNotUp;
        }


    }
}

package org.zhuzhenxi.test.hangdian.h1089to2012.h1200;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()){
            int lines = sc.nextInt();
            while (lines>0&&sc.hasNext()){
                String line = sc.next();
                System.out.println(days(line));
                lines--;

            }
        }
    }

    private static long days(String birthday) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Calendar calendar1 = Calendar.getInstance();

        try {
            Date birthDa = sdf.parse(birthday);
            calendar.setTime(birthDa);
            calendar1.setTime(birthDa);
        } catch (ParseException pe) {
            System.out.println(pe.getMessage());
        }
        calendar1.add(Calendar.YEAR, 18);
        String birthday18 = new SimpleDateFormat("yyyy-MM-dd").format(calendar1.getTime());
        if (!hasBirthday(birthday, birthday18)) {
            return -1;
        }
        long result = (calendar1.getTimeInMillis()-calendar.getTimeInMillis());
        return result / (1000 * 60 * 60 * 24);
    }

    private static boolean hasBirthday(String s1,String s2){
        String[] a = s1.split("-");
        String[] b = s2.split("-");
        return a[1].equals(b[1])&&a[2].equals(b[2]);
    }
}

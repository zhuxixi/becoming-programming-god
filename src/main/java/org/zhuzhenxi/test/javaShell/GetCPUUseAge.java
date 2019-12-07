package org.zhuzhenxi.test.javaShell;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class GetCPUUseAge {

    public static void main(String [] args)
    {
        try
        {
            Process P = Runtime.getRuntime().exec("top -b -n 1");
            P.waitFor();
            BufferedReader StdInput = new BufferedReader(new InputStreamReader(P.getInputStream()));
            String TopS ="";
            int i=0;

            while((TopS= StdInput.readLine())!=null)
            {
                System.out.println(TopS);
//                if(i==2)
//                    System.out.println(TopS);
//
//                if(i>6 && i<17)
//                {
//                    TopS=TopS.replaceAll("  ", " ");
//                    TopS=TopS.replaceAll("  ", " ");
//                    TopS=TopS.replaceAll("  ", " ");
//                    String[] tokens1= TopS.split(" ");
//                    int j=8;
//                    if(tokens1[0].isEmpty())
//                        j=j+1;
//                    String ProcCpuUtil = new String(tokens1[j]);
//                    j++;
//                    Float ProcMemUtil = Float.parseFloat(tokens1[j]);
//                    j = j+2;
//                    String ProcName = new String(tokens1[j]);
//
//                    System.out.println(ProcName+"\t"+ProcCpuUtil+"\t"+ProcMemUtil);
//
//                }
//                i++;

            }


        }catch(Exception e)
        {

        }
    }
}

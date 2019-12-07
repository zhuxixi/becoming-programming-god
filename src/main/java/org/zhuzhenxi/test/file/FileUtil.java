package org.zhuzhenxi.test.file;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.zhuzhenxi.test.random.RandomUtil;

import java.io.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * 读文件工具类
 */
public class FileUtil {
    public static List<String> readFile(String fileName){
        File file = new File("/Users/zhuzhenxi/Downloads/testfile/"+fileName+".csv");
        Long fileLength = file.length(); // 获取文件长度
        byte[] filecontent = new byte[fileLength.intValue()];
        try
        {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        String[] fileContentArr = new String(filecontent).split("\n");

        List<String> stringList = Arrays.asList(fileContentArr);
        return stringList;

    }

    public static List<String> readFileByPath(String filePath){
        File file = new File(filePath);
        Long fileLength = file.length(); // 获取文件长度
        byte[] filecontent = new byte[fileLength.intValue()];
        try
        {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        String[] fileContentArr = new String(filecontent).split("\n");

        List<String> stringList = Arrays.asList(fileContentArr);
        return stringList;

    }

    public static void writeFile(String fileName){
        File file2 = new File("E:\\alala"+fileName+".csv");
        Writer write = null;
        try {
            write = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file2), "UTF-8"));
            for (int i = 0; i < 100000; i++) {
                write.write(RandomUtil.generateRandomNumber() +"\n");
            }

            write.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if(write != null){
                try {
                    write.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void writeFile2(String fileName,List<Integer> content){
        File file2 = new File("E:\\alala\\"+fileName+".csv");
        Writer write = null;
        try {
            write = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file2), "UTF-8"));
            for (Iterator<Integer> it = content.iterator();it.hasNext();){
                write.write(it.next() +"\n");
            }

            write.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if(write != null){
                try {
                    write.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void writeFileByPath(String filePath, List<JSONObject> content){
        File file2 = new File(filePath);
        Writer write = null;
        try {
            write = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file2), "UTF-8"));
            write.write(JSON.toJSONString(content) +"\n");


            write.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if(write != null){
                try {
                    write.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

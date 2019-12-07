package org.zhuzhenxi.test.serializable;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException,ClassNotFoundException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("E:/person.out"));
        oos.writeObject(new ExampleObject("Bruce", "123456"));
        oos.close();

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("E:/person.out"));
        ExampleObject p = (ExampleObject) ois.readObject();
        System.out.println(p);

    }
}

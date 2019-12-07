package org.zhuzhenxi.test.tree;

import org.zhuzhenxi.test.file.FileUtil;

import java.util.LinkedList;
import java.util.List;

public class BinaryTree {
    public static void main(String[] args) {

        List<Integer> min100 = new LinkedList<>();
        for (int i = 0; i < 2; i++) {
            List<String> currentList = FileUtil.readFile("number" + i);
            for (String str:currentList){
                int currentInteger = Integer.valueOf(str);

            }
        }

//        BinaryTreeNode root = new BinaryTreeNode();



    }

}

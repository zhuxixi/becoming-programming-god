package org.zhuzhenxi.test.tree;

import lombok.Data;

@Data
public class BinaryTreeNode {
    BinaryTreeNode parent;
    BinaryTreeNode leftSon;
    BinaryTreeNode rightSon;
    Integer value;

}

package org.zhuzhenxi.test.linklist;

import lombok.Data;

/**
 * @author zhuzhenxi
 * @date 2019.09.01
 */
@Data
public class AliNode {
    private int a;
    private AliNode next;
    public AliNode(int a1){
        a = a1;
    }
}

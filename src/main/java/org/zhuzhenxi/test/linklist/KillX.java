package org.zhuzhenxi.test.linklist;

public class KillX {
    public static void main(String[] args){
        AliNode head = tailInit();
        while (head.getNext()!=null){
            AliNode next = head.getNext();
            System.out.println(next.getA());
            head = next;
        }



    }

    /**
     * 头插法
     * @return
     */
    private static AliNode headInit(){
        AliNode head = new AliNode(0);
        head.setNext(null);
        int i = 1;
        while (i<25){
            AliNode node = new AliNode(i);
            node.setNext(head.getNext());
            head.setNext(node);
            i++;
        }
        return head;
    }
    /**
     * 尾插法 要有一个尾指针,永远都指向最后一个节点，每次设置好新的next，移动指针
     * @return
     */
    private static AliNode tailInit(){
        AliNode head = new AliNode(0);
        head.setNext(null);
        AliNode tail = head;
        int i = 1;
        while (i<25){
            AliNode node = new AliNode(i);
            tail.setNext(node);
            tail = node;
            i++;
        }
        return head;
    }
}

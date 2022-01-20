package com.jaly.leecode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LinkedListDeepCopy {

    private List<Node> originalList = new ArrayList<>();

    private List<Node> deepCopyList = new ArrayList<>();

    private List<NewNode> flagCopyList = new ArrayList<>();

    /**
     * create the original list
     */
    public void buildingOriginalList(int length) {
        // 1. create node and set node value
        for (int index = 0; index < length; index++) {
            Node node = new Node(index);
            originalList.add(node);
        }
        // 2. set the next point
        for (int index = 0; index < length-1; index++) {
            originalList.get(index).setNext(originalList.get(index+1));
        }
        originalList.get(length-1).setNext(null);
        // 3. set random point
        originalList.get(0).setRandom(originalList.get(2));
        originalList.get(3).setRandom(originalList.get(1));

        // originalList.get(0).setRandom(originalList.get(2));
        // originalList.get(3).setRandom(originalList.get(0));
    }

    /**
     * A linked list of length n is given such that
     * each node contains an additional random pointer,
     * which could point to any node in the list, or null.
     *
     * Write program to make deep copy of original list.
     *
     * @param head
     * @return
     */
    public Node copyRandomList(Node head) {
        Node copiedNode = null;
        if (head == null)  return copiedNode;
        // if the input node is the last one
        // and there is no random point
        if (head.getNext() == null && head.getRandom() == null) {
            copiedNode = new Node(head.getVal());
            deepCopyList.add(copiedNode);
            return copiedNode;
        }

        flagNodeList();
        System.out.println("set default count -------");
        printFlagLink(flagCopyList);
        System.out.println("\n");

        nextNodeTraversal(head);
        randomNodeTraversal(head);
        throughList();
        // remove flag=0 node
        Iterator<NewNode> iterator = flagCopyList.iterator();
        while(iterator.hasNext()) {
            NewNode newNode = iterator.next();
            if (newNode.getByRefCount() == 0 ) {
                iterator.remove();
            }
        }

        //TODO: 打印一下 flagCopyList
        System.out.println("delete count=0  -------");
        printFlagLink(flagCopyList);
        System.out.println("\n");


        // create new Node instance for deep copying
        flagCopyList.forEach(newNode -> {
            Node tmpNode = new Node(newNode.getNode().getVal());
            deepCopyList.add(tmpNode);
        });

        // build next link
        for (int i = 0; i < deepCopyList.size() - 1; i++){
            deepCopyList.get(i).setNext(deepCopyList.get(i+1));
        }
        //build random link
        for (int i = 0; i < flagCopyList.size(); i++ ) {
            Node randomNode = flagCopyList.get(i).getNode().getRandom();
            if (randomNode == null) continue;
            for (int j = 0 ; j < flagCopyList.size(); j++) {
                if (randomNode.equals(flagCopyList.get(j).getNode())) {
                    deepCopyList.get(i).setRandom(deepCopyList.get(j));
                }
            }
        }
        //TODO: 打印一下 flagCopyList

        return copiedNode;
    }

    public void printLink(Node node) {
        System.out.println("--------------------------------------------");
        while(node != null) {
            System.out.print(node + " (next)-> " + node.getNext() + " | ");
            System.out.println(node + " (random)~>" + node.getRandom());
            node = node.getNext();
        }
    }

    public void printFlagLink(List<NewNode> nodes) {
        nodes.forEach(node -> {
            System.out.print(node + " ");
        });
    }

    /**
     * pick out the new node, which has been cloned in list
     * @param node
     * @return
     */
    private Node pickExistCloneNode(Node node) {
        Node tmp = null;
        for (int i=0; i < deepCopyList.size(); i++) {
            if (deepCopyList.get(i).equals(node)) {
                tmp = deepCopyList.get(i);
                break;
            }
        }
        return tmp;
    }

    /**
     * 给list的每个节点加上标记
     */
    private void flagNodeList() {
        if (originalList != null) {
            originalList.forEach(node -> {
                flagCopyList.add(new NewNode(node));
            });
        }
    }

    /**
     * The node byRefCount field will be plus one
     * if the "next" NOT null
     */
    private void nextNodeTraversal(Node entryNode) {
        int index = 0;
        int upperLimitCount = flagCopyList.size() - 1;
        for (int i = 0; i < flagCopyList.size(); i++) {
            if (entryNode.equals(flagCopyList.get(i).getNode())) {
                index = i;
                break;
            }
        }
        for (int i= index; i < flagCopyList.size(); i++) {
            if (flagCopyList.get(i).getByRefCount() >= upperLimitCount) {
                break;
            }
            flagCopyList.get(i).setByRefCount(flagCopyList.get(i).getByRefCount() + 1);
        }
    }

    /**
     * The node byRefCount field will be plus one
     * if the "random" NOT null
     */
    private void randomNodeTraversal(Node entryNode) {
        Node randomNode = entryNode.getRandom();
        Node node = entryNode;
        int upperLimitCount = flagCopyList.size() - 1;
        do {
            if (randomNode == null) {
                node = node.getNext();
                if (node!= null) {
                    randomNode = node.getRandom();
                } else randomNode = null;
            } else {
                for (int i = 0; i < flagCopyList.size(); i++) {
                    if (randomNode.equals(flagCopyList.get(i).getNode())) {
                        if (flagCopyList.get(i).getByRefCount() >= upperLimitCount) {
                            break;
                        }
                        flagCopyList.get(i).setByRefCount(flagCopyList.get(i).getByRefCount() + 1);
                        if (randomNode.getRandom()!=null) {
                            node = flagCopyList.get(i).getNode();
                            randomNode = node.getRandom();
                        } else {
                            node = null;
                            break;
                        }
                    }
                }

            }
        } while(node != null);
    }

    private void throughList() {
        NewNode currentNode = flagCopyList.get(0);
        // get the first node, which its byRefCount is NOT 0
        int firstRefCountNotZero = 0;
        for (int i=0; i < flagCopyList.size(); i++) {
            if (flagCopyList.get(i).getByRefCount() != 0) {
                firstRefCountNotZero = i;
                break;
            }
        }
        for (int i = firstRefCountNotZero; i < flagCopyList.size(); i++) {
            if (flagCopyList.get(i).getByRefCount() == 0) {
                flagCopyList.get(i).setByRefCount(1);
            }
        }
    }

    public static void main(String[] args) {
        LinkedListDeepCopy demo = new LinkedListDeepCopy();
        demo.buildingOriginalList(4);
        demo.printLink(demo.originalList.get(0));
        demo.copyRandomList(demo.originalList.get(3));
        demo.printLink(demo.deepCopyList.get(0));
    }

}

class Node {
    private int val;
    private Node next;
    private Node random;

    public Node(int val) {
        this.setVal(val);
        this.setNext(null);
        this.setRandom(null);
    }

    public Node(int val, Node next, Node random) {
        this.setVal(val);
        this.setNext(next);
        this.setRandom(random);
    }

    @Override
    public int hashCode() {
        int code = 31 * this.getVal() +
                   (this.getNext() != null ? this.getNext().getVal() : 0) +
                   (this.getRandom() != null ? this.getRandom().getVal() : 0);
        return code;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Node))
            return false;
        return this.hashCode() == other.hashCode();
    }

    @Override
    public String toString(){
        return "[Node("+ val + ")]";
    }

    public String toNextString() {
        String result = "";
        if (this.next != null) {
            result += " ->";
        } else {
            result += " -> Null";
        }
        return result;
    }
    public String toRandomNextString() {
        String result = "";
        if (this.random != null) {
            result += " ~>";
        } else {
            result += " ~> Null";
        }
        return result;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public Node getRandom() {
        return random;
    }

    public void setRandom(Node random) {
        this.random = random;
    }
}

class NewNode {
    private Node node;
    private int byRefCount;


    public NewNode(Node node) {
        this.setNode(node);
        this.setByRefCount(0);
    }


    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public int getByRefCount() {
        return byRefCount;
    }

    public void setByRefCount(int byRefCount) {
        this.byRefCount = byRefCount;
    }

    @Override
    public String toString() {
        return this.getNode() + ".count(" + byRefCount + ")";
    }
}
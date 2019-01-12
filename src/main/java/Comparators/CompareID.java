package Comparators;

import JSONClasses.Node;


public class CompareID implements Comparator{
    public boolean compararp1top2 (Node node1, Node node2) {
        boolean b = false;
        if (node1.getId() > node2.getId()) {
            b = true;
        }
        return b;
    }
    public boolean compararp2top1 (Node node1, Node node2) {
        boolean b = false;
        if (node1.getId() < node2.getId()) {
            b = true;
        }
        return b;
    }
    public boolean compararp2top1IncludeEqual (Node node1, Node node2) {
        boolean b = false;
        if (node1.getId() <= node2.getId()) {
            b = true;
        }
        return b;
    }
}

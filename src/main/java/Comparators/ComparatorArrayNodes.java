package Comparators;

import JSONClasses.Node;
import JSONClasses.User;

import java.util.ArrayList;

public interface ComparatorArrayNodes {
    public boolean compararp1top2 (ArrayList<Node> listnode1, ArrayList<Node> listnode2);
    public boolean compararp2top1 (ArrayList <Node> listnode1, ArrayList <Node> listnode2);
    public boolean compararp2top1IncludeEqual (ArrayList <Node> listnode1, ArrayList <Node> listnode2);
}

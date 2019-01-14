package Comparators;

import Algorismes.BranchAndBound;
import JSONClasses.Node;

import java.util.ArrayList;

public class CompareNodesByMinimCost implements ComparatorArrayNodes{
    public boolean compararp1top2 (ArrayList<Node> listnode1, ArrayList<Node> listnode2) {
        BranchAndBound bb = new BranchAndBound();
        if (bb.sumaCostos(listnode1) < bb.sumaCostos(listnode2)) {
            return true;
        }
        return false;
    }
    public boolean compararp2top1 (ArrayList <Node> listnode1, ArrayList <Node> listnode2) {
        BranchAndBound bb = new BranchAndBound();
        if (bb.sumaCostos(listnode1) > bb.sumaCostos(listnode2)) {
            return true;
        }
        return false;
    }
    public boolean compararp2top1IncludeEqual (ArrayList <Node> listnode1, ArrayList <Node> listnode2) {
        BranchAndBound bb = new BranchAndBound();
        if (bb.sumaCostos(listnode1) <= bb.sumaCostos(listnode2)) {
            return true;
        }
        return false;
    }
}

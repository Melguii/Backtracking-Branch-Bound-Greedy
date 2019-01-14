package Comparators;

import Algorismes.BranchAndBound;
import JSONClasses.Node;

import java.util.ArrayList;

public class CompareNodesByFiabilitat implements ComparatorArrayNodes{
    public boolean compararp1top2 (ArrayList<Node> listnode1, ArrayList<Node> listnode2) {
        BranchAndBound bb = new BranchAndBound();
        if (bb.calculFiabilitat(listnode1) > bb.calculFiabilitat(listnode2)) {
            return true;
        }
        return false;
    }
    public boolean compararp2top1 (ArrayList <Node> listnode1, ArrayList <Node> listnode2) {
        BranchAndBound bb = new BranchAndBound();
        if (bb.calculFiabilitat(listnode1) < bb.calculFiabilitat(listnode2)) {
            return true;
        }
        return false;
    }
    public boolean compararp2top1IncludeEqual (ArrayList <Node> listnode1, ArrayList <Node> listnode2) {
        BranchAndBound bb = new BranchAndBound();
        if (bb.calculFiabilitat(listnode1) <= bb.calculFiabilitat(listnode2)) {
            return true;
        }
        return false;
    }
}

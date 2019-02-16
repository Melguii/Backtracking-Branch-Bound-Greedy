package Comparators;

import Algorismes.BranchAndBound;
import JSONClasses.Node;

import java.util.ArrayList;

public class CompareNodesByFiabilitat implements ComparatorArrayNodes{
    /**
     * Comparem si el primer cami es mes fiable que l'altre
     * @param listnode1 Primer cami que volem comparar
     * @param listnode2 Segon cami que volem comparar
     * @return Retornem si el preimer cami té més fiabilitat que el segon
     */
    public boolean compararp1top2 (ArrayList<Node> listnode1, ArrayList<Node> listnode2) {
        BranchAndBound bb = new BranchAndBound();
        if (bb.calculFiabilitat(listnode1) > bb.calculFiabilitat(listnode2)) {
            return true;
        }
        return false;
    }

    /**
     * Comparem si el segon camí es mes fiable que l'altre
     * @param listnode1 Primer cami que volem comparar
     * @param listnode2 Segon cami que volem comparar
     * @return Retornem si el segon cami té més fiabilitat que el primer
     */
    public boolean compararp2top1 (ArrayList <Node> listnode1, ArrayList <Node> listnode2) {
        BranchAndBound bb = new BranchAndBound();
        if (bb.calculFiabilitat(listnode1) < bb.calculFiabilitat(listnode2)) {
            return true;
        }
        return false;
    }

    /**
     * Comparem si el segon camí es més fiable o IGUAL de fiable que el primer
     * @param listnode1 Primer camí a comparar
     * @param listnode2 Segon camí a comparar
     * @return Retornem si el segon cami té més fiabilitat que el primer
     */
    public boolean compararp2top1IncludeEqual (ArrayList <Node> listnode1, ArrayList <Node> listnode2) {
        BranchAndBound bb = new BranchAndBound();
        if (bb.calculFiabilitat(listnode1) <= bb.calculFiabilitat(listnode2)) {
            return true;
        }
        return false;
    }
}

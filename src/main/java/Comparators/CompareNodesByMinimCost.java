package Comparators;

import Algorismes.BranchAndBound;
import JSONClasses.Node;

import java.util.ArrayList;

public class CompareNodesByMinimCost implements ComparatorArrayNodes{
    /**
     * Comparem si el  primer arraylist de nodes és més gran que el segon
     * @param listnode1 Primer arraylist de nodes que introduim
     * @param listnode2 Segon arraylist de nodes que introduim
     * @return Retonrem si la primera llista introduida té més activitat acumulada que la segona
     */
    public boolean compararp1top2 (ArrayList<Node> listnode1, ArrayList<Node> listnode2) {
        BranchAndBound bb = new BranchAndBound();
        if (bb.sumaCostos(listnode1) < bb.sumaCostos(listnode2)) {
            return true;
        }
        return false;
    }

    /**
     * Comparem si el segon arraylist de nodes és més gran que el primer
     * @param listnode1 Primera llista de nodes a comparar
     * @param listnode2 Segona llista de nodes a comparar
     * @return Retornem si la segona lllista té un cost acumulat més gran que la primera
     */
    public boolean compararp2top1 (ArrayList <Node> listnode1, ArrayList <Node> listnode2) {
        BranchAndBound bb = new BranchAndBound();
        if (bb.sumaCostos(listnode1) > bb.sumaCostos(listnode2)) {
            return true;
        }
        return false;
    }

    /**
     * Comparem si la segona llista és més gran o igual que la primera
     * @param listnode1 Primera llista de nodes a comparar
     * @param listnode2 Segona llista de nodes a comparar
     * @return Retornem si la segona llista de nodes (camí) és més gran o igual que la primer en quan a suma de costos
     */
    public boolean compararp2top1IncludeEqual (ArrayList <Node> listnode1, ArrayList <Node> listnode2) {
        BranchAndBound bb = new BranchAndBound();
        if (bb.sumaCostos(listnode1) <= bb.sumaCostos(listnode2)) {
            return true;
        }
        return false;
    }
}

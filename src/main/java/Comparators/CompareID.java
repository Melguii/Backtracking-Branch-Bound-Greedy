package Comparators;

import JSONClasses.Node;


public class CompareID implements Comparator{
    /**
     * Compara el Node 1 amb el Node 2 (SEGONS el seu ID)
     * @param node1 Primer node que volem comparar
     * @param node2 Segon node que volem comparar
     * @return Retonrem true si el primer es més gran que el segon segons el seu id
     */
    public boolean compararp1top2 (Node node1, Node node2) {
        boolean b = false;
        if (node1.getId() > node2.getId()) {
            b = true;
        }
        return b;
    }

    /**
     * Compara el Node 2 amb el Node 1 (SEGONS el seu ID)
     * @param node1 Primer node que volem que volem comparar
     * @param node2 Segon node que volem comparar
     * @return Retorna true si el id del segon node és més gran que el del primer node
     */
    public boolean compararp2top1 (Node node1, Node node2) {
        boolean b = false;
        if (node1.getId() < node2.getId()) {
            b = true;
        }
        return b;
    }

    /**
     * Compara si l'id del segon node amb  el del primer node
     * @param node1 Primer node que volem comparar
     * @param node2 Segon node que volem comparar
     * @return Retorna si el id del segon node és més gran o igual que el del primer node
     */
    public boolean compararp2top1IncludeEqual (Node node1, Node node2) {
        boolean b = false;
        if (node1.getId() <= node2.getId()) {
            b = true;
        }
        return b;
    }
}

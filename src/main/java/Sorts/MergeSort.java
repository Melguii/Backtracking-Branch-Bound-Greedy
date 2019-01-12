/**
 * Mergesort
 * @autor Gerard Melgares Martinez  i Josep Roig Torres
 * @version 1.0.0
 */
package Sorts;

import Comparators.Comparator;
import JSONClasses.Node;
import JSONClasses.Post;
import JSONClasses.Server;


import java.util.ArrayList;
import java.util.List;

public class MergeSort {
    /**
     *Metode mergesort que ens permet ordenar una llista concreta
     * @param nodes Array de nodes a ordenar
     * @param c Comparador que gastem en el nostre mergesort
     * @param i posicio inicial
     * @param j posicio final
     * @return llista ordenada
     */
    public Node [] mergeSort (Node [] nodes, Comparator c, int i, int j) {
        int mig;
        if (i >= j) {
            return nodes;
        }
        else {
            mig = (i + j) / 2;
            nodes = mergeSort (nodes,c,i,mig);
            nodes = mergeSort (nodes,c,mig+1,j);
            nodes = merge (nodes,i,mig,j,c);
        }
        return nodes;
    }

    /**
     * Metode que ens permet anar adjuntant els diferents arrays
     * @param
     * @param i posicio inicial
     * @param mig posicio intermitja
     * @param j posicio final
     * @param c comparador que gastem en el nostre mergesort
     * @return l'array amb una petita part ordenada
     */
    private Node[] merge (Node [] nodes , int i, int mig, int j, Comparator c) {
        Node [] nodesAux = new Node [nodes.length];
        int k1;
        int k2;
        int cursor;
        int kr;
        k1 = i;
        k2 = mig + 1;
        cursor = 0;
        int y = 0;
        while ((k1 <= mig) && (k2 <= j) ) {
            if (c.compararp2top1IncludeEqual(nodes[k1], nodes[k2])) {
                nodesAux[y] = nodes[k1];
                y++;
                k1 = k1 + 1;
                cursor = cursor + 1;
            }
            else {
                nodesAux[y] = nodes[k2];
                y++;
                k2 = k2 + 1;
                cursor = cursor + 1;
            }
        }
        while (k1 <= mig) {
            nodesAux[y] = nodes[k1];
            y++;
            k1 = k1 + 1;
            cursor = cursor + 1;
        }
        while (k2 <= j) {
            nodesAux[y] = nodes[k2];
            y++;
            k2 = k2 + 1;
            cursor = cursor + 1;
        }
        cursor = 0;
        kr = i;
        while (kr <= j) {
            nodes [kr] = nodesAux[cursor];
            kr = kr + 1;
            cursor = cursor + 1;
        }

        return nodes;
    }
}

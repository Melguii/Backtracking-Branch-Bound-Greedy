package Sorts;

import Comparators.ComparatorArrayNodes;
import Comparators.ComparatorUser;
import JSONClasses.Node;


import java.util.ArrayList;

public class QuickSortArrayNodes {
    /**
     * Metode que ens permet realitzar el quickSort
     * @param p Array que volem ordenar
     * @param c Comparador que usarem per tal de realitzar el quicksort
     * @param i Posicio des d'on comencem a ordenar
     * @param j Posicio on volem acabar ordenant
     * @return Retornem el array de forma ordenada
     */
    public ArrayList<ArrayList<Node>> quickSort (ArrayList <ArrayList<Node>> p, ComparatorArrayNodes c, int i, int j) {
        int s;
        int t;
        int array_aux_ij [] = new int [2];
        int array_aux_st [] = new int [2];

        if (i >= j) {
            return p;
        }
        else{
            array_aux_ij[0] = i;
            array_aux_ij[1] = j;
            array_aux_st = particio(p,array_aux_ij,c);
            s = array_aux_st[0];
            t = array_aux_st[1];
            p = quickSort(p,c,i,t);
            p = quickSort(p,c,s,j);
        }
        return p;
    }

    /**
     * Metode que ens ordena una petita de l'array
     * @param p arraylist que volem ordenar
     * @param array_aux_ij posicio inicial i final que estem actualment, en forma de array
     * @param c comparador que gastem en el quicksort
     * @return El nostre array amb la petita part ordenada
     */
    private int [] particio (ArrayList<ArrayList<Node>> p, int array_aux_ij[], ComparatorArrayNodes c) {
        int mig;
        ArrayList<Node> pivot = new ArrayList<Node>();
        ArrayList<Node> tmp = new ArrayList <Node> ();
        int s;
        int t;
        s = array_aux_ij[0];
        t = array_aux_ij[1];
        mig = (array_aux_ij[0] + array_aux_ij[1])/2;
        pivot = p.get(mig);
        while (s <= t) {
            while (c.compararp1top2(p.get(s),pivot)) {
                s = s + 1;
            }
            while (c.compararp2top1(p.get(t),pivot)) {
                t = t - 1;
            }
            if (s < t) {
                tmp = p.get(s);
                p.set (s,p.get(t));
                p.set (t,tmp);
                s = s + 1;
                t = t - 1;
            }
            else {
                if (s == t) {
                    s = s + 1;
                    t = t - 1;
                }
            }
        }
        int [] array_aux_st = new int[2];
        array_aux_st[0]= s;
        array_aux_st[1]=t;

        return array_aux_st;
    }
}

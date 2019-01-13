package Sorts;


import Comparators.ComparatorServerList;
import JSONClasses.Post;
import JSONClasses.Server;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class QuickSort {

    /**
     * Quicksort per ordenar un arraylist de posts
     * @param p arraylist de posts
     * @param c comprador que gastem en el quicksort
     * @param i posicio inicial
     * @param j posicio final
     * @return
     */
    public ArrayList<ArrayList <Server>> quickSort (ArrayList<ArrayList<Server>> p, ComparatorServerList c, int i, int j, int numServers) {
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
            array_aux_st = particio(p,array_aux_ij,c,numServers);
            s = array_aux_st[0];
            t = array_aux_st[1];
            p = quickSort(p,c,i,t,numServers);
            p = quickSort(p,c,s,j,numServers);
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
    private int [] particio (ArrayList <ArrayList <Server>> p, int array_aux_ij[], ComparatorServerList c, int numServers) {
        int mig;
        ArrayList <Server> pivot = new ArrayList<Server>();
        ArrayList <Server> tmp = new ArrayList<Server>();
        int s;
        int t;
        s = array_aux_ij[0];
        t = array_aux_ij[1];
        mig = (array_aux_ij[0] + array_aux_ij[1])/2;
        pivot = p.get(mig);
        while (s <= t) {
            while (c.compararp2top1(p.get(s),pivot,numServers)) {
                s = s + 1;
            }
            while (c.compararp1top2(p.get(t),pivot,numServers)) {
                t = t - 1;
            }
            if (s < t) {
                tmp = p.get(s);
                p.set(s, p.get(t));
                p.set(t,tmp);
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

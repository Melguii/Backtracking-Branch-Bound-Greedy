package JSONClasses;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private int opcio;




    /**
     * S'ocupa de detectar quin és el fitxer escollit per obrir
     * @param args String dels arguments passats abans d'iniciar el programa
     * @return Retorna una variable tipus FileReader per tal que el fitxer pugui ser llegit/interpretat
     */
    public FileReader menuFitxers(String[] args) {
        FileReader fitxer;
        fitxer = seleccioFitxer(args);
        return fitxer;
    }

    /**
     * Control del nom de fitxer introduit( mirem si es correcte o si no existeix, i per tant hem de mostrar un error)
     * també establim que tots els fitxers estaran a la carpeta datasets
     * @param args String dels arguments passats abans d'iniciar el programa
     * @return Retorna una variable tipus FileReader per tal que el fitxer pugui ser llegit/interpretat
     */
    private FileReader seleccioFitxer(String[] args) {
        FileReader fitxer = null;
        String ubicacio = new String();
        ubicacio = "datasets/" + args[2];
        do {
            try {
                fitxer = new FileReader(ubicacio);
            } catch (FileNotFoundException e) {
                System.out.println("Error fitxer especificat no trobat (ha d'estar a la carpeta datasets),no ens petaras \nel programa tan facilment, fem PAED (⌐■_■)");
                System.out.println("La nostra generositat no coneix limits, trona'm a introduir nom del fitxer :):");
                Scanner sc = new Scanner(System.in);
                ubicacio = "datasets/" + sc.nextLine();
            }
        } while (fitxer == null);
        return fitxer;
    }

    /**
     * Metode de busqueda binaria, per tal de buscar un username concret (concretament el que busquem
     * és que ens passen per argument, per tal de poder mostrar la comparacio de prioritats d'aquell
     * usuari en concret)
     * @param users Array de users on buscarem el username introduit
     * @param valorBuscat Username que buscarem en l'array
     * @return Retornem tota la informacio del username trobat
     */
    private User busquedaUsuari (User[] users, String valorBuscat) {
        User user = null;
        User [] users_2;
        users_2 = users.clone();

        int principi = 0;
        int fin = users.length - 1;
        int valor_resultat = 0;
        int mig = (principi + fin)/2;
        boolean b = false;
        while (!b && (principi <= fin)){
            mig = (principi + fin)/2;
            if (users[mig].getUsername().equals(valorBuscat)) {
                b = true;
                valor_resultat = mig;
            }
            else {
                if (users[principi].getUsername().equals(valorBuscat)) {
                    b = true;
                    valor_resultat = principi;
                }
                else {
                    if (users[fin].getUsername().equals(valor_resultat)) {
                        b = true;
                        valor_resultat = fin;
                    }
                    else {
                        if (users[mig].getUsername().compareTo(valorBuscat) > 0) {
                            fin = mig - 1;
                        } else {
                            principi = mig + 1;
                        }
                    }
                }
            }
        }
        user = users[valor_resultat];
        return user;
    }

    /**
     * quickSort que ens servira per ordenar el array users per fer la busqueda binaria
     * @param p Array de users que volem ordenar
     * @param i Principi del array
     * @param j Final del array
     * @return Retornem el array introduit totalment ordenat
     */
    public User [] quickSort (User [] p, int i, int j) {
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
            array_aux_st = particio(p,array_aux_ij);
            s = array_aux_st[0];
            t = array_aux_st[1];
            p = quickSort(p,i,t);
            p = quickSort(p,s,j);
        }
        return p;
    }

    /**
     * Metode que ens serveix per tal de poder realitzar correctament el quickSort
     * @param p Array de users que volem ordenar
     * @param array_aux_ij La i i la j en forma de array per tal  que el seu valor
     *                     canvii tambe en el  metode quicksort
     * @return Els nous valors de s i t
     */
    private int [] particio (User [] p, int array_aux_ij[]) {
        int mig;
        User pivot;
        User tmp = new User();
        int s;
        int t;
        s = array_aux_ij[0];
        t = array_aux_ij[1];
        mig = (array_aux_ij[0] + array_aux_ij[1])/2;
        pivot = p[mig];
        while (s <= t) {
            while (pivot.getUsername().compareTo(p[s].getUsername()) > 0) {
                s = s + 1;
            }
            while(pivot.getUsername().compareTo(p[t].getUsername()) < 0) {
                t = t - 1;
            }
            if (s < t) {
                tmp = p[s];
                p[s] = p[t];
                p[t] = tmp;
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
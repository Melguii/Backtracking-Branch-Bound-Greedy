package JSONClasses;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private int opcioMenuFitxers;

    /**
     *
     * @return
     */
    public int seleccioMenuFitxer () {
        String opcioLlegida;
        int opcioInt;
        do {
            mostraMenuFitxers();
            Scanner sc = new Scanner(System.in);
            opcioLlegida = sc.nextLine();
        } while(comprovacioErrorsOpcio(opcioLlegida,1,2));
        return Integer.parseInt(opcioLlegida);
    }

    /**
     * Metode que s'ocupa de comprar que la opcio introduida per l'usuari segons un menu sigui valida
     * @param opcioLlegida
     * @param intervalMenor
     * @param intervalMajor
     * @return
     */
    private boolean comprovacioErrorsOpcio(String opcioLlegida, int intervalMenor, int intervalMajor) {
        int i = 0;
        int opcio;
        if (opcioLlegida.length() == 0) {
            System.out.println("Error no has introduit cap opcio");
            return true;
        }
        else{
            while (i < opcioLlegida.length()) {
                if (opcioLlegida.charAt(i) < '0' || opcioLlegida.charAt(i) > '9') {
                    System.out.println("Error has introduit algun caracter que no es numero");
                    return true;
                }
                i++;
            }
            opcio = Integer.parseInt(opcioLlegida);
            if (opcio < intervalMenor || opcio > intervalMajor) {
                System.out.println("La teva introducció no es cap de les opcions disponibles");
                return true;
            }
        }
        return false;
    }
    private void mostraMenuFitxers() {
        System.out.println("1.Fitxers per defecte (nodes.json, servers.json, users.json)");
        System.out.println("2.Soc informatic, tinc fitxers propis ʘ‿ʘ");
        System.out.println("De quins fitxers vols realiztar la lectura?:");
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
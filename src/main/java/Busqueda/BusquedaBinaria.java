package Busqueda;

import JSONClasses.Node;

public class BusquedaBinaria {
    /**
     * Busqueda binaria que ens facilita la busqueda de un Id concret en un array de nodes(Cost menor que una
     * busqueda normal)
     * @param valorBuscat Valor que busquem
     * @param arrayNodes Array de Nodes on busquem un valor concret
     * @return Posicio on s'ha trobat el id desitjat
     */
    public int busquedaBinaria(int valorBuscat, Node[] arrayNodes) {
        int principi = 0;
        int fin = arrayNodes.length - 1;
        int valor_resultat = 0;
        int mig = (principi + fin)/2;
        boolean b = false;

        while (!b && (principi <= fin)){
            mig = (principi + fin)/2;
            if (arrayNodes[mig].getId() == valorBuscat) {
                b = true;
                valor_resultat = mig;
            }
            else {
                if (arrayNodes[principi].getId() == valorBuscat) {
                    b = true;
                    valor_resultat = principi;
                }
                else {
                    if (arrayNodes[fin].getId() == valor_resultat) {
                        b = true;
                        valor_resultat = fin;
                    }
                    else {
                        if (arrayNodes[mig].getId() > valorBuscat) {
                            fin = mig - 1;
                        } else {
                            principi = mig + 1;
                        }
                    }
                }
            }
        }
        return valor_resultat;
    }
}

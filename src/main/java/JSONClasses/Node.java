
package JSONClasses;

import Busqueda.BusquedaBinaria;

import java.util.ArrayList;
import java.util.List;

public class Node implements Cloneable{

    private int id;
    private double reliability;
    private List<ConnectsTo> connectsTo;

    public Node() {
        this.id = id;
        this.reliability = reliability;
        this.connectsTo = new ArrayList<ConnectsTo>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getReliability() {
        return reliability;
    }

    public void setReliability(double reliability) {
        this.reliability = reliability;
    }

    public List<ConnectsTo> getConnectsTo() {
        return connectsTo;
    }

    public void setConnectsTo(List<ConnectsTo> connectsTo) {
        this.connectsTo = connectsTo;
    }

    /**
     * Metode que serveix per "traduir" l'ID en format String que tenim a connectsTo, a tipus Node
     * @param nodes Llista que conté tots els nodes obtinguts en el JSON ordenats (per tal de poder fer el Quicksort)
     */
    public void referenciarConnexions (Node [] nodes) {
        BusquedaBinaria b = new BusquedaBinaria();
        int posicio;

        //Bucle que recorre tots els connectsTo per tal de realitzar la traduccio en tots les conexions de un Node en concret
        for(int j = 0; j < connectsTo.size();j++) {

            //Fem la busqueda binaria per saber en quina posicio del array de nodes es troba el node cercat
            posicio = b.busquedaBinaria(connectsTo.get(j).getTo(), nodes);

            //Referenciem sempre i quan hagi trobat el node
            if (posicio != -1) {
                connectsTo.get(j).setNode(nodes[posicio]);
            }
        }
    }
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
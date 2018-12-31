
package JSONClasses;

import Busqueda.BusquedaBinaria;

import java.util.ArrayList;
import java.util.List;

public class Server {

    private int id;
    private String country;
    private List<Double> location = null;                   //Falta parlar si farem una ArrayList o un Array fixe de 2 posicions
    private int reachable_from [];
    private List <Node> nodesDisponibles;

    public Server() {
        this.id = id;
        this.country = country;
        this.location = location;
        this.reachable_from = reachable_from;
        this.nodesDisponibles = new ArrayList <Node> ();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Double> getLocation() {
        return location;
    }

    public void setLocation(List<Double> location) {
        this.location = location;
    }

    public int[] getReachable_from() {
        return reachable_from;
    }

    public void setReachable_from(int[] reachable_from) {
        this.reachable_from = reachable_from;
    }

    /**
     * Metode que serveix per traduir el array de ints que conté tots els ids dels Nodes als que pot arribar un server
     * (reachable_from) a un array de nodes(per aixi pode raccedir a ells directament)
     * @param arrayNodes Array que conté tots els nodes obtinguts en el JSON ordenats (per tal de poder realitzar la busqueda binaria)
     */
    public void referenciarNodes(Node [] arrayNodes) {
        int i = 0;
        int j = 0;
        BusquedaBinaria b = new BusquedaBinaria();
        //Recorrem tot el reachable_from per tal de poder realitzar la referencia
        while (i < this.reachable_from.length) {
            //Realitzem la busqueda binaria que ens retorna la posicio on es troba l'id buscat en el array de nodes
            j = b.busquedaBinaria(this.reachable_from[i], arrayNodes);
            if (j != -1 ) {
                nodesDisponibles.add(arrayNodes[j]);
            }
            i++;
        }
    }
    public List<Node> getNodesDisponibles() {
        return nodesDisponibles;
    }

    public void setNodesDisponibles(List<Node> nodesDisponibles) {
        this.nodesDisponibles = nodesDisponibles;
    }
}

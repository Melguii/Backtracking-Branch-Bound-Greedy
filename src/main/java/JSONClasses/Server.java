
package JSONClasses;

import Busqueda.BusquedaBinaria;

import java.util.ArrayList;
import java.util.List;

public class Server implements Cloneable {

    private int id;
    private String country;
    private List<Double> location = null;                   //Falta parlar si farem una ArrayList o un Array fixe de 2 posicions
    private int reachable_from [];
    private List <Node> nodesDisponibles;
    private List <User> users;
    private double carrega;
    private int sumaActivities;

    public Server() {
        this.id = id;
        this.country = country;
        this.location = location;
        this.reachable_from = reachable_from;
        this.nodesDisponibles = new ArrayList <Node> ();
        this.users = new ArrayList<User>();
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

    public Object clone () throws CloneNotSupportedException {
        return super.clone();
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public double getCarrega() {
        return carrega;
    }

    public void setCarrega(double carrega) {
        this.carrega = carrega;
    }

    /**
     * La carrega d'un server es la suma de carregues de cada usuari. La carrega d'un usuari es la proximitat al servidor elevat
     * a l'activitat que tenen amb el servidor.
     *
     * @param u
     */
    public void sumarCarrega (User u) {
        /*System.out.println("----------------------------");
        System.out.println((u.getUsername()));
        System.out.println(s.getCountry());
        System.out.println(u.calculHaversine(s.getLocation()));
        System.out.println(Math.pow(u.calculHaversine(s.getLocation()),u.getActivity()));*/
        sumaActivities = sumaActivities + u.getActivity();
        carrega = carrega + u.calculHaversine(this.location);//Math.pow(u.calculHaversine(s.getLocation()),u.getActivity());
    }
    public void restarCarrega (User u) {
        carrega = carrega -   u.calculHaversine(this.location);//Math.pow(u.calculHaversine(s.getLocation()),u.getActivity());
        sumaActivities = sumaActivities - u.getActivity();
    }

    public int getSumaActivities() {
        return sumaActivities;
    }

    public void setSumaActivities(int sumaActivities) {
        this.sumaActivities = sumaActivities;
    }
}

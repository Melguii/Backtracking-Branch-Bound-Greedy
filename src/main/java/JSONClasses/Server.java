
package JSONClasses;

import Busqueda.BusquedaBinaria;

import java.util.ArrayList;
import java.util.List;


public class Server implements Cloneable {
    //Atributs de la classe Server
    private int id;
    private String country;
    private List<Double> location = null;
    private int reachable_from [];
    private List <Node> nodesDisponibles;
    private List <User> users;
    private double carrega;
    private int sumaActivities;

    /**
     * Constructor de la classe Server, on inicialitzem tots els nostres atributs
     */
    public Server() {
        this.id = id;
        this.country = country;
        this.location = location;
        this.reachable_from = reachable_from;
        this.nodesDisponibles = new ArrayList <Node> ();                                                                //Nodes que es conecten directament al servidor
        this.users = new ArrayList<User>();
    }

    /**
     * Getter de ID
     * @return El id actual del server
     */
    public int getId() {
        return id;
    }

    /**
     * Setter de ID
     * @param id Id que volem establir al server
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter de Country
     * @return El pais del server
     */
    public String getCountry() {
        return country;
    }

    /**
     * Setter de Country
     * @param country Pais en el que volem establir el nostre server
     */
    public void setCountry(String country) {
        this.country = country;
    }


    /**
     * Getter de Location
     * @return Localitzacio del server
     */
    public List<Double> getLocation() {
        return location;
    }


    /**
     * Setter de Location
     * @param location Localitzacio on volem establir el server
     */
    public void setLocation(List<Double> location) {
        this.location = location;
    }

    /**
     * Getter de Reachable_from
     * @return Obtenim els ids des de quins nodes es accessible el node
     */
    public int[] getReachable_from() {
        return reachable_from;
    }


    /**
     * Setter de  Reachable_from
     * @param reachable_from Id dels nodes dels que podem accedir el server
     */
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

    /**
     * Getter de Nodes
     * @return Ens retornen l'array de nodes disponibles
     */
    public List<Node> getNodesDisponibles() {
        return nodesDisponibles;
    }

    /**
     * Setter de nodes
     * @param nodesDisponibles Ids dels nodes des dels que podem accedir a un server concret
     */
    public void setNodesDisponibles(List<Node> nodesDisponibles) {
        this.nodesDisponibles = nodesDisponibles;
    }

    /**
     * Metode que ens permet clonar el nostre Server
     * @return Retornem el objecte clonat
     * @throws CloneNotSupportedException Excepcio que indica que el Server no s'ha pogut clonar
     */
    public Object clone () throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * Getter de Users
     * @return Retornem la llista d'usuaris que pertanyen a un server concret
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * Setter de Users
     * @param users Llista d'usuaris que pertanyen a un server en concret i que volem editar
     */
    public void setUsers(List<User> users) {
        this.users = users;
    }

    /**
     * Getter de carrega
     * @return Obtenir la carrega del server
     */
    public double getCarrega() {
        return carrega;
    }

    /**
     * Setter de carrega
     * @param carrega Carrega que volem establir al server
     */
    public void setCarrega(double carrega) {
        this.carrega = carrega;
    }

    /**
     * Metode que s'ocupa de sumar la carrega d'un usuari en concret i, també, les seves distancies
     *
     * @param u Usuari del que volem, sumar la seva activitat i distancia
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

    /**
     * Metode que s'ocupa de restar la carrega total i la suma d'activitats aportada per un usuari concret
     * @param u Usuari del que volem restar la carrega i l'activitat
     */
    public void restarCarrega (User u) {
        carrega = carrega -   u.calculHaversine(this.location);//Math.pow(u.calculHaversine(s.getLocation()),u.getActivity());
        sumaActivities = sumaActivities - u.getActivity();
    }

    /**
     * Obtenir la suma de totes les activitats que gestiona un server en concret
     * @return La suma d'activitats del server
     */
    public int getSumaActivities() {
        return sumaActivities;
    }

    /**
     * Establir una quantitat a la suma d'actiivtats total del server
     * @param sumaActivities Suma d'activitats total que volem establir en el nostre server
     */
    public void setSumaActivities(int sumaActivities) {
        this.sumaActivities = sumaActivities;
    }
}

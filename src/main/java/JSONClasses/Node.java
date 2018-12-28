
package JSONClasses;

import java.util.ArrayList;
import java.util.List;

public class Node {

    private int id;
    private double reliability;
    private List<ConnectsTo> connectsTo = null;
    private int location []; //latitud, longitud
    private ArrayList <Integer> reachable_from;

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

    public int[] getLocation() {
        return location;
    }

    public void setLocation(int[] location) {
        this.location = location;
    }

    public ArrayList<Integer> getReachable_from() {
        return reachable_from;
    }

    public void setReachable_from(ArrayList<Integer> reachable_from) {
        this.reachable_from = reachable_from;
    }

    public void referenciarConnexionsNodes () {

    }
}
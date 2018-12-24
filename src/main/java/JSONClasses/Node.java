
package JSONClasses;

import java.util.ArrayList;
import java.util.List;

public class Node {

    private int id;
    private double reliability;
    private List<ConnectsTo> connectsTo = null;

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

}
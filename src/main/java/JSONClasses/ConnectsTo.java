package JSONClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConnectsTo implements Cloneable{

    private int to;
    private String name;
    private int cost;
    private Node node;

    public ConnectsTo() {
        this.to = to;
        this.name = name;
        this.cost = cost;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
package JSONClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConnectsTo implements Cloneable{
    //Atributs de ConnectsTo
    private int to;
    private String name;
    private int cost;
    private Node node;

    /**
     * Constructor de la classe ConnectsTo, on simplement inicialitzem tots els atributs de la classe.
     */
    public ConnectsTo() {
        this.to = to;
        this.name = name;
        this.cost = cost;
    }

    /**
     * Getter de To
     * @return Retorna cap a quin Node va un Node concret
     */
    public int getTo() {
        return to;
    }


    /**
     * Setter de To
     * @param to Estableix cap a quin Node va un Node concret
     */
    public void setTo(int to) {
        this.to = to;
    }

    /**
     * Getter de Name
     * @return Retorna quin és el nom de la connexió
     */
    public String getName() {
        return name;
    }

    /**
     * Setter de name
     * @param name Quin és el nom que volem establir a la connexió
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter de Cost
     * @return Cost que té la connexió en concret
     */
    public int getCost() {
        return cost;
    }

    /**
     * Setter de cost
     * @param cost Cost que volem establir a la connexió
     */
    public void setCost(int cost) {
        this.cost = cost;
    }

    /**
     * Getter de Node
     * @return Retorna el node amb el que s'esta connectat
     */
    public Node getNode() {
        return node;
    }

    /**
     * Setter de Node
     * @param node Node que volem establir a la connexió
     */
    public void setNode(Node node) {
        this.node = node;
    }

    /**
     * Metode que ens permet clonar la classe ConnectsTo
     * @return Retorna l'objecte clonat
     * @throws CloneNotSupportedException Excepció que s'activa si no s'ha pogut dur a terme el clone
     */
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
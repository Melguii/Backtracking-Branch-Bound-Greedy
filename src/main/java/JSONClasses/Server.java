
package JSONClasses;

import java.util.ArrayList;
import java.util.List;

public class Server {

    private String id;
    private String country;
    private List<Double> location = null;                   //Falta parlar si farem una ArrayList o un Array fixe de 2 posicions
    private int reachable_from;

    public Server() {
        this.id = id;
        this.country = country;
        this.location = location;
        this.reachable_from = reachable_from;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public int getReachableFrom() {
        return reachable_from;
    }

    public void setReachableFrom(int reachableFrom) {
        this.reachable_from = reachableFrom;
    }

}

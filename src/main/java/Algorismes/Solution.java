package Algorismes;

import JSONClasses.Server;
import JSONClasses.User;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    Server s;
    List <User> users;
    float activitatTotal;
    double carrega;

    public Solution () {
        users = new ArrayList<User>();
        carrega = 0;
    }

    public Server getS() {
        return s;
    }

    public void setS(Server s) {
        this.s = s;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public float getActivitatTotal() {
        return activitatTotal;
    }

    public void setActivitatTotal(float activitatTotal) {
        this.activitatTotal = activitatTotal;
    }

    public double getCarrega() {
        return carrega;
    }

    public void setCarrega(double carrega) {
        this.carrega = carrega;
    }

    public void sumarCarrega (User u) {
        int w = 0;
        carrega = carrega + Math.pow(u.calculHaversine(s.getLocation()),u.getActivity());
    }

}

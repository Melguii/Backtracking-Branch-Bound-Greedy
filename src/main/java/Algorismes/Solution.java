package Algorismes;

import JSONClasses.Server;
import JSONClasses.User;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    Server s;
    List <User> users;
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


    public double getCarrega() {
        return carrega;
    }

    public void setCarrega(double carrega) {
        this.carrega = carrega;
    }

    public void sumarCarrega (User u) {
        carrega = carrega + Math.pow(u.calculHaversine(s.getLocation()),u.getActivity());
    }
    public Object clone()
    {
        Object clone = null;
        try
        {
            clone = super.clone();
        }
        catch(CloneNotSupportedException e)
        {
            // No deberia suceder
        }
        return clone;
    }

}

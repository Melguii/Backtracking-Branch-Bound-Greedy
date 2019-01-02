package Algorismes;

import JSONClasses.Server;
import JSONClasses.User;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    Server s;
    List <User> users;
    double carrega;
    int sumaActivities;

    public Solution () {
        users = new ArrayList<User>();
        carrega = 0;
        sumaActivities = 0;
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
        /*System.out.println("----------------------------");
        System.out.println((u.getUsername()));
        System.out.println(s.getCountry());
        System.out.println(u.calculHaversine(s.getLocation()));
        System.out.println(Math.pow(u.calculHaversine(s.getLocation()),u.getActivity()));*/
        //TODO ELS HAVERSINE SON MES PODEROSOS QUE L'ACTIVITAT
        sumaActivities = sumaActivities + u.getActivity();
        carrega = carrega + u.calculHaversine(s.getLocation());//Math.pow(u.calculHaversine(s.getLocation()),u.getActivity());
    }
    public void restarCarrega (User u) {
        carrega = carrega -   u.calculHaversine(s.getLocation());//Math.pow(u.calculHaversine(s.getLocation()),u.getActivity());
        sumaActivities = sumaActivities - u.getActivity();
    }

    public int getSumaActivities() {
        return sumaActivities;
    }

    public void setSumaActivities(int sumaActivities) {
        this.sumaActivities = sumaActivities;
    }
}

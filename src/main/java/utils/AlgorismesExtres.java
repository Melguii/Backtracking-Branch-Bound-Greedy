package utils;

import JSONClasses.Server;
import JSONClasses.User;

import java.util.List;

public class AlgorismesExtres {

    public int obtindreMaximArray (List<Server> s) {
        int maxim = 0;
        int i = 0;

        while (i < s.size()) {
            if (s.get(i).getSumaActivities() > maxim) {
                maxim = s.get(i).getSumaActivities();
            }
            i++;
        }
        return maxim;
    }

    public int obtindreMinimArray (List <Server> s, int numServers, boolean finalArr) {
        int minim = Integer.MAX_VALUE;
        int i = 0;

        if(!(finalArr && (numServers != s.size()))) {
            while (i < s.size()) {
                if (s.get(i).getSumaActivities() < minim) {
                    minim = s.get(i).getSumaActivities();
                }
                i++;
            }
            return minim;
        }
        else {
            return 0;
        }
    }

    public void clonar (List<Server> solution, List <Server> possibleSolucio) {
        for (int w = 0; w < possibleSolucio.size();w++) {
            Server s = new Server();
            s.setId(possibleSolucio.get(w).getId());
            s.setCountry(possibleSolucio.get(w).getCountry());
            s.setLocation(possibleSolucio.get(w).getLocation());
            s.setReachable_from(possibleSolucio.get(w).getReachable_from());
            s.setNodesDisponibles(possibleSolucio.get(w).getNodesDisponibles());

            for (int j = 0;j < possibleSolucio.get(w).getUsers().size();j++) {
                User u =  new User();
                s.getUsers().add(u);
            }

            for (int j = 0;j < possibleSolucio.get(w).getUsers().size();j++) {
                try {
                    s.getUsers().set(j,(User)possibleSolucio.get(w).getUsers().get(j).clone());
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }

            s.setCarrega(possibleSolucio.get(w).getCarrega());
            s.setSumaActivities(possibleSolucio.get(w).getSumaActivities());
            solution.add(s);
        }
    }

    public int serverEncontrado (List<Server>s, int idBuscat) {
        int position = 0;

        while (position < s.size()) {
            if(s.get(position).getId() == idBuscat) {
                return position;
            }
            position++;
        }

        return -1;
    }

    public double calculDiferencial (List <Server> solution) {
        double resultat = 0;
        int i = 0;

        while (i < solution.size()) {
            resultat = resultat + solution.get(i).getCarrega();
            i++;
        }

        return resultat;
    }

    public Server setInformation(Server[] servers, int idServerTrobat, User[] candidates, int userActual){
        Server s = new Server();

        s.setId(servers[idServerTrobat].getId());
        s.setCountry(servers[idServerTrobat].getCountry());
        s.setLocation(servers[idServerTrobat].getLocation());
        s.setReachable_from(servers[idServerTrobat].getReachable_from());
        s.setNodesDisponibles(servers[idServerTrobat].getNodesDisponibles());
        s.getUsers().add(candidates[userActual]);
        s.sumarCarrega(candidates[userActual]);

        return s;
    }

}

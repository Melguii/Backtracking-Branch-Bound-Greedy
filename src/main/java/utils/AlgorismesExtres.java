package utils;

import JSONClasses.ConnectsTo;
import JSONClasses.Node;
import JSONClasses.Server;
import JSONClasses.User;

import java.util.ArrayList;
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

    public Server setInformation( Server ServerTrobat, User userActual){
        Server s = new Server();

        s.setId(ServerTrobat.getId());
        s.setCountry(ServerTrobat.getCountry());
        s.setLocation(ServerTrobat.getLocation());
        s.setReachable_from(ServerTrobat.getReachable_from());
        s.setNodesDisponibles(ServerTrobat.getNodesDisponibles());
        s.getUsers().add(userActual);
        s.sumarCarrega(userActual);

        return s;
    }
    public Server whereIsFeaseble(Server[] servers, User user, List<Server> solution, User [] users){
        double best = Double.MAX_VALUE;
        int bestIdServer = 0;
        double resultat;
        Server s = new Server();

        for (int i = 0; i < servers.length; i++){
            int idServerTrobat = serverEncontrado(solution, servers[i].getId());
            if (idServerTrobat != -1){
                s = solution.get(idServerTrobat);
                s.getUsers().add(user);
                s.sumarCarrega(user);
                solution.set(idServerTrobat, s);

            } else {
                s = setInformation(servers[i], user);
                solution.add(s);
            }
            int maxim = obtindreMaximArray(solution);
            int minim = obtindreMinimArray(solution, servers.length,user == users[users.length-1]);
            resultat =  Math.pow (1.05, (maxim - minim)) * calculDiferencial(solution);

            if (resultat < best){
                best = resultat;
                bestIdServer = i;
            }
            removeInformation (solution, idServerTrobat);
        }

        s = servers[bestIdServer];

        return s;
    }
    public int addInformationSolution (Server s, List <Server> solution, User c) {
        int idServerTrobat;
        idServerTrobat = serverEncontrado(solution, s.getId());

        if (idServerTrobat != -1){
            s = solution.get(idServerTrobat);
            s.getUsers().add(c);
            s.sumarCarrega(c);
            solution.set(idServerTrobat, s);

        } else {
            s = setInformation(s, c);
            solution.add(s);

        }
        return idServerTrobat;
    }
    public void removeInformation (List <Server> solution, int idServerTrobat) {
        if (idServerTrobat == -1) {
            solution.remove(solution.size() - 1);
        } else {
            solution.get(idServerTrobat).restarCarrega(solution.get(idServerTrobat).getUsers().get(solution.get(idServerTrobat).getUsers().size() - 1));
            solution.get(idServerTrobat).getUsers().remove(solution.get(idServerTrobat).getUsers().size() - 1);
        }
    }
    public void clonarCami (List <Node> solucio, List <Node> possibleSolucio) {
        for (int w = 0; w < possibleSolucio.size();w++) {
            Node s = new Node ();
            s.setId(possibleSolucio.get(w).getId());
            s.setReliability(possibleSolucio.get(w).getReliability());
            List <ConnectsTo> connectsToAux = new ArrayList<ConnectsTo>();
            for (int j = 0; j < possibleSolucio.get(w).getConnectsTo().size(); j++) {
                ConnectsTo c = new ConnectsTo();
                connectsToAux.add(c);
            }
            for (int j = 0; j < possibleSolucio.get(w).getConnectsTo().size(); j++) {
                try {
                    connectsToAux.set(j,(ConnectsTo) possibleSolucio.get(w).getConnectsTo().get(j).clone());
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }
            s.setConnectsTo(connectsToAux);
            solucio.add(possibleSolucio.get(w));
        }
    }
}

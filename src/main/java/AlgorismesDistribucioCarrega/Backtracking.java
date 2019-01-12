package AlgorismesDistribucioCarrega;

import JSONClasses.Server;
import JSONClasses.User;
//import org.apache.commons.beanutils.BeanUtils;


import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class Backtracking {
    private int maxActivityBest;
    private int minActivityBest;
    private double maxDistance;

    public Backtracking () {
        maxActivityBest = Integer.MAX_VALUE;
        minActivityBest = 0;
        maxDistance = Double.MAX_VALUE;
    }
    public double backtringDistribucioCarrega(Server[] servers, int posicio, double best,List <Server> possibleSolucio,List <Server> solution, User[] users) {

        if (posicio == users.length) {
            int minim = obtindreMinimArray(possibleSolucio, servers.length,true);
            int maxim = obtindreMaximArray(possibleSolucio);
            double posibleBest = Math.pow (1.05,(maxim - minim)) * calculDiferencial(possibleSolucio);

            if (posibleBest < best) {
                //Borrem tots els elements afegits anteriorment, ja que ara seran substituits
                solution.clear();
                clonar(solution, possibleSolucio);
                maxActivityBest = maxim;
                minActivityBest = minim;
                maxDistance = calculDiferencial(possibleSolucio);
                best = posibleBest;
                //System.out.println("Best:" + best);
                return best;

            } else {
                return best;

            }
        }
        else {
            int y;

            for (int j = 0; j < servers.length;j++) {
                int minim = obtindreMinimArray(possibleSolucio, servers.length,false);
                int maxim =  obtindreMaximArray(possibleSolucio);

                if (!(maxim > maxActivityBest && minim < minActivityBest && calculDiferencial(possibleSolucio) > maxDistance)) {
                    y = serverEncontrado(possibleSolucio, servers[j].getId());
                    Server s;

                    if (y != -1) {
                        s = possibleSolucio.get(y);
                        s.getUsers().add(users[posicio]);
                        s.sumarCarrega(users[posicio]);
                        possibleSolucio.set(y,s);

                    } else {
                        s = new Server();
                        s.setId(servers[j].getId());
                        s.setCountry(servers[j].getCountry());
                        s.setLocation(servers[j].getLocation());
                        s.setReachable_from(servers[j].getReachable_from());
                        s.setNodesDisponibles(servers[j].getNodesDisponibles());
                        s.getUsers().add(users[posicio]);
                        s.sumarCarrega(users[posicio]);
                        possibleSolucio.add(s);
                    }

                    int seguent = posicio + 1;

                    best = backtringDistribucioCarrega(servers, seguent, best, possibleSolucio, solution, users);

                    if (y == -1) {
                        possibleSolucio.remove(possibleSolucio.size() - 1);

                    } else {
                        possibleSolucio.get(y).restarCarrega(possibleSolucio.get(y).getUsers().get(possibleSolucio.get(y).getUsers().size()-1));
                        possibleSolucio.get(y).getUsers().remove(possibleSolucio.get(y).getUsers().size()-1);

                    }
                } else {
                    return best;

                }
            }
        }
        return best;
    }
    private int serverEncontrado (List<Server> s, int idBuscat) {
        int position = 0;
        while (position < s.size()) {
            if(s.get(position).getId() == idBuscat) {
                return position;
            }
            position++;
        }
        return -1;
    }
    private double calculDiferencial (List <Server> possibleSolution) {
        double resultat = 0;
        int i = 0;
        while (i < possibleSolution.size()) {
            resultat = resultat + possibleSolution.get(i).getCarrega();
            i++;
        }
        return resultat;
    }


    private int obtindreMaximArray (List <Server> s) {
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
    private int obtindreMinimArray (List <Server> s, int numServers, boolean finalArr) {
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
        } else {
            return 0;

        }
    }
    private void clonar (List<Server> solution, List <Server> possibleSolucio) {
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

}

package Algorismes;

import JSONClasses.Server;
import JSONClasses.User;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class Backtracking {

    /**
     *
     *
     *
     * @param servers
     * @param posicio
     * @param best
     * @param possibleSolucio
     * @param solution
     * @param users
     * @return
     */
    public double backtringDistribucioCarrega(Server[] servers, int posicio, double best,List <Solution> possibleSolucio,List <Solution> solution, User[] users) {

        if (posicio == users.length) {

            solution = new ArrayList<Solution>();

            for (int i = 0; i < possibleSolucio.size();i++) {
                solution.add(possibleSolucio.get(i));
            }
            System.out.println("--------------------------");
            for (int w = 0; w < solution.size();w++) {
                System.out.println("\nNom del server:" + solution.get(w).getS().getId());
                for (int t = 0; t < solution.get(w).getUsers().size(); t++) {
                    System.out.println("User:" + solution.get(w).getUsers().get(t).getUsername());
                }
            }
            best = calculDiferencial(possibleSolucio);
            return best;
        }
        else {
            int y;
            for (int j = 0; j < servers.length;j++) {
                if(calculDiferencial (possibleSolucio) < best) {
                    y = serverEncontrado(possibleSolucio, servers[j].getId());
                    Solution s;
                    if (y != -1) {
                        s = possibleSolucio.get(y);
                        s.getUsers().add(users[posicio]);
                        s.sumarCarrega(users[posicio]);
                        possibleSolucio.set(y,s);
                    } else {
                        s = new Solution();
                        s.setS(servers[j]);
                        s.getUsers().add(users[posicio]);
                        s.sumarCarrega(users[posicio]);
                        possibleSolucio.add(s);
                    }
                    int seguent = posicio + 1;
                    best = backtringDistribucioCarrega(servers, seguent, best, possibleSolucio, solution, users);
                    if (y == -1) {
                        possibleSolucio.remove(possibleSolucio.size() - 1);
                    }
                    else {
                        possibleSolucio.get(y).restarCarrega(possibleSolucio.get(y).getUsers().get(possibleSolucio.get(y).getUsers().size()-1));
                        possibleSolucio.get(y).getUsers().remove(possibleSolucio.get(y).getUsers().size()-1);
                    }
                }
                else {
                    return best;
                }
            }
        }
        return best;
    }
    private int serverEncontrado (List<Solution>s, int idBuscat) {
        int position = 0;
        while (position < s.size()) {
            if(s.get(position).getS().getId() == idBuscat) {
                return position;
            }
            position++;
        }
        return -1;
    }
    private double calculDiferencial (List <Solution> possibleSolution) {
        double resultat = 0;
        int i = 0;
        while (i < possibleSolution.size()) {
            resultat = resultat + possibleSolution.get(i).getCarrega();
            i++;
        }
        return resultat;
    }

}

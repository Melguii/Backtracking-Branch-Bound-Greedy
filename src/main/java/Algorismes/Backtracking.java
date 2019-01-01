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

            best = calculDiferencial(possibleSolucio);
            return best;
        }
        else {
            int y;
            for (int i = posicio; i < users.length;i++) {
                for (int j = 0; j < servers.length;j++) {
                    if(calculDiferencial (possibleSolucio) < best) {
                        y = serverEncontrado(possibleSolucio,servers[j].getId());
                        Solution s;
                        if(y != -1) {
                            s = possibleSolucio.get(y);
                        }
                        else {
                            s = new Solution();
                            s.setS(servers[j]);
                        }
                        s.getUsers().add(users[i]);
                        s.sumarCarrega(users[i]);
                        possibleSolucio.add(s);
                    }
                    int seguent = posicio + 1;
                    best = backtringDistribucioCarrega(servers,seguent,best,possibleSolucio, solution, users);
                    if(y == -1) {
                        possibleSolucio.remove(possibleSolucio.size() - 1);
                    }
                    else {
                        return best;
                    }
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

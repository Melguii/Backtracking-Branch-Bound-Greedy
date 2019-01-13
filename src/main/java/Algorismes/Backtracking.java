package Algorismes;

import JSONClasses.Node;
import JSONClasses.Server;
import JSONClasses.User;
import utils.AlgorismesExtres;
//import org.apache.commons.beanutils.BeanUtils;


import java.util.List;


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
        AlgorismesExtres funcExtr = new AlgorismesExtres();
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
                    Server s;
                    y = funcExtr.addInformationSolution(servers[j],possibleSolucio,users[posicio]);

                    int seguent = posicio + 1;

                    best = backtringDistribucioCarrega(servers, seguent, best, possibleSolucio, solution, users);

                    funcExtr.removeInformation(possibleSolucio,y);
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

    public int backTrackingCamiCurt(Node [] nodes, Node nodeActual, int best, List <Node> possibleSolucio, List <Node> solution, int distanciaRecorreguda, Node fi) {
        AlgorismesExtres funcEx = new AlgorismesExtres ();
        boolean senseEscapatoria =  camiSenseEscapatoria(nodeActual, possibleSolucio);
        if (hemArribatFinal (nodeActual,fi) || (!hemArribatFinal(nodeActual,fi) && senseEscapatoria)){
            if (hemArribatFinal(nodeActual,fi)) {
                if (distanciaRecorreguda < best) {
                    best = distanciaRecorreguda;
                    solution.clear();
                    funcEx.clonarCami(solution, possibleSolucio);
                }
            }
            return best;
        }
        else {
            for (int i = 0; i < nodeActual.getConnectsTo().size(); i++) {
                if (distanciaRecorreguda < best && noHemPassatPerNode (nodeActual.getConnectsTo().get(i).getNode(), possibleSolucio)) {
                    possibleSolucio.add(nodeActual.getConnectsTo().get(i).getNode());
                    distanciaRecorreguda = distanciaRecorreguda + nodeActual.getConnectsTo().get(i).getCost();
                    best = backTrackingCamiCurt(nodes, nodeActual.getConnectsTo().get(i).getNode(), best, possibleSolucio, solution, distanciaRecorreguda,fi);
                    distanciaRecorreguda = distanciaRecorreguda - nodeActual.getConnectsTo().get(i).getCost();
                    possibleSolucio.remove(possibleSolucio.size()-1);
                }
            }
        }
        return best;

    }

    public double backTrackingFiabilitat(Node [] nodes, Node nodeActual, double best, List <Node> possibleSolucio, List <Node> solution, double fiabilitat, Node fi) {
        AlgorismesExtres funcEx = new AlgorismesExtres ();
        boolean senseEscapatoria =  camiSenseEscapatoria(nodeActual, possibleSolucio);
        if (hemArribatFinal (nodeActual,fi) || (!hemArribatFinal(nodeActual,fi) && senseEscapatoria)){
            if (hemArribatFinal(nodeActual,fi)) {
                if (fiabilitat > best) {
                    best = fiabilitat;
                    solution.clear();
                    funcEx.clonarCami(solution, possibleSolucio);
                }
            }
            return best;
        }
        else {
            for (int i = 0; i < nodeActual.getConnectsTo().size(); i++) {
                if (fiabilitat > best && noHemPassatPerNode (nodeActual.getConnectsTo().get(i).getNode(), possibleSolucio)) {
                    possibleSolucio.add(nodeActual.getConnectsTo().get(i).getNode());
                    fiabilitat = fiabilitat * nodeActual.getConnectsTo().get(i).getNode().getReliability();
                    best = backTrackingFiabilitat(nodes, nodeActual.getConnectsTo().get(i).getNode(), best, possibleSolucio, solution, fiabilitat,fi);
                    fiabilitat = fiabilitat / nodeActual.getConnectsTo().get(i).getNode().getReliability();
                    possibleSolucio.remove(possibleSolucio.size()-1);
                }
            }
        }
        return best;

    }

    public boolean hemArribatFinal (Node node, Node fi) {
        if (node.getId() == fi.getId()) {
            return true;
        }
        else {
            return false;
        }
    }
    public boolean noHemPassatPerNode (Node nodeComprovar, List <Node> cami) {
        for (int i = 0; i < cami.size(); i++) {
            if (cami.get(i).getId() == nodeComprovar.getId()) {
                return false;
            }
        }
        return true;
    }
    public boolean camiSenseEscapatoria (Node actual, List <Node> cami) {
        int num_repetits = 0;
        for (int t=0; t < cami.size(); t++) {
            for (int y =0; y < actual.getConnectsTo().size();y++) {
                if(cami.get(t).getId() == actual.getConnectsTo().get(y).getNode().getId()) {
                    num_repetits++;
                }
            }
        }
        return cami.size() == num_repetits;
    }
}

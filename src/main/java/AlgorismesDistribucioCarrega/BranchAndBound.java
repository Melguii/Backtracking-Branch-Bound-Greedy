package AlgorismesDistribucioCarrega;

import Comparators.ComparatorServerList;
import Comparators.CompareDistribution;
import JSONClasses.Server;
import JSONClasses.User;
import Sorts.QuickSort;
import utils.AlgorismesExtres;

import java.util.ArrayList;
import java.util.List;

public class BranchAndBound {
    public void branchAndBoundDistribucioCarrega(Server [] servers, User[] users) {
        double best; //Cost de la millor solucio obtinguda
        ArrayList<Server> solucioDefinitiva = new ArrayList<Server>(); //Array on guardem la distribucio de la millor solucio obtinguda
        ArrayList<Server> possibleSolucio = new ArrayList<Server>(); //Array on guardem la solucio que estava dalt de tot de livesNodes
        ArrayList<ArrayList<Server>> lives_nodes = new ArrayList<ArrayList<Server>>(); //Array on guardem totes les possibles solucions ordenades
        ArrayList <ArrayList <Server>> options = new ArrayList<ArrayList<Server>>(); //Array on guardem totes les possibles continuacions, per exemple si estem en l'usuari 1 --> El server 1,2,3,4,5...
        best = Double.MAX_VALUE;
        AlgorismesExtres ExtraAlgorithms = new AlgorismesExtres();
        Server serverPrimerUsuari = ExtraAlgorithms.whereIsFeaseble(servers, users[0], possibleSolucio, users);
        possibleSolucio.add(serverPrimerUsuari);
        encuarSolucio (lives_nodes, possibleSolucio,servers.length);
        while (lives_nodes.size() != 0) {
            possibleSolucio = desencua (lives_nodes);
            expand (ExtraAlgorithms, options, possibleSolucio, servers, users);
            for (int j = 0; j < options.size(); j++) {
                if (esSolucio (options.get(j), users.length)){
                    best = comprovacioActualitzacioBest(ExtraAlgorithms,best, options.get(j),servers.length,solucioDefinitiva);
                }
                else {
                    if (is_promising (options.get(j),best,servers.length,ExtraAlgorithms)) {
                        encuarSolucio (lives_nodes, options.get(j),servers.length);
                    }
                }
            }
            //System.out.println("Hola");
        }

    }

    private boolean esSolucio (List <Server> possibleSolucio, int numUsers) {
        int suma = 0;
        for (int i = 0; i < possibleSolucio.size(); i++) {
            suma = suma + possibleSolucio.get(i).getUsers().size();
        }
        return numUsers == suma;
    }

    private ArrayList <Server> desencua (ArrayList<ArrayList<Server>> lives_nodes) {
        AlgorismesExtres ExtraAlgorithms = new AlgorismesExtres();
        ArrayList <Server> aux = new ArrayList<Server>();
        ExtraAlgorithms.clonar (aux, lives_nodes.get(0));
        for (int u = 1; u < lives_nodes.size();u++){
            lives_nodes.set(u-1, lives_nodes.get(u));
        }
        lives_nodes.remove(lives_nodes.size()-1);
        return aux;
    }
    private void expand (AlgorismesExtres ExtraAlgorithms, ArrayList <ArrayList <Server>> options, ArrayList<Server> possibleSolucio,Server[] servers, User[] users) {
        User u;
        int idServerTrobat;
        u = buscarUsuariNoIntroduit(users,possibleSolucio);
        for (int i = 0; i < servers.length; i++) {
            idServerTrobat = ExtraAlgorithms.addInformationSolution(servers[i],possibleSolucio, u);
            options.add(possibleSolucio);
            ExtraAlgorithms.removeInformation(possibleSolucio,idServerTrobat);
        }
    }
    private double comprovacioActualitzacioBest (AlgorismesExtres ExtraAlgorithms, double best, List <Server> possibleBest, int numServers, List<Server>solucioDefinitiva) {
        int maxim = ExtraAlgorithms.obtindreMaximArray(possibleBest);
        int minim = ExtraAlgorithms.obtindreMinimArray(possibleBest, numServers,true);
        double resultat =  Math.pow (1.05, (maxim - minim)) * ExtraAlgorithms.calculDiferencial(possibleBest);
        if(resultat < best) {
            ExtraAlgorithms.clonar (solucioDefinitiva, possibleBest);
            return resultat;
        }
        else{
            return best;
        }

    }
    private boolean is_promising (List <Server> possibleSolucio, double best, int numServers, AlgorismesExtres ExtraAlgorithms) {
        int maxim = ExtraAlgorithms.obtindreMaximArray(possibleSolucio);
        int minim = ExtraAlgorithms.obtindreMinimArray(possibleSolucio,numServers ,false);
        double resultat =  Math.pow (1.05, (maxim - minim)) * ExtraAlgorithms.calculDiferencial(possibleSolucio);
        if(resultat < best) {
            return true;
        }
        else{
            return false;
        }
    }

    private User buscarUsuariNoIntroduit (User [] users, List <Server> possibleSolucio) {
        User u;
        ArrayList <User> arrayUserAux = new ArrayList<User>();
        boolean trobat;
        int i = 0;
        while (i < possibleSolucio.size()) {
            int j = 0;
            while (j < possibleSolucio.get(i).getUsers().size()) {
                arrayUserAux.add(possibleSolucio.get(i).getUsers().get(j));
                j++;
            }
            i++;
        }
        trobat = false;
        int h = 0;
        while (!trobat && h < users.length) {
            trobat =  true;
            for (int w = 0; w < arrayUserAux.size(); w++) {
                if (arrayUserAux.get(w) == users[h]) {
                    trobat = false;
                }
            }
            h++;
        }
        return users[h-1];
    }
    private void encuarSolucio (ArrayList<ArrayList<Server>> lives_nodes,ArrayList<Server> possibleSolucio, int numServers) {
        lives_nodes.add(possibleSolucio);
        QuickSort q = new QuickSort ();
        ComparatorServerList c = new CompareDistribution();
        q.quickSort(lives_nodes,c,0,lives_nodes.size() -1,numServers);

    }
}

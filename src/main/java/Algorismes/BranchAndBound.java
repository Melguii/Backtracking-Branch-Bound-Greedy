package Algorismes;

import Comparators.ComparatorServerList;
import Comparators.CompareDistribution;
import JSONClasses.Logica;
import JSONClasses.Node;
import JSONClasses.Server;
import JSONClasses.User;
import Sorts.QuickSort;
import utils.AlgorismesExtres;

import java.util.ArrayList;
import java.util.List;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Node;

public class BranchAndBound {
    public ArrayList<Server> branchAndBoundDistribucioCarrega(Server[] servers, User[] users, ArrayList<Server> solucioDefinitiva, double best) {
        ArrayList<Server> possibleSolucio = new ArrayList<Server>();                                                         //Array on guardem la solucio que estava dalt de tot de livesNodes
        ArrayList<ArrayList<Server>> lives_nodes = new ArrayList<ArrayList<Server>>();                                  //Array on guardem totes les possibles solucions ordenades
        ArrayList<ArrayList<Server>> options = new ArrayList<ArrayList<Server>>();                                                    //Array on guardem totes les possibles continuacions, per exemple si estem en l'usuari 1 --> El server 1,2,3,4,5...
        AlgorismesExtres ExtraAlgorithms = new AlgorismesExtres();

        Server serverPrimerUsuari = ExtraAlgorithms.whereIsFeaseble(servers, users[0], possibleSolucio, users);
        possibleSolucio.add(serverPrimerUsuari);
        ExtraAlgorithms.addInformationSolution(serverPrimerUsuari, possibleSolucio, users[0]);

        encuarSolucio(lives_nodes, possibleSolucio, servers.length);
        while (lives_nodes.size() != 0) {
            possibleSolucio = desencua(lives_nodes);
            expand(ExtraAlgorithms, options, possibleSolucio, servers, users);

            for (int j = 0; j < options.size(); j++) {
                if (esSolucio(options.get(j), users.length)) {
                    best = comprovacioActualitzacioBest(ExtraAlgorithms, best, options.get(j), servers.length, solucioDefinitiva);
                } else {
                    if (is_promising(options.get(j), best, servers.length, ExtraAlgorithms)) {
                        encuarSolucio(lives_nodes, options.get(j), servers.length);

                    }
                }
            }

            options.clear();
        }

        return solucioDefinitiva;
    }

    private boolean esSolucio(List<Server> possibleSolucio, int numUsers) {
        int suma = 0;
        for (int i = 0; i < possibleSolucio.size(); i++) {
            suma = suma + possibleSolucio.get(i).getUsers().size();
        }
        return numUsers == suma;
    }

    private ArrayList<Server> desencua(ArrayList<ArrayList<Server>> lives_nodes) {
        AlgorismesExtres ExtraAlgorithms = new AlgorismesExtres();
        ArrayList<Server> aux = new ArrayList<Server>();
        ExtraAlgorithms.clonar(aux, lives_nodes.get(0));
        for (int u = 1; u < lives_nodes.size(); u++) {
            lives_nodes.set(u - 1, lives_nodes.get(u));
        }
        lives_nodes.remove(lives_nodes.size() - 1);
        return aux;
    }

    private void expand(AlgorismesExtres ExtraAlgorithms, ArrayList<ArrayList<Server>> options, ArrayList<Server> possibleSolucio, Server[] servers, User[] users) {
        User u;
        int idServerTrobat;
        u = buscarUsuariNoIntroduit(users, possibleSolucio);
        for (int i = 0; i < servers.length; i++) {
            idServerTrobat = ExtraAlgorithms.addInformationSolution(servers[i], possibleSolucio, u);
            ArrayList<Server> auxiliar = new ArrayList<Server>();
            ExtraAlgorithms.clonar(auxiliar, possibleSolucio);
            options.add(auxiliar);
            ExtraAlgorithms.removeInformation(possibleSolucio, idServerTrobat);
        }
    }

    private double comprovacioActualitzacioBest(AlgorismesExtres ExtraAlgorithms, double best, List<Server> possibleBest, int numServers, List<Server> solucioDefinitiva) {
        int maxim = ExtraAlgorithms.obtindreMaximArray(possibleBest);
        int minim = ExtraAlgorithms.obtindreMinimArray(possibleBest, numServers, true);
        double resultat = Math.pow(1.05, (maxim - minim)) * ExtraAlgorithms.calculDiferencial(possibleBest);
        if (resultat < best) {
            solucioDefinitiva.clear();
            ExtraAlgorithms.clonar(solucioDefinitiva, possibleBest);
            return resultat;
        } else {
            return best;
        }

    }

    private boolean is_promising(List<Server> possibleSolucio, double best, int numServers, AlgorismesExtres ExtraAlgorithms) {
        int maxim = ExtraAlgorithms.obtindreMaximArray(possibleSolucio);
        int minim = ExtraAlgorithms.obtindreMinimArray(possibleSolucio, numServers, false);
        double resultat = Math.pow(1.05, (maxim - minim)) * ExtraAlgorithms.calculDiferencial(possibleSolucio);
        if (resultat < best) {
            return true;
        } else {
            return false;
        }
    }

    private User buscarUsuariNoIntroduit(User[] users, List<Server> possibleSolucio) {
        User u;
        ArrayList<User> arrayUserAux = new ArrayList<User>();
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
            trobat = true;
            for (int w = 0; w < arrayUserAux.size(); w++) {
                if (arrayUserAux.get(w).getUsername().equals(users[h].getUsername())) {
                    trobat = false;
                }
            }
            h++;
        }
        return users[h - 1];
    }

    private void encuarSolucio(ArrayList<ArrayList<Server>> lives_nodes, ArrayList<Server> possibleSolucio, int numServers) {
        lives_nodes.add(possibleSolucio);
        QuickSort q = new QuickSort();
        AlgorismesExtres ExtraAlgorithms = new AlgorismesExtres();
        ComparatorServerList c = new CompareDistribution();
        q.quickSort(lives_nodes, c, 0, lives_nodes.size() - 1, numServers);

    }
public List <Node> branchAndBoundBusquedaCamiCurt (List <Node> nodes_principals, List <Node> nodes_finals, List <Node> best, int millor_solucio) {
    ArrayList <Node> x =  new ArrayList< Node >(); //Node actual
    ArrayList<ArrayList<Node>> opcions = new ArrayList<ArrayList<Node>>();
    ArrayList<ArrayList<Node>> lives_nodes = new ArrayList<ArrayList<Node>>();

    int i = 0;

    while(i<nodes_principals.size()) {
        lives_nodes.add(new ArrayList<Node>());
        lives_nodes.get(i).add(nodes_principals.get(i));
        i++;
    }

    while(lives_nodes.size() !=0) {

        x = desencuaCamiMinim(lives_nodes);
        opcions = expandirCamiMinim(x);

        for (int y = 0; y < opcions.size(); y++) {
            if (esSolucioCamiMinim(opcions.get(y),nodes_finals)) {
                int resultat =  sumaCostos(opcions.get(y));
                if (resultat < millor_solucio){
                    best.clear();
                    for(int k=0; k < opcions.get(y).size();k++){
                        best.add(opcions.get(y).get(k));
                    }
                    millor_solucio = resultat;
                }

            } else {
                encua (lives_nodes, opcions.get(y), millor_solucio);
            }


        }
    }
    return best;

    }

    public List <Node> branchAndBoundBusquedaCamiFiable (List <Node> nodes_principals, List <Node> nodes_finals, List<Node> best, double millor_solucio) {
        ArrayList <Node> x =  new ArrayList< Node >(); //Node actual
        ArrayList<ArrayList<Node>> opcions = new ArrayList<ArrayList<Node>>();
        ArrayList<ArrayList<Node>> lives_nodes = new ArrayList<ArrayList<Node>>();
        int i = 0;

        while(i<nodes_principals.size()) {
            lives_nodes.add(new ArrayList<Node>());
            lives_nodes.get(i).add(nodes_principals.get(i));
            i++;
        }

        while(lives_nodes.size() !=0) {

            x = desencuaCamiMinim(lives_nodes);
            opcions = expandirCamiMinim(x);

            for (int y = 0; y < opcions.size(); y++) {
                if (esSolucioCamiMinim(opcions.get(y),nodes_finals)) {
                    double resultat =  calculFiabilitat(opcions.get(y));
                    if (resultat > millor_solucio){
                        best.clear();
                        for(int k=0; k < opcions.get(y).size();k++){
                            best.add(opcions.get(y).get(k));
                        }
                        millor_solucio = resultat;
                    }

                } else {
                    encuaFiables (lives_nodes, opcions.get(y), millor_solucio);
                }


            }
        }
        return best;

    }

    public void encua (ArrayList <ArrayList <Node>> lives_nodes, ArrayList <Node>opcio,int best) {
        if (sumaCostos(opcio) < best) {
            lives_nodes.add(opcio);
        }
    }

    public void encuaFiables (ArrayList <ArrayList <Node>> lives_nodes, ArrayList <Node>opcio,double best) {
        if (calculFiabilitat(opcio) > best) {
            lives_nodes.add(opcio);
        }
    }
    public double calculFiabilitat (List <Node> possibleSolucio) {
        double resultat = 1;
        int j = 0;
        while (j < possibleSolucio.size()) {
            resultat = resultat * possibleSolucio.get(j).getReliability();
            j++;
        }
        return resultat;
    }
    private boolean esSolucioCamiMinim (ArrayList <Node> opcio,List<Node> array_posFinal) {
        int i = 0;
        while (i < array_posFinal.size()) {
            if (opcio.get(opcio.size()-1) ==  array_posFinal.get(i)) {
                return true;
            }
            i++;
        }
        return false;
    }
    private  int  sumaCostos (ArrayList <Node> opcio) {
        int cost = 0;
        boolean trobat = false;
        for (int i = 0; i < opcio.size()-1; i++) {
            trobat =  false;
            int j = 0;
            while (!trobat) {
                if (opcio.get(i).getConnectsTo().get(j).getTo() == opcio.get(i+1).getId()) {
                    trobat = true;
                }
                j++;
            }
            cost = cost + opcio.get(i).getConnectsTo().get(j-1).getCost();
        }
        return cost;
    }
    private ArrayList <Node> desencuaCamiMinim (ArrayList <ArrayList <Node>> lives_nodes) {
        ArrayList <Node> auxiliar = new ArrayList<JSONClasses.Node>();
        for (int r = 0; r < lives_nodes.get(0).size();r++) {
                try {
                    auxiliar.add((Node) lives_nodes.get(0).get(r).clone());
                } catch (CloneNotSupportedException e1) {
                    e1.printStackTrace();
                }
        }

        for (int p= 0;p < (lives_nodes.size()-1);p++) {
            lives_nodes.set(p, lives_nodes.get(p+1));
        }
        lives_nodes.remove(lives_nodes.size()-1);
        return auxiliar;
    }
    public ArrayList <ArrayList <Node>> expandirCamiMinim (ArrayList<Node> opcioActual){
        ArrayList <ArrayList <Node>> auxiliar = new ArrayList<ArrayList<JSONClasses.Node>>();
        AlgorismesExtres algExtr = new AlgorismesExtres();
        int comptador_valids =0;
        for (int h= 0; h < opcioActual.get(opcioActual.size()-1).getConnectsTo().size();h++) {
            if (algExtr.nodeEncontrado(opcioActual, opcioActual.get(opcioActual.size() - 1).getConnectsTo().get(h).getTo()) == -1 ){
                auxiliar.add(new ArrayList<JSONClasses.Node>());
                for (int u = 0; u < opcioActual.size(); u++) {
                    try {
                        auxiliar.get(comptador_valids).add((Node) opcioActual.get(u).clone());
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                }
                auxiliar.get(comptador_valids).add(opcioActual.get(opcioActual.size() - 1).getConnectsTo().get(h).getNode());
                comptador_valids++;
            }
        }
        return auxiliar;
    }
}

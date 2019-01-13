package JSONClasses;

import AlgorismesDistribucioCarrega.Backtracking;
import AlgorismesDistribucioCarrega.BranchAndBound;
import AlgorismesDistribucioCarrega.Greedy;
import Comparators.ComparatorUser;
import Comparators.ComparatorUserCharge;
import Sorts.QuickSortUsers;
import com.google.gson.Gson;

import Comparators.Comparator;
import Comparators.CompareID;
import Sorts.MergeSort;
import utils.AlgorismesExtres;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Logica {
    private User [] users;
    private Node [] nodes;
    private Server [] servers;
    private List<Post> posts;
    private ArrayList <Server> solution =  new ArrayList <Server>();

    //Constructor de Logica (la classe actua)
    public Logica () {
        posts = new ArrayList<Post>();
    }

    /**
     * Metode que s'ocupa de realizar un proces o un altre segons la opcio introduida per l'usuari en el menu de fitxers
     * @param opcioFitxer Opcio introduida per l'usuari en el menu fitxers
     */
    public void execucioMenuFitxer (int opcioFitxer) {
        //El nostres fitxers json sempre estaran a datasets/
        String aux = "datasets/";
        switch (opcioFitxer) {
            case 1:
                //Llegim els fitxers per defecte
                lecturaFitxers(aux + "users.json", aux + "nodes_plus.json", aux + "servers_plus.json");
                break;
            case 2:
                //Demanem a l'usuari les diferents opcions i anem llegint els fitxers, en cas d'error ja se li ho tornarà a demanar a l'usuari el nom del fitxer
                System.out.println("Introdueix-me el nom del fitxer que conté els noms dels usuaris:");
                Scanner sc = new Scanner(System.in);
                System.out.println("Introdueix-me el nom del fitxer que conté els noms dels nodes:");
                Scanner sc2 = new Scanner(System.in);
                System.out.println("Introdueix-me el nom del fitxer que conté els noms dels servers:");
                Scanner sc3 = new Scanner(System.in);
                lecturaFitxers(aux + sc.nextLine(), aux + sc2.nextLine(), aux + sc3.nextLine());
                break;
            default:
                System.out.println("Error, estas intentant accedir a una opcio que no existeix");
                break;
        }
    }

    /**
     *
     *
     * @param opcio
     */
    public List <Server> execucioMenuModeCarrega(int opcio) {
        solution.clear();
        Backtracking b = new Backtracking();
        List <Server> possibleSolucio = new ArrayList<Server>();
        BranchAndBound boo = new BranchAndBound();
        ArrayList<Server> solucioDefinitiva = new ArrayList<Server>(); //Array on guardem la distribucio de la millor solucio obtinguda
        double best; //Cost de la millor solucio obtinguda
        int maxim = 0;
        int minim = 0;
        best = Double.MAX_VALUE;
        Greedy g = new Greedy();
        AlgorismesExtres ExtraAlgorithms = new AlgorismesExtres();

        switch (opcio) {
            case 1:
                b.backtringDistribucioCarrega(servers,0, Double.MAX_VALUE, possibleSolucio, solution, users);
                break;

            case 2:
                QuickSortUsers qUsers = new QuickSortUsers();
                ComparatorUser c = new ComparatorUserCharge();
                qUsers.quickSort(users,c,0,users.length-1);
                solution = boo.branchAndBoundDistribucioCarrega(servers,users,solucioDefinitiva,best);
                break;

            case 3:
                solution = g.greedyDistribucioCarrega(servers, users, 999999999);
                break;

            case 4:
                solution = g.greedyDistribucioCarrega(servers, users, 999999999);

                maxim = ExtraAlgorithms.obtindreMaximArray(solution);
                minim = ExtraAlgorithms.obtindreMinimArray(solution, servers.length ,true);
                best =  Math.pow (1.05, (maxim - minim)) * ExtraAlgorithms.calculDiferencial(solution);

                b.backtringDistribucioCarrega(servers,0, best, possibleSolucio, solution, users);
                break;

            case 5:
                solution = g.greedyDistribucioCarrega(servers, users, 999999999);

                maxim = ExtraAlgorithms.obtindreMaximArray(solution);
                minim = ExtraAlgorithms.obtindreMinimArray(solution, servers.length ,true);
                best =  Math.pow (1.05, (maxim - minim)) * ExtraAlgorithms.calculDiferencial(solution);

                solution = boo.branchAndBoundDistribucioCarrega(servers, users, solution, best);
                break;

            default:
                System.out.println("Error opcio introduida no valida");
        }

        for (int w = 0; w < solution.size(); w++) {
            System.out.println("\nNom del server:" + solution.get(w).getId());

            for (int t = 0; t < solution.get(w).getUsers().size(); t++) {
                System.out.println("User:" + solution.get(w).getUsers().get(t).getUsername());
            }
        }
        System.out.println("\n");
        return solution;
    }

    public void execucioMenuModeDisponibilitat(int opcio, User userEmisor, User userReceptor, List <Server> distribucions){
        List <Node> solutio = new ArrayList<Node>();
        switch (opcio) {
            case 1:
                Backtracking b =  new Backtracking();
                double best = Double.MAX_VALUE;
                List <Node> possibleSolucio = new ArrayList<Node>();
                List <Node> nodes_start  = busquedaUser (userEmisor,distribucions);
                List <Node> nodes_end = busquedaUser (userReceptor,distribucions);
                for (int i = 0; i < nodes_start.size(); i++) {
                    for(int j = 0; j <nodes_end.size(); j++) {
                        possibleSolucio.add (nodes_start.get(i));
                        best = b.backTrackingCamiCurt(nodes, nodes_start.get(i), best, possibleSolucio, solutio, 0, nodes_end.get(j));
                        possibleSolucio.clear();
                    }
                }

                break;

            case 2:

                break;

            case 3:

                break;

            case 4:
                break;
            case 5:
                break;
            default:
                System.out.println("Error opcio introduida no valida");
        }
        System.out.println("\n");
        for (int w = 0; w < solutio.size(); w++) {
            System.out.print(solutio.get(w).getId());
            if (w != (solutio.size() -1)) {
                System.out.print("-->");
            }
        }
        System.out.println("\n");
    }

    public User cercaUser(String userName){
        for (int i = 0; i < users.length; i++){
            if (userName.equals(users[i].getUsername())){
                return users[i];
            }
        }

        System.out.println("Error, usuari no trobat");
        return null;
    }

    /**
     * S'ocupa de llegir tots els fitxers que introdueix l'usuari o es fiquen per defecte, a la mateixa vegada que comprova la seva existencia i va emplenant els atributs de la classe
     * @param nomFitxerUsers Nom que te el fitxer que conte els usuaris
     * @param nomFitxerNodes Nom que te el fitxer que conte els nodes
     * @param nomFitxerServers Nom que te el fitxer que conte els servers
     */
    private void lecturaFitxers(String nomFitxerUsers, String nomFitxerNodes, String nomFitxerServers) {
        FileReader fitxerUsers;
        FileReader fitxerNodes;
        FileReader fitxerServers;
        //Comprovem l'existencia dels diferents fitxers (users, nodes, servers)
        fitxerUsers = seleccioFitxer(nomFitxerUsers, "Users");
        fitxerNodes = seleccioFitxer(nomFitxerNodes, "Nodes");
        fitxerServers = seleccioFitxer(nomFitxerServers, "Servers");
        //Ara ens dediquem a anar llegint tots el fitxers
        Gson gson = new Gson();
        users = gson.fromJson(fitxerUsers, User[].class);
        nodes = gson.fromJson(fitxerNodes, Node[].class);
        servers = gson.fromJson(fitxerServers, Server[].class);
        /*JsonParser parser = new JsonParser();
        JsonArray jA = (JsonArray) parser.parse(fitxerServers);
        for (int  i = 0; i < jA.size(); i++) {
            JsonObject jO = jA.get(i).getAsJsonObject();
            Server s = new Server();
            s.setCountry(jO.get("country").getAsString());
            s.setId(jO.get("id").getAsInt());
            List<Double> arrayints = new ArrayList<Double>();
            arrayints.add (jO.get("location").getAsJsonArray().get(0).getAsDouble());
            arrayints.add (jO.get("location").getAsJsonArray().get(1).getAsDouble());
            s.setLocation(arrayints);
            if(jO.get("reachable_from").)
        }*/
        int  i = 0;
        while (i < users.length) {
            obtindrePosts(users[i]);
            users[i].referenciarSeguidors(users);
            users[i].calcularLocalitzacioUsuari();
            i++;
        }
        Comparator c = new CompareID();
        MergeSort mergesort = new MergeSort ();
        mergesort.mergeSort(nodes,c,0, nodes.length-1);
        for (int j = 0; j < servers.length; j++) {
            servers[j].referenciarNodes(nodes);
        }
        for (int j = 0; j < nodes.length; j++) {
            nodes[j].referenciarConnexions(nodes);
        }

        //Un cop fet tot aquest proces tindrem tota la informació necessaria en els atributs de la classe
    }

    /**
     * Control del nom de fitxer introduit( mirem si es correcte o si no existeix, i per tant hem de mostrar un error)
     * també establim que tots els fitxers estaran a la carpeta datasets
     * @param nom_fitxer Nom del fitxer que ha introduit l'usuari
     * @return Retorna una variable tipus FileReader per tal que el fitxer pugui ser llegit/interpretat
     */
    private FileReader seleccioFitxer(String nom_fitxer, String tipus) {
        FileReader fitxer = null;
        do {
            try {
                fitxer = new FileReader(nom_fitxer);
            } catch (FileNotFoundException e) {
                System.out.println("Error fitxer que conté els "+ tipus +" no trobat (ha d'estar a la carpeta datasets),no ens petaras \nel programa tan facilment, fem PAED (⌐■_■)");
                System.out.println("La nostra generositat no coneix limits, trona'm a introduir nom del fitxer :):");
                Scanner sc = new Scanner(System.in);
                nom_fitxer = "datasets/" + sc.nextLine();
            }
        } while (fitxer == null);
        return fitxer;
    }

    /**
     *
     *
     *
     * @param user
     */
    private void obtindrePosts(User user) {
        for (int j = 0; j < user.getPosts().size(); j++) {
            posts.add(user.getPosts().get(j));
        }
    }
    /**
     * quickSort que ens servira per ordenar el array users per fer la busqueda binaria
     * @param p Array de users que volem ordenar
     * @param i Principi del array
     * @param j Final del array
     * @return Retornem el array introduit totalment ordenat
     */
    public User [] quickSort (User [] p, int i, int j) {
        int s;
        int t;
        int array_aux_ij [] = new int [2];
        int array_aux_st [] = new int [2];

        if (i >= j) {
            return p;
        }
        else{
            array_aux_ij[0] = i;
            array_aux_ij[1] = j;
            array_aux_st = particio(p,array_aux_ij);
            s = array_aux_st[0];
            t = array_aux_st[1];
            p = quickSort(p,i,t);
            p = quickSort(p,s,j);
        }
        return p;
    }

    /**
     * Metode que ens serveix per tal de poder realitzar correctament el quickSort
     * @param p Array de users que volem ordenar
     * @param array_aux_ij La i i la j en forma de array per tal  que el seu valor
     *                     canvii tambe en el  metode quicksort
     * @return Els nous valors de s i t
     */
    private int [] particio (User [] p, int array_aux_ij[]) {
        int mig;
        User pivot;
        User tmp = new User();
        int s;
        int t;
        s = array_aux_ij[0];
        t = array_aux_ij[1];
        mig = (array_aux_ij[0] + array_aux_ij[1])/2;
        pivot = p[mig];
        while (s <= t) {
            while (pivot.getUsername().compareTo(p[s].getUsername()) > 0) {
                s = s + 1;
            }
            while(pivot.getUsername().compareTo(p[t].getUsername()) < 0) {
                t = t - 1;
            }
            if (s < t) {
                tmp = p[s];
                p[s] = p[t];
                p[t] = tmp;
                s = s + 1;
                t = t - 1;
            }
            else {
                if (s == t) {
                    s = s + 1;
                    t = t - 1;
                }
            }
        }
        int [] array_aux_st = new int[2];
        array_aux_st[0]= s;
        array_aux_st[1]=t;

        return array_aux_st;
    }
    public List<Node> busquedaUser (User user, List<Server> distribucions) {
        int j = 0;
        int w;
        boolean trobat = false;
        while (j < distribucions.size() && !trobat) {
            w = 0;
            while (w < distribucions.get(j).getUsers().size() && !trobat) {
                if (user.getUsername().equals(distribucions.get(j).getUsers().get(w).getUsername())) {
                    trobat = true;
                }
                w++;
            }
            j++;
        }
        List <Node> llistaNodes =  new ArrayList <Node> ();
        for (int u = 0; u < distribucions.get(j-1).getNodesDisponibles().size(); u++) {
            llistaNodes.add(distribucions.get(j-1).getNodesDisponibles().get(u));
        }
        return llistaNodes;
    }
}

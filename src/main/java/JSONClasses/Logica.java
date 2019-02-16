package JSONClasses;

import Algorismes.Backtracking;
import Algorismes.BranchAndBound;
import Algorismes.Greedy;
import Comparators.ComparatorUser;
import Comparators.ComparatorUserCharge;
import Sorts.QuickSortUsers;
import com.google.gson.Gson;

import Comparators.Comparator;
import Comparators.CompareID;
import Sorts.MergeSort;
import utils.AlgorismesExtres;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Logica {
    //Atributs de la classe
    private User []             users;
    private Node []             nodes;
    private Server []           servers;
    private List<Post>          posts;
    private ArrayList <Server>  solution             =  new ArrayList <Server>();
    private ArrayList <ArrayList <Node>>  solutionCamiMinim    =  new ArrayList <ArrayList <Node>>();
    private ArrayList <ArrayList <Node>>  solutionCamiFiable   =  new ArrayList <ArrayList <Node>>();

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
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                System.out.println("Introdueix-me el nom del fitxer que conté els noms dels nodes:");
                BufferedReader br2 = new BufferedReader(new InputStreamReader(System.in));
                System.out.println("Introdueix-me el nom del fitxer que conté els noms dels servers:");
                BufferedReader br3 = new BufferedReader(new InputStreamReader(System.in));
                try {
                    lecturaFitxers(aux + br.readLine(), aux + br2.readLine(), aux + br3.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            default:
                System.out.println("Error, estas intentant accedir a una opcio que no existeix");
                break;
        }
    }

    /**
     * Aquest metode s'ocupa de preguntar a l'usuari de quina manera prefereix realizar la distribució de carrega
     * @param opcio Es la opcio seleccionada per l'usuari, és a dir, si ha seleccionat backtracking, branch and bound...
     * @return Retorna el resultat de la distribució realitzada
     */
    public List <Server> execucioMenuModeCarrega(int opcio) {
        //Netegem la solucio anterior
        solution.clear();
        //Desfinim el backtracking, l'array on construirem la possible solucio i el branch and bound
        Backtracking b = new Backtracking();
        List <Server> possibleSolucio = new ArrayList<Server>();
        BranchAndBound boo = new BranchAndBound();
        ArrayList<Server> solucioDefinitiva = new ArrayList<Server>(); //Array on guardem la distribucio de la millor solucio obtinguda
        double best; //Cost de la millor solucio obtinguda
        //Definim el minim i el maxim al 0
        long startTime;
        long endTime;
        long tempsTotal;
        int maxim = 0;
        int minim = 0;
        //El best sempre sera el major possible. D'aquesta manera, ens assegurem que sempre hi haurà una solució millor
        best = Double.MAX_VALUE;
        //Inicialitzem també Greedy
        Greedy g = new Greedy();

        //Aquesta classe que importem ens aportarà tots els algorismes, que son importants per a moltes classes i que no formen
        //part d'una classe en concret
        AlgorismesExtres ExtraAlgorithms = new AlgorismesExtres();


        switch (opcio) {
            case 1:
                startTime = System.currentTimeMillis();
                //Cridem a la funció que s'ocupa de realitzar la distribució de carrega dels servers amb solament backtracking
                b.backtringDistribucioCarrega(servers,0, Double.MAX_VALUE, possibleSolucio, solution, users);
                endTime = System.currentTimeMillis();
                break;

            case 2:
                //Fem un quicksort per tal de facilitar el branch and bound, d'aquesta manera, tindrem tots els usuaris ordenats per la seva carrega
                //cosa que, segons la nostra opinió, serà més eficient.
                startTime = System.currentTimeMillis();
                QuickSortUsers qUsers = new QuickSortUsers();
                ComparatorUser c = new ComparatorUserCharge();
                qUsers.quickSort(users,c,0,users.length-1);
                //Cridem al metode que s'ocupa de realitzar el Branch and Bound
                solution = boo.branchAndBoundDistribucioCarrega(servers,users,solucioDefinitiva,best);
                endTime = System.currentTimeMillis();
                break;

            case 3:
                //En aquest cas, deixem introduir una tolerancia al usuari per a que estableixi un limit al Greedy, pero
                //en cas que no pugui trobar la solucio adecuada a l'usuari avisara mitjançant un error.
                System.out.println("Intorudeix-me la maxima diferencia d'activitat entre els servers:");
                int tolerancia;
                Scanner sc3 = new Scanner(System.in);
                tolerancia = sc3.nextInt();
                //Cridem a la funció del Greedy
                startTime = System.currentTimeMillis();
                solution = g.greedyDistribucioCarrega(servers, users, tolerancia);
                endTime = System.currentTimeMillis();
                break;

            case 4:
                //Cridem a la funció de greedy, sense introduir-li una tolerancia
                startTime = System.currentTimeMillis();
                solution = g.greedyDistribucioCarrega(servers, users, Integer.MAX_VALUE);

                //Calculem mitjaçant la fomrula el millor cas quin best tenia
                maxim = ExtraAlgorithms.obtindreMaximArray(solution);
                minim = ExtraAlgorithms.obtindreMinimArray(solution, servers.length ,true);
                best =  Math.pow (1.05, (maxim - minim)) * ExtraAlgorithms.calculDiferencial(solution);

                //Cridem a la funcio de backtracking passant-li com a parametre el best calculat mitjançant el Greedy
                b.backtringDistribucioCarrega(servers,0, best, possibleSolucio, solution, users);
                endTime = System.currentTimeMillis();
                break;

            case 5:
                //Cridem a la funció de greedy, sense introduir-li una tolerancia
                startTime = System.currentTimeMillis();
                solution = g.greedyDistribucioCarrega(servers, users, Integer.MAX_VALUE);

                //Calculem mitjaçant la fomrula el millor cas quin best tenia
                maxim = ExtraAlgorithms.obtindreMaximArray(solution);
                minim = ExtraAlgorithms.obtindreMinimArray(solution, servers.length ,true);
                best =  Math.pow (1.05, (maxim - minim)) * ExtraAlgorithms.calculDiferencial(solution);

                //Cridem a la funcio de branch and bound que s'ocupa de la distribucio de carrega passant-li el valor de best calculat anteriorment
                solution = boo.branchAndBoundDistribucioCarrega(servers, users, solution, best);
                endTime = System.currentTimeMillis();
                break;

            default:
                startTime = 0;
                endTime = 0;
                System.out.println("Error opcio introduida no valida");
        }

        for (int w = 0; w < solution.size(); w++) {
            //Mostrem al usuari el resultat obtingut en un format especific
            System.out.println("\nNom del server:" + solution.get(w).getId());

            for (int t = 0; t < solution.get(w).getUsers().size(); t++) {
                System.out.println("User:" + solution.get(w).getUsers().get(t).getUsername());
            }
        }
        System.out.println("\n");
        tempsTotal = endTime -startTime;
        System.out.println("HE TARDAT: " + tempsTotal + " ms");
        //Retornem la solució obtinguda
        return solution;
    }

    /**
     * S'encarrega de trobar un cami fiable i el cami més curt amb els metodes explicats anteriorment (branch and bound, backtracking)
     * @param opcio Opcio seleccionada per l'usuari (selecciona si vol Branch and Bound, Backtracking...
     * @param userEmisor Usuari del que volem començar
     * @param userReceptor Usuari amb el que volem connectar
     * @param distribucions Distribucions obtingudes mitjançant el metode anterior
     */
    public void execucioMenuModeDisponibilitat(int opcio, User userEmisor, User userReceptor, List <Server> distribucions){
        long startTimeCurt;
        long endTimeCurt;
        long tempsTotalCurt=0;
        long startTimeFia;
        long endTimeFia;
        long tempsTotalFia=0;
        long auxiliar;
        //Array on guardarem el cami més curt
        List <Node> solutio = new ArrayList<Node>();
        //Carreguem una classe mitjançant la qual podrem adquirir metodes interessants
        AlgorismesExtres ExtraAlgorithms = new AlgorismesExtres();
        //Creem una classe de Greedy, per poder utilitzar-lo posteriorment
        Greedy g = new Greedy();
        //Obtenim els servers del usuaris que es volen conectar (Receptor i emissor)
        Server serverEmisor = ExtraAlgorithms.getServerUsuari(solution, userEmisor.getUsername() );;
        Server serverReceptor = ExtraAlgorithms.getServerUsuari(solution, userReceptor.getUsername());;
        //Establim que la millor solucio del Greedy sera sempre la mes alta, d'aquesta manera, ens assegurem que tothom la podra superar
        int millor = Integer.MAX_VALUE;
        //La solució més fiable començarà en 0, perquè així sempre podrà ser superar(la pitjor fiabilitat possible és 0)
        double millorFiable = 0;

        int pathNumber = 0;

        //Inicialitzem la classe Backtracking i Branch and Bound, per poder utilitzar tots els seus mètodes
        Backtracking b =  new Backtracking();
        BranchAndBound bb =  new BranchAndBound();

        //Establim la millor solucio dels altres metodes que son siguin Greedy
        int best = Integer.MAX_VALUE;
        //Establim la millor solucio en quan a fiabilitat dels altres metodes que no siguin greedy
        double bestFiabilitat = Double.MAX_VALUE;
        //Definim els arrays on guardarem les solucions/possibles solucions
        List <Node> possibleSolucio = new ArrayList<Node>();
        List <Node> solutioFiabilitat = new ArrayList <Node>();
        //Definim un array on guardarem tots els nodes dels que pot començar l'usuari emissor
        List <Node> nodes_start  = busquedaUser (userEmisor,distribucions);
        //Definim un array on guardarem tots els nodes en els que pot acabar l'usuari recpetor
        List <Node> nodes_end = busquedaUser (userReceptor,distribucions);

        //Fem el Greddy des de les diferents parts d'on pot començar un usuari, per obtindre el cami més curt
        startTimeCurt = System.currentTimeMillis();
        while ( pathNumber < serverEmisor.getNodesDisponibles().size()){
            ArrayList <Node> resolucio = new ArrayList<Node>();
            int possibleBest = g.greedyCamiMinim(serverEmisor, serverReceptor, nodes, serverEmisor.getNodesDisponibles().get(pathNumber), resolucio);
            //Mirem quina de les tres alternatives per començar és millor
            if (possibleBest < millor) {
                solutio.clear();
                //Establim la nova solucio
                for(int s = 0; s < resolucio.size(); s++) {
                    solutio.add(resolucio.get(s));
                }
                millor =  possibleBest;
            }
            //solutionCamiFiable.add(g.greedyCamiFiable(solution, serverEmisor, serverReceptor, nodes));
            pathNumber++;
        }
        endTimeCurt = System.currentTimeMillis();
        pathNumber = 0;
        //Fem el Greedy des de les diferent parts d'on pot començar l'usuari
        startTimeFia = System.currentTimeMillis();
        while ( pathNumber < serverEmisor.getNodesDisponibles().size()){
            ArrayList <Node> resolucioFiabilitat = new ArrayList<Node>();
            double possibleBestFiable = g.greedyFiabilitat(serverEmisor, serverReceptor, nodes, serverEmisor.getNodesDisponibles().get(pathNumber), resolucioFiabilitat);
            //Mirem quina de les tres alternatives per començar és millor
            if (possibleBestFiable > millorFiable) {
                //Borrem la solucio anterior, i establim la nova
                solutioFiabilitat.clear();
                for(int s = 0; s < resolucioFiabilitat.size(); s++) {
                    solutioFiabilitat.add(resolucioFiabilitat.get(s));
                }
                millorFiable =  possibleBestFiable;
            }
            //solutionCamiFiable.add(g.greedyCamiFiable(solution, serverEmisor, serverReceptor, nodes));
            pathNumber++;
        }
        endTimeFia = System.currentTimeMillis();
        switch (opcio) {
            case 1:
                //Fem el Backtracking pero per les diferents opcions que hem establit (que comenci i acabi en diferents nodes)
                startTimeCurt = System.currentTimeMillis();
                for (int i = 0; i < nodes_start.size(); i++) {
                    for(int j = 0; j <nodes_end.size(); j++) {
                        possibleSolucio.add (nodes_start.get(i));
                        //Anem fent backtrackings
                        best = b.backTrackingCamiCurt(nodes, nodes_start.get(i), best, possibleSolucio, solutio, 0, nodes_end.get(j));
                        possibleSolucio.clear();
                    }
                }
                endTimeCurt = System.currentTimeMillis();
                //Establim la pitjor fiabilitat possibilitat possible, per tal que tothom la pugui superar
                bestFiabilitat = 0.0;
                //Fem el mateix, backtracking establint diferents nodes de començament i final
                startTimeFia = System.currentTimeMillis();
                for (int i = 0; i < nodes_start.size(); i++) {
                    for(int j = 0; j <nodes_end.size(); j++) {
                        possibleSolucio.add (nodes_start.get(i));
                        bestFiabilitat = b.backTrackingFiabilitat(nodes, nodes_start.get(i), bestFiabilitat, possibleSolucio, solutioFiabilitat, 1, nodes_end.get(j));
                        possibleSolucio.clear();
                    }
                }
                endTimeFia = System.currentTimeMillis();
                break;

            case 2:
                //En aquest cas no cal fer 2 fors, ja que ja es tindra en compte a l'hora de crear les diferents possibilitats
                startTimeCurt = System.currentTimeMillis();
                solutio = bb.branchAndBoundBusquedaCamiCurt (nodes_start, nodes_end, solutio, Integer.MAX_VALUE);
                endTimeCurt = System.currentTimeMillis();
                startTimeFia = System.currentTimeMillis();
                solutioFiabilitat = bb.branchAndBoundBusquedaCamiFiable(nodes_start, nodes_end, solutioFiabilitat, 0);
                endTimeFia = System.currentTimeMillis();
                break;

            case 3:
                //El greedy com que ja l'hem calculat abans, simplement mostrem errors, si no ens ha estat possible trobar cap solucio
                if (solutioFiabilitat.size() == 0) {
                    System.out.println("En aquest cas concret, el Greedy no pot donar una solucio de cami mes fiable valida, degut a que s'arriba a un punt on s'ha d'anar a nodes ja recorreguts i per tant es queda encallat");
                }
                if (solutio.size() == 0) {
                    System.out.println("En aquest cas concret, el Greedy no pot donar una solucio de cami mes curt valida, degut a que s'arriba a un punt on s'ha d'anar a nodes ja recorreguts i per tant es queda encallat");
                }

                break;

            case 4:

                millor = Integer.MAX_VALUE;
                b =  new Backtracking();
                best = millor;
                possibleSolucio = new ArrayList<Node>();
                //Establim els nodes des d'on podem començar
                nodes_start  = busquedaUser (userEmisor,distribucions);
                nodes_end = busquedaUser (userReceptor,distribucions);

                //Fem els mateixos calculs que hem fet anteriorment, amb l'unica diferencia que ara li passarem al backtracking la solucio
                //obtinguda per Greedy, per tal de fer una poda molt millor
                tempsTotalCurt = - (System.currentTimeMillis() - startTimeFia);
                auxiliar = System.currentTimeMillis();
                for (int i = 0; i < nodes_start.size(); i++) {
                    for(int j = 0; j < nodes_end.size(); j++) {
                        possibleSolucio.add (nodes_start.get(i));
                        best = b.backTrackingCamiCurt(nodes, nodes_start.get(i), best, possibleSolucio, solutio, 0, nodes_end.get(j));
                        possibleSolucio.clear();
                    }
                }
                endTimeCurt = System.currentTimeMillis();
                for (int i = 0; i < nodes_start.size(); i++) {
                    for(int j = 0; j <nodes_end.size(); j++) {
                        possibleSolucio.add (nodes_start.get(i));
                        millorFiable = b.backTrackingFiabilitat(nodes, nodes_start.get(i), millorFiable, possibleSolucio, solutioFiabilitat, 1, nodes_end.get(j));
                        possibleSolucio.clear();
                    }
                }
                endTimeFia = System.currentTimeMillis();
                tempsTotalFia = - (endTimeCurt - auxiliar);
                break;
            case 5:
                //Fem iogual que abans amb el Branch and Bound, amb l'unica diferencia que li ho passem la solucio per Greedy
                auxiliar = System.currentTimeMillis();
                tempsTotalCurt =  -(System.currentTimeMillis() - endTimeFia);
                solutio = bb.branchAndBoundBusquedaCamiCurt (nodes_start, nodes_end, solutio, millor);
                endTimeCurt = System.currentTimeMillis();
                solutioFiabilitat = bb.branchAndBoundBusquedaCamiFiable(nodes_start, nodes_end, solutioFiabilitat, millorFiable);
                endTimeFia = System.currentTimeMillis();
                tempsTotalFia = -(endTimeCurt - auxiliar);
                break;
            default:
                startTimeCurt = 0;
                startTimeFia = 0;
                endTimeCurt = 0;
                endTimeFia = 0;
                System.out.println("Error opcio introduida no valida");
        }
        //Mostrem la solucio obtinguda per algun dels metodes establerts anteriorment
        System.out.println("\nCami mes curt:");
        for (int w = 0; w < solutio.size(); w++) {
            System.out.print(solutio.get(w).getId());
            if (w != (solutio.size() -1)) {
                System.out.print("-->");
            }
        }
        System.out.println("\nCami mes fiable");
        for (int w = 0; w < solutioFiabilitat.size(); w++) {
            System.out.print(solutioFiabilitat.get(w).getId());
            if (w != (solutioFiabilitat.size() -1)) {
                System.out.print("-->");
            }
        }
        tempsTotalCurt = (endTimeCurt - startTimeCurt) + tempsTotalCurt;
        tempsTotalFia = (endTimeFia - startTimeFia) + tempsTotalFia;
        if ((solutio.size() != 0) && (solutioFiabilitat.size() != 0)) {
            System.out.println("\nHE TARDAT " + tempsTotalCurt + "ms en calcular el cami curt");
            System.out.println("\nHE TARDAT " + tempsTotalFia + "ms en calcular el cami mes fiable");
        }
        /*
        for (int w = 0; w < solutionCamiFiable.size(); w++) {
            System.out.println("\nNom del server:" + solutionCamiMinim.get(w).getId());
        }*/
        System.out.println("\n");
    }

    /**
     * Comprovem si un usuari concret esta en l'array de users que tenim
     * @param userName Li ho passem el usurname del usuari que volem comprovar
     * @return Retornem si existeix o no aquest usuari, sino retornem null
     */
    public User cercaUser(String userName){
        for (int i = 0; i < users.length; i++){
            if (userName.equals(users[i].getUsername())){
                return users[i];
            }
        }

        System.out.println("Error, usuari no trobat");
        User u = new User();
        u.setUsername("");
        return u;
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
        int  i = 0;
        //Anem referenciant els seguidors de tots els usuaris que tenim, sabem que aquest no
        //es un pas necessari, pero ho fem per a que aquest codi pugui tenir més utilitats en un futur
        while (i < users.length) {
            obtindrePosts(users[i]);
            users[i].referenciarSeguidors(users);
            users[i].calcularLocalitzacioUsuari();
            i++;
        }
        Comparator c = new CompareID();
        MergeSort mergesort = new MergeSort ();
        mergesort.mergeSort(nodes,c,0, nodes.length-1);
        //Referenciem nodes i server, per tal de  no quedar-nos només amb un id, sinó que puguessim
        //accedir directament a la informació
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
     * Metode que serveix per anar afegint tots els posts que tenim a un array de posts
     * @param user User del que volem obtindre els posts
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

    /**
     * Metode que ens serveix per buscar un usuari entre tots els servers que tenim i retornar tots els nodes dels quals pot començar
     * @param user Usuari del que volem buscar on esta posicionat
     * @param distribucions Array de servers on busquem el nostre usuari
     * @return Nodes des de els quals pot començar l'usuari
     */
    public List<Node> busquedaUser (User user, List<Server> distribucions) {
        int j = 0;
        int w;
        boolean trobat = false;
        //Anem buscant a l'usuari fins que arribem al final de les distribucions o el trobem
        while (j < distribucions.size() && !trobat) {
            w = 0;
            //Dins de les distribucions anem mirant tots els usuaris, a no ser que el trobem
            while (w < distribucions.get(j).getUsers().size() && !trobat) {
                if (user.getUsername().equals(distribucions.get(j).getUsers().get(w).getUsername())) {
                    trobat = true;
                }
                w++;
            }
            j++;
        }
        //Un cip trobat el server de l'usuari, simplement retornem tots els nodes des dels quals podem sortir
        List <Node> llistaNodes =  new ArrayList <Node> ();
        for (int u = 0; u < distribucions.get(j-1).getNodesDisponibles().size(); u++) {
            llistaNodes.add(distribucions.get(j-1).getNodesDisponibles().get(u));
        }
        return llistaNodes;
    }
}

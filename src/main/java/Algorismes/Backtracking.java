package Algorismes;

import JSONClasses.Node;
import JSONClasses.Server;
import JSONClasses.User;
import utils.AlgorismesExtres;
//import org.apache.commons.beanutils.BeanUtils;


import java.util.List;


public class Backtracking {

    //Atributs de la classe Backtracking
    private int maxActivityBest; //Indica quina es la maxima activitat que te un server
    private int minActivityBest; //Indica quina es la minima activitat que te un server
    private double maxDistance; //Indica quina és la suma de distancies entre els diferents servers

    /**
     * Constructor de la classe, simplement s'ocupa d'inicialitzar els diferents atributs que te disponible la classe
     */
    public Backtracking () {
        maxActivityBest = Integer.MAX_VALUE;
        minActivityBest = 0;
        maxDistance = Double.MAX_VALUE;
    }

    /**
     * Backtracking que s'ocupa de realitzar la distribucio de la carrega, és a dir, de col·locar tots els usuaris obtinguts en els diferents servers
     * @param servers Array de servers on tenim la possibilitat de col·locar els usuaris
     * @param posicio Posicio en la qual estem actualment, en referencia als usuaris
     * @param best Indica quin es el valor (al fer els calculs de la formula) del millor cas, per tal de poder comparar
     * @param possibleSolucio Es la solucio que estem construint
     * @param solution Es on guardem la millor solucio fins al moment
     * @param users Array on tenim col·locats tots els usuaris que hem obtingut en el JSON
     * @return Retorna el valor (segons la formula) de la solucio
     */
    public double backtringDistribucioCarrega(Server[] servers, int posicio, double best,List <Server> possibleSolucio,List <Server> solution, User[] users) {
        AlgorismesExtres funcExtr = new AlgorismesExtres();
        //Comprovem si hem arribat al final de l'array de usuaris o si per contra encara que queden usuaris que col·locar
        if (posicio == users.length) {
            //Obtenim la maxima i minima carrega dels diferents servers que tenim
            int minim = obtindreMinimArray(possibleSolucio, servers.length,true);
            int maxim = obtindreMaximArray(possibleSolucio);
            //Calculem la formula de la possible solucio
            double posibleBest = Math.pow (1.05,(maxim - minim)) * calculDiferencial(possibleSolucio);
            //Comparem el valor obtingut amb el valor actual del millor cas
            if (posibleBest < best) {
                //Borrem tots els elements afegits anteriorment, ja que ara seran substituits
                solution.clear();
                //Clonem la solucio, perque al passar-se per referencia els objectes en JAVA, es borraria en la seguent iteracio la solucio
                clonar(solution, possibleSolucio);
                //Guardem els maxims i els minims
                maxActivityBest = maxim;
                minActivityBest = minim;
                maxDistance = calculDiferencial(possibleSolucio);
                best = posibleBest;
                //System.out.println("Best:" + best);
                return best;

            } else {
                //Si no es solucio retornem el best i seguim en altres casos
                return best;


            }
        }
        else {
            int y;
            //Anem provant que succeix si anem introduint un usuari en cadascun dels servers que tenim disponibles
            for (int j = 0; j < servers.length;j++) {
                //Veiem com esta la possible solucio que estem construint
                int minim = obtindreMinimArray(possibleSolucio, servers.length,false);
                int maxim =  obtindreMaximArray(possibleSolucio);

                //Mirem si podem descartar alguna solucio, en cas que es superi el maxim, el minim i es superi el calcul del diferencial
                if (!(maxim > maxActivityBest && minim < minActivityBest && calculDiferencial(possibleSolucio) > maxDistance)) {
                    Server s;
                    //Provem d'afegir l'usuari en el server concret en el que estem
                    y = funcExtr.addInformationSolution(servers[j],possibleSolucio,users[posicio]);

                    int seguent = posicio + 1;
                    //Tornem a cridar al backtracking i continuem iterant
                    best = backtringDistribucioCarrega(servers, seguent, best, possibleSolucio, solution, users);
                    //Borrem l'usuari del server emn el que l'haviem col·locat i provem de col·locar-lo en un altre, per veure que passa
                    funcExtr.removeInformation(possibleSolucio,y);
                } else {
                    return best;
                }
            }
        }
        return best;
    }

    /**
     * Metode que busca en quina posicio de l'array de servers es troba l'id d'un server en concret
     * @param s Llista on tenim tots els servers
     * @param idBuscat id del server que estem buscant la seva posicio
     * @return Retornem la posicio del server que busquem
     */
    private int serverEncontrado (List<Server> s, int idBuscat) {
        int position = 0;
        while (position < s.size()) {
            if(s.get(position).getId() == idBuscat) {
                return position;
            }
            position++;
        }
        //En cas de no trobar res, retornem un -1, per indicar que no s'ha trobat res
        return -1;
    }

    /**
     * Metode que retorna la suma de totes les carregues de la solucio que estem/hem estatconstruit
     * @param possibleSolution Solucio que hem estat/estem construint
     * @return Retornem la suma de totes les carregues dels diferents severs
     */
    private double calculDiferencial (List <Server> possibleSolution) {
        double resultat = 0;
        int i = 0;
        while (i < possibleSolution.size()) {
            resultat = resultat + possibleSolution.get(i).getCarrega();
            i++;
        }
        return resultat;
    }

    /**
     * Metode que serveix per obtindre la maxima carrega, de tots els servers que tenim disponibles
     * @param s LLista de servers del quals volem calcular un maxim
     * @return Retornem la carrega del server amb més c arrega
     */
    private int obtindreMaximArray (List <Server> s) {
        int maxim = 0;
        int i = 0;
        //Anem recorrent tots els servers, mirant si la seva carrega es superior a la del maxim actual
        while (i < s.size()) {
            if (s.get(i).getSumaActivities() > maxim) {
                //Si es superior, simplement, actualitzem maxim
                maxim = s.get(i).getSumaActivities();
            }
            i++;
        }
        return maxim;
    }

    /**
     * Metode que serveix per obntindre la minima carrega de tots els servers que tenim disponibles
     * @param s Llista de servers dels quals volem obtindre el minim
     * @param numServers Indica el numero de servers que tenim
     * @param finalArr Indica si estem calculant el minim d'una possible solucio o si ja estem calculant el minim d'una solucio, ja definitva
     * @return Retornem el minim de l'array de servers que ens passen per parametre (encara que hi ha un cas en que retornem 0)
     */
    private int obtindreMinimArray (List <Server> s, int numServers, boolean finalArr) {
        int minim = Integer.MAX_VALUE;
        int i = 0;
        //En cas que estiguessim al final del array i haguessim utilitzat tots els servers
        //calcularem el minim, sino es innecesari(ja que algun server tindra un valor 0)
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

    /**
     * Metode que serveix per clonar tota la informacio de la solcuio que hem estat construint fins al moment
     * @param solution Solucio actual, a la qual volem copiar tota la informacio
     * @param possibleSolucio Solucio que hem construit, i que com supera a la solucio actual, la subtituira(i per tant s'ha de clonar)
     */
    private void clonar (List<Server> solution, List <Server> possibleSolucio) {
        //Anem clonant server a server i passant-lo al nostre array de solucio
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

            //Anem afegint els servers a la solucio
            solution.add(s);
        }
    }

    /**
     * Backtracking que s'ocupa de trobar el cami més curt
     * @param nodes Array de nodes que conté tots els nodes
     * @param nodeActual Node que estem actualment
     * @param best Variable que guarda la informació del millor camí que hem trobat
     * @param possibleSolucio Solucio que estem construint
     * @param solution Solucio actual, la que actualment conté el camí més curt trobat
     * @param distanciaRecorreguda Inlcou la distancia que hem recorregut en la possibleSolucio actual
     * @param fi Node en el que volem acabar
     * @return Retornem el valor de la distancia recorreguda en el cami construit
     */
    public int backTrackingCamiCurt(Node [] nodes, Node nodeActual, int best, List <Node> possibleSolucio, List <Node> solution, int distanciaRecorreguda, Node fi) {
        AlgorismesExtres funcEx = new AlgorismesExtres ();
        //Comprovem si el cami que estem construint te escopatoria o si, per contra, no.
        boolean senseEscapatoria =  camiSenseEscapatoria(nodeActual, possibleSolucio);
        //Comprovem si hem arribat al final del cami o si no hem arribat al final i no hi ha escapatoria
        if (hemArribatFinal (nodeActual,fi) || (!hemArribatFinal(nodeActual,fi) && senseEscapatoria)){
            if (hemArribatFinal(nodeActual,fi)) {
                if (distanciaRecorreguda < best) {
                    best = distanciaRecorreguda;
                    solution.clear();
                    funcEx.clonarCami(solution, possibleSolucio);
                }
            }
            //Si no hi havia escapatoria directament retornarem best, en cas que haguem arribat al final també, però abans recorrerem l'if
            return best;
        }
        else {
            //Anem canviant de node continuament, d'aquesta manera, recorrem els diferents camins que tenim disponibles
            for (int i = 0; i < nodeActual.getConnectsTo().size(); i++) {
                //Comprovem que no haguem passat per aquell node en concret i que el cami que estiguem construit encara sigui prometedor
                if (distanciaRecorreguda < best && noHemPassatPerNode (nodeActual.getConnectsTo().get(i).getNode(), possibleSolucio)) {
                    //Afegim el seguent node a la solucio que estem construint
                    possibleSolucio.add(nodeActual.getConnectsTo().get(i).getNode());
                    //Afegim el cost que te viatjar al seguent node
                    distanciaRecorreguda = distanciaRecorreguda + nodeActual.getConnectsTo().get(i).getCost();
                    //Tornem a cridar al backtracking per continuar creant cami/o indicar que ja som solucio o que no tenim escapatoria
                    best = backTrackingCamiCurt(nodes, nodeActual.getConnectsTo().get(i).getNode(), best, possibleSolucio, solution, distanciaRecorreguda,fi);
                    //Un cop treballat amb aquest node en concret, borrem tota la informacio afegida, i provem amb altres nodes
                    distanciaRecorreguda = distanciaRecorreguda - nodeActual.getConnectsTo().get(i).getCost();
                    possibleSolucio.remove(possibleSolucio.size()-1);
                }
            }
        }
        return best;

    }

    /**
     * Backtracking que s'ocupa de calcular el cami més fiable
     * @param nodes Array que conté tots els nodes que tenim disponibles
     * @param nodeActual Node que estem actualment
     * @param best Millor fiabilitat trobada fins al moment
     * @param possibleSolucio Solucio que estem/hem estat construint fins al moment
     * @param solution Array de nodes que conte el cami mes fiable fins al moment
     * @param fiabilitat Fiabilitat del cami que estem construint actualment
     * @param fi Indica el node on volem acabar
     * @return Retornem la fiabilitat del cami mes fiable
     */
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

    /**
     * Metode que ens indica si hem arribat al ultim Node o, per contra, encara ens queden nodes per recorrer
     * @param node Node que estem actualment
     * @param fi El que ha de ser l'ultim node del cami
     * @return Retorna true si hem arribat al final o false no hem arribat
     */
    public boolean hemArribatFinal (Node node, Node fi) {
        if (node.getId() == fi.getId()) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Metode que serveix per indicar-nos si hem passat per un node concret o si, per contra, no
     * @param nodeComprovar Node que volem comprobar si esta en el cami
     * @param cami Cami que hem estat construint fins al moment
     * @return Ens indica si hem passat per aquell node concret (true)
     */
    public boolean noHemPassatPerNode (Node nodeComprovar, List <Node> cami) {
        //Anem recorrent tots els nodes pels que hem anat passant
        for (int i = 0; i < cami.size(); i++) {
            if (cami.get(i).getId() == nodeComprovar.getId()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Comprovem si el cami que estem construint encara té opcions d'arribar al final
     * @param actual Node actual en el que estem
     * @param cami Cami que hem estat construint fins al moment
     * @return Si el cami que hem construint no te escapatoria
     */
    public boolean camiSenseEscapatoria (Node actual, List <Node> cami) {
        int num_repetits = 0;
        //Anem recorrent tots dels nodes del cami
        for (int t=0; t < cami.size(); t++) {
            //I mirem si a tots els camins que podem anar hem passat ja
            for (int y =0; y < actual.getConnectsTo().size();y++) {
                if(cami.get(t).getId() == actual.getConnectsTo().get(y).getNode().getId()) {
                    num_repetits++;
                }
            }
        }
        return cami.size() == num_repetits;
    }
}

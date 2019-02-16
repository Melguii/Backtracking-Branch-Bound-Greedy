package utils;

import JSONClasses.Node;
import JSONClasses.ConnectsTo;
import JSONClasses.Node;
import JSONClasses.Server;
import JSONClasses.User;

import java.util.ArrayList;
import java.util.List;

public class AlgorismesExtres {

    /**
     * Metode que ens permet obtindre el server amb més activitats de tots els que tenim
     * @param s Llista que conté tots els servers que tenim
     * @return Retornem l'activitat maxima de l'array
     */
    public int obtindreMaximArray (List<Server> s) {
        int maxim = 0;
        int i = 0;
        //Anem recorrent tots els servers
        while (i < s.size()) {
            //Mirem si el server supera el maxim actual
            if (s.get(i).getSumaActivities() > maxim) {
                maxim = s.get(i).getSumaActivities();
            }
            i++;
        }
        return maxim;
    }


    /**
     * Metode que ens permet obtindre el server amb menys activitat de tots els que tenim
     * @param s Llista que conté tots els servers que tenim
     * @param numServers Numero de servers que tenim
     * @param finalArr Indica si estem intentant obtindre el minim d'un array que no està complet encara(osigui
     *                 que encara falten usuaris per introduir
     * @return Retornem la quantitat d'activitat que té el server amb menys activitat
     */
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

    /**
     * Metode que serveix per clonar la solució a introduir, per tal que no es borri posteriorment
     * @param solution Solucio que abans era la que buscavem, ara ha estat superada
     * @param possibleSolucio Solucio que hem copnstruit i supera, en algun aspecte a l'anterior
     */
    public void clonar (List<Server> solution, List <Server> possibleSolucio) {
        //Anem recorrent server a server totes les posicions de l'array de servers
        for (int w = 0; w < possibleSolucio.size();w++) {
            //Anem clonant part a part cada atribut de la classse Server
            Server s = new Server();
            s.setId(possibleSolucio.get(w).getId());
            s.setCountry(possibleSolucio.get(w).getCountry());
            s.setLocation(possibleSolucio.get(w).getLocation());
            s.setReachable_from(possibleSolucio.get(w).getReachable_from());
            s.setNodesDisponibles(possibleSolucio.get(w).getNodesDisponibles());

            //Borrem tots els usuaris anteriors
            for (int j = 0;j < possibleSolucio.get(w).getUsers().size();j++) {
                User u =  new User();
                s.getUsers().add(u);
            }

            for (int j = 0;j < possibleSolucio.get(w).getUsers().size();j++) {
                try {
                    //Clonem tots els usuaris que tenim i els anem introduint
                    s.getUsers().set(j,(User)possibleSolucio.get(w).getUsers().get(j).clone());
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }
            s.setCarrega(possibleSolucio.get(w).getCarrega());
            s.setSumaActivities(possibleSolucio.get(w).getSumaActivities());
            //Afegim el server clonat a la solucio i aixi succesivament fins arribar al final
            solution.add(s);
        }
    }

    /**
     * Metode que s'ocupa de buscar si un id de un server existeix
     * @param s Llista que emmagatzema tots els servers que tenim disponibles
     * @param idBuscat Id de Server que estem cercant
     * @return Retorna la posicio de l'array de servers on hem trobat el id especificat
     */
    public int serverEncontrado (List<Server> s, int idBuscat) {
        int position = 0;

        while (position < s.size()) {
            if(s.get(position).getId() == idBuscat) {
                return position;
            }
            position++;
        }

        return -1;
    }

    /**
     * Metode que s'ocupa de realitzar el calcul de la suma de carregues
     * @param solution Solucio que hem estat/s'esta construint
     * @return Retorna el valor de la suma de carregues de la solucio
     */
    public double calculDiferencial (List <Server> solution) {
        double resultat = 0;
        int i = 0;

        while (i < solution.size()) {
            resultat = resultat + solution.get(i).getCarrega();
            i++;
        }

        return resultat;
    }

    /**
     * Metode que s'ocupa de afegir un usuari en un serve concret
     * @param ServerTrobat Server al qual volem afegir  la informacio
     * @param userActual Usuari que volem afegir al server
     * @return El server actualitzat
     */
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

    /**
     * Metode que s'encarrega de decidir quin es la millor opcio per col·locar l'usuari
     * @param servers Array de servers on guardem la informacio
     * @param user Usuari que volem obtenir quina es la millor opció
     * @param solution Solucio que estem construint
     * @param users Array que c onté tots els usuaris obtinguts
     * @return Retornem el server que considerem que és el que millor s'dapata l'usuari
     */
    public Server whereIsFeaseble(Server[] servers, User user, List<Server> solution, User [] users){
        double best = Double.MAX_VALUE;
        int bestIdServer = 0;
        double resultat;
        Server s = new Server();

        //Recorrem tots els servers
        for (int i = 0; i < servers.length; i++){
            int idServerTrobat = serverEncontrado(solution, servers[i].getId());
            //Mirem si el server que estem mirant esta en la solucio
            if (idServerTrobat != -1){
                //Afegim l'usuari en la solucio que estem construint
                s = solution.get(idServerTrobat);
                s.getUsers().add(user);
                s.sumarCarrega(user);
                solution.set(idServerTrobat, s);

            } else {
                s = setInformation(servers[i], user);
                solution.add(s);
            }
            //Obtenim els resultats dels servers un cop introduit l'usuari
            int maxim = obtindreMaximArray(solution);
            int minim = obtindreMinimArray(solution, servers.length,user == users[users.length-1]);
            resultat =  Math.pow (1.05, (maxim - minim)) * calculDiferencial(solution);

            //Mirem si la distribució construida és millor
            if (resultat < best){
                best = resultat;
                bestIdServer = i;
            }
            removeInformation (solution, idServerTrobat);
        }

        s = servers[bestIdServer];

        return s;
    }

    /**
     * Metode per trobar quin és el node al qual hem d'anar per obtindre el cami més fiable
     * @param n Node que estem actualment
     * @param path Cami que hem estat construint fins al moment
     * @param fiabilitat Fiabilitat fins al moment del cami que hem estat construit fins ara
     * @return Retorna quin és el Node cap al cap d'anar
     */
    public Node whichCostIsFeaseble(Node n, ArrayList<Node> path, double [] fiabilitat){
        int costId;
        double possibleCost;
        double bestCost = 0;
        Node nodeSolucio = new Node();
        //Mirem quin és el node més fiable per anar
        for (int i = 0; i < n.getConnectsTo().size(); i++){
            possibleCost = n.getConnectsTo().get(i).getNode().getReliability();
            costId = n.getConnectsTo().get(i).getTo();
            //Anem provant amb els diferents nodes(sempre guardant) el que millor adaptació ha tingut (NO AGAFEM MAI REPETITS)
            if (possibleCost > bestCost && (nodeEncontrado(path, costId) == -1)){
                bestCost = possibleCost;
                nodeSolucio = n.getConnectsTo().get(i).getNode();
                fiabilitat[0] = fiabilitat[0] * possibleCost;
            }
        }
        fiabilitat[0] = bestCost;
        return  nodeSolucio;
    }

    /**
     * Metode que ens indica quin és el cami que té menys cost, durant la seva construccio
     * @param n Node que estem actualment
     * @param path Cami que estem construint
     * @param cost Cost total que portem acumulant en el cami
     * @return Retornem quin és el Node que assegura en un moment concret el cami amb menys cost
     */
    public Node whichCostIsFeasebleEnMinim(Node n, ArrayList<Node> path, int [] cost){
        int costId;
        int possibleCost;
        int bestCost = Integer.MAX_VALUE;
        Node nodeSolucio = new Node();
        //Mirem quines son les diferents opcions que tenim si comencem des del Node Actual.
        for (int i = 0; i < n.getConnectsTo().size(); i++){
            possibleCost = n.getConnectsTo().get(i).getCost();
            costId = n.getConnectsTo().get(i).getTo();
            //Anem comparant quin és el millor node per seguir
            if (possibleCost < bestCost && (nodeEncontrado(path, costId) == -1)){
                bestCost = possibleCost;
                nodeSolucio = n.getConnectsTo().get(i).getNode();
                cost[0] = possibleCost + cost[0];
            }
        }

        return  nodeSolucio;
    }

    /**
     * Metode que ens permet actualitzar un Server a un array de solucions
     * @param s Server que volem actualitzar
     * @param solution Solucio que estem construint
     * @param c User que volem afegir
     * @return Retornem l'ID del server que hem trobat, si és que estava a la solucio, sino, si s'ha afegit un de nou
     *         perquè no s'ha pogut afegir abans, el que fem és retornar un -1
     */
    public int addInformationSolution (Server s, List <Server> solution, User c) {
        int idServerTrobat;
        idServerTrobat = serverEncontrado(solution, s.getId());
        //Si es el server que buisquem esta en la solucio, afegim l'usuari facilment
        if (idServerTrobat != -1){
            s = solution.get(idServerTrobat);
            s.getUsers().add(c);
            s.sumarCarrega(c);
            solution.set(idServerTrobat, s);

        } else {
            //Si no hi és, simplement establir la informació del nou server,juntament amb l'usuari a introduir, i afegir el server a la solucio
            s = setInformation(s, c);
            solution.add(s);
        }
        return idServerTrobat;
    }

    /**
     * Borrem la informació del usuari que s'ha afegit anteriorment
     * @param solution Array d'on volem borrar la informacio introduida amb anterioritat
     * @param idServerTrobat Posicio en la que hem trobat el server anteiorment
     */
    public void removeInformation (List <Server> solution, int idServerTrobat) {
        //Si abans simplement hem afegit el server, el que hem de fer és borarr simplement l'ultim server introduit
        if (idServerTrobat == -1) {
            solution.remove(solution.size() - 1);
        } else {
            //Si no el que fem és anar a la posició on hem afegit l'ultim usuari, i el borrem
            solution.get(idServerTrobat).restarCarrega(solution.get(idServerTrobat).getUsers().get(solution.get(idServerTrobat).getUsers().size() - 1));
            solution.get(idServerTrobat).getUsers().remove(solution.get(idServerTrobat).getUsers().size() - 1);
        }
    }

    /**
     * Metode que serveix per definir si estem en un node amb el qual podem arribar al server desitjat
     * @param node Node que estem actualment
     * @param serverBuscat Server que busquem (final del cami)
     * @return Retornem true si podem accedir al server des del node que estem actualment
     */
    public boolean serverTrobat(Node node, Server serverBuscat){
        //Anem recorrent tots els nodes des dels que es pot accedir al server i comparnt-los amb el node actual
        for (int i = 0; i < serverBuscat.getNodesDisponibles().size(); i++){
            if (node.getId() == serverBuscat.getNodesDisponibles().get(i).getId()){
                return true;
            }
        }

        return false;
    }

    /**
     * Busquem si un id concret dins de la nostra llista de nodes(dins del nostre cami)
     * @param n Llista de nodes(cami construit fins al moment)
     * @param idBuscat ID del Node que busquem
     * @return Retornem la posicio on l'hem trobat si el trobem, o si no el trobem retornem un -1
     */
    public int nodeEncontrado(List<Node> n, int idBuscat) {
        int position = 0;

        //Anem de la posició 0 fins la mida total de la llista de nodes
        while (position < n.size()) {
            if (n.get(position).getId() == idBuscat) {
                return position;
            }
            position++;
        }

        return -1;
    }

    /**
     * Metode que ens serveix per buscar un usuari concret dins de la nostra llista de servers (veure on esta col·locat un usuari en concret)
     * @param servers Llista de servers que tenim (on buscarem l'usuari)
     * @param nameUser Nom del usuari que volem buscar dins del nostre array de servers
     * @return Retornem el server on està col·locat l'usuari
     */
    public Server getServerUsuari(List <Server> servers, String nameUser){
        //Anem recorrent server a server
        for (int i = 0; i < servers.size(); i++){
            //Dins de cada server mirem tots els usuaris que estan col·locats alli dins
            for (int j = 0; j < servers.get(i).getUsers().size(); j++){
                //Mirem si l'usuari que estem consultant és el mateix que el que comparem
                if (nameUser.equals(servers.get(i).getUsers().get(j).getUsername())){
                    //Quan ho sigui retornem el server on esta col·locat
                    return servers.get(i);
                }
            }
        }

        return null;
    }

    /**
     * Metode que serveix per clonar un cami concret, per tal de no perdre la solucio
     * @param solucio Solucio que fins ara era la millor opció
     * @param possibleSolucio La solució que hem obtingut que passarà a ser la millor i que per tant substituirà a l'anterior
     */
    public void clonarCami (List <Node> solucio, List <Node> possibleSolucio) {
        //Anem recorrent node a node i copiant toda la seva informació
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

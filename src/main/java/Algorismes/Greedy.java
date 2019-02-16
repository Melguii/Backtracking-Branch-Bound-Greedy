package Algorismes;

//import static java.sql.JDBCType.NULL;


import JSONClasses.Node;
import JSONClasses.Server;
import JSONClasses.User;
import utils.AlgorismesExtres;

import java.util.ArrayList;
import java.util.List;

public class Greedy {
    /**
     * Metode on apliquem el Greedy de la distribucio de carrega
     * @param servers Array de servers (on guardem la informacio de tots els servers que tenim)
     * @param candidates Array de usuaris que ens indica tots els usuaris que hem de col·locar en els diferents servers
     * @param tolerancia Tolerancia que ens passen indicant la maxima diferencia entre carregues que tindrem
     * @return Retornem la distribucio de la solucio obtinguda
     */
    public ArrayList <Server> greedyDistribucioCarrega(Server[] servers, User[] candidates, int tolerancia){
        int idServerTrobat;

        ArrayList <Server> solution = new ArrayList<Server>();
        //Classe on tenim diferents metodes que ens poden ser utils per a fer el Greedy
        AlgorismesExtres funcEx = new AlgorismesExtres();
        Server s = new Server();

        //Repartim els usuaris
        for (int i = 0; i < (candidates.length ); i++){
            //Recollim tota la informació de l'usuari actual
            User c = candidates[i];

            // Mirem a quin és el server més factible per aquest usuari i l'equitativitat
            s = funcEx.whereIsFeaseble(servers, c, solution,candidates);
            idServerTrobat = funcEx.addInformationSolution (s, solution, c);
        }

        if (is_Solution(servers, tolerancia, solution, funcEx)){
            //En cas de no obtindre el resultat desitjat per la tolerancia, simplement, mostrem error i no retornem cap solucio
            System.out.println("Error! La tolerancia introduida no és suficient per a que els servidors aguantin " +
                    "a tots els usuaris");
            System.out.println("La proxima vegada intenta introduir una tolerancia més gran");

            //Fem això per a que es retorni null
            ArrayList <Server> array_aux = new ArrayList<Server>();
            return array_aux;
        }

        return solution;
    }

    /**
     * Metode Greedy que s'ocupa de calcular el cami minim
     * @param serverInicial Server del que comencem el cami
     * @param serverBuscat Server en el que volem acabar
     * @param nodes Nodes que tenim disponibles
     * @param nodeInicial Es el node dell que tenim intencio de començar
     * @param solution Solucio obtinguda per Greedy (la retornarem ja que en Java els valors es retornen per referencia)
     * @return Retornem el cost en distancia del cami definit
     */
    public int greedyCamiMinim(Server serverInicial, Server serverBuscat, Node[] nodes, Node nodeInicial, ArrayList <Node> solution){
        List <Node> nodesActuals = serverInicial.getNodesDisponibles();
        AlgorismesExtres funcEx = new AlgorismesExtres();

        Node n = nodeInicial;
        //Afegim el node inicial
        solution.add(n);
        int comptador = 0;
        int cost = 0;

        int aux_cost [] = new int [1];
        aux_cost[0] = 0;
        /*Mentres que el node que estem mirant no estigui directament connectat a el servidor que busquem, seguim fent camí*/
        while (!funcEx.serverTrobat(n, serverBuscat)){

            /* Mirem a quina és la distancia mínima cap al següent node i que el següent node ja estigui a la solució */
            n = funcEx.whichCostIsFeasebleEnMinim(n, solution,aux_cost);

            solution.add(n);
            /*Comprovem que no estiguem encallats sense sortida*/
            comptador = 0;
            for (int i = 0; i < n.getConnectsTo().size(); i++){
                if (funcEx.nodeEncontrado(solution, n.getConnectsTo().get(i).getTo()) != -1){
                    comptador++;
                }
            }

            if (comptador == n.getConnectsTo().size()){
                //Netegem la solucio pèr deixar-la en NULL i aixi indicar que no s'ha pogut obtindre una solucio optima
                solution.clear();
                //Li assignem també el maxim cost possible, també com a indicatiu
                aux_cost[0] = Integer.MAX_VALUE;
                break;
            }
        }
        return aux_cost [0];
    }


    /**
     * Greedy que s'ocupa d'obtindre el cami més fiable
     * @param serverInicial Server del que volem començar
     * @param serverBuscat Server en el que volem acabar
     * @param nodes Array que conté tots els nodes possibles
     * @param nodeInicial Node del que començarem
     * @param solution Solucio que conté o no el cami més fiable
     * @return Fiabilitat del cami més fiable
     */
    public double greedyFiabilitat(Server serverInicial, Server serverBuscat, Node[] nodes, Node nodeInicial, ArrayList <Node> solution){
        List <Node> nodesActuals = serverInicial.getNodesDisponibles();
        AlgorismesExtres funcEx = new AlgorismesExtres();


        Node n = nodeInicial;
        //Afegim el node inicial
        solution.add(n);
        int comptador = 0;
        double aux_fiabilitat [] = new double[1];
        aux_fiabilitat[0] = 1.0;
        //Mentre que el node que estem mirant no estigui directament connectat al servidor que busquem, seguim fent camí
        while (!funcEx.serverTrobat(n, serverBuscat)){

            //Mirem a quina és la distancia mínima cap al següent node i que el següent node ja estigui a la solució
            n = funcEx.whichCostIsFeaseble(n, solution,aux_fiabilitat);

            solution.add(n);
            /*Comprovem que no estiguem encallats sense sortida*/
            comptador = 0;
            for (int i = 0; i < n.getConnectsTo().size(); i++){
                if (funcEx.nodeEncontrado(solution, n.getConnectsTo().get(i).getTo()) != -1){
                    comptador++;
                }
            }

            if (comptador == n.getConnectsTo().size()){
                solution.clear();
                aux_fiabilitat[0] = 0;
                break;
            }
        }
        return aux_fiabilitat [0];
    }
    /*
    public ArrayList <Node> greedyCamiFiable(ArrayList<Server> servers, Server serverIncial, Server serverBuscat, Node[] nodes){
        int idServerTrobat;

        ArrayList <Server> solution = new ArrayList<Server>();
        AlgorismesExtres funcEx = new AlgorismesExtres();
        Server s = new Server();

        //Repartim els usuaris
        for (int i = 0; i < (candidates.length ); i++){
            /* Recollim tota la informació de l'usuari actual
            User c = candidates[i];

            /* Mirem a quin és el server més factible per aquest usuari i l'equitativitat
            s = funcEx.whereIsFeaseble(servers, c, solution,candidates);
            idServerTrobat = funcEx.addInformationSolution (s, solution, c);
        }

        if (is_Solution(servers, tolerancia, solution, funcEx)){
            System.out.println("Error! La tolerancia introduida no és suficient per a que els servidors aguantin " +
                    "a tots els usuaris");
            System.out.println("La proxima vegada intenta introduir una tolerancia més gran");

            return null;
        }

        return solution;
    }
*/

    /**
     * Metodo que se ocupa de decir si la posible solucion encontrada es solucion
     * @param servers Array que conte tots els servers
     * @param tolerancia Indica el maxim i minim de carrega que esta disposat a acceptar l'usuari en un server
     * @param solution Solucio obtinguda (com a JAVA es passa tots els objectes per referencia, per aquesta variable obtindriem la solucio)
     * @param funcEx Classe que inclou alguns metodes que ens poden ser de gran ajuda
     * @return Retornem si és solucio (true) o false
     */
    public boolean is_Solution(Server[] servers, int tolerancia, List<Server> solution, AlgorismesExtres funcEx){
        //Obtenim el maxim i el minim de carrega
        int menorCarrega= funcEx.obtindreMinimArray(solution, servers.length,true);
        int majorCarrega= funcEx.obtindreMaximArray(solution);
        //Si s'adapta a la tolerancia retornem true, si no false
        return (majorCarrega - menorCarrega) >= tolerancia;
    }

    /*
    public User select(int usuariActual, User[] candidates){
        User user = new User();
        int idUser;

        for (int i = 0; i < candidates.length - 1; i++){
            if (i == usuariActual){
                user = candidates[i];
                break;
            }
        }

        return user;
    }
    */

}

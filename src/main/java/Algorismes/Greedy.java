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
     *
     * @param servers: nodes on els usuaris són repartits
     * @param candidates: candidates
     */
    public ArrayList <Server> greedyDistribucioCarrega(Server[] servers, User[] candidates, int tolerancia){
        int idServerTrobat;

        ArrayList <Server> solution = new ArrayList<Server>();
        AlgorismesExtres funcEx = new AlgorismesExtres();
        Server s = new Server();

        //Repartim els usuaris
        for (int i = 0; i < (candidates.length ); i++){
            /* Recollim tota la informació de l'usuari actual */
            User c = candidates[i];

            /* Mirem a quin és el server més factible per aquest usuari i l'equitativitat */
            s = funcEx.whereIsFeaseble(servers, c, solution,candidates);
            idServerTrobat = funcEx.addInformationSolution (s, solution, c);
        }

        if (is_Solution(servers, tolerancia, solution, funcEx)){
            System.out.println("Error! La tolerancia introduida no és suficient per a que els servidors aguantin " +
                    "a tots els usuaris");
            System.out.println("La proxima vegada intenta introduir una tolerancia més gran");
            ArrayList <Server> array_aux = new ArrayList<Server>();
            return array_aux;
        }

        return solution;
    }

    public int greedyCamiMinim(Server serverInicial, Server serverBuscat, Node[] nodes, Node nodeInicial, ArrayList <Node> solution){
        List <Node> nodesActuals = serverInicial.getNodesDisponibles();
        AlgorismesExtres funcEx = new AlgorismesExtres();

        Node n = nodeInicial;
        solution.add(n);                                                                                                //Afegim el node inicial
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
                solution.clear();
                aux_cost[0] = Integer.MAX_VALUE;
                break;
            }
        }
        return aux_cost [0];
    }

    public double greedyFiabilitat(Server serverInicial, Server serverBuscat, Node[] nodes, Node nodeInicial, ArrayList <Node> solution){
        List <Node> nodesActuals = serverInicial.getNodesDisponibles();
        AlgorismesExtres funcEx = new AlgorismesExtres();

        Node n = nodeInicial;
        solution.add(n);                                                                                                //Afegim el node inicial
        int comptador = 0;
        double aux_fiabilitat [] = new double[1];
        aux_fiabilitat[0] = 1.0;
        /*Mentres que el node que estem mirant no estigui directament connectat a el servidor que busquem, seguim fent camí*/
        while (!funcEx.serverTrobat(n, serverBuscat)){

            /* Mirem a quina és la distancia mínima cap al següent node i que el següent node ja estigui a la solució */
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

    public boolean is_Solution(Server[] servers, int tolerancia, List<Server> solution, AlgorismesExtres funcEx){
        int menorCarrega= funcEx.obtindreMinimArray(solution, servers.length,true);
        int majorCarrega= funcEx.obtindreMaximArray(solution);

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

package AlgorismesDistribucioCarrega;

//import static java.sql.JDBCType.NULL;


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
    public List<Server> greedyDistribucioCarrega(Server[] servers, User[] candidates, int tolerancia){
        int idServerTrobat;

        List<Server> solution = new ArrayList<Server>();
        AlgorismesExtres funcEx = new AlgorismesExtres();
        Server s = new Server();

        //Repartim els usuaris
        for (int i = 0; i < (candidates.length - 1); i++){
            /* Recollim tota la informació de l'usuari actual */
            User c = candidates[i];

            /* Mirem a quin és el server més factible per aquest usuari i l'equitativitat */
            s = whereIsFeaseble(funcEx, servers, candidates[i], solution);
            idServerTrobat = funcEx.serverEncontrado(solution, s.getId());

            if (idServerTrobat != -1){
                s = solution.get(idServerTrobat);
                s.getUsers().add(candidates[i]);
                s.sumarCarrega(candidates[i]);
                solution.set(idServerTrobat, s);

            } else {
                s = funcEx.setInformation(servers, s.getId(), candidates, i);
                solution.add(s);

            }
        }

        if (is_Solution(servers, tolerancia, solution, funcEx)){
            System.out.println("Error! La tolerancia introduida no és suficient per a que els servidors aguantin " +
                    "a tots els usuaris");
            System.out.println("La proxima vegada intenta introduir una tolerancia més gran");

            return null;
        }

        return solution;
    }

    public Server whereIsFeaseble(AlgorismesExtres funcEx, Server[] servers, User user, List<Server> solution){
        double best = Double.MAX_VALUE;
        int bestIdServer = 0;
        double resultat;
        int maxim = funcEx.obtindreMaximArray(solution);
        int minim = funcEx.obtindreMinimArray(solution, servers.length,false);

        Server server;

        for (int i = 0; i < servers.length - 1; i++){
            resultat =  Math.pow (1.05, (maxim - minim)) * funcEx.calculDiferencial(solution);

            if (resultat < best){
                best = resultat;
                bestIdServer = servers[i].getId();
            }
        }

        server = servers[bestIdServer];

        return server;
    }

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

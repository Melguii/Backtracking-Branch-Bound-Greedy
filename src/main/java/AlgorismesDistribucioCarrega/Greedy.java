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
        for (int i = 0; i < (candidates.length ); i++){
            /* Recollim tota la informació de l'usuari actual */
            User c = candidates[i];

            /* Mirem a quin és el server més factible per aquest usuari i l'equitativitat */
            s = whereIsFeaseble(funcEx, servers, c, solution,candidates);
            idServerTrobat = funcEx.serverEncontrado(solution, s.getId());

            if (idServerTrobat != -1){
                s = solution.get(idServerTrobat);
                s.getUsers().add(c);
                s.sumarCarrega(c);
                solution.set(idServerTrobat, s);

            } else {
                s = funcEx.setInformation(s, candidates[i]);
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

    public Server whereIsFeaseble(AlgorismesExtres funcEx, Server[] servers, User user, List<Server> solution, User [] users){
        double best = Double.MAX_VALUE;
        int bestIdServer = 0;
        double resultat;
        Server s = new Server();

        for (int i = 0; i < servers.length; i++){
            int idServerTrobat = funcEx.serverEncontrado(solution, servers[i].getId());
            if (idServerTrobat != -1){
                s = solution.get(idServerTrobat);
                s.getUsers().add(user);
                s.sumarCarrega(user);
                solution.set(idServerTrobat, s);

            } else {
                s = funcEx.setInformation(servers[i], user);
                solution.add(s);
            }
            int maxim = funcEx.obtindreMaximArray(solution);
            int minim = funcEx.obtindreMinimArray(solution, servers.length,user == users[users.length-1]);
            resultat =  Math.pow (1.05, (maxim - minim)) * funcEx.calculDiferencial(solution);

            if (resultat < best){
                best = resultat;
                bestIdServer = i;
            }
            if (idServerTrobat == -1) {
                solution.remove(solution.size()-1);
            }
            else {
                solution.get(idServerTrobat).restarCarrega(solution.get(idServerTrobat).getUsers().get(solution.get(idServerTrobat).getUsers().size()-1));
                solution.get(idServerTrobat).getUsers().remove(solution.get(idServerTrobat).getUsers().size()-1);
            }
        }

        s = servers[bestIdServer];

        return s;
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

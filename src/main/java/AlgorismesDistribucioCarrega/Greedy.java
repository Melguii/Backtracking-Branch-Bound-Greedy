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

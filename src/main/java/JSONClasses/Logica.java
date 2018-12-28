package JSONClasses;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Logica {
    User [] users;
    Node [] nodes;
    Server [] servers;

    /**
     * S'ocupa de llegir tots els fitxers que introdueix l'usuari o es fiquen per defecte, a la mateixa vegada que comprova la seva existencia
     * @param nomFitxerUsers Nom que te el fitxer que conte els usuaris
     * @param nomFitxerNodes Nom que te el fitxer que conte els nodes
     * @param nomFitxerServers Nom que te el fitxer que conte els servers
     */
    public void lecturaFitxers(String nomFitxerUsers, String nomFitxerNodes, String nomFitxerServers) {
        FileReader fitxerUsers;
        FileReader fitxerNodes;
        FileReader fitxerServers;
        //Comprovem l'existencia dels diferents fitxers (users, nodes, servers)
        fitxerUsers = seleccioFitxer(nomFitxerUsers);
        fitxerNodes = seleccioFitxer(nomFitxerNodes);
        fitxerServers = seleccioFitxer(nomFitxerServers);
        //Ara ens dediquem a anar llegint tots el fitxers
        Gson gson = new Gson();
        users = gson.fromJson(fitxerUsers, User[].class);
        nodes = gson.fromJson(fitxerNodes, Node[].class);
        servers = gson.fromJson(fitxerServers, Server[].class);
        //TODO FALTARIA REFERENCIAR TOTA LA INFORMACIO QUE TENIM A USERS, NODES I SERVERS
        //Un cop fet tot aquest proces tindrem tota la informació necessaria en els atributs de la classe
    }

    /**
     * Control del nom de fitxer introduit( mirem si es correcte o si no existeix, i per tant hem de mostrar un error)
     * també establim que tots els fitxers estaran a la carpeta datasets
     * @param nom_fitxer Nom del fitxer que ha introduit l'usuari
     * @return Retorna una variable tipus FileReader per tal que el fitxer pugui ser llegit/interpretat
     */
    private FileReader seleccioFitxer(String nom_fitxer) {
        FileReader fitxer = null;
        do {
            try {
                fitxer = new FileReader(nom_fitxer);
            } catch (FileNotFoundException e) {
                System.out.println("Error fitxer especificat no trobat (ha d'estar a la carpeta datasets),no ens petaras \nel programa tan facilment, fem PAED (⌐■_■)");
                System.out.println("La nostra generositat no coneix limits, trona'm a introduir nom del fitxer :):");
                Scanner sc = new Scanner(System.in);
                nom_fitxer = "datasets/" + sc.nextLine();
            }
        } while (fitxer == null);
        return fitxer;
    }
}

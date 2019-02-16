package JSONClasses;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private int opcioMenuFitxers;
    private int opcioMenuDisponibilitat;
    private int opcioMenuDistribucioCarrega;
    private User UserEmisor;
    private User UserReceptor;

    /**
     * Mostrem a l'usuari les diferentes opcions del menu de fitxers(que et pregunta sobre quin son els fitxers que vols utilitzar)
     */
    public void seleccioMenuFitxer () {
        String opcioLlegida;
        int opcioInt;
        do {
            //Anem mostrant el menu de fitxers constantment, fins que l'opcio sigui valida
            mostraMenuFitxers();
            //L'usuari elegeix l'opcio
            Scanner sc = new Scanner(System.in);
            opcioLlegida = sc.nextLine();
        } while(comprovacioErrorsOpcio(opcioLlegida,1,2));
        //Igualem l'opcio llegida (amb comprovacio d'errors a l'atribut de la classe
        opcioMenuFitxers = Integer.parseInt(opcioLlegida);
    }

    /**
     * Metode que s'ocupa de comprar que la opcio introduida per l'usuari segons un menu sigui valida
     * @param opcioLlegida Opcio introduida per l'usuari en format de cadena de caracters
     * @param intervalMenor Nombre més petit de totes les opcions disponibles
     * @param intervalMajor Nombre més gran de totes les opcions disponibles
     * @return Si hi ha hagut algun error i que per tant no sigui una opcio valida
     */
    private boolean comprovacioErrorsOpcio(String opcioLlegida, int intervalMenor, int intervalMajor) {
        int i = 0;
        int opcio;
        //Comprovem que s'hagi introduit alguna cosa
        if (opcioLlegida.length() == 0) {
            System.out.println("Error no has introduit cap opcio\n");
            return true;
        }
        else{
            //Anem recorrent tots els caracters mirant si n'hi ha algun que no sigui valid
            while (i < opcioLlegida.length()) {
                if (opcioLlegida.charAt(i) < '0' || opcioLlegida.charAt(i) > '9') {
                    System.out.println("Error has introduit algun caracter que no es numero\n");
                    return true;
                }
                i++;
            }
            //Passem l'opcio introduida a numero
            opcio = Integer.parseInt(opcioLlegida);
            //Mirem que l'opcio introduida estigui entre l'interval de les opcions donades
            if (opcio < intervalMenor || opcio > intervalMajor) {
                System.out.println("La teva introducció no es cap de les opcions disponibles\n");
                return true;
            }
        }
        //Si no hi ha cap error retornem fals
        return false;
    }

    /**
     * Metode que s'ocupa de mostrar les diferents opcions que hi ha respecte als fitxers a introduir
     */
    private void mostraMenuFitxers() {
        //Mostrem informacio sobre cadascuna de les opcions
        System.out.println("1.Fitxers per defecte (nodes_plus.json, servers_plus.json, users.json)");
        System.out.println("2.Soc informatic, tinc fitxers propis ʘ‿ʘ");
        System.out.println("De quins fitxers vols realiztar la lectura?:");
    }
    /**
     * Metode de busqueda binaria, per tal de buscar un username concret (concretament el que busquem
     * és que ens passen per argument, per tal de poder mostrar la comparacio de prioritats d'aquell
     * usuari en concret)
     * @param users Array de users on buscarem el username introduit
     * @param valorBuscat Username que buscarem en l'array
     * @return Retornem tota la informacio del username trobat
     */
    private User busquedaUsuari (User[] users, String valorBuscat) {
        User user = null;
        User [] users_2;
        users_2 = users.clone();

        int principi = 0;
        int fin = users.length - 1;
        int valor_resultat = 0;
        int mig = (principi + fin)/2;
        boolean b = false;
        while (!b && (principi <= fin)){
            mig = (principi + fin)/2;
            if (users[mig].getUsername().equals(valorBuscat)) {
                b = true;
                valor_resultat = mig;
            }
            else {
                if (users[principi].getUsername().equals(valorBuscat)) {
                    b = true;
                    valor_resultat = principi;
                }
                else {
                    if (users[fin].getUsername().equals(valor_resultat)) {
                        b = true;
                        valor_resultat = fin;
                    }
                    else {
                        if (users[mig].getUsername().compareTo(valorBuscat) > 0) {
                            fin = mig - 1;
                        } else {
                            principi = mig + 1;
                        }
                    }
                }
            }
        }
        user = users[valor_resultat];
        return user;
    }

    /**
     * Metode que ens permet seleccionar una opcio per tal de dur a termer la distrubucio de carrega o disponibilitat
     * @param texto Text que indica a l'usuari el que se li està demanant
     */
    public void seleccioMenuMode(String texto) {
        String opcio;
        do {
            mostarMenuModes(texto);
            //El usuari introdueix l'opcio desitjada
            Scanner sc = new Scanner (System.in);
            opcio = sc.nextLine();
            //Repetirem el proces fins que s'introdueixi una opcio correcta
        } while(comprovacioErrorsOpcio(opcio,1,5));
        //Retornem la opcio correcta introduida per l'usuari
        if (texto.equals("Distribucio de carrega")) {
            opcioMenuDistribucioCarrega = Integer.parseInt(opcio);
        }
        else {
            opcioMenuDisponibilitat = Integer.parseInt(opcio);
        }
    }

    public void seleccioUsuari(String texto, Logica l){
        String userName;
        boolean userTrobat = false;

        User user = new User();

        do {
            System.out.println(texto);
            //El usuari introdueix l'opcio desitjada
            Scanner sc = new Scanner (System.in);
            userName = sc.nextLine();

            if (userName.equals(l.cercaUser(userName).getUsername())){
                userTrobat = true;
                user = l.cercaUser(userName);
            }

        } while(!userTrobat);                                                                                           //Repetirem el proces fins que s'introdueixi una opcio correcta

        //Retornem la opcio correcta introduida per l'usuari
        if (texto.equals("Quin usuari ets?")) {
            UserEmisor = user;
        }
        else {
            UserReceptor = user;
        }
    }

    /**
     * Mostra el menu amb tots els modes possibles
     * @param texto Text que indica a l'usuari el que se li ho està demanant
     */
    private void mostarMenuModes (String texto) {
        System.out.println("1.Backtracking");
        System.out.println("2.Branch & Bound");
        System.out.println("3.Greedy");
        System.out.println("4.Greedy + Backtracking");
        System.out.println("5.Greedy + Branch & Bound");
        System.out.println("Selecciona una opcio per realizar la " + texto);
    }

    /**
     * Getter de la opcio de menu fitxers
     * @return Opcio introduida per l'usuari en el menu de fitxers
     */
    public int getOpcioMenuFitxers() {
        return opcioMenuFitxers;
    }

    /**
     * Setter de la opcio menu fitxers
     * @param opcioMenuFitxers Opcio introduida per l'usuari en menu fitxers
     */
    public void setOpcioMenuFitxers(int opcioMenuFitxers) {
        this.opcioMenuFitxers = opcioMenuFitxers;
    }

    /**
     * Getter de la opcio del menu de disponibilitat
     * @return Opcio introduida per l'usuari en la opcio del menu de disponibilitat
     */
    public int getOpcioMenuDisponibilitat() {
        return opcioMenuDisponibilitat;
    }

    /**
     * Setter de menu de disponibilitat
     * @param opcioMenuDisponibilitat La opcio introduida per l'usuari en el menu de disponibilitat
     */
    public void setOpcioMenuDisponibilitat(int opcioMenuDisponibilitat) {
        this.opcioMenuDisponibilitat = opcioMenuDisponibilitat;
    }

    /**
     * Getter opcio menu distribucio carrega
     * @return Opcio introduida per l'usuari en el menu de distribucio de carrega
     */
    public int getOpcioMenuDistribucioCarrega() {
        return opcioMenuDistribucioCarrega;
    }

    /**
     * Setter de opcio menu distribucio carrega
     * @param opcioMenuDistribucioCarrega Opcio introduida per l'usuari en el menu de distribucio de carrega
     */
    public void setOpcioMenuDistribucioCarrega(int opcioMenuDistribucioCarrega) {
        this.opcioMenuDistribucioCarrega = opcioMenuDistribucioCarrega;
    }

    /**
     * Getter del Usuari Emissor
     * @return Retorna el usuari emissor del cami
     */
    public User getUserEmisor() {
        return UserEmisor;
    }

    /**
     * Setter del Usuari Emissor
     * @param userEmisor Usuari Emissor que volem establir en el cami que estem construint
     */
    public void setUserEmisor(User userEmisor) {
        UserEmisor = userEmisor;
    }

    /**
     * Getter del User Receptor
     * @return Retorna el usuari recpetor del cami que estem construint
     */
    public User getUserReceptor() {
        return UserReceptor;
    }

    /**
     * Setter del User Receptor
     * @param userReceptor Usuari Receptor que volem establir en el cami que estem construint
     */
    public void setUserReceptor(User userReceptor) {
        UserReceptor = userReceptor;
    }
}
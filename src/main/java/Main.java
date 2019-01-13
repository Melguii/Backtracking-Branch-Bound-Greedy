import JSONClasses.Logica;
import JSONClasses.Menu;
import JSONClasses.Server;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Menu menu = new Menu();
        Logica logica = new Logica();
        menu.seleccioMenuFitxer();
        logica.execucioMenuFitxer(menu.getOpcioMenuFitxers());

        //Demanem a l'usuari que introdueixi el metode que vol utilitzar per distribuir els diferents usuaris en diferents servers
        menu.seleccioMenuMode("Distribucio de carrega");
        List<Server> solucio;
        solucio = logica.execucioMenuModeCarrega (menu.getOpcioMenuDistribucioCarrega());

        //Demanem a l'usuari que introdueixi el metode que vol utilitzar per calcular el cami més fiable i el més curt per anar d'un node al altre
        menu.seleccioMenuMode("Disponibilitat (cami entre dos usuaris)");
        menu.seleccioUsuari("Quin usuari ets?",logica);
        menu.seleccioUsuari("A qui vols stalkejar?",logica);
        logica.execucioMenuModeDisponibilitat (menu.getOpcioMenuDisponibilitat(), menu.getUserEmisor(), menu.getUserReceptor(), solucio);


    }
}

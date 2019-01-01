import JSONClasses.Logica;
import JSONClasses.Menu;

public class Main {
    public static void main(String[] args) {

        Menu menu = new Menu();
        Logica logica = new Logica();
        menu.seleccioMenuFitxer();
        logica.execucioMenuFitxer(menu.getOpcioMenuFitxers());

        //Demanem a l'usuari que introdueixi el metode que vol utilitzar per distribuir els diferents usuaris en diferents servers
        menu.seleccioMenuMode("Distribucio de carrega");
        logica.execucioMenuModeDisponibilitat (menu.getOpcioMenuDistribucioCarrega());

        //Demanem a l'usuari que introdueixi el metode que vol utilitzar per calcular el cami més fiable i el més curt per anar d'un node al altre
        menu.seleccioMenuMode("Disponibilitat (cami entre dos usuaris)");
        System.out.println(menu.getOpcioMenuDisponibilitat());


    }
}

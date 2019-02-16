package Comparators;

import JSONClasses.Server;
import utils.AlgorismesExtres;

import java.util.List;

public class CompareDistribution implements ComparatorServerList{
    /**
     * Compara dues llistes de servers (indicant si la primera és més gran, segons un criteri especific)
     * @param llista1 Primera llista de severs
     * @param llista2 Segona llista de servers que comparem
     * @param numServers numero de servers que tenim en la nostra plataforma
     * @return Retornem si la primera llista és més gran, en quan a la formula (que té en compte la distancia i la disferencia de carregues)
     *         que la segona.
     */
    public boolean compararp1top2(List<Server> llista1, List<Server> llista2, int numServers) {
        AlgorismesExtres ExtraAlgorithms = new AlgorismesExtres();
        int maxim = ExtraAlgorithms.obtindreMaximArray(llista1);
        int minim = ExtraAlgorithms.obtindreMinimArray(llista1,numServers ,false);
        double resultat1 =  Math.pow (1.05, (maxim - minim)) * ExtraAlgorithms.calculDiferencial(llista1);
        maxim = ExtraAlgorithms.obtindreMaximArray(llista2);
        minim = ExtraAlgorithms.obtindreMinimArray(llista2,numServers ,false);
        double resultat2 =  Math.pow (1.05, (maxim - minim)) * ExtraAlgorithms.calculDiferencial(llista2);
        if (resultat1 > resultat2) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Compara dues llistes de servers (indicant si la segona és més gran ,segons un criteri especific)
     * @param llista1 Primera llista de servers que copmparem
     * @param llista2 Segona llista de servers que comparem
     * @param numServers Numero de servers que tenim en total
     * @return
     */
    public boolean compararp2top1(List<Server> llista1, List<Server> llista2, int numServers) {
        AlgorismesExtres ExtraAlgorithms = new AlgorismesExtres();
        int maxim = ExtraAlgorithms.obtindreMaximArray(llista1);
        int minim = ExtraAlgorithms.obtindreMinimArray(llista1,numServers ,false);
        double resultat1 =  Math.pow (1.05, (maxim - minim)) * ExtraAlgorithms.calculDiferencial(llista1);
        maxim = ExtraAlgorithms.obtindreMaximArray(llista2);
        minim = ExtraAlgorithms.obtindreMinimArray(llista2,numServers ,false);
        double resultat2 =  Math.pow (1.05, (maxim - minim)) * ExtraAlgorithms.calculDiferencial(llista2);
        if (resultat1 < resultat2) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Compara dues llistes de servers (indicant si la segona és més gran o gual que la primera, segons un criteri especific)
     * @param llista1 Primer array de servers
     * @param llista2 Segon array de servers que volem comparar
     * @param numServers Numero de servers total que tenim
     * @return Retornem si la segona llista és més gran o igual que la primera pel que fa al calcul de la formula (que té en compte
     * la distancia del usuaris al server i la diferencia entre carregues del servers)
     */
    public boolean compararp2top1IncludeEqual(List<Server> llista1, List<Server> llista2, int numServers) {
        AlgorismesExtres ExtraAlgorithms = new AlgorismesExtres();
        //Per a cadascuna de les llistes de servers calculem el resultat de aplicar la formula
        int maxim = ExtraAlgorithms.obtindreMaximArray(llista1);
        int minim = ExtraAlgorithms.obtindreMinimArray(llista1,numServers ,false);
        double resultat1 =  Math.pow (1.05, (maxim - minim)) * ExtraAlgorithms.calculDiferencial(llista1);
        maxim = ExtraAlgorithms.obtindreMaximArray(llista2);
        minim = ExtraAlgorithms.obtindreMinimArray(llista2,numServers ,false);
        double resultat2 =  Math.pow (1.05, (maxim - minim)) * ExtraAlgorithms.calculDiferencial(llista2);
        if (resultat1 <= resultat2) {
            return true;
        }
        else {
            return false;
        }
    }
}

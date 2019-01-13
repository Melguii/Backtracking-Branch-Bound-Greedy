package Comparators;

import JSONClasses.Server;
import utils.AlgorismesExtres;

import java.util.List;

public class CompareDistribution implements ComparatorServerList{
    public boolean compararp1top2(List<Server> llista1, List<Server> llista2, int numServers) {
        AlgorismesExtres ExtraAlgorithms = new AlgorismesExtres();
        int maxim = ExtraAlgorithms.obtindreMaximArray(llista1);
        int minim = ExtraAlgorithms.obtindreMinimArray(llista1,numServers ,false);
        double resultat1 =  Math.pow (1.05, (maxim - minim)) * ExtraAlgorithms.calculDiferencial(llista1);
        maxim = ExtraAlgorithms.obtindreMaximArray(llista1);
        minim = ExtraAlgorithms.obtindreMinimArray(llista1,numServers ,false);
        double resultat2 =  Math.pow (1.05, (maxim - minim)) * ExtraAlgorithms.calculDiferencial(llista1);
        if (resultat1 > resultat2) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean compararp2top1(List<Server> llista1, List<Server> llista2, int numServers) {
        AlgorismesExtres ExtraAlgorithms = new AlgorismesExtres();
        int maxim = ExtraAlgorithms.obtindreMaximArray(llista1);
        int minim = ExtraAlgorithms.obtindreMinimArray(llista1,numServers ,false);
        double resultat1 =  Math.pow (1.05, (maxim - minim)) * ExtraAlgorithms.calculDiferencial(llista1);
        maxim = ExtraAlgorithms.obtindreMaximArray(llista1);
        minim = ExtraAlgorithms.obtindreMinimArray(llista1,numServers ,false);
        double resultat2 =  Math.pow (1.05, (maxim - minim)) * ExtraAlgorithms.calculDiferencial(llista1);
        if (resultat1 < resultat2) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean compararp2top1IncludeEqual(List<Server> llista1, List<Server> llista2, int numServers) {
        AlgorismesExtres ExtraAlgorithms = new AlgorismesExtres();
        int maxim = ExtraAlgorithms.obtindreMaximArray(llista1);
        int minim = ExtraAlgorithms.obtindreMinimArray(llista1,numServers ,false);
        double resultat1 =  Math.pow (1.05, (maxim - minim)) * ExtraAlgorithms.calculDiferencial(llista1);
        maxim = ExtraAlgorithms.obtindreMaximArray(llista1);
        minim = ExtraAlgorithms.obtindreMinimArray(llista1,numServers ,false);
        double resultat2 =  Math.pow (1.05, (maxim - minim)) * ExtraAlgorithms.calculDiferencial(llista1);
        if (resultat1 <= resultat2) {
            return true;
        }
        else {
            return false;
        }
    }
}

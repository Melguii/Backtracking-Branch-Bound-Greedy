package JSONClasses;

import java.util.ArrayList;
import java.util.List;

public class Post {
    private int          id;
    private long         published;
    private List<Float>  location;
    private String       category;
    private List<String> liked_by;
    private List<String> commented_by;
    private long         comparacioUbicacio;        //Distancia entre usuaris
    private long         valorPrioritat;


    /**
     * Retorna la diferencia en distancia calculada en Haversine entre la ubicacio de referencia i l'ubicacio del Post
     * @return Distancia en Haversine
     */
    public long getComparacioUbicacio() {
        return comparacioUbicacio;
    }
    /**
     * Estableix la diferencia en distancia calculada en Haversine
     * @return Distancia en Haversine
     */
    public void setComparacioUbicacio(long comparacioUbicacio) {
        this.comparacioUbicacio = comparacioUbicacio;
    }

    /**
     * Constructor de Post, necessari per establir a de quin tipus concret es la interficie List
     */
    public Post() {
        this.location = new ArrayList<Float>();
        this.liked_by = new ArrayList<String>();
        this.commented_by = new ArrayList<String>();
    }

    /**
     * Getter de ID
     * @return Valor actual de ID
     */
    public int getId() {
        return id;
    }

    /**
     * Setter de ID
     * @param id Valor que establim a ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter de Published
     * @return Valor actual de published
     */
    public long getPublished() {
        return published;
    }

    /**
     * Setter de Published
     * @param published Valor que establim a Published
     */
    public void setPublished(long published) {
        this.published = published;
    }

    /**
     * Getter de Location
     * @return Valors actuals que té la l'atribut de tipus llista Location
     */
    public List<Float> getLocation() {
        return location;
    }

    /**
     * Setter de Location
     * @param location Valors que volem assignar a l'atribut tipus llista location
     */
    public void setLocation(List<Float> location) {
        this.location = location;
    }

    /**
     * Getter  de Category
     * @return Valor actual de Category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Setter de Category
     * @param category Valor que volem establir a l'atribut Category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Getter de liked_by
     * @return Valor actual de l'atribut llista liked_by
     */
    public List<String> getLiked_by() {
        return liked_by;
    }

    /**
     * Setter de liked_by
     * @param liked_by Valors que volem assignar a l'atribut tipus llista liked_by
     */
    public void setLiked_by(List<String> liked_by) {
        this.liked_by = liked_by;
    }

    /**
     * Getter de commented_by
     * @return Valor actual de l'atribut tipus llista commented_by
     */
    public List<String> getCommented_by() {
        return commented_by;
    }

    /**
     * Setter de commented_by
     * @param commented_by Valors que volem assignar a l'atribut de tipus llista commented_by
     */
    public void setCommented_by(List<String> commented_by) {
        this.commented_by = commented_by;
    }

    /**
     * Getter valor prioritat
     * @return Valor de prioritat que te un post en concret
     */
    public long getValorPrioritat() {
        return valorPrioritat;
    }

    /**
     * Setter de valor prioritat
     * @param valorPrioritat Valor de prioritat que assignem a post (per tal de poder fer la comparacio)
     */
    public void setValorPrioritat(long valorPrioritat) {
        this.valorPrioritat = valorPrioritat;
    }


    /**
     * Funcio que ens ajuda a calcular la distancia entre dos usuaris correctament
     * @param longitudRef Indica la longitud objectiu
     * @param latitudRef Indica la latitud objectiu
     */
    public void calculHaversine (float longitudRef, float latitudRef) {
        double dlatitud = latitudRef-this.location.get(0);
        double dlongitud = longitudRef-this.location.get(1);
        comparacioUbicacio = (int) (100000 *(2 * 6371* Math.asin(Math.sqrt(Math.pow(Math.sin(Math.toRadians(dlatitud/2)),(float)2)+Math.cos(Math.toRadians(this.location.get(0)))*Math.cos(Math.toRadians(latitudRef))*Math.pow(Math.sin(Math.toRadians(dlongitud/2)),2)))));
    }
}

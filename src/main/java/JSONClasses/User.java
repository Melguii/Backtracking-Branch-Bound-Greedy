package JSONClasses;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String              username;
    private int                 activity;
    private int                 followers;
    private int                 follows;
    private List<Connection>    connections;
    private List<Post>          posts;
    private List<User>          link;                   //Informacio dels usuaris dels quals interacciona/segueix
    private List<Double>      location;

    /**
     * Constructor per a User, això el que ens permet establir que les List especificades seran ArrayList
     */
    public User() {
        this.connections = new ArrayList<Connection>();
        this.posts = new ArrayList<Post>();
        this.link = new ArrayList<User>();
        this.location = new ArrayList<Double>();
    }

    /**
     * Getter de Link
     * @return Informacio complerta actual de tots els usuaris que segueixen a un usuari en concret
     */
    public List<User> getLink() {
        return link;
    }

    /**
     * Setter de Link
     * @param link Es l'array de l'users que assignarem a el nostre array link
     */
    public void setLink(List<User> link) {
        this.link = link;
    }

    /**
     * Get de Username
     * @return El nom actual d'un usuari en concret
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter de Username
     * @param username Nom d'usuari que assignem a un usuari en concret
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter de Followers
     * @return Numero de followers que te actualment un usuari en concret
     */
    public int getFollowers() {
        return followers;
    }

    /**
     * Setter de Followers
     * @param followers Valor que volem assignar a els followers d'un usuari
     */
    public void setFollowers(int followers) {
        this.followers = followers;
    }

    /**
     * Getter de Follows
     * @return Numero de seguits actual de l'usuari
     */
    public int getFollows() {
        return follows;
    }

    /**
     * Setter de Follows
     * @param follows Numero de seguits que volem assignar a un usuari en concret
     */
    public void setFollows(int follows) {
        this.follows = follows;
    }

    /**
     * Getter de connections
     * @return Array de connections actual de l'usuari (conte tota la informacio de els usuaris amb qui l'usuari desitjat interacciona)
     */
    public List<Connection> getConnections() {
        return connections;
    }

    /**
     * Setter de connections
     * @param connections Array de connections (que conte tota la informacio de les interaccions de l'usuari) que assignem a un usuari concret
     */
    public void setConnections(List<Connection> connections) {
        this.connections = connections;
    }

    /**
     * Getter de posts
     * @return Retornem l'array amb tota la informació dels posts que té l'usuari actualment
     */
    public List<Post> getPosts() {
        return posts;
    }

    /**
     * Setter de posts
     * @param posts Array de posts que volem assignar a un usuari en concret
     */
    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }


    /**
     * De l'usuari en concret afegim la informació dels usuaris als quals segeuix/interactua
     * @param u Array que conte la llista de tots els usuaris de la plataforma
     */
    public void referenciarSeguidors (User u[]) {
        boolean trobat;
        int i;

        for (Connection c: connections) {
            trobat = false;
            i = 0;
            while (trobat == false && i < u.length) {

                if (c.getUsername().equals(u[i].getUsername())) {
                    link.add(u[i]);
                    trobat = true;
                }

                i++;
            }
        }
    }

    public int getActivity() {
        return activity;
    }

    public void setActivity(int activity) {
        this.activity = activity;
    }
    public void calcularLocalitzacioUsuari () {
        location.add(posts.get(posts.size() - 1).getLocation().get(0));
        location.add(posts.get(posts.size() - 1).getLocation().get(0));
    }
    public double calculHaversine (List<Double> locationRef) {
        double comparacioUbicacio;
        double latitudRef = locationRef.get(0);
        double longitudRef = locationRef.get(1);
        double dlatitud = latitudRef-location.get(0);
        double dlongitud = longitudRef-this.location.get(1);
        comparacioUbicacio = ((2 * 6371* Math.asin(Math.sqrt(Math.pow(Math.sin(Math.toRadians(dlatitud/2)),(float)2)+Math.cos(Math.toRadians(this.location.get(0)))*Math.cos(Math.toRadians(latitudRef))*Math.pow(Math.sin(Math.toRadians(dlongitud/2)),2)))));
        return comparacioUbicacio;
    }

}

package JSONClasses;

public class Connection {
    //Atributs de la classe Connection
    private String  username;
    private long     since;
    private int     visits;
    private int     likes;
    private int     comments;

    /**
     * Getter de username
     * @return Retorna el nom del usuari amb que un altre usuari s'ha connectat
     */
    public String getUsername() {
        return username;
    }

    /**
     * Nom que volem assignar a l'usuari de la connexio
     * @param username Nom d'usuari que volem assignar
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter de since
     * @return Retornem des de quan fa que estan connectats els dos usuaris
     */
    public long getSince() {
        return since;
    }

    /**
     * Setter de since
     * @param since S'assigna un valor a des de quan fa que els usuaris estan connectats
     */
    public void setSince(long since) {
        this.since = since;
    }

    /**
     * Getter de visits
     * @return Retorna quantes visites s'han fet a l'usuari en concret
     */
    public int getVisits() {
        return visits;
    }

    /**
     * Setter de visits
     * @param visits Es passa com a parmetre el nombre de vistes que ha fet un usuari a l'altre
     */
    public void setVisits(int visits) {
        this.visits = visits;
    }

    /**
     * Getter de likes
     * @return Retorna el numero de likes que el ususari li ha donat mg a l'altre usuari
     */
    public int getLikes() {
        return likes;
    }

    /**
     * Setter de likes
     * @param likes Estableix el nombre de likes que li ha fet un usuari a l'altre
     */
    public void setLikes(int likes) {
        this.likes = likes;
    }

    /**
     * Getter de Comments
     * @return Retorna el numero de comentaris que un usuari li ho ha fet a l'altre
     */
    public int getComments() {
        return comments;
    }

    /**
     * Setter de Comments
     * @param comments Passa per parametre quants comentaris li ha fet un usuari a un altre
     */
    public void setComments(int comments) {
        this.comments = comments;
    }

}

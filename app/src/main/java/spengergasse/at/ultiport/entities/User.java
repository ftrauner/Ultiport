package spengergasse.at.ultiport.entities;

public class User {

    private String id;
    private String vorname;
    private String nachname;
    private String username;
    private String passwort;
    private String gruppe;

        public User(){

        }

        public User(String vorname, String nachname, String gruppe, String id){
            this.vorname = vorname;
            this.nachname = nachname;
            this.id = id;
            this.gruppe = gruppe;
        }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public String getGruppe() {
        return gruppe;
    }

    public void setGruppe(String gruppe) {
        this.gruppe = gruppe;
    }
}

package spengergasse.at.ultiport.entities;

public class Raum {

    private String raumID;
    private String raumText;
    private Organsationseinheit raumOE;


    public String getRaumID() {
        return raumID;
    }

    public void setRaumID(String raumID) {
        this.raumID = raumID;
    }

    public String getRaumText() {
        return raumText;
    }

    public void setRaumText(String raumText) {
        this.raumText = raumText;
    }

    public Organsationseinheit getRaumOE() {
        return raumOE;
    }

   
}

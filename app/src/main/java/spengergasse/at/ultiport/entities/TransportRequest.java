package spengergasse.at.ultiport.entities;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;

//Klasse für den Transportauftrag
public class TransportRequest implements Parcelable {


    // ID des Auftrags, automatisch generiert in DB
    private String reqID;
    //Beschreibung des Auftrags, wird beim Erstellen eingegeben
    private String reqBeschr;
    //Ersteller des Auftrags, wird mitgespeichert beim Erstellen (aktiver User)
    private User reqAnforderer;
    //Zuständiger Transporteur, ist zunächst null und wird beim Annehmen des Auftrags auf den Trasnporteur gesetzt
    private User reqTransporteur;
    //Datum und Uhrzeit des Erstellens, wird mitgespeichert beim Erstellen
    private Date reqStartZeit;
    //Start-Organisationseinheit beim Erstellen festgelegt
    private String reqStartOE;
    //Start-Raum beim Erstellen festgelegt
    private String reqStartRaum;
    //Ziel-Organisationseinheit beim Erstellen festgelegt
    private String reqEndOE;
    //Ziel-Raum beim Erstellen festgelegt
    private String reqEndRaum;


    //Liste speichert alle Aufträge
    public static ArrayList<TransportRequest> requests = new ArrayList<TransportRequest>();


    //Normaler Konstruktor


    public TransportRequest(String reqStartOE, String reqStartRaum, String reqEndOE, String reqEndRaum, Date reqStartZeit, String reqBeschr) {
        this.reqBeschr = reqBeschr;
        this.reqStartZeit = reqStartZeit;
        this.reqStartOE = reqStartOE;
        this.reqStartRaum = reqStartRaum;
        this.reqEndOE = reqEndOE;
        this.reqEndRaum = reqEndRaum;
    }

    //Konstruktor für Parcelable zur Datenübergabe zwischen Activities
    private TransportRequest(Parcel in) {
        //Setze Beschreibungs-Text auf eingelesenen String
        reqBeschr = in.readString();
        //Setze Zeit auf eingelesene Long
        reqStartZeit = new Date(in.readLong());
        reqStartOE = in.readString();
        reqStartRaum = in.readString();
        reqEndOE = in.readString();
        reqEndRaum = in.readString();
    }

    //Anforderung für Parcelable
    public static final Creator<TransportRequest> CREATOR = new Creator<TransportRequest>() {
        @Override
        public TransportRequest createFromParcel(Parcel in) {
            return new TransportRequest(in);
        }

        @Override
        public TransportRequest[] newArray(int size) {
            return new TransportRequest[size];
        }
    };

    //Get & Set

    public String getReqID() {
        return reqID;
    }

    public void setReqID(String reqID) {
        this.reqID = reqID;
    }

    public String getReqBeschr() {
        return reqBeschr;
    }

    public void setReqBeschr(String reqBeschr) {
        this.reqBeschr = reqBeschr;
    }

    public User getReqAnforderer() {
        return reqAnforderer;
    }

    public void setReqAnforderer(User reqAnforderer) {
        this.reqAnforderer = reqAnforderer;
    }

    public User getReqTransporteur() {
        return reqTransporteur;
    }

    public void setReqTransporteur(User reqTransporteur) {
        this.reqTransporteur = reqTransporteur;
    }

    public Date getReqStartZeit() {
        return reqStartZeit;
    }

    public void setReqStartZeit(Date reqStartZeit) {
        this.reqStartZeit = reqStartZeit;
    }

    public String getReqStartOE() {
        return reqStartOE;
    }

    public void setReqStartOE(String reqStartOE) {
        this.reqStartOE = reqStartOE;
    }

    public String getReqStartRaum() {
        return reqStartRaum;
    }

    public void setReqStartRaum(String reqStartRaum) {
        this.reqStartRaum = reqStartRaum;
    }

    public String getReqEndOE() {
        return reqEndOE;
    }

    public void setReqEndOE(String reqEndOE) {
        this.reqEndOE = reqEndOE;
    }

    public String getReqEndRaum() {
        return reqEndRaum;
    }

    public void setReqEndRaum(String reqEndRaum) {
        this.reqEndRaum = reqEndRaum;
    }

    //Get & Set Ende

    //Anforderung für Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    // Anforderung für Parcelable TODO: Alle Attribute setzen wenn möglich
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //Übergebe Beschreibungs-Text
        dest.writeString(this.reqBeschr);
        //Übergebe Zeit als Long mit getTime(), da Date Datentyp nicht möglich
        dest.writeLong(this.reqStartZeit.getTime());
    }
}

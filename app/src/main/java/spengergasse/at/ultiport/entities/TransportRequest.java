package spengergasse.at.ultiport.entities;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;

//Klasse für den Transportauftrag
public class TransportRequest implements Parcelable {


    // ID des Auftrags, automatisch generiert in DB?
    private String reqID;
    //Beschreibung des Auftrags, wird beim Erstellen eingegeben
    private String reqText;
    //Ersteller des Auftrags, wird mitgespeichert beim Erstellen (aktiver User)
    private Anforderer reqAnforderer;
    //Zuständiger Transporteur, ist zunächst null und wird beim Annehmen des Auftrags auf den Trasnporteur gesetzt
    private Transporteur reqTransporteur;
    //Datum und Uhrzeit des Erstellens, wird mitgespeichert beim Erstellen
    private Date reqTime;
    //Start-Organisationseinheit beim Erstellen festgelegt
    private Organsationseinheit reqOEStart;
    //Start-Raum beim Erstellen festgelegt
    private Raum reqRaumStart;
    //Ziel-Organisationseinheit beim Erstellen festgelegt
    private Organsationseinheit reqOETarget;
    //Ziel-Raum beim Erstellen festgelegt
    private Raum reqRaumTarget;


    //Liste speichert alle Aufträge
    public static ArrayList<TransportRequest> requests = new ArrayList<TransportRequest>();


    //Normaler Konstruktor TODO: Alle Attribute setzen wenn möglich
    public TransportRequest(String reqText, Date reqTime) {
        this.reqText = reqText;
        this.reqTime = reqTime;

    }

    //Konstruktor für Parcelable zur Datenübergabe zwischen Activities TODO: Alle Attribute setzen wenn möglich
    public TransportRequest(Parcel in) {
        //Setze Beschreibungs-Text auf eingelesenen String
        reqText = in.readString();
        //Setze Zeit auf eingelesene Long
        reqTime = new Date(in.readLong());
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

    public String getReqText() {
        return reqText;
    }

    public void setReqText(String reqText) {
        this.reqText = reqText;
    }

    public Anforderer getReqAnforderer() {
        return reqAnforderer;
    }

    public void setReqAnforderer(Anforderer reqAnforderer) {
        this.reqAnforderer = reqAnforderer;
    }

    public Transporteur getReqTransporteur() {
        return reqTransporteur;
    }

    public void setReqTransporteur(Transporteur reqTransporteur) {
        this.reqTransporteur = reqTransporteur;
    }

    public Date getReqTime() {
        return reqTime;
    }

    public void setReqTime(Date reqTime) {
        this.reqTime = reqTime;
    }

    public static ArrayList<TransportRequest> getRequests() {
        return requests;
    }

    public static void setRequests(ArrayList<TransportRequest> requests) {
        TransportRequest.requests = requests;
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
        dest.writeString(this.reqText);
        //Übergebe Zeit als Long mit getTime(), da Date Datentyp nicht möglich
        dest.writeLong(this.reqTime.getTime());
    }
}

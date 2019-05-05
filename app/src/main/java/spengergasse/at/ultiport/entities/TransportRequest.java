package spengergasse.at.ultiport.entities;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//Klasse für den Transportauftrag
public class TransportRequest implements Parcelable {

    // ID des Auftrags, automatisch generiert in DB
    @SerializedName("a_nummer")
    private int reqNummer;
    //Ersteller des Auftrags, wird mitgespeichert beim Erstellen (aktiver User)
    @SerializedName("a_e_anf")
    private int reqAnforderer;
    //Start-Raum beim Erstellen festgelegt
    @SerializedName("a_r_start")
    private String reqStartRaum;
    //Ziel-Raum beim Erstellen festgelegt
    @SerializedName("a_r_ziel")
    private String reqEndRaum;
    //Start-Organisationseinheit beim Erstellen festgelegt
    @SerializedName("a_oe_start")
    private String reqStartOE;
    //Ziel-Organisationseinheit beim Erstellen festgelegt
    @SerializedName("a_oe_ende")
    private String reqEndOE;
    //Art des Transports
    @SerializedName("a_o_id")
    private String reqArt;
    //Status 1= Erstellt 2= In Arbeit 3= Abgeschlossen
    @SerializedName("a_s_status")
    private String reqStatus;
    //Datum und Uhrzeit des Erstellens, wird mitgespeichert beim Erstellen
    @SerializedName("a_startzeit")
    private String reqStartZeit;
    //Datum und Uhrzeit des Abschlusses
    @SerializedName("a_endzeit")
    private String reqEndZeit;
    //Zuständiger Transporteur, ist zunächst null und wird beim Annehmen des Auftrags auf den Trasnporteur gesetzt
    @SerializedName("a_e_trans")
    private int reqTransporteur;
    //Beschreibung des Auftrags, wird beim Erstellen eingegeben
    @SerializedName("a_beschr")
    private String reqBeschr;

    public TransportRequest(int reqNummer, String reqStartRaum, String reqEndRaum, String reqStartOE, String reqEndOE, String reqArt, String reqStatus, int reqTransporteur, String reqBeschr) {
        this.reqNummer = reqNummer;
        this.reqStartRaum = reqStartRaum;
        this.reqEndRaum = reqEndRaum;
        this.reqStartOE = reqStartOE;
        this.reqEndOE = reqEndOE;
        this.reqArt = reqArt;
        this.reqStatus = reqStatus;
        this.reqTransporteur = reqTransporteur;
        this.reqBeschr = reqBeschr;
    }

    //Konstruktor für Parcelable zur Datenübergabe zwischen Activities
    private TransportRequest(Parcel in) {
        reqNummer = in.readInt();
        //Setze Beschreibungs-Text auf eingelesenen String
        reqBeschr = in.readString();
        //Setze Zeit auf eingelesene Long
        reqStatus = in.readString();
        reqStartZeit = in.readString();
        reqStartOE = in.readString();
        reqStartRaum = in.readString();
        reqEndOE = in.readString();
        reqEndRaum = in.readString();
        reqArt = in.readString();

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

    public int getReqNummer() {
        return reqNummer;
    }

    public void setReqNummer(int reqNummer) {
        this.reqNummer = reqNummer;
    }

    public String getReqBeschr() {
        return reqBeschr;
    }

    public void setReqBeschr(String reqBeschr) {
        this.reqBeschr = reqBeschr;
    }

    public int getReqAnforderer() {
        return reqAnforderer;
    }

    public void setReqAnforderer(int reqAnforderer) {
        this.reqAnforderer = reqAnforderer;
    }

    public int getReqTransporteur() {
        return reqTransporteur;
    }

    public void setReqTransporteur(int reqTransporteur) {
        this.reqTransporteur = reqTransporteur;
    }

    public String getReqStartZeit() {
        return reqStartZeit;
    }

    public void setReqStartZeit(String reqStartZeit) {
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

    public String getReqStatus() {
        return reqStatus;
    }

    public void setReqStatus(String reqStatus) {
        this.reqStatus = reqStatus;
    }

    public String getReqArt() {
        return reqArt;
    }

    public void setReqArt(String reqArt) {
        this.reqArt = reqArt;
    }

    public String getReqEndZeit() {
        return reqEndZeit;
    }

    public void setReqEndZeit(String reqEndZeit) {
        this.reqEndZeit = reqEndZeit;
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
        dest.writeInt(this.reqNummer);
        //Übergebe Beschreibungs-Text
        dest.writeString(this.reqBeschr);
        //Übergebe Zeit
        dest.writeString(this.reqStatus);
        dest.writeString(this.reqStartZeit);
        dest.writeString(this.reqArt);

    }
}

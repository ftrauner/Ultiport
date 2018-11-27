package spengergasse.at.ultiport.entities;


import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Date;



public class TransportRequest implements Parcelable {

    private String reqID;
    private String reqText;
    private Anforderer reqAnforderer;
    private Transporteur reqTransporteur;
    private Date reqTime;

    public static ArrayList<TransportRequest> requests = new ArrayList<TransportRequest>();



    public TransportRequest(String reqText, Date reqTime) {
        this.reqText = reqText;
        this.reqTime = reqTime;

    }

    public TransportRequest(Parcel in) {
        reqText = in.readString();
        reqTime = new Date(in.readLong());
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.reqText);
        dest.writeLong(this.reqTime.getTime());
    }
}

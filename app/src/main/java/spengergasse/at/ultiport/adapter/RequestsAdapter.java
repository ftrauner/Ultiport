package spengergasse.at.ultiport.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import spengergasse.at.ultiport.MainActivity;
import spengergasse.at.ultiport.R;
import spengergasse.at.ultiport.entities.TransportRequest;

//Adapter-Klasse um Request-Objekte in der Recycler-View (Liste) der MainActivity anzuzeigen
public class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.ViewHolder> {



    private Context mContext;
    //Interne Request-Liste
    private List<TransportRequest> mRequests;

    //Konstruktor
    public RequestsAdapter(Context context, List<TransportRequest> requests) {
        this.mContext = context;
        this.mRequests = requests;
    }

    public RequestsAdapter() {

    }

    //Neuerstellung eines ViewHolders für die Liste
    @NonNull
    @Override
    public RequestsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Setze Kontext
        Context context = parent.getContext();
        //Setze Layout-Inflater
        LayoutInflater inflater = LayoutInflater.from(context);

        //Setze View
        View requestView = inflater.inflate(R.layout.request_item, parent, false);

        //Rückgabe des ViewHolders mit View als Parameter
        return new ViewHolder(requestView);
    }

    private void MachSchoenBunt(ViewHolder viewHolder, TransportRequest request){
        switch(request.getReqStatus()){
            //erstellt
            case "1": {
                viewHolder.itemView.setBackgroundColor(Color.parseColor("#33CC33"));
                break;
            }
            //aktiv
            case "2": {
                if(request.getReqTransporteur() == MainActivity.userID) {
                    viewHolder.itemView.setBackgroundColor(Color.parseColor("#ffff00"));
                }
                break;
            }
        }
    }

    //Update des ViewHolders der Liste
    @Override
    public void onBindViewHolder(@NonNull RequestsAdapter.ViewHolder viewHolder, int position) {
        //Setzte Request-Objekt auf Objekt bei der entsprechenden Positionsnummer in der Liste
        TransportRequest request = mRequests.get(position);
        //Datumsformat, um die Zeit hübsch anzuzeigen
        DateFormat format = SimpleDateFormat.getTimeInstance();

        //Check zur Vermeidung von NullPointern, weiß nicht ob notwendig
        if(request != null) {
            //Setze GUI-Elemente auf Element im Holder
            /*TextView requestStartOE = viewHolder.requestStartOE;
            requestStartOE.setText(request.getReqStartOE());
            TextView requestStartRaum = viewHolder.requestStartRaum;
            requestStartRaum.setText(request.getReqStartRaum());
            TextView requestEndOE = viewHolder.requestEndOE;
            requestEndOE.setText(request.getReqEndOE());
            TextView requestEndRaum = viewHolder.requestEndRaum;
            requestEndRaum.setText(request.getReqEndRaum());
            TextView requestStartZeit = viewHolder.requestStartZeit;*/
            //requestStartZeit.setText(format.format(request.getReqStartZeit()));
            //TextView requestBeschr = viewHolder.requestBeschr;
            //requestBeschr.setText(request.getReqBeschr());

            String start = request.getReqStartOE()+request.getReqStartRaum();
            String ende = request.getReqEndOE()+request.getReqEndRaum();
            TextView requestStart = viewHolder.requestStart;
            requestStart.setText(start);
            TextView requestEnde = viewHolder.requestEnde;
            requestEnde.setText(ende);
            ImageView requestBild = viewHolder.requestBild;

            switch(request.getReqArt()){
                //Rollstuhl
                case "1": {
                    requestBild.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_patient));
                    break;
                }
                //Laborprobe
                case "2": {
                    requestBild.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_laborprobe));
                    break;
                }
                //Bett
                case "3": {
                    requestBild.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_bett));
                    break;
                }
            }
            MachSchoenBunt(viewHolder,request);
        }
    }

    //Liefert Anzahl der Elemente
    @Override
    public int getItemCount() {
        if (mRequests != null) {
            return mRequests.size();
        }
        return 0;
    }

    //Klasse zur Definition der Anzeige-Elemente in der Liste, durch die die Daten in der Liste dargestellt werden
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView requestStartOE;
        TextView requestStartRaum;
        TextView requestEndOE;
        TextView requestEndRaum;
        TextView requestStartZeit;
        TextView requestBeschr;
        TextView requestStart;
        TextView requestEnde;
        ImageView requestBild;

        //Konstruktor
        ViewHolder(View itemView) {
            super(itemView);

            //Setze die oben genannten Elemente auf die Layout-Elemente, definiert in res\layout\request_item.xml
            requestStart = itemView.findViewById(R.id.request_start);
            //requestStartRaum = itemView.findViewById(R.id.request_startRaum);
            requestEnde = itemView.findViewById(R.id.request_ende);
            //requestEndRaum = itemView.findViewById(R.id.request_endRaum);
            //requestStartZeit = itemView.findViewById(R.id.request_startZeit);
            //requestBeschr = itemView.findViewById(R.id.request_beschr);
            requestBild = itemView.findViewById(R.id.request_bild);
        }
    }
}
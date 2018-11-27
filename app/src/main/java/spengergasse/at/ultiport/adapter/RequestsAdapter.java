package spengergasse.at.ultiport.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import spengergasse.at.ultiport.R;
import spengergasse.at.ultiport.entities.TransportRequest;

//Adapter-Klasse um Request-Objekte in der Recycler-View (Liste) der MainActivity anzuzeigen
public class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.ViewHolder> {

    //Klasse zur Definition der Anzeige-Elemente in der Liste, durch die die Daten in der Liste dargestellt werden
    public class ViewHolder extends RecyclerView.ViewHolder {
        //Textfeld für Auftrag-Beschreibung
        public TextView requestTextView;
        //Textfeld für Auftrag-Erstellzeit
        public TextView requestTimeView;

        //Konstruktor
        public ViewHolder(View itemView) {
            super(itemView);

            //Setze die oben genannten Elemente auf die Layout-Elemente, definiert in res\layout/item_request.xml
            requestTextView = (TextView) itemView.findViewById(R.id.request_Text);
            requestTimeView = (TextView) itemView.findViewById(R.id.request_Time);
        }
    }

    //Interne Request-Liste
    private List<TransportRequest> mRequests;

    //Konstruktor
    public RequestsAdapter(List<TransportRequest> requests){
        mRequests = requests;
    }

    //Neuerstellung eines ViewHolders für die Liste
    @Override
    public RequestsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Setze Kontext
        Context context = parent.getContext();
        //Setze Layout-Inflater
        LayoutInflater inflater = LayoutInflater.from(context);

        //Setze View
        View requestView = inflater.inflate(R.layout.item_request, parent, false);

        //Rückgabe des ViewHolders mit View als Parameter
        return new ViewHolder(requestView);
    }

    //Update des ViewHolders der Liste
    @Override
    public void onBindViewHolder(RequestsAdapter.ViewHolder viewHolder, int position) {
        //Setzte Request-Objekt auf Objekt bei der entsprechenden Positionsnummer in der Liste
        TransportRequest request = mRequests.get(position);
        //Datumsformat, um die Zeit hübsch anzuzeigen
        SimpleDateFormat format = new SimpleDateFormat("d.M.Y H:mm");

        //Check zur Vermeidung von NullPointern, weiß nicht ob notwendig
        if(request != null) {
            //Setze GUI-Elemente auf Element im Holder
            TextView requestTextView = viewHolder.requestTextView;
            requestTextView.setText(request.getReqText());
            TextView requestTimeView = viewHolder.requestTimeView;
            requestTimeView.setText(format.format(request.getReqTime()));
        }
    }

    //Liefert Anzahl der Elemente
    @Override
    public int getItemCount() {
        return mRequests.size();
    }
}


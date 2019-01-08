package spengergasse.at.ultiport.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import spengergasse.at.ultiport.R;
import spengergasse.at.ultiport.entities.User;

//Adapter-Klasse um Request-Objekte in der Recycler-View (Liste) der MainActivity anzuzeigen
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    //Interne User-Liste
    private List<User> mUsers;

    //Konstruktor
    public UserAdapter(List<User> users) {
        mUsers = users;
    }

    //Neuerstellung eines ViewHolders für die Liste
    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Setze Kontext
        Context context = parent.getContext();
        //Setze Layout-Inflater
        LayoutInflater inflater = LayoutInflater.from(context);

        //Setze View
        View requestView = inflater.inflate(R.layout.user_item, parent, false);

        //Rückgabe des ViewHolders mit View als Parameter
        return new ViewHolder(requestView);
    }

    //Update des ViewHolders der Liste
    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder viewHolder, int position) {
        //Setzte Request-Objekt auf Objekt bei der entsprechenden Positionsnummer in der Liste
        User user = mUsers.get(position);
        //Datumsformat, um die Zeit hübsch anzuzeigen
        DateFormat format = SimpleDateFormat.getTimeInstance();

        //Check zur Vermeidung von NullPointern, weiß nicht ob notwendig
        if (user != null) {
            //Setze GUI-Elemente auf Element im Holder
            TextView vornameView = viewHolder.userVorname;
            TextView nachnameView = viewHolder.userNachname;
            vornameView.setText(user.getVorname());
            nachnameView.setText(user.getNachname());
            TextView gruppeView = viewHolder.userGruppe;
            gruppeView.setText(user.getGruppe());
        }
    }

    //Liefert Anzahl der Elemente
    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    //Klasse zur Definition der Anzeige-Elemente in der Liste, durch die die Daten in der Liste dargestellt werden
    class ViewHolder extends RecyclerView.ViewHolder {
        //Textfeld für Vorname
        TextView userVorname;
        //Textfeld für Nachname
        TextView userNachname;
        //Textfeld für Gruppe
        TextView userGruppe;

        //Konstruktor
        ViewHolder(View itemView) {
            super(itemView);

            //Setze die oben genannten Elemente auf die Layout-Elemente, definiert in res\layout\request_item.xml
            userVorname = itemView.findViewById(R.id.user_vorname);
            userNachname = itemView.findViewById(R.id.user_nachname);
            userGruppe = itemView.findViewById(R.id.user_gruppe);
        }
    }
}


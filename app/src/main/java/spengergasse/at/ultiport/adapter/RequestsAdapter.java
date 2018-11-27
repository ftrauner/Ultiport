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

public class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView requestTextView;
        public TextView requestTimeView;

        public ViewHolder(View itemView) {
            super(itemView);

            requestTextView = (TextView) itemView.findViewById(R.id.request_Text);
            requestTimeView = (TextView) itemView.findViewById(R.id.request_Time);
        }
    }

    private List<TransportRequest> mRequests;

    public RequestsAdapter(List<TransportRequest> requests){
        mRequests = requests;
    }

    @Override
    public RequestsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);


        View requestView = inflater.inflate(R.layout.item_request, parent, false);

        return new ViewHolder(requestView);
    }

    @Override
    public void onBindViewHolder(RequestsAdapter.ViewHolder viewHolder, int position) {
        TransportRequest request = mRequests.get(position);
        SimpleDateFormat format = new SimpleDateFormat("d.M.Y H:mm");

        if(request != null) {
            TextView requestTextView = viewHolder.requestTextView;
            requestTextView.setText(request.getReqText());
            TextView requestTimeView = viewHolder.requestTimeView;
            requestTimeView.setText(format.format(request.getReqTime()));
        }
    }

    @Override
    public int getItemCount() {
        return mRequests.size();
    }
}


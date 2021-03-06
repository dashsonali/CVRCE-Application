package com.devhub.official.cvrceapplication.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.devhub.official.cvrceapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Adapter_Resolved_Complaints extends RecyclerView.Adapter<Adapter_Resolved_Complaints.MyViewHolder> {
    private Context context;
    LayoutInflater inflater;
    ArrayList<JSONObject> items;


    public Adapter_Resolved_Complaints(Context myContext, ArrayList<JSONObject> items){
        context = myContext;
        this.items = items;
        inflater = LayoutInflater.from(this.context);


    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = inflater.inflate(R.layout.element_resolved_complaint,parent,false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Log.d("fuck it", "onBindViewHolder: " + "Oh fuck its not comming here");
        String issued_by,description,title,solved_by,solved_at,reg_id,issued_at,complaint_id;
        try {
            issued_at = "issue date: " + items.get(position).getString("created_at");
            description = items.get(position).getString("description");
            title = items.get(position).getString("title");
            issued_by = "Name: " + items.get(position).getString("first_name").concat(" " + items.get(position).getString("last_name"));
            solved_by = "solved by: " + items.get(position).getString("solved_by");
            solved_at = "Resolve date: " + items.get(position).getString("solved_at");
            reg_id = "Reg id: " + items.get(position).getString("issued_by");
            complaint_id = "Complaint id: " + items.get(position).getString("complaint_id");

            holder.titleTextView.setText(title);
            holder.regTextView.setText(reg_id);
            holder.solvedByTextView.setText(solved_by);
            holder.issuedByTextView.setText(issued_by);
            holder.atTextView.setText(issued_at);
            holder.descriptionTextView.setText(description);
            holder.solvedAtTextView.setText(solved_at);
            holder.complaint_id_view.setText(complaint_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView titleTextView;
        public TextView descriptionTextView;
        public TextView atTextView;
        public TextView solvedByTextView;
        public TextView issuedByTextView;
        public TextView solvedAtTextView;
        public TextView regTextView;
        public TextView complaint_id_view;


        public MyViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView)itemView.findViewById(R.id.complaint_title);
            descriptionTextView = (TextView)itemView.findViewById(R.id.complaint_description);
            atTextView = (TextView)itemView.findViewById(R.id.complaint_created_at);
            solvedAtTextView = (TextView)itemView.findViewById(R.id.solved_at);
            solvedByTextView = (TextView)itemView.findViewById(R.id.solved_by);
            issuedByTextView = (TextView)itemView.findViewById(R.id.complaint_posted_by);
            regTextView = (TextView)itemView.findViewById(R.id.registration_id);
            complaint_id_view = (TextView)itemView.findViewById(R.id.complaint_id);






        }
    }
}

package com.devhub.official.cvrceapplication.Fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.devhub.official.cvrceapplication.Adapters.Adapter_Complaints;
import com.devhub.official.cvrceapplication.Globals.Globals;
import com.devhub.official.cvrceapplication.HomeActivity;
import com.devhub.official.cvrceapplication.MainActivity;
import com.devhub.official.cvrceapplication.R;
import com.devhub.official.cvrceapplication.SharedPrefManager;
import com.devhub.official.cvrceapplication.StudentHomeActivity;
import com.devhub.official.cvrceapplication.URLs;
import com.devhub.official.cvrceapplication.models.UserModel;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;


public class FragmentIndividual extends Fragment  {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    JSONObject complaints_data;
    String serverAddress;
    Globals global;
    static RequestQueue myQueue;
    SwipeRefreshLayout swipeRefreshLayout;

    public FragmentIndividual() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StudentHomeActivity activity = (StudentHomeActivity) getActivity();
        complaints_data =  activity.getUserComplains();
        serverAddress = URLs.SERVER_ADDR;
        global = (Globals)activity.getApplication();
        serverAddress = URLs.SERVER_ADDR;
        myQueue = global.getVolleyQueue();



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_complaints, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.complaint_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        callAdapters();

        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getContext(), "Hey I am Here", Toast.LENGTH_SHORT).show();

                updateData();


            }
        });


        return view;

    }

    private void updateData() {
        class FetchData extends AsyncTask<Void,Void,Void>{

            @Override
            protected Void doInBackground(Void... voids) {
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
               // UserModel userModel = new UserModel();
               // URLs.ROOT_URL = "";
                String url = serverAddress.concat("/public/user_complaints.php?user_id="+ SharedPrefManager.getInstance(getActivity()).getUser().getRegid());
                Log.e("url",url);
                JsonObjectRequest request0 = new JsonObjectRequest(Request.Method.GET,url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //Toast.makeText(global, "Student added Successfully", Toast.LENGTH_SHORT).show();
                        complaints_data = response;
                       // Log.d("Chutiya data dekh", "onResponse: " + response);
                        //mAdapter.notifyDataSetChanged();
                        callUpdatedAdapters(complaints_data);
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(getContext(), "Updated", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Bhai Bhai",error.toString());
                        Toast toast = Toast.makeText(getContext(), "in indi Fragements"+error.getMessage(), Toast.LENGTH_LONG);
                        toast.show();
                    }
                });
                //Add the first request in the queue
                myQueue.add(request0);
            }
        }
        FetchData fetchData = new FetchData();
        fetchData.execute();
    }

    private void callAdapters(){
        // specify an adapter (see also next example)
        StudentHomeActivity activity = (StudentHomeActivity) getActivity();
        Context context = (StudentHomeActivity) getContext();
        complaints_data =  activity.getUserComplains();
        mAdapter = new Adapter_Complaints(complaints_data,activity,context);
        //Log.i("hagga",complaints_data.toString());

        mRecyclerView.setAdapter(mAdapter);
    }
    private void callUpdatedAdapters(JSONObject complaints) {
        Log.e("Hehe ho gya sayad!!",complaints.toString());
        StudentHomeActivity activity = (StudentHomeActivity) getActivity();
        Context context = (StudentHomeActivity) getContext();
        mAdapter = new Adapter_Complaints(complaints,activity,context);
        //Log.i("hagga",complaints_data.toString());

        mRecyclerView.setAdapter(mAdapter);
    }
    @Override
    public void onResume() {
        super.onResume();
        updateData();
        mAdapter.notifyDataSetChanged();
    }
}

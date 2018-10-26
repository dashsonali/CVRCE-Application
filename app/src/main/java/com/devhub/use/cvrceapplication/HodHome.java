package com.devhub.use.cvrceapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.devhub.use.cvrceapplication.Fragments.FragmentExam;
import com.devhub.use.cvrceapplication.Fragments.FragmentFood;
import com.devhub.use.cvrceapplication.Fragments.FragmentHostel;
import com.devhub.use.cvrceapplication.Fragments.FragmentIndividual;
import com.devhub.use.cvrceapplication.Fragments.FragmentInstitute;
import com.devhub.use.cvrceapplication.Fragments.FragmentPlacemnt;
import com.devhub.use.cvrceapplication.Fragments.HodFragmentFood;
import com.devhub.use.cvrceapplication.Fragments.MentorFragmentAcademics;
import com.devhub.use.cvrceapplication.Fragments.MentorFragmentExam;
import com.devhub.use.cvrceapplication.Fragments.MentorFragmentFood;
import com.devhub.use.cvrceapplication.Fragments.MentorFragmentHostel;
import com.devhub.use.cvrceapplication.Fragments.MentorFragmentIndividual;
import com.devhub.use.cvrceapplication.Fragments.MentorFragmentInstitute;
import com.devhub.use.cvrceapplication.Fragments.MentorFragmetnPlacement;
import com.devhub.use.cvrceapplication.Globals.Globals;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class HodHome extends AppCompatActivity {
    private JSONObject userComplains,hostelComplains,instiComplains,foodComplains,examComplains,placementComplains,academicsComplaints;
    String choose,first_name,last_name;
    String mentorId,department;

    EditText studentEditText;
    static RequestQueue myQueue;
    Globals global;
    Context context;
    String serverAddress;
    String addUrl;
    ProgressDialog progressDialog;
    String name;
    public JSONObject getAcademicsComplaints(){
        return academicsComplaints;
    }
    public JSONObject getUserComplains(){
        return userComplains;
    }
    public JSONObject getHostelComplains(){
        return hostelComplains;
    }
    public JSONObject getInstiComplains(){
        return instiComplains;
    }
    public JSONObject getFoodComplains(){
        return foodComplains;
    }
    public JSONObject getPlacementComplains(){
        return placementComplains;
    }
    public JSONObject getExamComplains(){
        return examComplains;
    }
    public String getFirst_name(){return first_name;}
    public String getLast_name(){return last_name;}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hod_login_new);
        Intent intent = getIntent();
        choose = intent.getStringExtra("choose");
        mentorId = intent.getStringExtra("mentorId");
        department=intent.getStringExtra("department");

        first_name = intent.getStringExtra("name");
        last_name = "Mentor";
        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        context = this;
        global = (Globals)this.getApplication();
        serverAddress = URLs.SERVER_ADDR;
        myQueue = global.getVolleyQueue();
        Log.e("CHOOSE",choose);

        //addFragment();
        progressDialog = new ProgressDialog(this);


        successCallback();
    }
    //public String getMentorId(){
     //   return mentorId;
   // }

    public void successCallback(){
        class FetchData extends AsyncTask<Void,Void,Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                progressDialog.dismiss();
                Log.d("inside", "successCallback: " + "inside the method"+" deprt = "+department+"mentorId"+mentorId);
                String url_user_complaints = serverAddress.concat("/public/hod_individual_complaints.php").concat("?mentor_id=").concat(mentorId);
                String url_hostel_complaints = serverAddress.concat("/public/hod_hostel_complaints.php").concat("?mentor_id=").concat(mentorId);
                String url_insti_complaints = serverAddress.concat("/public/hod_institute_complaints.php").concat("?mentor_id=").concat(mentorId);
                String url_exam_complaints = serverAddress.concat("/public/hod_exam_complaints.php").concat("?mentor_id=").concat(mentorId);
                String url_food_complaints = serverAddress.concat("/public/hod_food_complaints.php").concat("?mentor_id=").concat(mentorId);
                String url_academics_complaints = serverAddress.concat("/public/hod_academics_complaints.php").concat("?mentor_id=").concat(mentorId);

                String url_placement_complaints = serverAddress.concat("/public/hod_placement_complaints.php").concat("?mentor_id=").concat(mentorId);
                Log.e("HOSTEL LINK Hod",url_food_complaints);
                final JsonObjectRequest request4 = new JsonObjectRequest(Request.Method.GET,url_insti_complaints,null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        instiComplains = response;
                        Log.e("institute complaint", "onResponse: " + instiComplains);
                        addFragment();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toast = Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG);
                        Log.e("ERROR IN MENTOR INSI",error.getMessage());
                        toast.show();
                    }
                }) ;



                final JsonObjectRequest request3 = new JsonObjectRequest(Request.Method.GET,url_hostel_complaints,null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        hostelComplains = response;
                        Log.d("hostelcomplaints", "onResponse: " + hostelComplains);
                        myQueue.add(request4 );
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toast = Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG);
                        Log.e("ERROR IN HOSTEL ",error.getMessage());
                        toast.show();
                    }
                }) ;


                final JsonObjectRequest request2 = new JsonObjectRequest(Request.Method.GET,url_user_complaints,null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        userComplains = response;
                        Log.d("usercomplaints", "onResponse: " + userComplains);
                        myQueue.add(request3 );


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toast = Toast.makeText(context, error.getMessage() , Toast.LENGTH_LONG);
                        Log.e("ERROR IN MENTOR USER",error.getMessage());
                        toast.show();
                    }
                }) ;
                final JsonObjectRequest request5 = new JsonObjectRequest(Request.Method.GET,url_food_complaints,null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        foodComplains = response;
                        //   Log.d("usercomplaints", "onResponse: " + userComplains);
                        myQueue.add(request2 );


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toast = Toast.makeText(context, error.getMessage() , Toast.LENGTH_LONG);
                        Log.e("ERROR IN MENTOR FOOD",error.getMessage());
                        toast.show();
                    }
                }) ;
                final JsonObjectRequest request6 = new JsonObjectRequest(Request.Method.GET,url_exam_complaints,null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        examComplains = response;
                        //  Log.d("usercomplaints", "onResponse: " + userComplains);
                        myQueue.add(request5 );


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toast = Toast.makeText(context, error.getMessage() , Toast.LENGTH_LONG);
                        Log.e("ERROR IN MENTOR EXAM",error.getMessage());
                        toast.show();
                    }
                }) ;
                final JsonObjectRequest request7 = new JsonObjectRequest(Request.Method.GET,url_placement_complaints,null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        placementComplains = response;
                        //Log.d("usercomplaints", "onResponse: " + userComplains);
                        myQueue.add(request6);


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toast = Toast.makeText(context, error.getMessage() , Toast.LENGTH_LONG);
                        Log.e("ERROR IN PLCMNT",error.getMessage());
                        toast.show();
                    }
                }) ;
                final JsonObjectRequest request8 = new JsonObjectRequest(Request.Method.GET,url_academics_complaints,null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        academicsComplaints = response;
                        //  Log.d("usercomplaints", "onResponse: " + userComplains);
                        myQueue.add(request7 );


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toast = Toast.makeText(context, error.getMessage() , Toast.LENGTH_LONG);
                        Log.e("ERROR IN academics",error.getMessage());
                        toast.show();
                    }
                }) ;
                myQueue.add(request8);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.setMessage("Loading please wait....");
                progressDialog.show();
            }
        }

        FetchData fetchData = new FetchData();
        fetchData.execute();

    }
    private  void addFragment(){
        FragmentManager fragmentManager
                = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction
                = fragmentManager.beginTransaction();
        if(choose.equals("hostel"))
        {
            HodFragmentFood fragment = new HodFragmentFood();
            fragment.SetMntr(mentorId);
            fragmentTransaction.add(R.id.container,fragment);
            fragmentTransaction.commit();
        }else if(choose.equals("food"))
        {
            HodFragmentFood fragment = new HodFragmentFood();
            fragment.SetMntr(mentorId);
            fragmentTransaction.add(R.id.container,fragment);
            fragmentTransaction.commitAllowingStateLoss();
        }else if(choose.equals("other"))
        {
            HodFragmentFood fragment = new HodFragmentFood();
            fragment.SetMntr(mentorId);
            fragmentTransaction.add(R.id.container,fragment);
            fragmentTransaction.commitAllowingStateLoss();
        }
        else if(choose.equals("exam"))
        {
            HodFragmentFood fragment = new HodFragmentFood();
            fragment.SetMntr(mentorId);
            fragmentTransaction.add(R.id.container,fragment);
            fragmentTransaction.commitAllowingStateLoss();
        }
        else if(choose.equals("placement"))
        {
            HodFragmentFood fragment = new HodFragmentFood();
            fragment.SetMntr(mentorId);
            fragmentTransaction.add(R.id.container,fragment);
            fragmentTransaction.commitAllowingStateLoss();
        }else if(choose.equals("dsw"))
        {
            HodFragmentFood fragment = new HodFragmentFood();
            fragment.SetMntr(mentorId);
            Log.e("view err","in hodhome");

            fragmentTransaction.add(R.id.container,fragment);
            fragmentTransaction.commitAllowingStateLoss();
        }
        else if(choose.equals("academics"))
        {
            HodFragmentFood fragment = new HodFragmentFood();
            fragment.SetMntr(mentorId);
            fragmentTransaction.add(R.id.container,fragment);
            fragmentTransaction.commitAllowingStateLoss();
        }




    }
}
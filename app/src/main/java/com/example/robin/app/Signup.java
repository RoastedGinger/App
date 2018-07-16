package com.example.robin.app;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by robin on 13/7/18.
 */

public class Signup extends Fragment {

    String Server_url = "https://photogenic0001.000webhostapp.com/photogenic/photogenic1.0/createaccount.php";
    Button button;
    Spinner spinner;
    ImageView imageView,plock1,plock0;
    EditText username,password,repassword,phno;
    ArrayAdapter<CharSequence> adapter;
    String gender,un,ps,repas,phn;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.signup,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        username = getActivity().findViewById(R.id.username1);
        imageView = getActivity().findViewById(R.id.show);
        password = getActivity().findViewById(R.id.password1);
        repassword = getActivity().findViewById(R.id.rpassword1);
        phno = getActivity().findViewById(R.id.phno1);
        button = getActivity().findViewById(R.id.signup1);
        plock1 = getActivity().findViewById(R.id.plock);
        plock0 = getActivity().findViewById(R.id.plock0);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                gender = spinner.getSelectedItem().toString();
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                imageView.setVisibility(View.VISIBLE);
                plock0.setVisibility(View.INVISIBLE);
                plock1.setVisibility(View.VISIBLE);
                open();
            }
        });

    }


    public void open(){
        StringRequest request = new StringRequest(Request.Method.POST, Server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getActivity(),response,Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getActivity(),MainActivity.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String voll = error.toString();
                Toast.makeText(getActivity(),voll,Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                un = username.getText().toString();
                ps = password.getText().toString();
                repas = repassword.getText().toString();
                phn = phno.getText().toString();
                params.put("name",un);
                params.put("password",ps);
                params.put("phno",phn);
                params.put("gender",gender);
                return params;
            }
        };
        MySingleton.getInstance(getActivity()).addToRequestQue(request);

    }
}

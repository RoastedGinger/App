package com.example.robin.app;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.wang.avi.AVLoadingIndicatorView;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class Login extends Fragment  {
    String Server_url = "https://photogenic0001.000webhostapp.com/photogenic/photogenic1.0/login1.php";
     public Button logg;
    EditText username,password;
    String un,ps;
    TextView signup;
    AVLoadingIndicatorView avi;
    ImageView imageView;
    android.support.v4.app.FragmentTransaction fragmentTransaction;
     public ProgressBar p;
     public Login()
     {

     }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_login, container, false);
        return view;
    }


    @Override
   public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view,savedInstanceState);
        avi = getActivity().findViewById(R.id.avi);
        username = getActivity().findViewById(R.id.username1);
        password = getActivity().findViewById(R.id.password1);
        signup = getActivity().findViewById(R.id.signup);
        logg =getActivity().findViewById(R.id.login);
        logg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                avi.show();

                post();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                Signup signin_form = new Signup();
                Login login_page = (Login) fragmentManager.findFragmentByTag("login666");
               fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.remove(login_page);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.add(R.id.cre,signin_form,"signin");
                // getFragmentManager().popBackStackImmediate();
                fragmentTransaction.commit();
            }
        });


    }
    void post()
    {
       StringRequest request = new StringRequest(Request.Method.POST, Server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String res=response.toString().trim();
                        if(!res.equals("0"))
                        {
                            SharedPreferences sharedPreferences=getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.putString("status",res);
                            editor.apply();
                            Toast.makeText(getActivity(), res,Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getActivity(),MainActivity.class);
                            startActivity(intent);
//                            fragmentTransaction.remove(Login.this).commit();
                        }
                        else
                        {
                            avi.hide();
                            Toast.makeText(getActivity(), "invalid user name or password",Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String voll = error.toString();
                Toast.makeText(getActivity(),voll,Toast.LENGTH_LONG).show();
                avi.hide();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                un = username.getText().toString();
                ps = password.getText().toString();
                params.put("name",un);
                params.put("password",ps);
                return params;
            }
        };
        MySingleton.getInstance(getActivity()).addToRequestQue(request);
    }
}

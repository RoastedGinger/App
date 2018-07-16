package com.example.robin.app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class fompart extends AppCompatActivity implements View.OnClickListener{

    Button upload,choose;
    EditText name;
    ImageView image;
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    private final int img=1;
    Bitmap bitmap;
    String part,Server_url = "https://photogenic0001.000webhostapp.com/photogenic/photogenic1.0/updatestatus.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fompart);
        upload = findViewById(R.id.upload1);
        choose = findViewById(R.id.choose1);
        name =  findViewById(R.id.name1);
        spinner = findViewById(R.id.spinner1);
        image = findViewById(R.id.image1);
        choose.setOnClickListener(this);
        upload.setOnClickListener(this);

        adapter = ArrayAdapter.createFromResource(this,R.array.PART, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                part = spinner.getSelectedItem().toString();
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.choose1:
                selectimage();
                break;
            case R.id.upload1:
                uploadImage();
                break;
        }
    }

    private void selectimage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,img);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==img && resultCode==RESULT_OK && data!=null){
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                image.setImageBitmap(bitmap);
                image.setVisibility(View.VISIBLE);
                name.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage()
    {
        StringRequest request = new StringRequest(Request.Method.POST, Server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //JSONObject jsonObject = new JSONObject(response);
                        //String Response = jsonObject.getString("response");
                        Toast.makeText(fompart.this,response,Toast.LENGTH_LONG).show();
                        image.setImageResource(0);
                        image.setVisibility(View.GONE);
                        name.setText("");
                        name.setVisibility(View.GONE);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String voll = error.toString();
                Toast.makeText(fompart.this,voll,Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("name",name.getText().toString());
                params.put("image",imagetoString(bitmap));
                params.put("gender",part);
                return params;
            }
        };
        MySingleton.getInstance(fompart.this).addToRequestQue(request);
    }

    private String imagetoString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imbByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imbByte,Base64.DEFAULT);
    }
}

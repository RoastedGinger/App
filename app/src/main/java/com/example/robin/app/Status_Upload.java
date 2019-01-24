package com.example.robin.app;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.service.media.MediaBrowserService;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.gun0912.tedpicker.ImagePickerActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class Status_Upload extends AppCompatActivity implements View.OnClickListener{

    ImageButton choose,camera;
    EditText name;
    TextView textView,genic_name;
    Button upload;
    ImageView image;
    EditText bodyText,titleText;
    LinearLayout outerLayout,adding_photos;
    private final int img=1;
    Bitmap bitmap;
    TextView question;
    LinearLayout linearLayout;
    String restoredText,Server_url = "https://photogenic0001.000webhostapp.com/photogenic/photogenic1.0/updatestatus.php";

    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int CAMERA_REQUEST = 1888;
    private static final int INTENT_REQUEST_GET_IMAGES = 13;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.status_upload);
        upload = findViewById(R.id.upload);
        choose = findViewById(R.id.choose);
        image = findViewById(R.id.image);
        textView = findViewById(R.id.app_name);
        camera = findViewById(R.id.open_camera);
        outerLayout =  findViewById(R.id.relative);
        question = findViewById(R.id.question);
        linearLayout = findViewById(R.id.add);
        genic_name = findViewById(R.id.genic_name);
        adding_photos = findViewById(R.id.add);
        name =findViewById(R.id.name);
        choose.setOnClickListener(this);
        camera.setOnClickListener(this);
        upload.setOnClickListener(this);
        question.setOnClickListener(this);
        Typeface face1 = Typeface.createFromAsset(getAssets(),"font/Bilbo-Regular.ttf");
        Typeface face = Typeface.createFromAsset(getAssets(),"font/Bilbo Swash Caps.ttf");
        textView.setTypeface(face);
        genic_name.setTypeface(face1);

        question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(adding_photos.getVisibility() == View.GONE)
                linearLayout.setVisibility(View.VISIBLE);
                else
                    linearLayout.setVisibility(View.GONE);
            }
        });

        SharedPreferences prefs = getSharedPreferences("user",MODE_PRIVATE);
         restoredText = prefs.getString("status", null);

    }



    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.choose:
                selectimage();
                break;
            case R.id.upload:
                uploadImage();
                break;
            case R.id.open_camera:
                open_camera();
                break;

        }
    }


    private void open_camera() {

            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new
                        Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }

        }
    }
    private void selectimage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,img);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==img && resultCode==RESULT_OK && data!=null){
            Uri path = data.getData();
            try {
                // ArrayList<Uri> image_uris = data.getParcelableArrayListExtra(ImagePickerActivity.EXTRA_IMAGE_URIS);
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                /*Matrix matrix = new Matrix();
                matrix.postRotate(180);
                Bitmap rotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(),
                        matrix, true);*/

                //    image.setImageBitmap(rotated);
                image.setImageBitmap(bitmap);
                image.setVisibility(View.VISIBLE);
                name.setVisibility(View.VISIBLE);

            } catch (IOException e) {
                e.printStackTrace();
            }

           /* Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                public void onGenerated(Palette palette) {
                    final Palette.Swatch vibrantSwatch = palette.getDarkVibrantSwatch();
                    if (vibrantSwatch != null) {
                        outerLayout.setBackgroundColor(vibrantSwatch.getRgb());
                        upload.setBackgroundColor(vibrantSwatch.getTitleTextColor());
                        bodyText.setTextColor(vibrantSwatch.getBodyTextColor());
                        choose.setBackgroundColor(vibrantSwatch.getTitleTextColor());
                        camera.setBackgroundColor(vibrantSwatch.getTitleTextColor());
                        bodyText.setBackgroundColor(vibrantSwatch.getTitleTextColor());
                    }
                }
            }); */
        }

        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            image.setImageBitmap(photo);
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
                         Toast.makeText(Status_Upload.this,response,Toast.LENGTH_LONG).show();
                        image.setImageResource(0);
                        image.setVisibility(View.GONE);
                        name.setText("");
                        name.setVisibility(View.GONE);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String voll = error.toString();
                //   Log.e("no network",voll);
                if(voll.equals("com.android.volley.NoConnectionError: java.net.UnknownHostException: Unable to resolve host \"photogenic0001.000webhostapp.com\": No address associated with hostname"))
                    Toast.makeText(Status_Upload.this,"No Internet Connection",Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("name",name.getText().toString());
                params.put("image",imagetoString(bitmap));
                params.put("phone",restoredText);
                return params;
            }
        };
        MySingleton.getInstance(Status_Upload.this).addToRequestQue(request);
    }

    private String imagetoString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imbByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imbByte,Base64.DEFAULT);
    }
}
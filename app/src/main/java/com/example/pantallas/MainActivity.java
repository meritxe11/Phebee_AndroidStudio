package com.example.pantallas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText et1;
    EditText et2;
    EditText et3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        et3 = findViewById(R.id.et3);
    }

    public void login(View v) {
        String username = et1.getText().toString();
        String id = et2.getText().toString();   //password
        String host = et3.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = host + "/login/?student_id=" + id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url,null,
                response -> {
                    aux_login(response,username,id,host);
                }, error -> {
                    Toast notificacion = Toast.makeText(this, "Error", Toast.LENGTH_LONG);
                    notificacion.show();
                }
        );
        queue.add(jsonObjectRequest);
    }

    public void aux_login(JSONObject json, String username, String id, String host) {
        String user;
        try {
            user = json.getJSONArray("students").getJSONObject(0).getString("name");
            System.out.println();

            if(user.equals(username)){
                Intent i = new Intent(this, Menu_page.class);
                i.putExtra("user_name", username);
                i.putExtra("password", id);
                i.putExtra("host", host);

                startActivity(i);
            }else {
                Toast notificacion = Toast.makeText(this, "Username & password don't match", Toast.LENGTH_LONG);
                notificacion.show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}



package com.example.pantallas;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class Menu_page extends AppCompatActivity {

    TextView tv1, tv2;
    EditText et1;
    String username;
    TableLayout taula;
    String id;
    String host;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_page);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        et1 = findViewById(R.id.et1);
        taula = findViewById(R.id.tabla);

        Bundle bundle = getIntent().getExtras();
        username = bundle.getString("user_name");
        tv1.setTextColor(Color.parseColor("#464F63"));
        tv1.setText(username);
        id = bundle.getString("password");
        host = bundle.getString("host");
    }

    public void salir(View v) {
        finish();
    }


    public void ejecutar(View v) {
        String consulta = et1.getText().toString().toLowerCase(Locale.ROOT);
        String idtaula="";
        String constraint = "";
        //Separem idtaula de constraint
        if (consulta.contains("?")) {
            String[] aux = consulta.split("[?]");
            idtaula = aux[0];
            constraint = aux[1];
            System.out.println(idtaula + "....." + constraint);
        } else {
            idtaula = consulta;
        }

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = host + "/" + idtaula + "/?student_id=" + id + "&" + constraint;
        System.out.println(url);
        String finalIdtaula = idtaula;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                response -> {
                    aux_consulta(response, finalIdtaula);
                }, error -> {
            Toast notificacion = Toast.makeText(this, "Error connection", Toast.LENGTH_LONG);
            notificacion.show();
        });
        queue.add(jsonObjectRequest);

        //Modifiquem titol taula, si es un dels casos contemplats
        switch (idtaula) {
            case "timetables":
                tv2.setText("Timetable");
                break;
            case "tasks":
                tv2.setText("Tasks");
                break;
            case "marks":
                tv2.setText("Marks");
                break;
        }
    }

    public void aux_consulta(JSONObject jsonObject, String idtaula){
        JSONArray jsonArray = null;
        try {
            jsonArray = jsonObject.getJSONArray(idtaula);
            crear_tabla(jsonArray, idtaula);
        } catch (JSONException e) {
            Toast notificacio = Toast.makeText(this,"Incorrect order", Toast.LENGTH_LONG);
            notificacio.show();
        }
    }

    protected void crear_tabla(JSONArray jsonArray, String idtaula) {
        try {
            taula.removeAllViews();
            View registro = null;

            switch (idtaula) {
                case "tasks":
                    registro = LayoutInflater.from(this).inflate(R.layout.row_tasks, null, false);
                    TextView colDate1 = registro.findViewById(R.id.colData_T);
                    TextView colSubject1 = registro.findViewById(R.id.colSubj_T);
                    TextView colName1 = registro.findViewById(R.id.colName_T);

                    colDate1.setTextColor(getResources().getColor(R.color.white));
                    colName1.setTextColor(getResources().getColor(R.color.white));
                    colSubject1.setTextColor(getResources().getColor(R.color.white));
                    registro.setBackgroundColor(getResources().getColor(R.color.colorRowOne));

                    colDate1.setText("Data");
                    colSubject1.setText("Subject");
                    colName1.setText("Name");
                    taula.addView(registro);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        registro = LayoutInflater.from(this).inflate(R.layout.row_tasks, null, false);
                        TextView colDate = registro.findViewById(R.id.colData_T);
                        TextView colSubject = registro.findViewById(R.id.colSubj_T);
                        TextView colName = registro.findViewById(R.id.colName_T);
                        if (i % 2 == 0) {
                            registro.setBackgroundColor(getResources().getColor(R.color.colorRowEven));
                        } else {
                            registro.setBackgroundColor(getResources().getColor(R.color.colorRowOdd));
                        }

                        colDate.setText(jsonObject.getString("date"));
                        colSubject.setText(jsonObject.getString("subject"));
                        colName.setText(jsonObject.getString("name"));

                        taula.addView(registro);
                    }
                    break;
                case "timetables":
                    registro = LayoutInflater.from(this).inflate(R.layout.row_timetables, null, false);
                    TextView colDay1 = registro.findViewById(R.id.colDay_Ti);
                    TextView colHour1 = registro.findViewById(R.id.colHour_Ti);
                    TextView colSubj1 = registro.findViewById(R.id.colSubj_Ti);
                    TextView colRoom1 = registro.findViewById(R.id.colRoom_Ti);

                    colDay1.setTextColor(getResources().getColor(R.color.white));
                    colHour1.setTextColor(getResources().getColor(R.color.white));
                    colSubj1.setTextColor(getResources().getColor(R.color.white));
                    colRoom1.setTextColor(getResources().getColor(R.color.white));
                    registro.setBackgroundColor(getResources().getColor(R.color.colorRowOne));

                    colDay1.setText("Day");
                    colHour1.setText("Hour");
                    colSubj1.setText("Subject");
                    colRoom1.setText("Room");
                    taula.addView(registro);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        registro = LayoutInflater.from(this).inflate(R.layout.row_timetables, null, false);
                        TextView colDay = registro.findViewById(R.id.colDay_Ti);
                        TextView colHour = registro.findViewById(R.id.colHour_Ti);
                        TextView colSubj = registro.findViewById(R.id.colSubj_Ti);
                        TextView colRoom = registro.findViewById(R.id.colRoom_Ti);
                        if (i % 2 == 0) {
                            registro.setBackgroundColor(getResources().getColor(R.color.colorRowEven));
                        } else {
                            registro.setBackgroundColor(getResources().getColor(R.color.colorRowOdd));
                        }

                        colDay.setText(jsonObject.getString("day"));
                        colHour.setText(jsonObject.getString("hour"));
                        colSubj.setText(jsonObject.getString("subject"));
                        colRoom.setText(jsonObject.getString("room"));

                        taula.addView(registro);
                    }
                    break;
                case "marks":
                    registro = LayoutInflater.from(this).inflate(R.layout.row_marks, null, false);
                    TextView colSubj2 = registro.findViewById(R.id.colSubj_M);
                    TextView colName2 = registro.findViewById(R.id.colName_M);
                    TextView colMark1 = registro.findViewById(R.id.colMarks_M);

                    colSubj2.setTextColor(getResources().getColor(R.color.white));
                    colName2.setTextColor(getResources().getColor(R.color.white));
                    colMark1.setTextColor(getResources().getColor(R.color.white));
                    registro.setBackgroundColor(getResources().getColor(R.color.colorRowOne));

                    colSubj2.setText("Subject");
                    colName2.setText("Name");
                    colMark1.setText("Mark");
                    taula.addView(registro);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        registro = LayoutInflater.from(this).inflate(R.layout.row_marks, null, false);
                        TextView colSubj = registro.findViewById(R.id.colSubj_M);
                        TextView colName = registro.findViewById(R.id.colName_M);
                        TextView colMark = registro.findViewById(R.id.colMarks_M);
                        if (i % 2 == 0) {
                            registro.setBackgroundColor(getResources().getColor(R.color.colorRowEven));
                        } else {
                            registro.setBackgroundColor(getResources().getColor(R.color.colorRowOdd));
                        }

                        colSubj.setText(jsonObject.getString("subject"));
                        colName.setText(jsonObject.getString("name"));
                        colMark.setText(jsonObject.getString("mark"));

                        taula.addView(registro);
                    }
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}


package com.example.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class login extends AppCompatActivity {
    Button btnEjecutar;
    TextView mostrar, txtUser,txtPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnEjecutar=findViewById(R.id.button);
        txtUser=findViewById(R.id.txtUsuario);
        txtPass=findViewById(R.id.txtPass);
        mostrar=findViewById(R.id.text);
        btnEjecutar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if (txtUser.length()>0 && txtPass.length()>0){
                consumir(txtUser.getText().toString(),txtPass.getText().toString());
                //}
                //else
                //  {mostrar.setText("Llene todas las credenciales");}
            }
        });
    }

    private void consumir(String user, String pass)
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url="https://56e4e2e68623.ngrok.io/login_autentication/"+user+"/"+pass+"";
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if(response.length()>0) {
                            String inf=":(";
                            try {
                                JSONObject Obj = response.getJSONObject(0);
                                inf=(""+Obj.get("users")).toString();
                                Intent intent = new Intent(login.this, MainActivity.class);
                                intent.putExtra("inf",inf );
                               // Bundle b = new Bundle();
                                //b.putString("inf", "asdasdasda");
                                //intent.putExtras(b);
                                startActivity(intent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else
                        {mostrar.setText("asdas");}
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        error.printStackTrace();

                    }
                });

        queue.add(jsonObjectRequest);

    }

}
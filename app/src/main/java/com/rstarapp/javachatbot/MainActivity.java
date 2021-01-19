package com.rstarapp.javachatbot;


import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    RequestQueue requestQueue;
    EditText editText;
    Button button;
    TextView textViewOutput;
    TextToSpeech textToSpeech;
    String url="https://acobot-brainshop-ai-v1.p.rapidapi.com/get?bid=178&key=sX5A2PcYZbsN5EY6&uid=mashape&msg=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestQueue=Volley.newRequestQueue(this);

        textViewOutput = (TextView)findViewById(R.id.txtOutput);
        editText=(EditText)findViewById(R.id.edtUserInput);
        button=(Button)findViewById(R.id.btnSend);
        textToSpeech=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.ENGLISH);
                }
            }
        });
        textToSpeech.speak("Hi",TextToSpeech.QUEUE_FLUSH,null);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt=editText.getText().toString();
                getData(txt);
            }
        });
    }
    private void getData(String txt) {
        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.GET, url + txt, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String data=response.get("cnt").toString();
                    textViewOutput.setText("Bot : "+data);
                    textToSpeech.speak(data,TextToSpeech.QUEUE_FLUSH,null);
                    editText.setText("");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("x-rapidapi-host", "acobot-brainshop-ai-v1.p.rapidapi.com");
                headers.put("x-rapidapi-key", "fe10c04f7bmsh86dfc582f44f399p133f9bjsnfd7189ee1ba9");
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
}
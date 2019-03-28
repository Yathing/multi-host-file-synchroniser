package com.lucky.filesync;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;


/**
 * This activity handles user authentication
 */
public class MyLogin extends AppCompatActivity {

   private EditText username;
   private EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_login);
        username = (EditText) findViewById(R.id.emailView);
        password = (EditText) findViewById(R.id.passwordView);


    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        super.onResume();

        SharedPreferences shared = getSharedPreferences("FileSync", 0);
        String name = (shared.getString("Username", ""));
        String pass = (shared.getString("Password", ""));

        username.setText(name);
        password.setText(pass);

        if (!name.isEmpty() && !pass.isEmpty()){
            JSONObject obj = new JSONObject();
            try {
                obj.put("username", name);
                obj.put("password", pass);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            autoAuth(obj);
        }
    }


    /**
     * @param view
     * execute login
     */
    public void doLogin(View view) {
        JSONObject obj = new JSONObject();

        try {
            obj.put("username", username.getText().toString());
            obj.put("password", password.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        autoAuth(obj);

    }

    /**
     * @param view
     * go to register activity
     */
    public void goRegister(View view) {
        Intent i = new Intent(MyLogin.this, MyRegister.class);
        startActivity(i);
    }

    /**
     * @param obj
     * log user in if details are stored
     */
    public void autoAuth(JSONObject obj){
        AndroidNetworking.post("http://46.101.20.26:3000/users/authenticate")
                .addJSONObjectBody(obj)
                .setContentType("application/json; charset=utf-8")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        try {
                            if ((boolean) response.get("success")){
                                SharedPreferences sp = getSharedPreferences("FileSync", 0);
                                SharedPreferences.Editor Ed=sp.edit();
                                Ed.putString("Username",username.getText().toString() );
                                Ed.putString("Password",password.getText().toString());
                                Ed.apply();
                                Intent i = new Intent(MyLogin.this, FileActivity.class);
                                startActivity(i);
                            }else{
                                String msg = response.get("msg").toString();
                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Toast.makeText(getApplicationContext(), "Whoopsie-Daisy! That's a failed login.", Toast.LENGTH_SHORT).show();
                        System.out.println(error);
                    }
                });

    }
}

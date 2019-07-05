package com.willsdev.moneytransferapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Map;

class NetworkThread extends AsyncTask<String, Void, String>
{
    private Context context;

    public NetworkThread(Context context){
        this.context=context;
    }

    protected String doInBackground(String... arg0) {
        String host = "jdbc:mysql://91.132.103.123:3306/app?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String user = "root";
        String pass = "123123123App";
        DBController controller = new DBController(host, user, pass);
        Map map = null;
        try
        {
            map = controller.getData();
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        Toast.makeText(context, map.toString(), Toast.LENGTH_LONG).show();

        return null;
    }

    protected void onPostExecute(String result) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
}

public class LoginActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText user = findViewById(R.id.login_user);
        EditText pass = findViewById(R.id.login_pass);

        Button login = findViewById(R.id.btn_login);
        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                new NetworkThread(getApplicationContext()).execute("Your URL here");
            }
        });

        Button signup = findViewById(R.id.btn_signup);
        signup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    public void gotoBlah() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }
}

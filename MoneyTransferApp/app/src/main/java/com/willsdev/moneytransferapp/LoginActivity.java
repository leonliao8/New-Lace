package com.willsdev.moneytransferapp;

import android.app.Application;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class RunQuery {
    DBController dbController;
    QueryType queryType;
    Map<String, String> data;

    public RunQuery(DBController dbController, QueryType queryType, Map<String,String> data) {
        this.dbController = dbController;
        this.queryType = queryType;
        this.data = data;
    }
}

enum QueryType {
    LOGIN,GET_WALLETS,TRANSFER,SIGNUP
}

/**
 * <Do in background,
 */
class NetworkThread extends AsyncTask<RunQuery, String, Map>
{
    private Application application;
    private RunQuery runQuery;

    NetworkThread(Application application, RunQuery runQuery){
        this.application=application;
        this.runQuery=runQuery;
    }

    protected Map doInBackground(RunQuery... arg0) {
        Map map = null;
        if(runQuery.dbController==null) {
            String host = "jdbc:mysql://91.132.103.123:3306/app?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            String user = "root";
            String pass = "123123123App";
            runQuery.dbController = new DBController(host,user,pass);
            ((MyApplication)application).dbController = runQuery.dbController;
        }
        switch (runQuery.queryType) {
            case LOGIN:{
                String login_query = "select * from users where login=\""+runQuery.data.get("user")+"\" and pass=\"" + runQuery.data.get("pass") + "\"";
                try
                {
                    int result = runQuery.dbController.get_count(login_query);
                    if(result>0){
                        Intent intent = new Intent(application.getApplicationContext(), MainActivity.class);
                        application.getApplicationContext().startActivity(intent);
                    }
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            case GET_WALLETS:{
                String user_id = runQuery.data.get("user_id");
                String query = "select * from accounts where users_user_id="+user_id;
                try {
                    //map = runQuery.dbController.getData(query);
                } catch (Exception e){}
            }
            case TRANSFER:{
                String user_id = runQuery.data.get("user_id");
                String initial_amt = runQuery.data.get("initial_amt");
                String final_amt = runQuery.data.get("final_amt");
                String query = "update";

            }
            case SIGNUP:{
                Map<String, String> data = runQuery.data;
                String user = data.get("user");
                String pass = data.get("pass");
                String name = data.get("name");
                String country = data.get("country");
                String language = data.get("language");
                String query = "";
            }
        }
        return map;
    }
}

public class LoginActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getApplication();

        String login = ((EditText)findViewById(R.id.login_user)).getText().toString();
        String pass = ((EditText)findViewById(R.id.login_pass)).getText().toString();
        Map<String,String> data = new HashMap<>();
        data.put("user",login);
        data.put("pass",pass);
        final RunQuery runQuery = new RunQuery(null, QueryType.LOGIN, data);

        Button login_btn = findViewById(R.id.btn_login);
        login_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                NetworkThread networkThread = new NetworkThread(getApplication(), runQuery);
                networkThread.execute(runQuery);
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
}

package edu.illinois.cs465.myquizapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.*;

public class history extends AppCompatActivity {
    ListView listView;
    JSONObject obj;
    SharedPreferences pref;
    Button homebtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);
        homebtn = (Button)findViewById(R.id.button_home);
        View.OnClickListener button_back_listener = new View.OnClickListener() {
            public void onClick(View v) {
                gethome();
            }
        };
        homebtn.setOnClickListener(button_back_listener);
        pref = getSharedPreferences("game_profile",Context.MODE_PRIVATE);
        ArrayList<Match> arrayOfMatches = new ArrayList<Match>();
        MatchAdapter adapter = new MatchAdapter(this, arrayOfMatches);
        int counter = 0;
        int total = pref.getInt("counter",-1);


        Map<String, ?> keys = pref.getAll();

        for(Map.Entry<String, ?> entry : keys.entrySet()){
            if(entry.getKey().equals("counter")) {
                continue;
            }

            String i = (String)entry.getValue();
            try {
                JSONObject obj = new JSONObject(i);
                String info = obj.getString("date") + " " + obj.getString("myTeamName") + " vs "+ obj.getString("opponentTeamName");
                String score = obj.getString("myTeamScore") + " - " + obj.getString("opponentTeamScore");
                adapter.add(new Match(info, score));
            }
            catch(JSONException e){
                System.out.print("JSON object fail");
            }
        }

        listView = (ListView) findViewById(R.id.mobile_list);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent mIntent = new Intent(history.this, StatsActivity.class);
                mIntent.putExtra("pos", Integer.toString(position));
                startActivity(mIntent);
            }

        });

        listView.setAdapter(adapter);
    }
    public void gethome(){
        Intent mIntent = new Intent(history.this, MainActivity.class);
        startActivity(mIntent);
    }
}

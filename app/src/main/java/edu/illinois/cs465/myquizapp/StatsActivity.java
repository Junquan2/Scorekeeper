package edu.illinois.cs465.myquizapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class StatsActivity extends AppCompatActivity {

    JSONObject match_data;
    private TextView textview_datetime;
    private TextView textview_homescore;
    private TextView textview_awayscore;
    private TextView textview_free;
    private TextView textview_twopoints;
    private TextView textview_threepoints;
    private TextView textview_miss;
    private TextView textview_total;
    private TextView textview_textfree;
    private TextView textview_texttwopoints;
    private TextView textview_textthreepoints;
    private TextView textview_textmiss;
    private TextView textview_texttotal;
    SharedPreferences pref;
    Button backbtn;
    Button deletebtn;
    String k;
    Boolean is_myteam = true;
    RadioGroup rGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        backbtn = (Button)findViewById(R.id.button_back);
        View.OnClickListener button_back_listener = new View.OnClickListener() {
            public void onClick(View v) {
                getback();
            }
        };
        backbtn.setOnClickListener(button_back_listener);

        deletebtn = (Button)findViewById(R.id.button_delete);
        View.OnClickListener button_del_listener = new View.OnClickListener() {
            public void onClick(View v) {
                delete();
            }
        };
        deletebtn.setOnClickListener(button_del_listener);


        pref = getSharedPreferences("game_profile", Context.MODE_PRIVATE);

        rGroup = (RadioGroup) findViewById(R.id.rdogrp);


        Button home = findViewById(R.id.homeTeam);

        home.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                System.out.println("onclick home");
                is_myteam = true;
                int radioButtonID = rGroup.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) rGroup.findViewById(radioButtonID);
                String selectedtext = (String) radioButton.getText().toString();;
                if(selectedtext.equals("All")) {
                    set_stats("all_quarters",is_myteam);
                }
                else if(selectedtext.equals("qtr1")) {
                    set_stats("first_quarter",is_myteam);
                }
                else if(selectedtext.equals("qtr2")) {
                    set_stats("second_quarter",is_myteam);
                }
                else if(selectedtext.equals("qtr3")) {
                    set_stats("third_quarter",is_myteam);
                }
                else {
                    set_stats("fourth_quarter",is_myteam);
                }
            }
        });

        Button away = findViewById(R.id.awayTeam);

        away.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                System.out.println("onclick away");
                is_myteam = false;
                int radioButtonID = rGroup.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) rGroup.findViewById(radioButtonID);
                String selectedtext = (String) radioButton.getText().toString();;

                System.out.println("selectedtext");
                System.out.println(selectedtext);
                if(selectedtext.equals("All")) {
                    set_stats("all_quarters",is_myteam);
                }
                else if(selectedtext.equals("qtr1")) {
                    set_stats("first_quarter",is_myteam);
                }
                else if(selectedtext.equals("qtr2")) {
                    set_stats("second_quarter",is_myteam);
                }
                else if(selectedtext.equals("qtr3")) {
                    set_stats("third_quarter",is_myteam);
                }
                else {
                    set_stats("fourth_quarter",is_myteam);
                }

            }
        });

        rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
                boolean isChecked = checkedRadioButton.isChecked();
                String checkedTabStr = checkedRadioButton.getText().toString();
                if(checkedTabStr.equals("All")) {
                    set_stats("all_quarters",is_myteam);
                }
                else if(checkedTabStr.equals("qtr1")) {
                    set_stats("first_quarter",is_myteam);
                }
                else if(checkedTabStr.equals("qtr2")) {
                    set_stats("second_quarter",is_myteam);
                }
                else if(checkedTabStr.equals("qtr3")) {
                    set_stats("third_quarter",is_myteam);
                }
                else {
                    set_stats("fourth_quarter",is_myteam);
                }
            }
        });

        textview_datetime = findViewById(R.id.title);
        textview_homescore = findViewById(R.id.homeScore);
        textview_awayscore = findViewById(R.id.awayScore);
        textview_free = findViewById(R.id.FreeThrow);
        textview_twopoints = findViewById(R.id.Score2pts);
        textview_threepoints = findViewById(R.id.Score3pts);
        textview_miss = findViewById(R.id.missCount);
        textview_total = findViewById(R.id.totalPoints);

        textview_textfree = findViewById(R.id.TextFreeThrow);
        textview_texttwopoints = findViewById(R.id.Text2pts);
        textview_textthreepoints = findViewById(R.id.Text3pts);
        textview_textmiss = findViewById(R.id.miss);
        textview_texttotal = findViewById(R.id.total);


        k=null;
        try {
//            match_data = new JSONObject(getIntent().getStringExtra("match_data"));
            int counter = 0;
            int pos = Integer.parseInt(getIntent().getStringExtra("pos"));
            System.out.println(pos);
            Map<String, ?> keys = pref.getAll();

            for(Map.Entry<String, ?> entry : keys.entrySet()){
                if(counter == pos){
                    if(((String)entry.getKey()).equals("counter")){
                        continue;
                    }
                        String i = (String)entry.getValue();
                        match_data = new JSONObject(i);
                        System.out.println("i");
                        System.out.println(i);
                        System.out.println("counter");
                        System.out.println(counter);
                        System.out.println(match_data);
                        k = (String) entry.getKey();
                        break;

                }
                counter++;
            }
        }
        catch(JSONException e) {
            System.out.println("error");
        }

        set_stats("all_quarters", is_myteam);
    }


    private void set_stats(String which_quarter, Boolean is_myteam) {
        String miss;
        String free;
        String two_points;
        String three_points;
        String total;
        String miss2;
        String free2;
        String two_points2;
        String three_points2;
        String total2;
        String overall_score_team1;
        String overall_score_team2;

        String datetime;

        try {
            datetime = match_data.getString("date");
//            System.out.println(datetime);

            overall_score_team1 = match_data.getString("myTeamScore");
            overall_score_team2 = match_data.getString("opponentTeamScore");

//            System.out.println(overall_score_team1);

            String tmp = match_data.getString("match_data");
            JSONObject t2 = new JSONObject(tmp);
            JSONObject quarter = (JSONObject) t2.get(which_quarter);
//            System.out.println(quarter.toString());

            miss = quarter.getString("miss");
            free = quarter.getString("free");
            two_points = quarter.getString("two_points");
            three_points = quarter.getString("three_points");
            total = quarter.getString("total");

            miss2 = quarter.getString("miss2");
            free2 = quarter.getString("free2");
            two_points2 = quarter.getString("two_points2");
            three_points2 = quarter.getString("three_points2");
            total2 = quarter.getString("total2");

            textview_datetime.setText(datetime);
            textview_homescore.setText(overall_score_team1);
            textview_awayscore.setText(overall_score_team2);


//            System.out.println(is_myteam);
            if (is_myteam){
                textview_free.setText(free);
                textview_textfree.setTextColor(Color.parseColor("#FF8800"));
                textview_twopoints.setText(two_points);
                textview_texttwopoints.setTextColor(Color.parseColor("#FF8800"));
                textview_threepoints.setText(three_points);
                textview_textthreepoints.setTextColor(Color.parseColor("#FF8800"));
                textview_miss.setText(miss);
                textview_textmiss.setTextColor(Color.parseColor("#FF8800"));
                textview_total.setText(total);
                textview_texttotal.setTextColor(Color.parseColor("#FF8800"));
            }else{
                textview_free.setText(free2);
                textview_textfree.setTextColor(Color.parseColor("#87CEEB"));
                textview_twopoints.setText(two_points2);
                textview_threepoints.setText(three_points2);
                textview_miss.setText(miss2);
                textview_total.setText(total2);
                textview_texttwopoints.setTextColor(Color.parseColor("#87CEEB"));
                textview_textthreepoints.setTextColor(Color.parseColor("#87CEEB"));
                textview_textmiss.setTextColor(Color.parseColor("#87CEEB"));
                textview_texttotal.setTextColor(Color.parseColor("#87CEEB"));
            }


            TextView temp  = (TextView) findViewById(R.id.homeTeam);
            TextView temp2  = (TextView) findViewById(R.id.awayTeam);
            temp.setText(match_data.getString("myTeamName"));
            temp2.setText(match_data.getString("opponentTeamName"));
        }
        catch (JSONException e) {
            System.out.println("Unable to fetch data from json object");
        }


    }
    public void getback(){
        Intent mIntent = new Intent(StatsActivity.this, history.class);
        startActivity(mIntent);
    }
    public void delete(){
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(k);
        editor.commit();
        Intent mIntent = new Intent(StatsActivity.this, history.class);
        startActivity(mIntent);

    }
}

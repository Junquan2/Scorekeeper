package edu.illinois.cs465.myquizapp;

//package com.tutlane.datepickerexample;
import android.support.v7.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Calendar;
import android.widget.CheckBox;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import java.io.File;
import android.widget.Toast;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;



import org.json.JSONObject;
import org.json.JSONException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class GameProfileActivity extends AppCompatActivity {
    DatePickerDialog picker;
    EditText eText;
    EditText homeTeamName;
    EditText homeTeamScore;
    EditText visitorTeamName;
    EditText visitorTeamScore;
//    CheckBox chk;
//    CheckBox chk2;
//    Boolean isfirstChecked;

    Button btnGet;
    TextView tvw;
    Button saveBtn;
    FileOutputStream fstream;
    Intent intent;

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    String myTeamName;
    int myTeamScore;
    String opponentTeamName;
    int opponentTeamScore;
    int my_totalscore;
    int opponent_total_score;

//    private CheckBox checkBoxA, checkBoxB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_profile);
        eText=(EditText) findViewById(R.id.dateEntry);
        homeTeamName =(EditText) findViewById(R.id.homeTeamNameEntry);
        homeTeamScore = (EditText) findViewById(R.id.homeTeamScoreEntry);
        visitorTeamName = (EditText) findViewById(R.id.visitorTeamNameEntry);
        visitorTeamScore = (EditText) findViewById(R.id.visitorTeamScoreEntry);
        String date_n = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
        TextView date  = (TextView) findViewById(R.id.dateEntry);
        date.setText(date_n);
        eText.setInputType(InputType.TYPE_NULL);
        String intentvar2 = getIntent().getStringExtra("match_data");
        try{
            JSONObject tm = new JSONObject(intentvar2);
            my_totalscore = tm.getInt("overall_score_team1");
            opponent_total_score = tm.getInt("overall_score_team2");
        }
        catch (JSONException e){
            my_totalscore = 0;
        }

        eText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(GameProfileActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                eText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });


//        chk = (CheckBox) findViewById(R.id.homeTeamCk1);
//        chk2 = (CheckBox) findViewById(R.id.homeTeamCk2);
        TextView temp  = (TextView) findViewById(R.id.homeTeamScoreEntry);
        TextView temp2  = (TextView) findViewById(R.id.visitorTeamScoreEntry);
        temp.setText(Integer.toString(my_totalscore));
        temp2.setText(Integer.toString(opponent_total_score));
//        chk.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                boolean checked = ((CheckBox) v).isChecked();
//                // Check which checkbox was clicke1
//
//                if (checked){
//                    isfirstChecked = Boolean.TRUE;
//                    Log.d("Added click 1", "ok");
//                    chk2.setChecked(false);
//
//
//
//                }else{
//                }
//            }
//        });
//        isfirstChecked = Boolean.FALSE;
//        chk2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                TextView temp  = (TextView) findViewById(R.id.visitorTeamScoreEntry);
////                TextView temp2  = (TextView) findViewById(R.id.homeTeamScoreEntry);
//                boolean checked = ((CheckBox) v).isChecked();
//                // Check which checkbox was clicke1
//                if (checked){
//                    isfirstChecked = Boolean.TRUE;
//                    Log.d("Added click 2 ", "ok");
//                    chk.setChecked(false);
//
////                    temp.setText(Integer.toString(my_totalscore));
////                    temp2.setText("");
//
//                }else{
////                    temp.setText("");
//                }
//            }
//        });



        String FILENAME = "game_profile";
        saveBtn = (Button)findViewById(R.id.save);
        pref = getSharedPreferences(FILENAME,MODE_PRIVATE);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myTeamName = homeTeamName.getText().toString();
                myTeamScore = Integer.parseInt( homeTeamScore.getText().toString() );
                opponentTeamName = visitorTeamName.getText().toString();
                opponentTeamScore = Integer.parseInt( visitorTeamScore.getText().toString() );
//                Log.d("is it checked", Boolean.toString(isfirstChecked));
//                if(isfirstChecked){
//                    opponentTeamName = homeTeamName.getText().toString();
//                    myTeamName = visitorTeamName.getText().toString();
//                    if (homeTeamScore.getText().toString().equals("")){
//                        opponentTeamScore = 0;
//                    }else{
//                        opponentTeamScore = Integer.parseInt( homeTeamScore.getText().toString() );
//                    }
//
//                    if (visitorTeamScore.getText().toString().equals("")){
//                        myTeamScore = 0;
//                    }else{
//                        myTeamScore = Integer.parseInt( visitorTeamScore.getText().toString() );
//                    }
//                }else{
//                    myTeamName = homeTeamName.getText().toString();
//                    opponentTeamName = visitorTeamName.getText().toString();
//                    if (visitorTeamScore.getText().toString().equals("")){
//                        opponentTeamScore = 0;
//                    }else{
//                        opponentTeamScore = Integer.parseInt( visitorTeamScore.getText().toString() );
//                    }
//
//                    if (homeTeamScore.getText().toString().equals("")){
//                        myTeamScore = 0;
//                    }else{
//                        myTeamScore = Integer.parseInt( homeTeamScore.getText().toString() );
//                    }
//                }
                int counter = pref.getInt("counter",0);
                System.out.println("counter in profile");
                System.out.println(counter);

                editor = pref.edit();

                String date = eText.getText().toString();
                String intentvar = getIntent().getStringExtra("match_data");
                System.out.println(intentvar);
                JSONObject profile = new JSONObject();

                try {
                    profile.put("date", date);
                    profile.put("myTeamName", myTeamName);
                    profile.put("myTeamScore", myTeamScore);
                    profile.put("opponentTeamName", opponentTeamName);
                    profile.put("opponentTeamScore", opponentTeamScore);
                    profile.put("match_data", intentvar);

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                String jsonStr = profile.toString();
                String key = Integer.toString(counter);
                editor.putString(key,jsonStr);
                counter = counter + 1;
                editor.putInt("counter", counter);
                editor.apply();
                Intent mIntent = new Intent(GameProfileActivity.this, history.class);
                startActivity(mIntent);
            }
        });
    }




}



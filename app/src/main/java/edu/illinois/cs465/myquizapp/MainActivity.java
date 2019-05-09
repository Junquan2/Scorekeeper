package edu.illinois.cs465.myquizapp;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;

import java.util.*;
import org.json.JSONObject;
import org.json.JSONException;
import java.util.Calendar;
import android.view.MotionEvent;
import android.support.v4.view.GestureDetectorCompat;

public class MainActivity extends Activity {
    private Button button_miss;
    private Button button_free;
    private Button button_twopoints;
    private Button button_threepoints;

    private Button button_miss2;
    private Button button_free2;
    private Button button_twopoints2;
    private Button button_threepoints2;

    private Button button_undo;
    private Button button_end;
    private Button button_history;
    private Button button_left;
    private Button button_right;


    private TextView textview_misses;
    private TextView textview_free;
    private TextView textview_twopoints;
    private TextView textview_threepoints;

    private TextView textview_misses2;
    private TextView textview_free2;
    private TextView textview_twopoints2;
    private TextView textview_threepoints2;

    private TextView textview_quarter;

    int quarter = 1;

    int misses = 0;
    int frees = 0;
    int twopoints = 0;
    int threepoints = 0;
    int total_score = 0;

    int misses2 = 0;
    int frees2 = 0;
    int twopoints2 = 0;
    int threepoints2 = 0;
    int total_score2 = 0;

    Stack<Integer>[] action_stacks = new Stack[4];

    JSONObject[] quarters;
    JSONObject match_data;
    String[] number2word = {"First","Second","Third","Fourth"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for(int i = 0; i < 4; i++) {
            action_stacks[i] = new Stack<Integer>();
        }
        // Create a common gesture listener object.
        //DetectSwipeGestureListener gestureListener = new DetectSwipeGestureListener();

        // Set activity in the listener.
        //gestureListener.setActivity(this);

        // Create the gesture detector with the gesture listener.
        //gestureDetectorCompat = new GestureDetectorCompat(this, gestureListener);

        textview_misses = findViewById(R.id.textview_misses);
        textview_free = findViewById(R.id.textview_free);
        textview_twopoints = findViewById(R.id.textview_twopoints);
        textview_threepoints = findViewById(R.id.textview_threepoints);


        textview_misses2 = findViewById(R.id.textview_misses2);
        textview_free2 = findViewById(R.id.textview_free2);
        textview_twopoints2 = findViewById(R.id.textview_twopoints2);
        textview_threepoints2 = findViewById(R.id.textview_threepoints2);

        textview_quarter = findViewById(R.id.textview_quarter);
        //textview_message = findViewById(R.id.message);

        start();


        button_left = findViewById(R.id.button_left);
        View.OnClickListener button_left_listener = new View.OnClickListener() {
            public void onClick(View v) {
                // Store current quarter's data, show next quarter's data
                if(quarter == 1) {
                    return;
                }
                store_quarter_data();
                show_quarter_data(--quarter);
            }
        };
        button_left.setOnClickListener(button_left_listener);

        button_right = findViewById(R.id.button_right);
        View.OnClickListener button_right_listener = new View.OnClickListener() {
            public void onClick(View v) {
                store_quarter_data();
                if(quarter == 4) {
                    // Go to next activity
                    Intent mIntent = new Intent(MainActivity.this, GameProfileActivity.class);
                    mIntent.putExtra("match_data", match_data.toString());
                    startActivity(mIntent);
                    return;
                }
                else {
                    show_quarter_data(++quarter);
                }
            }
        };
        button_right.setOnClickListener(button_right_listener);


        button_history = findViewById(R.id.button_history);
        View.OnClickListener button_history_listener = new View.OnClickListener() {
            public void onClick(View v) {
                history();
            }
        };
        button_history.setOnClickListener(button_history_listener);

        button_miss = findViewById(R.id.button_miss);
        View.OnClickListener button_miss_listener = new View.OnClickListener() {
            public void onClick(View v) {
                miss();
            }
        };
        button_miss.setOnClickListener(button_miss_listener);

        button_miss2 = findViewById(R.id.button_miss2);
        View.OnClickListener button_miss_listener2 = new View.OnClickListener() {
            public void onClick(View v) {
                miss2();
            }
        };
        button_miss2.setOnClickListener(button_miss_listener2);


        button_free2 = findViewById(R.id.button_free2);
        View.OnClickListener button_free_listener2 = new View.OnClickListener() {
            public void onClick(View v) {
                free2();
            }
        };
        button_free2.setOnClickListener(button_free_listener2);

        button_free = findViewById(R.id.button_free);
        View.OnClickListener button_free_listener = new View.OnClickListener() {
            public void onClick(View v) {
                free();
            }
        };
        button_free.setOnClickListener(button_free_listener);

        button_twopoints = findViewById(R.id.button_twopoints);
        View.OnClickListener button_twopoints_listener = new View.OnClickListener() {
            public void onClick(View v) {
                twopoints++;
                total_score += 2;
                action_stacks[quarter - 1].push(2);
                textview_twopoints.setText(Integer.toString(twopoints));
            }
        };
        button_twopoints.setOnClickListener(button_twopoints_listener);


        button_twopoints2 = findViewById(R.id.button_twopoints2);
        View.OnClickListener button_twopoints_listener2 = new View.OnClickListener() {
            public void onClick(View v) {
                twopoints2++;
                total_score2 += 2;
                action_stacks[quarter - 1].push(-2);
                textview_twopoints2.setText(Integer.toString(twopoints2));
            }
        };
        button_twopoints2.setOnClickListener(button_twopoints_listener2);

        button_threepoints = findViewById(R.id.button_threepoints);
        View.OnClickListener button_threepoints_listener = new View.OnClickListener() {
            public void onClick(View v) {
                threepoints++;
                total_score += 3;
                action_stacks[quarter - 1].push(3);
                textview_threepoints.setText(Integer.toString(threepoints));
            }
        };
        button_threepoints.setOnClickListener(button_threepoints_listener);

        button_threepoints2 = findViewById(R.id.button_threepoints2);
        View.OnClickListener button_threepoints_listener2 = new View.OnClickListener() {
            public void onClick(View v) {
                threepoints2++;
                total_score2 += 3;
                action_stacks[quarter - 1].push(-3);
                textview_threepoints2.setText(Integer.toString(threepoints2));
            }
        };
        button_threepoints2.setOnClickListener(button_threepoints_listener2);

        button_undo = findViewById(R.id.button_undo);
        View.OnClickListener button_undo_listener = new View.OnClickListener() {
            public void onClick(View v) {
                if(action_stacks[quarter - 1].empty()) {
                    Toast.makeText(getApplicationContext(),"You have not performed any actions yet",Toast.LENGTH_SHORT).show();
                    return;
                }
                int action = action_stacks[quarter - 1].pop();
                switch (action) {
                    case 0:
                        misses--;
                        break;
                    case 1:
                        total_score--;
                        frees--;
                        break;
                    case 2:
                        total_score -= 2;
                        twopoints--;
                        break;
                    case 3:
                        total_score -= 3;
                        threepoints--;
                        break;
                    case -1:
                        total_score2--;
                        frees2--;
                        break;
                    case -2:
                        total_score2 -= 2;
                        twopoints2--;
                        break;
                    case -3:
                        total_score2 -= 3;
                        threepoints2--;
                        break;
                    case -4:
                        misses2--;
                        break;
                }
                textview_misses.setText(Integer.toString(misses));
                textview_free.setText(Integer.toString(frees));
                textview_twopoints.setText(Integer.toString(twopoints));
                textview_threepoints.setText(Integer.toString(threepoints));

                textview_misses2.setText(Integer.toString(misses2));
                textview_free2.setText(Integer.toString(frees2));
                textview_twopoints2.setText(Integer.toString(twopoints2));
                textview_threepoints2.setText(Integer.toString(threepoints2));
            }
        };
        button_undo.setOnClickListener(button_undo_listener);
/*
        button_end = findViewById(R.id.button_end);
        View.OnClickListener button_end_listener = new View.OnClickListener() {
            public void onClick(View v) {

                if(quarter <= 4) {
                    JSONObject curr_quarter = new JSONObject();
                    try {
                        curr_quarter.put("player_score", total_score);
                        curr_quarter.put("miss", misses);
                        curr_quarter.put("free", frees);
                        curr_quarter.put("two_points", twopoints);
                        curr_quarter.put("three_points", threepoints);
                        quarters[quarter - 1] = curr_quarter;
                        //Toast.makeText(getApplicationContext(),curr_quarter.toString(),Toast.LENGTH_LONG).show();
                    }
                    catch (JSONException exc) { }


                    // Next quarter
                    //Toast.makeText(getApplicationContext(),number2word[quarter] + "Quarter",Toast.LENGTH_SHORT).show();
                    if(quarter < 4) {
                        textview_quarter.setText(number2word[quarter] + " Quarter");
                    }


                    // Store these into some data structure, and then set them to 0
                    misses = 0;
                    frees = 0;
                    twopoints = 0;
                    threepoints = 0;
                    total_score = 0;

                    textview_misses.setText(Integer.toString(misses));
                    textview_free.setText(Integer.toString(frees));
                    textview_twopoints.setText(Integer.toString(twopoints));
                    textview_threepoints.setText(Integer.toString(threepoints));
                }
                quarter++;
                if(quarter > 4) {
                    // Go to next activity
                    //Toast.makeText(getApplicationContext(),"Finally! all quarters are finished!",Toast.LENGTH_SHORT).show();

                    int overall_score_for_observed_player = 0;


                    // Now, write the results to the json file
                    try {
                        int total_frees = 0, total_twopoints = 0, total_threepoints = 0, total_misses = 0, total_points = 0;
                        for(int i = 0; i < 4; i++) {
                            overall_score_for_observed_player += quarters[i].getInt("free");
                            overall_score_for_observed_player += quarters[i].getInt("two_points");
                            overall_score_for_observed_player += quarters[i].getInt("three_points");
                            quarters[i].put("total", quarters[i].getInt("free") + quarters[i].getInt("two_points") * 2 + quarters[i].getInt("three_points") * 3);

                            total_frees += quarters[i].getInt("free");
                            total_twopoints += quarters[i].getInt("two_points");
                            total_threepoints += quarters[i].getInt("three_points");
                            total_misses += quarters[i].getInt("miss");
                        }
                        total_points = total_frees + total_twopoints * 2 + total_threepoints * 3;
                        quarters[4] = new JSONObject();
                        quarters[4].put("free", total_frees);
                        quarters[4].put("two_points", total_twopoints);
                        quarters[4].put("three_points", total_threepoints);
                        quarters[4].put("miss", total_misses);
                        quarters[4].put("total", total_points);

                        match_data.put("first_quarter", quarters[0]);
                        match_data.put("second_quarter", quarters[1]);
                        match_data.put("third_quarter", quarters[2]);
                        match_data.put("fourth_quarter", quarters[3]);
                        match_data.put("all_quarters", quarters[4]);

//                        match_data.put("overall_score_for_observed_player", overall_score_for_observed_player);
                        match_data.put("overall_score_team1", total_points);
                        match_data.put("overall_score_team2", 0);
                        //Toast.makeText(getApplicationContext(),match_data.toString(),Toast.LENGTH_LONG).show();
                    }
                    catch(JSONException e) { }

                    Intent mIntent = new Intent(MainActivity.this, GameProfileActivity.class);
                    mIntent.putExtra("match_data", match_data.toString());
                    startActivity(mIntent);

                }
            }
        };
        button_end.setOnClickListener(button_end_listener);
*/
    }


    public void free()
    {
        frees++;
        total_score++;
        action_stacks[quarter - 1].push(1);
        textview_free.setText(Integer.toString(frees));
    }

    public void free2()
    {
        frees2++;
        total_score2++;
        action_stacks[quarter - 1].push(-1);
        textview_free2.setText(Integer.toString(frees2));
    }

    public void miss2()
    {
        misses2++;
        action_stacks[quarter - 1].push(-4);
        textview_misses2.setText(Integer.toString(misses2));
    }

    public void three()
    {
        threepoints++;
        total_score += 3;
        action_stacks[quarter - 1].push(3);
        textview_threepoints.setText(Integer.toString(threepoints));
    }

    public void two()
    {
        twopoints++;
        total_score += 2;
        action_stacks[quarter - 1].push(2);
    }

    public void miss()
    {
        misses++;
        action_stacks[quarter - 1].push(0);
        textview_misses.setText(Integer.toString(misses));
    }


    public void start() {
        quarter = 1;
        misses = 0;
        frees = 0;
        twopoints = 0;
        threepoints = 0;
        total_score = 0;


        quarters = new JSONObject[5];
        match_data = new JSONObject();

        // Match name should be some shared variable passed in by previous activity
        String match_name = "This is Match Name";
        try {
            match_data.put("match_name", match_name);
            match_data.put("datetime", Calendar.getInstance().getTime());
        }
        catch(JSONException e) { }

        textview_misses.setText(Integer.toString(misses));
        textview_free.setText(Integer.toString(frees));
        textview_twopoints.setText(Integer.toString(twopoints));
        textview_threepoints.setText(Integer.toString(threepoints));

    }

    public void restart() {
        start();
        textview_misses.setText(Integer.toString(misses));
        textview_free.setText(Integer.toString(frees));
        textview_twopoints.setText(Integer.toString(twopoints));
        textview_threepoints.setText(Integer.toString(threepoints));
        textview_quarter.setText("First Quarter");
    }

    public void single_tap() {
        free();
        //ImageView gesture_image = findViewById(R.id.swipe);
        //gesture_image.setVisibility(View.INVISIBLE);
        //textview_message.setVisibility(View.INVISIBLE);
    }

    public void double_tap() {
        two();
    }

    public void history() {
        Intent mIntent = new Intent(MainActivity.this, history.class);
        //mIntent.putExtra("match_data", match_data.toString());
        startActivity(mIntent);
    }

    public void store_quarter_data() {
        JSONObject curr_quarter = new JSONObject();
        try {
            curr_quarter.put("player_score", total_score);
            curr_quarter.put("miss", misses);
            curr_quarter.put("free", frees);
            curr_quarter.put("two_points", twopoints);
            curr_quarter.put("three_points", threepoints);

            curr_quarter.put("player_score2", total_score2);
            curr_quarter.put("miss2", misses2);
            curr_quarter.put("free2", frees2);
            curr_quarter.put("two_points2", twopoints2);
            curr_quarter.put("three_points2", threepoints2);

            quarters[quarter - 1] = curr_quarter;
            //Toast.makeText(getApplicationContext(),curr_quarter.toString(),Toast.LENGTH_LONG).show();

            if(quarter == 4) {
                int total_frees = 0, total_twopoints = 0, total_threepoints = 0, total_misses = 0, total_points = 0;
                int total_frees2 = 0, total_twopoints2 = 0, total_threepoints2 = 0, total_misses2 = 0, total_points2 = 0;
                for(int i = 0; i < 4; i++) {
                    total_points += quarters[i].getInt("free");
                    total_points += quarters[i].getInt("two_points");
                    total_points += quarters[i].getInt("three_points");
                    quarters[i].put("total", quarters[i].getInt("free") + quarters[i].getInt("two_points") * 2 + quarters[i].getInt("three_points") * 3);

                    total_frees += quarters[i].getInt("free");
                    total_twopoints += quarters[i].getInt("two_points");
                    total_threepoints += quarters[i].getInt("three_points");
                    total_misses += quarters[i].getInt("miss");

                    total_points2 += quarters[i].getInt("free2");
                    total_points2 += quarters[i].getInt("two_points2");
                    total_points2 += quarters[i].getInt("three_points2");
                    quarters[i].put("total2", quarters[i].getInt("free2") + quarters[i].getInt("two_points2") * 2 + quarters[i].getInt("three_points2") * 3);

                    total_frees2 += quarters[i].getInt("free2");
                    total_twopoints2 += quarters[i].getInt("two_points2");
                    total_threepoints2 += quarters[i].getInt("three_points2");
                    total_misses2 += quarters[i].getInt("miss2");
                }
                total_points = total_frees + total_twopoints * 2 + total_threepoints * 3;
                total_points2 = total_frees2 + total_twopoints2 * 2 + total_threepoints2 * 3;

                match_data.put("overall_score_team1", total_points);
                match_data.put("overall_score_team2", total_points2);

                quarters[4] = new JSONObject();
                quarters[4].put("free", total_frees);
                quarters[4].put("two_points", total_twopoints);
                quarters[4].put("three_points", total_threepoints);
                quarters[4].put("miss", total_misses);
                quarters[4].put("total", total_points);

                quarters[4].put("free2", total_frees2);
                quarters[4].put("two_points2", total_twopoints2);
                quarters[4].put("three_points2", total_threepoints2);
                quarters[4].put("miss2", total_misses2);
                quarters[4].put("total2", total_points2);

                match_data.put("first_quarter", quarters[0]);
                match_data.put("second_quarter", quarters[1]);
                match_data.put("third_quarter", quarters[2]);
                match_data.put("fourth_quarter", quarters[3]);
                match_data.put("all_quarters", quarters[4]);


            }

        }
        catch (JSONException exc) { }


    }

    public void show_quarter_data(int q) {
        JSONObject show_quarter = quarters[q - 1];
        if(show_quarter == null) {
            misses = frees = twopoints = threepoints = total_score = 0;
            misses2 = frees2 = twopoints2 = threepoints2 = total_score2 = 0;

        }
        else {
            try {
                misses = show_quarter.getInt("miss");
                frees = show_quarter.getInt("free");
                twopoints = show_quarter.getInt("two_points");
                threepoints = show_quarter.getInt("three_points");

                misses2 = show_quarter.getInt("miss2");
                frees2 = show_quarter.getInt("free2");
                twopoints2 = show_quarter.getInt("two_points2");
                threepoints2 = show_quarter.getInt("three_points2");
            }
            catch (JSONException e) {

            }

        }

        textview_misses.setText(Integer.toString(misses));
        textview_free.setText(Integer.toString(frees));
        textview_twopoints.setText(Integer.toString(twopoints));
        textview_threepoints.setText(Integer.toString(threepoints));

        textview_misses2.setText(Integer.toString(misses2));
        textview_free2.setText(Integer.toString(frees2));
        textview_twopoints2.setText(Integer.toString(twopoints2));
        textview_threepoints2.setText(Integer.toString(threepoints2));

        textview_quarter.setText(number2word[q - 1] + " Quarter");
    }
}

/*
        button_end = findViewById(R.id.button_end);
        View.OnClickListener button_end_listener = new View.OnClickListener() {
            public void onClick(View v) {

                if(quarter <= 4) {
                    JSONObject curr_quarter = new JSONObject();
                    try {
                        curr_quarter.put("player_score", total_score);
                        curr_quarter.put("miss", misses);
                        curr_quarter.put("free", frees);
                        curr_quarter.put("two_points", twopoints);
                        curr_quarter.put("three_points", threepoints);
                        quarters[quarter - 1] = curr_quarter;
                        //Toast.makeText(getApplicationContext(),curr_quarter.toString(),Toast.LENGTH_LONG).show();
                    }
                    catch (JSONException exc) { }


                    // Next quarter
                    //Toast.makeText(getApplicationContext(),number2word[quarter] + "Quarter",Toast.LENGTH_SHORT).show();
                    if(quarter < 4) {
                        textview_quarter.setText(number2word[quarter] + " Quarter");
                    }


                    // Store these into some data structure, and then set them to 0
                    misses = 0;
                    frees = 0;
                    twopoints = 0;
                    threepoints = 0;
                    total_score = 0;

                    textview_misses.setText(Integer.toString(misses));
                    textview_free.setText(Integer.toString(frees));
                    textview_twopoints.setText(Integer.toString(twopoints));
                    textview_threepoints.setText(Integer.toString(threepoints));
                }
                quarter++;
                if(quarter > 4) {
                    // Go to next activity
                    //Toast.makeText(getApplicationContext(),"Finally! all quarters are finished!",Toast.LENGTH_SHORT).show();

                    int overall_score_for_observed_player = 0;


                    // Now, write the results to the json file
                    try {
                        int total_frees = 0, total_twopoints = 0, total_threepoints = 0, total_misses = 0, total_points = 0;
                        for(int i = 0; i < 4; i++) {
                            overall_score_for_observed_player += quarters[i].getInt("free");
                            overall_score_for_observed_player += quarters[i].getInt("two_points");
                            overall_score_for_observed_player += quarters[i].getInt("three_points");
                            quarters[i].put("total", quarters[i].getInt("free") + quarters[i].getInt("two_points") * 2 + quarters[i].getInt("three_points") * 3);

                            total_frees += quarters[i].getInt("free");
                            total_twopoints += quarters[i].getInt("two_points");
                            total_threepoints += quarters[i].getInt("three_points");
                            total_misses += quarters[i].getInt("miss");
                        }
                        total_points = total_frees + total_twopoints * 2 + total_threepoints * 3;
                        quarters[4] = new JSONObject();
                        quarters[4].put("free", total_frees);
                        quarters[4].put("two_points", total_twopoints);
                        quarters[4].put("three_points", total_threepoints);
                        quarters[4].put("miss", total_misses);
                        quarters[4].put("total", total_points);

                        match_data.put("first_quarter", quarters[0]);
                        match_data.put("second_quarter", quarters[1]);
                        match_data.put("third_quarter", quarters[2]);
                        match_data.put("fourth_quarter", quarters[3]);
                        match_data.put("all_quarters", quarters[4]);

//                        match_data.put("overall_score_for_observed_player", overall_score_for_observed_player);
                        match_data.put("overall_score_team1", total_points);
                        match_data.put("overall_score_team2", 0);
                        //Toast.makeText(getApplicationContext(),match_data.toString(),Toast.LENGTH_LONG).show();
                    }
                    catch(JSONException e) { }

                    Intent mIntent = new Intent(MainActivity.this, GameProfileActivity.class);
                    mIntent.putExtra("match_data", match_data.toString());
                    startActivity(mIntent);

                }
            }
        };
        button_end.setOnClickListener(button_end_listener);
*/


/*
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        xmlns:tools="http://schemas.android.com/tools"
        android:layout_width="match_parent"

        android:layout_height="300dp"

        android:orientation="vertical">

        <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
            xmlns:app="http://schemas.android.com/apk/res-auto"
            xmlns:tools="http://schemas.android.com/tools"
            android:layout_width="match_parent"
            android:layout_height="100dp"
            android:layout_marginTop="0dp"
            android:orientation="horizontal">


            <Button
                android:id="@+id/button_undo"
                android:layout_width="150dp"
                android:layout_height="50dp"
                android:text="undo"
                android:layout_marginTop="30dp"
                android:layout_marginStart="30dp"
                android:layout_marginRight="10dp"
                android:layout_marginEnd="10dp"
                android:textSize="20sp"
                android:fontFamily="arial"
                android:textColor="@color/white_color"

                android:theme="@style/SecondaryButton"
                android:background="@drawable/second"

                />

            <Button
                android:id="@+id/button_restart"
                android:layout_width="150dp"
                android:layout_height="50dp"
                android:text="restart"
                android:layout_marginTop="30dp"
                android:layout_marginStart="30dp"
                android:layout_marginLeft="30dp"
                android:layout_marginRight="30dp"
                android:layout_marginEnd="30dp"
                android:layout_toRightOf="@id/button_restart"
                android:textSize="20sp"
                android:fontFamily="arial"
                android:textColor="@color/white_color"
                android:theme="@style/SecondaryButton"
                android:background="@drawable/second"
                />
        </LinearLayout>>
        <Button
            android:id="@+id/button_end"
            android:layout_width="340dp"
            android:layout_height="60dp"
            android:layout_marginStart="30dp"
            android:text="Next quarter"
            android:layout_marginTop="10dp"
            android:textSize="18sp"
            android:fontFamily="arial"
            android:textColor="@color/white_color"
            android:theme="@style/PrimaryButton"
            android:background="@drawable/noncurve"
            />
    </LinearLayout>>
* */
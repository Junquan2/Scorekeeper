package edu.illinois.cs465.myquizapp;
import java.util.*;
import org.json.JSONObject;
import org.json.JSONException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    JSONObject json_object = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        try {
            json_object = new JSONObject(getIntent().getStringExtra("match_data"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        TextView tv = findViewById(R.id.tv);
        tv.setText(json_object.toString());

    }
}

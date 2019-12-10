package com.yujinhong.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public void goBack(View V) {
        finish();
    }

    public void showAlarm(View V) {
        Toast toast = Toast.makeText(this, "hello im yujin", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 50, 50);
        toast.show();
//        Toast.makeText(getApplicationContext(), "button pressed", Toast.LENGTH_LONG).show();
    }

    public void goToLogin (View V) {
        Intent intent = new Intent(getApplicationContext(), Main3Activity.class);
        startActivity(intent);
    }
}

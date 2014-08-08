package com.pong.android;

import com.pong.android.R;
import com.pong.android.R.id;
import com.pong.android.R.layout;
import com.pong.android.R.menu;
import com.pong.android.modell.breakout.BreakOutActivity;
import com.pong.android.modell.dodgeball.DodgeballActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class GameOverActivity extends ActionBarActivity {
    
    public static String message = "";
    public static int retry = 0;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pong_game_over);
        updateText();
    }
 
    @Override 
    public void onStart(){
        super.onStart();
        updateText();
    }
    
    @Override
    public void onResume(){
        super.onResume();
        updateText();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pong_game_over, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public void btn1Clicked(View view) {
        if(retry == 1){
            startActivity(new Intent(this, PongActivity.class));
        } else if (retry == 2){
            startActivity(new Intent(this, DodgeballActivity.class));
        }
        else {
            startActivity(new Intent(this, BreakOutActivity.class));

        }
    }
    
    public void btn2Clicked(View view) {
        startActivity(new Intent(this, MenuActivity.class));
    }
    
    private void updateText(){
        TextView tv = (TextView) findViewById(R.id.textView1);
        tv.setText(message);
    }
    
}

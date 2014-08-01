package com.pong.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class PongGameOverActivity extends ActionBarActivity {
    
    public static int number = 0;
    
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
        startActivity(new Intent(this, PongActivity.class));
    }
    
    public void btn2Clicked(View view) {
        startActivity(new Intent(this, MenuActivity.class));
    }
    
    private void updateText(){
        TextView tv = (TextView) findViewById(R.id.textView1);
        tv.setText("You managed to survive for "+number+" rounds");
    }
    
}

package com.pong.android;

import com.pong.android.modell.breakout.BreakOutActivity;
import com.pong.android.modell.dodgeball.DodgeballActivity;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MenuActivity extends ActionBarActivity {

    public static MenuActivity dis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dis = this;
        setContentView(R.layout.activity_menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
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
        startActivity(new Intent(this, BreakOutActivity.class));
    }
    
    public void btn3Clicked(View view) {
        startActivity(new Intent(this, DodgeballActivity.class));
    }

    public void gameOver(String message) {
        GameOverActivity.message = message;
        startActivity(new Intent(this, GameOverActivity.class));
    }
}

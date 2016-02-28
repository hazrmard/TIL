package me.iahmed.til;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import me.iahmed.til.ReadRedditTitle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        int SDK = android.os.Build.VERSION.SDK_INT;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView main_text = (TextView) findViewById(R.id.main_text);
        main_text.setText(R.string.greeting);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.fab_circle));
        fab.setHapticFeedbackEnabled(true);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                //Snackbar.make(view, "MA BRO!", Snackbar.LENGTH_LONG)
                //  .setAction("Action", null).show();
            }
        });
        try {
            main_text.setText(new BackgroundTasks.GetToken().execute(this).get());
        } catch (InterruptedException | ExecutionException e) {
            //Like I care
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.night_mode) {
            item.setChecked(!item.isChecked());
            if (item.isChecked()) {
                Toast.makeText(this, R.string.night_mode_en, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, R.string.night_mode_den, Toast.LENGTH_SHORT).show();
            }
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}

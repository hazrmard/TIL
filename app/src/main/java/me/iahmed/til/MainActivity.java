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

public class MainActivity extends AppCompatActivity {

    protected TextView main_text;
    protected FloatingActionButton fab;
    protected MainActivity main_context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        int SDK = android.os.Build.VERSION.SDK_INT;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        main_text = (TextView) findViewById(R.id.main_text);
        main_text.setText(R.string.greeting);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageDrawable(ContextCompat.getDrawable(main_context, R.drawable.fab_circle));
        fab.setHapticFeedbackEnabled(true);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                new BackgroundTasks.RefillQueue().execute(main_context);
                //Snackbar.make(view, "MA BRO!", Snackbar.LENGTH_LONG)
                //  .setAction("Action", null).show();
            }
        });
        new BackgroundTasks.GetToken().execute(main_context);
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

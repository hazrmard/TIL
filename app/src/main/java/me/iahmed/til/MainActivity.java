package me.iahmed.til;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.RequiresPermission;
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
    private Entry current_entry=null;

    @Override
    protected void onStart() {
        super.onStart();
        if (ReadRedditTitle.entries.isEmpty()) {
            new BackgroundTasks.GetToken().execute(main_context);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        int SDK = android.os.Build.VERSION.SDK_INT;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        main_text = (TextView) findViewById(R.id.main_text);
        main_text.setText(R.string.greeting);
        main_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (current_entry!=null) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(current_entry.link));
                    startActivity(browserIntent);
                }
            }
        });

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageDrawable(ContextCompat.getDrawable(main_context, R.drawable.fab_circle));
        fab.setHapticFeedbackEnabled(true);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

                try {
                    Entry e = ReadRedditTitle.entries.remove(0);
                    current_entry = e;
                    main_text.setText(e.getTitle());
                    System.out.println("Titles left: " + ReadRedditTitle.entries.size());
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("List is empty.");
                }

                if (ReadRedditTitle.entries.isEmpty()) {
                    Snackbar.make(view, R.string.loading, Snackbar.LENGTH_SHORT)
                      .setAction("Action", null).show();
                    new BackgroundTasks.RefillQueue().execute(main_context);
                }

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        helpers.setNightMode(main_context, helpers.getNightMode(main_context));
        MenuItem nightmode_option = menu.findItem(R.id.night_mode);
        nightmode_option.setChecked(helpers.getNightMode(main_context));

        String search_mode = helpers.getSearchMode(main_context);
        System.out.println("Stored search mode:" + search_mode);
        MenuItem byhot = menu.findItem(R.id.by_hot);
        MenuItem bytop = menu.findItem(R.id.by_top);
        MenuItem bynew = menu.findItem(R.id.by_new);
        switch (search_mode) {
            case "hot":
                byhot.setChecked(true);
                break;
            case "top":
                bytop.setChecked(true);
                break;
            case "new":
                bynew.setChecked(true);
                break;
        }

        System.out.println(byhot.isChecked());
        System.out.println(bytop.isChecked());
        System.out.println(bynew.isChecked());

        helpers.setSearchMode(main_context, byhot.isChecked(),
                bytop.isChecked(),
                bynew.isChecked());
        System.out.println("SearchMode after menu inflation is: " + ReadRedditTitle.request_suburl);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.about) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://github.com/hazrmard/TIL"));
            startActivity(browserIntent);
            return true;
        } else if (id == R.id.night_mode) {
            item.setChecked(!item.isChecked());
            if (item.isChecked()) {
                Toast.makeText(this, R.string.night_mode_en, Toast.LENGTH_SHORT).show();
                helpers.setNightMode(main_context, true);
            } else {
                Toast.makeText(this, R.string.night_mode_den, Toast.LENGTH_SHORT).show();
                helpers.setNightMode(main_context, false);
            }
            return true;
        } else if ((id==R.id.by_hot) || (id==R.id.by_new) || (id==R.id.by_top)) {

            item.setChecked(true);
            helpers.setSearchMode(main_context, id == R.id.by_hot,
                    id == R.id.by_top,
                    id == R.id.by_new);

            ReadRedditTitle.flush_queue();
            Snackbar.make(fab, R.string.loading, Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
            new BackgroundTasks.RefillQueue().execute(main_context);
        }


        return super.onOptionsItemSelected(item);
    }
}

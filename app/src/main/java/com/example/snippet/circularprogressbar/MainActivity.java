
// Create circular progress bar

package com.example.snippet.circularprogressbar;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {

    private CustomView view;
    private MyTask mytask;
    private boolean en;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set the display to custom view
        view = new CustomView(getApplicationContext());
        setContentView(view);

        // enable start menu
        en = true;
    }

    // create menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // handle menu selection
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.menu_start:  onMenuStartThread();  break;
            case R.id.menu_stop:   onMenuStopThread();   break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    // enable/disable menu
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        menu.findItem(R.id.menu_start).setEnabled(en);
        menu.findItem(R.id.menu_stop).setEnabled(!en);

        return super.onPrepareOptionsMenu(menu);
    }

    // start thread
    private void onMenuStartThread(){
        mytask = new MyTask(getApplicationContext());
        mytask.execute();
    }

    // stop thread
    private void onMenuStopThread(){
        mytask.cancel(true);
    }

    // progress task
    private class MyTask extends AsyncTask<Void, Integer, String> {

        private Context context;

        // contructor
        public MyTask(Context context) {
            this.context = context;
        }

        // before animatiion
        @Override
        protected void onPreExecute() {
            view.show();
        }

        // after animation
        @Override
        protected void onPostExecute(String s) {
            view.hide();
            Toast.makeText(context, s , Toast.LENGTH_SHORT).show();
        }

        // after cancel animation
        @Override
        protected void onCancelled(String s) {
            Toast.makeText(context, s , Toast.LENGTH_SHORT).show();
        }

        // update display
        @Override
        protected void onProgressUpdate(Integer... values) {
            view.update(values[0]);
        }

        // do animation
        @Override
        protected String doInBackground(Void... voids) {

            int i;
            String str;

            // enable stop menu
            en = false;

            str = "GAME OVER";

            // loop from 0 to 100
            for(i=0;i<101;i++){

                // break the loop
                if (isCancelled()) {
                    str = "WALANG MAGAWA";
                    break;
                }

                // delay
                SystemClock.sleep(50);

                // call onProgressUpdate
                publishProgress(i);
            }

            // enable start menu
            en = true;

            return str;
        }
    }
}

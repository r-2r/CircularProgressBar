
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set the display to custom view
        view = new CustomView(getApplicationContext());
        setContentView(view);

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

        // before
        @Override
        protected void onPreExecute() {
            view.show();
        }

        // after
        @Override
        protected void onPostExecute(String s) {
            view.hide();
            Toast.makeText(context, s , Toast.LENGTH_SHORT).show();
        }

        // update display
        @Override
        protected void onProgressUpdate(Integer... values) {
            view.update(values[0]);
        }

        // in progress
        @Override
        protected String doInBackground(Void... voids) {

            int i;
            String str;

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

            return str;
        }
    }
}

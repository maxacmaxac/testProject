package testrunner.android.com.testsrunner;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import testrunner.android.com.testsrunner.DbUtils.DataSource;
import testrunner.android.com.testsrunner.model.Email;



/**
 * Created by marjan on 11/1/2014.
 */
public class MyActivity extends Activity {

    private DataSource mDataSource;

    private TextView mEmailID;

    private TextView mEmailName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        mDataSource = new DataSource(getApplicationContext());
        mDataSource.open();

        Button searchButton = (Button) findViewById(R.id.search_button);

        Button addButton = (Button) findViewById(R.id.add_button);

        mEmailID = (TextView) findViewById(R.id.email_id);

        mEmailName = (TextView) findViewById(R.id.email_name);


        final EditText searchEditText = (EditText) findViewById(R.id.search_edit_text);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchTest = searchEditText.getText().toString();
                if (TextUtils.isEmpty(searchTest)) {
                    Toast.makeText(getApplicationContext(),
                            R.string.error_message, Toast.LENGTH_LONG).show();
                } else {
                    searchDB.execute(new String[]{searchTest});
                }
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchTest = searchEditText.getText().toString();
                if (TextUtils.isEmpty(searchTest)) {
                    Toast.makeText(getApplicationContext(),
                            R.string.error_message, Toast.LENGTH_LONG).show();
                } else {
                    insertDB.execute(new String[]{searchTest});
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDataSource.close();
    }

    /**
     * Visible for testing
     */
    public void deleteAllFromDB(){
        mDataSource.deleteAll();
    }

    private AsyncTask insertDB = new AsyncTask<String,  Void , Void >(){
        @Override
        protected  Void  doInBackground(String... params) {
            mDataSource.insert(params[0]);
            return null;
        }
    };

    private AsyncTask searchDB = new AsyncTask<String,  List<Email> ,  List<Email> >(){
        @Override
        protected  List<Email>  doInBackground(String... params) {
            return mDataSource.getEmailByName(params[0]);
        }

        @Override
        protected void onPostExecute( List<Email> emails) {
            mEmailID.setText(String.valueOf(emails.get(0).getId()));
            mEmailName.setText(emails.get(0).getEmailName());
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

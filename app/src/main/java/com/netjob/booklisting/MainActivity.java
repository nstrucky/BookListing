package com.netjob.booklisting;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.net.ConnectivityManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {



    private ListView mBooksListView;
    private EditText mSearchEditText;
    private List<Book> mBooks;
    private BookAdapter mBookAdapter;
    private Button mSearchButton;
    private static String mSearchTerms = "";
    BookLoader bookLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearchButton = (Button) findViewById(R.id.button_search);
        mSearchEditText = (EditText) findViewById(R.id.editText_searchBox);

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchTerms = mSearchEditText.getText().toString();
                getLoaderManager().restartLoader(0, null, MainActivity.this);


            }
        });

        mBooks = new ArrayList<>();
        mBooksListView = (ListView) findViewById(R.id.listView_booksReturned);
        mBookAdapter = new BookAdapter(this, mBooks);
        mBooksListView.setAdapter(mBookAdapter);

        startLoader();

    }


    private void startLoader() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected()) {

                getLoaderManager().initLoader(0, null, this);


        } else {
            Toast.makeText(this, getString(R.string.message_no_internet), Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
        bookLoader = new BookLoader(this);

        return bookLoader;
    }


    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {

        mBookAdapter.clear();
        if (books != null && !books.isEmpty()) {
            mBookAdapter.addAll(books);
        }
    }


    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {


    }

    private static class BookLoader extends AsyncTaskLoader<List<Book>> {

        public BookLoader(Context context) {
            super(context);
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            forceLoad();
        }

        @Override
        public List<Book> loadInBackground() {

            URL url = QueryUtils.buildUrl(mSearchTerms);
            return QueryUtils.makeHttpRequest(url);

        }
    }

}

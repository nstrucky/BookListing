package com.netjob.booklisting;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
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
    private Button mTryAgainButton;
    private TextView mEmptyTextView;
    private ProgressBar mProgressBar;
    private static String mSearchTerms = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mEmptyTextView = (TextView) findViewById(R.id.textView_empty_list);
        mSearchEditText = (EditText) findViewById(R.id.editText_searchBox);
        mTryAgainButton = (Button) findViewById(R.id.button_tryAgain);
        mTryAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loaderAction(2);
            }
        });
        mSearchButton = (Button) findViewById(R.id.button_search);
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mSearchTerms = mSearchEditText.getText().toString();
                hideSoftKeyboard();
                loaderAction(2);

            }
        });

        mBooks = new ArrayList<>();
        mBooksListView = (ListView) findViewById(R.id.listView_booksReturned);
        mBookAdapter = new BookAdapter(this, mBooks);
        mBooksListView.setAdapter(mBookAdapter);
        mBooksListView.setEmptyView(mEmptyTextView);

        loaderAction(1);

    }

    private void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

    }

    private void loaderAction(int action) {
        mBookAdapter.clear();
        mEmptyTextView.setVisibility(View.GONE);
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected()) {

            mProgressBar.setVisibility(View.VISIBLE);
            mTryAgainButton.setVisibility(View.GONE);
            mEmptyTextView.setText(getString(R.string.message_emptytext));
            switch (action) {

                case 1:
                    getLoaderManager().initLoader(0, null, this);
                    break;

                case 2:
                    getLoaderManager().restartLoader(0, null, this);
                    break;

            }

        } else {
            mBookAdapter.clear();
            mEmptyTextView.setVisibility(View.VISIBLE);
            mTryAgainButton.setVisibility(View.VISIBLE);
            mEmptyTextView.setText(getString(R.string.message_no_internet));
            Toast.makeText(this, getString(R.string.message_no_internet), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
        return new BookLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {

        mProgressBar.setVisibility(View.GONE);

        if (books != null && !books.isEmpty()) {
            mBookAdapter.addAll(books);
        }
        mEmptyTextView.setText(getString(R.string.message_no_data));
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

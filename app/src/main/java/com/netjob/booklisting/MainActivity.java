package com.netjob.booklisting;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {



    ListView mBooksListView;
    EditText mSearchEditText;
    List<Book> mBooks;
    BookAdapter mBookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearchEditText = (EditText) findViewById(R.id.editText_searchBox);

        mBooks = new ArrayList<>();
        mBooksListView = (ListView) findViewById(R.id.listView_booksReturned);
        mBookAdapter = new BookAdapter(this, mBooks);
        mBooksListView.setAdapter(mBookAdapter);
        getLoaderManager().initLoader(0, null, this);

    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
        return new BookLoader(this);
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

            URL url = QueryUtils.buildUrl("The Godfather");
            return QueryUtils.makeHttpRequest(url);

        }
    }

}

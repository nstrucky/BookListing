package com.netjob.booklisting;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

//

    ListView mBooksListView;
    EditText mSearchEditText;
    List<Book> mBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearchEditText = (EditText) findViewById(R.id.editText_searchBox);

        String[] test = {"John Hamm", "Hammy Johnson", "William H. Macy"};
        String[] test2 = {"Me", "you"};

        mBooks = new ArrayList<>();
        mBooks.add(new Book("Romeo and Juliet", test, "2016-12-12"));
        mBooks.add(new Book("Dark 11", test2, "2017-01-11"));


        mBooksListView = (ListView) findViewById(R.id.listView_booksReturned);

        BookAdapter bookAdapter = new BookAdapter(this, mBooks);

        mBooksListView.setAdapter(bookAdapter);

    }


}

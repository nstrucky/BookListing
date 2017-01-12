package com.netjob.booklisting;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

//

    ListView mBooksListView;
    EditText mSearchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearchEditText = (EditText) findViewById(R.id.editText_searchBox);


        String[] dummyBooks = {"harry potter", "To Kill a Mockingbird", "Game of Thrones",
                                "A Knights Tale", "A Knight of the Seven Kingdoms",
                                "True Lies", "The Year of the Flood", "Unix for beginners"};

        mBooksListView = (ListView) findViewById(R.id.listView_booksReturned);

        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.textview_dummy, dummyBooks);

        mBooksListView.setAdapter(adapter);

    }


}

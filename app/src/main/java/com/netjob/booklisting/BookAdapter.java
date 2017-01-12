package com.netjob.booklisting;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by root on 1/11/17.
 */

public class BookAdapter extends ArrayAdapter {


    public BookAdapter(Context context, List<Book> list) {
        super(context, 0, list);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        ViewHolder viewHolder;
        Book currentBook = (Book) getItem(position);

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_book, null, false);

            viewHolder = new ViewHolder();

            viewHolder.mBookTitle = (TextView) listItemView.findViewById(R.id.textView_bookTitle);
            viewHolder.mAuthors = (TextView) listItemView.findViewById(R.id.textView_authors);
            viewHolder.mPublishDate = (TextView) listItemView.findViewById(R.id.textView_publshDate);

            listItemView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) listItemView.getTag();
        }

        String[] authors = currentBook.getAuthors();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < authors.length; i++) {
            sb.append(authors[i]);

            if (authors.length == 2 && i == 0) {
                sb.append(" and ");

            } else {
                if (authors.length > 2 && authors.length - i > 1) {
                    sb.append(", ");
                }

                if (authors.length - i == 2) {
                    sb.append("and ");
                }
            }
        }

        viewHolder.mBookTitle.setText(currentBook.getTitle());
        viewHolder.mAuthors.setText(sb.toString());
        viewHolder.mPublishDate.setText(currentBook.getPublishDate());

        return listItemView;
    }

}


class ViewHolder {

    TextView mBookTitle;
    TextView mAuthors;
    TextView mPublishDate;

}
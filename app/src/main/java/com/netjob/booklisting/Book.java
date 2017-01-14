package com.netjob.booklisting;

import android.graphics.Bitmap;

/**
 * Created by root on 1/11/17.
 */

public class Book {


    private String mTitle;
    private String[] mAuthors;
    private String mPublishDate;
    private Bitmap mBitmap;

    public Book(String title, String[] authors, String publishDate, Bitmap bitmap) {
        mTitle = title;
        mAuthors = authors;
        mPublishDate = publishDate;
        mBitmap = bitmap;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String[] getAuthors() {
        return mAuthors;
    }

    public void setAuthors(String[] authors) {
        mAuthors = authors;
    }

    public String getPublishDate() {
        return mPublishDate;
    }

    public void setPublishDate(String publishDate) {
        mPublishDate = publishDate;
    }

    public Bitmap getImageBitmap() {
        return mBitmap;
    }

    public void setImageBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }

}

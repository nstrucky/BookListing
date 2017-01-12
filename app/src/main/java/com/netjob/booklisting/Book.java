package com.netjob.booklisting;

/**
 * Created by root on 1/11/17.
 */

public class Book {


    private String mTitle;
    private String[] mAuthors;
    private String mPublishDate;

    public Book(String title, String[] authors, String publishDate) {
        mTitle = title;
        mAuthors = authors;
        mPublishDate = publishDate;
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

}

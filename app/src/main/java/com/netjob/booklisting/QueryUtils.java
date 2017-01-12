package com.netjob.booklisting;

import android.net.Uri;

import java.net.URL;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

/**
 * Created by root on 1/11/17.
 */

public class QueryUtils {

    private final String apiKey = "AIzaSyDm5Jus_bDlY-KTNd3dJ20veQH-_BkSzdg";



    public URL buildUrl(String searchTerm) {

        String searchString = searchTerm.replace(" ", "+");
        final String BASE_URL = "https://www.googleapis.com/books/v1/volumes?q=";






        return null;
    }


}

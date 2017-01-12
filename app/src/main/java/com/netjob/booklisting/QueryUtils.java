package com.netjob.booklisting;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

/**
 * Created by root on 1/11/17.
 */

public class QueryUtils {

    private final String LOG_TAG = "QueryUtils";

    public URL buildUrl(String searchTerm) {

        URL url = null;
        final String BASE_URL = "https://www.googleapis.com/books/v1/volumes?q=";
        final String PARAM_Q = "q";
        final String PARAM_KEY = "key";

        final String APIKEY = "AIzaSyDm5Jus_bDlY-KTNd3dJ20veQH-_BkSzdg";
        String searchString = searchTerm.replace(" ", "+");

        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_KEY, APIKEY)
                .appendQueryParameter(PARAM_Q, searchString)
                .build();

        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Malformed URL", e);
        }

        return url;
    }


    public String makeHttpRequest(URL url) {

        HttpURLConnection httpURLConnection = null;
        StringBuilder stringBuilder = new StringBuilder();
        InputStream inputStream = null;
        String jsonString = "";

        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            inputStream = httpURLConnection.getInputStream();

            if (inputStream == null) {
                return jsonString;
            }

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + "\n");

            }

            jsonString = stringBuilder.toString();
            Log.i(LOG_TAG, jsonString);

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();

            }

            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "IO Exception", e);
                }
            }
        }

        return jsonString;

    }


}

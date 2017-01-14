package com.netjob.booklisting;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

/**
 * Created by root on 1/11/17.
 */

public class QueryUtils {

    private static final String LOG_TAG = "QueryUtils";

    public static URL buildUrl(String searchTerm) {

        URL url = null;
        final String BASE_URL = "https://www.googleapis.com/books/v1/volumes?";
        final String PARAM_Q = "q";
        final String PARAM_KEY = "key";
        final String PARAM_ORDER = "orderBy";
        final String PARAM_MAX_RESULTS = "maxResults";

        final String APIKEY = "AIzaSyDm5Jus_bDlY-KTNd3dJ20veQH-_BkSzdg";
        String searchString = searchTerm.replace(" ", "+");
        final String ordering = "relevance";
        final String maxResults = "20";

        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_KEY, APIKEY)
                .appendQueryParameter(PARAM_Q, searchString)
                .appendQueryParameter(PARAM_ORDER, ordering)
                .appendQueryParameter(PARAM_MAX_RESULTS, maxResults)
                .build();

        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Malformed URL", e);
        }

        return url;
    }

    public static List<Book> makeHttpRequest(URL url) {

        String jsonString = "";
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode() == 200) {
                inputStream = httpURLConnection.getInputStream();
                jsonString = readFromInputStream(inputStream);

            }

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

        return fetchBookListData(jsonString);

    }

    private static List<Book> fetchBookListData(String jsonString) {

        List<Book> mBooksList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray items = jsonObject.getJSONArray("items");

            for (int i = 0; i < items.length(); i++) {

                JSONObject item = items.getJSONObject(i);
                JSONObject info = item.getJSONObject("volumeInfo");
                String title = info.getString("title");
                JSONArray jsonAuthors = info.getJSONArray("authors");
                String[] authors = new String[jsonAuthors.length()];
                String imageUrl = info.getJSONObject("imageLinks").getString("thumbnail");
                Bitmap bitmap = getBitmapFromUrl(imageUrl);

                for (int j = 0; j < authors.length; j++) {
                    authors[j] = jsonAuthors.getString(j);
                }

                String publishDate = info.getString("publishedDate");
                mBooksList.add(new Book(title, authors, publishDate, bitmap));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return mBooksList;

    }

    private static String readFromInputStream(InputStream inputStream) {

        String jsonResponse = "";
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        if (inputStream == null) {
            return jsonResponse;
        }

        try {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + "\n");

            }

        } catch (IOException e) {
            Log.e(LOG_TAG, "IO Exception", e);
        }

        jsonResponse = stringBuilder.toString();
        Log.i(LOG_TAG, jsonResponse);

        return jsonResponse;
    }

    public static Bitmap getBitmapFromUrl(String stringUrl) {

        Bitmap bitmap = null;
        InputStream inputStream = null;
        try {
            URL url = new URL(stringUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode() == 200) {
                inputStream = httpURLConnection.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;

    }

}

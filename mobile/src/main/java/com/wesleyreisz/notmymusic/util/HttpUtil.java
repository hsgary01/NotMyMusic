package com.wesleyreisz.notmymusic.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.wesleyreisz.notmymusic.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by wesleyreisz on 10/19/14.
 */
public class HttpUtil {
    private static String TAG = "HTTP_DOWNLOAD";

    public static String getJson(String url){
        InputStream inputStream = null;
        String result = "";
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    public static Bitmap fetchImage(String strUrl){
        if(strUrl==null){
            return null;
        }

        try {
            URL url = new URL(strUrl);
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
            if(httpCon.getResponseCode()!=200){
                throw new Exception("Failed to Connect");
            }

            InputStream is = httpCon.getInputStream();
            return BitmapFactory.decodeStream(is);
        } catch (MalformedURLException e) {
            Log.e(TAG, "malformedurl: " + strUrl);
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
        //return default image if nothing is loaded
        //return BitmapFactory.decodeResource(context.getResources(),
        //        R.drawable.placeholder);
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }
}

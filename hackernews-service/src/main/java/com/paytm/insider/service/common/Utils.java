package com.paytm.insider.service.common;

import com.paytm.insider.service.exceptions.URLRequestException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class Utils {

    /**
     * Function for generating String from stream of data
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static String genrateStringfromStream(InputStream inputStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(
                inputStream));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

    /**
     * Function for making a connecting request to given URL and return the response payload
     * @param Url
     * @return
     * @throws IOException
     * @throws URLRequestException
     */
    public static String fetchDataFromURL(String Url) throws IOException, URLRequestException{
        URL url = new URL(Url);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setConnectTimeout(7000);
        con.setReadTimeout(7000);
        int status = con.getResponseCode();
        String response  = genrateStringfromStream(con.getInputStream());
        if(status==HttpURLConnection.HTTP_OK){
            return response;
        } else {
            throw new URLRequestException(status, response);
        }
    }

    /**
     * Generate Hacker news URL based on content(ID od story or comment) and resources(Item, user)
     * @param content
     * @param resource
     * @return
     */
    public static String generateHackerNewsUrl(String content, String resource){
        return "https://hacker-news.firebaseio.com/v0/" + resource + "/" + content + ".json";
    }

    /**
     * Convert from UNIX Timestamp to ZonedDateTime
     * @param time
     * @return
     */
    public static ZonedDateTime covertUnixtoZoneDateTime(String time){
        Date givenDate = new Date( Long.parseLong(time) * 1000 );
        return givenDate.toInstant().atZone(ZoneId.systemDefault());
    }

    /**
     * Fetch No of Years from current ZonedDateTime
     * @param time
     * @return
     */
    public static int getYearsfromZonedTime(ZonedDateTime time){
        ZonedDateTime now = ZonedDateTime.now();
        return (int) ChronoUnit.YEARS.between(time, now);
    }
}


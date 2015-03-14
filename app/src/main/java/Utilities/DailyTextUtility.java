package Utilities;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by alekszilagyi on 3/11/15.
 */
public class DailyTextUtility
{
    private String dtWebsite = "http://www.moravian.org/todays-daily-text/daily-text/";

    public DailyTextUtility()
    {
    }

    public String getTodaysText()
    {
        try {
            return new UrlRequester().execute(dtWebsite).get();
        }
        catch (Exception e)
        {
            return "Concurrency Error. Please Report.";
        }
    }

    private class UrlRequester extends AsyncTask<String, Void, String>
    {
        private Exception e;

        @Override
        protected String doInBackground(String... params) {
            String text = "";
            URL url;
            HttpURLConnection connection = null;
            try
            {
                Log.w("Async", "Entering");
                String site = params[0];
                url = new URL(site);
                connection = (HttpURLConnection)(url.openConnection());
                BufferedReader stream = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String line = "";
                String startPattern = "(.*)entry-content(.*)";
                String endPattern = "(.*)</div>(.*)";

                do
                {
                    line = stream.readLine();
                } while (line.matches(startPattern) == false);

                line = stream.readLine();

                while (line.matches(endPattern) == false)
                {

                    line = line.replaceAll("<[a-zA-Z/][a-zA-Z]*[\\s]?[/]?>|\t|&nbsp;", "");
                    text = text.concat(line + "\n");
                    line = stream.readLine();
                }
                line = stream.readLine();
                Log.w("Async", line);

                stream.close();
            }
            catch (MalformedURLException e)
            {
                return "Bad URL. Please Report.";
            }
            catch (IOException e)
            {
                return "Could Not Connect. Please try again later";
            }
            Log.w("ASync Exit", text);
            return text;
        }
    }
}

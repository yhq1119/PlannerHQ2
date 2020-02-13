package HQ.Planner.GPS;

import android.content.Context;
import android.location.Location;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;

import HQ.Planner.R;

public class DurationData {

    private double latitude;
    private double longitude;
    private Context context;

    public DurationData(Location currentLocation, Context context) {
        this.latitude = currentLocation.getLatitude();
        this.longitude = currentLocation.getLongitude();
        this.context = context;
    }

    public synchronized int getDurationDataMins(String url, Location location) {
        int min = Integer.MAX_VALUE;
        try {

            JSONObject mapInfo =
                    new JSONObject(
                            getHtml(
                                    url.replace(" ", ""),
                                    location));
            JSONArray rows = mapInfo.getJSONArray("rows");
            JSONArray elements = rows.getJSONObject(0).getJSONArray("elements");
            String time = elements.getJSONObject(0).getJSONObject("duration").getString("value");
            min = Integer.parseInt(time);


        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return min;
    }

    private synchronized String getHtml(String locateURL, Location location) throws IOException {

        final String URL_API = "https://maps.googleapis.com/maps/api/distancematrix/json?";

        if (location != null) {
            BigDecimal lat = new BigDecimal(location.getLatitude());
            BigDecimal lng = new BigDecimal(location.getLongitude());

            latitude = lat.setScale(7, BigDecimal.ROUND_DOWN).doubleValue();
            longitude = lng.setScale(7, BigDecimal.ROUND_DOWN).doubleValue();
        }
        String mapKey = context.getResources()
                .getString(R.string.google_maps_key);
        URL url = new URL(URL_API +

                "origins=" +

                latitude + "," + longitude +
                "&destinations=" + locateURL + "&mode=driving&key=" + mapKey);
        HttpURLConnection connect = (HttpURLConnection) url.openConnection();
        connect.setRequestMethod("GET");
        connect.setConnectTimeout(5 * 1000);
        InputStream inputStream = connect.getInputStream();
        byte[] data = readInputStream(inputStream);
        return new String(data, StandardCharsets.UTF_8).trim();
    }

    private synchronized static byte[] readInputStream(InputStream inStream) throws IOException {

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, length);
        }
        inStream.close();
        return outStream.toByteArray();
    }
}

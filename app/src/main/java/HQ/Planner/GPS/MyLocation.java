//package HQ.Planner.GPS;
//
//import android.Manifest;
//import android.content.Context;
//import android.content.pm.PackageManager;
//import android.graphics.Color;
//import android.location.Location;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.app.Fragment;
//
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.api.GoogleApi;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.PolylineOptions;
//
//import java.io.BufferedInputStream;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Objects;
//
//import HQ.Planner.R;
//import HQ.Planner.utilities.MToast;
//
//public class MyLocation
//
//        implements
//        GoogleApiClient.ConnectionCallbacks,
//        GoogleApiClient.OnConnectionFailedListener {
//
//    final static int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
//    Context context;
//    GoogleApiClient googleApiClient;
//    Location myLocation;
//
//    public MyLocation(Context context) {
//        this.context = context;
//
//        if (googleApiClient == null) {
//            googleApiClient = new GoogleApiClient.Builder(
//                    Objects.requireNonNull(context))
//                    .addConnectionCallbacks(this)
//                    .addOnConnectionFailedListener(this)
//                    .addApi(LocationServices.API)
//                    .build();
//        }
//
//
//    }
//
//
////    @Override
////    public void onRequestPermissionsResult(
////            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
////        switch (requestCode) {
////            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
////                // If request is cancelled, the result arrays are empty.
////                if (grantResults.length > 0
////                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
////                    getMyLocation();
////
////                } else {
////                    say("Permission Denied");
////                }
////                return;
////            }
////
////            // other 'case' lines to check for other
////            // permissions this app might request
////        }
////    }
//
//    public void requestPermission(
//
//        int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode) {
//            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    getMyLocation();
//
//                } else {
//                    say("Permission Denied");
//                }
//                return;
//            }
//            // other 'case' lines to check for other
//            // permissions this app might request
//        }
//    }
//
//
//    @Override
//    public void onConnected(@Nullable Bundle bundle) {
//        getMyLocation();
//    }
//
//    private String getRequestURL(LatLng origin,LatLng dest){
//
//        String regex1 = ",";
//        String regex2 = "&";
//        String regex3 = "?";
//        String key = context.getResources().getString(R.string.google_maps_key);
//
//        //Origin
//        String str_origin = "origin="+origin.latitude+regex1+origin.longitude;
//        //destination
//        String str_dest = "destination="+dest.latitude+regex1+dest.longitude;
//
//        String sensor = "sensor=false";
//        String mode ="mode=driving";
//        String params = str_origin+regex2+str_dest+regex2+sensor+regex2+mode;
//
//        String output = "json";
//
//        String url = "https://maps.googleapis.com/maps/api/directions/"+output+regex3+params;
//
//        return url;
//    }
//
//
//    private String requestDirection(String reqURL) throws IOException {
//        String respond = "";
//        InputStream inputStream = null;
//        HttpURLConnection connection = null;
//        try {
//            URL url = new URL(reqURL);
//            connection = (HttpURLConnection)url.openConnection();
//            inputStream = connection.getInputStream();
//
//            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
//
//            String line = "";
//            StringBuffer stringBuffer = new StringBuffer();
//            while ((line = in.readLine())!=null){
//                stringBuffer.append(line);
//            }
//
//            respond = stringBuffer.toString();
//
//            in.close();
//
//
//
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }finally {
//            if (inputStream!=null){
//                inputStream.close();
//            }
//            connection.disconnect();
//        }
//        return respond;
//    }
//
//    public class TaskRequestDirections extends AsyncTask<String,Void,List<List<HashMap<String,String>>>>{
//
//        @Override
//        protected String doInBackground(String... strings) {
//            String response = "";
//
//            try {
//                response = requestDirection(strings[0]);
//            }catch (IOException e){
//                e.printStackTrace();
//            }
//
//
//
//            return response;
//        }
//
//        @Override
//        protected void onPostExecute(List<List<HashMap<String,String>>> lists) {
//            //get list route
//            List points=null;
//            PolylineOptions polylineOptions = null;
//
//            for (List<HashMap<String,String>> path:lists){
//                points = new ArrayList();
//                polylineOptions = new PolylineOptions();
//                for (HashMap<String,String> point:path){
//                    double lat = Double.parseDouble(point.get("lat"));
//                    double lng = Double.parseDouble(point.get("lon"));
//
//                    points.add(new LatLng(lat,lng));
//                }
//
//                polylineOptions.addAll(points);
//                polylineOptions.width(15);
//                polylineOptions.color(Color.BLUE);
//                polylineOptions.geodesic(true);
//            }
//
//            if (polylineOptions!=null){
//
//            }
//        }
//    }
//
//
//    private void getMyLocation() {
//
//        if (ActivityCompat.checkSelfPermission(
//                Objects.requireNonNull(context),
//                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                &&
//                ActivityCompat.checkSelfPermission(
//                        context,
//                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//
//            return;
//        }
//        myLocation = LocationServices
//                .FusedLocationApi
//                .getLastLocation(googleApiClient);
//
//        if (myLocation != null) {
////            default_position = new LatLng(
////                    myLocation.getLatitude(),
////                    myLocation.getLongitude()
////            );
////            mMap.animateCamera(
////                    CameraUpdateFactory
////                            .newLatLngZoom(default_position, zoomLevel)
////            );
//        } else {
//            say("Cannot Get Current Location");
//        }
//    }
//
//    @Override
//    public void onConnectionSuspended(int i) {
//        say("onConnectionSuspended: " + String.valueOf(i));
//    }
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//        say("onConnectionFailed: \n" + connectionResult.toString());
//    }
//
//    public void connect() {
//        googleApiClient.connect();
//    }
//
//    public void disconnect() {
//        googleApiClient.disconnect();
//    }
//
//
//    private void say(String msg) {
//        MToast.say(context, msg);
//    }
//}

package HQ.Planner.view.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import HQ.Planner.MainActivity;
import HQ.Planner.R;
import HQ.Planner.model.Event;
import HQ.Planner.model.Planner;
import HQ.Planner.utilities.MToast;


public class PlacesFragment
        extends Fragment
        implements GoogleMap.OnMarkerClickListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener
{
    View view;
    Bundle savedInstanceState;
    float zoomLevel;
    Toolbar toolbar;
    ActionMenuItemView setting_menu_item;
    MapView mapView;
    GoogleMap mMap;
    GoogleApiClient googleApiClient;
    Location myLocation;
    LatLng default_position;

    Button setCamera;
    Button zoom_in;
    Button zoom_out;
    Button prev;
    Button next;


    List<Event> displayEvents;
    List<Marker> markers;

    Marker tempMarker;
    private final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    private void see() {
        zoomLevel = mMap.getCameraPosition().zoom;
        if (mMap == null) {
            say("Map is currently NOT available");
            return;
        }

        if (tempMarker == null) {
            say("No Markers Found");
            return;
        }

        LatLng temp = new LatLng(tempMarker.getPosition().latitude, tempMarker.getPosition().longitude);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(temp, zoomLevel));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_places, container, false);

        this.savedInstanceState = savedInstanceState;

        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(
                    Objects.requireNonNull(getContext()))
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        toolbar = getActivity().findViewById(R.id.activity_home_toolbar);
        toolbar.getMenu().clear();
        toolbar.setTitle("Places");
        toolbar.inflateMenu(R.menu.fragment_places_toolbar_menu);
        toolbar.setOnMenuItemClickListener(barListener);
        setting_menu_item = view.findViewById(R.id.menu_item_setting_time);
        zoom_in = view.findViewById(R.id.z_in);
        zoom_out = view.findViewById(R.id.z_out);
        prev = view.findViewById(R.id.places_prev_location);
        next = view.findViewById(R.id.places_next_location);


        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<Marker> temp = new ArrayList<>();
                int i = 2;
                while (i > 0) {
                    for (Marker marker : markers) {
                        temp.add(marker);
                    }
                    i--;
                }
                for (int k = 0; k < markers.size(); k++) {
                    if (markers.get(k).equals(tempMarker)) {
                        tempMarker = temp.get(k + 1);
                        break;
                    }
                }
                see();
            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<Marker> temp = new ArrayList<>();
                int i = 2;
                while (i > 0) {
                    for (Marker marker : markers) {
                        temp.add(marker);
                    }
                    i--;
                }
                for (int k = markers.size(); k < temp.size(); k++) {
                    if (temp.get(k).equals(tempMarker)) {
                        tempMarker = temp.get(k - 1);
                        break;
                    }
                }
                see();
            }
        });

        zoom_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zoom_in();
            }
        });
        zoom_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zoom_out();
            }
        });

        setCamera = view.findViewById(R.id.places_current_location);
        setCamera.setOnClickListener(setCurrentBtnListener);

        displayEvents = Planner.getShared().getAllEvents();
        markers = new ArrayList<>();
        initMap();
        return view;
    }


    private void initMap() {
        zoomLevel = 11;
        default_position = new LatLng(-37.8136, 144.9631);
        mapView = view.findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);


        mapView.onResume();
        mapView.getMapAsync(mapCallBack);
    }

    public void zoom_in() {
        zoomLevel = mMap.getCameraPosition().zoom;
        if (zoomLevel < 20) {
            zoomLevel++;
        }

        if (mMap == null) {
            say("Map is currently not available");
            return;
        }


        CameraPosition position = mMap.getCameraPosition();
        default_position = position.target;
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(default_position, zoomLevel));
    }

    public void zoom_out() {
        zoomLevel = mMap.getCameraPosition().zoom;
        if (zoomLevel > 2) {
            zoomLevel--;
        }


        if (mMap == null) {
            say("Map is currently not available");
            return;
        }


        CameraPosition position = mMap.getCameraPosition();
        default_position = position.target;
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(default_position, zoomLevel));
    }

    private View.OnClickListener setCurrentBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (mMap == null) {
                say("Map is currently not available");
                return;
            }

            if (googleApiClient != null) {
                getMyLocation();
            } else {
                say("Google API Client disconnected");
                return;
            }

            showEvents();
        }
    };

    private void showEvents() {
        markers = new ArrayList<>();
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        if (displayEvents.size()<1){
            return;
        }
        for (Event event : displayEvents) {
            markers.add(mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(event.getLatitude(), event.getLongitude()))
                    .title(event.getTitle())));
        }

        for (Marker marker : markers) {
            builder.include(marker.getPosition());

        }

        LatLngBounds bounds = builder.build();
        int padding = 200;

        tempMarker = markers.get(0);
        CameraUpdate camera = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        mMap.animateCamera(camera);
    }


    OnMapReadyCallback mapCallBack = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
            if (mMap == null) {
                say("Map is currently not available");
                return;
            }

            if (ActivityCompat.checkSelfPermission(getContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    &&
                    ActivityCompat.checkSelfPermission(getContext(),

                            Manifest.permission.ACCESS_COARSE_LOCATION)
                            !=
                            PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        1);
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mMap.setMapStyle(MapStyleOptions
                    .loadRawResourceStyle(getContext(),R.raw.aubergine));
            mMap.setMyLocationEnabled(true);

            ///////////////////////////


            if (mMap == null) {
                say("Map is currently not available");
                return;
            }


            markers = new ArrayList<>();
            LatLngBounds.Builder builder = new LatLngBounds.Builder();


            if (displayEvents.size()<1){
                return;
            }
            for (Event event : displayEvents) {
                markers.add(mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(event.getLatitude(), event.getLongitude()))
                        .title(event.getTitle())));
            }

            for (Marker marker : markers) {
                builder.include(marker.getPosition());

            }

            LatLngBounds bounds = builder.build();
            int padding = 200;


            tempMarker = markers.get(0);
            CameraUpdate camera = CameraUpdateFactory.newLatLngBounds(bounds, padding);
            mMap.animateCamera(camera);
            zoomLevel = mMap.getCameraPosition().zoom;
            default_position = mMap.getCameraPosition().target;
            say("Map Setup Completed");
        }


    };

    GoogleMap.OnMapLoadedCallback mapLoadedCallback =
            new GoogleMap.OnMapLoadedCallback() {
                @Override
                public void onMapLoaded() {

                }
            };


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        if (markers.contains(marker)) {

            tempMarker = marker;
//
            Event event = displayEvents.get(markers.indexOf(marker));
//
//            ((MainActivity) getActivity()).openEventDetailDialog(this, event);

            say(event.getStartDateString());

        }

        return true;
    }

    private void getMyLocation() {
        if (ActivityCompat.checkSelfPermission(
                Objects.requireNonNull(getContext()),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                &&
                ActivityCompat.checkSelfPermission(
                        getContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        myLocation = LocationServices
                .FusedLocationApi
                .getLastLocation(googleApiClient);

        if (myLocation != null) {
            default_position = new LatLng(
                    myLocation.getLatitude(),
                    myLocation.getLongitude()
            );
//            mMap.animateCamera(
//                    CameraUpdateFactory
//                            .newLatLngZoom(default_position, zoomLevel)
//            );
        } else {
            say("Cannot Get Current Location");
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getMyLocation();

                } else {
                    say("Permission Denied");
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    @Override
    public void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(@NonNull Bundle bundle) {
        getMyLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {
        say("onConnectionSuspended: " + String.valueOf(i));
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        say("onConnectionFailed: \n" + connectionResult.toString());
    }


    private Toolbar.OnMenuItemClickListener barListener =
            new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    MainActivity activity = ((MainActivity) getActivity());
                    switch (item.getItemId()) {
                        case R.id.menu_item_setting_time:
                            say("Remind's Settings");
                            activity.openSettingTimeDialog();
                            break;

                        default:
                            say(String.valueOf(item.getItemId()));
                            break;
                    }
                    return true;
                }
            };

    private void say(String msg) {
        MToast.say(getContext(), msg);
    }


}

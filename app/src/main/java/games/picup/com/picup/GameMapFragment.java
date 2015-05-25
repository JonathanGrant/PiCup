package games.picup.com.picup;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.pm.PackageManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.List;

public class GameMapFragment extends Fragment implements OnMapReadyCallback, LocationListener {

    MapView mapView;
    GoogleMap googleMap;
    public LocationManager locationManager;
    private static final long MIN_TIME = 400;
    private static final float MIN_DISTANCE = 1000;
    double tLX = 0, tLR = 0, bLX = 0, bLR = 0, tRX = 0, tRR = 0, bRX = 0, bRR = 0, cX = 0, cY = 0, zoomSize = 17.0f;
    String Location = "Crom";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);


        return view;

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
//        googleMap.addMarker(new MarkerOptions()
//                .position(new LatLng(0, 0))
//                .title("Marker"));
    }

    @Override
    public void onResume() {
        mapView.onResume();

        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

        mapView.onLowMemory();
    }

    private void setUpMapIfNeeded() {

        MapsInitializer.initialize(getActivity());

        if (googleMap == null) {
            googleMap = ((MapView) getView().findViewById(R.id.store_map_frag)).getMap();
            if (googleMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.setMyLocationEnabled(true);
        onLocationChanged(googleMap.getMyLocation());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = (MapView) getView().findViewById(R.id.store_map_frag);
        mapView.onCreate(savedInstanceState);

        setUpMapIfNeeded();

    }

    public void setMapValues(){
        if(Location.equals("Crom")){
            TypedValue outValue = new TypedValue();
            getResources().getValue(R.dimen.Crom_top_left_x, outValue, true);
            tLX = outValue.getFloat();
            getResources().getValue(R.dimen.Crom_top_left_y, outValue, true);
            tLR = outValue.getFloat();
            getResources().getValue(R.dimen.Crom_bottom_left_x, outValue, true);
            bLX = outValue.getFloat();
            getResources().getValue(R.dimen.Crom_bottom_left_y, outValue, true);
            bLR = outValue.getFloat();
            getResources().getValue(R.dimen.Crom_top_right_x, outValue, true);
            tRX = outValue.getFloat();
            getResources().getValue(R.dimen.Crom_top_right_y, outValue, true);
            tRR = outValue.getFloat();
            getResources().getValue(R.dimen.Crom_bottom_right_x, outValue, true);
            bRX = outValue.getFloat();
            getResources().getValue(R.dimen.Crom_bottom_right_y, outValue, true);
            bRR = outValue.getFloat();
            getResources().getValue(R.dimen.Crom_center_x, outValue, true);
            cX = outValue.getFloat();
            getResources().getValue(R.dimen.Crom_center_y, outValue, true);
            cY = outValue.getFloat();
            getResources().getValue(R.dimen.Crom_zoom, outValue, true);
            zoomSize = outValue.getFloat();
        } else if(Location.equals("Brit")){
            TypedValue outValue = new TypedValue();
            getResources().getValue(R.dimen.Brit_top_left_x, outValue, true);
            tLX = outValue.getFloat();
            getResources().getValue(R.dimen.Brit_top_left_y, outValue, true);
            tLR = outValue.getFloat();
            getResources().getValue(R.dimen.Brit_bottom_left_x, outValue, true);
            bLX = outValue.getFloat();
            getResources().getValue(R.dimen.Brit_bottom_left_y, outValue, true);
            bLR = outValue.getFloat();
            getResources().getValue(R.dimen.Brit_top_right_x, outValue, true);
            tRX = outValue.getFloat();
            getResources().getValue(R.dimen.Brit_top_right_y, outValue, true);
            tRR = outValue.getFloat();
            getResources().getValue(R.dimen.Brit_bottom_right_x, outValue, true);
            bRX = outValue.getFloat();
            getResources().getValue(R.dimen.Brit_bottom_right_y, outValue, true);
            bRR = outValue.getFloat();
            getResources().getValue(R.dimen.Brit_center_x, outValue, true);
            cX = outValue.getFloat();
            getResources().getValue(R.dimen.Brit_center_y, outValue, true);
            cY = outValue.getFloat();
            getResources().getValue(R.dimen.Brit_zoom, outValue, true);
            zoomSize = outValue.getFloat();
        } else if(Location.equals("MCar")){
            TypedValue outValue = new TypedValue();
            getResources().getValue(R.dimen.MCar_top_left_x, outValue, true);
            tLX = outValue.getFloat();
            getResources().getValue(R.dimen.MCar_top_left_y, outValue, true);
            tLR = outValue.getFloat();
            getResources().getValue(R.dimen.MCar_bottom_left_x, outValue, true);
            bLX = outValue.getFloat();
            getResources().getValue(R.dimen.MCar_bottom_left_y, outValue, true);
            bLR = outValue.getFloat();
            getResources().getValue(R.dimen.MCar_top_right_x, outValue, true);
            tRX = outValue.getFloat();
            getResources().getValue(R.dimen.MCar_top_right_y, outValue, true);
            tRR = outValue.getFloat();
            getResources().getValue(R.dimen.MCar_bottom_right_x, outValue, true);
            bRX = outValue.getFloat();
            getResources().getValue(R.dimen.MCar_bottom_right_y, outValue, true);
            bRR = outValue.getFloat();
            getResources().getValue(R.dimen.MCar_center_x, outValue, true);
            cX = outValue.getFloat();
            getResources().getValue(R.dimen.MCar_center_y, outValue, true);
            cY = outValue.getFloat();
            getResources().getValue(R.dimen.MCar_zoom, outValue, true);
            zoomSize = outValue.getFloat();
        } else if(Location.equals("MCal")){
            TypedValue outValue = new TypedValue();
            getResources().getValue(R.dimen.MCal_top_left_x, outValue, true);
            tLX = outValue.getFloat();
            getResources().getValue(R.dimen.MCal_top_left_y, outValue, true);
            tLR = outValue.getFloat();
            getResources().getValue(R.dimen.MCal_bottom_left_x, outValue, true);
            bLX = outValue.getFloat();
            getResources().getValue(R.dimen.MCal_bottom_left_y, outValue, true);
            bLR = outValue.getFloat();
            getResources().getValue(R.dimen.MCal_top_right_x, outValue, true);
            tRX = outValue.getFloat();
            getResources().getValue(R.dimen.MCal_top_right_y, outValue, true);
            tRR = outValue.getFloat();
            getResources().getValue(R.dimen.MCal_bottom_right_x, outValue, true);
            bRX = outValue.getFloat();
            getResources().getValue(R.dimen.MCal_bottom_right_y, outValue, true);
            bRR = outValue.getFloat();
            getResources().getValue(R.dimen.MCal_center_x, outValue, true);
            cX = outValue.getFloat();
            getResources().getValue(R.dimen.MCal_center_y, outValue, true);
            cY = outValue.getFloat();
            getResources().getValue(R.dimen.MCal_zoom, outValue, true);
            zoomSize = outValue.getFloat();
        }
    }

    public void changeLocation(String Loc){
        Location = Loc;
        setMapValues();
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(location.getLatitude(), location.getLongitude()), 3));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                    // Sets the center of the map to user's current location
                    .target(new LatLng(cX, cY))
                    .zoom((float) zoomSize)                   // Sets the zoom
                    .build();                   // Creates a CameraPosition from the builder
        googleMap.addPolygon(new PolygonOptions().geodesic(true)
                        .add(new LatLng(tLX, tLR))  // top left
                        .add(new LatLng(bLX, bLR))  // bottom left
                        .add(new LatLng(bRX, bRR))  // bottom right
                        .add(new LatLng(tRX, tRR)) //top right
                        .add(new LatLng(tLX, tLR)) // top left
                        .fillColor(Color.RED)
        );
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void onLocationChanged(Location location) {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        if (location != null) {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(location.getLatitude(), location.getLongitude()), 13));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    // Sets the center of the map to user's current location
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))
                    .zoom(13)                   // Sets the zoom
                    .build();                   // Creates a CameraPosition from the builder
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


        } else {

            criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
            // Finds a provider that matches the criteria
            String provider = locationManager.getBestProvider(criteria, true);
            // Use the provider to get the last known location
            location = locationManager.getLastKnownLocation(provider);
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}


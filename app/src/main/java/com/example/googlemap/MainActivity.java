package com.example.googlemap;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.googlemap.databinding.ActivityMainBinding;
import com.google.android.gms.maps.model.PointOfInterest;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener,GoogleMap.OnPoiClickListener {

    private GoogleMap mMap;
    private ActivityMainBinding binding;
    private UiSettings mUiSettings;
    Location location;
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        text=(TextView) findViewById(R.id.textv);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mUiSettings=mMap.getUiSettings();
        // Add a marker in Sydney and move the camera
        LatLng jimma = new LatLng(7.6736450324877294, 36.83693743308046);
        mMap.addMarker(new MarkerOptions().position(jimma).title("Marker in jimma"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(jimma));
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        // mMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        // Display traffic.
        mMap.setTrafficEnabled(true);
        mMap.setMyLocationEnabled(true);
        mMap.setIndoorEnabled(true);
        mMap.getUiSettings().setIndoorLevelPickerEnabled(true);

        mUiSettings.setCompassEnabled(true);
        mUiSettings.setZoomControlsEnabled(true);
        //  mUiSettings.setMyLocationButtonEnabled(true);
        /*
        *  mUiSettings.setCompassEnabled(true);
        *  mUiSettings.setZoomControlsEnabled(isChecked(R.id.zoom_buttons_toggle));
        mUiSettings.setCompassEnabled(isChecked(R.id.compass_toggle));
        mUiSettings.setMyLocationButtonEnabled(isChecked(R.id.mylocationbutton_toggle));
        mUiSettings.setScrollGesturesEnabled(isChecked(R.id.scroll_toggle));
        mUiSettings.setZoomGesturesEnabled(isChecked(R.id.zoom_gestures_toggle));
        mUiSettings.setTiltGesturesEnabled(isChecked(R.id.tilt_toggle));
        mUiSettings.setRotateGesturesEnabled(isChecked(R.id.rotate_toggle));
        * mMap.de
        * */

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        text.setText("lat: "+location.getLatitude()+
                " "+"long: "+location.getLongitude());
    }
    public void searchLocation(View view) {
        //  EditText locationSearch = (EditText) findViewById(R.id.editText);
        String location = "jimma";
        //String location = locationSearch.getText().toString();
        List<Address> addressList = null;

        if (location != null || !location.equals("")) {
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location, 1);

            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng).title(location));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            Toast.makeText(getApplicationContext(), address.getLatitude() + " " + address.getLongitude(), Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onPoiClick(PointOfInterest poi) {
        Toast.makeText(getApplicationContext(), "Clicked: " +
                        poi.name + "\nPlace ID:" + poi.placeId +
                        "\nLatitude:" + poi.latLng.latitude +
                        " Longitude:" + poi.latLng.longitude,
                Toast.LENGTH_SHORT).show();
        text.setText( poi.latLng.latitude+"");
    }
}
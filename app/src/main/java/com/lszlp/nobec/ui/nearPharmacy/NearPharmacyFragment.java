package com.lszlp.nobec.ui.nearPharmacy;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.libraries.places.api.Places;
import com.lszlp.nobec.R;
import com.lszlp.nobec.databinding.FragmentHomeBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

public class NearPharmacyFragment extends Fragment implements  GoogleMap.OnMapLongClickListener, OnMapReadyCallback{
    private static final String TAG = "YER";


    /*
    AIzaSyAnDmTZaaXEVE-5ygEpFpOvF-VWG2oHLjU
    
    apı key
     */
    PlacesClient placesClient;
    MapView mMapView;
    private GoogleMap googleMap;

    private  GoogleMap mMap;

    private FragmentHomeBinding binding;
    ActivityResultLauncher<String> permissionLauncher;
    LocationManager locationManager;
    LocationListener locationListener;
    SharedPreferences sharedPreferences;
    boolean info;


    private void registerLauncher() {
        permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                if (result) {
//permission granted
                    if (ContextCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
                        Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (lastLocation != null){
                            LatLng lastUserLocation = new LatLng(lastLocation.getLatitude(),lastLocation.getLongitude());
                            mMap.addMarker(new MarkerOptions().position(lastUserLocation).title("Buradasınız").snippet("Marker Description"));

                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastUserLocation,15));
                        }
                    }
                } else{
                    Toast.makeText(getContext(),"izin ihtiyacı",Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NearPharmacyViewModel nearPharmacyViewModel =
                new ViewModelProvider(this).get(NearPharmacyViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        // Initialize Places.
        Places.initialize(getContext(), getResources().getString(R.string.google_map_key_place));

        // Create a new Places client instance.
        placesClient = Places.createClient(getContext());
        registerLauncher();
        sharedPreferences = getContext().getSharedPreferences("com.lszlp.nobec",Context.MODE_PRIVATE);
        info = false;
        mMapView = (MapView) root.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }



        mMapView.getMapAsync(this);

                /*

                new OnMapReadyCallback() {

                    @Override

            public void onMapReady(GoogleMap mMap) {
                //googleMap = mMap;
                  locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
                 locationListener = new LocationListener() {
                    @Override
                    public void onLocationChanged(@NonNull Location location) {


                        info = sharedPreferences.getBoolean("info",false);
                        if (info == false){
                            LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                            mMap.addMarker(new MarkerOptions().position(userLocation).title("Buradasınız").snippet("Marker Description"));
                            // For zooming automatically to the location of the marker
                            CameraPosition cameraPosition = new CameraPosition.Builder().target(userLocation).zoom(12).build();
                            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
sharedPreferences.edit().putBoolean("info",true).apply();
                        }

                       System.out.println("konum: " + location.toString());
                        // For showing a move to my location button


                    }
                };

if (ContextCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
    //request permission

    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
        Snackbar.make(binding.getRoot(), "Haritalar için izin isteniyor", Snackbar.LENGTH_INDEFINITE).setAction("İzin ver", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //request permission
                permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
            }
        }).show();

    } else {
        //request permission
        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
    }
}else {

    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
    Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    if (lastLocation != null){
        LatLng lastUserLocation = new LatLng(lastLocation.getLatitude(),lastLocation.getLongitude());
        mMap.addMarker(new MarkerOptions().position(lastUserLocation).title("Buradasınız").snippet("Marker Description"));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastUserLocation,15));
    }

    mMap.setMyLocationEnabled(true);

}


            }

        });
*/

        final TextView textView = binding.textHome;
        nearPharmacyViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(latLng));
        System.out.println("konumlar :" + latLng);
        findNearPharmacy(latLng);
    }

    public String getCityName(LatLng latLng) {
        String cityName = "";
        Geocoder gcd = new Geocoder(getContext(), Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = gcd.getFromLocation(latLng.latitude, latLng.longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses != null && addresses.size() > 0) {
            String locality = addresses.get(0).getAdminArea();
            System.out.println("Şehir " + locality);
            cityName = locality;
        }

        return cityName ;

    }
public String getPhoneNumberFromPlace (String place_id) {

    //AtomicReference<String> phoneNumber = new AtomicReference<>("");
    List<String> phoneNumber = new ArrayList<>();
    // Define a Place ID.


    return (phoneNumber.get(0));

/*
 //  https://maps.googleapis.com/maps/api/place/details/json?place_id=ChIJ97jopCEUyhQRsFGA7yhiVG8&fields=formatted_phone_number&key=AIzaSyDa7QFIp37mG40p81Ckj7r6LSvYwH1Uty4
        String url = "https://maps.googleapis.com/maps/api/place/details/json?place_id=" + place_id+
                 "&fields=name%2Cformatted_phone_number&key="+ getResources().getString(R.string.google_map_key_place);
        //new PlaceIDTask().execute(url);
 */

}
    public void findNearPharmacy(LatLng location){
        //maps.googleapis.com/maps/api/place/nearbysearch/


        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?keyword=nobetci_eczane,drugstore" +
                "&location=" + location.latitude + ","+ location.longitude +
                "&radius=3000" +
                "&type=pharmacy" +
                "&key=" + getResources().getString(R.string.google_map_key_place);

        System.out.println("URL-> :" + url);
        new PlaceTask().execute(url);
    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

       mMap = googleMap;
       mMap.setOnMapLongClickListener(this);
        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {


                info = sharedPreferences.getBoolean("info",false);
                if (info == false){
                    LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(userLocation).title("Buradasınız").snippet("Marker Description"));
                    // For zooming automatically to the location of the marker
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(userLocation).zoom(12).build();
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                   System.out.println("Bulunduğun şehir  " + getCityName(userLocation));
                   findNearPharmacy(userLocation);
                    sharedPreferences.edit().putBoolean("info",true).apply();
                }

                System.out.println("konum: " + location.toString());
                // For showing a move to my location button


            }
        };

        if (ContextCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //request permission

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                Snackbar.make(binding.getRoot(), "Haritalar için izin isteniyor", Snackbar.LENGTH_INDEFINITE).setAction("İzin ver", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //request permission
                        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
                    }
                }).show();

            } else {
                //request permission
                permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
            }
        }else {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
            Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastLocation != null){
                LatLng lastUserLocation = new LatLng(lastLocation.getLatitude(),lastLocation.getLongitude());
                mMap.addMarker(new MarkerOptions().position(lastUserLocation).title("Buradasınız").snippet(getCityName(lastUserLocation)));
                System.out.println("Bulunduğun şehir  " + getCityName(lastUserLocation));
                findNearPharmacy(lastUserLocation);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastUserLocation,15));

            }

      mMap.setMyLocationEnabled(true);

       }


    }


    private class PlaceTask extends AsyncTask<String,Integer,String> {


        @Override
        protected String doInBackground(String... strings) {
            String data = null;
            try {
                data = downloadUrl(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
    System.out.println("veriler : " + data);
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            //execute parser task
            new ParserTask().execute(s);
            super.onPostExecute(s);
        }
    }

    private String downloadUrl(String string) throws IOException {
        URL url = new URL(string);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.connect();
        InputStream stream = httpURLConnection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder stringBuilder = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null) {
    stringBuilder.append(line);
        }
        String data = stringBuilder.toString();
        reader.close();
        return data;

    }

    private class ParserTask extends  AsyncTask<String,Integer,List<HashMap<String,String>>> {
        @Override
        protected List<HashMap<String, String>> doInBackground(String... strings) {
            //Create JSONparser class
            JSONParser jsonParser = new JSONParser();
            List<HashMap<String, String>> mapList = null;
            JSONObject object = null;
            try {
                object = new JSONObject(strings[0]);
                mapList = jsonParser.parseResult(object);
                System.out.println("maplist " + mapList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // return mapList
            return mapList;
        }

        @Override
        protected void onPostExecute(List<HashMap<String, String>> hashMaps) {
            //clear map
            //  mMap.clear();
            for (int i = 0; i < hashMaps.size(); i++) {
                HashMap<String, String> hashMapList = hashMaps.get(i);
                final String placeId = hashMapList.get("place_id");

// Specify the fields to return.
                final List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.PHONE_NUMBER);

// Construct a request object, passing the place ID and fields array.
                final FetchPlaceRequest request = FetchPlaceRequest.newInstance(placeId, placeFields);


                placesClient.fetchPlace(request).addOnSuccessListener((response) -> {
                    Place place = response.getPlace();


                    //get latitude
                    double lat = Double.parseDouble(hashMapList.get("lat"));
                    //get longitude
                    double lng = Double.parseDouble(hashMapList.get("lng"));
                    //get name
                    String name = hashMapList.get("name");
                    //get PlaceID
                    //String placeId = hashMapList.get("place_id");

                    // get phone number
                    String phoneNumber = (place.getPhoneNumber());
                    if (place.getPhoneNumber() == null ){
                        phoneNumber = "Telefon Mevcut Değil!";
                    }
                    ;
                    // concat latitude and longitude;
                    LatLng latLng = new LatLng(lat, lng);
                    //initialize marker options
                    MarkerOptions markerOptions = new MarkerOptions();
                    //set positions
                    markerOptions.position(latLng);
                    //set title

                    markerOptions.title(name);
                    //set subtitle
                    markerOptions.snippet(phoneNumber);
                    //customize icon

                    markerOptions.icon(bitmapDescriptor(getContext(), R.drawable.ic_pharmacybluelogo)
                    );
                    //add marker on map

                    mMap.addMarker(markerOptions);


                    System.out.println("telefon verisi :" + phoneNumber);
                    Log.i(TAG, "Place found: " + place.getName() + "Phone " + phoneNumber);

                }).addOnFailureListener((exception) -> {
                    if (exception instanceof ApiException) {
                        final ApiException apiException = (ApiException) exception;
                        Log.e(TAG, "Place not found: " + exception.getMessage());
                        final int statusCode = apiException.getStatusCode();
                        // TODO: Handle error with given status code.
                    }
                });


                super.onPostExecute(hashMaps);
            }

        }

        public BitmapDescriptor bitmapDescriptor(Context context, int vectorResId) {
            Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
            vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
            Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            vectorDrawable.draw(canvas);
            return BitmapDescriptorFactory.fromBitmap(bitmap);
        }

        private class PlaceIDTask extends AsyncTask<String, Integer, String> {


            @Override
            protected String doInBackground(String... strings) {
                String data = null;
                try {
                    data = downloadUrl(strings[0]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("PlaceID veriler : " + data);
                return data;
            }

            @Override
            protected void onPostExecute(String s) {
                //execute parser task
                new ParserIDTask().execute(s);
                super.onPostExecute(s);
            }
        }

        private class ParserIDTask extends AsyncTask<String, Integer, List<HashMap<String, String>>> {
            @Override
            protected List<HashMap<String, String>> doInBackground(String... strings) {
                //Create JSONparserPlaceID class
                JSONParserPlaceID jsonParser = new JSONParserPlaceID();
                List<HashMap<String, String>> mapList = null;
                JSONObject object = null;
                try {
                    object = new JSONObject(strings[0]);
                    mapList = jsonParser.parseResult(object);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // return mapList
                return mapList;
            }

            @Override
            protected void onPostExecute(List<HashMap<String, String>> hashMaps) {
                //clear map
                //  mMap.clear();
                for (int i = 0; i < hashMaps.size(); i++) {
                    HashMap<String, String> hashMapList = hashMaps.get(i);
                    //get latitude
                    String phoneNumber = hashMapList.get("formatted_phone_number");

                    System.out.println("telefonlar" + phoneNumber);

                }

                super.onPostExecute(hashMaps);
            }

        }
    }

}
/*

 */
package com.example.welove;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.welove.location.Location;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FragMap extends Fragment implements OnMapReadyCallback {
    private View view;
    private GoogleMap map;
    private MainActivity mainActivity;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_map, container, false);
        MapView mapView = (MapView) view.findViewById(R.id.googleMap);
        mainActivity =(MainActivity) getActivity();
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);



        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
        Location location = new Location();
        location.getLocationProcess(view.getContext(), this, mainActivity, new LocationCallbackInner ( ));

        googleMap.addMarker(getMarker("이디야커피", "태릉입구역점", 37.618640, 127.077535));
        googleMap.addMarker(getMarker("아고라커피", "태릉입구역점", 37.620737, 127.078510));

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                return false;
            }
        });
        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                startActivity(new Intent(getContext(), CafeDetailActivity.class));
            }
        });


        FirebaseDatabase database = FirebaseDatabase.getInstance(); // 파이어베이스 데이터베이스 연동
        DatabaseReference databaseReference;
        databaseReference = database.getReference("cafeList_ Gongneung"); // DB 테이블 연결
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 파이어베이스 데이터베이스의 데이터를 받아오는 곳
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) { // 반복문으로 데이터 List를 추출해냄
                    Log.d("Cafe!", "Cafe!");
                    cafeList cafe = snapshot.getValue(cafeList.class); // 만들어뒀던 User 객체에 데이터를 담는다.
                    double latitude = cafe.getLatitude();
                    double longitude = cafe.getLongitude();

                    map.addMarker(getMarker(cafe.getCafeName(), cafe.getAddress(), longitude, latitude));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 디비를 가져오던중 에러 발생 시
                Log.e("Fraglike", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        });
    }

    private MarkerOptions getMarker(String title, String snippet, Double lat, Double longi){
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(lat, longi));
        markerOptions.title(title);
        markerOptions.snippet(snippet);
        return markerOptions;
    }

    private class LocationCallbackInner extends LocationCallback {
        public LocationCallbackInner() {
            super();
        }

        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            android.location.Location location = locationResult.getLocations().get(0);
            Log.d("onLocationResult", "location");
            LatLng lg = new LatLng(location.getLatitude(), location.getLongitude());
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(lg, 17f));
        }

        @Override
        public void onLocationAvailability(LocationAvailability locationAvailability) {
            super.onLocationAvailability(locationAvailability);
        }
    }
}

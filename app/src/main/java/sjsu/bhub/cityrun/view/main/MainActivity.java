package sjsu.bhub.cityrun.view.main;

import android.Manifest;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import sjsu.bhub.cityrun.BaseActivity;
import sjsu.bhub.cityrun.R;
import sjsu.bhub.cityrun.data.DrawerMenuVO;
import sjsu.bhub.cityrun.databinding.ActivityMainBinding;
import sjsu.bhub.cityrun.service.StepCountService;
import sjsu.bhub.cityrun.utils.PermissionUtil;
import sjsu.bhub.cityrun.view.store.StoreActivity;
import sjsu.bhub.cityrun.view.unity.UnityPlayerActivity;

import static sjsu.bhub.cityrun.R.drawable.icon_distance;
import static sjsu.bhub.cityrun.R.drawable.icon_fire;
import static sjsu.bhub.cityrun.R.drawable.icon_step;
import static sjsu.bhub.cityrun.R.drawable.icon_treasure;

public class MainActivity extends BaseActivity<ActivityMainBinding> implements OnMapReadyCallback {
    private final String TAG = "MainActivity";

    private DrawerMenuAdapter adapter;
    private String serviceData;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PermissionUtil.getPermission(this);

        initView();
        initDrawerMenu();
        startStepCountService();
    }

    @Override
    protected void onDestroy() {
        finishService();
        super.onDestroy();
    }

    public void finishService() {
        Intent StepCountService = new Intent(this, StepCountService.class);
        stopService(StepCountService);
    }

    private void initView() {

        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment)fragmentManager.findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        binding.toolbar.buttonToolbarRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.drawerLayout.isDrawerOpen(binding.drawerMenu)) {
                    binding.drawerLayout.closeDrawer(binding.drawerMenu);
                } else {
                    binding.drawerLayout.openDrawer(binding.drawerMenu);
                }
            }
        });

        binding.buttonAr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent unityActivity = new Intent(getApplicationContext(), UnityPlayerActivity.class);
                startActivity(unityActivity);
            }
        });
    }

    private void initDrawerMenu() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);

        /*layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 0;
            }
        });*/

        binding.layoutDrawerMenu.recyclerViewMenu.setLayoutManager(layoutManager);

        ArrayList<DrawerMenuVO> menuList = new ArrayList<>();
        menuList.add(new DrawerMenuVO(icon_step, "5000", "STEP"));
        menuList.add(new DrawerMenuVO(icon_treasure, "2000", "GOLD"));
        menuList.add(new DrawerMenuVO(icon_fire, "400", "Kcal"));
        menuList.add(new DrawerMenuVO(icon_distance, "5", "km"));
        //menuList.add(new DrawerMenuVO(R.drawable.icon_cart, "Store",""));

        adapter = new DrawerMenuAdapter(this, menuList);
        binding.layoutDrawerMenu.recyclerViewMenu.setAdapter(adapter);

        binding.layoutDrawerMenu.storeLinearLayout.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), StoreActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.enter_no_anim, R.anim.exit_no_anim);
                    }
        });

    }

    ////////////////////////////////////// map

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private GoogleMap googleMap;
    private boolean stateMarker = true;

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        if(stateMarker) {
            LatLng box = new LatLng(37.335657, -121.884988);
            Drawable drawable = getResources().getDrawable(R.drawable.marker);
            Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(box);
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bitmap));
            googleMap.addMarker(markerOptions);
            stateMarker = false;
        }

        googleMap.setOnMyLocationButtonClickListener(onMyLocationButtonClickListener);
        googleMap.setOnMyLocationClickListener(onMyLocationClickListener);
        enableMyLocationIfPermitted();

        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.setMinZoomPreference(11);
    }

    private void enableMyLocationIfPermitted() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else if (googleMap != null) {
            googleMap.setMyLocationEnabled(true);
        }
    }

    private void showDefaultLocation() {
        Toast.makeText(this, "Location permission not granted, " +
                        "showing default location",
                Toast.LENGTH_SHORT).show();
        LatLng redmond = new LatLng(47.6739881, -122.121512);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(redmond));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    enableMyLocationIfPermitted();
                } else {
                    showDefaultLocation();
                }
                return;
            }

        }
    }

    private GoogleMap.OnMyLocationButtonClickListener onMyLocationButtonClickListener =
            new GoogleMap.OnMyLocationButtonClickListener() {
                @Override
                public boolean onMyLocationButtonClick() {
                    googleMap.setMinZoomPreference(15);
                    return false;
                }
            };

    private GoogleMap.OnMyLocationClickListener onMyLocationClickListener = new GoogleMap.OnMyLocationClickListener() {
        @Override
                public void onMyLocationClick(@NonNull Location location) {

                    googleMap.setMinZoomPreference(12);

                    CircleOptions circleOptions = new CircleOptions();
                    circleOptions.center(new LatLng(location.getLatitude(),
                            location.getLongitude()));

                    circleOptions.radius(200);
                    circleOptions.fillColor(Color.RED);
                    circleOptions.strokeWidth(6);

                    googleMap.addCircle(circleOptions);
                }
            };


    //////////////////////////////////////////////step count

    private void startStepCountService() {
        Intent serviceIntent = new Intent(getApplicationContext(), StepCountService.class);
        BroadcastReceiver receiver = new PlayingReceiver();

        try {
            IntentFilter mainFilter = new IntentFilter("make.a.yong.manbo");
            registerReceiver(receiver, mainFilter);
            startService(serviceIntent);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
    }

    class PlayingReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            serviceData = intent.getStringExtra("stepService");
        }
    }
}

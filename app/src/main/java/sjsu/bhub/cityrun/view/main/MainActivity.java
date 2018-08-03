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
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

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
import sjsu.bhub.cityrun.data.StepData;
import sjsu.bhub.cityrun.databinding.ActivityMainBinding;
import sjsu.bhub.cityrun.service.StepCountService;
import sjsu.bhub.cityrun.utils.PermissionUtil;
import sjsu.bhub.cityrun.view.store.StoreActivity;
import sjsu.bhub.cityrun.view.unity.UnityPlayerActivity;

public class MainActivity extends BaseActivity<ActivityMainBinding> implements OnMapReadyCallback {
    private final String TAG = "MainActivity";

    private DrawerMenuAdapter adapter;
    private String serviceData;
    private ArrayList<DrawerMenuVO> menuList;

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
///////////////////////////////////////////////////////////// unity
        binding.buttonAr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent unityActivity = new Intent(getApplicationContext(), UnityPlayerActivity.class);
                startActivity(unityActivity);
            }
        });
    }

    private void initDrawerMenu() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);
        binding.layoutDrawerMenu.recyclerViewMenu.setLayoutManager(layoutManager);
        binding.layoutDrawerMenu.recyclerViewMenu.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        binding.layoutDrawerMenu.recyclerViewMenu.setHasFixedSize(true);


        ArrayList<DrawerMenuVO> menuList = new ArrayList<>();
        menuList.add(new DrawerMenuVO(R.drawable.icon_step, "STEP", "/ 10000", StepData.Step));
        menuList.add(new DrawerMenuVO(R.drawable.icon_coin, "POINT", "gold", 2000));
        menuList.add(new DrawerMenuVO(R.drawable.icon_treasure, "TREASURE", "box", 20));
        menuList.add(new DrawerMenuVO(R.drawable.icon_fire, "CALORIE", "kcal",400));
        menuList.add(new DrawerMenuVO(R.drawable.icon_distance, "DISTANCE", "km",5));
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

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private GoogleMap googleMap;
    private boolean stateMarker = true;
    private boolean mCompassEnabled;
    LocationManager manager;

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
            showDefaultLocation();
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
        googleMap.setMinZoomPreference(12);
        LatLng redmond = new LatLng(37.336786, -121.878610);
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
            adapter.getItemList().get(0).setStatus(Integer.parseInt(serviceData));
            adapter.notifyItemChanged(0);
        }
    }
}

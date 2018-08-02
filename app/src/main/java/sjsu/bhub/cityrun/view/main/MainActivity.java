package sjsu.bhub.cityrun.view.main;

import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import sjsu.bhub.cityrun.BaseActivity;
import sjsu.bhub.cityrun.R;
import sjsu.bhub.cityrun.data.DrawerMenuVO;
import sjsu.bhub.cityrun.databinding.ActivityMainBinding;
import sjsu.bhub.cityrun.service.LocationService;
import sjsu.bhub.cityrun.service.StepCountService;
import sjsu.bhub.cityrun.utils.PermissionUtil;

public class MainActivity extends BaseActivity<ActivityMainBinding> implements OnMapReadyCallback {
    private final String TAG = "MainActivity";
    public static final String EXTRA_LATITUDE = "EXTRA_LATITUDE";
    public static final String EXTRA_LONGITUDE = "EXTRA_LONGITUDE";

    private DrawerMenuAdapter adapter;

    private String serviceData;
    private GoogleMap googleMap;

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
        startLocationService();
        startStepCountService();
    }

    @Override
    protected void onDestroy() {
        finishService();
        super.onDestroy();
    }

    public void finishService() {
        Intent LocationService = new Intent(this, LocationService.class);
        stopService(LocationService);
        Intent StepCountService = new Intent(this, StepCountService.class);
        stopService(StepCountService);
    }

    private void initView() {

        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment)fragmentManager
                .findFragmentById(R.id.map);
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
        menuList.add(new DrawerMenuVO(R.drawable.icon_step, "5000", "STEP"));
        menuList.add(new DrawerMenuVO(R.drawable.icon_treasure, "2000", "GOLD"));
        menuList.add(new DrawerMenuVO(R.drawable.icon_fire, "400", "Kcal"));
        menuList.add(new DrawerMenuVO(R.drawable.icon_distance, "5", "km"));
//        menuList.add(new DrawerMenuVO(R.drawable.icon_store, "Store"));

        adapter = new DrawerMenuAdapter(this, menuList);
        binding.layoutDrawerMenu.recyclerViewMenu.setAdapter(adapter);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;

        updateLocation(37.335657, -121.884988);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        processLocationCallBack(intent);
    }

    private void startLocationService() {
        Intent intent = new Intent(getApplicationContext(), LocationService.class);
        startService(intent);
    }

    private void processLocationCallBack(Intent intent) {
        if(intent != null) {
            double latitude = intent.getDoubleExtra(EXTRA_LATITUDE, 0);
            double longitude = intent.getDoubleExtra(EXTRA_LONGITUDE, 0);

//            showSnackBar("latitude: " + latitude + ",longitude: " + longitude);
            updateLocation(latitude, longitude);
        }
    }

    private void updateLocation(double lat, double lon){
        if(googleMap == null){
            return;
        }
        LatLng location = new LatLng(lat, lon);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(location);
        googleMap.addMarker(markerOptions);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat, lon)));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }

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

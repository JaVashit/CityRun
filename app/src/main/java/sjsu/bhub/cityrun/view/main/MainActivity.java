package sjsu.bhub.cityrun.view.main;

import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

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
import sjsu.bhub.cityrun.view.store.StoreActivity;
import sjsu.bhub.cityrun.view.unity.UnityPlayerActivity;

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
        menuList.add(new DrawerMenuVO(R.drawable.icon_step, "STEP", "/ 10000",5000));
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

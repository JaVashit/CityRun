package sjsu.bhub.cityrun;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import sjsu.bhub.cityrun.databinding.ActivityMainBinding;
import sjsu.bhub.cityrun.service.StepCountService;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    private final String TAG = "MainActivity";

    private String serviceData;
    private boolean flag = true;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        startStepCountService();
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

    private void initView() {
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

    class PlayingReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("PlayignReceiver", "IN");
            serviceData = intent.getStringExtra("stepService");
            binding.layoutDrawerMenu.textStepCount.setText(serviceData);
            Toast.makeText(getApplicationContext(), "Playing game", Toast.LENGTH_SHORT).show();
        }
    }
}

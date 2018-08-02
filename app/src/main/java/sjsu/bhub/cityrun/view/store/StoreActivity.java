package sjsu.bhub.cityrun.view.store;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import sjsu.bhub.cityrun.BaseActivity;
import sjsu.bhub.cityrun.R;
import sjsu.bhub.cityrun.databinding.ActivityStoreBinding;

public class StoreActivity extends BaseActivity<ActivityStoreBinding> {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_store;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView(){


        tabLayout = binding.storeTabLayout;

        tabLayout.addTab(tabLayout.newTab().setText("electronics"));
        tabLayout.addTab(tabLayout.newTab().setText("electronics"));
        tabLayout.addTab(tabLayout.newTab().setText("electronics"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // Initializing ViewPager
        viewPager = binding.storePager;

        // Creating TabPagerAdapter adapter
        TabAdapter pagerAdapter = new TabAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        // Set TabSelectedListener
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
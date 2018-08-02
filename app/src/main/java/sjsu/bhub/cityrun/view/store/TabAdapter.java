package sjsu.bhub.cityrun.view.store;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TabAdapter extends FragmentStatePagerAdapter {

    // Count number of tabs
    private int tabCount;

    public TabAdapter(FragmentManager fragmentManager, int tabCount) {
        super(fragmentManager);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        // Returning the current tabs
        switch (position) {
            case 0:
                ElectronicsFragment electronicsFragment1 = new ElectronicsFragment();
                return electronicsFragment1;
            case 1:
                ElectronicsFragment electronicsFragment2 = new ElectronicsFragment();
                return electronicsFragment2;
            case 2:
                ElectronicsFragment electronicsFragment3 = new ElectronicsFragment();
                return electronicsFragment3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}

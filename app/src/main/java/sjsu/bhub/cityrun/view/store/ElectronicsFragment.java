package sjsu.bhub.cityrun.view.store;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import java.util.ArrayList;

import sjsu.bhub.cityrun.BaseFragment;
import sjsu.bhub.cityrun.R;
import sjsu.bhub.cityrun.data.DrawerMenuVO;
import sjsu.bhub.cityrun.databinding.FragmentElectronicsBinding;
import sjsu.bhub.cityrun.view.main.DrawerMenuAdapter;

public class ElectronicsFragment extends BaseFragment<FragmentElectronicsBinding> {

    private DrawerMenuAdapter adapter;
    View view;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_electronics;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {

        GridLayoutManager layoutManager = new GridLayoutManager(this.getContext(), 2);

        binding.fragmentRecyclerViewMenu.setLayoutManager(layoutManager);

        ArrayList<DrawerMenuVO> menuList = new ArrayList<>();
        menuList.add(new DrawerMenuVO(R.drawable.icon_step, "5000", "STEP"));
        menuList.add(new DrawerMenuVO(R.drawable.icon_treasure, "2000", "GOLD"));
        menuList.add(new DrawerMenuVO(R.drawable.icon_fire, "400", "Kcal"));
        menuList.add(new DrawerMenuVO(R.drawable.icon_distance, "5", "km"));

        adapter = new DrawerMenuAdapter(context, menuList);
        binding.fragmentRecyclerViewMenu.setAdapter(adapter);
    }

}

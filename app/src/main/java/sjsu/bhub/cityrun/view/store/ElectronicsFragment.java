package sjsu.bhub.cityrun.view.store;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import java.util.ArrayList;

import sjsu.bhub.cityrun.BaseFragment;
import sjsu.bhub.cityrun.R;
import sjsu.bhub.cityrun.data.StoreMenuVO;
import sjsu.bhub.cityrun.databinding.LayoutStoreMenuBinding;


public class ElectronicsFragment extends BaseFragment<LayoutStoreMenuBinding> {

    private StoreMenuAdapter adapter;
    View view;

    @Override
    protected int getLayoutId() {
        return R.layout.layout_store_menu;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {

        GridLayoutManager layoutManager = new GridLayoutManager(this.getContext(), 2);

        binding.fragmentRecyclerViewMenu.setLayoutManager(layoutManager);

        ArrayList<StoreMenuVO> menuList = new ArrayList<>();
        menuList.add(new StoreMenuVO(R.drawable.test1, "Ipad 64G", "$348,00"));
        menuList.add(new StoreMenuVO(R.drawable.test2, "Bluetooth Speaker", "$234.39"));
        menuList.add(new StoreMenuVO(R.drawable.test3, "Bang & Olufsen A9", "$552.00"));
        menuList.add(new StoreMenuVO(R.drawable.test4, "Samsung Tablet", "$552.00"));
        menuList.add(new StoreMenuVO(R.drawable.test5, "Airpod", "$159,99"));
        menuList.add(new StoreMenuVO(R.drawable.test6, "Mac Pro Laptop", "$409.78"));

        adapter = new StoreMenuAdapter(context, menuList);
        binding.fragmentRecyclerViewMenu.setAdapter(adapter);
    }

}

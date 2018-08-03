package sjsu.bhub.cityrun.view.store;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import sjsu.bhub.cityrun.BaseRecyclerViewAdapter;
import sjsu.bhub.cityrun.R;
import sjsu.bhub.cityrun.data.StoreMenuVO;
import sjsu.bhub.cityrun.databinding.LayoutStoreItemBinding;

public class StoreMenuAdapter extends BaseRecyclerViewAdapter<StoreMenuVO, StoreMenuAdapter.StoreMenuViewHolder> {

    public StoreMenuAdapter(Context context){
        super(context);
    }

    public StoreMenuAdapter(Context context, List<StoreMenuVO> list){
        super(context, list);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_store_item, null, false);
        return new StoreMenuViewHolder(view);
    }

    @Override
    protected void onBindView(StoreMenuViewHolder holder, int position) {
        final StoreMenuVO item = itemList.get(position);

        holder.binding.storeItemImage.setBackgroundResource(item.getMenuIconId());
        holder.binding.storeItemName.setText(item.getMenuName());
        holder.binding.storeItemPrice.setText(item.getMenuPrice());

    }

    public class StoreMenuViewHolder extends RecyclerView.ViewHolder {

        protected  LayoutStoreItemBinding binding;

        public StoreMenuViewHolder(View itemView) {
                super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}

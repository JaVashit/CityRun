package sjsu.bhub.cityrun.view.main;

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
import sjsu.bhub.cityrun.data.DrawerMenuVO;
import sjsu.bhub.cityrun.databinding.LayoutDrawerItemBinding;

public class DrawerMenuAdapter extends BaseRecyclerViewAdapter<DrawerMenuVO, DrawerMenuAdapter.DrawerMenuViewHolder> {

    public DrawerMenuAdapter(Context context){
        super(context);
    }

    public DrawerMenuAdapter(Context context, List<DrawerMenuVO> list){
        super(context, list);
    }

    @Override
    protected void onBindView(DrawerMenuViewHolder holder, int position) {
        final DrawerMenuVO item = itemList.get(position);

        holder.binding.imageStatusIcon.setImageResource(item.getStatusIconId());
        holder.binding.textStatusName.setText(item.getStatusName());
        holder.binding.textStatusUnit.setText(item.getStatusUnit());
        holder.binding.textStatus.setText("" + item.getStatus());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_drawer_item, null, false);
        return new DrawerMenuViewHolder(view);
    }

    public class DrawerMenuViewHolder extends RecyclerView.ViewHolder {

        protected  LayoutDrawerItemBinding binding;

        public DrawerMenuViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}

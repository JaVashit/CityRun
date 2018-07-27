package sjsu.bhub.cityrun;

import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import sjsu.bhub.cityrun.utils.SnackBarUtil;

public abstract class BaseActivity<B extends ViewDataBinding> extends AppCompatActivity {

    protected B binding;

    protected abstract int getLayoutId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 화면 세로 고정
        binding = DataBindingUtil.setContentView(this, getLayoutId());
    }

    protected void showSnackBar(String message){
        SnackBarUtil.showSnackbar(binding.getRoot(), message);
    }
}
package sjsu.bhub.cityrun.view.launch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import sjsu.bhub.cityrun.BaseActivity;
import sjsu.bhub.cityrun.R;
import sjsu.bhub.cityrun.databinding.ActivityLoginBinding;
import sjsu.bhub.cityrun.view.main.MainActivity;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
    }

    private void initView(){
        binding.buttonLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enter_no_anim, R.anim.exit_no_anim);
                finish();
            }
        });

        binding.buttonSignup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enter_no_anim, R.anim.exit_no_anim);
            }
        });
    }
}
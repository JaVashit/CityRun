package sjsu.bhub.cityrun.view.launch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import sjsu.bhub.cityrun.BaseActivity;
import sjsu.bhub.cityrun.R;
import sjsu.bhub.cityrun.databinding.ActivitySignupBinding;
import sjsu.bhub.cityrun.view.launch.LoginActivity;

public class SignupActivity extends BaseActivity<ActivitySignupBinding> {



    @Override
    protected int getLayoutId() {
        return R.layout.activity_signup;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
    }

    private void initView(){
        binding.signupRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showSnackBar("success! please login!");
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enter_no_anim, R.anim.exit_no_anim);
                finish();
            }
        });

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.countryList, android.R.layout.simple_spinner_dropdown_item);
        binding.signupCountrySpinner.setAdapter(arrayAdapter);


    }

}

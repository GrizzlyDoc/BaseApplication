package com.myki.challenge;

import android.os.Bundle;
import android.view.Window;
import com.myki.challenge.base.ui.BaseActivity;
import com.myki.challenge.main.MainFragment;

public class MainActivity extends BaseActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    super.onCreate(savedInstanceState);

    BaseApplication.get(this).appComponent().inject(this);
    setContentView(R.layout.activity_main);
    //ButterKnife.bind(this);

    getSupportFragmentManager().beginTransaction()
        .replace(R.id.main_content, new MainFragment())
        .commit();
  }
}

package com.grizzly.baseapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import com.grizzly.baseapplication.base.ui.BaseActivity;

public class MainActivity extends BaseActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    super.onCreate(savedInstanceState);
    BaseApplication.get(this).appComponent().inject(this);
    setContentView(R.layout.activity_main);
  }
}

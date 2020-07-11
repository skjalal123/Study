package com.example.study;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class SettingPage extends Activity {
LinearLayout Account1,VideoSetting,FAQ,PrivacyPolicy,TermCondition,RateUs;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settingpage);

        Account1 = findViewById(R.id.Account1);
        VideoSetting = findViewById(R.id.VideoSetting);
        FAQ = findViewById(R.id.FAQ);
        PrivacyPolicy = findViewById(R.id.PrivacyPolicy);
        TermCondition = findViewById(R.id.TermCondition);
        RateUs = findViewById(R.id.RateUs);

        Account1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AccountSetting.class));
            }
        });
    }
}

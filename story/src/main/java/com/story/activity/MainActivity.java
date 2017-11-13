package com.story.activity;

import android.os.Bundle;

import com.story.R;

import retrofit.Retrofit;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder();
    }

}

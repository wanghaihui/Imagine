package com.story.activity;

import android.os.Bundle;

import com.story.R;
import com.story.api.ApiService;
import com.story.bean.Repo;

import java.util.List;

import retrofit.Call;
import retrofit.Retrofit;
import retrofit.converters.gson.GsonConverterFactory;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl("www.baidu.com")
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<List<Repo>> repos = apiService.listRepos("octocat");
    }

}

package com.mambure.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.mambure.myapplication.adapters.ViewPagerAdapter;
import com.mambure.myapplication.viewmodels.MainActivityViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager2 viewpager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private MainActivityViewModel mainActivityViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        mainActivityViewModel = new ViewModelProvider(this)
                .get(MainActivityViewModel.class);

        // Setup viewmodel to obeserve lifecycle and get lifecycle callbacks;
        getLifecycle().addObserver(mainActivityViewModel);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle());
        viewpager.setAdapter(viewPagerAdapter);
        new TabLayoutMediator(tabLayout, viewpager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Skill IQ Leaders");
                    break;
                case 1:
                    tab.setText("Learning Leaders");
                    break;
            }
        }).attach();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainActivityViewModel.getMessagesLiveData()
                .observe(this, this::showMessage);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mainActivityViewModel.removeLiveDataObservers(this);
    }

    @OnClick(R.id.btn_submit)
    public void onClick() {
        Intent intent = new Intent(this, SubmitActivity.class);
        startActivity(intent);
    }

    private void showMessage(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show();
    }
}
package com.mambure.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.mambure.myapplication.adapters.HoursItemAdapter;
import com.mambure.myapplication.viewmodels.MainActivityViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LearningLeadersFragment extends Fragment {

    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private MainActivityViewModel mainActivityViewModel;
    private HoursItemAdapter hoursItemAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the same viewmodel instance of the parent activity
        mainActivityViewModel = new ViewModelProvider(requireActivity())
                .get(MainActivityViewModel.class);

        hoursItemAdapter = new HoursItemAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_layout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvList.setAdapter(hoursItemAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        progressBar.setVisibility(View.VISIBLE);
        mainActivityViewModel.getHoursLeaderboardLiveData()
                .observe(this, hourItems -> {
                    if(hourItems != null){
                        hoursItemAdapter.addData(hourItems);
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    public void onPause() {
        super.onPause();
        mainActivityViewModel.removeLiveDataObservers(this);
    }
}

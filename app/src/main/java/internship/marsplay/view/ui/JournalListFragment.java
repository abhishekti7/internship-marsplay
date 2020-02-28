package internship.marsplay.view.ui;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.List;

import internship.marsplay.R;
import internship.marsplay.service.model.Journal;
import internship.marsplay.viewmodel.JournalsViewModel;

public class JournalListFragment extends Fragment {

    private List<Journal> journalList ;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ConstraintLayout loader_layout;
    private ConstraintLayout journal_list_layout;
    private LottieAnimationView loader;

    public static internship.marsplay.view.adapters.JournalListAdapter journalListAdapter;
    private JournalsViewModel journalsViewModel;

    private Activity pActivity;

    public JournalListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_journal_list, container, false);

        pActivity = getActivity();
        recyclerView = view.findViewById(R.id.journal_recyclerview);
        swipeRefreshLayout = view.findViewById(R.id.journal_swiperefresh);
        loader_layout = view.findViewById(R.id.loader_layout);
        journal_list_layout = view.findViewById(R.id.journal_list_layout);
        loader = view.findViewById(R.id.screen1_loader);
        journalList = new ArrayList<>();

        loader.setProgress(0);
        loader.addAnimatorListener(new AnimatorListenerAdapter() {
            @Override public void onAnimationEnd(Animator animation) {
                loader.setProgress(0);
            }
        });

        loader_layout.setVisibility(View.VISIBLE);
        journal_list_layout.setVisibility(View.GONE);

        Handler handler = new Handler();
        Runnable r = new Runnable() {
            public void run() {
                viewModel();
            }
        };
        handler.postDelayed(r, 1000);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loader_layout.setVisibility(View.VISIBLE);
                journal_list_layout.setVisibility(View.GONE);
                journalList.clear();
                viewModel();

            }
        });
        return view;
    }

    private void viewModel(){
        //View Model
        swipeRefreshLayout.setRefreshing(false);
        journalsViewModel = ViewModelProviders.of(getActivity()).get(JournalsViewModel.class);
        journalsViewModel.init();
        journalsViewModel.getJournalRepository().observe(getActivity(), new Observer<List<Journal>>() {
            @Override
            public void onChanged(List<Journal> response) {
                Log.i("TAGGED", "onChanged: "+response);
                journalList.addAll(response);
                journalListAdapter.notifyDataSetChanged();
            }
        });
        setUpRecyclerView();
    }
    private void setUpRecyclerView() {
        journalListAdapter = new internship.marsplay.view.adapters.JournalListAdapter(journalList,pActivity);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(journalListAdapter);
        loader_layout.setVisibility(View.GONE);
        journal_list_layout.setVisibility(View.VISIBLE);
    }

}

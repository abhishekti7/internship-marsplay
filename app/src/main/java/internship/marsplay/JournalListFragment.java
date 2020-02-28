package internship.marsplay;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JournalListFragment extends Fragment {

    private List<Journal> journalList;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ConstraintLayout loader_layout;
    private ConstraintLayout journal_list_layout;
    private LottieAnimationView loader;

    public static JournalListAdapter journalListAdapter;
    private Activity pActivity;

    public JournalListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_journal_list, container, false);

        recyclerView = view.findViewById(R.id.journal_recyclerview);
        swipeRefreshLayout = view.findViewById(R.id.journal_swiperefresh);
        loader_layout = view.findViewById(R.id.loader_layout);
        loader = view.findViewById(R.id.screen1_loader);

        loader.setProgress(0);
        loader.addAnimatorListener(new AnimatorListenerAdapter() {
            @Override public void onAnimationEnd(Animator animation) {
                loader.setProgress(0);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        pActivity = getActivity();
        journalList = new ArrayList<>();
        loader_layout.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setVisibility(View.GONE);
        getResponse();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loader_layout.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setVisibility(View.GONE);
                journalList.clear();
                getResponse();
            }
        });
        return view;
    }

    private void getResponse(){
        swipeRefreshLayout.setRefreshing(false);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.JSONURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<RetrofitDataModel> call = apiInterface.getString();

        Log.i("URL", call.request().url().toString());
        call.enqueue(new Callback<RetrofitDataModel>() {
            @Override
            public void onResponse(Call<RetrofitDataModel> call, Response<RetrofitDataModel> response) {
                Log.i("MSG", response.body().toString());
                if(response.isSuccessful()){
                    if (response.body()!=null){
                        RetrofitDataModel resp = response.body();
                        parseResponse(resp);
                    }else{
                        Log.i("onEmptyResponse", "Returned empty response");
                    }
                }
            }

            @Override
            public void onFailure(Call<RetrofitDataModel> call, Throwable t) {
                Log.i("FAIL", t.getMessage());
            }
        });
    }

    private void parseResponse(RetrofitDataModel response){
        internship.marsplay.Response resp = response.getResponse();
        List<Journal> journals = resp.getJournal();
        for(Journal journal : journals){
            journalList.add(journal);
        }
        loader_layout.setVisibility(View.GONE);
        swipeRefreshLayout.setVisibility(View.VISIBLE);
        journalListAdapter = new JournalListAdapter(journalList, pActivity);
        recyclerView.setAdapter(journalListAdapter);
        journalListAdapter.notifyDataSetChanged();
    }
}

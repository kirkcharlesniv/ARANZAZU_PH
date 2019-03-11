package ph.aranzazushrine.aranzazuph.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ph.aranzazushrine.aranzazuph.API.ApiClient;
import ph.aranzazushrine.aranzazuph.API.ApiInterface;
import ph.aranzazushrine.aranzazuph.Adapter;
import ph.aranzazushrine.aranzazuph.Models.News;
import ph.aranzazushrine.aranzazuph.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {
    View v;
    RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private List<News> news = new ArrayList<>();
    private Adapter adapter;


    public NewsFragment() {
        // Required empty public constructor
    }


//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_news, container, false);
        recyclerView = v.findViewById(R.id.recyclerView);
        Adapter adapter = new Adapter(getContext(), news);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
//        layoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(layoutManager);

        LoadJSON();
//        setHasOptionsMenu(true);
        return v;
    }

    public void LoadJSON() {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<News>> call;
        call = apiInterface.getNews();
        call.enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                if (response.body() != null) {
                    if (!news.isEmpty()) {
                        news.clear();
                    }

                    news = response.body();
                    adapter = new Adapter(getContext(), news);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(getContext(), "No Result", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {
                Log.d("Error: ", t.toString());
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}

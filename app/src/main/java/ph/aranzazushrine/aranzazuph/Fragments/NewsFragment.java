package ph.aranzazushrine.aranzazuph.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ph.aranzazushrine.aranzazuph.API.ApiClient;
import ph.aranzazushrine.aranzazuph.API.ApiInterface;
import ph.aranzazushrine.aranzazuph.Adapters.NewsAdapter;
import ph.aranzazushrine.aranzazuph.Models.News;
import ph.aranzazushrine.aranzazuph.NewsDetailActivity;
import ph.aranzazushrine.aranzazuph.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static ph.aranzazushrine.aranzazuph.Utils.DateUtils.DateFormat;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    View v;
    RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private List<News> news = new ArrayList<>();
    private NewsAdapter newsAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;


    public NewsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_news, container, false);
        recyclerView = v.findViewById(R.id.recyclerView);
        NewsAdapter newsAdapter = new NewsAdapter(getContext(), news);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(newsAdapter);

        swipeRefreshLayout = v.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        onLoadingSwipe();
        return v;
    }

    private void onLoadingSwipe() {
        swipeRefreshLayout.post(
                new Runnable() {
                    @Override
                    public void run() {
                        LoadJSON();
                    }
                }
        );
    }

    public void LoadJSON() {
        swipeRefreshLayout.setRefreshing(true);
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<News>> call;
        call = apiInterface.getNews();
        call.enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(@NonNull Call<List<News>> call, @NonNull Response<List<News>> response) {
                if (response.body() != null) {
                    if (!news.isEmpty()) {
                        news.clear();
                    }

                    news = response.body();
                    newsAdapter = new NewsAdapter(getContext(), news);
                    recyclerView.setAdapter(newsAdapter);
                    newsAdapter.notifyDataSetChanged();
                    initListener();
                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    Toast.makeText(getContext(), "No Result", Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<News>> call, @NonNull Throwable t) {
                Log.d("Error: ", t.toString());
            }
        });
    }

    private void initListener() {
        newsAdapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                ImageView imageView = view.findViewById(R.id.newsImage);
                TextView headerText = view.findViewById(R.id.newsHeader);
                Intent intent = new Intent(getContext(), NewsDetailActivity.class);

                News newsInfo = news.get(i);
                intent.putExtra("url", "http://aranzazushrine.ph/?p=" + newsInfo.getId());
                intent.putExtra("image", newsInfo.getMedia());
                intent.putExtra("header", newsInfo.getTitle());
                intent.putExtra("published", DateFormat(newsInfo.getDate()));
                intent.putExtra("author", String.valueOf(newsInfo.getAuthor()));

                Pair<View, String> pair = Pair.create((View) imageView, ViewCompat.getTransitionName(imageView));
                Pair<View, String> headerPair = Pair.create((View) headerText, ViewCompat.getTransitionName(headerText));
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(Objects.requireNonNull(getActivity()), pair, headerPair);

                startActivity(intent, optionsCompat.toBundle());
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onRefresh() {
        LoadJSON();
    }

}

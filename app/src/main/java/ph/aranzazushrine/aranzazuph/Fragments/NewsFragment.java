package ph.aranzazushrine.aranzazuph.Fragments;


import android.content.Intent;
import android.os.Bundle;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ph.aranzazushrine.aranzazuph.API.ApiClient;
import ph.aranzazushrine.aranzazuph.API.ApiInterface;
import ph.aranzazushrine.aranzazuph.Adapters.NewsAdapter;
import ph.aranzazushrine.aranzazuph.Models.News;
import ph.aranzazushrine.aranzazuph.NewsDetailActivity;
import ph.aranzazushrine.aranzazuph.R;
import ph.aranzazushrine.aranzazuph.Utils.DateUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
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
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
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
            public void onFailure(Call<List<News>> call, Throwable t) {
                Log.d("Error: ", t.toString());
            }
        });
    }

    private void initListener() {
        newsAdapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                ImageView imageView = view.findViewById(R.id.newsImage);
                Intent intent = new Intent(getContext(), NewsDetailActivity.class);

                News newsInfo = news.get(i);
                intent.putExtra("ID", newsInfo.getId());
                intent.putExtra("url", "http://aranzazushrine.ph/?p=" + newsInfo.getId());
                intent.putExtra("header", newsInfo.getTitle());
                intent.putExtra("image", newsInfo.getMedia());
                intent.putExtra("time", DateUtils.DateToTimeFormat(newsInfo.getDate()));
                intent.putExtra("desc", newsInfo.getExcerpt());

                Pair<View, String> pair = Pair.create((View) imageView, ViewCompat.getTransitionName(imageView));
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), pair);

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

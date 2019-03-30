package ph.aranzazushrine.aranzazuph;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.shimmer.ShimmerFrameLayout;

import ph.aranzazushrine.aranzazuph.API.ApiClient;
import ph.aranzazushrine.aranzazuph.API.ApiInterface;
import ph.aranzazushrine.aranzazuph.Models.Author;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsDetailActivity extends AppCompatActivity {
    ShimmerFrameLayout shimmerFrameLayout;
    ShimmerFrameLayout newsShimmerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        ImageView detailImage = findViewById(R.id.detailImage);
        TextView detailHeader = findViewById(R.id.detailHeader);

        shimmerFrameLayout = findViewById(R.id.author_shimmer);
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmer();

        final CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("");


        Intent intent = getIntent();

        String mURL = intent.getStringExtra("url");
        String mImage = intent.getStringExtra("image");
        String mHeader = intent.getStringExtra("header");
        final String mPublished = intent.getStringExtra("published");
        String mAuthor = intent.getStringExtra("author");

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        final Call<Author> authorCall = apiInterface.getAuthor(mAuthor);
        authorCall.enqueue(new Callback<Author>() {
            @Override
            public void onResponse(Call<Author> call, Response<Author> response) {
                Author authorResponse = response.body();

                ImageView authorImage = findViewById(R.id.authorImage2);
                Glide.with(getBaseContext())
                        .load(String.valueOf(authorResponse.getPhoto()))
                        .into(authorImage);

                TextView authorName = findViewById(R.id.authorName);
                authorName.setText(authorResponse.getName());

                TextView detailPublished = findViewById(R.id.detailPublished);
                detailPublished.setText(mPublished);

                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);

                TextView byText = findViewById(R.id.byText);
                byText.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<Author> call, Throwable t) {
                Toast.makeText(getBaseContext(), "We currently cannot get the news. Please try again later!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        RequestOptions requestOptions = new RequestOptions();

        Glide.with(this)
                .load(mImage)
                .apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(detailImage);

        detailHeader.setText(mHeader);

        initWebView(mURL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        shimmerFrameLayout.stopShimmer();
    }
    
    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView(String url) {
        WebView webView = findViewById(R.id.webView);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                newsShimmerLayout = findViewById(R.id.news_shimmer);
                newsShimmerLayout.setVisibility(View.VISIBLE);
                newsShimmerLayout.startShimmer();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                newsShimmerLayout.stopShimmer();
                newsShimmerLayout.setVisibility(View.GONE);
            }
        });
        webView.loadUrl(url);

    }
}

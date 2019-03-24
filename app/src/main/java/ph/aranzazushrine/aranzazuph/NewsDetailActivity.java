package ph.aranzazushrine.aranzazuph;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import ph.aranzazushrine.aranzazuph.API.ApiClient;
import ph.aranzazushrine.aranzazuph.API.ApiInterface;
import ph.aranzazushrine.aranzazuph.Models.Author;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        ImageView detailImage = findViewById(R.id.detailImage);
        TextView detailHeader = findViewById(R.id.detailHeader);
        TextView detailPublished = findViewById(R.id.detailPublished);



        final CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("");


        Intent intent = getIntent();

        String mURL = intent.getStringExtra("url");
        String mImage = intent.getStringExtra("image");
        String mHeader = intent.getStringExtra("header");
        String mPublished = intent.getStringExtra("published");
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
            }

            @Override
            public void onFailure(Call<Author> call, Throwable t) {

            }
        });

        RequestOptions requestOptions = new RequestOptions();

        Glide.with(this)
                .load(mImage)
                .apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(detailImage);

        detailHeader.setText(mHeader);
        detailPublished.setText(mPublished);

        initWebView(mURL);
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
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}

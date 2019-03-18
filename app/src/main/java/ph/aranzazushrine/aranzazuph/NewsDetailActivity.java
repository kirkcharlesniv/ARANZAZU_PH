package ph.aranzazushrine.aranzazuph;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

public class NewsDetailActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {
    private ImageView detailImage;
    private TextView detailHeader, detailDesc, detailTime;
    private String mID, mURL, mImage, mHeader, mDesc, mTime;
    private WebView webView;

    private Toolbar toolbar;
    private boolean isHideToolbarView = false;
    private AppBarLayout appBarLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        detailImage = findViewById(R.id.detailImage);
        detailHeader = findViewById(R.id.detailHeader);
        detailDesc = findViewById(R.id.detailDesc);
        detailTime = findViewById(R.id.detailTime);

        final CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("");

        appBarLayout = findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(this);


        Intent intent = getIntent();

        mID = intent.getStringExtra("ID");
        mURL = intent.getStringExtra("url");
        mImage = intent.getStringExtra("image");
        mHeader = intent.getStringExtra("header");
        mDesc = intent.getStringExtra("desc");
        mTime = intent.getStringExtra("time");

        RequestOptions requestOptions = new RequestOptions();

        Glide.with(this)
                .load(mImage)
                .apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(detailImage);

        detailHeader.setText(mHeader);
        detailDesc.setText(mDesc);
        detailTime.setText(mTime);

        initWebView(mURL);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView(String url) {
        webView = findViewById(R.id.webView);
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
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(i) / (float) maxScroll;

        if (percentage == 1f && isHideToolbarView) {
            isHideToolbarView = !isHideToolbarView;
        } else if (percentage < 1f && isHideToolbarView) {
            isHideToolbarView = !isHideToolbarView;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}

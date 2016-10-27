package com.rance.beautypapa.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.rance.beautypapa.R;
import com.rance.beautypapa.base.BaseMvpActivity;
import com.rance.beautypapa.model.NewsDetailsEntity;
import com.rance.beautypapa.presenter.NewsDetailsPresenter;
import com.rance.beautypapa.view.NewsDetailsView;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：Rance on 2016/10/25 15:19
 * 邮箱：rance935@163.com
 */
public class NewsDetailsActivity extends BaseMvpActivity<NewsDetailsPresenter> implements NewsDetailsView {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private String id;
    @Bind(R.id.web_view)
    WebView webView;
    @Bind(R.id.iv_web_img)
    ImageView webImg;
    @Bind(R.id.tv_img_title)
    TextView imgTitle;
    @Bind(R.id.tv_img_source)
    TextView imgSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        toolbar.setTitle("啪啪日报");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        id = getIntent().getStringExtra("id");
        mvpPresenter.getDetailNews(id);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                showProgressDialog();
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                dismissProgressDialog();
            }
        });

        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String title) {
                toolbar.setTitle(title);
                setSupportActionBar(toolbar);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        });

    }

    @Override
    protected NewsDetailsPresenter createPresenter() {
        return new NewsDetailsPresenter(this);
    }

    @Override
    public void getNewsDetailsSuccess(NewsDetailsEntity newsEntity) {
        String head = "<head>\n" +
                "\t<link rel=\"stylesheet\" href=\"" + newsEntity.getCss()[0] + "\"/>\n" +
                "</head>";
        String img = "<div class=\"headline\">";
        String html = head + newsEntity.getBody().replace(img, " ");
        webView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
        Picasso.with(this).load(newsEntity.getImage()).into(webImg);

        imgTitle.setText(newsEntity.getTitle());
        imgSource.setText("来自:" + newsEntity.getImage_source());
    }

    @Override
    public void getNewsDetailsFail(String msg) {

    }
}

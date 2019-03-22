package tw.dddssz.allschoolthings;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Lunch extends AppCompatActivity
{
    private WebView web_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lunch);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        web_view= (WebView)findViewById(R.id.web_view);
        web_view.getSettings().setBuiltInZoomControls(true);
        web_view.getSettings().setJavaScriptEnabled(true);
        web_view.setWebViewClient(new WebViewClient());
        web_view.loadUrl("https://tcnr.wda.gov.tw/");
//        web_view.loadUrl("https://bit.ly/2TPwpdy");
        //https://fatraceschool.moe.gov.tw/frontend/search.html

//        web_view.loadUrl("file:///android_asset/example.html");
//        String data = Html.fromHtml(content).toString();
//        web_view.loadDataWithBaseURL("http://webhost.net", data, "text/html", "UTF-8", null);
//        String data = " Html 資料";
//        web_view.loadDataWithBaseURL(null,data, "text/html", "utf-8",null);
    }
    public void onBackPressed() {
        if (web_view.canGoBack()) {
            web_view.goBack();
            return;
        }
        super.onBackPressed();
    }

}

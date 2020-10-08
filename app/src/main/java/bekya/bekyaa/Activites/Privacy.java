package bekya.bekyaa.Activites;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import bekya.bekyaa.R;

import androidx.appcompat.app.AppCompatActivity;

public class Privacy extends AppCompatActivity {
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);
        webView = (WebView) findViewById(R.id.WebView);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

        webView.loadUrl("file:///android_asset/terms.html");
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.setInitialScale(135);


    }
}

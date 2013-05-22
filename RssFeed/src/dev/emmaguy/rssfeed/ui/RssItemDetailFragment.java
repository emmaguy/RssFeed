package dev.emmaguy.rssfeed.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import dev.emmaguy.rssfeed.R;

public class RssItemDetailFragment extends Fragment {
    private String rssItemUrl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

	if (savedInstanceState != null) {
	    rssItemUrl = savedInstanceState.getString("rssItemUrl");
	}

	return inflater.inflate(R.layout.rss_item_detail_fragment, container, false);
    }

    @Override
    public void onStart() {
	super.onStart();

	Bundle args = getArguments();
	if (args != null) {
	    showRssItemUrlInWebView(args.getString("rssItemUrl"));
	}
    }

    @Override
    public void onSaveInstanceState(Bundle savingState) {
	super.onSaveInstanceState(savingState);

	savingState.putString("rssItemUrl", rssItemUrl);
    }

    public void showRssItemUrlInWebView(String rssItemUrl) {

	WebView webView = (WebView) getActivity().findViewById(R.id.web_view);
	final ProgressBar webViewProgressBar = (ProgressBar) getActivity().findViewById(R.id.page_load_progress_bar);

	// open links in this webview rather than resorting to opening a browser
	webView.setWebViewClient(new WebViewClient());

	webView.setWebChromeClient(new WebChromeClient() {
	    public void onProgressChanged(WebView view, int progress) {
		if (progress < 100 && webViewProgressBar.getVisibility() == ProgressBar.GONE) {
		    webViewProgressBar.setVisibility(ProgressBar.VISIBLE);
		}
		webViewProgressBar.setProgress(progress);
		if (progress == 100) {
		    webViewProgressBar.setVisibility(ProgressBar.GONE);
		}
	    }
	});
	webView.getSettings().setBuiltInZoomControls(true);
	webView.loadUrl(rssItemUrl);
    }
}
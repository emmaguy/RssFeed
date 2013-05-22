package dev.emmaguy.rssfeed.ui;

import java.util.List;

import org.xml.sax.InputSource;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import dev.emmaguy.rssfeed.R;

import dev.emmaguy.rssfeed.core.RssItem;
import dev.emmaguy.rssfeed.rss.RssItemParserAsyncTask;
import dev.emmaguy.rssfeed.rss.RssItemParserAsyncTask.OnRetrievedRssItems;
import dev.emmaguy.rssfeed.ui.RssFeedListFragment.OnRssItemSelectedListener;

public class RssFeedActivity extends FragmentActivity implements OnRssItemSelectedListener, OnRetrievedRssItems {

    @Override
    public void onCreate(Bundle savedInstanceState) {

	super.onCreate(savedInstanceState);
	setContentView(R.layout.rss_feed_activity);

	if (findViewById(R.id.fragment_container) != null) {
	    if (savedInstanceState != null) {
		return;
	    }

	    RssFeedListFragment rssFeed = new RssFeedListFragment();
	    getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, rssFeed).commit();
	}

	new RssItemParserAsyncTask(this, new InputSource("http://feeds.bbci.co.uk/news/technology/rss.xml")).execute();
    }

    @Override
    public void onRetrievedRssItems(List<RssItem> rssItems) {

	if (rssItems.size() == 0) {
	    Toast.makeText(getApplication(), "Failed to retrieve any rss items :-(", Toast.LENGTH_LONG).show();
	    return;
	}

	RssFeedListFragment rssFeedListFragment = (RssFeedListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
	if (rssFeedListFragment != null) {
	    rssFeedListFragment.setRssItems(rssItems);
	} else {
	    RssFeedListFragment feedListFragment = (RssFeedListFragment) getSupportFragmentManager().findFragmentById(R.id.rss_feed_list);
	    feedListFragment.setRssItems(rssItems);
	}
    }

    @Override
    public void onRssItemSelected(String rssItemUrl) {
	RssItemDetailFragment rssItemDetailFragment = (RssItemDetailFragment) getSupportFragmentManager().findFragmentById(R.id.rss_feed_detail);
	
	if (rssItemDetailFragment == null) {

	    // the detail fragment is not in the layout => we're displaying 1 fragment only
	    Bundle args = new Bundle();
	    args.putString("rssItemUrl", rssItemUrl);

	    RssItemDetailFragment newrssItemDetailFragment = new RssItemDetailFragment();
	    newrssItemDetailFragment.setArguments(args);

	    FragmentTransaction transaction = (FragmentTransaction) getSupportFragmentManager().beginTransaction();
	    transaction.replace(R.id.fragment_container, newrssItemDetailFragment);
	    transaction.addToBackStack(null);
	    transaction.commit();

	} else {
	    rssItemDetailFragment.showRssItemUrlInWebView(rssItemUrl);
	}
    }
}
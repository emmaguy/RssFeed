package dev.emmaguy.rssfeed.ui;

import java.util.List;

import org.xml.sax.InputSource;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.dev.emmaguy.shazamtags.R;

import dev.emmaguy.rssfeed.core.RssItem;
import dev.emmaguy.rssfeed.rss.RssItemParserAsyncTask;
import dev.emmaguy.rssfeed.rss.RssItemParserAsyncTask.OnRetrievedTagsFromRss;
import dev.emmaguy.rssfeed.ui.RssFeedListFragment.OnTagSelectedListener;

public class RssFeedActivity extends FragmentActivity implements OnTagSelectedListener, OnRetrievedTagsFromRss {

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
    public void onRetrievedTags(List<RssItem> tags) {

	if (tags.size() == 0) {
	    Toast.makeText(getApplication(), "Failed to retrieve any rss items :-(", Toast.LENGTH_LONG).show();
	    return;
	}

	RssFeedListFragment tagsRssFeedListFragment = (RssFeedListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
	if (tagsRssFeedListFragment != null) {
	    tagsRssFeedListFragment.setTags(tags);
	} else {
	    RssFeedListFragment feedListFragment = (RssFeedListFragment) getSupportFragmentManager().findFragmentById(R.id.rss_feed_list);
	    feedListFragment.setTags(tags);
	}
    }

    @Override
    public void onTagSelected(String tagUrl) {
	RssItemDetailFragment tagDetailFragment = (RssItemDetailFragment) getSupportFragmentManager().findFragmentById(R.id.rss_feed_detail);
	
	if (tagDetailFragment == null) {

	    // the detail fragment is not in the layout => we're displaying 1 fragment only
	    Bundle args = new Bundle();
	    args.putString("tagUrl", tagUrl);

	    RssItemDetailFragment newTagDetailFragment = new RssItemDetailFragment();
	    newTagDetailFragment.setArguments(args);

	    FragmentTransaction transaction = (FragmentTransaction) getSupportFragmentManager().beginTransaction();
	    transaction.replace(R.id.fragment_container, newTagDetailFragment);
	    transaction.addToBackStack(null);
	    transaction.commit();

	} else {
	    tagDetailFragment.showTagUrlInWebView(tagUrl);
	}
    }
}
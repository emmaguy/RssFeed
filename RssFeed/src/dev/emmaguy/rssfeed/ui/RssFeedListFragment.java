package dev.emmaguy.rssfeed.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import dev.emmaguy.rssfeed.R;
import dev.emmaguy.rssfeed.core.RssItem;

public class RssFeedListFragment extends ListFragment {
    private List<RssItem> rssItems = new ArrayList<RssItem>();
    private OnRssItemSelectedListener listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	this.setListAdapter(new RssFeedArrayAdapter(getActivity(), R.layout.rss_item, rssItems));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
	super.onActivityCreated(savedInstanceState);

	RssItemDetailFragment rssItemDetailFragment = (RssItemDetailFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.rss_feed_detail);
	if (rssItemDetailFragment != null) {
	    getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
	}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	return inflater.inflate(R.layout.rss_feed_list_fragment, null);
    }

    public void setRssItems(List<RssItem> rssItems) {
	this.rssItems.clear();
	this.rssItems.addAll(rssItems);

	((RssFeedArrayAdapter) getListAdapter()).notifyDataSetChanged();
    }

    public interface OnRssItemSelectedListener {
	public void onRssItemSelected(String rssItemUrl);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

	RssItem clickedRssItem = rssItems.get(position);

	listener.onRssItemSelected(clickedRssItem.getLink());
    }

    @Override
    public void onAttach(Activity activity) {

	super.onAttach(activity);

	try {
	    listener = (OnRssItemSelectedListener) activity;
	} catch (ClassCastException e) {
	    throw new ClassCastException(activity.toString() + " must implement OnRssItemSelectedListener");
	}
    }
}
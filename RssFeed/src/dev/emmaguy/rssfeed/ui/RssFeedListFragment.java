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

import com.dev.emmaguy.shazamtags.R;

import dev.emmaguy.rssfeed.core.RssItem;

public class RssFeedListFragment extends ListFragment {
    private List<RssItem> tags = new ArrayList<RssItem>();
    private OnTagSelectedListener listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	this.setListAdapter(new RssFeedArrayAdapter(getActivity(), R.layout.rss_item, tags));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
	super.onActivityCreated(savedInstanceState);

	RssItemDetailFragment tagDetailFragment = (RssItemDetailFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.rss_feed_list);
	if (tagDetailFragment != null) {
	    getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
	}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	return inflater.inflate(R.layout.rss_feed_list_fragment, null);
    }

    public void setTags(List<RssItem> tags) {
	this.tags.clear();
	this.tags.addAll(tags);

	((RssFeedArrayAdapter) getListAdapter()).notifyDataSetChanged();
    }

    public interface OnTagSelectedListener {
	public void onTagSelected(String tagUrl);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

	RssItem clickedTag = tags.get(position);

	listener.onTagSelected(clickedTag.getLink());
    }

    @Override
    public void onAttach(Activity activity) {

	super.onAttach(activity);

	try {
	    listener = (OnTagSelectedListener) activity;
	} catch (ClassCastException e) {
	    throw new ClassCastException(activity.toString() + " must implement OnTagSelectedListener");
	}
    }
}
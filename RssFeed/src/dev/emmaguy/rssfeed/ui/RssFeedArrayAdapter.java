package dev.emmaguy.rssfeed.ui;

import java.util.List;

import dev.emmaguy.rssfeed.R;
import dev.emmaguy.rssfeed.core.RssItem;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class RssFeedArrayAdapter extends ArrayAdapter<RssItem> {
    private List<RssItem> rssItems;
    private LayoutInflater viewInflater;

    public RssFeedArrayAdapter(Context context, int textViewResourceId, List<RssItem> rssItems) {
	super(context, textViewResourceId, rssItems);

	this.rssItems = rssItems;
	this.viewInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
	View view = convertView;
	if (view == null) {
	    view = viewInflater.inflate(R.layout.rss_item, null);
	}

	RssItem rssItem = rssItems.get(position);
	if (rssItem != null) {
	    TextView title = (TextView) view.findViewById(R.id.title);
	    TextView artist = (TextView) view.findViewById(R.id.description);

	    title.setText(rssItem.getTitle());
	    artist.setText(rssItem.getDescription());
	}
	
	if(position % 2 == 0) {
	    view.setBackgroundColor(Color.parseColor("#BBFAFEFA"));
	} else {
	    view.setBackgroundColor(Color.parseColor("#BBCACACC"));
	}
	return view;
    }
}

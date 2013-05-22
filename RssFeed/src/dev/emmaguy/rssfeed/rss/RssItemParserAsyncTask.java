package dev.emmaguy.rssfeed.rss;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.os.AsyncTask;
import android.util.Log;
import dev.emmaguy.rssfeed.core.RssItem;

public class RssItemParserAsyncTask extends AsyncTask<Void, Void, List<RssItem>> {

    private InputSource rssFeedSource;
    private OnRetrievedTagsFromRss retrievedTags;

    public RssItemParserAsyncTask(OnRetrievedTagsFromRss retrievedTags, InputSource rssFeedSource) {
	this.retrievedTags = retrievedTags;
	this.rssFeedSource = rssFeedSource;
    }

    @Override
    protected List<RssItem> doInBackground(Void... arg0) {

	RssItemSAXHandler rssItemSaxHandler = new RssItemSAXHandler();

	try {
	    
	    XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
	    xmlReader.setContentHandler(rssItemSaxHandler);
	    xmlReader.parse(rssFeedSource);
	    
	} catch (ParserConfigurationException e) {
	    Log.e("RssFeed", e.getMessage());
	} catch (SAXException e) {
	    Log.e("RssFeed", e.getMessage());
	} catch (IOException e) {
	    Log.e("RssFeed", e.getMessage());
	}

	return rssItemSaxHandler.getRssItems();
    }

    public interface OnRetrievedTagsFromRss {
	public void onRetrievedTags(List<RssItem> tags);
    }

    @Override
    protected void onPostExecute(List<RssItem> tags) {
	retrievedTags.onRetrievedTags(tags);
    }
}

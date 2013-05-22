package dev.emmaguy.rssfeed.rss;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.*;
import org.xml.sax.helpers.*;

import dev.emmaguy.rssfeed.core.RssItem;
import dev.emmaguy.rssfeed.core.RssItemBuilder;

import android.util.Log;

public class RssItemSAXHandler extends DefaultHandler {
    
    private List<RssItem> rssItems = new ArrayList<RssItem>();
    
    private RssItemBuilder rssItemBuilder = new RssItemBuilder();
    private StringBuilder elementContentsStringBuilder = new StringBuilder();
    
    private boolean isInChannelElement = false;
    
    public List<RssItem> getRssItems() {
	return rssItems;
    }

    @Override
    public void startElement(String uri, String localName, String qualifiedName, Attributes attributes) {
	if (localName.equalsIgnoreCase("channel")) {
	    isInChannelElement = true;
	} else if (localName.equalsIgnoreCase("item")) {
	    if (isInChannelElement) {
		rssItemBuilder.clear();
	    }
	}
    }
    
    @Override
    public void characters (char ch[], int start, int length) {
	if (elementContentsStringBuilder != null) {
	    for (int index = start; index < start + length; index++) {
		elementContentsStringBuilder.append(ch[index]);
	    }
	}
    }

    @Override
    public void endElement(String uri, String localName, String qualifiedName) {
	if (localName.equalsIgnoreCase("channel")) {
	    isInChannelElement = false;
	} else if (localName.equalsIgnoreCase("item")) {

	    try {
		rssItems.add(rssItemBuilder.build());
	    } catch (Exception e) {
		Log.e("RssFeed", e.getMessage());
	    }

	} else if (localName.equalsIgnoreCase("title")) {
	    rssItemBuilder.setTitle(retrieveElementContentsString());
	} else if (localName.equalsIgnoreCase("description")) {
	    rssItemBuilder.setDescription(retrieveElementContentsString());
	} else if (localName.equalsIgnoreCase("link")) {
	    rssItemBuilder.setLink(retrieveElementContentsString());
	}
	
	clearElementContentsStringBuilder();
    }

    private String retrieveElementContentsString() {
	return elementContentsStringBuilder.toString().trim();
    }

    private void clearElementContentsStringBuilder() {
	elementContentsStringBuilder.delete(0, elementContentsStringBuilder.length());
    }
}
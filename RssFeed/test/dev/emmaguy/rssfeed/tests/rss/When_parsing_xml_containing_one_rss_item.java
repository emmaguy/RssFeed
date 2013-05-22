package dev.emmaguy.rssfeed.tests.rss;

import java.io.StringReader;


import dev.emmaguy.rssfeed.core.RssItem;
import dev.emmaguy.rssfeed.rss.RssItemSAXHandler;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import static org.junit.Assert.assertEquals;

@RunWith(org.robolectric.RobolectricTestRunner.class)
public class When_parsing_xml_containing_one_rss_item {

    private RssItem firstRssItem;

    @Before
    public void setup() throws Exception {

	RssItemSAXHandler handler = new RssItemSAXHandler();

	XMLReader parser = XMLReaderFactory.createXMLReader();
	parser.setContentHandler(handler);
	parser.parse(new InputSource(new StringReader(
		"<channel>" + 
			"<item>" + 
				"<title>Title title</title>" + 
				"<description>Descriptions are cool, yo</description>" + 
				"<link>www.twitter.com/emmaguy</link>" + 
			"</item>" + 
		"</channel>")));

	firstRssItem = handler.getRssItems().get(0);
    }

    @Test
    public void should_be_able_to_retrieve_title() throws Exception {
	assertEquals(firstRssItem.getTitle(), "Title title");
    }

    @Test
    public void should_be_able_to_retrieve_description() throws Exception {
	assertEquals(firstRssItem.getDescription(), "Descriptions are cool, yo");
    }

    @Test
    public void should_be_able_to_retrieve_link() throws Exception {
	assertEquals(firstRssItem.getLink(), "www.twitter.com/emmaguy");
    }
}
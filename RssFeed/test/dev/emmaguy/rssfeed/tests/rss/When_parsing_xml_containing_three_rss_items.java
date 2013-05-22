package dev.emmaguy.rssfeed.tests.rss;

import static org.junit.Assert.assertEquals;

import java.io.StringReader;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import dev.emmaguy.rssfeed.core.RssItem;
import dev.emmaguy.rssfeed.rss.RssItemSAXHandler;

@RunWith(org.robolectric.RobolectricTestRunner.class)
public class When_parsing_xml_containing_three_rss_items {

    private List<RssItem> rssItems;

    @Before
    public void setup() throws Exception {

	RssItemSAXHandler handler = new RssItemSAXHandler();

	XMLReader parser = XMLReaderFactory.createXMLReader();
	parser.setContentHandler(handler);
	parser.parse(new InputSource(new StringReader(
				"<channel>" + 
					"<item>" + 
        					"<title>Title1</title>" + 
        					"<description>Desc</description>" +  
        					"<link>Link1</link>" + 
					"</item>" + 
					"<item>" + 
        					"<title>Title2</title>" + 
        					"<description>Desc2</description>" +  
        					"<link>Link2</link>" + 
					"</item>" + 
					"<item>" + 
        					"<title>Track3</title>" + 
        					"<link>Link3</link>" + 
					"</item>" + 
				"</channel>")));
	
	rssItems = handler.getRssItems();
    }

    @Test
    public void should_have_3_rss_items() throws Exception {
	assertEquals(rssItems.size(), 3);
    }
}
package dev.emmaguy.rssfeed.tests.core;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import dev.emmaguy.rssfeed.core.RssItemBuilder;

@RunWith(org.robolectric.RobolectricTestRunner.class)
public class When_trying_to_build_an_rss_item_with_no_title {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void should_throw_meaningful_exception() throws Exception {

	exception.expect(Exception.class);
	exception.expectMessage("Title of an rss item cannot be empty");

	RssItemBuilder rssItemBuilder = new RssItemBuilder();
	rssItemBuilder.setLink("link");
	rssItemBuilder.build();
    }
}
package dev.emmaguy.rssfeed.core;

public class RssItem {
    private String title;
    private String link;
    private String description;

    public RssItem(String title, String link, String description) {
	this.title = title;
	this.link = link;
	this.description = description;
    }

    public String getTitle() {
	return this.title;
    }

    public String getLink() {
	return this.link;
    }

    public String getDescription() {
	return this.description;
    }
}
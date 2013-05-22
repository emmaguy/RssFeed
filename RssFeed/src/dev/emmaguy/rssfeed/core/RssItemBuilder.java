package dev.emmaguy.rssfeed.core;

public class RssItemBuilder {
    private String title;
    private String link;
    private String description;

    public RssItem build() throws Exception {

	if (isStringIsNullOrEmpty(title)) {
	    throw new Exception("Title of a tag cannot be empty");
	}
	if (isStringIsNullOrEmpty(link)) {
	    throw new Exception("Link of a tag cannot be empty");
	}

	return new RssItem(title, link, description);
    }

    public void setTitle(String title) {
	this.title = title;
    }

    public void setLink(String link) {
	this.link = link;
    }
    
    public void setDescription(String description) {
	this.description = description;
    }

    private boolean isStringIsNullOrEmpty(String str) {
	return str == null || str.length() == 0;
    }

    public void clear() {
	this.title = "";
	this.link = "";
	this.description = "";
    }
}

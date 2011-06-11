package org.xwiki.android.rest;

public class Requests {

	private String URLprefix;

	public Requests(String URLprefix) {
		this.URLprefix = URLprefix;
	}

	// Method for search
	public String requestSearch(String wikiName, String keyword) {

		Search search = new Search(URLprefix);
		return search.decodeSearchResponse(search.doWikiSearch(wikiName,
				keyword));
	}

	public String requestSearch(String wikiName, String spaceName,
			String keyword) {

		Search search = new Search(URLprefix);
		return search.decodeSearchResponse(search.doSpacesSearch(wikiName,
				spaceName, keyword));
	}

	// Method for retrieving Wikis
	public String requestWiki() {

		WikiResources wikiresources = new WikiResources(URLprefix);
		return wikiresources.decodeWikiResponse(wikiresources.getWikis());
	}

	// Method for retrieving Spaces
	public String requestSpaces() {
		String wikiName = "xwiki";
		SpaceResources spacesresources = new SpaceResources(URLprefix, wikiName);
		return spacesresources.decodeSpaceResponse(spacesresources.getSpaces());
	}

	// Method for requesting pages (to be implemented)
	public void requestPage() {

	}

}

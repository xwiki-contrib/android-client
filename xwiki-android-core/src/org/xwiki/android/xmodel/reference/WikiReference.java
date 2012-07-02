package org.xwiki.android.xmodel.reference;

public class WikiReference extends EntityReference {
	String wiki;

	public String getWiki() {
		return wiki;
	}

	public void setWiki(String wiki) {
		this.wiki = wiki;
	}

	@Override
	public String getURL() {
		// TODO Auto-generated method stub
		return null;
	}
}

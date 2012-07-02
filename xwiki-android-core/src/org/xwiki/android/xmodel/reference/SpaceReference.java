package org.xwiki.android.xmodel.reference;

public class SpaceReference extends EntityReference {
	String wiki;
	String space;
	
	public String getWiki() {
		return wiki;
	}
	public void setWiki(String wiki) {
		this.wiki = wiki;
	}
	public String getSpace() {
		return space;
	}
	public void setSpace(String space) {
		this.space = space;
	}
	@Override
	public String getURL() {
		// TODO Auto-generated method stub
		return null;
	}
}

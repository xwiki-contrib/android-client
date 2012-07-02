package org.xwiki.android.xmodel.reference;

public class ObjectReference extends DocumentReference {
	public ObjectReference(String wikiName, String spaceName, String pageName, String objType, int number ) {
		super(wikiName, spaceName, pageName);
		type=objType;
		this.number=number;
	}
	String type;
	int number;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
}

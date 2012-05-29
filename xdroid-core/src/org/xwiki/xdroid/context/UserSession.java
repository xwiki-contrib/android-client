package org.xwiki.xdroid.context;

import java.util.Hashtable;

public class UserSession extends Hashtable {
	
	private String userName;
	private String pwdUri;
	
	UserSession(){}//make constructing a new UserSession() prohibited outside package.
	
	public String getUserName(){
		return userName;
	}
	/**
	 * Read pwd from persistant store. Decrypt it and return.
	 * Does not store plain text pwd in memory for enhanced security.
	 * @return password of current user plain text.
	 */
	public String getPassword(){
		//TODO: read pwd from uri and return decrypted pwd.
		return pwdUri;
	}
		
}

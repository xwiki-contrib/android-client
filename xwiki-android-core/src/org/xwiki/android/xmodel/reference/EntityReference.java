package org.xwiki.android.xmodel.reference;

import java.io.File;
import java.net.MalformedURLException;

import org.xwiki.android.context.XWikiApplicationContext;
import org.xwiki.android.context.XWikiApplicationContextAPI;
import org.xwiki.android.entity.User;

import com.j256.ormlite.field.DatabaseField;

/**
 * 
 * @author xwiki gsoc 2012
 *
 *support both file:// (for local) and http:// (for remote) URI types.
 */
public abstract class EntityReference<T> {
	//do not mark this class as @DatabaseTable, table inheritance is not yet supported in ORMLite.
	//make inheriting class mark @DatabaseTable
	
	@DatabaseField(id=true)
	int _id;//local id for persistence. "_" to comply with android adapters. No need to set. Set by ORMlite
		
	/**
	 * file:// sections
	 * 
	 */
	File file;
	@DatabaseField()
	String filePath;
	/**
	 * http:// scheme. host section 
	 * sample:- http://username:password@host:8080/directory/file?querryparam #fragment
	 */
	@DatabaseField
	String host;
	@DatabaseField
	String port;	
	/**
	 * http:// scheme. user info section:  the  username:password  part
	 */	
	@DatabaseField(foreign=true)
	User authInfo;
	
	public EntityReference() {
		XWikiApplicationContextAPI ctx=XWikiApplicationContext.getInstance();
		String server=ctx.getUserSession().getRealm(); //host+port  //TODO: When xwiki-android supports multiple wikis, which may be seperate authentication realms, you might need to change here.
		String arg[]=server.split(":");
		host=arg[0];
		port=arg[1];			
	}
	
	
	//special getters
	public String getAuthority(){
		return authInfo.getUserName()+":"+authInfo.getPassword()+"@"+host+":"+port;
	}
	public abstract String getURL();	
	
	//special setters
	
	public void setFromURL() throws MalformedURLException{
		//TODO: implement
	}
	
	//getter setters
	
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
		this.filePath=file.getAbsolutePath();
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public User getAuthInfo() {
		return authInfo;
	}
	public void setAuthInfo(User authInfo) {
		this.authInfo = authInfo;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	
	
	
}

package org.xwiki.android.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
/**
 * 
 * @author admin	
 *
 */
@DatabaseTable(tableName="C_USER")
public class User {
	//public vars
	@DatabaseField(generatedId=true,useGetSet=true)
	private int _id;	
	@DatabaseField(useGetSet=true)
	private String firstName;
	@DatabaseField(useGetSet=true)
	private String lastName;
	@DatabaseField(canBeNull=false, uniqueCombo=true,useGetSet=true)
	private String userName;	
	@DatabaseField(useGetSet=true)
	private String encryptedPassword;
	@DatabaseField(useGetSet=true)
	private String email;	
	@DatabaseField( uniqueCombo=true,useGetSet=true)
	private String wikiRealm;	
	

	
	public String getWikiRealm() {
		return wikiRealm;
	}
	public void setWikiRealm(String wikiUrl) {
		//TODO : pattern matching to remove unwanted path tails.
		this.wikiRealm = wikiUrl;
	}	
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * use security utils to decrypt the pwd.
	 * returns empty string if password is not stored.
	 * @return encrypted Password
	 */
	public String getEncryptedPassword() {
		if (encryptedPassword==null){
			encryptedPassword="";
		}
		return encryptedPassword;
	}
	/**
	 * do not store plain text pwd
	 * @param password
	 */
	public void setEncryptedPassword(String password) {
		this.encryptedPassword = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}	
	
	//constructors
	public User(){}
	public User(String firstName, String lastName, String userName, String password, String email){
		//TODO SET VARS
	}

}

package org.xwiki.android.entity;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
@Deprecated
@DatabaseTable(tableName="C_LoginAttempt_LOG")
public class LoginAttempt {
	@DatabaseField
	int _id;
	@DatabaseField
	String userName;
	@DatabaseField
	String wikiRealm;
	@DatabaseField
	Date time;
	@DatabaseField
	String status;
	@DatabaseField
	int responseCode;
	
	public static final String STATUS_SUCCEED="s";
	public static final String STATUS_FAILURE="f";	
	
	public LoginAttempt() {	}//for ormlite

	public LoginAttempt(String userName, String wikiRealm, Date time,
			String status, int responseCode) {
		super();
		this.userName = userName;
		this.wikiRealm = wikiRealm;
		this.time = time;
		this.status = status;
		this.responseCode = responseCode;
	}
	
	
	
}

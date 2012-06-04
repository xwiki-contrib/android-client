package org.xwiki.android.process;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.xwiki.android.context.XAContextInitializer;
import org.xwiki.android.dal.XORMOpenHelper;
import org.xwiki.android.entity.LoginAttempt;
import org.xwiki.android.entity.User;
import org.xwiki.android.rest.HttpConnector;

import com.j256.ormlite.dao.Dao;

import android.content.Context;
import android.util.Log;

public class LoginFacade {
	/**
	 * Send login request to user.
	 * Create new user if new entry.
	 * Logs the login attempt.
	 * @param username
	 * @param password
	 * @param url
	 * @param ctx  the activity context or application context
	 * @return  response Code.
	 */
	public int login(String username,String password,String url, Context ctx){		
		HttpConnector httpConnector = new HttpConnector();
        int code = httpConnector.checkLogin(username, password, url); 
        //start seperate thread to add login attempt to DB. (update LoginAttempt , update User if new user data combination )
        Thread statusUpdater=new Thread(new statusUpdater(username,password,url,code,ctx));
        statusUpdater.start();        
        return code;
	}
	
	//TODO: revise: what about using a method local inner class?
	/**
	 * 
	 * @author sasinda
	 *
	 *runnable. Update the XAContext,(initialize user session)
	 *Update database if new username, xwiki  server realm combination
	 *Log the login attempt.
	 */
	private class statusUpdater implements Runnable{
		
		private String usrname,pwd,url;
		private String realm;//url can be anything, realm identifies the part of the url where the user is authenticated
		private int code;
		private Context ctx;
		statusUpdater(String usrname, String pwd, String url,int code, Context ctx){
			this.usrname=usrname;
			this.pwd=pwd;
			this.url=url;
			this.code=code;
			this.ctx=ctx;
		}
		@Override
		public void run() {
			XORMOpenHelper helper=new XORMOpenHelper(ctx);
			Log.i(LoginFacade.class.getSimpleName(),"updating state "+usrname+" "+pwd+" "+url+" "+code);
			if(code==200){//success
				//update context to authenticated state. if new user add to db;
				
				try {
					Dao<User,Integer> udao=helper.getDao(User.class);
					User search=new User();
					search.setUserName(usrname);
					search.setWikiRealm(url);					
					List<User>matches=udao.queryForMatchingArgs(search);
					if(matches.isEmpty()){
						//this is a new user -->create new entry
						search.setEncryptedPassword(pwd);//TODO: encrypt the pw here and set.But do not implement a method like: encryptAndSetPWD(String pwPlain) in the entity.
						udao.create(search);
						Log.i(LoginFacade.class.getSimpleName(),"created new user db entry "+search.getUserName()+" "+search.get_id());
					}
					Dao<LoginAttempt,Integer> logins=helper.getDao(LoginAttempt.class);
					LoginAttempt la=new LoginAttempt(usrname,url,new Date(),LoginAttempt.STATUS_SUCCEED,code);
					logins.create(la);					
				} catch (SQLException e) {					
					Log.e(this.getClass().getSimpleName(),"cannot create entry ofr user",e);
				}
			//	XAContextInitializer.updateToAuthenticatedState(user);
				
				
			}else {
				Dao<LoginAttempt, Integer> logins;
				try {
					logins = helper.getDao(LoginAttempt.class);
					LoginAttempt la=new LoginAttempt(usrname,url,new Date(),LoginAttempt.STATUS_FAILURE,code);
					logins.create(la);
				} catch (SQLException e) {					
					e.printStackTrace();
				}				
			}
			helper.close();			
		}		
		
	}
	
}

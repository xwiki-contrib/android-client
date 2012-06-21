package org.xwiki.android.cmnSvc;

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
	 * @param remPwd : wether to remember the password.
	 * @param ctx  the activity context or application context
	 * @return  response Code.
	 */
	public int login(String username,String password,String url,boolean remPwd, Context ctx){		
		HttpConnector httpConnector = new HttpConnector();
        int code = httpConnector.checkLogin(username, password, url); 
        //start seperate thread to add login attempt to DB. (update LoginAttempt , update User if new user data combination )
        if(remPwd==false){
        	password=null;
        }        
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
					List<User>matches=udao.queryForMatching(search);
					if(matches.isEmpty()){
						//this is a new user -->create new entry
						search.setPassword(pwd);//User object should manage if pwd is null
						udao.create(search);
						Log.i(LoginFacade.class.getSimpleName(),"created new user db entry "+search.getUserName()+" "+search.get_id());
						//ctx update						
						XAContextInitializer.updateToAuthenticatedState(search);
					}else{
						User u=matches.get(0);
						//if pwd was not saved earlier save it now. If remPwd is false delete existing remembered pwd entry
						if(u.getPassword()==null&pwd!=null || pwd==null & u.getPassword()!=null){
							u.setPassword(pwd);
							udao.update(u);
						}							
						XAContextInitializer.updateToAuthenticatedState(u);
					}
					//log the attempt
					Dao<LoginAttempt,Integer> logins=helper.getDao(LoginAttempt.class);
					LoginAttempt la=new LoginAttempt(usrname,url,new Date(),LoginAttempt.STATUS_SUCCEED,code);
					logins.create(la);					
				} catch (SQLException e) {					
					Log.e(this.getClass().getSimpleName(),"cannot create/update entry of user",e);
					//if db fail update the ctx yet with new val
					XAContextInitializer.updateToAuthenticatedState(new User(null,null,usrname,pwd,url,null));
				}
				
				
				
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

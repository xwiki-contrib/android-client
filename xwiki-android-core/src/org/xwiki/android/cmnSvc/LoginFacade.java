package org.xwiki.android.cmnSvc;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.xwiki.android.context.XWikiApplicationContext;
import org.xwiki.android.dal.EntityManager;
import org.xwiki.android.entity.LoginAttempt;
import org.xwiki.android.entity.User;
import org.xwiki.android.rest.HttpConnector;

import com.j256.ormlite.dao.Dao;

import android.content.Context;
import android.util.Log;

public class LoginFacade
{
    /**
     * Send login request to user. Create new user if new entry. Logs the login attempt.
     * 
     * @param username
     * @param password
     * @param url
     * @param remPwd : wether to remember the password.
     * @param ctx the activity context or application context
     * @return response Code.
     */
    public int login(String username, String password, String url, boolean remPwd)
    {
        HttpConnector httpConnector = new HttpConnector();
        int code = httpConnector.checkLogin(username, password, url);
        // start seperate thread to add login attempt to DB. (update LoginAttempt , update User if new user data
        // combination )
        Thread statusUpdater = new Thread(new statusUpdater(username, password, url, code, remPwd));
        statusUpdater.start();
        return code;
    }

    // TODO: revise: what about using a method local inner class?
    /**
     * @author sasinda runnable. Update the XWikiApplicationContext,(initialize user session) Update database if new
     *         username, xwiki server realm combination Log the login attempt.
     */
    private class statusUpdater implements Runnable
    {

        private String usrname, pwd, url;

        private String realm;// url can be anything, realm identifies the part of the url where the user is
                             // authenticated

        private int code;

        private boolean remPwd;

        statusUpdater(String usrname, String pwd, String url, int code, boolean remPwd)
        {
            this.usrname = usrname;
            this.pwd = pwd;
            this.url = url;
            this.code = code;
            this.remPwd = remPwd;
        }

        public void run()
        {
            XWikiApplicationContext ctx = XWikiApplicationContext.getInstance();
            EntityManager eman = ctx.newEntityManager();
            Log.i(LoginFacade.class.getSimpleName(), "updating state " + usrname + " " + pwd + " " + url + " " + code);
            if (code == 200) {// success
                // update context to authenticated state. if new user add to db;

                try {
                    Dao<User, Integer> udao = eman.getDao(User.class);
                    User search = new User();
                    search.setUserName(usrname);
                    search.setWikiRealm(url);
                    List<User> matches = udao.queryForMatching(search);
                    if (matches.isEmpty()) {
                        // this is a new user -->create new entry
                        search.setPassword(pwd);// User object should manage if pwd is null

                        // ctx update
                        ((XWikiApplicationContext) ctx.getApplicationContext()).updateToAuthenticatedState(search);
                        // db
                        User dbuser = search.clone();
                        if (remPwd == false) {
                            dbuser.setPassword(null);
                        }
                        udao.create(dbuser);
                        Log.i(LoginFacade.class.getSimpleName(), "created new user db entry " + dbuser.getUserName()
                            + " " + dbuser.get_id());
                    } else {
                        User u = matches.get(0);
                        User ucpy = u.clone();
                        // if pwd was not saved earlier save it now. If remPwd is false delete existing remembered pwd
                        // entry
                        if (u.getPassword() == null & pwd != null || pwd == null & u.getPassword() != null) {
                            ucpy.setPassword(pwd);
                            udao.update(ucpy);
                        }
                        ((XWikiApplicationContext) ctx.getApplicationContext()).updateToAuthenticatedState(u);
                    }
                    // log the attempt
                    Dao<LoginAttempt, Integer> logins = eman.getDao(LoginAttempt.class);
                    LoginAttempt la = new LoginAttempt(usrname, url, new Date(), LoginAttempt.STATUS_SUCCEED, code);
                    logins.create(la);
                } catch (SQLException e) {
                    Log.e(this.getClass().getSimpleName(), "cannot create/update entry of user", e);
                    // if db fail update the ctx yet with new val
                    ((XWikiApplicationContext) ctx.getApplicationContext()).updateToAuthenticatedState(new User(null,
                        null, usrname, pwd, url, null));
                }

            } else {
                Dao<LoginAttempt, Integer> logins;
                try {
                    logins = eman.getDao(LoginAttempt.class);
                    LoginAttempt la = new LoginAttempt(usrname, url, new Date(), LoginAttempt.STATUS_FAILURE, code);
                    logins.create(la);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            eman.close();
        }

    }

}
